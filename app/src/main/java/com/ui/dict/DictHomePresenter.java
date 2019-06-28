package com.ui.dict;

import com.business.bean.VideoBussinessItem;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.entity.Dict;
import com.core.db.greenDao.gen.DictDao;
import com.core.db.greenDao.gen.SentenceYyDao;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.linlsyf.area.R;
import com.ui.HttpService;
import com.utils.TimeAreaUtils;

import java.util.ArrayList;
import java.util.List;


public class DictHomePresenter {
	HttpService service;
	IdictHomeView idictHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private DictDao mDictDao;

	private boolean isIniting;
	SentenceYyDao sentenceYyDao;

	public DictHomePresenter(IdictHomeView iSafeSettingView) {
    	this.idictHomeView =iSafeSettingView;
		service=new HttpService();
		mDictDao = GlobalConstants.getInstance().getDaoSession().getDictDao();
		sentenceYyDao = GlobalConstants.getInstance().getDaoSession().getSentenceYyDao();


	}
    public void  initData(){
		initAssets();
		long readedCount = mDictDao.queryBuilder().where(DictDao.Properties.Status.eq(1)).count();
		long   notReadCount = mDictDao.queryBuilder().where(DictDao.Properties.Status.eq(0)).count();
		long   collectCount = mDictDao.queryBuilder().where(DictDao.Properties.Status.eq(2)).count();

		String date =TimeAreaUtils.getToDayString();

		long   todayCount = mDictDao.queryBuilder().where(DictDao.Properties.ModifyTime.like("%" +date+ "%")).count();
		long   totalCount = mDictDao.count();
		String yesday= TimeAreaUtils.getYesDayString();
		long  yesdayCount =	 mDictDao.queryBuilder().where(DictDao.Properties.ModifyTime.like("%" +yesday+ "%")).orderAsc(DictDao.Properties.ModifyTime).count();
		settingSection=new Section(KEY_SETTING);
		List<IDyItemBean> settingMaps=new ArrayList<>();

		final DictBusBean dictBusBean=new DictBusBean();
		dictBusBean.setUnread(yesdayCount);
		dictBusBean.setToday(todayCount);
		dictBusBean.setViewType(3);
		dictBusBean.setOnItemListener(new IItemView.onItemClick() {
			@Override
			public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				if (typeEnum== IItemView.ClickTypeEnum.ITEM){
					idictHomeView.showType(null,DictTypeEnum.YESDAY.value());
				}
				else if (typeEnum== IItemView.ClickTypeEnum.RIGHTBUTTION){
					idictHomeView.showType(null,DictTypeEnum.TODAY.value());
				}
				else if (typeEnum== IItemView.ClickTypeEnum.ITEM_LONG){
					idictHomeView.showAllLearn();
				}
				else if (typeEnum== IItemView.ClickTypeEnum.RIGHT_SCALE_CENTER_IMG){
					idictHomeView.showType(null,typeEnum.value());
				}
			}
		});

		final  VideoBussinessItem msgBean=new VideoBussinessItem();
		String percent="";
	 String 	msg ="";

	 long  readCount=readedCount+collectCount;


		dictBusBean.setTotalMsg("成语总数:"+totalCount+" 未学："+notReadCount);
		dictBusBean.setTotalLearnMsg("已学："+readCount +"  收藏:"+collectCount+" [点击查看收藏]");

		settingMaps.add(dictBusBean);


		DictBusBean  spliteDyItemBean=new DictBusBean();
		spliteDyItemBean.setItemHight(200);
		  spliteDyItemBean.setViewType(7);
		spliteDyItemBean.setOnItemListener(new IItemView.onItemClick() {
			@Override
			public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {

				if (clickTypeEnum== IItemView.ClickTypeEnum.ITEM){
					idictHomeView.startStudy();
				}else  if((clickTypeEnum== IItemView.ClickTypeEnum.ITEM_LONG)){
					idictHomeView.toYuePing();
				}
				else{
					idictHomeView.toTranslate();
				}


			}
		});

		settingMaps.add(spliteDyItemBean);


    settingSection.setDataMaps(settingMaps);
    settingSection.setAutoAddSpliteLine(false);
	idictHomeView.initUI(settingSection);
	}
	public void initAssets() {
		List<Dict>  dictList =   mDictDao.loadAll();
		  if (dictList.size()>0) {
		   return;
		  }

		  if (isIniting==false){
			  idictHomeView.showToast(idictHomeView.getContext().getString(R.string.wait_dict_init_please));
			  isIniting=true;
			  idictHomeView.showToast(idictHomeView.getContext().getString(R.string.wait_dict_init_please));

			  DictBeanUtils.iniDbFile(idictHomeView);
//			  DictBeanUtils.copyDbFile(idictHomeView.getContext());
//			  DictBeanUtils.initDb(idictHomeView.getContext(), new DictBeanUtils.parseDictcallback() {
//				  @Override
//				  public void parseDataBack(Object obj) {
////				  	DictBeanUtils.initLJ(idictHomeView.getContext(),);
//				  }
//
//				  @Override
//				  public void showMsg(String msg) {
//					  idictHomeView.showToast(msg);
//
//				  }
//			  });


		  }

	}

}
