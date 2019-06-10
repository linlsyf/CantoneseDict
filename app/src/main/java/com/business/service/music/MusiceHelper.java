package com.business.service.music;

import android.content.Context;
import android.content.Intent;

/**
 * Created by lindanghong on 2019/6/10.
 */

public class MusiceHelper {


    public static  void startService(Context context){
        Intent intentMusic = new Intent(context, MusicService.class);
        context.startService(intentMusic);
    }
}
