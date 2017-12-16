package com.renhuikeji.wanlb.wanlibao.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.adapter.GuideViewPagerAdapter;
import com.renhuikeji.wanlb.wanlibao.utils.Constant;
import com.renhuikeji.wanlb.wanlibao.utils.SPUtils;
import com.renhuikeji.wanlb.wanlibao.utils.StatusBarUtil;
import com.renhuikeji.wanlb.wanlibao.utils.ToastUtils;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xialo on 2016/7/25.
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager vp;
    private GuideViewPagerAdapter adapter;
    private List<View> views;
    private Button startBtn;

    // 引导页图片资源
    private static final int[] pics = {R.layout.guide_view1,
            R.layout.guide_view2, R.layout.guide_view3};

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StatusBarUtil.setTranslucentForImageView(GuideActivity.this,0,null);
        views = new ArrayList<View>();

        // 初始化引导页视图列表
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);

            if (i == pics.length - 1) {
                startBtn = (Button) view.findViewById(R.id.btn_enter);
                startBtn.setTag("enter");
                startBtn.setOnClickListener(this);
            }

            views.add(view);

        }

        vp = (ViewPager) findViewById(R.id.vp_guide);
        adapter = new GuideViewPagerAdapter(views);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new PageChangeListener());

        initDots();
        checkAllPermiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        // 如果切换到后台，就设置下次不进入功能引导页
        SPUtils.put(GuideActivity.this, Constant.First_START, false);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        dots = new ImageView[pics.length];

        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(false);// 都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(true); // 设置为白色，即选中状态

    }

    /**
     * 设置当前view
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);
    }

    /**
     * 设置当前指示点
     *
     * @param position
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("enter")) {
            enterMainActivity();
            return;
        }

        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }


    private void enterMainActivity() {
        SPUtils.put(GuideActivity.this, Constant.First_START, false);
        Intent intent = new Intent(GuideActivity.this,
                SplashActivity.class);
        startActivity(intent);

        finish();
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int position) {

        }

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            // 设置底部小点选中状态
            setCurDot(position);
        }

    }


    //----------------------------------------------------------------------


    /**
     * 检查提醒用的权限
     */
    private void checkAllPermiss() {
        AndPermission.with(GuideActivity.this)
                .requestCode(200)
                .permission(
                        Manifest.permission.WRITE_CALENDAR,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA
                )
                .rationale(rationaleListener)
                .callback(listener)
                .start();
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 这里的requestCode就是申请时设置的requestCode。
            if (requestCode == 200) {
                // ToastUtils.toastForShort(getActivity(), "权限申请成功");
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                // Toast.makeText(SplashActivity.this, "失败", Toast.LENGTH_SHORT).show();

                // 是否有不再提示并拒绝的权限。
                if (AndPermission.hasAlwaysDeniedPermission(GuideActivity.this, deniedPermissions)) {
                    // 第一种：用AndPermission默认的提示语。
                    // AndPermission.defaultSettingDialog(activity, 400).show();

                    // 第二种：用自定义的提示语。
                    AndPermission.defaultSettingDialog(GuideActivity.this, 400)
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
            AlertDialog.newBuilder(GuideActivity.this)
                    .setTitle("友好提醒")
                    .setMessage("你已拒绝权限，无法使用一些功能!")
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 400: { // 这个400就是你上面传入的数字。
                if (AndPermission.hasPermission(GuideActivity.this, Manifest.permission.WRITE_CALENDAR)) {
                    ToastUtils.toastForShort(GuideActivity.this, "权限设置成功");
                }
                break;
            }
        }
    }

    //----------------------------------------------------------------------
}
