package com.ui.app.tv.cate;

import com.business.BusinessBroadcastUtils;
import com.business.bean.SelectBindBean;
import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.entity.CustomTv;
import com.core.db.greenDao.entity.VideoDB;
import com.core.db.greenDao.gen.CustomTvDao;
import com.core.db.greenDao.gen.VideoDBDao;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.RightSecondImgSettings;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.bean.SelectBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easysoft.utils.lib.system.DensityUtil;
import com.linlsyf.cantonese.R;
import com.ui.HttpService;
import com.ui.app.tv.ITVView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class TVCustomPresenter {
	HttpService service;
	ITVView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private VideoDBDao mVideoDao;
	private boolean mIsCanSelect=false;

	CustomTvDao  dao;
	private List<CustomTv> videoList;
	public TVCustomPresenter(ITVView iSafeSettingView) {
		this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		dao = GlobalConstants.getInstance().getDaoSession().getCustomTvDao();
	}

	public void init(boolean isreaddb){
		settingSection=new Section(KEY_SETTING);
		settingSection.setShowSection(false);


		List<IDyItemBean> settingMaps=new ArrayList<>();
		settingSection=new Section(KEY_SETTING);
		settingSection.setShowSection(false);

		int headImgSize= DensityUtil.dip2px(CoreApplication.getAppContext(),80);
		int i=0;
		if (isreaddb){
			videoList  = dao.loadAll();
		}
		for (CustomTv item : videoList ) {
//			mDbFileMap.put(item.getId(),item.get());
			final  VideoBussinessItem updateBean=new VideoBussinessItem();
			updateBean.setTitle(item.getName());
			updateBean.setId(item.getId());
			updateBean.setData(item.getUrl());
			RightSecondImgSettings imgSetting=new RightSecondImgSettings();
			imgSetting.setRightSecondImgRadius(DensityUtil.dip2px(CoreApplication.getAppContext(), 25));

			imgSetting.setRightSecondImgResId(R.drawable.common_edit_text_clear);
			updateBean.setRightSecondImgSettings(imgSetting);

			final  int postion=i;
			updateBean.setOnItemListener(new IItemView.onItemClick() {
				@Override
				public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
					 if (typeEnum==IItemView.ClickTypeEnum.ITEM){
						 iVideoHomeView.openVideoBySelf(updateBean);

					 }
					 else if (typeEnum==IItemView.ClickTypeEnum.RIGHT_SECOND_IMG){
						 videoList.remove(postion);
						 dao.deleteByKey(bean.getId());
						 init(false);


					 }
				}
			});


			if (i!=0){
				DyItemBean spliteItem=new DyItemBean();
				spliteItem.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
				settingMaps.add(spliteItem);
			}

			settingMaps.add(updateBean);
			i++;

		}
		settingSection.setDataMaps(settingMaps);
		iVideoHomeView.initUI(settingSection);


	}

	 public void add(String txt){
		 String[]  data= txt.split(",");
		 CustomTv item=new CustomTv();
		  if (data.length==1){
			  item.setName(getSubStr(data[0],2));
			  item.setUrl(data[0]);
		  }else{
			  item.setName(data[0]);
			  item.setUrl(data[1]);
		  }

		 Random random=new Random();
		 String id=random.nextLong()+"";
		 item.setId(id);
		 dao.insert(item);//添加一个
		 videoList.add(0,item);
		 init(false);

	 }


	private static String getSubStr(String str, int num) {

		if (!(str.indexOf('/') > -1)) {
			return "自定义";
		}
		String result = "";
		int i = 0;
		while (i < num) {

			int lastFirst = str.lastIndexOf('/');
			result = str.substring(lastFirst) + result;
			str = str.substring(0, lastFirst);
			i++;
		}

		return result.substring(1);
	 }
	public boolean setCanEdit() {
		List<IDyItemBean>  resourceList=settingSection.getDataMaps();
		mIsCanSelect=!mIsCanSelect;
		for (IDyItemBean itemBean: resourceList ) {
			if (itemBean.getViewType()== IItemView.ViewTypeEnum.ITEM.value()){
//				itemBean.setItemCanEdit(mIsCanSelect);
//				itemBean.setShowLeftCheckBox(mIsCanSelect);
//				itemBean.setSelectType(settingSection.getId());
//				itemBean.setOnItemClickAble(!mIsCanSelect);
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
