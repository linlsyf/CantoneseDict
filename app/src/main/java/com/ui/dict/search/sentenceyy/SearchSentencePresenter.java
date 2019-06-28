package com.ui.dict.search.sentenceyy;

import com.business.bean.VideoBussinessItem;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.entity.SentenceYy;
import com.core.db.greenDao.gen.SentenceYyDao;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.ui.HttpService;

import java.util.ArrayList;
import java.util.List;


public class SearchSentencePresenter {
	private  SentenceYyDao mSentenceYyDao;
	HttpService service;
	ISearchSentenceView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
//	private DictDao mDictDao;
	int type=0;
	public static String ID_NEWS="ID_FILMS";
	public static String ID_SEARCH="ID_TV_FILM";
	public static String ID_TV="ID_TV";
	public static String ID_HIDE="ID_HIDE";
	public static String ID_BTDOWNLOAD="ID_BTDOWNLOAD";
	public static String ID_EMPTY="ID_EMPTY";
	String searchKey;

	public SearchSentencePresenter(ISearchSentenceView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();

	}

	public void  initData(){
		List<SentenceYy>  dictList =new ArrayList<>();
			mSentenceYyDao = GlobalConstants.getInstance().getDaoSession().getSentenceYyDao();
		   searchKey= searchKey.replace("\"", "").trim();
		  String[] searchKeys=new String[]{};
		  if (searchKey.contains(",")){
			  searchKeys=searchKey.split(",");
		  }
		if (searchKey.contains("，")){
			searchKeys=searchKey.split("，");
		}
		  if (searchKeys.length>=2){
		  	 int i=0;
			  for (String key:searchKeys  ) {
				  List<SentenceYy>	  list =	 mSentenceYyDao.queryBuilder().where(SentenceYyDao.Properties.Name.like("%" +searchKeys[i].trim()+ "%")) .list();
                      i=i+1 ;
				  dictList.addAll(list);
			  }

		  }else{
			  dictList =	 mSentenceYyDao.queryBuilder().where(SentenceYyDao.Properties.Name.like("%" +searchKey+ "%")).list();

		  }


		settingSection=new Section(KEY_SETTING);
		settingSection.setShowSection(false);
		List<IDyItemBean> settingMaps=new ArrayList<>();
       int i=0;
		for (final SentenceYy dict:dictList ) {
			if (i > 1) {
				VideoBussinessItem splitBean = new VideoBussinessItem();
				splitBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
				settingMaps.add(splitBean);
			}
			i=i+1;
			final  VideoBussinessItem hideBean=new VideoBussinessItem();

			hideBean.setTitle(dict.getName());
			hideBean.setHint("粤语："+dict.getTranName());
			hideBean.setHintShow(true);

//			hideBean.setRightFirstButtonText("标记已学");
//			hideBean.setOnItemListener(new IItemView.onItemClick() {
//				@Override
//				public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
//
//				}
//			});


			settingMaps.add(hideBean);
		}

		settingSection.setDataMaps(settingMaps);
		iVideoHomeView.initUI(settingSection);
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
}
