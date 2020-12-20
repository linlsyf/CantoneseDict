package com.ui.other.tuling.news.newdetail;

import com.easy.recycleview.bean.Section;
import com.ui.HttpService;
import com.ui.other.tuling.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;


public class NewsDetailPresenter {
	HttpService service;

	INewsDetailsView iNewsView;
	public static  String KEY_SETTING="setting";
	private List<NewsEntity> newsList=new ArrayList<>();

	private Section settingSection;


	public NewsDetailPresenter(INewsDetailsView iSafeSettingView) {
    	this.iNewsView =iSafeSettingView;
		service=new HttpService();
	}

      public void init(){
//		  List<DyItemBean> settingMaps=new ArrayList<>();
//		  settingSection=new Section(KEY_SETTING);
//		  settingSection.setShowSection(false);
//		  DyItemBean itemBean=new DyItemBean();
//		  itemBean.setViewType(3);
//		  settingMaps.add(itemBean);
//		  iNewsView.initUI(settingSection);

      }



}
