package com.ui.setting.about;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.BusinessBroadcastUtils;
import com.business.bean.ResponseMsgData;
import com.business.login.User;
import com.core.CoreApplication;
import com.core.ServerUrl;
import com.core.db.greenDao.entity.SentenceYy;
import com.core.db.greenDao.gen.SentenceYyDao;
import com.core.utils.SpUtils;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.DyItemBean;
import com.easy.recycleview.custom.bean.TitleSettings;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.easysoft.utils.lib.http.ResponseMsg;
import com.easysoft.utils.lib.system.StringUtils;
import com.linlsyf.area.R;
import com.ui.HttpService;
import com.ui.dict.DictBeanUtils;
import com.ui.setting.InfoCardBean;
import com.utils.WebUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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


//		  DyItemBean spliteBean=new DyItemBean();
//		  spliteBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
//		  dataMaps.add(spliteBean);

		  DyItemBean  linkHomeBean=new DyItemBean();
		  linkHomeBean.setTitle(iSafeSettingView.getContext().getResources().getString(R.string.app_home_link));
//		itemBean.setViewType(5);
		  linkHomeBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  String url="http://kaifangcidian.com/han/yue";
				  WebUtils.openUrl(iSafeSettingView.getContext(),url);

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
				  String url="https://github.com/linlsyf/AreaAndroid/releases/download/1.0.0/cantonese.apk";
				  iSafeSettingView.openUrl(url);
//				iSafeSettingView.showUpdate();
			  }
		  });
		  dataMaps.add(updateBean);



		  DyItemBean itemBean=new DyItemBean();

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
