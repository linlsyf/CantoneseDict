package com.ui.common.setting;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.core.ServerUrl;
import com.core.base.BasicActivity;

import com.core.utils.SpUtils;
import com.linlsyf.cantonese.R;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * <br>创建者：ldh 
 * <br>时间：2015年4月27日 上午10:35:01 
 * <br>注释：设置端口界面
 */
public class SettingActivity extends BasicActivity implements OnClickListener{
	String TAG = SettingActivity.class.getSimpleName();
	/** 顶部 */
	 @Bind(R.id.loginsetting_topbar)
	NavigationBar mTopbar;

	@Bind(R.id.setip)
	 EditText setip;

	@Bind(R.id.etPort)
	 EditText mEtPort;
	@Bind(R.id.resetAdress)
	TextView mRestAddressTv;
	@Bind(R.id.resetTestAdress)
	TextView mRestTestAddressTv;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       setContentView(R.layout.login_setting_ui);
		ButterKnife.bind(this);
		initUIView();
		initData();
		initListener();
	}
	
	@Override
	public void initUIView() {


		TopBarBuilder.buildOnlyText(mTopbar, this, NavigationBar.Location.LEFT_FIRST, "返回", 0);

		TopBarBuilder.buildCenterTextTitle(mTopbar, this,  getResources().getString(R.string.setting),  0);

		String ip = SpUtils.getString(this, ServerUrl.list_server_iP);
		String port = SpUtils.getString(this,ServerUrl.list_server_port);
//		String registerAddress = SpUtils.getString(this,ConfigUtil.list_regigster_server);

		if(ip!=null&&!ip.equals("")){
			setip.setText(ip);
		}else{
			setip.setText(ServerUrl.ip);
		}

		if(port!=null&&!port.equals("")){
			mEtPort.setText(port);
		}else{
			mEtPort.setText(String.valueOf(ServerUrl.port));
		}

//
//		if (StringUtils.isNotEmpty(registerAddress)) {
//			registerAdressEditText.setText(registerAddress);
//		}else {
//			registerAdressEditText.setText(ConfigUtil.CA_SERVICE_ADDRESS);
//		}
	}
	
	@Override
	public void initData() {
		
	}

	@Override
	public void initListener() {
//		mTopbar.getLeft_btn().setOnClickListener(this);
		mTopbar.setNavigationBarListener(new NavigationBarListener() {
			@Override
			public void onClick(ViewGroup containView, NavigationBar.Location location) {
				switch (location){
					case LEFT_FIRST:
						KeyboardUtils.closeKeybord(SettingActivity.this);


						String ip = setip.getText().toString().trim();

						String port =  mEtPort.getText().toString().trim();

						if(StringUtils.isNotEmpty(port)){
							SpUtils.putString(SettingActivity.this,ServerUrl.list_server_port,port);

						}else{
							ToastUtils.show(SettingActivity.this,"端口不能为空");
							return;
						}

						if(StringUtils.isNotEmpty(ip)){
	            	     SpUtils.putString(SettingActivity.this, ServerUrl.list_server_iP, ip);
						}else{
							ToastUtils.show(SettingActivity.this,"地址不能为空");
							return;
						}
						ServerUrl.port=Integer.parseInt(port);
						ServerUrl.ip=ip;
						ServerUrl.resetBaseUrl();
						finish();
						break;

				}
			}
		});
		mRestAddressTv.setOnClickListener(this);
		mRestTestAddressTv.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v==mRestAddressTv){
		String ip="18.222.51.213";
			 int port=8080;
			setip.setText(ip);
			mEtPort.setText(port+"");
		}
		else if (v==mRestTestAddressTv){
		String ip="10.0.0.96";
			 int port=8090;
			setip.setText(ip);
			mEtPort.setText(port+"");
		}

	}

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		
	}
	
	
}
