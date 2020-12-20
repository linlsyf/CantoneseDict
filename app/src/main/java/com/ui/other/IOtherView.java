package com.ui.other;

import com.core.base.IBaseView;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;


public interface IOtherView extends IBaseView{

	 void updateSection(Section section);

	void updateItem(DyItemBean imgBean);
	void showNews();

	void showApay();
}
