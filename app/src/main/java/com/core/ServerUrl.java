package com.core;

public class ServerUrl {

//	public static String host="http://18.222.51.213";
//    public static int port=8080;

	public static String ip="18.222.51.213";
	public static String host="http://"+ip;
	public static int port=8080;
	public static String baseUrl="https://www.linlsyf.cn";
//	public static String baseUrl=host+":"+port;
//	public static String baseUrl=host+":"+port+"/easy";

	public static String APP_LIST="/api/v1/applist/search";





	public static String UPLOAD_URL="/api/v1/file/upload";
	//图片
	public static final String IMG_URL="/api/v1/file/down?type=2&name=";
     //订单相关
	 public static String addOrder="/api/v1/order/add";
	 public static String Order_GETSIGN="/api/v1/order/getSign";

	public static String ORDER_LIST="/api/v1/order/list";
	public static String LIST_ORDERED ="/api/v1/order/listOrdered";
	public static String LIST_MY_ORDERED ="/api/v1/order/getMyOrder";
	public static String ORDER_GET="/api/v1/order/get";
	public static String ORDER_REMOVE="/api/v1/order/remove";
	//商品
	public static String GOODSLIST="/api/v1/goods/list";
	public static String GOODS_ADD="/api/v1/goods/add";

	//商品搜索
	public static String GOODS_SEARCH="/api/v1/goods/search";

	//更新apk
	public static String updateUrl="/api/v1/file/downApk";
	public static String updateCheck="/api/v1/applist/searchAppInfo";
	//用户
	public static String loginUrl="/api/v1/user/login";
	public static String Get_UserUrl="/api/v1/user/get";
	public static String ADD_UserUrl="/api/v1/user/add";
	public static String REGISTER_UserUrl="/api/v1/user/register";
	public static String checkUserExitUrl="/api/v1/user/checkUserExit";
    //反馈意见
	public static String commentListUrl="/api/v1/comment/list";
	public static String commentAddUrl="/api/v1/comment/add";


	public static String list_server_iP="server_ip";
	public static String list_server_port="server_port";

	public static String GITHUB_UPDATE_URL="server_port";


	public static void resetBaseUrl(){
		 host="http://"+ip;
		 baseUrl=host+":"+port+"/easy";
	}

   public static  String  getFinalUrl(String url,String json){
	   url=url+"?msg="+json;
	   return url;
   }


}
