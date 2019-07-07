package com.ui.dict;

import com.core.base.GlobalConstants;
import com.core.db.greenDao.entity.Dict;
import com.core.db.greenDao.gen.DictDao;
import com.easysoft.utils.lib.system.ThreadPoolUtils;
import com.linlsyf.area.R;

import java.util.List;


public class DictHomePresenter {
	IdictHomeView idictHomeView;
	private DictDao mDictDao;
	private boolean isIniting;

	public DictHomePresenter(IdictHomeView iSafeSettingView) {
    	this.idictHomeView =iSafeSettingView;
	}
//    public void  initData(){
//		Section settingSection=new Section("");
//		List<IDyItemBean> settingMaps=settingSection.getDataMaps();
//		DictBusBean  spliteDyItemBean=new DictBusBean();
//		spliteDyItemBean.setOnItemListener(new IItemView.onItemClick() {
//			@Override
//			public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
//				if (clickTypeEnum== IItemView.ClickTypeEnum.ITEM){
////					initAssets();
//					//idictHomeView.startStudy();
//				}
//				else  if((clickTypeEnum== IItemView.ClickTypeEnum.ITEM_LONG)){
//					idictHomeView.toYuePing();
//				}
//				else  if((clickTypeEnum== IItemView.ClickTypeEnum.CUSTOM)){
//					BusinessBroadcastUtils.sendBroadcast(idictHomeView.getContext(),BusinessBroadcastUtils.Type_Local_HOME_PAGE_CHANGE,1);
//				}
//				else{
//					idictHomeView.toTranslate();
//				}
//
//			}
//		});
//
//		settingMaps.add(spliteDyItemBean);
//    settingSection.setAutoAddSpliteLine(false);
//	idictHomeView.initUI(settingSection);
//	}
	public boolean initAssets() {
		mDictDao = GlobalConstants.getInstance().getDaoSession().getDictDao();

		List<Dict>  dictList =   mDictDao.loadAll();
		if (dictList.size()>0) {
//			idictHomeView.startStudy();
			return true;
		}

		if (isIniting){
			return false;
		}
		isIniting=true;
			idictHomeView.showToast(idictHomeView.getContext().getString(R.string.wait_dict_init_please));

		ThreadPoolUtils.execute(new Runnable() {
			@Override
			public void run() {
				DictBeanUtils.iniDbFile(idictHomeView, new DictBeanUtils.parseDictcallback() {
					@Override
					public void parseDataBack(Object list) {
						isIniting=false;

						idictHomeView.showToast(idictHomeView.getContext().getString(R.string.init_data_suceess));

//						idictHomeView.startStudy();
					}

					@Override
					public void showMsg(String msg) {
						idictHomeView.showToast(msg);
						isIniting=false;
					}
				});

			}
		});

		 return false;

	}

}
