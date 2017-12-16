package com.renhuikeji.wanlb.wanlibao.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-2-16.
 */

public class PermissionUtil {

    public static boolean isAllow(Context mContext,String permission){
        boolean b=false;
        if(ContextCompat.checkSelfPermission(mContext,  permission)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{permission},
                    11);
            b=false;
            Toast.makeText(mContext,"请打开权限！",Toast.LENGTH_LONG).show();
        }else{
            b=true;
        }
        return b;
    }
}
