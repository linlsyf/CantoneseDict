package com.ui.dict.start;

import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.core.db.greenDao.entity.Dict;
import com.core.db.greenDao.gen.DictDao;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.DyItemBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easysoft.utils.lib.system.TimeUtils;
import com.linlsyf.area.R;
import com.ui.HttpService;
import com.ui.dict.DictBusBean;
import com.ui.dict.DictTypeEnum;
import com.utils.RecycleHelper;

import java.util.ArrayList;
import java.util.List;


public class DictStudyPresenter {
	HttpService service;
	IStartView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private DictDao mDictDao;
	public static String ID_NEWS="ID_FILMS";
	public static String ID_SEARCH="ID_TV_FILM";
	public static String ID_TV="ID_TV";
	public static String ID_HIDE="ID_HIDE";
	public static String ID_BTDOWNLOAD="ID_BTDOWNLOAD";
	public static String ID_EMPTY="ID_EMPTY";
	boolean isLoadDictSucess=false;
	private boolean isIniting;
	public Dict	mEditDict;
	private int countRead=0;

	public DictStudyPresenter(IStartView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		mDictDao = CoreApplication.getInstance().getDaoSession().getDictDao();
	}
    public void  initData(){
     List<Dict>  dictList ;

      if (countRead==0){
		  dictList = mDictDao.queryBuilder().where(DictDao.Properties.Status.eq(0),DictDao.Properties.Count.eq(countRead)).orderAsc(DictDao.Properties.Id).orderDesc(DictDao.Properties.TranPy).limit(8).list();
	  }else  if(countRead==10){
		  dictList = mDictDao.queryBuilder().where(DictDao.Properties.Status.eq(1),DictDao.Properties.Count.ge(1),DictDao.Properties.Count.le(10)).orderAsc(DictDao.Properties.Id).orderDesc(DictDao.Properties.TranPy).limit(8).list();
	  }
	  else{
		  dictList = mDictDao.queryBuilder().where(DictDao.Properties.Status.eq(1),DictDao.Properties.Count.gt(10)).orderAsc(DictDao.Properties.Id).orderDesc(DictDao.Properties.TranPy).limit(8).list();
	  }

		settingSection=new Section(KEY_SETTING);
//		settingSection.setShowSection(true);
		List<IDyItemBean> settingMaps=new ArrayList<>();

		final DictBusBean dictBusBean=new DictBusBean();

		dictBusBean.setViewType(3);
		dictBusBean.setOnItemListener(new IItemView.onItemClick() {
			@Override
			public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				if (typeEnum== IItemView.ClickTypeEnum.ITEM){
					iVideoHomeView.showType(null,DictTypeEnum.YESDAY.value());
				}

				else if (typeEnum== IItemView.ClickTypeEnum.RIGHTBUTTION){
					iVideoHomeView.showType(null,DictTypeEnum.TODAY.value());
				}
			}
		});

		final  VideoBussinessItem msgBean=new VideoBussinessItem();

		msgBean.setHintShow(true);
		msgBean.setOnItemListener(new IItemView.onItemClick() {
			@Override
			public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
				if (typeEnum.value()==IItemView.ClickTypeEnum.ITEM.value()){
					iVideoHomeView.showType(null,msgBean.getViewType());

				}
			}
		});

		DyItemBean sectionBean=new DyItemBean();
		sectionBean.setViewType(IItemView.ViewTypeEnum.SECTION.value());
		sectionBean.setTitle("点击查看例句，长按编辑");
//		sectionBean.setsho
//		sectionBean.setTitle("随机待学"+dictList.size()+"个新词");

		settingMaps.add(sectionBean);
		int i=0;
		for (final Dict dict:dictList ) {

			i=i+1;
			final  VideoBussinessItem hideBean=new VideoBussinessItem();

			hideBean.setTitle(dict.getName());
			hideBean.setHint("粤语："+dict.getTranName()+"\n粤拼："+dict.getTranPy() +"\n次数："+dict.getCount());
			hideBean.setHintShow(true);
			hideBean.setRightFirstButtonText("标记已学");
			hideBean.getRightCenterScaleImgSettings().setRightCenterScaleImgResId(R.drawable.ic_filled_star);
			hideBean.setOnItemListener(new IItemView.onItemClick() {

				@Override
				public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
					if (typeEnum.value()==IItemView.ClickTypeEnum.RIGHTBUTTION.value()){
						dict.setModifyTime(TimeUtils.getCurrentTime());
						dict.setStatus(1);
						mDictDao.update(dict);
						initData();
					}
					else if (typeEnum.value()==IItemView.ClickTypeEnum.RIGHT_SCALE_CENTER_IMG.value()){
						dict.setModifyTime(TimeUtils.getCurrentTime());
						dict.setCount(1);
						dict.setStatus(2);
						mDictDao.update(dict);
						initData();
					}
					else if (typeEnum== IItemView.ClickTypeEnum.ITEM_LONG){
						longclick(dict);
						//iVideoHomeView.showType(null,DictTypeEnum.YESDAY.value());
					}
					else if (typeEnum.value()==IItemView.ClickTypeEnum.ITEM.value()){
						hideBean.setBindObject(dict);
						iVideoHomeView.showType(hideBean,DictTypeEnum.SEARCH.value());
					}

				}
			});

			settingMaps.add(hideBean);
		}

    settingSection.setDataMaps(RecycleHelper.wrappingList(settingMaps));
	iVideoHomeView.initUI(settingSection);
	}

	private void longclick(Dict dict) {
		mEditDict=dict;

		List<DyItemBean> dataList=new ArrayList<>();


		DyItemBean   itemBeanEdit=new DyItemBean();
		itemBeanEdit.setId(DictDao.Properties.Name.columnName.toLowerCase());
		itemBeanEdit.setTitle("普通");
		itemBeanEdit.setHint(dict.getName());
		dataList.add(itemBeanEdit);
		DyItemBean   itemBeanTran=new DyItemBean();
		itemBeanTran.setId(DictDao.Properties.TranName.columnName.toLowerCase());
		itemBeanTran.setTitle("粤语");
		itemBeanTran.setHint(dict.getTranName());
		dataList.add(itemBeanTran);

		DyItemBean   itemBeanTranPy=new DyItemBean();
		itemBeanTranPy.setId(DictDao.Properties.TranPy.columnName.toLowerCase());
		itemBeanTranPy.setTitle("粤拼");
		itemBeanTranPy.setHint(dict.getTranPy());
		dataList.add(itemBeanTranPy);


		DyItemBean   itemBeanTranCount=new DyItemBean();
		itemBeanTranCount.setId(DictDao.Properties.Count.columnName.toLowerCase());
		itemBeanTranCount.setTitle("查看次数");
		itemBeanTranCount.setHint(dict.getCount()+"");
		dataList.add(itemBeanTranCount);

		iVideoHomeView.editType(dataList);
	}


	public void searchByGY(final  String text) {
		List<Dict> dictlist = mDictDao.queryBuilder().where(DictDao.Properties.Name.like("%" +text+ "%")).list();

		if (!isLoadDictSucess){
		  	iVideoHomeView.showToast(iVideoHomeView.getContext().getString(R.string.wait_dict_init_please));
		  }else {
			  if (dictlist.size()==0){
				  iVideoHomeView.showToast(iVideoHomeView.getContext().getString(R.string.search_null));
			  }else{
				  settingSection=new Section(KEY_SETTING);
				  settingSection.setShowSection(false);
				  List<IDyItemBean> settingMaps=new ArrayList<>();
                 int i=0;
				  for (final Dict dict:dictlist ) {
				  	  if (i>0){
						    VideoBussinessItem splitBean=new VideoBussinessItem();
						    splitBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
						  settingMaps.add(splitBean);
					  }
				  	i=i+1;
					  final  VideoBussinessItem hideBean=new VideoBussinessItem();

					  hideBean.setTitle(dict.getName());
					  hideBean.setHint(dict.getTranName()+"  "+dict.getTranPy());
					  hideBean.setRightFirstButtonText("删除");
					  hideBean.setHintShow(true);
					  hideBean.setOnItemListener(new IItemView.onItemClick() {
						  @Override
						  public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {



							  if (clickTypeEnum.value()==IItemView.ClickTypeEnum.RIGHTBUTTION.value()){
								  mDictDao.delete(dict);
								  searchByGY( text);
								  iVideoHomeView.showToast("删除成功");
							  }
							  if (clickTypeEnum== IItemView.ClickTypeEnum.ITEM_LONG){
                                  longclick(dict);

							  }

						  }
					  });

					  settingMaps.add(hideBean);
				  }


//				   dictlist.stream().filter(e -> )
				  settingSection.setDataMaps(settingMaps);
				  iVideoHomeView.initUI(settingSection);
			  }
		  }

	}


	public void updateMsg(List<IDyItemBean> itemBeans) {

				for(IDyItemBean itemBean:itemBeans){
					DyItemBean dyItemBean=(DyItemBean)itemBean;
					if (itemBean.getId().equals(DictDao.Properties.Name.columnName.toLowerCase())){
						mEditDict.setName(dyItemBean.getEidtSettings().getEditContent());
					}
					else if (itemBean.getId().equals(DictDao.Properties.TranName.columnName.toLowerCase())){
						mEditDict.setTranName(dyItemBean.getEidtSettings().getEditContent());
					}
					else if (itemBean.getId().equals(DictDao.Properties.TranPy.columnName.toLowerCase())){
						mEditDict.setTranPy(dyItemBean.getEidtSettings().getEditContent());
					}
					else if (itemBean.getId().equals(DictDao.Properties.Count.columnName.toLowerCase())){
						int count=Integer.parseInt(dyItemBean.getEidtSettings().getEditContent());
						mEditDict.setCount(count);
					}
				}
		mDictDao.update(mEditDict);
		initData();

	}

    public  List<DyItemBean> getReadListCount() {
		List<DyItemBean> readList=new ArrayList<>();

			 DyItemBean itemBeanNone=new DyItemBean();
			 itemBeanNone.setId(0+"");
			 itemBeanNone.setTitle("未学");
			 readList.add(itemBeanNone);
			 DyItemBean itemBeanBetween=new DyItemBean();
		  itemBeanBetween.setId(10+"");
		  itemBeanBetween.setTitle("1-10次");
			 readList.add(itemBeanBetween);




		DyItemBean itemBeanTenAbove=new DyItemBean();
		itemBeanTenAbove.setId("11");
		itemBeanTenAbove.setTitle("大于10次");
		//itemBeanTenAbove.setHint("11");
		readList.add(itemBeanTenAbove);

        return readList;
    }



	public void chaneReadCount(List<IDyItemBean> itemBeans) {
		IDyItemBean  itemBean=  itemBeans.get(0);
		 countRead=Integer.parseInt(itemBean.getId());
//		 initData();

	}
}
