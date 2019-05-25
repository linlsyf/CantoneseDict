package com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.business.bean.VideoBussinessItem;
import com.easysoft.utils.lib.system.ToastUtils;

import java.io.File;



public class OpenFileUtils {
    public  static void openVideo(Activity activity, String path){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File file = new File(path);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "video/*");
            activity.startActivity(intent);
        }catch (Exception e){
            ToastUtils.show(activity,"打开文件失败"+e.getMessage());
        }

    }
    public static void openVideoBySelf(Context context, VideoBussinessItem imgBean){
//        Intent homeIntent = new Intent(context, VideoDKActivity.class);
//        homeIntent.putExtra("url", imgBean.getData());
//        context.startActivity(homeIntent);
    }

}
