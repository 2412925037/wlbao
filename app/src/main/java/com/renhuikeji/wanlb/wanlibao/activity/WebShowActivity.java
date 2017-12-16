package com.renhuikeji.wanlb.wanlibao.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.config.ConfigValue;
import com.renhuikeji.wanlb.wanlibao.utils.CheckAppUtils;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.DialogUtils;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Web展示界面
 */
public class WebShowActivity extends BaseActivity {

    @BindView(R.id.title_left_one_btn)
    ImageView titleLeftOneBtn;
    @BindView(R.id.tv_middle_title)
    TextView tvMiddleTitle;
    @BindView(R.id.title_right_one_tv)
    TextView titleRightOneTv;
    @BindView(R.id.web_web_show)
    WebView webWebShow;
    @BindView(R.id.title_right_one_btn)
    ImageView titleRightOne;
    private View ll_fail;
    private String url;
    private String title;
    private String rightText;
    private Handler mhandler = null;
    private String uid;
    private String session;
    private OkHttpClient client;
    private boolean isAccept = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_show);
        ButterKnife.bind(this);

        uid = (String) SPUtils.get(WebShowActivity.this, Constant.User_Uid, "");
        session = (String) SPUtils.get(WebShowActivity.this, Constant.MSESSION, "");
        mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    byte[] bytes = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    saveBitmap(bitmap);
                    DialogUtils.stopProgressDlg();
                    ToastUtils.toastForShort(WebShowActivity.this, "二维码保存成功");
                } else {
                    DialogUtils.stopProgressDlg();
                    ToastUtils.toastForShort(WebShowActivity.this, "二维码下载失败！");
                }

            }
        };

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        rightText = intent.getStringExtra("right_text");

        initViews();
    }

    private void saveBitmap(Bitmap bitmap) {
// 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "wanlibao_qrcode");
        Log.d("wch", "saveBitmap: " + appDir.getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
    }

    private void downLoadimg() {
        DialogUtils.showProgressDlg(WebShowActivity.this, "正在保存二维码.....");
        String encode = URLEncoder.encode("http://m.yasbao.com/index.php/Home/User/regist.html?rec=" + uid);
        String str_url = "http://app.yasbao.com/Application/Home/Common/t1.php?text=" + encode;


        client = new OkHttpClient();
        final Request request = new Request.Builder().get()
                .addHeader("cookie", session)
                .url(str_url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // e.printStackTrace();
                ToastUtils.toastForShort(WebShowActivity.this, "下载失败了");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = mhandler.obtainMessage();
                if (response.isSuccessful()) {
                    message.what = 1;
                    message.obj = response.body().bytes();
                    mhandler.sendMessage(message);
                } else {
                    mhandler.sendEmptyMessage(0);
                }
            }
        });
    }

    private void initViews() {
        titleLeftOneBtn.setVisibility(View.VISIBLE);
        tvMiddleTitle.setText(title);
        ll_fail = findViewById(R.id.load_fail_web_show);

        if (!TextUtils.isEmpty(rightText) && !"".equals(rightText)) {
            titleRightOneTv.setVisibility(View.VISIBLE);
            titleRightOneTv.setText(rightText);
        } else {
            titleRightOne.setVisibility(View.VISIBLE);
        }

        loadUrl(url);

        webWebShow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isAccept) {
                    final NormalDialog dialog = DialogUtils.getNormalDialog(WebShowActivity.this, "提示", "是否将二维码保存到本地？");
                    dialog.show();
                    dialog.setOnBtnClickL(new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.dismiss();
                        }
                    }, new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.dismiss();
                            downLoadimg();
                        }
                    });
                }
                return false;
            }
        });

    }

    private void loadUrl(String url) {
        DialogUtils.showProgressDlg(this, "努力加载中……");
        webWebShow.getSettings().setJavaScriptEnabled(true);
        webWebShow.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());

                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                DialogUtils.stopProgressDlg();
                if (url.contains("InviteFriends_app")) {
                    isAccept = true;
                } else {
                    isAccept = false;
                }
                checkMyPermiss();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                webWebShow.setVisibility(View.GONE);
                ll_fail.setVisibility(View.VISIBLE);
                DialogUtils.stopProgressDlg();
            }
        });
        webWebShow.loadUrl(url);
    }

    @OnClick({R.id.title_left_one_btn, R.id.title_right_one_tv, R.id.title_right_one_btn, R.id.web_web_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.web_web_show:
                break;
            case R.id.title_left_one_btn:
                finish();
                break;
            case R.id.title_right_one_btn:
                startActivity(new Intent(this, TaoBaoSearchActivity.class));
            case R.id.title_right_one_tv:
                switch (rightText) {
                    case "打开微信":
                        if (CheckAppUtils.isAvilible(this, "com.tencent.mm")) {// 传入指定应用包名
                            Intent wxIntent = new Intent();
                            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                            wxIntent.setAction(Intent.ACTION_MAIN);
                            wxIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                            wxIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            wxIntent.setComponent(cmp);
                            startActivity(wxIntent);
                        } else {
                            ToastUtils.toastForShort(this, "未安装微信");
                        }
                        break;
                    case "开始推广":
                        Intent webIntent = getIntent();
                        webIntent.putExtra("url", ConfigValue.INVITE_FRIENDS);
                        webIntent.putExtra("title", "邀请好友");
                        webIntent.putExtra("right_text", "");
                        startActivity(webIntent);
                        break;
                    case "认领订单":
                        startActivity(new Intent(this, ConfirmBillActivity.class));
                        break;
                }
                break;
        }
    }

    //----------------------------------------------------------------------


    /**
     * 检查提醒用的权限
     */
    private void checkMyPermiss() {
        AndPermission.with(WebShowActivity.this)
                .requestCode(200)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale(rationaleListener)
                .callback(listener)
                .start();
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
               // ToastUtils.toastForShort(WebShowActivity.this, "权限申请成功");
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                // Toast.makeText(SplashActivity.this, "失败", Toast.LENGTH_SHORT).show();

                // 是否有不再提示并拒绝的权限。
                if (AndPermission.hasAlwaysDeniedPermission(WebShowActivity.this, deniedPermissions)) {
                    // 第一种：用AndPermission默认的提示语。
                    // AndPermission.defaultSettingDialog(activity, 400).show();

                    // 第二种：用自定义的提示语。
                    AndPermission.defaultSettingDialog(WebShowActivity.this, 400)
                            .setTitle("权限申请失败")
                            .setMessage("您拒绝了我们必要的一些权限，请在设置中授权！")
                            .setPositiveButton("去设置")
                            .show();
                }
            }
        }
    };
    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            AlertDialog.newBuilder(WebShowActivity.this)
                    .setTitle("友好提醒")
                    .setMessage("你已拒绝写入权限，无法使用一些功能!")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            rationale.cancel();
                        }
                    })
                    .show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 400: { // 这个400就是你上面传入的数字。
                if (AndPermission.hasPermission(WebShowActivity.this, Manifest.permission.WRITE_CALENDAR)) {
                    ToastUtils.toastForShort(WebShowActivity.this, "权限设置成功");
                }
                break;
            }
        }
    }

    //----------------------------------------------------------------------
}
