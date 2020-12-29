package com.utils;

import android.content.Context;
import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.core.CoreApplication;
import com.core.db.greenDao.entity.Dict;
import com.core.db.greenDao.gen.DaoMaster;
import com.core.db.greenDao.gen.DaoSession;
import com.core.db.greenDao.gen.DictDao;
import com.easysoft.utils.lib.system.FileUtils;
import com.linlsyf.cantonese.R;

import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lindanghong on 2019/2/26.
 */

public class Info2File {
    static String fileName="yydict.txt";

     public static InputStream getJsonFileOutputStream() throws Exception {
         File sd = Environment.getExternalStorageDirectory();
         String fullPath = sd.getPath() + "/MMMDebug/"+fileName;
         File file = new File(fullPath);
         if(!file.exists()) {
           int i=0;
         }
         InputStream instream = new FileInputStream(file);
         return instream;
     }

    public static String saveCrashInfo2File(String txt) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        StringBuffer sb = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        sb.append(txt);

        printWriter.close();
//        String appVersion = "1.0";
//        String descappend = appVersion + "\r\n" + TimeAreaUtils.getCurrentTime() + "\r\n";
//        String result = descappend + writer.toString();
        sb.append(txt);

        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
//            String fileName = time + "-" + timestamp + ".txt";

            if(Environment.getExternalStorageState().equals("mounted")) {
                File sd = Environment.getExternalStorageDirectory();
                String fullPath = sd.getPath() + "/MMMDebug/";
                File dir = new File(fullPath);
                if(!dir.exists()) {
                    dir.mkdirs();
                }

                FileUtils.delAllFile(fullPath);
                FileOutputStream fos = new FileOutputStream(fullPath + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }

            return fileName;
        } catch (Exception var17) {
            return null;
        }
    }


//    public static  void initDb(final Context context,final  parseDictcallback  dictcallback){
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "back.db");
//        Database db = helper.getReadableDb();
//        DaoSession daoSession = new DaoMaster(db).newSession();
//        DictDao dao = daoSession.getDictDao();
//        if (dao!=null){
//            List<Dict>  data=    dao.loadAll();
//            int  size=data.size();
//            if (size==0){
//                dictcallback.showMsg(context.getString(R.string.dict_init_error));
//            }else {
//                DictDao mDictDao = CoreApplication.getInstance().getDaoSession().getDictDao();
//                mDictDao.insertOrReplaceInTx(data);
//                String msg="导入完毕:总共导入"+size+"条";
//                dictcallback.showMsg(msg);
//                dictcallback.parseDataBack(data);
//
//
//            }
//
//        }
//    }


//    public static  void initAssets(final Context context,final  parseDictcallback  dictcallback) {
//        Observable.create(new ObservableOnSubscribe< ArrayList<Dict>   >() {
//            @Override
//            public void subscribe(ObservableEmitter< ArrayList<Dict>  > emitter)
//                    throws Exception {
//                ArrayList<Dict>  dictNewList=new ArrayList<>();
//                String filecidanName="cidian_yp-2018620.txt";
//
//                try {
//                    InputStream inputStream = context.getAssets().open(filecidanName);
//
//                    String str = VideoUtils.readOrgText(inputStream);
//
//                    String[] arr = str.split(",");
//
//                    String lastIsContainChinese="1";
////			  String tempName="";
//                    Dict itembean=null;
//                    for (int i=0;i<arr.length;i++){
//                        String item=arr[i];
//                        boolean isContainChinese=	VideoUtils.isContainChinese(item);
//                        if (isContainChinese){
//                            if (lastIsContainChinese=="1"){
//                                itembean=new Dict();
//                                itembean.setType(1);
//                                itembean.setId(UUID.randomUUID().toString());
//                                itembean.setTranName(item);
//
//                            }else{
//                                itembean.setName(item);
//                                dictNewList.add(itembean);
//                            }
//                            lastIsContainChinese="1";
//                        }else{
//
//                            if (itembean.getName()==null){
//                                String  YyPy= itembean.getTranPy();
//                                if (null==YyPy){
//                                    YyPy="";
//                                }else{
//                                    YyPy=YyPy+" ";
//                                }
//                                itembean.setTranPy(YyPy+item);
//                            }else{
//                                String  PtPy= itembean.getPy();
//                                if (null==PtPy){
//                                    PtPy="";
//                                }else{
//                                    PtPy=PtPy+" ";
//                                }
//
//                                itembean.setPy(PtPy+item);
//                                //tempName="";//重新添加新的数据
//                            }
//                            lastIsContainChinese="0";
//                        }
//                    }
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//                emitter.onNext(dictNewList);
//
//            } })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io()).subscribe(new Observer< ArrayList<Dict>  >() {
//            @Override public void onSubscribe(Disposable d) {
////				mDisposable=d;
//            }
//            @Override public void onNext( ArrayList<Dict>  list) {
//                dictcallback.parseDataBack(list);
////                iVideoHomeView.showToast(iVideoHomeView.getContext().getString(R.string.dict_init_sucess));
////                isLoadDictSucess=true;
////                mDictDao.insertOrReplaceInTx(list);
////                initData();
////				searchByGY("婴儿");
//            }
//            @Override public void onError(Throwable e) {
//            }
//
//            @Override public void onComplete() {
//
//            }
//        });
//    }
}
