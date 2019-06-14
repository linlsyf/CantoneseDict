package com.business.service.music.server;

import com.business.bean.BaseBean;

/**
 * Created by Administrator on 2019/6/14 0014.
 */

public class SongBean extends BaseBean {
    private String sid;
    private String picture;
    private String artist;
    private String url;
    private String lrc;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }
}
