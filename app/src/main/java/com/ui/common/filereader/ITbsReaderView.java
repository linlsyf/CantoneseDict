package com.ui.common.filereader;

import com.business.bean.VideoBussinessItem;
import com.core.base.IBaseView;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;

import java.util.List;


public interface ITbsReaderView extends IBaseView{

	 void initUI(Section section);

	void showType(VideoBussinessItem item, int type);

	void test();

    void openCustomView(List<DyItemBean> dataList);

	void toYuePing();
	void showAllLearn();
	void updateUIItem(boolean isPlaying, DyItemBean imgBean);


}
