package com.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Map;

public class PermissionRequest {
//    String string_permission,int request_code
public static Activity mActivity;
    public static void request(Activity activity,List<PermissioBean> requestList){

        mActivity=activity;
        for (PermissioBean itemBean:requestList ) {

     ActivityCompat.requestPermissions(activity,new String[]{itemBean.name,itemBean.code+""} , 1);

        if(checkReadPermission(itemBean.name, itemBean.code)){

        }

        }


//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , 1);

//        if(checkReadPermission(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_READ_STOR_PERMISSION)){
//
//        }
//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_SETTINGS} , 1);
//
//        if(checkReadPermission(Manifest.permission.WRITE_SETTINGS, REQUEST_READ_STOR_PERMISSION)){
//
//        }
    }

    public static boolean checkReadPermission(String string_permission,int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(mActivity, string_permission) ==  PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(mActivity, new String[]{string_permission}, request_code);
        }
        return flag;
    }

}
