package com.ui.dict.translate;

import com.alibaba.fastjson.JSON;
import com.business.baidu.translate.SearchParam;
import com.business.baidu.translate.TranSalteKey;
import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.entity.Dict;
import com.core.db.greenDao.entity.SentenceYy;
import com.core.db.greenDao.gen.DictDao;
import com.core.db.greenDao.gen.SentenceYyDao;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easysoft.utils.lib.system.DensityUtil;
import com.easysoft.utils.lib.system.StringUtils;
import com.linlsyf.area.R;
import com.ui.HttpService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class TranslatePresenter {
	HttpService service;
	ITranslateView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private DictDao mDictDao;

	private SentenceYyDao mSentenceYyDao;


	private String  orgType="zh";
	private String  toTranslateType="yue";
	private String tempSeachKey;

	public TranslatePresenter(ITranslateView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		mDictDao = GlobalConstants.getInstance().getDaoSession().getDictDao();
		mSentenceYyDao = GlobalConstants.getInstance().getDaoSession().getSentenceYyDao();

	}
    public void  search(String text){
		if (StringUtils.isEmpty(text)){
			return;
		}
		tempSeachKey=text;
		SearchParam searchParam=new SearchParam(text,orgType,toTranslateType);
		TranSalteKey.toTran(searchParam, new TranSalteKey.translatecallback() {
			@Override
			public void call(Object result) {

				String  wordTran=(String) result;

				TransReponseBean  reponseBean= JSON.parseObject(wordTran,TransReponseBean.class);
				List<TransResultBean>  transResultArrayLis=new ArrayList<>();
				  if (null!=reponseBean.getTrans_result()){
					  transResultArrayLis=	JSON.parseArray(reponseBean.getTrans_result().toString(),TransResultBean.class);

				  }


				settingSection=new Section(KEY_SETTING);
				final List<IDyItemBean> settingMaps=new ArrayList<>();


				if (orgType=="zh"&&toTranslateType=="yue"){
					DyItemBean dyItemBean=new DyItemBean();
					dyItemBean.setViewType(IItemView.ViewTypeEnum.SECTION.value());
					dyItemBean.setTitle("普通话-粤语 搜索结果：");
					settingMaps.add(dyItemBean);
				}
				if (orgType=="yue"&&toTranslateType=="zh"){
					DyItemBean dyItemBean=new DyItemBean();
					dyItemBean.setViewType(IItemView.ViewTypeEnum.SECTION.value());
					dyItemBean.setTitle("粤语-普通话  搜索结果：");
					settingMaps.add(dyItemBean);
				}

//                 List<String>   ljList=new ArrayList<>();
				 String   resultKey="";
				 int i=0;
				for (final TransResultBean itemBean:transResultArrayLis) {

					final DyItemBean dyItemBean=new DyItemBean();

					    resultKey=itemBean.getDst();

					dyItemBean.setTitle(resultKey);
					int voiceIconRadius= DensityUtil.dip2px(iVideoHomeView.getContext(),30);

					dyItemBean.setRightFirstButtonText("保存");
					dyItemBean.getRightFistImgeSettings().setRightFirstImgResId(R.drawable.voice_speak).setRightFirstImgRadius(voiceIconRadius);

					dyItemBean.setOnItemListener(new IItemView.onItemClick() {
						@Override
						public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {



							if (clickTypeEnum.value()==IItemView.ClickTypeEnum.RIGHTBUTTION.value()){
								List<Dict>  dictList =new ArrayList<>();
								String  isPtoYue="0";
								if (	orgType=="zh"&&toTranslateType=="yue"){

									dictList= mDictDao.queryBuilder().where(DictDao.Properties.Name.eq(tempSeachKey)).limit(1).list();

									isPtoYue="1";
								}
								else{
									String  yuyket=dyItemBean.getTitle();
									dictList= mDictDao.queryBuilder().where(DictDao.Properties.Name.eq(yuyket)).limit(1).list();
									isPtoYue="1";
								}
								Dict dict;
								if (dictList.size()>0){
									dict= dictList.get(0);
									dict.setName(dict.getName().replace("\"", ""));
									dict.setCount(dict.getCount()+10);
								}else{
									dict=new Dict();
									dict.setId(UUID.randomUUID().toString());
									if (isPtoYue.equals("1")){
										dict.setName(tempSeachKey);
										dict.setTranName(dyItemBean.getTitle());

									}else{
										dict.setName(dyItemBean.getTitle());
										dict.setTranName(tempSeachKey);

									}
								}
								if (dictList.size()>0){
									mDictDao.update(dict);
								}else{
									mDictDao.insertOrReplaceInTx(dict);
								}
								iVideoHomeView.showToast(CoreApplication.instance.getString(R.string.exec_sucess));
							}
	     else if (clickTypeEnum.value()==IItemView.ClickTypeEnum.RIGHT_FIRST_IMG.value()){
								if (StringUtils.isNotEmpty(dyItemBean.getTitle())){
									String textYuey=dyItemBean.getTitle().replace("\"","");
									iVideoHomeView.speak(textYuey);
								}

							}


						}
					});
					settingMaps.add(dyItemBean);

				}
				DyItemBean dyItemBeanSentence=new DyItemBean();
				dyItemBeanSentence.setViewType(IItemView.ViewTypeEnum.SECTION.value());
				dyItemBeanSentence.setTitle(CoreApplication.instance.getString(R.string.sentence));
				settingMaps.add(dyItemBeanSentence);

				List<SentenceYy>	 sentenceYyList =new ArrayList<>();

				if (orgType=="zh"&&toTranslateType=="yue"){
					sentenceYyList=mSentenceYyDao.queryBuilder().where(SentenceYyDao.Properties.TranName.like("%" +resultKey.trim()+ "%")) .list();

				}else{
					sentenceYyList=mSentenceYyDao.queryBuilder().where(SentenceYyDao.Properties.Name.like("%" +resultKey.trim()+ "%")) .list();

				}

				for (final SentenceYy dict:sentenceYyList ) {
					if (i > 1) {
						VideoBussinessItem splitBean = new VideoBussinessItem();
						splitBean.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
						settingMaps.add(splitBean);
					}
					final  VideoBussinessItem hideBean=new VideoBussinessItem();

					hideBean.setTitle(dict.getName());
					hideBean.setHint("粤语："+dict.getTranName());
					hideBean.setHintShow(true);

					settingMaps.add(hideBean);
				}

				settingSection.setDataMaps(settingMaps);
				iVideoHomeView.initUI(settingSection);
			}

			@Override
			public void showMsg(String msg) {

			}
		});




	}

	public void changeTranslateType(SearchParam  searchParam) {
    	orgType=searchParam.getFrom();
    	toTranslateType=searchParam.getTo();
    	search(tempSeachKey);
	}

//	public void search(String text) {
//
//
//
//	}
}
