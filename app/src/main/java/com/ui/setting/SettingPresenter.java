package com.ui.setting;

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
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.DyItemBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easy.recycleview.outinter.RecycleConfig;
import com.easy.recycleview.outinter.ThemeConfig;
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.easysoft.utils.lib.http.ResponseMsg;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.config.WidgetConfig;
import com.linlsyf.area.R;
import com.ui.HttpService;
import com.ui.dict.DictBeanUtils;
import com.utils.ThemeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class SettingPresenter   {
	HttpService service;
	ISafeSettingView iSafeSettingView;
	private String KEY_SETTING="setting";
	private String KEY_INFO="info";
	private String KEY_LOGOUT="logout";
	private String KEY_UPDATE="update";
	private String KEY_USER_INFO="userInfo";
	private InfoCardBean infoCardBean;
	private String SECTION_NEW="new";
	private String KEY_ABOUT="about";
	private String KEY_TEST="test";
	private DyItemBean exportBean;
	private DyItemBean importBean;
	SentenceYyDao sentenceYyDao;

//	 int theme


	public SettingPresenter(ISafeSettingView iSafeSettingView) {
    	this.iSafeSettingView=iSafeSettingView;
		service=new HttpService();
	}

      public void init(){
		  List<IDyItemBean> dataMaps=new ArrayList<>();
		  List<IDyItemBean> settingMaps=new ArrayList<>();
		    infoCardBean=new InfoCardBean();
		    infoCardBean.setId(KEY_USER_INFO);
		  String loginName="用户";
		   if (BusinessBroadcastUtils.loginUser!=null){
			   loginName=BusinessBroadcastUtils.loginUser.getName();
			   if (BusinessBroadcastUtils.loginUser.getIsAdmin().equals("1")){
				   loginName=loginName+" (管理)";
			   }
		   }
		    infoCardBean.setUserName(loginName);
		    infoCardBean.setViewType(3);
		  if (GlobalConstants.getInstance().getAppType()==GlobalConstants.TYPE_SHOP_APP){
			  dataMaps.add(infoCardBean);

		  }


		    DyItemBean updateBean=new DyItemBean();
		    updateBean.setId(KEY_UPDATE);
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
		  settingMaps.add(updateBean);
//
//			DyItemBean itembeanSpace = new DyItemBean();
//			itembeanSpace.setViewType(IItemView.ViewTypeEnum.SPLITE
//							.value());
//			settingMaps.add(itembeanSpace);
			
		    DyItemBean aboutBean=new DyItemBean();
		  aboutBean.setId(KEY_ABOUT);
		  aboutBean.setTitle("关于");
		  aboutBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
			  	iSafeSettingView.clickAbout();
//				  Logout();
			  }
		  });
		    settingMaps.add(aboutBean);

		  DyItemBean spliteBean=new DyItemBean();
		  spliteBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
		  settingMaps.add(spliteBean);

		  DyItemBean  itemBean=new DyItemBean();
		  itemBean.setTitle(iSafeSettingView.getContext().getResources().getString(R.string.app_home_link));
//		itemBean.setViewType(5);
		  itemBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  String url="http://kaifangcidian.com/han/yue";
				  iSafeSettingView.openUrl(url);

			  }
		  });



		  settingMaps.add(itemBean);

		   exportBean=new DyItemBean();
		  exportBean.setId(KEY_ABOUT);
		  exportBean.setTitle("备份字典数据库到SD卡");
		  exportBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
			  	exportDict();
			  }
		  });
		  settingMaps.add(exportBean);

		   sentenceYyDao = CoreApplication.getInstance().getDaoSession().getSentenceYyDao();

		   importBean =new DyItemBean();
		  importBean.setId(KEY_ABOUT);
//		  if (dataCount>0){
//			  importBean.setTitle(iSafeSettingView.getContext().getString(R.string.dict_init_sucess));
//
//		  }else{
			  importBean.setTitle("覆盖当前进度（默认数据在SD上）");
			  importBean.setOnItemListener(new IItemView.onItemClick() {
				  @Override
				  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {

					  inportDictLJ();
				  }
			  });
//		  }

		  settingMaps.add(importBean);

		  Section nextSection=new Section(KEY_INFO);
		
    	  nextSection.setDataMaps(dataMaps);
    	  Section settingSection=new Section(KEY_SETTING);
    	  settingSection.setDataMaps(settingMaps);

    	  iSafeSettingView.initUI(nextSection);
    	  iSafeSettingView.initUI(settingSection);
    	  initJpush();

		  Section  newSection=new Section(SECTION_NEW);
		  newSection.setPosition(0);

//		  newSection.setName("其他");
		  List<IDyItemBean>  newSectionList=new ArrayList<>();

		  DyItemBean newItemBean=new DyItemBean();
		  newItemBean.setTitle("助手小Q");
		  newItemBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  iSafeSettingView.showNews();
			  }
		  });
//		  newSectionList.add(newItemBean);

		  DyItemBean itembeanSpaceLogOut= new DyItemBean();
		  itembeanSpaceLogOut.setViewType(IItemView.ViewTypeEnum.SPLITE
				  .value());
		  newSectionList.add(itembeanSpaceLogOut);
		  DyItemBean exitBean=new DyItemBean();
		  exitBean.setId(KEY_LOGOUT);
		  exitBean.setTitle("退出登录");
		  exitBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  Logout();
			  }
		  });
		  if (GlobalConstants.getInstance().getAppType()==GlobalConstants.TYPE_SHOP_APP){
			  newSectionList.add(exitBean);
		  }
		  DyItemBean  ljBean=new DyItemBean();
		  ljBean.setTitle(iSafeSettingView.getContext().getString(R.string.init_sentence));
		  ljBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {

				  DictBeanUtils.importDbSentence(iSafeSettingView.getContext(), new DictBeanUtils.parseDictcallback() {
					  @Override
					  public void parseDataBack(Object list) {

						  List<SentenceYy> sentenceYys=(List<SentenceYy>)list;
						  sentenceYyDao.insertOrReplaceInTx(sentenceYys);
						  iSafeSettingView.showToast(iSafeSettingView.getContext().getString(R.string.exec_sucess));

					  }

					  @Override
					  public void showMsg(String msg) {

					  }
				  });
			  }
		  });


		  newSectionList.add(ljBean);


//		  DyItemBean split= new DyItemBean();
//		  split.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
//
//		  newSectionList.add(split);
		  DyItemBean  shareBean=new DyItemBean();
		  shareBean.setId(KEY_TEST);
		  shareBean.setTitle(iSafeSettingView.getContext().getString(R.string.share_lange_app));
		  shareBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  WeixinShare.init(iSafeSettingView.getContext());
				  WeixinShare.test("推荐一个粤语学习APP给你，下载地址：https://www.coolapk.com/apk/224772");
			  }
		  });
		  newSectionList.add(shareBean);

		   DyItemBean split= new DyItemBean();
		    split.setViewType(IItemView.ViewTypeEnum.SPLITE.value());

		  newSectionList.add(split);
		  DyItemBean  themeBean=new DyItemBean();
//		  themeBean.setId(KEY_TEST);


		  themeBean.setTitle(iSafeSettingView.getContext().getString(R.string.change_theme));
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
		  DyItemBean  testtBean=new DyItemBean();
		  testtBean.setId(KEY_TEST);
		  testtBean.setTitle(iSafeSettingView.getContext().getString(R.string.test));
		  testtBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {


//				  WeixinShare.init(iSafeSettingView.getContext());
//				  WeixinShare.test("ldh test");
			  }
		  });
//		  newSectionList.add(testtBean);



		  newSection.setDataMaps(newSectionList);
		  iSafeSettingView.initUI(newSection);
      }

	private void inportDictLJ() {

		iSafeSettingView.showToast("开始导入:请耐心等待 不要重复点击");

		DictBeanUtils.importFrombackUp(iSafeSettingView.getContext(), new DictBeanUtils.parseDictcallback() {
			@Override
			public void parseDataBack(Object obj) {
				List<Dict> data=(List<Dict>) obj;
               if (data.size()>0){
               	iSafeSettingView.showToast("导入覆盖成功");
               	BusinessBroadcastUtils.sendBroadcast(iSafeSettingView.getContext(),BusinessBroadcastUtils.TYPE_YY_REFRESH_HOME_COUNT,null);

				}else{
				   iSafeSettingView.showToast("导入覆盖失败");
				}

			}

			@Override
			public void showMsg(String msg) {
				iSafeSettingView.showToast(msg);

			}
		});

	}
	private void initDictLJ() {
		DictBeanUtils.importDbSentence(iSafeSettingView.getContext(), new DictBeanUtils.parseDictcallback() {
			@Override
			public void parseDataBack(Object list) {

				List<SentenceYy> sentenceYys=(List<SentenceYy>)list;
				sentenceYyDao.insertOrReplaceInTx(sentenceYys);

				//		 long dataCount=  sentenceYyDao.queryBuilder().count();


				iSafeSettingView.showToast("导入完成");
			}

			@Override
			public void showMsg(String msg) {

			}
		});



	}

	private void exportDict() {//导出字典数据
		iSafeSettingView.showToast("开始导出:请耐心等待 不要重复点击");
		DictBeanUtils.exportDb(iSafeSettingView.getContext(), new DictBeanUtils.parseDictcallback() {
			@Override
			public void parseDataBack(Object obj) {
				String isSucess=(String) obj;
				if (isSucess.equals("1")){
//					exportBean.setTitle("成功导出词典成功_sd卡目录_cantonese.db");
//					iSafeSettingView.updateItem(exportBean);
					ToastUtils.show(iSafeSettingView.getContext(),"成功导出词典成功_sd卡目录_cantonese.db");
				}else{
					ToastUtils.show(iSafeSettingView.getContext(),"导出失败");
				}


			}

			@Override
			public void showMsg(String msg) {
				iSafeSettingView.showToast(msg);

			}
		});

	}

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
		WidgetConfig widgetConfig= ThemeUtils.getThemeConfig(iSafeSettingView.getContext(),type);

		ThemeUtils.switchTheme(iSafeSettingView.getContext(),type);
		ThemeConfig themeConfig=new ThemeConfig();
		themeConfig.setBgColorResId(widgetConfig.getBgColor());
		themeConfig.setTitleColorResId(widgetConfig.getTextColor());
		RecycleConfig.getInstance().setThemeConfig(themeConfig);

		BusinessBroadcastUtils.sendBroadcast(iSafeSettingView.getContext(),BusinessBroadcastUtils.TYPE_CHANGE_THEME,type);
		init();

	}
}
