package com.ui.catalog;

import com.business.service.music.MusiceHelper;
import com.business.service.music.server.SongBean;
import com.core.CommonDefined;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.gen.DictDao;
import com.easy.recycleview.bean.AddressHeadImgeSettings;
import com.easy.recycleview.bean.CentLayoutConfig;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easysoft.utils.lib.system.DensityUtil;
import com.linlsyf.area.R;
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
	public CatalogPresenter(ICatalogView iSafeSettingView) {
    	this.iSafeSettingView=iSafeSettingView;
	}
      public void init(){
		  mDictDao = GlobalConstants.getInstance().getDaoSession().getDictDao();
		  List<IDyItemBean>  newSectionList=new ArrayList<>();
		  Section newSection=new Section("");
		  int headRadius= DensityUtil.dip2pxInt(iSafeSettingView.getContext(),25);
		  musicBean=new DyItemBean();
		  musicBean.setTitle(iSafeSettingView.getContext().getString(R.string.radom_yuyu_music));
		  musicBean.setHeadImgeSettings(new AddressHeadImgeSettings().setHeadImgDrawableId(R.drawable.setting_music).setHeadImgRadius(headRadius));
		    musicBean.setRightFirstButtonText(iSafeSettingView.getContext().getString(R.string.next_music));
		  musicBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
				  if (clickTypeEnum== IItemView.ClickTypeEnum.ITEM){
					  MusiceHelper.getInstance(iSafeSettingView.getContext()).checkPlay(new MusiceHelper.playCallBack() {
						  @Override
						  public void callBack(boolean isPlaying,SongBean songBean) {
							  updateSongItemUI(isPlaying,songBean);
						  }
					  });

				  }else if(clickTypeEnum== IItemView.ClickTypeEnum.RIGHTBUTTION){
					  MusiceHelper.getInstance(iSafeSettingView.getContext()).next(new MusiceHelper.playCallBack() {
						  @Override
						  public void callBack(boolean isPlaying,SongBean songBean) {
							  updateSongItemUI(isPlaying,songBean);
						  }
					  });
				  }
			  }
		  });
		  newSectionList.add(musicBean);
		  DyItemBean  splitItemBean=new DyItemBean();
		  splitItemBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
		  newSectionList.add(splitItemBean);

		  int hight=DensityUtil.dip2pxInt(iSafeSettingView.getContext(),100);
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




		  DyItemBean  itemErrorBasen=new DyItemBean();
		  itemErrorBasen.setItemHight(hight);

		  itemErrorBasen.setCentLayoutConfig(
				  new CentLayoutConfig()
						  .setImgRadius(headRadius).setImgResId(R.drawable.setting_about)
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

						  int hight= DensityUtil.dip2px(iSafeSettingView.getContext(),1200);
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
		  DyItemBean  testtBean=new DyItemBean();
//		  testtBean.setTitle(iSafeSettingView.getContext().getString(R.string.laboratory_yueyu));
		  testtBean.setCentLayoutConfig(
		  		new CentLayoutConfig().setImgRadius(headRadius).setImgResId(R.drawable.catalog_pro)
		  .setName(iSafeSettingView.getContext().getString(R.string.laboratory_yueyu))
		  );
		  testtBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
			  	 iSafeSettingView.showToast(iSafeSettingView.getContext().getString(R.string.develop_setting));
			  }
		  });
           newSection.setAutoAddSpliteLine(false);
		  newSection.setDataMaps(newSectionList);
		  iSafeSettingView.initUI(newSection);
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
