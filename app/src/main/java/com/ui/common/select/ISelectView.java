package com.ui.common.select;

import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;

import java.util.List;


public interface ISelectView {

	 void initUI(Section section);

    void select(List<IDyItemBean> selectList);
}
