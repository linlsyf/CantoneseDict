package com.ui.common.tempview;

import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;

import java.util.List;


public interface ITempView {

	 void initUI(Section section);

    void select(List<IDyItemBean> selectList);
    void initAddView(List<IDyItemBean> selectList);

}
