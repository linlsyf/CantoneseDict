package com.ui.common.register;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.business.BusinessBroadcastUtils;
import com.business.login.LoginPerson;
import com.core.base.BaseFragment;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.utils.lib.system.PasswordUtils;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.TextViewURLSpan;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.edittextview.ValidateEdittextView;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.process.ProgressHUD;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.area.R;


//import cn.smssdk.SMSSDK;

/**
 * 
 *<br> 创建者：ldh
 *<br>时间：2015年8月19日 上午9:47:44
 *<br>注释：账号验证
 *<br>
 */
public class RegisterUserFragment extends BaseFragment implements OnClickListener,IregisterView {
	/**输入手机号*/
	private TextView registerPhoneTextView;
	/**获得验证码按钮*/
	private Button getCodeButton;
	/** 顶部 toorbar*/
	private NavigationBar toolbar;
	/**注册用户*/
	public static String loginId ="";
	/** 保存信息进度框 */
	private ProgressHUD progressHUD;

	/**已经请求完成用户信息最后一步*/
	private boolean isRequestComplete=false;
	/**请求发送验证码*/
	private boolean isRequestGetCode=false;

	/** 重发倒计时*/
	private int mReSendTime = 60;
	/**提交*/
	private Button sumbitButton;
	private ValidateEdittextView validateEdittextView;
	/**真实姓名*/
	private EditText realUserNameEditText;
	/**密码*/
	private EditText pwdEditText;
	/** 声明文字*/
	private TextView noticeTextView;
	/**已经初始化过一次*/
	private boolean isInit=false;
	/**变换密码是否可见*/
	private RelativeLayout changePwdshowLayout;
	/**是否可见图标*/
	private ImageView visualizedImageView;
	/**是否可见图标*/
	private boolean isVisualized=false;

	/**解绑倒计时*/
	private CountdownRunnable countdownRunnable;

	public static final String KEY_LOGIN_BEAN="login_bean";
	RegisterPersenter registerPersenter;
	@Override
	public int getLayoutResId() {
		return R.layout.common_register_account_validate;
	}
	@Override
	public void initFragment() {
		initUIView();
		initData();
		initListener();
	}
	@Override
	public void initUIView() {
		registerPhoneTextView = getViewById(R.id.tv_input_phone_register);
		getCodeButton =  getViewById(R.id.bt_getcode_register);
		realUserNameEditText =  getViewById(R.id.edit_relality_name);
		pwdEditText =  getViewById(R.id.edit_pwd);
		noticeTextView =  getViewById(R.id.tv_notice);
		changePwdshowLayout =  getViewById(R.id.rl_changeshow);
		visualizedImageView =  getViewById(R.id.img_visualized);
		initStepVerifyView();
	}
	
	/**
	 *创建者 王婷玉
	 *时间2016-1-6上午11:48:04
	 *注释：初始化填写验证码的view 
	 */
	private void initStepVerifyView() {
		sumbitButton =  getViewById(R.id.bt_next_step);
		validateEdittextView =  getViewById(R.id.etview_validate_code);
		validateEdittextView.getVerifyCodeEditText().requestFocus();
		KeyboardUtils.openKeybord(getActivity(), validateEdittextView.getVerifyCodeEditText());

		String item1=getString(R.string.cannot_receive_sms);//收不到短信验证码?
		String item2=getString(R.string.resend_sms);//重发短信
		String item3=getString(R.string.get_validatecode);//获取验证码
		SpannableString spanStr = new SpannableString(item1);
		SpannableStringBuilder ssb = new SpannableStringBuilder(spanStr);
		final String clickitem1 = item2;
		ssb.append(clickitem1);
		ssb.append(item3);
		// 计算第一个点击文字的位置
		final int start = spanStr.length();
		//设置文字样式以及监听
		OnClickListener listener=new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		};
		TextViewURLSpan clickItem1=new TextViewURLSpan(getActivity(), listener);
		clickItem1.setcliclkColorColor(getResources().getColor(R.color.context_bg_blue));
		ssb.setSpan(clickItem1, start, start + clickitem1.length(), 0);
	}

	@Override
	public void initData() {
	    if (!isInit) {
	    	if (null!=getArguments()) {
				LoginPerson loginPerson=(LoginPerson)getArguments().get(KEY_LOGIN_BEAN);
	    		loginId=loginPerson.getLoginId();
			}
	    	handler=new Handler();
			countdownRunnable=new CountdownRunnable(getCodeButton, handler);
			int color=getResources().getColor(R.color.context_bg_blue);
			int greycolor=getResources().getColor(R.color.context_bg_blue);
			countdownRunnable.setTextColor(greycolor, color);
			handler.post(countdownRunnable);
	    	isInit=true;
			registerPersenter=new RegisterPersenter(this);
			registerPersenter.sendCode(loginId);
//	    	Register register = new Register(getActivity(),loginId);
//			register.getCaptcha("register");
		}
//		if (StringUtils.isPhoneNumber(loginId)) {
//			loginId="+86 "+loginId;
//		}
	    registerPhoneTextView.setText(loginId);


		toolbar=getViewById(R.id.topbar_register);

		TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.LEFT_FIRST, "返回", 0);

		TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "输入内容", 0);
		TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.RIGHT_FIRST, "确定", 0);

//		mTopbar.initView(getString(R.string.back), R.drawable.public_topbar_back_arrow, 0, getResources().getString(R.string.register_newuser), "", 0, 0);
//
//		       // 构建声明文字
//				noticeTextView.setMovementMethod(LinkMovementMethod.getInstance());
//
//				String appName = ApkUtils.getInstance(getActivity()).getApplicationName();
//
//				String agreMentMsg = String.format(getString(R.string.register_and_agree), appName);
//
//				SpannableString spanStr = new SpannableString(agreMentMsg);// 注册账号即表示您同意遵守?账号的
//				SpannableStringBuilder ssb = new SpannableStringBuilder(spanStr);
//
//				// 拼接文字
//				String clickitem1 = getResources().getString(R.string.user_agreement);// 用户协议
//
//				ssb.append(clickitem1);
//
//				String str2 = getResources().getString(R.string.and);// 和
//				ssb.append(str2);
//
//				final String clickitem2 = getResources().getString(R.string.privacy_clause);// 隐私条款
//				ssb.append(clickitem2);
//
//				// 计算第一个点击文字的位置
//				final int start = spanStr.length();
//				// 计算第二个点击文字的位置
//				final int start2 = spanStr.length() + clickitem1.length() + str2.length();
//				// 设置第一个文字的点击事件
//
//				// 设置文字样式以及监听
//				OnClickListener listener1 = new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//					}
//				};
//				TextViewURLSpan clickItem1 = new TextViewURLSpan(getActivity(), listener1);
//				clickItem1.setcliclkColorColor(getResources().getColor(R.color.context_bg_blue));
//				// 设置第二个点击文字颜色
//
//				OnClickListener listener2 = new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//
//					}
//				};
//				TextViewURLSpan clickItem2 = new TextViewURLSpan(getActivity(), listener2);
//
//				clickItem2.setcliclkColorColor(getResources().getColor(R.color.context_bg_blue));
//
//				ssb.setSpan(clickItem1, start, start + clickitem1.length(), 0);
//				// 设置第二个点击事件
//				ssb.setSpan(clickItem2, start2, start2 + clickitem2.length(), 0);
//				// 设置文字到textview
//				noticeTextView.setText(ssb, BufferType.SPANNABLE);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		SMSSDK.unregisterAllEventHandler();
	}

	@Override
	public void initListener() {
		super.initListener();
		getCodeButton.setOnClickListener(this);
//		mTopbar.getLeft_btn().setOnClickListener(this);
		sumbitButton.setOnClickListener(this);
		changePwdshowLayout.setOnClickListener(this);
		validateEdittextView.getVerifyCodeEditText().addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
					if(validateEdittextView.getVerifyCodeEditText().getText().toString().length()==6){
						KeyboardUtils.closeKeybord(getActivity(),validateEdittextView.getVerifyCodeEditText());
						realUserNameEditText.requestFocus();
						KeyboardUtils.openKeybord(getActivity(),realUserNameEditText);
					}
			}
		});

		pwdEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
				if (actionId == EditorInfo.IME_ACTION_DONE)
				{
					if (validateVerify(true)) {
						if (validate(true)) {
							isRequestComplete=true;
							// 验证 验证码
//							Register register = new Register(getActivity(), loginId);
//							register.sendRegister(validateEdittextView.getValidateCode().trim(), realUserNameEditText.getText().toString().trim(), pwdEditText.getText().toString().trim());
							registerPersenter.register(loginId,validateEdittextView.getValidateCode().trim(), realUserNameEditText.getText().toString().trim(), pwdEditText.getText().toString().trim());

							progressHUD = ProgressHUD.show(getActivity(), "", false, true, null, null);
						}

					}
				}
					return false;
			}
		});
		toolbar.setNavigationBarListener(new NavigationBarListener() {

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
		
		if (StringUtils.isEmpty(realUserNameEditText.getText().toString().trim())) {
			if (showToast) {
				ToastUtils.show(getActivity(), getResources().getString(R.string.username_cannot_null));
			}
			return false;
		}
		if (!PasswordUtils.conformLoginPsw(pwdEditText.getText().toString().trim())) {
			if (showToast) {
				ToastUtils.show(getActivity(), getResources().getString(R.string.pwd_format_is_error));
			}
			return false;
		}
		return true;
	}
	@Override
	public void onClick(View v) {
		KeyboardUtils.closeKeybord(getActivity());
//		if (v == mTopbar.getLeft_btn()) {// 导航栏 左侧按钮
//
//			FragmentHelper.popBackFragment(getActivity());
//
//		} else
		if (v == getCodeButton) {
			isRequestGetCode=true;

				loginId = registerPhoneTextView.getText().toString().trim();

			registerPersenter.sendCode(loginId);
			
				countdownRunnable=new CountdownRunnable(getCodeButton, handler);
				int color=getResources().getColor(R.color.context_bg_blue);
				int greycolor=getResources().getColor(R.color.context_bg_blue);
				countdownRunnable.setTextColor(greycolor, color);
				handler.post(countdownRunnable);
				KeyboardUtils.closeKeybord(getActivity());

		}else if (v == sumbitButton) {
			if (validateVerify(true)) {
				if (validate(true)) {
					isRequestComplete=true;
					// 验证 验证码
//					Register register = new Register(getActivity(), loginId);
//					register.sendRegister(validateEdittextView.getValidateCode().trim(), realUserNameEditText.getText().toString().trim(), pwdEditText.getText().toString().trim());
                 registerPersenter.register(loginId,validateEdittextView.getValidateCode().trim(), realUserNameEditText.getText().toString().trim(), pwdEditText.getText().toString().trim());

					progressHUD = ProgressHUD.show(getActivity(), "", false, true, null, null);
				}
//
			}
			
		}else if (v==changePwdshowLayout) {
			if (isVisualized) {
				isVisualized=false;
				
				 //设置密码为隐藏的
				visualizedImageView.setImageResource(R.drawable.not_visualized);
				pwdEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
				pwdEditText.setSelection(pwdEditText.getText().length());

			}else {
				isVisualized=true;
				 //设置EditText的密码为可见的
				visualizedImageView.setImageResource(R.drawable.visualization);
				pwdEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				pwdEditText.setSelection(pwdEditText.getText().length());
			}
		}
	}
	/**
	 *创建者 王婷玉
	 *时间2016-1-6上午11:53:12
	 *注释：验证验证是否符合规格
	 */
	private boolean validateVerify(boolean b) {
		if (StringUtils.isEmpty(validateEdittextView.getValidateCode())) {
			ToastUtils.show(getActivity(), getResources().getString(R.string.verify_code_no_complete));
			return false;
		}
		
		if (validateEdittextView.getValidateCode().length()<6) {
			ToastUtils.show(getActivity(), getResources().getString(R.string.verify_code_no_complete));
			return false;
		}
		return true;
	}
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (mReSendTime > 1) {
				mReSendTime--;

				getCodeButton.setText("(" + mReSendTime +"s"+ ")后重新获取");
				getCodeButton.setEnabled(false);
				handler.sendEmptyMessageDelayed(0, 1000);
			} else {
				mReSendTime = 60;
				getCodeButton.setText("重新获取验证码");
				getCodeButton.setEnabled(true);
			}
		}
	};
	
	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		 if (type.equals(BusinessBroadcastUtils.HTTP_ERROR)) {
			
				if(progressHUD!=null){
					progressHUD.dismiss();
				}
				if (null!=mode) {
					ToastUtils.show(getActivity(),mode.toString());

				}else {
					ToastUtils.show(getActivity(),getResources().getString(R.string.connet_server_error));
				}

		}
	}

	/**
	 * 
	 *<br> 创建者：林党宏
	 *<br>时间：2016年4月20日 下午4:45:07
	 *<br>注释：验证账号密码
	 */
	public boolean validateNamePwd(boolean showToast) {
		String realUserName = realUserNameEditText.getEditableText().toString();
		String pwd = pwdEditText.getEditableText().toString();
		if (realUserName.isEmpty() || pwd.isEmpty()) {
			if (showToast) {
				ToastUtils.show(getActivity(), getResources().getString(R.string.check_no_input));
			}
			return false;
		}
		if (!PasswordUtils.conformLoginPsw(pwd)) {
			if (showToast) {
				ToastUtils.show(getActivity(), getResources().getString(R.string.pwd_format_is_error));
			}
			return false;
		}
		 return true;
	}

	@Override
	public void register(boolean isSucess) {
		progressHUD.dismiss();
		 if (isSucess){
			 RegisterSuccessFragment registerSuccessFragment=new RegisterSuccessFragment();
			 LoginPerson loginPerson=new LoginPerson();
			 loginPerson.setLoginId(loginId);


			 Bundle intentBundle=new Bundle();
			 intentBundle.putSerializable(RegisterSuccessFragment.KEY_LOGIN_BEAN,loginPerson);
			 FragmentHelper.showFrag(getActivity(), R.id.flyt,registerSuccessFragment,intentBundle);

		 }else{

		 }
	}

	@Override
	public void showToast(String text) {
		progressHUD.dismiss();
		super.showToast(text);
	}
}
