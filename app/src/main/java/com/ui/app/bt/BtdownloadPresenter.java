package com.ui.app.bt;

import com.business.BusinessBroadcastUtils;
import com.business.bean.SelectBindBean;
import com.core.CoreApplication;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.entity.VideoDB;
import com.core.db.greenDao.gen.VideoDBDao;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.bean.SelectBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.ui.HttpService;
import com.ui.video.IVideoHomeView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class BtdownloadPresenter {
	HttpService service;
	IVideoHomeView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private VideoDBDao mVideoDao;
	private boolean mIsCanSelect=false;
	public static String ID_NEWS="ID_NEWS";
	public static String ID_HIDE="ID_HIDE";
	public static String ID_BTDOWNLOAD="ID_BTDOWNLOAD";
	public static String ID_EMPTY="ID_EMPTY";
	public BtdownloadPresenter(IVideoHomeView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		mVideoDao = GlobalConstants.getInstance().getDaoSession().getVideoDBDao();
	}

      public void init(){
		  settingSection=new Section(KEY_SETTING);
		  settingSection.setShowSection(false);
		  List<DyItemBean> settingMaps=new ArrayList<>();

//			 final  VideoBussinessItem updateBean=new VideoBussinessItem();//新闻
//			  updateBean.setTitle(CoreApplication.getAppContext().getResources().getString(R.string.news));
//			  updateBean.setViewType(3);
//		  updateBean.setId(ID_NEWS);
//		  AddressHeadImgeSettings  imgeSettings=new AddressHeadImgeSettings();
//		  imgeSettings.setHeadImgDrawableId(R.drawable.new_life_icon);
//		  updateBean.setHeadImgeSettings(imgeSettings);
//
//
//		  updateBean.setSpanSize(2);
//			  updateBean.setOnItemListener(new IItemView.onItemClick() {
//				  @Override
//				  public void onItemClick(IItemView.ClickTypeEnum typeEnum, DyItemBean bean) {
//						  iVideoHomeView.showItem(updateBean);
//				  }
//			  });
//		  settingMaps.add(updateBean);
//
//		  final  VideoBussinessItem hideBean=new VideoBussinessItem();
//		  hideBean.setViewType(3);
//		  hideBean.setSpanSize(2);
//		  hideBean.setId(ID_HIDE);
//		  hideBean.setTitle(CoreApplication.getAppContext().getResources().getString(R.string.hide));
//		  AddressHeadImgeSettings  imgeSettingsHide=new AddressHeadImgeSettings();
//		  imgeSettingsHide.setHeadImgDrawableId(R.drawable.new_find_icon);
//		  hideBean.setHeadImgeSettings(imgeSettingsHide);
//
//		  hideBean.setOnItemListener(new IItemView.onItemClick() {
//			  @Override
//			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, DyItemBean bean) {
//				  iVideoHomeView.showItem(hideBean);
//
//			  }
//		  });
//		  settingMaps.add(hideBean);
//
//		  final  VideoBussinessItem searchBean=new VideoBussinessItem();
//		  hideBean.setViewType(3);
//		  hideBean.setSpanSize(2);
//		  searchBean.setId(ID_HIDE);
//		  searchBean.setTitle(CoreApplication.getAppContext().getResources().getString(R.string.search));
//		  AddressHeadImgeSettings  imgeSettingsSearch=new AddressHeadImgeSettings();
//		  imgeSettingsSearch.setHeadImgDrawableId(R.drawable.ic_launcher);
//		  searchBean.setHeadImgeSettings(imgeSettingsSearch);
//
//		  searchBean.setOnItemListener(new IItemView.onItemClick() {
//			  @Override
//			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, DyItemBean bean) {
//				  iVideoHomeView.showItem(searchBean);
//
//			  }
//		  });
//		  settingMaps.add(searchBean);
//		    for (int i=0;i<9;i++){
//				final  VideoBussinessItem emptyBean=new VideoBussinessItem();
//				emptyBean.setViewType(3);
//				emptyBean.setSpanSize(2);
//				emptyBean.setId(ID_EMPTY);
//				AddressHeadImgeSettings  imgeSettingsEmpty=new AddressHeadImgeSettings();
//				imgeSettingsEmpty.setHeadImgDrawableId(R.drawable.transparent);
//				emptyBean.setHeadImgeSettings(imgeSettingsEmpty);
//				settingMaps.add(emptyBean);
//
//			}
//		  settingSection.setDataMaps(settingMaps);
//
//    	  iVideoHomeView.initUI(settingSection);
//
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


	public void setHide(  List<SelectBean>  selectBeanList) {
		List<IDyItemBean>  resourceList=settingSection.getDataMaps();
		List<IDyItemBean>  newList=new ArrayList<>();
		List<SelectBindBean>  chaneList=new ArrayList<>();
		List<VideoDB>  chaneDbList=new ArrayList<>();


		for (SelectBean itemBean:selectBeanList) {
			String selectId=    itemBean.getId();
			File   oldeFile=new File(selectId);

			String newPathName=oldeFile.getAbsolutePath().substring(0,oldeFile.getAbsolutePath().lastIndexOf("/")+1);
			File   newFile=new File(newPathName+"."+oldeFile.getName()+".hide");
			boolean isSucess=oldeFile.renameTo(newFile);
			if (isSucess){
				SelectBindBean bindBean=new SelectBindBean();
				bindBean.setId(itemBean.getId());
				bindBean.setHidePath(newFile.getAbsolutePath());
				chaneList.add(bindBean);
			}
		}

		for (IDyItemBean  oldItem :resourceList ) {
			boolean isHide=false;
			for (SelectBindBean  itemSelectBean:chaneList ) {

				if (itemSelectBean.getId().equals(oldItem.getId())){
					isHide=true;
					VideoDB   videoDBItem=new VideoDB();
					videoDBItem.setName(oldItem.getTitle());
//					videoDBItem.setDurationString(oldItem.getHint());
//					videoDBItem.set
					videoDBItem.setOldFilePath(oldItem.getId());
					videoDBItem.setData(itemSelectBean.getHidePath());
					chaneDbList.add(videoDBItem);
					UUID uuid = UUID.randomUUID();

					String uniqueId = uuid.toString();
					videoDBItem.setId(uniqueId);
					mVideoDao.insert(videoDBItem);
					break;
				}
			}
			if (!isHide){
				newList.add(oldItem);
			}

		}
		settingSection.setDataMaps(newList);
		iVideoHomeView.initUI(settingSection);
		BusinessBroadcastUtils.sendBroadcast(iVideoHomeView.getContext(), BusinessBroadcastUtils.TYPE_REFRESH_VIDEO_HIDE, null);

	}
}
