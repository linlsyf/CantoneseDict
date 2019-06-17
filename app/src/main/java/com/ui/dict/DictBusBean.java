package com.ui.dict;


import com.easy.recycleview.bean.DyItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public class DictBusBean   extends DyItemBean {

    private  long unread=0;
    private  long today=0;
    private String totalMsg="";
    private String totalLearnMsg="";

//    private  long today=0;




    List<DyItemBean> dataList=new ArrayList<>();

    public List<DyItemBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DyItemBean> dataList) {
        this.dataList = dataList;
    }

    public long getUnread() {
        return unread;
    }

    public void setUnread(long unread) {
        this.unread = unread;
    }

    public long getToday() {
        return today;
    }

    public void setToday(long today) {
        this.today = today;
    }

    public String getTotalMsg() {
        return totalMsg;
    }

    public void setTotalMsg(String totalMsg) {
        this.totalMsg = totalMsg;
    }

    public String getTotalLearnMsg() {
        return totalLearnMsg;
    }

    public void setTotalLearnMsg(String totalLearnMsg) {
        this.totalLearnMsg = totalLearnMsg;
    }
}
