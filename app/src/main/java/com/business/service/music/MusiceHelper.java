package com.business.service.music;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.alibaba.fastjson.JSON;
import com.business.service.music.server.SongBean;
import com.business.service.music.server.SongResponBean;
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.EasyHttpUtils;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.easysoft.utils.lib.http.ResponseMsg;
import com.easysoft.utils.lib.system.ToastUtils;
import com.linlsyf.area.R;

import java.util.List;

/**
 * Created by lindanghong on 2019/6/10.
 */

public class MusiceHelper {
    public static MusicServiceConnection musicServiceConnection;
    public static String url="https://jirenguapi.applinzi.com/fm/getSong.php?channel=public_yuzhong_yueyu";
    public  static Context mContext;
    public static MusiceHelper mHelper;
    public static playCallBack mPlayCallBack;
    private static SongBean playSong;

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
               musicServiceConnection=new MusicServiceConnection();

               Intent intentMusic = new Intent(context, MusicService.class);
               context.bindService(intentMusic, musicServiceConnection, context.BIND_AUTO_CREATE);
//               context.startService(intentMusic);
           }

    }
    public static  void play(String url){
        musicServiceConnection.getMusicBinder().play(url);

    }
    public static  void playYueyu(String url){
        musicServiceConnection.getMusicBinder().play(url);

    }
    public static  void get(String url,IEasyResponse iResponse) throws Exception{

//        EasyHttpUtils.getInStance().post(url,iResponse).setOutside(true));
//        EasyHttpUtils.getInStance().post(url,new EasyHttpCallback(iResponse).setOutside(true));

    }
    public static  void checkPlay(playCallBack callBack)  {

        mPlayCallBack=callBack;
          if (musicServiceConnection.getMusicBinder() !=null&&musicServiceConnection.getMusicBinder().isPlaying()){
              musicServiceConnection.getMusicBinder().stop();

              if (callBack!=null){
                  callBack.callBack(true,null);
              }
              return;
          }

          if (   musicServiceConnection.getMusicBinder()!=null&&musicServiceConnection.getMusicBinder().isHasInit()){
              musicServiceConnection.getMusicBinder().resumePlay();
              if (callBack!=null){
                  callBack.callBack(false,playSong);
              }
        }else{
            getRadomAndPlay();

        }


    }
    public static  void next(playCallBack callBack)  {

        mPlayCallBack=callBack;
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

                    final  ResponseMsg   msg = callBackResult.getResponseMsg();

                            try {
                                SongResponBean songResponBeanBean = JSON.parseObject(msg.getData().toString(), SongResponBean.class);
                                List<SongBean> songBeanList = JSON.parseArray(songResponBeanBean.getSong().toString(), SongBean.class);

                                 if (songBeanList.size()>0){
                                       playSong= songBeanList.get(0);

                                     musicServiceConnection.getMusicBinder().play(playSong.getUrl());
                                     musicServiceConnection.getMusicBinder().getPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                         @Override
                                         public void onCompletion(MediaPlayer mediaPlayer) {
                                             getRadomAndPlay();
                                         }
                                     });
                                     if (mPlayCallBack!=null){
                                         mPlayCallBack.callBack(false,playSong);
                                     }
                                 }

                            }catch (Exception e){

//                                ToastUtils.show(mContext,mContext.getString(R.string.exec_fail));
                            }


                }
            });
        } catch (Exception e) {
            ToastUtils.show(mContext,mContext.getString(R.string.exec_fail));

        }
    }

    public  interface  playCallBack{

        void callBack(boolean isPlayIng, SongBean songBean);

    }


}
