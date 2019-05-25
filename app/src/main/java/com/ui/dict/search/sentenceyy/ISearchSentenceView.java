package com.ui.dict.search.sentenceyy;

import com.business.bean.VideoBussinessItem;
import com.core.base.IBaseView;
import com.easy.recycleview.bean.Section;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public interface ISearchSentenceView extends IBaseView {

    void initUI(Section section);
    void showItem(final VideoBussinessItem imgBean);
}
