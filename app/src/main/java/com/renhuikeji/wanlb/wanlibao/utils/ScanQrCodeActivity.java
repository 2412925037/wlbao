package com.renhuikeji.wanlb.wanlibao.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.renhuikeji.wanlb.wanlibao.R;
import com.renhuikeji.wanlb.wanlibao.activity.BaseActivity;

public class ScanQrCodeActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;
    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);
        button = (Button) findViewById(R.id.moren);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.moren:
                cameraTask();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(ScanQrCodeActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
//    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
    public void cameraTask() {
//        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
//            Intent intent = new Intent(getApplication(), CaptureActivity.class);
//            startActivityForResult(intent, REQUEST_CODE);
//        } else {
//            // Ask for one permission
//            EasyPermissions.requestPermissions(this, "需要请求camera权限",
//                    REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
//        }
    }


//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        Toast.makeText(this, "执行onPermissionsGranted()...", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        Toast.makeText(this, "执行onPermissionsDenied()...", Toast.LENGTH_SHORT).show();
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            new AppSettingsDialog.Builder(this, "当前App需要申请camera权限,需要打开设置页面么?")
//                    .setTitle("权限申请hhhh")
//                    .setPositiveButton("确认")
//                    .setNegativeButton("取消", null /* click listener */)
//                    .setRequestCode(REQUEST_CAMERA_PERM)
//                    .build()
//                    .show();
//        }
//    }
}
