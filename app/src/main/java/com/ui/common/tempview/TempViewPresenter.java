package com.ui.common.tempview;

import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.ui.common.CommonConstran;

import java.util.ArrayList;
import java.util.List;

public class TempViewPresenter {
    ITempView iinputView;
    private Section settingSection;
    private String mViewType="TYPE_EDIT";
    private boolean hasInit;

    public TempViewPresenter(ITempView iSafeSettingView) {
        this.iinputView =iSafeSettingView;
    }

    public void init( List<DyItemBean> dataList) {

                 if(hasInit){
                   return;
                 }
        hasInit=true;
        settingSection=new Section(CommonConstran.KEY_SETTING);
        settingSection.setShowSection(false);
        List<IDyItemBean> settingMaps=new ArrayList<>();
        settingMaps.addAll(dataList);
//        settingSection.setDataMaps(settingMaps);
     iinputView.initAddView(settingMaps);

    }


    public void setViewType(String viewType) {
        mViewType = viewType;
    }
}
