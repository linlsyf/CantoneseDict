package com.ui.app.tv;

import com.business.bean.VideoBussinessItem;
import com.core.base.IBaseView;
import com.easy.recycleview.bean.Section;


public interface ITVView extends IBaseView {
    void initUI(Section section);
    void showItem(VideoBussinessItem itemBean);
    void openVideoBySelf(VideoBussinessItem itemBean);
    void openVideo(VideoBussinessItem itemBean);

}
