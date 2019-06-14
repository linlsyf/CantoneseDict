package com.ui.common.register;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.business.BusinessBroadcastUtils;
import com.business.login.LoginPerson;
import com.core.base.BaseFragment;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.area.R;


/**
 * 
 *<br> 创建者：ldh
 *<br>时间：2015年8月19日 上午9:48:04
 *<br>注释：注册成功
 *<br>
 */
public class RegisterSuccessFragment extends BaseFragment implements OnClickListener {
	/**顶部 toorbar*/
	private NavigationBar mTopbar;
	/** 立即使用*/
	private Button toUserButton;
	/** 注册用户*/
	public LoginPerson loginPerson;
	public static final String KEY_LOGIN_BEAN="login_bean";

	@Override
	public int getLayoutResId() {
		return R.layout.common_register_success;
	}
	@Override
	public void initFragment() {
		initUIView();
		initData();
		initListener();
	}
	@Override
	public void initUIView() {
		mTopbar = getViewById(R.id.topbar_register);
		toUserButton = getViewById(R.id.bt_touse);
	}
	@Override
	public void initData() {
		Bundle mBundle=getArguments();
		if (mBundle!=null) {
			loginPerson=(LoginPerson) mBundle.getSerializable(KEY_LOGIN_BEAN);
		}

	}
	@Override
	public void initListener() {
		super.initListener();
		toUserButton.setOnClickListener(this);
		mTopbar=getViewById(R.id.topbar_register);


		TopBarBuilder.buildCenterTextTitle(mTopbar, getActivity(), getResources().getString(R.string.register_complete),  0);
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		
	}
	@Override
	public void onClick(View v) {

		 if (v==toUserButton) {
			 BusinessBroadcastUtils.sendBroadcast(getActivity(), BusinessBroadcastUtils.TYPE_LOCAL_REGISTER_SUCCESS_AUTOLOGIN, loginPerson);
		      FragmentHelper.cleanAllFragement(getActivity());
		}
	}
	
}
