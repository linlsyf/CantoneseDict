package com.ui.dict.search;

import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.core.db.greenDao.entity.Dict;
import com.core.db.greenDao.gen.DictDao;
import com.core.db.greenDao.gen.SentenceYyDao;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.linlsyf.area.R;
import com.ui.HttpService;
import com.ui.dict.DictTypeEnum;
import com.ui.dict.IdictView;
import com.utils.StringUtilsArea;
import com.utils.TimeAreaUtils;

import java.util.ArrayList;
import java.util.List;


public class SearchDictPresenter {
	private  SentenceYyDao mSentenceYyDao;
	HttpService service;
	IdictView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private DictDao mDictDao;
	int type=DictTypeEnum.TODAY.value();
	private boolean mIsCanSelect=false;
	public static String ID_NEWS="ID_FILMS";
	public static String ID_SEARCH="ID_TV_FILM";
	public static String ID_TV="ID_TV";
	public static String ID_HIDE="ID_HIDE";
	public static String ID_BTDOWNLOAD="ID_BTDOWNLOAD";
	public static String ID_EMPTY="ID_EMPTY";
	boolean isLoadDictSucess=false;
	String searchKey;



	public SearchDictPresenter(IdictView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		mDictDao = CoreApplication.getInstance().getDaoSession().getDictDao();

	}

	public void  initData(){
		List<Dict>  dictList =new ArrayList<>();
		if (type== DictTypeEnum.MARK.value()){

			dictList =	 mDictDao.queryBuilder().where(DictDao.Properties.Status.eq(2)).limit(100).orderDesc(DictDao.Properties.Count).list();
		}

		else if (type== DictTypeEnum.TODAY.value()){
			String date =  TimeAreaUtils.getToDayString();//获取今天天日期
			dictList =	 mDictDao.queryBuilder().where(DictDao.Properties.ModifyTime.like("%" +date+ "%")).orderAsc(DictDao.Properties.ModifyTime).list();

		}
		else if (type== DictTypeEnum.YESDAY.value()){
			String yesday= TimeAreaUtils.getYesDayString();//获取昨天日期
			dictList =	 mDictDao.queryBuilder().where(DictDao.Properties.ModifyTime.like("%" +yesday+ "%")).orderAsc(DictDao.Properties.ModifyTime).list();
		}

		settingSection=new Section(KEY_SETTING);
		settingSection.setShowSection(true);
		List<IDyItemBean> settingMaps=new ArrayList<>();
       int i=0;
		for (final Dict dict:dictList ) {
			if (i > 1) {
				VideoBussinessItem splitBean = new VideoBussinessItem();
				splitBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
				settingMaps.add(splitBean);
			}
			i=i+1;
			final  VideoBussinessItem hideBean=new VideoBussinessItem();

			hideBean.setTitle(dict.getName());
			hideBean.setHint("粤语："+dict.getTranName()+"  粤拼："+dict.getTranPy());
			hideBean.setHintShow(true);
			hideBean.setRightFirstButtonText("标记已学");
			hideBean.getRightCenterScaleImgSettings().setRightCenterScaleImgResId(R.drawable.ic_filled_star);

			hideBean.setOnItemListener(new IItemView.onItemClick() {
				@Override
				public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
					if (typeEnum.value()==IItemView.ClickTypeEnum.RIGHTBUTTION.value()){
						dict.setStatus(1);
						mDictDao.update(dict);
						initData();
					}
					else if (typeEnum.value()==IItemView.ClickTypeEnum.RIGHT_SCALE_CENTER_IMG.value()){

						dict.setCount(dict.getCount()+1);
						iVideoHomeView.showToast("已记录点击次数+1,排序将会靠前");
						mDictDao.update(dict);
						initData();
					}
					else if (typeEnum.value()==IItemView.ClickTypeEnum.ITEM_LONG.value()){

						StringUtilsArea.copy(iVideoHomeView.getContext(),dict.getTranName());
						iVideoHomeView.showToast("已复制");

					}

					else {
						hideBean.setBindObject(dict);
						iVideoHomeView.showType(hideBean,DictTypeEnum.LJ.value());
					}


				}
			});

			settingMaps.add(hideBean);
		}

		settingSection.setDataMaps(settingMaps);
		iVideoHomeView.initUI(settingSection);
	}

	public void initAssets() {
		List<Dict>  dictList =   mDictDao.loadAll();
		  if (dictList.size()>0) {
			  isLoadDictSucess=true;
		   return;
		  }

	}

	public void searchByGY(String text) {
		List<Dict> dictlist = mDictDao.queryBuilder().where(DictDao.Properties.Name.like("%" +text+ "%")).list();

		if (!isLoadDictSucess){
		  	iVideoHomeView.showToast(iVideoHomeView.getContext().getString(R.string.wait_dict_init_please));
		  }else {
			  if (dictlist.size()==0){
				  iVideoHomeView.showToast(iVideoHomeView.getContext().getString(R.string.search_null));
			  }else{
				  List<IDyItemBean> settingMaps=new ArrayList<>();
                 int i=0;
				  for (Dict dict:dictlist ) {
				  	  if (i>0){
						    VideoBussinessItem splitBean=new VideoBussinessItem();
						    splitBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
						  settingMaps.add(splitBean);
					  }
				  	i=i+1;
					  final  VideoBussinessItem hideBean=new VideoBussinessItem();

					  hideBean.setId(dict.getName());
					  hideBean.setTitle(dict.getName());
					  hideBean.setHint(dict.getTranName()+"  "+dict.getTranPy());
					  hideBean.setHintShow(true);
					  settingSection=new Section(KEY_SETTING);
					  settingSection.setShowSection(false);
					  settingMaps.add(hideBean);
				  }

				  settingSection.setDataMaps(settingMaps);
				  iVideoHomeView.initUI(settingSection);
			  }
		  }



	}

	public void setType(int type) {
		this.type = type;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
}
