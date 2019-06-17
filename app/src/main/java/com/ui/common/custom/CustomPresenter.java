package com.ui.common.custom;

import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.ui.common.CommonConstran;

import java.util.ArrayList;
import java.util.List;

public class CustomPresenter {
    ICustomView iinputView;
    private Section settingSection;
    private String mViewType="TYPE_EDIT";

    public CustomPresenter(ICustomView iSafeSettingView) {
        this.iinputView =iSafeSettingView;
    }

    public void init( List<DyItemBean> dataList) {
        settingSection=new Section(CommonConstran.KEY_SETTING);
        settingSection.setShowSection(false);
        List<IDyItemBean> settingMaps=new ArrayList<>();
        settingMaps.addAll(dataList);
        settingSection.setDataMaps(settingMaps);
     iinputView.initUI(settingSection);

    }


    public void setViewType(String viewType) {
        mViewType = viewType;
    }
}
