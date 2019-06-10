package com.business.service.music;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by lindanghong on 2019/6/10.
 */

public class MusicService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }
}
