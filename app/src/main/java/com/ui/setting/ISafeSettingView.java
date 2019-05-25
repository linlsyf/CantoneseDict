package com.ui.setting;

import com.core.base.IBaseView;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.DyItemBean;

import java.util.List;


public interface ISafeSettingView extends IBaseView{

	 void initUI(Section section);

	void showUpdate();

	void logOut();
	void updateItem(DyItemBean imgBean);
	void showNews();
	void showTest();

    void clickAbout();

	void openUrl(String url);

    void selectTheme(List<DyItemBean> dataList);
}
