package com.ui.common.select;

import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.DyItemBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.ui.common.CommonConstran;

import java.util.ArrayList;
import java.util.List;

public class SelectPresenter {
    ISelectView iinputView;
    private Section settingSection;
    private String mViewType="TYPE_EDIT";

    public SelectPresenter(ISelectView iSafeSettingView) {
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

                  itemBean.setTitle(it.getTitle()+":"+((DyItemBean) item).getHint());
                  itemBean.setSelectType(CommonConstran.KEY_SETTING);//选择类型

            itemBean.setOnItemListener(new IItemView.onItemClick() {
                @Override
                public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
                    List<IDyItemBean>  selectList=new ArrayList<>();
                    selectList.add(iDyItemBean);
                    iinputView.select(selectList);

                }
            });

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
