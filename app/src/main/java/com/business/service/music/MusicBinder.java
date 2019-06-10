package com.business.service.music;

import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by lindanghong on 2019/6/10.
 */

public class MusicBinder extends Binder {
    private MediaPlayer player;
    private boolean  hasInit=false;

    public MusicBinder() {
        this.player=new MediaPlayer();
    }

    //判断是否处于播放状态
    public boolean isPlaying(){
        return player.isPlaying();
    }

    //播放或暂停歌曲
    public void play(String url) {
      
        if (player.isPlaying()) {
            player.pause();
        } else {

            if (hasInit){
             player.start();

            }else{
                try {
                    player.setDataSource(url);
                    //异步准备
                    player.prepareAsync();
                    //添加准备好的监听
                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            //如果准备好了，就会进行这个方法
                            mp.start();
                            hasInit=true;
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        //Log.e("服务", "播放音乐");
    }

    //返回歌曲的长度，单位为毫秒
    public int getDuration(){
        return player.getDuration();
    }

    //返回歌曲目前的进度，单位为毫秒
    public int getCurrenPostion(){
        return player.getCurrentPosition();
    }

    //设置歌曲播放的进度，单位为毫秒
    public void seekTo(int mesc){
        player.seekTo(mesc);
    }



}
