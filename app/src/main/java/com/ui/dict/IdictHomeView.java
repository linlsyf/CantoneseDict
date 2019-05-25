package com.ui.dict;

import com.business.bean.VideoBussinessItem;
import com.core.base.IBaseView;
import com.easy.recycleview.bean.Section;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public interface IdictHomeView extends IBaseView {

    void initUI(Section section);
    void showType(VideoBussinessItem item, int type);
    void openVideo(VideoBussinessItem itemBean);

    void startStudy();

    void showAllLearn();


    void toTranslate();

    void toYuePing();
}
