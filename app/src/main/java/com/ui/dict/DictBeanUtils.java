package com.ui.dict;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.core.base.GlobalConstants;
import com.core.base.IBaseView;
import com.core.db.greenDao.entity.Dict;
import com.core.db.greenDao.entity.SentenceYy;
import com.core.db.greenDao.gen.DaoMaster;
import com.core.db.greenDao.gen.DaoSession;
import com.core.db.greenDao.gen.DictDao;
import com.core.db.greenDao.gen.SentenceYyDao;
import com.easysoft.utils.lib.string.StringUtils;
import com.easysoft.utils.lib.system.ThreadPoolUtils;
import com.linlsyf.area.R;
import com.utils.FileUtils;
import com.utils.VideoUtils;

import org.greenrobot.greendao.database.Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
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
 * Created by Administrator on 2019/3/3 0003.
 */

public class DictBeanUtils {


    //数据库文件路径
    private static final String DB_PATH = "/data/data/com.linlsyf.area/databases/";
    //数据库文件名
    private static final String DB_NAME = "back.db";
    private static final String DB_EXPORT_NAME = "cantonese.db";
    private static String  dbBackPath= Environment.getExternalStorageDirectory() + "/" + DB_EXPORT_NAME;

//    private static String dbBackUpPath;

    public static void iniDbFile(final  IBaseView iBaseView, final parseDictcallback dictcallback) {
        DictBeanUtils.copyDbFile(iBaseView.getContext());
        DictBeanUtils.initDb(iBaseView.getContext(), dictcallback);

    }


        /**
         * 将assets文件夹下文件拷贝到/databases/下
         * @param context
         */
    public static void copyDbFile(Context context) {
        InputStream in = null;
        FileOutputStream out = null;
        String path = "/data/data/" + context.getPackageName() + "/databases/";
        File file = new File(path + DB_NAME);

        //创建文件夹
        File filePath = new File(path);
        if (!filePath.exists())
            filePath.mkdirs();

        if (file.exists())
            return;

        try {
            in = context.getAssets().open(DB_NAME); // 从assets目录下复制
            out = new FileOutputStream(file);
            int length = -1;
            byte[] buf = new byte[1024];
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    public static  void initDb(final Context context, final parseDictcallback dictcallback){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        Database db = helper.getReadableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        DictDao dao = daoSession.getDictDao();
        if (dao!=null){
            List<Dict> data=    dao.loadAll();
            int  size=data.size();
            if (size==0){
                dictcallback.showMsg(context.getString(R.string.dict_init_error));
            }else {
                DictDao mDictDao = GlobalConstants.getInstance().getDaoSession().getDictDao();
               mDictDao.insertOrReplaceInTx(data);


        SentenceYyDao sentenceYyDao= daoSession.getSentenceYyDao();
          if (sentenceYyDao!=null){
              List<SentenceYy> datasSentenceYy=    sentenceYyDao.loadAll();
              GlobalConstants.getInstance().getDaoSession().getSentenceYyDao().insertOrReplaceInTx(datasSentenceYy);
            }



                String msg="导入完毕:总共导入"+size+"条";
                dictcallback.showMsg(msg);
                dictcallback.parseDataBack(data);


            }

        }
    }
    public static  void initLJ(final Context context, final parseDictcallback dictcallback){

        Observable.create(new ObservableOnSubscribe< ArrayList<SentenceYy>   >() {
            @Override
            public void subscribe(ObservableEmitter< ArrayList<SentenceYy>  > emitter)
                    throws Exception {
                ArrayList<SentenceYy>  dictNewList=new ArrayList<>();
                String filecidanName= "liju_yue-jt-kfcd-2018620.txt";

                try {
                    InputStream inputStream = context.getAssets().open(filecidanName);

                    String result = "";//如果path是传递过来的参数，可以做一个非目录的判断
                    try {
                        if (inputStream != null)
                        {
                            BufferedReader buffreader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                            String line;
                            //分行读取
                            while (( line = buffreader.readLine()) != null) {
                                if (StringUtils.isNotEmpty(line)){
                                    try{
                                        String [] arr = line.split("\\s+");
                                        SentenceYy dict=new SentenceYy();
                                        dict.setId(UUID.randomUUID()+"");
                                         dict.setName(arr[1]);
                                         dict.setTranName(arr[0]);

                                        dictNewList.add(dict);
//                                        result=result+line;
                                    }catch (Exception e){
                                        System.out.print(e.getMessage());
                                    }


                                }
//						newList.add(line+"\n");
                            }
                            inputStream.close();
                        }
                    }
                    catch (Exception e)
                    {
                        Log.d("TestFile", "The File doesn't not exist.");
                    }




                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                emitter.onNext(dictNewList);

            } })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer< ArrayList<SentenceYy>  >() {
            @Override public void onSubscribe(Disposable d) {
//				mDisposable=d;
            }
            @Override public void onNext( ArrayList<SentenceYy>  list) {
                dictcallback.parseDataBack(list);

            }
            @Override public void onError(Throwable e) {
            }

            @Override public void onComplete() {

            }
        });

    }
    public static  void getErrorMsg(final Context context, final parseDictcallback dictcallback){

        Observable.create(new ObservableOnSubscribe<String   >() {
            @Override
            public void subscribe(ObservableEmitter< String  > emitter)
                    throws Exception {
               // ArrayList<SentenceYy>  dictNewList=new ArrayList<>();
                String filecidanName= "liju_yue-jt-kfcd-2018620.txt";

                String result = "";


                File sd = Environment.getExternalStorageDirectory();
                String fullPath = sd.getPath() + "/MMMDebug/";
                File dir = new File(fullPath);
                File[] array = dir.listFiles();
                if (array.length>0){
                   // String  path=array[0].getAbsolutePath();

//               FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TbsReaderFragment(), bundle);
                }else{
                    dictcallback.showMsg(context.getString(R.string.no_data));
                    return;
                }

                 File   errorMsgFile=array[0];
                try {
                    InputStream inputStream =new FileInputStream(errorMsgFile);

                    StringBuffer sBuffer = new StringBuffer();
                    try {
                        if (inputStream != null)
                        {
                            BufferedReader buffreader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                            String line;
                            //分行读取
                            while (( line = buffreader.readLine()) != null) {
                                if (StringUtils.isNotEmpty(line)){
                                    sBuffer.append(line + "\n");//strLine就是一行的内容

                                }
//						newList.add(line+"\n");
                            }
                            inputStream.close();
                            result=sBuffer.toString();
                        }
                    }
                    catch (Exception e)
                    {
                        Log.d("TestFile", "The File doesn't not exist.");
                    }




                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                emitter.onNext(result);

            } })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer<String >() {
            @Override public void onSubscribe(Disposable d) {
//				mDisposable=d;
            }
            @Override public void onNext( String list) {
                dictcallback.parseDataBack(list);

            }
            @Override public void onError(Throwable e) {
            }

            @Override public void onComplete() {

            }
        });

    }
     public static void  emptyDb(){
         GlobalConstants.getInstance().getDaoSession().getDictDao().deleteAll();
     }
    public static  void exportDb(final Context context, final parseDictcallback dictcallback){

         String dbPath = "/data/data/com.linlsyf.cantonese/databases/"
                +"easy-db";

//          dbBackPath=Environment.getExternalStorageDirectory() + "/"
//                  + DB_EXPORT_NAME;

        boolean success= FileUtils.copyFile(dbPath, dbBackPath);
          String isSucess="0";
          if(success){
              isSucess="1";
          }
        dictcallback.parseDataBack(isSucess);

    }
    public static  void importFrombackUp(final Context context, final parseDictcallback dictcallback){


        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                String path = "/data/data/" + context.getPackageName() + "/databases/"+ DB_NAME;

                FileUtils.copyFile(dbBackPath,path);
                initDb(context,dictcallback);
            }
        });


//        dbBackUpPath


    }


     public static String getReadMeMsg(final Context context, final parseDictcallback dictcallback){
        String  msg="";


         Observable.create(new ObservableOnSubscribe< String   >() {
             @Override
             public void subscribe(ObservableEmitter<String> emitter)
                     throws Exception {

                 String readMsg="";
                 String filecidanName= "readme.txt";

                 try {
                     InputStream inputStream = context.getAssets().open(filecidanName);

                     String result = "";//如果path是传递过来的参数，可以做一个非目录的判断
                     try {
                         if (inputStream != null)
                         {
                             BufferedReader buffreader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                             String line;
                             //分行读取
                             while (( line = buffreader.readLine()) != null) {
                                 if (StringUtils.isNotEmpty(line)){
                                     try{
                                         readMsg=readMsg+"\r\n"+line;
                                     }catch (Exception e){
                                         System.out.print(e.getMessage());
                                     }


                                 }
//						newList.add(line+"\n");
                             }
                             inputStream.close();
                         }
                     }
                     catch (Exception e)
                     {
                         Log.d("TestFile", "The File doesn't not exist.");
                     }




                 } catch (Exception e1) {
                     e1.printStackTrace();
                 }
                 emitter.onNext(readMsg);

             } })
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeOn(Schedulers.io()).subscribe(new Observer<String   >() {
             @Override public void onSubscribe(Disposable d) {
//				mDisposable=d;
             }
             @Override public void onNext(String msg) {
                 dictcallback.parseDataBack(msg);

             }
             @Override public void onError(Throwable e) {
             }

             @Override public void onComplete() {

             }
         });



         return msg;
     }



        public static  void importDbSentence(final Context context, final parseDictcallback dictcallback){

        Observable.create(new ObservableOnSubscribe< ArrayList<SentenceYy>   >() {
            @Override
            public void subscribe(ObservableEmitter< ArrayList<SentenceYy>  > emitter)
                    throws Exception {
                ArrayList<SentenceYy>  dictNewList=new ArrayList<>();
                String filecidanName= "liju_yue-jt-kfcd-2018620.txt";

                try {
                    InputStream inputStream = context.getAssets().open(filecidanName);

                    String result = "";//如果path是传递过来的参数，可以做一个非目录的判断
                    try {
                        if (inputStream != null)
                        {
                            BufferedReader buffreader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                            String line;
                            //分行读取
                            while (( line = buffreader.readLine()) != null) {
                                if (StringUtils.isNotEmpty(line)){
                                    try{
                                        String [] arr = line.split("\\s+");
                                        SentenceYy dict=new SentenceYy();
                                        dict.setId(UUID.randomUUID()+"");
                                         dict.setName(arr[1]);
                                         dict.setTranName(arr[0]);

                                        dictNewList.add(dict);
//                                        result=result+line;
                                    }catch (Exception e){
                                        System.out.print(e.getMessage());
                                    }


                                }
//						newList.add(line+"\n");
                            }
                            inputStream.close();
                        }
                    }
                    catch (Exception e)
                    {
                        Log.d("TestFile", "The File doesn't not exist.");
                    }




                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                emitter.onNext(dictNewList);

            } })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer< ArrayList<SentenceYy>  >() {
            @Override public void onSubscribe(Disposable d) {
//				mDisposable=d;
            }
            @Override public void onNext( ArrayList<SentenceYy>  list) {
                dictcallback.parseDataBack(list);

            }
            @Override public void onError(Throwable e) {
            }

            @Override public void onComplete() {

            }
        });



    }

    public static  void testmagnet(final Context context, final parseDictcallback dictcallback){

        Observable.create(new ObservableOnSubscribe< ArrayList<SentenceYy>   >() {
            @Override
            public void subscribe(ObservableEmitter< ArrayList<SentenceYy>  > emitter)
                    throws Exception {

                ArrayList<SentenceYy>  dictNewList=new ArrayList<>();

                emitter.onNext(dictNewList);

            } })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer< ArrayList<SentenceYy>  >() {
            @Override public void onSubscribe(Disposable d) {
//				mDisposable=d;
            }
            @Override public void onNext( ArrayList<SentenceYy>  list) {
                dictcallback.parseDataBack(list);

            }
            @Override public void onError(Throwable e) {
            }

            @Override public void onComplete() {

            }
        });



    }
    public  interface parseDictcallback extends Serializable {

        void   parseDataBack(Object list);
        void    showMsg(String msg);

    }
    public static  void initAssets(final Context context,final parseDictcallback dictcallback) {
        Observable.create(new ObservableOnSubscribe< ArrayList<Dict>   >() {
            @Override
            public void subscribe(ObservableEmitter< ArrayList<Dict>  > emitter)
                    throws Exception {
                ArrayList<Dict>  dictNewList=new ArrayList<>();
                String filecidanName="cidian_yp-2018620.txt";

                try {
                    InputStream inputStream = context.getAssets().open(filecidanName);

                    String str = VideoUtils.readOrgText(inputStream);

                    String[] arr = str.split(",");

                    String lastIsContainChinese="1";
//			  String tempName="";
                    Dict itembean=null;
                    for (int i=0;i<arr.length;i++){
                        String item=arr[i];
                        boolean isContainChinese=	VideoUtils.isContainChinese(item);
                        if (isContainChinese){
                            if (lastIsContainChinese=="1"){
                                itembean=new Dict();
                                itembean.setType(1);
                                itembean.setId(UUID.randomUUID().toString());
                                itembean.setTranName(item);

                            }else{
                                itembean.setName(item);
                                dictNewList.add(itembean);
                            }
                            lastIsContainChinese="1";
                        }else{

                            if (itembean.getName()==null){
                                String  YyPy= itembean.getTranPy();
                                if (null==YyPy){
                                    YyPy="";
                                }else{
                                    YyPy=YyPy+" ";
                                }
                                itembean.setTranPy(YyPy+item);
                            }else{
                                String  PtPy= itembean.getPy();
                                if (null==PtPy){
                                    PtPy="";
                                }else{
                                    PtPy=PtPy+" ";
                                }

                                itembean.setPy(PtPy+item);
                                //tempName="";//重新添加新的数据
                            }
                            lastIsContainChinese="0";
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                emitter.onNext(dictNewList);

            } })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Observer< ArrayList<Dict>  >() {
            @Override public void onSubscribe(Disposable d) {
//				mDisposable=d;
            }
            @Override public void onNext( ArrayList<Dict>  list) {
                dictcallback.parseDataBack(list);
//                idictHomeView.showToast(idictHomeView.getContext().getString(R.string.dict_init_sucess));
//                isLoadDictSucess=true;
//                mDictDao.insertOrReplaceInTx(list);
//                initData();
//				searchByGY("婴儿");
            }
            @Override public void onError(Throwable e) {
            }

            @Override public void onComplete() {

            }
        });
    }
}
