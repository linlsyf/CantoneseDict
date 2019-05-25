package com.ui;

import com.business.BusinessBroadcastUtils;
import com.ui.login.IlogInView;
import com.ui.login.LoginPresenter;


public class HomePresenter {

    IHomeView iHomeView;
    HttpService service;
    public HomePresenter(IHomeView ilogInView) {
        this.iHomeView=ilogInView;
        service=new HttpService();
    }
    public void login(){

    }
}
