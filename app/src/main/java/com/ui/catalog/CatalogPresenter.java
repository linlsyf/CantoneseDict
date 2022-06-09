package com.ui.catalog;

import com.alibaba.fastjson.JSONObject;
import com.bean.AppMsg;
import com.business.service.music.MusiceHelper;
import com.business.service.music.server.SongBean;
import com.core.CommonDefined;
import com.core.CoreApplication;
import com.core.ServerUrl;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.gen.DictDao;
import com.easy.recycleview.bean.AddressEditSettings;
import com.easy.recycleview.bean.AddressHeadImgeSettings;
import com.easy.recycleview.bean.BgSetting;
import com.easy.recycleview.bean.CentLayoutConfig;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.easysoft.utils.lib.http.ResponseMsg;
import com.easysoft.utils.lib.system.DensityUtil;
import com.linlsyf.cantonese.R;
import com.ui.HttpService;
import com.ui.dict.DictBeanUtils;
import com.ui.dict.DictBusBean;
import com.ui.dict.DictTypeEnum;
import com.utils.TimeAreaUtils;

import java.util.ArrayList;
import java.util.List;


public class CatalogPresenter {
	ICatalogView iSafeSettingView;
	private DictDao mDictDao;
	private  int mSpanSize=3;
	private DyItemBean musicBean;
	int hight=100;
	List<IDyItemBean>  newSectionList;
	private HttpService service;
	Section newSection;
	private int headRadius;

	public CatalogPresenter(ICatalogView iSafeSettingView) {
    	this.iSafeSettingView=iSafeSettingView;
	}
      public void init(){
		  mDictDao = GlobalConstants.getInstance().getDaoSession().getDictDao();
		  newSectionList=new ArrayList<>();
		   newSection=new Section("");
		   headRadius= DensityUtil.dip2pxInt(iSafeSettingView.getContext(),25);
		  hight= DensityUtil.dip2pxInt(iSafeSettingView.getContext(),100);
		  service=new HttpService();

//		  DyItemBean  logoBean=new DyItemBean();
//		  BgSetting setting =new BgSetting();
//
//		   setting.setContentBgResid(R.drawable.openlogo);
//		  logoBean.setItemHight(hight*4);
//		  logoBean.setViewType(2);
//		  newSectionList.add(logoBean);

		  DyItemBean  testtBean=new DyItemBean();
//		  testtBean.setTitle(iSafeSettingView.getContext().getString(R.string.laboratory_yueyu));
		  testtBean.setCentLayoutConfig(
				  new CentLayoutConfig().setImgRadius(headRadius)
						  .setImgResId(R.drawable.catalog_pro)
						  .setName(iSafeSettingView.getContext().getString(R.string.feedback))
		  );
		  testtBean.setSpanSize(mSpanSize);
		  testtBean.setItemHight(hight);

		  testtBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  //iSafeSettingView.showToast(iSafeSettingView.getContext().getString(R.string.develop_setting));

				  iSafeSettingView .openUrl("https://support.qq.com/products/281738?");

			  }
		  });

		  newSectionList.add(testtBean);


		  DyItemBean  itemErrorBasen=new DyItemBean();
		  itemErrorBasen.setItemHight(hight);

		  itemErrorBasen.setCentLayoutConfig(
				  new CentLayoutConfig()
						  .setImgRadius(headRadius)
						  .setImgResId(R.drawable.setting_about)
						  .setName(iSafeSettingView.getContext().getString(R.string.last_error_msg))
		  );

		  itemErrorBasen.setSpanSize(mSpanSize);
		  itemErrorBasen.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
				  DictBeanUtils.getErrorMsg(iSafeSettingView.getContext(), new DictBeanUtils.parseDictcallback() {
					  @Override
					  public void parseDataBack(Object list) {
						  List<DyItemBean> dataListCustom=new ArrayList<>();
						  final DictBusBean todayBean=new DictBusBean();
						  todayBean.setTitle(list.toString());
						  todayBean.setViewType(CommonDefined.ViewType_ScrollView);

//						  int hight= DensityUtil.dip2px(iSafeSettingView.getContext(),1200);
						  todayBean.setItemHight(hight);
//						  todayBean.setCentLayoutConfig(new CentLayoutConfig().setName(list.toString()));
						  dataListCustom.add(todayBean);

						  iSafeSettingView.openTempView(dataListCustom);
					  }

					  @Override
					  public void showMsg(String msg) {
						  iSafeSettingView.showToast(msg);

					  }
				  });

			  }
		  });
		  newSectionList.add(itemErrorBasen);
		  DyItemBean  splitItemBean=new DyItemBean();
		  splitItemBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
		  newSectionList.add(splitItemBean);

		  DyItemBean  itemBean=new DyItemBean();
		    itemBean.setItemHight(hight);
//		  itemBean.setCentLayoutConfig(new CentLayoutConfig().setName(iSafeSettingView.getContext().getString(R.string.start_percent)));
		  itemBean.setCentLayoutConfig(
				  new CentLayoutConfig()
						  .setImgRadius(headRadius).setImgResId(R.drawable.catalog_pro)
						  .setName(iSafeSettingView.getContext().getString(R.string.start_percent))
		  );
		      itemBean.setSpanSize(mSpanSize);
		    itemBean.setOnItemListener(new IItemView.onItemClick() {
				@Override
				public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
					List<DyItemBean> dataListCustom=new ArrayList<>();
					long readedCount = mDictDao.queryBuilder().where(DictDao.Properties.Status.eq(1)).count();
					long   notReadCount = mDictDao.queryBuilder().where(DictDao.Properties.Status.eq(0)).count();
					long   collectCount = mDictDao.queryBuilder().where(DictDao.Properties.Status.eq(2)).count();
					String date = TimeAreaUtils.getToDayString();
					long   todayCount = mDictDao.queryBuilder().where(DictDao.Properties.ModifyTime.like("%" +date+ "%")).count();
					long   totalCount = mDictDao.count();
					String yesday= TimeAreaUtils.getYesDayString();
					long  yesdayCount =	 mDictDao.queryBuilder().where(DictDao.Properties.ModifyTime.like("%" +yesday+ "%")).orderAsc(DictDao.Properties.ModifyTime).count();
					final DictBusBean notReadBean=new DictBusBean();
					notReadBean.setTitle("成语总数:"+totalCount+" 未学："+notReadCount);
					long  readCount=readedCount+collectCount;
					notReadBean.setHint("已学："+readCount +"  收藏:"+collectCount+" [点击查看收藏]");
					notReadBean.setOnItemListener(new IItemView.onItemClick() {
						@Override
						public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
							iSafeSettingView.showAllLearn();
						}
					});
					dataListCustom.add(notReadBean);

					final DictBusBean todayBean=new DictBusBean();
					todayBean.setTitle("今日："+todayCount);
					todayBean.setOnItemListener(new IItemView.onItemClick() {
						@Override
						public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
							iSafeSettingView.showType(null, DictTypeEnum.TODAY.value());
						}
					});
					dataListCustom.add(todayBean);
					final DictBusBean yesdayBean=new DictBusBean();
					yesdayBean.setTitle("昨日："+yesdayCount);
					yesdayBean.setOnItemListener(new IItemView.onItemClick() {
						@Override
						public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
							iSafeSettingView.showType(null,DictTypeEnum.YESDAY.value());
						}
					});
					dataListCustom.add(yesdayBean);
					iSafeSettingView.openCustomView(dataListCustom);
				}
			});
		     newSectionList.add(itemBean);
		  DyItemBean  itemBeaBasen=new DyItemBean();
		  itemBeaBasen.setItemHight(hight);

		  itemBeaBasen.setCentLayoutConfig(
				  new CentLayoutConfig()
						  .setImgRadius(headRadius).setImgResId(R.drawable.setting_about)
						  .setName(iSafeSettingView.getContext().getString(R.string.learn_base_yp))
		  );

		  itemBeaBasen.setSpanSize(mSpanSize);
		  itemBeaBasen.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
				  iSafeSettingView.toYuePing();
			  }
		  });
		  newSectionList.add(itemBeaBasen);

		  String url = ServerUrl.baseUrl+ ServerUrl.APP_LIST;

		  url=url+"?type=cantonese";
//		  url=url+"?start="+offset+"&limit="+offsetNum;
		  service.request(iSafeSettingView.getContext(), url ,new IEasyResponse() {
			  @Override
			  public void onFailure(CallBackResult serviceCallBack) {
				  iSafeSettingView.showToast(R.string.exec_fail);
			  }

			  @Override
			  public void onResponse(CallBackResult serviceCallBack) {
				  if (serviceCallBack.isSucess()){
					  refresh(serviceCallBack);
				  }else{

					  iSafeSettingView.showToast(R.string.exec_fail);
				  }

			  }
		  });
		  DyItemBean  splitItemBean2=new DyItemBean();
		  splitItemBean2.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
		  newSectionList.add(splitItemBean2);
		  DyItemBean  homeBean=new DyItemBean();
//		  testtBean.setTitle(iSafeSettingView.getContext().getString(R.string.laboratory_yueyu));
		  homeBean.setCentLayoutConfig(
				  new CentLayoutConfig().setImgRadius(headRadius)
						  .setImgResId(R.drawable.catalog_pro)
						  .setName(iSafeSettingView.getContext().getString(R.string.websearch))
		  );
		  homeBean.setSpanSize(mSpanSize);
		  homeBean.setItemHight(hight);

		  homeBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  //iSafeSettingView.showToast(iSafeSettingView.getContext().getString(R.string.develop_setting));

//				  iSafeSettingView .openUrl("https://www.linlsyf.cn/yueyu.html");
				  iSafeSettingView .openUrl("file:///android_asset/yueyu.html");

			  }
		  });

		  newSectionList.add(homeBean);
		   homeBean=new DyItemBean();
//		  testtBean.setTitle(iSafeSettingView.getContext().getString(R.string.laboratory_yueyu));
		  homeBean.setCentLayoutConfig(
				  new CentLayoutConfig().setImgRadius(headRadius)
						  .setImgResId(R.drawable.catalog_pro)
						  .setName(iSafeSettingView.getContext().getString(R.string.developingtite))
		  );
		  homeBean.setSpanSize(mSpanSize);
		  homeBean.setItemHight(hight);

		  newSectionList.add(homeBean);


           newSection.setAutoAddSpliteLine(false);
		  newSection.setDataMaps(newSectionList);
		  iSafeSettingView.initUI(newSection);
	}
	public void refresh(CallBackResult serviceCallBack){
		ResponseMsg msg=   serviceCallBack.getResponseMsg();
		ResponseMsg serverUserResponseMsgData= JSONObject.parseObject(msg.getData().toString(), ResponseMsg.class);
		String data=serverUserResponseMsgData.getData().toString();

		if (null!=data){
			List<AppMsg> dataListFavorites= JSONObject.parseArray(data.toString(), AppMsg.class);

			for (final AppMsg  exam:dataListFavorites  ) {
				final DyItemBean  itemBeanDb=new DyItemBean();
				itemBeanDb.setBindObject(exam);
				itemBeanDb.setCentLayoutConfig(
						new CentLayoutConfig().setName(exam.getName())
				)  ;

				itemBeanDb.setId(exam.getId());
//				itemBeanDb.setSpanSize(mSpanSize);


				itemBeanDb.setItemHight(hight);
//		  itemBean.setCentLayoutConfig(new CentLayoutConfig().setName(iSafeSettingView.getContext().getString(R.string.start_percent)));
				itemBeanDb.setCentLayoutConfig(
						new CentLayoutConfig()
								.setImgRadius(headRadius).setImgResId(R.drawable.catalog_pro)
								.setName(exam.getName())
				);
				itemBeanDb.setSpanSize(mSpanSize);
				
				itemBeanDb.setOnItemListener(new IItemView.onItemClick() {
					@Override
					public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
							iSafeSettingView.openUrl(exam.getContent());


					}
				});
				newSectionList.add(itemBeanDb);

			}

			newSection.setAutoAddSpliteLine(false);
			newSection.setDataMaps(newSectionList);
			iSafeSettingView.initUI(newSection);


		}
	}
	private void updateSongItemUI(boolean isPlaying,SongBean songBean) {
		musicBean.setHintShow(true);
		if (isPlaying){
			musicBean.setTitle(iSafeSettingView.getContext().getString(R.string.radom_yuyu_music));
			musicBean.setHint(iSafeSettingView.getContext().getString(R.string.click_play_music));
		}else {
			String  musicTitle=songBean.getTitle();
			if (musicTitle==null){
				musicTitle=iSafeSettingView.getContext().getString(R.string.unknown);
			}
			AddressHeadImgeSettings  headImgeSettings=new AddressHeadImgeSettings();
			headImgeSettings.setHeadImgRadius((int)iSafeSettingView.getContext().getResources().getDimension(R.dimen.comon_head_radius));
			headImgeSettings.setHeadImgUrl(songBean.getPicture());
			musicBean.setHeadImgeSettings(headImgeSettings);
			musicBean.setTitle("正在播放:"+" (点击暂停)");
			musicBean.setHint(musicTitle+" ("+songBean.getArtist()+")");
		}
		iSafeSettingView.updateUIItem(isPlaying,musicBean);
	}


}
