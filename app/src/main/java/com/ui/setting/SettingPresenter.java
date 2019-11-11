package com.ui.setting;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.BusinessBroadcastUtils;
import com.business.bean.ResponseMsgData;
import com.business.login.User;
import com.business.weixin.WeixinShare;
import com.core.CoreApplication;
import com.core.ServerUrl;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.entity.Dict;
import com.core.db.greenDao.entity.SentenceYy;
import com.core.db.greenDao.gen.SentenceYyDao;
import com.core.utils.SpUtils;
import com.easy.recycleview.bean.AddressHeadImgeSettings;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.easysoft.utils.lib.http.ResponseMsg;
import com.easysoft.utils.lib.system.AppInfo;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ToastUtils;
import com.linlsyf.area.R;
import com.ui.HttpService;
import com.ui.dict.DictBeanUtils;
import com.utils.PermissionCheckUtils;
import com.utils.ThemeHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class SettingPresenter   {
	HttpService service;
	ISafeSettingView iSafeSettingView;
	private String KEY_SETTING="setting";

	 InfoCardBean infoCardBean;

	 DyItemBean exportBean;
	SentenceYyDao sentenceYyDao;
	 DyItemBean musicBean;


	public SettingPresenter(ISafeSettingView iSafeSettingView) {
    	this.iSafeSettingView=iSafeSettingView;
		service=new HttpService();
	}

      public void init(){
		  List<IDyItemBean> dataMaps=new ArrayList<>();
		  List<IDyItemBean>  newSectionList=new ArrayList<>();
//		    infoCardBean=new InfoCardBean();
//		    infoCardBean.setId(KEY_USER_INFO);
//		  String loginName="用户";
//		   if (BusinessBroadcastUtils.loginUser!=null){
//			   loginName=BusinessBroadcastUtils.loginUser.getName();
//			   if (BusinessBroadcastUtils.loginUser.getIsAdmin().equals("1")){
//				   loginName=loginName+" (管理)";
//			   }
//		   }
//		    infoCardBean.setUserName(loginName);
//		    infoCardBean.setViewType(3);
//		  if (GlobalConstants.getInstance().getAppType()==GlobalConstants.TYPE_SHOP_APP){
//			  dataMaps.add(infoCardBean);
//
//		  }

		  int headRadius=(int)iSafeSettingView.getContext().getResources().getDimension(R.dimen.comon_setting_headimg_radius);

		    DyItemBean aboutBean=new DyItemBean();

		 String abcout="关于("+ AppInfo.getAppVersion(CoreApplication.getAppContext())+")";
		  aboutBean.setTitle(abcout);

		  aboutBean.setHeadImgeSettings(new AddressHeadImgeSettings().setHeadImgDrawableId(R.drawable.setting_about).setHeadImgRadius(headRadius));
		  aboutBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
			  	iSafeSettingView.clickAbout();
//				  Logout();
			  }
		  });
		  newSectionList.add(aboutBean);

		    final DyItemBean settingBean=new DyItemBean();
		  settingBean.setTitle(iSafeSettingView.getContext().getString(R.string.datasource_exec));
		  settingBean.setHeadImgeSettings(new AddressHeadImgeSettings().setHeadImgDrawableId(R.drawable.setting_db).setHeadImgRadius(headRadius));

		  settingBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  List<DyItemBean> dataListCustom = DataBaseUtils.getDataDyItemBeans(iSafeSettingView,SettingPresenter.this);
				  iSafeSettingView.openCustomView(dataListCustom);
			  }
		  });
		  newSectionList.add(settingBean);
		   sentenceYyDao = GlobalConstants.getInstance().getDaoSession().getSentenceYyDao();

    	  Section settingSection=new Section(KEY_SETTING);

    	  iSafeSettingView.initUI(settingSection);
    	  initJpush();

		  Section  newSection=new Section("");


		  DyItemBean newItemBean=new DyItemBean();
		  newItemBean.setTitle("助手小Q");
		  newItemBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  iSafeSettingView.showNews();
			  }
		  });

		  DyItemBean exitBean=new DyItemBean();
		  exitBean.setTitle("退出登录");
		  exitBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  Logout();
			  }
		  });

		  DyItemBean  shareBean=new DyItemBean();
		  shareBean.setTitle(iSafeSettingView.getContext().getString(R.string.share_lange_app));
		  shareBean.setHeadImgeSettings(new AddressHeadImgeSettings().setHeadImgDrawableId(R.drawable.setting_share).setHeadImgRadius(headRadius));

		  shareBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  WeixinShare.init(iSafeSettingView.getContext());
				  WeixinShare.test("推荐一个粤语学习APP给你，下载地址：https://www.coolapk.com/apk/224772");
			  }
		  });
		  newSectionList.add(shareBean);

		  DyItemBean  themeBean=new DyItemBean();
		  themeBean.setTitle(iSafeSettingView.getContext().getString(R.string.change_theme));
		  themeBean.setHeadImgeSettings(new AddressHeadImgeSettings().setHeadImgDrawableId(R.drawable.setting_theme).setHeadImgRadius(headRadius));

		  themeBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  List<DyItemBean> dataList=new ArrayList<>();

				  DyItemBean   itemBeanEdit=new DyItemBean();
				  itemBeanEdit.setId(R.style.theme_dark+"");
				  itemBeanEdit.setTitle("漆黑意志");
				  dataList.add(itemBeanEdit);

				  DyItemBean   itemBeanWhite=new DyItemBean();
				  itemBeanWhite.setId(R.style.theme_light+"");
				  itemBeanWhite.setTitle("白色相薄");
				  dataList.add(itemBeanWhite);

				  DyItemBean   itemBeanEye=new DyItemBean();
				  itemBeanEye.setId(R.style.theme_green+"");
				  itemBeanEye.setTitle("绿野仙踪");
				  dataList.add(itemBeanEye);
				  DyItemBean   itemBeanEasy=new DyItemBean();
				  itemBeanEasy.setId(R.style.theme_green+"");
				  itemBeanEasy.setTitle("系统推荐");
				  dataList.add(itemBeanEasy);

				  iSafeSettingView.selectTheme(dataList);

			  }
		  });
		  newSectionList.add(themeBean);

		  DyItemBean  developBean=new DyItemBean();
		  developBean.setTitle(iSafeSettingView.getContext().getString(R.string.develop_setting));
		  developBean.setHeadImgeSettings(new AddressHeadImgeSettings().setHeadImgDrawableId(R.drawable.setting_deve).setHeadImgRadius(headRadius));

		  developBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {

//				  boolean enableAdb = (Settings.Secure.getInt(iSafeSettingView.getContext().getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);//判断adb调试模式是否打开
//				  if (enableAdb) {
//					  iSafeSettingView.showToast("adb调试模式已经打开");
//				  } else {
					  PermissionCheckUtils.startDevelopmentActivity(iSafeSettingView.getContext());//跳转到开发者选项界面
//				  }

			  }
		  });


		  newSectionList.add(developBean);

		  DyItemBean  userPrivateBean=new DyItemBean();
		  userPrivateBean.setHeadImgeSettings(new AddressHeadImgeSettings().setHeadImgDrawableId(R.drawable.setting_about).setHeadImgRadius(headRadius));

//		  userPrivateBean.setViewType(4);
		  userPrivateBean.setTitle(iSafeSettingView.getContext().getString(R.string.user_private));
		  userPrivateBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {

				  List<DyItemBean> dataListCustom=new ArrayList<>();


				  DyItemBean  privateBean=new DyItemBean();
				  privateBean.setViewType(4);
				  privateBean.setTitle(iSafeSettingView.getContext().getString(R.string.user_content));
				    dataListCustom.add(privateBean);

				  iSafeSettingView.openCustomView(dataListCustom);

//						   Intent intent = new Intent(iSafeSettingView.getContext(), TestActivity.class);
//						   iSafeSettingView.getContext().startActivity(intent);

			  }
		  });
		  newSectionList.add(userPrivateBean);

		  newSection.setDataMaps(newSectionList);
		  iSafeSettingView.initUI(newSection);
      }



//	private void updateSongItemUI(boolean isPlaying,SongBean songBean) {
//		musicBean.setHintShow(true);
//		if (isPlaying){
//			musicBean.setTitle(iSafeSettingView.getContext().getString(R.string.radom_yuyu_music));
//			musicBean.setHint(iSafeSettingView.getContext().getString(R.string.click_play_music));
//		}else {
//			String  musicTitle=songBean.getTitle();
//			if (musicTitle==null){
//				musicTitle=iSafeSettingView.getContext().getString(R.string.unknown);
//			}
//			AddressHeadImgeSettings  headImgeSettings=new AddressHeadImgeSettings();
//
//			headImgeSettings.setHeadImgRadius((int)iSafeSettingView.getContext().getResources().getDimension(R.dimen.comon_head_radius));
//			headImgeSettings.setHeadImgUrl(songBean.getPicture());
//			musicBean.setHeadImgeSettings(headImgeSettings);
//
//			musicBean.setTitle("正在播放:"+" (点击暂停)");
//			musicBean.setHint(musicTitle+" ("+songBean.getArtist()+")");
//
//		}
//		iSafeSettingView.updateUIItem(isPlaying,musicBean);
//
//	}


	public void getLoginUserMsg(){
		  if(StringUtils.isNotEmpty(BusinessBroadcastUtils.USER_VALUE_USER_ID)){{

			   String url = ServerUrl.baseUrl+ServerUrl.Get_UserUrl;
			  User loginUser=new User();
			  loginUser.setId(BusinessBroadcastUtils.USER_VALUE_USER_ID);
			  final String json= JSON.toJSONString(loginUser);
			  url=ServerUrl.getFinalUrl(url,json);

			  service.request( url , new EasyHttpCallback(new IEasyResponse() {
				  @Override
				  public void onFailure(CallBackResult serviceCallBack) {
				  }

				  @Override
				  public void onResponse(CallBackResult serviceCallBack) {
					  if (serviceCallBack.isSucess()){
						  ResponseMsg msg=   serviceCallBack.getResponseMsg();
						  ResponseMsgData serverUserResponseMsgData= JSONObject.parseObject(msg.getMsg(), ResponseMsgData.class);

						  if (StringUtils.isNotEmpty(serverUserResponseMsgData.getData().toString())){
							  User  serverUser=JSONObject.parseObject(serverUserResponseMsgData.getData().toString(), User.class);

							  infoCardBean.setId(serverUser.getId());
							  infoCardBean.setUserName(serverUser.getName());
							  iSafeSettingView.updateItem(infoCardBean);
						  }


					  }

//                ilogInView.showToast("登录成功");
				  }
			  }));
		  }}
	  }


    public void initJpush(){
    	JPushInterface.setAlias(CoreApplication.getAppContext(), 0, "ldh");
    	  JPushInterface.setAlias(CoreApplication.getAppContext(),"ldh", new TagAliasCallback() {
              @Override
              public void gotResult(int i, String s, Set<String> set) {
//            	  ToastUtils.show(CoreApplication.getAppContext(), "设置成功");
//                  tvAlias.setText("当前alias："+alias);
              }


          });
    }
    public void Logout() {
		BusinessBroadcastUtils.USER_VALUE_LOGIN_ID = "";
		BusinessBroadcastUtils.USER_VALUE_PWD 	   = "";
		BusinessBroadcastUtils.USER_VALUE_USER_ID  ="";
		SpUtils.clear(iSafeSettingView.getContext(), BusinessBroadcastUtils.STRING_LOGIN_USER_ID);
		SpUtils.clear(iSafeSettingView.getContext(), BusinessBroadcastUtils.STRING_LOGIN_USER_PWD);
		SpUtils.clear(iSafeSettingView.getContext(), BusinessBroadcastUtils.STRING_LOGIN_ID);
		iSafeSettingView.logOut();
    }

	public void updateUserInfo() {
		infoCardBean.setId(BusinessBroadcastUtils.loginUser.getId());
		String 	name=BusinessBroadcastUtils.loginUser.getName();
		if (BusinessBroadcastUtils.loginUser.getIsAdmin().equals("1")){
			name=name+" (管理)";
		}
		infoCardBean.setUserName(name);
		iSafeSettingView.updateItem(infoCardBean);
	}

	public void updateSelectTheme(List<IDyItemBean> itemBeans) {


		IDyItemBean  itemBean=itemBeans.get(0);

		int type =Integer.parseInt(itemBean.getId());

		ThemeHelper.changeTheme(iSafeSettingView.getContext(),type);

		BusinessBroadcastUtils.sendBroadcast(iSafeSettingView.getContext(),BusinessBroadcastUtils.TYPE_CHANGE_THEME_RESTART_ACTIVITY,type);
		init();

	}

	public void changeTemeWB() {
		int  type=ThemeHelper.getStoreTheme(iSafeSettingView.getContext());
		List<IDyItemBean> itemBeans=new ArrayList<>();
		DyItemBean itemBean=new DyItemBean();
		  if (type== R.style.theme_light){
			  itemBean.setId(R.style.theme_dark+"");

		  }else{
			  itemBean.setId(R.style.theme_light+"");

		  }
		itemBeans.add(itemBean);
		updateSelectTheme(itemBeans);
	}
}
