package com.ui.catalog;

import com.business.bean.VideoBussinessItem;
import com.core.base.IBaseView;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;

import java.util.List;


public interface ICatalogView extends IBaseView{

	 void initUI(Section section);

	void showType(VideoBussinessItem item, int type);

	void test();

    void openCustomView(List<DyItemBean> dataList);

	void toYuePing();
	void showAllLearn();
	void updateUIItem(boolean isPlaying,DyItemBean imgBean);

	void openTempView(List<DyItemBean> dataListCustom);

    void openUrl(String s);


    /// void toOpenErrorFile();
}
