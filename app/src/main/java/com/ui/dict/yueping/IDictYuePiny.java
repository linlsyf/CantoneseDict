package com.ui.dict.yueping;

import com.business.bean.VideoBussinessItem;
import com.core.base.IBaseView;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;

import java.util.List;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public interface IDictYuePiny extends IBaseView {

    void initUI(Section section);
    void showType(VideoBussinessItem item, int type);
    void openVideo(VideoBussinessItem itemBean);

    void editType(List<DyItemBean> dataList);
}
