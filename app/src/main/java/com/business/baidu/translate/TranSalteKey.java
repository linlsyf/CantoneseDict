package com.business.baidu.translate;

import com.core.KeyStoreCommon;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TranSalteKey {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
//    private static final String APP_ID = "20190507000294875";
//    private static final String SECURITY_KEY = "p081XtTH_hK047KG6SU1";

    public static void toTran(final SearchParam param, final translatecallback callBack) {



        Observable.create(new ObservableOnSubscribe< String >() {
            @Override
            public void subscribe(ObservableEmitter<String  > emitter)
                    throws Exception {
               TransApi api = new TransApi(KeyStoreCommon.APP_ID, KeyStoreCommon.SECURITY_KEY);

//        String query = "高度600米";
                String result=  api.getTransResult(param.getQuery(), param.getFrom(), param.getTo());


                emitter.onNext(result);

            } })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer< String  >() {
            @Override public void onSubscribe(Disposable d) {
//				mDisposable=d;
            }
            @Override public void onNext( String reuslt) {
                callBack.call(reuslt);


            }
            @Override public void onError(Throwable e) {
            }

            @Override public void onComplete() {

            }
        });

    }


    public  interface translatecallback extends Serializable {

        void   call(Object result);
        void    showMsg(String msg);

    }
//     public void readKeyValue(final Context context){
//         String filecidanName= "liju_yue-jt-kfcd-2018620.txt";
//
//         try {
//             InputStream inputStream = context.getAssets().open(filecidanName);
//
//             String result = "";//如果path是传递过来的参数，可以做一个非目录的判断
//
//             String readJson="";
//             try {
//                 if (inputStream != null)
//                 {
//                     BufferedReader buffreader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
//                     String line;
//                     //分行读取
//                     while (( line = buffreader.readLine()) != null) {
//                         if (StringUtils.isNotEmpty(line)){
//                             try{
//                                 readJson=readJson+line.trim();
//                             }catch (Exception e){
//                                 System.out.print(e.getMessage());
//                             }
//
//
//                         }
////						newList.add(line+"\n");
//                     }
//                     inputStream.close();
//                 }
//             }
//             catch (Exception e)
//             {
//                 Log.d("TestFile", "The File doesn't not exist.");
//             }
//
//
//
//
//         } catch (Exception e1) {
//             e1.printStackTrace();
//         }
//         Json.p
//
//
//     }
}
