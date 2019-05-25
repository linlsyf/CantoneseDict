package com.business.baidu.translate;

public class TranSalteMain {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20190507000294875";
    private static final String SECURITY_KEY = "p081XtTH_hK047KG6SU1";

    public static String toTran(String query) {


       TransApi api = new TransApi(APP_ID, SECURITY_KEY);

//        String query = "高度600米";
        return api.getTransResult(query, "auto", "en");
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
