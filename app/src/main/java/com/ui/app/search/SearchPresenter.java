package com.ui.app.search;

import android.content.ClipboardManager;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.gen.VideoDBDao;
import com.easy.recycleview.bean.AddressHeadImgeSettings;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.linlsyf.area.R;
import com.ui.HttpService;
import com.ui.app.search.bean.ResultsBean;
import com.ui.app.search.bean.SearchResultBean;
import com.ui.video.IVideoHomeView;

import java.util.ArrayList;
import java.util.List;



public class SearchPresenter {
	HttpService service;
	IVideoHomeView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private VideoDBDao mVideoDao;
	private boolean mIsCanSelect=false;
	public static String ID_NEWS="ID_FILMS";
	public static String ID_SEARCH="ID_TV_FILM";
	public static String ID_TV="ID_TV";
	public static String ID_HIDE="ID_HIDE";
	public static String ID_BTDOWNLOAD="ID_BTDOWNLOAD";
	public static String ID_EMPTY="ID_EMPTY";
	public SearchPresenter(IVideoHomeView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		mVideoDao = GlobalConstants.getInstance().getDaoSession().getVideoDBDao();
	}

      public void init(){
		  settingSection=new Section(KEY_SETTING);
		  settingSection.setShowSection(false);
		  List<IDyItemBean> settingMaps=new ArrayList<>();

			 final  VideoBussinessItem updateBean=new VideoBussinessItem();//新闻
//			  updateBean.setTitle(CoreApplication.getAppContext().getResources().getString(R.string.news));
			  updateBean.setViewType(3);
		  updateBean.setId(ID_NEWS);
//		  AddressHeadImgeSettings imgeSettings=new AddressHeadImgeSettings();
//		  imgeSettings.setHeadImgDrawableId(R.drawable.new_life_icon);
//		  updateBean.setHeadImgeSettings(imgeSettings);


		  updateBean.setSpanSize(2);
			  updateBean.setOnItemListener(new IItemView.onItemClick() {
				  @Override
				  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
						  iVideoHomeView.showItem(updateBean);
				  }
			  });
		  settingMaps.add(updateBean);

		  final  VideoBussinessItem hideBean=new VideoBussinessItem();
		  hideBean.setViewType(3);
		  hideBean.setSpanSize(2);
		  hideBean.setId(ID_HIDE);
//		  hideBean.setTitle(CoreApplication.getAppContext().getResources().getString(R.string.hide));
//		  AddressHeadImgeSettings  imgeSettingsHide=new AddressHeadImgeSettings();
//		  imgeSettingsHide.setHeadImgDrawableId(R.drawable.new_find_icon);
//		  hideBean.setHeadImgeSettings(imgeSettingsHide);

		  hideBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  iVideoHomeView.showItem(hideBean);

			  }
		  });
		  settingMaps.add(hideBean);

		  final  VideoBussinessItem searchBean=new VideoBussinessItem();
		  searchBean.setViewType(3);
		  searchBean.setSpanSize(2);
		  searchBean.setId(ID_SEARCH);
		  searchBean.setTitle(CoreApplication.getAppContext().getResources().getString(R.string.search));
		  AddressHeadImgeSettings  imgeSettingsSearch=new AddressHeadImgeSettings();
		  imgeSettingsSearch.setHeadImgDrawableId(R.drawable.ic_launcher);
		  searchBean.setHeadImgeSettings(imgeSettingsSearch);

		  searchBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  iVideoHomeView.showItem(searchBean);

			  }
		  });
		  settingMaps.add(searchBean);
		  final  VideoBussinessItem tvBean=new VideoBussinessItem();
		  tvBean.setViewType(3);
		  tvBean.setSpanSize(2);
		  tvBean.setId(ID_TV);
//		  tvBean.setTitle(CoreApplication.getAppContext().getResources().getString(R.string.tv_ch));
		  AddressHeadImgeSettings  tvSettingsSearch=new AddressHeadImgeSettings();
		  tvSettingsSearch.setHeadImgDrawableId(R.drawable.ic_launcher);
		  tvBean.setHeadImgeSettings(tvSettingsSearch);

		  tvBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  iVideoHomeView.showItem(tvBean);

			  }
		  });
		  settingMaps.add(tvBean);
		    for (int i=0;i<9;i++){
				final  VideoBussinessItem emptyBean=new VideoBussinessItem();
				emptyBean.setViewType(3);
				emptyBean.setSpanSize(2);
				emptyBean.setId(ID_EMPTY);
				AddressHeadImgeSettings  imgeSettingsEmpty=new AddressHeadImgeSettings();
				imgeSettingsEmpty.setHeadImgDrawableId(R.drawable.transparent);
				emptyBean.setHeadImgeSettings(imgeSettingsEmpty);
				settingMaps.add(emptyBean);

			}
		  settingSection.setDataMaps(settingMaps);

    	  iVideoHomeView.initUI(settingSection);
    	
      }


	public boolean setCanEdit() {
		List<IDyItemBean>  resourceList=settingSection.getDataMaps();
		mIsCanSelect=!mIsCanSelect;
		for (IDyItemBean itemBean: resourceList ) {
			if (itemBean.getViewType()== IItemView.ViewTypeEnum.ITEM.value()){
//				itemBean.setItemCanEdit(mIsCanSelect);
//				itemBean.setShowLeftCheckBox(mIsCanSelect);
//				itemBean.setSelectType(settingSection.getId());
//				itemBean.setOnItemClickAble(mIsCanSelect);
			}
		}
		iVideoHomeView.initUI(settingSection);
          return   mIsCanSelect;
	}


	public void search(String text) {
		String url = "http://bt.xiandan.in/api/search";

		String source="种子搜";
		int pager=1;
		 url=url+"?"+"source="+source+"&keyword="+text+"&page="+pager;
		//   http://bt.xiandan.in/api/search  source: 种子搜  keyword: 苍井空  page: 1

//		service.request(url, new EasyHttpCallback(new IEasyResponse() {
//			@Override
//			public void onResponse(CallBackResult callBack) {
//				if(callBack.isSucess()&&callBack.getResponseMsg()!=null){
//                    SearchResultBean data =JSON.parseObject(callBack.getResponseMsg().getData().toString(),SearchResultBean.class);
//					List<ResultsBean> orderList = (List<ResultsBean>)JSON.parseArray(data.getResults().toString(), ResultsBean.class);
//					Section nextSection=new Section(KEY_SETTING);
//					List<IDyItemBean> dataMaps=new ArrayList<>();
////					for (int i = 0; i < orderList.size(); i++) {
////						ResultsBean order=orderList.get(i);
////						IDyItemBean itembean=getDyItemBean(order);
////						dataMaps.add(itembean);
////
////					}
//					nextSection.setDataMaps(dataMaps);
//					nextSection.setShowSection(false);
//
//					iVideoHomeView.initUI(nextSection);
//
//				}
//			}
//
//			@Override
//			public void onFailure(CallBackResult  serviceCallBack) {
//				iVideoHomeView.showToast( "服务器响应失败");
//			}
//		}).setOutside(true));


	}

	public DyItemBean getIDyItemBean(final ResultsBean goods){
		DyItemBean itembean = new DyItemBean();
		itembean.setOnItemListener(new IItemView.onItemClick() {
			@Override
			public void onItemClick(IItemView.ClickTypeEnum typeEnum,
									IDyItemBean bean) {
				if (typeEnum== IItemView.ClickTypeEnum.ITEM) {

					ClipboardManager cm = (ClipboardManager)iVideoHomeView.getContext(). getSystemService(Context.CLIPBOARD_SERVICE);
//					cm.setText(bean.getHint());
					iVideoHomeView.showToast( "复制成功，可以发给朋友们了。");

				}

			}
		});

		itembean.setTitle(goods.getName());
		itembean.setHint(goods.getMagnet());
		itembean.setRightFirstText(goods.getFormatSize());
		itembean.setHintShow(true);
//		itembean.setId(goods.getMagnet());

		return  itembean;
	}
}
