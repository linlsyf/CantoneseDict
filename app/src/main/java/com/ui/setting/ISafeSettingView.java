package com.ui.setting;

import com.core.base.IBaseView;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;

import java.util.List;


public interface ISafeSettingView extends IBaseView{

	 void initUI(Section section);

	void showUpdate();

	void logOut();
	void updateItem(DyItemBean imgBean);
	void updateApk(String url, String name);
	void updateUIItem(boolean isPlaying, DyItemBean imgBean);
	void showNews();
	void showTest();

    void clickAbout();
    void openCustomView(List<DyItemBean> dataList);

	void openUrl(String url);

    void selectTheme(List<DyItemBean> dataList);

    void test();
}
