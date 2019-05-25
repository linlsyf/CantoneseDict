package com.ui.dict.yueping;

import android.content.Intent;
import android.net.Uri;

import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.core.db.greenDao.entity.Dict;
import com.core.db.greenDao.gen.DictDao;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.AddressHeadImgeSettings;
import com.easy.recycleview.custom.bean.DyItemBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.linlsyf.area.R;
import com.ui.HttpService;
import com.ui.dict.DictBeanUtils;
import com.ui.dict.DictBusBean;

import java.util.ArrayList;
import java.util.List;


public class DictYuePinyresenter {
	HttpService service;
	IDictYuePiny iVideoHomeView;
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

	public DictYuePinyresenter(IDictYuePiny iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		mDictDao = CoreApplication.getInstance().getDaoSession().getDictDao();
	}
    public void  initData(){
		initAssets();
		settingSection=new Section(KEY_SETTING);
//		settingSection.setShowSection(true);
		List<IDyItemBean> settingMaps=new ArrayList<>();

		final DictBusBean dictNoticeBean=new DictBusBean();
		dictNoticeBean.setTitle(iVideoHomeView.getContext().getString(R.string.notice_yy));
		dictNoticeBean.setOnItemListener(new IItemView.onItemClick() {
			@Override
			public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {


				String url="http://www.fyan8.com/";
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				iVideoHomeView.getContext().startActivity(intent);
			}
		});
		settingMaps.add(dictNoticeBean);

		final DictBusBean dictBusBean=new DictBusBean();
		dictBusBean.setTitle(iVideoHomeView.getContext().getString(R.string.yp_sm));
		dictBusBean.setViewType(IItemView.ViewTypeEnum.SECTION.value());
//		dictBusBean.setOnItemListener(new IItemView.onItemClick() {
//			@Override
//			public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
////				if (typeEnum== IItemView.ClickTypeEnum.ITEM){
////					iVideoHomeView.showType(null,DictTypeEnum.YESDAY.value());
////				}
////
////				else if (typeEnum== IItemView.ClickTypeEnum.RIGHTBUTTION){
////					iVideoHomeView.showType(null,DictTypeEnum.TODAY.value());
////				}
//			}
//		});
		settingMaps.add(dictBusBean);


		final DictBusBean dictPgBean=new DictBusBean();
		dictPgBean.setViewType(4);
		AddressHeadImgeSettings headImgeSettings=new AddressHeadImgeSettings();
		headImgeSettings.setHeadImgDrawableId(R.drawable.yueysm);
//		headImgeSettings.setHeadImgRadius(DensityUtil.dip2px(iVideoHomeView.getContext(),200));
		dictPgBean.setHeadImgeSettings(headImgeSettings);
		settingMaps.add(dictPgBean);


		final DictBusBean dictYmBean=new DictBusBean();
		dictYmBean.setTitle(iVideoHomeView.getContext().getString(R.string.yp_ym));
		dictYmBean.setViewType(IItemView.ViewTypeEnum.SECTION.value());
		settingMaps.add(dictYmBean);

		final DictBusBean dictymPgBean=new DictBusBean();
		dictymPgBean.setViewType(4);
		 headImgeSettings=new AddressHeadImgeSettings();
		headImgeSettings.setHeadImgDrawableId(R.drawable.yueyuym);
//		headImgeSettings.setHeadImgRadius(DensityUtil.dip2px(iVideoHomeView.getContext(),250));
		dictymPgBean.setHeadImgeSettings(headImgeSettings);
		settingMaps.add(dictymPgBean);
		//settingMaps

		final DictBusBean dictsdBean=new DictBusBean();
		dictsdBean.setTitle(iVideoHomeView.getContext().getString(R.string.yp_sd));
		dictsdBean.setViewType(IItemView.ViewTypeEnum.SECTION.value());
		settingMaps.add(dictsdBean);


		final DictBusBean dictysdBean=new DictBusBean();
		dictysdBean.setViewType(4);
		headImgeSettings=new AddressHeadImgeSettings();
		headImgeSettings.setHeadImgDrawableId(R.drawable.yysd);
//		headImgeSettings.setHeadImgRadius(DensityUtil.dip2px(iVideoHomeView.getContext(),200));
		dictysdBean.setHeadImgeSettings(headImgeSettings);
		settingMaps.add(dictysdBean);


    settingSection.setDataMaps(settingMaps);
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


	public void initAssets() {
		List<Dict>  dictList =   mDictDao.loadAll();
		  if (dictList.size()>0) {
			  isLoadDictSucess=true;
		   return;
		  }

		  if (isIniting==false){
			  iVideoHomeView.showToast(iVideoHomeView.getContext().getString(R.string.wait_dict_init_please));

			  isIniting=true;

			  iVideoHomeView.showToast(iVideoHomeView.getContext().getString(R.string.wait_dict_init_please));

			  DictBeanUtils.copyDbFile(iVideoHomeView.getContext());
			  DictBeanUtils.initDb(iVideoHomeView.getContext(), new DictBeanUtils.parseDictcallback() {
				  @Override
				  public void parseDataBack(Object obj) {
//					  List<Dict> list= (List<Dict>) obj;
				  }

				  @Override
				  public void showMsg(String msg) {
					  iVideoHomeView.showToast(msg);

				  }
			  });

		  }



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
}
