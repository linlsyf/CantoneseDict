package com.business.bean;

public class Comment  extends BaseBean {
    private String title;
    private String content;
    private String imgeId;
    private String email;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgeId() {
        return imgeId;
    }

    public void setImgeId(String imgeId) {
        this.imgeId = imgeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
