package com.ui.other.tuling.news;

import com.easy.recycleview.bean.Section;
import com.ui.HttpService;
import com.ui.other.tuling.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;


public class NewsPresenter {
	HttpService service;

	INewsView iNewsView;
	public static  String KEY_SETTING="setting";
	private List<NewsEntity> newsList=new ArrayList<>();

	private Section settingSection;


	public NewsPresenter(INewsView iSafeSettingView) {
    	this.iNewsView =iSafeSettingView;
		service=new HttpService();
	}

      public void init(){

//		  requestApiByRetrofit_RxJava("新闻");

    	
      }
//
//	// 请求图灵API接口，获得问答信息
//	private void requestApiByRetrofit_RxJava(final String info) {
//		service=new HttpService();
//		final String url = TulingParams.TULING_URL+"?key="+TulingParams.TULING_KEY+"&info="+info;
//
//		service.request( url , new EasyHttpCallback(new IEasyResponse() {
//			@Override
//			public void onFailure(CallBackResult serviceCallBack) {
//			}
//
//			@Override
//			public void onResponse(CallBackResult serviceCallBack) {
//				if (serviceCallBack.isSucess()){
//					ResponseMsgData data = JSON.parseObject(serviceCallBack.getResponseMsg().getData().toString(),
//							ResponseMsgData.class);
//					try{
//						newsList =  JSON.parseArray(data.getList().toString(), NewsEntity.class) ;
//					}catch (Exception e){
//						iNewsView.showToast(iNewsView.getContext().getString(R.string.response_error));
//						return;
//					}
//					List<IDyItemBean> settingMaps=new ArrayList<>();
//					settingSection=new Section(KEY_SETTING);
//					settingSection.setShowSection(false);
//
//					int i=0;
//					for (final NewsEntity item : newsList ) {
//
//						item.setTitle(item.getArticle());
//						item.setOnItemListener(new IItemView.onItemClick() {
//							@Override
//							public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
//								iNewsView.showItem(item);
//							}
//						});
//
//						if (i!=0){
//							DyItemBean spliteItem=new DyItemBean();
//							spliteItem.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
//							settingMaps.add(spliteItem);
//						}
//
//						settingMaps.add(item);
//						i++;
//
//					}
//					settingSection.setDataMaps(settingMaps);
//					iNewsView.initUI(settingSection);
//				}
//
//			}
//		}).setOutside(true));
//	}

}
