package com.ui.other.tuling.news;

import com.core.base.IBaseView;
import com.easy.recycleview.bean.Section;
import com.ui.other.tuling.entity.NewsEntity;


public interface INewsView extends IBaseView{

	 void initUI(Section section);
     void showItem(NewsEntity itemBean);

}
