package com.ui.dict.translate;

/**
 * Created by Administrator on 2019/5/11 0011.
 */

public class TransReponseBean {
    private  String from;
    private  String to;
    private  Object trans_result;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Object getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(Object trans_result) {
        this.trans_result = trans_result;
    }
}
