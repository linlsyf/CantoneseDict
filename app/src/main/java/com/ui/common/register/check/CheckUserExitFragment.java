package com.ui.common.register.check;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.business.login.LoginPerson;
import com.core.base.BaseFragment;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.dialog.ChatBaseDialog;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.process.ProgressHUD;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.area.R;
import com.ui.common.register.RegisterUserFragment;


/**
 * 
 *<br> 创建者：ldh
 *<br>时间：2015年8月19日 上午9:47:44
 *<br>注释：输入手机号获得验证码
 *<br>
 */
public class CheckUserExitFragment extends BaseFragment implements OnClickListener,IcheckPhoneView {
	/**输入手机号*/
	private EditText mEtPhone;
	/** 顶部 toorbar*/
	private NavigationBar mTopbar;
	/** 保存信息进度框 */
	private ProgressHUD progressHUD;
	/** 弹出是否已经存在用户 */
	private ChatBaseDialog mIsExitDialog;
	/**提交*/
	private Button sumbitButton;
	/**注册用户*/
	public static String loginId ="";
	/**检查是否存在 账号，如果已经存在 且点击登录那么发送广播到登录界面改变账号*/
	public  String mCheckRegisterNumString ="";
	CheckUserExitPersenter   checkUserExitPersenter;
	@Override
	public int getLayoutResId() {
		return R.layout.common_register_phone_validate;
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
		mEtPhone = getViewById(R.id.et_input_phone_register);
		sumbitButton =  getViewById(R.id.bt_next_step);

		mEtPhone.postDelayed(new Runnable() {
			@Override
			public void run() {
				mEtPhone.setFocusable(true);
				mEtPhone.setFocusableInTouchMode(true);
				mEtPhone.requestFocus();
				mEtPhone.findFocus();
				KeyboardUtils.openKeybord(getActivity(),mEtPhone);
			}
		},500);

	}
	
	@Override
	public void initData() {
		Bundle mBundle=getArguments();
		if (mBundle!=null) {
			loginId=mBundle.getString("id");
		}
		checkUserExitPersenter=new CheckUserExitPersenter(this);
//		mTopbar.initView(getResources().getString(R.string.back), R.drawable.public_topbar_back_arrow, 0, getResources().getString(R.string.register_newuser), "", 0, 0);
	}

	
	@Override
	public void initListener() {
		super.initListener();
//		mTopbar.getLeft_btn().setOnClickListener(this);


		mTopbar=getViewById(R.id.topbar_register);

		TopBarBuilder.buildOnlyText(mTopbar, getActivity(), NavigationBar.Location.LEFT_FIRST, "返回", 0);

		TopBarBuilder.buildCenterTextTitle(mTopbar, getActivity(),  getResources().getString(R.string.register_newuser),  0);
//		TopBarBuilder.buildOnlyText(mTopbar, getActivity(), NavigationBar.Location.RIGHT_FIRST, "确定", 0);



		sumbitButton.setOnClickListener(this);

		mEtPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
				//点击下一项获取注册验证码
				if (actionId == EditorInfo.IME_ACTION_NEXT)
				{
					if (validate(true)) {
						progressHUD = ProgressHUD.show(getActivity(), "", false, true, null, null);
//						Register register = new Register(getActivity(), mEtPhone.getText().toString().trim());
//						register.sendIsExist();
						checkUserExitPersenter.checkPhoneExit(mEtPhone.getText().toString().trim());
					}
				}
				return false;
			}
		});
		mTopbar.setNavigationBarListener(new NavigationBarListener() {

			@Override
			public void onClick(ViewGroup containView, NavigationBar.Location location) {
				switch (location){
					case LEFT_FIRST:
						KeyboardUtils.closeKeybord(getActivity());
						FragmentHelper.popBackFragment(getActivity());
						break;

				}

			}
		});
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

	/**
 * 
 *<br> 创建者：ldh
 *<br>时间：2015年8月20日 上午11:59:05
 *<br>注释：验证手机号码
 *<br>@return
 */
	public boolean validate(Boolean showToast) {
		if (StringUtils.isEmpty(mEtPhone.getText().toString())) {
			if (showToast) {
				ToastUtils.show(getActivity(), getResources().getString(R.string.phone_mail_cant_not_null));
			}
			return false;
		}
		if (StringUtils.isPhoneNumber(mEtPhone.getText().toString())) {
			return true;
		}
		if (StringUtils.isEmail(mEtPhone.getText().toString())) {
			return true;
		}
		if (showToast) {
			ToastUtils.show(getActivity(), getResources().getString(R.string.phone_mail_format_error));
		}
		return false;
	}
	@Override
	public void onClick(View v) {
		KeyboardUtils.closeKeybord(getActivity());
//		if (v == mTopbar.getLeft_btn()) {// 导航栏 左侧按钮
//			FragmentHelper.popBackFragment(getActivity());
//		}else
		if (v == sumbitButton) {
			if (validate(true)) {
				progressHUD = ProgressHUD.show(getActivity(), "", false, true, null, null);
				mCheckRegisterNumString=mEtPhone.getText().toString().trim();
				checkUserExitPersenter.checkPhoneExit(mEtPhone.getText().toString().trim());

//				Register register = new Register(getActivity(),mCheckRegisterNumString );
//				register.sendIsExist();
			}
		}
		
	}
	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		

	}
	/**
	 * 
	 *<br> 创建者：ldh
	 *<br>时间：2015年8月21日 下午5:06:44
	 *<br>注释：显示已经存在用户
	 *<br>
	 */
	private void showExitDialog() {
		if (mIsExitDialog ==null&getActivity()!=null) {
			mIsExitDialog =new ChatBaseDialog(getActivity(),true,true);
			mIsExitDialog.setTitle(getResources().getString(R.string.notice));
			mIsExitDialog.setTitleLineVisible(View.GONE);
			mIsExitDialog.getOkView().setText(getString(R.string.login));
			mIsExitDialog.getOkView().setTextColor(getResources().getColor(R.color.common_blue));
			mIsExitDialog.setBodyText(getResources().getString(R.string.username_has_exit));
			mIsExitDialog.setOKListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					mCheckRegisterNumString
//					LoginPerson loginPerson=new LoginPerson();
//					loginPerson.setLoginId(mCheckRegisterNumString);
//					BusinessBroadcastUtils.sendBroadcast(getActivity(),BusinessBroadcastUtils.TYPE_LOCAL_REGISTER_EXIT_LOGIN,loginPerson);

					mIsExitDialog.dismiss();
					FragmentHelper.cleanAllFragement(getActivity());

				}
			});
		}
		mIsExitDialog.show();
	}

	@Override
	public void checkPhoneUserExit(final  boolean isExit) {
		progressHUD.dismiss();
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (isExit){
					showExitDialog();
				}else {
					RegisterUserFragment registerAccountVerFragment=new RegisterUserFragment();
					LoginPerson loginPerson=new LoginPerson();
					loginPerson.setLoginId(mCheckRegisterNumString);
					Bundle intentBundle=new Bundle();
					intentBundle.putSerializable(RegisterUserFragment.KEY_LOGIN_BEAN,loginPerson);
					FragmentHelper.showFrag(getActivity(), R.id.flyt,registerAccountVerFragment,intentBundle);

				}
			}
		});

	}

	@Override
	public void loadDataStart() {

	}

	//	@Override
//	public Context getContext() {
//		return getActivity();
//	}
//
	@Override
	public void showToast(String text) {
		 if (progressHUD!=null){
			 progressHUD.dismiss();
		 }
     ToastUtils.show(getActivity(),text);
	}
}
