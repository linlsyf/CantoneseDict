package com.ui.app.tv.cate;

import android.app.Activity;

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
import com.linlsyf.area.R;
import com.ui.HttpService;
import com.ui.app.tv.ITVView;
import com.utils.VideoItem;
import com.utils.VideoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TVPresenter   {
	HttpService service;
	ITVView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private VideoDBDao mVideoDao;
	private boolean mIsCanSelect=false;
	Disposable mDisposable;

	CustomTvDao dao;

	public TVPresenter(ITVView iSafeSettingView) {
		this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		mVideoDao = GlobalConstants.getInstance().getDaoSession().getVideoDBDao();
		dao = GlobalConstants.getInstance().getDaoSession().getCustomTvDao();

	}

	public void init(Activity activity,final  String name){
		settingSection=new Section(KEY_SETTING);
		settingSection.setShowSection(false);


		Observable.create(new ObservableOnSubscribe<  List<VideoItem> >() {
			@Override
			public void subscribe(ObservableEmitter< List<VideoItem> > emitter)
					throws Exception {
				ArrayList<VideoItem>  videoList=new ArrayList<>() ;


				VideoUtils.radTxtFile(GlobalConstants.getInstance().getAppDocumentHomePath()+"/"+name+".txt",videoList);

				VideoItem videoItem2=new VideoItem();
				videoItem2.setData("http://zhibo.hkstv.tv/livestream/zb2yhapo/playlist.m3u8");
				videoItem2.setName("香港台");
				videoList.add(0,videoItem2);





				emitter.onNext(videoList);


			} })
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io()).subscribe(new Observer< List<VideoItem> >() {
			@Override public void onSubscribe(Disposable d) {
				mDisposable=d;
			}
			@Override public void onNext(  List<VideoItem>  videoList) {
				initView( videoList);
			}
			@Override public void onError(Throwable e) {
			}

			@Override public void onComplete() {

			}
		});

	}

	private void initView( List<VideoItem> videoList) {
		List<IDyItemBean> settingMaps=new ArrayList<>();

		int headImgSize= DensityUtil.dip2px(CoreApplication.getAppContext(),80);
		int i=0;
		for (VideoItem item : videoList ) {
			String  path=item.getData();
			final VideoBussinessItem updateBean=new VideoBussinessItem();
			updateBean.setTitle(item.getName());
			updateBean.setId(path);
			updateBean.setData(item.getData());
			updateBean.setThumbPath(item.getThumbPath());
			updateBean.setHint(item.getDurationString());
			updateBean.setHintShow(true);
//			updateBean.setContentBgResid(R.drawable.corners_bg);
			RightSecondImgSettings imgSetting=new RightSecondImgSettings();
			imgSetting.setRightSecondImgRadius(DensityUtil.dip2px(CoreApplication.getAppContext(), 25));

			imgSetting.setRightSecondImgResId(R.drawable.chx_addressmanger_uncheced);
			updateBean.setRightSecondImgSettings(imgSetting);
			updateBean.setOnItemListener(new IItemView.onItemClick() {
				@Override
				public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
					 if (typeEnum==IItemView.ClickTypeEnum.ITEM){
						 iVideoHomeView.openVideoBySelf(updateBean);
					 }
					 else if (typeEnum==IItemView.ClickTypeEnum.RIGHT_SECOND_IMG){
						 CustomTv item=new CustomTv();
							 item.setName(updateBean.getName());
							 item.setUrl(updateBean.getData());
						 Random random=new Random();
						 String id=random.nextLong()+"";
						 item.setId(id);
						 dao.insert(item);//添加一个
						 iVideoHomeView.showToast("添加成功请到自定义界面查看");

					 }
					 else  if (typeEnum==IItemView.ClickTypeEnum.ITEM_LONG) {
						 iVideoHomeView.openVideo(updateBean);
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


	public boolean setCanEdit() {
		List<IDyItemBean>  resourceList=settingSection.getDataMaps();
		mIsCanSelect=!mIsCanSelect;
		for (IDyItemBean itemBean: resourceList ) {
//			if (itemBean.getViewType()== IItemView.ViewTypeEnum.ITEM.value()){
//				itemBean.setItemCanEdit(mIsCanSelect);
//				itemBean.setShowLeftCheckBox(mIsCanSelect);
//				itemBean.setSelectType(settingSection.getId());
//				itemBean.setOnItemClickAble(!mIsCanSelect);
//			}
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
