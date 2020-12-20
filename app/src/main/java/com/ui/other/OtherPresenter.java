package com.ui.other;

import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.ui.HttpService;

import java.util.ArrayList;
import java.util.List;

//import com.mob.mobapi.API;
//import com.mob.mobapi.APICallback;
//import com.mob.mobapi.MobAPI;
//import com.mob.mobapi.apis.Weather;

public class OtherPresenter   {
	HttpService service;

	IOtherView iOtherView;
	private String SECTION_WEATHER="weather";
	private String SECTION_APAY="apay";
	private String SECTION_NEW="new";

	public OtherPresenter(IOtherView IOtherView) {
    	this.iOtherView=IOtherView;
		service=new HttpService();

	}

      public void init(){
//		  Weather api = (Weather) MobAPI.getAPI(Weather.NAME);
//		  api.queryByCityName("广州", new APICallback() {
//			  @Override
//			  public void onSuccess(API api, int i, Map<String, Object> map) {
//				ArrayList<Map> reusltList=   (ArrayList<Map> ) map.get("result");
//				  List<WeatherMsg> futureWeatherList=new ArrayList<WeatherMsg>();
//				     for (Map itemMap:reusltList){
//						 if (itemMap.containsKey("future")){
//							 futureWeatherList=JSON.parseArray(JSON.toJSONString(itemMap.get("future")),WeatherMsg.class);
//
//							 break;
//						 }
//
//					 }
//				  Section  weatherSection=new Section(SECTION_WEATHER);
//				  weatherSection.setPosition(0);
//				  weatherSection.setName("天气");
//				  List<DyItemBean>  weartherSectionList=new ArrayList<DyItemBean>();
//				  for (WeatherMsg itemBean:futureWeatherList) {
//					  if (weartherSectionList.size()<=1){
//						  //itemBean.setViewType(IItemView.ViewTypeEnum.INFO_CARD_VIEW.value());
//						  itemBean.setTitle(itemBean.getWeek());
//						  itemBean.setLeftSecondText(itemBean.getTemperature());
//						  itemBean.setRightFirstText(itemBean.getDayTime()+" "+itemBean.getWind());
//						  weartherSectionList.add(itemBean);
//					  }else{
//						  break;
//					  }
//
//				  }
//				  weatherSection.setDataMaps(weartherSectionList);
//				  iOtherView.updateSection(weatherSection);
//			  }
//			  @Override
//			  public void onError(API api, int i, Throwable throwable) {
//				  iOtherView.showToast("请求服务器失败");
//			  }
//		  });
//		  Section  apaySection=new Section(SECTION_APAY);
//		  apaySection.setPosition(1);
//		  apaySection.setName("支付");
//		  List<DyItemBean>  paySectionList=new ArrayList<DyItemBean>();
//		  DyItemBean itemBean=new DyItemBean();
//		  itemBean.setTitle("测试支付功能");
//		  itemBean.setOnItemListener(new IItemView.onItemClick() {
//			  @Override
//			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, DyItemBean bean) {
//				   String url = ServerUrl.baseUrl+ServerUrl.Order_GETSIGN;
//				  ShopOrder loginUser=new ShopOrder();
//				  final String json= JSON.toJSONString(loginUser);
//				  url=ServerUrl.getFinalUrl(url,json);
//				  service.request( url , new MyCallback(new MyCallback.IResponse() {
//					  @Override
//					  public void onFailure(ServiceCallBack serviceCallBack) {
//						  iOtherView.showToast("服务器获取数据失败");
//					  }
//					  @Override
//					  public void onResponse(ServiceCallBack serviceCallBack) {
//						  if (serviceCallBack.isSucess()){
//
//							  ResponseMsgData data =JSON.parseObject(serviceCallBack.getResponseMsg().getMsg(),
//									  ResponseMsgData.class);
//
//							  SignUtils.payString=data.getData().toString();
//							  iOtherView.showApay();
//						  }else{
//							  iOtherView.showToast("服务器获取数据失败");
//						  }
//
//					  }
//				  }));
//
//			  }
//		  });
//		  paySectionList.add(itemBean);
//
//		  apaySection.setDataMaps(paySectionList);

		  Section newSection=new Section(SECTION_NEW);
		  newSection.setPosition(0);

		  newSection.setName("圈子");
		  List<IDyItemBean>  newSectionList=new ArrayList<>();

		  newSection.setDataMaps(newSectionList);
		  iOtherView.updateSection(newSection);
    }
}
