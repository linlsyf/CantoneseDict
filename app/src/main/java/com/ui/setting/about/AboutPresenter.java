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
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.easysoft.utils.lib.http.ResponseMsg;
import com.easysoft.utils.lib.system.StringUtils;
import com.linlsyf.area.R;
import com.ui.HttpService;
import com.ui.dict.DictBeanUtils;
import com.ui.setting.InfoCardBean;

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
	private String KEY_INFO="info";
	private String KEY_LOGOUT="logout";
	private String KEY_UPDATE="update";
	private String KEY_USER_INFO="userInfo";
	private InfoCardBean infoCardBean;
	private String SECTION_NEW="new";
	private String KEY_ABOUT="about";
	private String KEY_TEST="test";
	private DyItemBean exportBean;
	private DyItemBean inportBean;
	SentenceYyDao mDictDao;

	public AboutPresenter(IAboutView iSafeSettingView) {
    	this.iSafeSettingView=iSafeSettingView;
		service=new HttpService();
	}

      public void init(){
		  List<IDyItemBean> dataMaps=new ArrayList<>();

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

	private void inportDictLJ() {

		iSafeSettingView.showToast("开始导出:请耐心等待 不要重复点击");


		iSafeSettingView.showToast(iSafeSettingView.getContext().getString(R.string.wait_dict_init_please));

		DictBeanUtils.initLJ(iSafeSettingView.getContext(), new DictBeanUtils.parseDictcallback() {
			@Override
			public void parseDataBack(Object obj) {
				List<SentenceYy> list= (List<SentenceYy>) obj;
                       if (list.size()>0){
						   SentenceYyDao mDictDao = CoreApplication.getInstance().getDaoSession().getSentenceYyDao();
						   mDictDao.insertOrReplaceInTx(list);
					   }


				String msg="导入完毕:总共导入"+list.size()+"条";
				inportBean.setTitle("成功导入例句数"+list.size()+"条");
				iSafeSettingView.updateItem(inportBean);
				iSafeSettingView.showToast(msg);

			}

			@Override
			public void showMsg(String msg) {
				iSafeSettingView.showToast(msg);

			}
		});

	}

	private void exportDict() {//导出字典数据
		iSafeSettingView.showToast("开始导出:请耐心等待 不要重复点击");

		Observable.create(new ObservableOnSubscribe< Integer  >() {
			@Override
			public void subscribe(ObservableEmitter< Integer > emitter)
					throws Exception {
//				DictDao mDictDao = CoreApplication.getInstance().getDaoSession().getDictDao();
//				List<Dict>  dictList =   mDictDao.loadAll();
//				String text= JSON.toJSONString(dictList);
//
////				String text =JSONArray.toJSONString(dictList);
//				Info2File.saveCrashInfo2File(text);

				int size=0;
				emitter.onNext(size);

			} })
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io()).subscribe(new Observer<Integer>() {
			@Override public void onSubscribe(Disposable d) {
//				mDisposable=d;
			}
			@Override public void onNext( Integer size) {
//				String msg="导出完毕:总共导出"+size+"条";
				exportBean.setTitle("成功导出词典成功");
				iSafeSettingView.updateItem(exportBean);
//				iSafeSettingView.showToast(msg);

			}
			@Override public void onError(Throwable e) {
			}

			@Override public void onComplete() {

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
}
