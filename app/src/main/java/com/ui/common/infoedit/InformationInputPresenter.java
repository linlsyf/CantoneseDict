package com.ui.common.infoedit;

import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.AddressEditSettings;
import com.easy.recycleview.inter.IDyItemBean;
import com.ui.common.CommonConstran;

import java.util.ArrayList;
import java.util.List;

public class InformationInputPresenter   {
    IinputView   iinputView;
    private Section settingSection;
    private String mViewType="TYPE_EDIT";

    public InformationInputPresenter(IinputView iSafeSettingView) {
        this.iinputView =iSafeSettingView;
    }

    public void init( List<DyItemBean> dataList) {
        settingSection=new Section(CommonConstran.KEY_SETTING);
        settingSection.setShowSection(false);
        List<IDyItemBean> settingMaps=new ArrayList<>();
       int i=0;
        for ( IDyItemBean item:dataList){
                DyItemBean it=(DyItemBean)item;
            String  id=it.getId();
            String value= it.getHint().replace("\"","");
             DyItemBean itemBean=new DyItemBean();

//              if (mViewType.equals("TYPE_EDIT")){
                  AddressEditSettings editSettings=new AddressEditSettings();
                  editSettings.setShowEdittext(true);
                  itemBean.setTitle(it.getTitle()+": ");
                  editSettings.setEditContent(value);
                  if (i==0){
                      editSettings.setOpenKeybord(true);
                  }
                  itemBean.setEidtSettings(editSettings);
//              }
//             else  if (mViewType.equals("TYPE_SELECT")){
//                  itemBean.setShowLeftCheckBox(true);
//                  itemBean.setTitle(it.getTitle()+":"+((DyItemBean) item).getHint());
//                  itemBean.setSelectType(CommonConstran.KEY_SETTING);//选择类型
//
//              }


              itemBean.setId(id);
            // itemBean.setEidtSettings(editSettings);
             i++;
             settingMaps.add(itemBean);
         }
        settingSection.setDataMaps(settingMaps);
     iinputView.initUI(settingSection);

    }


    public void setViewType(String viewType) {
        mViewType = viewType;
    }
}
