package com.business.service.music;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.alibaba.fastjson.JSON;
import com.business.service.music.server.SongBean;
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.EasyHttpUtils;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.easysoft.utils.lib.http.ResponseMsg;
import com.easysoft.utils.lib.system.ToastUtils;
import com.linlsyf.area.R;

/**
 * Created by lindanghong on 2019/6/10.
 */

public class MusiceHelper {
    public static MusicServiceConnection musicServiceConnection;
    public static String url="http://zhangmenshiting.qianqian.com/data2/music/124822723/124822723.mp3?xcode=2c4474f89edc223f350a5c038dd045b5";
    public  static Context mContext;
    public static MusiceHelper mHelper;
    public static MusiceHelper getInstance(Context context){

          if (null==mHelper){
              mHelper=new MusiceHelper();
          }
         mContext=context;
           startService(context);
          return  mHelper;

    }


    public static  void startService(Context context){
           if (musicServiceConnection==null){
               Intent intentMusic = new Intent(context, MusicService.class);
               context.startService(intentMusic);
               musicServiceConnection=new MusicServiceConnection();
           }

    }
    public static  void play(String url){
        musicServiceConnection.getMusicBinder().play(url);

    }
    public static  void playYueyu(String url){
        musicServiceConnection.getMusicBinder().play(url);

    }
    public static  void get(String url,IEasyResponse iResponse) throws Exception{

        EasyHttpUtils.getInStance().post(url,new EasyHttpCallback(iResponse));

    }
    public static  void checkPlay()  {

          if (musicServiceConnection.getMusicBinder().isPlaying()){
              musicServiceConnection.getMusicBinder().stop();
              return ;
          }
        getRadomAndPlay();

    }

    private static void getRadomAndPlay() {
        try {
            MusiceHelper.get(MusiceHelper.url, new IEasyResponse() {
                @Override
                public void onFailure(CallBackResult callBackResult) {

                    ToastUtils.show(mContext,mContext.getString(R.string.exec_fail));
                }

                @Override
                public void onResponse(CallBackResult callBackResult) {

                    ResponseMsg msg = callBackResult.getResponseMsg();
                    SongBean songBean = JSON.parseObject(msg.getMsg(), SongBean.class);
                    musicServiceConnection.getMusicBinder().play(songBean.getUrl());
                    musicServiceConnection.getMusicBinder().getPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            getRadomAndPlay();
                        }
                    });

                }
            });
        } catch (Exception e) {
            ToastUtils.show(mContext,mContext.getString(R.string.exec_fail));

        }
    }


}
