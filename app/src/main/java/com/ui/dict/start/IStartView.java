package com.ui.dict.start;

import com.business.bean.VideoBussinessItem;
import com.core.base.IBaseView;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.DyItemBean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public interface IStartView extends IBaseView {

    void initUI(Section section);
    void showType(VideoBussinessItem item, int type);
    void openVideo(VideoBussinessItem itemBean);

    void editType(List<DyItemBean> dataList);
}
