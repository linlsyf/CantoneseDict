package com.ui.setting.about;

import com.core.base.IBaseView;
import com.easy.recycleview.bean.Section;


public interface IAboutView extends IBaseView{

	 void initUI(Section section);


	void openUrl(String url);
	void openUrlByBroswer(String url);
}
