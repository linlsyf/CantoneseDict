package com.business.baidu.translate;

/**
 * Created by Administrator on 2019/5/11 0011.
 */

public class SearchParam {
    String query;
     String  from="zh";
    //	private String  orgType="auto";
     String  to="yue";
     int  type=0;
     String  typeName="";

    public SearchParam() {
    }

    public SearchParam(String query, String from, String to) {
        this.query = query;
        this.from = from;
        this.to = to;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
