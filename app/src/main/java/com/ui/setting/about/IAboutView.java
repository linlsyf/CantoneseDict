package com.ui.setting.about;

import com.core.base.IBaseView;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.DyItemBean;


public interface IAboutView extends IBaseView{

	 void initUI(Section section);


	void openUrl(String url);
}
