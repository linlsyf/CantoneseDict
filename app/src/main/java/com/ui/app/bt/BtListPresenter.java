package com.ui.app.bt;

import android.os.Environment;

import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.entity.DownLoadBean;
import com.core.db.greenDao.gen.DownLoadBeanDao;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.RightSecondImgSettings;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easysoft.utils.lib.system.DensityUtil;
import com.linlsyf.area.R;
import com.ui.HttpService;

import java.util.ArrayList;
import java.util.List;

//import com.xunlei.downloadlib.XLTaskHelper;


public class BtListPresenter {
	HttpService service;
	IBTTVView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private DownLoadBeanDao dao;
	private boolean mIsCanSelect=false;
//	XLTaskHelper helper;
	public static final String DOWNLOAD_PATH =
			Environment.getExternalStorageDirectory().getAbsolutePath() +
					"/downloads/";

	private List<DownLoadBean> videoList;
	public BtListPresenter(IBTTVView iSafeSettingView) {
		this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		dao = GlobalConstants.getInstance().getDaoSession().getDownLoadBeanDao();
//		helper=XLTaskHelper.instance(CoreApplication.getAppContext());

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
		for (final DownLoadBean item : videoList ) {
			final VideoBussinessItem updateBean=new VideoBussinessItem();
			updateBean.setBindObject(item);
			updateBean.setTitle(item.getName());
			updateBean.setId(item.getId());

			long percent=0;
			if (item.getFileSize()!=0){
				percent=(item.getDownSize()/(item.getFileSize()*100))*100;
			}
			 updateBean.setHint(percent+"%");
			 updateBean.setTaskStatus( item.getTaskStatus());

			updateBean.setData(item.getUrl());
			RightSecondImgSettings imgSetting=new RightSecondImgSettings();
			imgSetting.setRightSecondImgRadius(DensityUtil.dip2px(CoreApplication.getAppContext(), 25));

			imgSetting.setRightSecondImgResId(R.drawable.common_edit_text_clear);
			updateBean.setRightSecondImgSettings(imgSetting);

			final  int postion=i;
			updateBean.setOnItemListener(new IItemView.onItemClick() {
				@Override
				public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
//					DownLoadBean item =(DownLoadBean) bean.getBindObject();
//					if (typeEnum==IItemView.ClickTypeEnum.ITEM){
////						 if (item.getFileSize()==0){
//////						 	 updateBean.setData(item.get);
//////						iVideoHomeView.openVideoBySelf(updateBean);
////
////						 }else{
//							 init(true);
//							 iVideoHomeView.startDownload(item.getTaskId());
////						 }
//
//					}
//					else if (typeEnum==IItemView.ClickTypeEnum.ITEM_LONG){
////						 if (bean.getF)
////						iVideoHomeView.openVideoBySelf(updateBean);
//					}
//					else if (typeEnum==IItemView.ClickTypeEnum.RIGHT_SECOND_IMG){
//
//						videoList.remove(postion);
//						dao.deleteByKey(bean.getId());
//						init(false);
//
//
//					}
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

//		if(!TextUtils.isEmpty(txt) ){
//			long taskId = 0;
//			try {
//				taskId = helper.addThunderTask(txt,DOWNLOAD_PATH,null);
//				//UUID uuid = UUID.randomUUID();
////					String uniqueId = uuid.toString();
//				DownLoadBean dbItem=dao.load(taskId+"");
//				 if (dbItem==null){
//					 DownLoadBean downLoadBean=new DownLoadBean();
//					 downLoadBean.setId(taskId+"");
//					 downLoadBean.setTaskId(taskId);
//					 downLoadBean.setName(helper.getTaskInfo(taskId).mFileName);
//					 dao.insert(downLoadBean);
//				 }
//				init(true);
//				iVideoHomeView.startDownload(taskId);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//
////			handler.sendMessage(handler.obtainMessage(0,taskId));
//		}


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
				itemBean.setShowLeftCheckBox(mIsCanSelect);
//				itemBean.setSelectType(settingSection.getId());
//				itemBean.setOnItemClickAble(!mIsCanSelect);
			}
		}
		iVideoHomeView.initUI(settingSection);
		return   mIsCanSelect;
	}


//	public void setHide(  List<SelectBean>  selectBeanList) {
//		List<DyItemBean>  resourceList=settingSection.getDataMaps();
//		List<DyItemBean>  newList=new ArrayList<>();
//		List<SelectBindBean>  chaneList=new ArrayList<>();
//		List<VideoDB>  chaneDbList=new ArrayList<>();
//		for (SelectBean itemBean:selectBeanList) {
//			String selectId=    itemBean.getId();
//			File   oldeFile=new File(selectId);
//			String newPathName=oldeFile.getAbsolutePath().substring(0,oldeFile.getAbsolutePath().lastIndexOf("/")+1);
//			File   newFile=new File(newPathName+"."+oldeFile.getName()+".hide");
//			boolean isSucess=oldeFile.renameTo(newFile);
//			if (isSucess){
//				SelectBindBean bindBean=new SelectBindBean();
//				bindBean.setId(itemBean.getId());
//				bindBean.setHidePath(newFile.getAbsolutePath());
//				chaneList.add(bindBean);
//			}
//		}
//		for (DyItemBean  oldItem :resourceList ) {
//			boolean isHide=false;
//			for (SelectBindBean  itemSelectBean:chaneList ) {
//
//				if (itemSelectBean.getId().equals(oldItem.getId())){
//					isHide=true;
//					VideoDB   videoDBItem=new VideoDB();
//					videoDBItem.setName(oldItem.getTitle());
//					videoDBItem.setDurationString(oldItem.getHint());
//					videoDBItem.setOldFilePath(oldItem.getId());
//					videoDBItem.setData(itemSelectBean.getHidePath());
//					chaneDbList.add(videoDBItem);
//					UUID uuid = UUID.randomUUID();
//					String uniqueId = uuid.toString();
//					videoDBItem.setId(uniqueId);
//					downLoadBeanDao.insert(videoDBItem);
//					break;
//				}
//			}
//			if (!isHide){
//				newList.add(oldItem);
//			}
//		}
//		settingSection.setDataMaps(newList);
//		iVideoHomeView.initUI(settingSection);
//		BusinessBroadcastUtils.sendBroadcast(iVideoHomeView.getContext(), BusinessBroadcastUtils.TYPE_REFRESH_VIDEO_HIDE, null);
//	}
}
