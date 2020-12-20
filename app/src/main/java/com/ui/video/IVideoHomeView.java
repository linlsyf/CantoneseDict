package com.ui.video;

import com.business.bean.VideoBussinessItem;
import com.core.base.IBaseView;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;

import java.util.List;


public interface IVideoHomeView extends IBaseView{

	 void initUI(Section section);
     void showItem(VideoBussinessItem itemBean);
     void openVideo(VideoBussinessItem itemBean);

    void showCustomView(List<DyItemBean> dataList);
}
