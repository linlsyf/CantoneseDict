package com.ui.common.custom;

import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;

import java.util.List;


public interface ICustomView {

	 void initUI(Section section);

    void select(List<IDyItemBean> selectList);
}
