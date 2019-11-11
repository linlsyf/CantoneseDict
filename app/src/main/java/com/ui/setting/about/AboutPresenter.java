package com.ui.setting.about;

import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.bean.TitleSettings;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.linlsyf.area.R;
import com.ui.HttpService;

import java.util.ArrayList;
import java.util.List;


public class AboutPresenter {
	HttpService service;

	IAboutView iSafeSettingView;
	private String KEY_SETTING="setting";


	public AboutPresenter(IAboutView iSafeSettingView) {
    	this.iSafeSettingView=iSafeSettingView;
		service=new HttpService();
	}

      public void init(){
		  List<IDyItemBean> dataMaps=new ArrayList<>();


		  DyItemBean linkHomeBean=new DyItemBean();
		  linkHomeBean.setTitle(iSafeSettingView.getContext().getResources().getString(R.string.app_home_link));
//		itemBean.setViewType(5);
		  linkHomeBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  String url="http://kaifangcidian.com/han/yue";
				  iSafeSettingView.openUrl(url);
//				  WebUtils.openUrl(iSafeSettingView.getContext(),url);

			  }
		  });

		  dataMaps.add(linkHomeBean);

		  DyItemBean updateBean=new DyItemBean();
		  //updateBean.setId(KEY_UPDATE);
		  String  verson="检查更新（链接）";
//		     verson=verson+"("+ AppInfo.getAppVersion(CoreApplication.getAppContext())+")";
//		     verson=verson+"("+ AppInfo.getAppVersion(CoreApplication.getAppContext())+")";
		  updateBean.setTitle(verson);
		  updateBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  String url="https://www.coolapk.com/apk/224772";
				  iSafeSettingView.openUrlByBroswer(url);
//				iSafeSettingView.showUpdate();
			  }
		  });
		  dataMaps.add(updateBean);



		  DyItemBean itemBean=new DyItemBean();
		  itemBean.setViewType(4);
		  TitleSettings titleSettings=new TitleSettings();
		  titleSettings.setTextSize(23);
		  itemBean.setTitleSettings(titleSettings);
		  itemBean.setTitle(iSafeSettingView.getContext().getString(R.string.about));

		  dataMaps.add(itemBean);
		  Section settingSection=new Section(KEY_SETTING);
		  settingSection.setDataMaps(dataMaps);
		  iSafeSettingView.initUI(settingSection);
      }

}
