package com.business.service.music;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by Administrator on 2019/6/14 0014.
 */

public class MusicServiceConnection  implements ServiceConnection {
    MusicBinder  musicBinder;
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        musicBinder=(MusicBinder)iBinder;
    }

    public MusicBinder getMusicBinder() {
        return musicBinder;
    }

    public void setMusicBinder(MusicBinder musicBinder) {
        this.musicBinder = musicBinder;
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
