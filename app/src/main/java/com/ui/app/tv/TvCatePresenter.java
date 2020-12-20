package com.ui.app.tv;

import com.business.BusinessBroadcastUtils;
import com.business.bean.SelectBindBean;
import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.entity.VideoDB;
import com.core.db.greenDao.gen.VideoDBDao;
import com.easy.recycleview.bean.AddressHeadImgeSettings;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.bean.SelectBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.linlsyf.area.R;
import com.ui.HttpService;
import com.ui.video.IVideoHomeView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TvCatePresenter {
	HttpService service;
	IVideoHomeView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private VideoDBDao mVideoDao;
	private boolean mIsCanSelect=false;
	public static String ID_FILMS ="ID_FILMS";
	public static String ID_TV_FILM ="ID_TV_FILM";
	public static String ID_CUSTOM ="ID_CUSTOM";
	public static String ID_TV="ID_TV";
	public static String ID_HIDE="ID_HIDE";
	public static String ID_EMPTY="ID_EMPTY";
	public static String ID_BTDOWNLOAD="ID_BTDOWNLOAD";
	public static String ID_translate="ID_translate";

	public TvCatePresenter(IVideoHomeView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		mVideoDao = GlobalConstants.getInstance().getDaoSession().getVideoDBDao();
	}

      public void init(){
		  settingSection=new Section(KEY_SETTING);
		  settingSection.setShowSection(false);
		  List<IDyItemBean> settingMaps=new ArrayList<>();

			 final  VideoBussinessItem updateBean=new VideoBussinessItem();//新闻
//			  updateBean.setTitle(CoreApplication.getAppContext().getResources().getString(R.string.film));
			  updateBean.setViewType(3);
		  updateBean.setId(ID_FILMS);
//		  AddressHeadImgeSettings imgeSettings=new AddressHeadImgeSettings();
//		  imgeSettings.setHeadImgDrawableId(R.drawable.film);
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
//		  imgeSettingsHide.setHeadImgDrawableId(R.drawable.cili_magnet);
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
		  searchBean.setId(ID_TV_FILM);
//		  searchBean.setTitle(CoreApplication.getAppContext().getResources().getString(R.string.tv_film));
//		  AddressHeadImgeSettings  imgeSettingsSearch=new AddressHeadImgeSettings();
//		  imgeSettingsSearch.setHeadImgDrawableId(R.drawable.tv);
//		  searchBean.setHeadImgeSettings(imgeSettingsSearch);

		  searchBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  iVideoHomeView.showItem(searchBean);

			  }
		  });
		  final  VideoBussinessItem customBean=new VideoBussinessItem();
		  customBean.setViewType(3);
		  customBean.setSpanSize(2);
		  customBean.setId(ID_CUSTOM);
//		  customBean.setTitle(CoreApplication.getAppContext().getResources().getString(R.string.custom_edit));
//		  AddressHeadImgeSettings  imgeSettingsCustom=new AddressHeadImgeSettings();
//		  imgeSettingsCustom.setHeadImgDrawableId(R.drawable.ic_launcher);
//		  customBean.setHeadImgeSettings(imgeSettingsSearch);

		  customBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  iVideoHomeView.showItem(customBean);

			  }
		  });
		  settingMaps.add(customBean);
		  final  VideoBussinessItem tvBean=new VideoBussinessItem();
		  tvBean.setViewType(3);
		  tvBean.setSpanSize(2);
		  tvBean.setId(ID_TV);
//		  tvBean.setTitle(CoreApplication.getAppContext().getResources().getString(R.string.tv_ch));
//		  AddressHeadImgeSettings  tvSettingsSearch=new AddressHeadImgeSettings();
//		  tvSettingsSearch.setHeadImgDrawableId(R.drawable.tv_custom);
//		  tvBean.setHeadImgeSettings(tvSettingsSearch);

		  tvBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  iVideoHomeView.showItem(tvBean);

			  }
		  });
		  settingMaps.add(tvBean);



		  final VideoBussinessItem itemBean=new VideoBussinessItem();
		  AddressHeadImgeSettings  itemHeadSetting=new AddressHeadImgeSettings();

//		  itemHeadSetting.setHeadImgDrawableId(R.drawable.cili_magnet);
//		  itemBean.setHeadImgeSettings(itemHeadSetting);
		  itemBean.setViewType(3);
		  itemBean.setId(ID_BTDOWNLOAD);
		  itemBean.setSpanSize(2);
		  itemBean.setTitle("下载");
		  itemBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  iVideoHomeView.showItem(itemBean);

			  }
		  });
		  settingMaps.add(itemBean);

		  final VideoBussinessItem itemBeanTranslate=new VideoBussinessItem();
//		  AddressHeadImgeSettings  itemHeadSetting=new AddressHeadImgeSettings();

//		  AddressHeadImgeSettings  itemHeadSettingCantonese=new AddressHeadImgeSettings();
//
//		  itemHeadSettingCantonese.setHeadImgDrawableId(R.drawable.);
//		  itemBeanTranslate.setHeadImgeSettings(itemHeadSettingCantonese);
		  itemBeanTranslate.setViewType(3);
		  itemBeanTranslate.setId(ID_translate);
		  itemBeanTranslate.setSpanSize(2);
		  itemBeanTranslate.setTitle(iVideoHomeView.getContext().getString(R.string.translate));
		  itemBeanTranslate.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				  iVideoHomeView.showItem(itemBeanTranslate);

			  }
		  });
		  settingMaps.add(itemBeanTranslate);




		    for (int i=0;i<12;i++){
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
