package com.ui.dict.dicttype;

import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.entity.DictType;
import com.core.db.greenDao.gen.DictDao;
import com.core.db.greenDao.gen.DictTypeDao;
import com.core.db.greenDao.gen.SentenceYyDao;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.linlsyf.cantonese.R;
import com.ui.HttpService;
import com.ui.dict.DictTypeEnum;

import java.util.ArrayList;
import java.util.List;


public class DictTypePresenter {
	private  SentenceYyDao mSentenceYyDao;
	HttpService service;
	IdictTypeView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private DictTypeDao mDictTypeDao;
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



	public DictTypePresenter(IdictTypeView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		mDictTypeDao = GlobalConstants.getInstance().getDaoSession().getDictTypeDao();

	}

	public void  initData(){
		List<DictType>  dictList =	 mDictTypeDao.queryBuilder().list();

		settingSection=new Section(KEY_SETTING);
		settingSection.setShowSection(true);
		List<IDyItemBean> settingMaps=new ArrayList<>();
       int i=0;
		for (final DictType dict:dictList ) {
			if (i > 1) {
				VideoBussinessItem splitBean = new VideoBussinessItem();
				splitBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
				settingMaps.add(splitBean);
			}
			i=i+1;
			final  VideoBussinessItem hideBean=new VideoBussinessItem();

			hideBean.setTitle(dict.getName());

			hideBean.setOnItemListener(new IItemView.onItemClick() {
				@Override
				public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {

            iVideoHomeView.showType(hideBean,DictTypeEnum.SEARCH.value());


				}
			});

			settingMaps.add(hideBean);
		}

		settingSection.setDataMaps(settingMaps);
		iVideoHomeView.initUI(settingSection);
	}


	public void searchByGY(String text) {
		List<DictType> dictlist = mDictTypeDao.queryBuilder().where(DictDao.Properties.Name.like("%" +text+ "%")).list();

		if (!isLoadDictSucess){
		  	iVideoHomeView.showToast(iVideoHomeView.getContext().getString(R.string.wait_dict_init_please));
		  }else {
			  if (dictlist.size()==0){
				  iVideoHomeView.showToast(iVideoHomeView.getContext().getString(R.string.search_null));
			  }else{
				  List<IDyItemBean> settingMaps=new ArrayList<>();
                 int i=0;
				  for (DictType dict:dictlist ) {
				  	  if (i>0){
						    VideoBussinessItem splitBean=new VideoBussinessItem();
						    splitBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
						  settingMaps.add(splitBean);
					  }
				  	i=i+1;
					  final  VideoBussinessItem hideBean=new VideoBussinessItem();

					  hideBean.setId(dict.getName());
					  hideBean.setTitle(dict.getName());
					  //hideBean.setHint(dict.getTranName()+"  "+dict.getTranPy());
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
