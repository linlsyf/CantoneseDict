package com.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.business.BusinessBroadcastUtils;
import com.business.login.LoginPerson;
import com.core.base.BasicActivity;
import com.easysoft.utils.lib.system.AppInfo;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.dialog.CustomDialog;
import com.easysoft.widget.edittextview.BoundEditText;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.imgeview.MultiShapeView;
import com.easysoft.widget.process.ProgressHUD;
import com.easysoft.widget.statusview.SoftKeyBoardSatusView;
import com.linlsyf.cantonese.R;
import com.ui.common.register.check.CheckUserExitFragment;
import com.ui.common.setting.SettingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录界面类
 * <p>
 * UI层调用函数请求登录业务，业务层处理完毕，回调返回结果给UI层体现效果,而具体如何实现业务,UI层不需要关心过程，
 * 并且UI层没有if，else之类的业务数据判断，UI完全与业务层隔离,分工明细，便于维护。
 */
public class LoginActivity extends BasicActivity implements IlogInView,
        SoftKeyBoardSatusView.SoftkeyBoardListener, View.OnClickListener {

    /** 弹框显示的信息的传入参数字段 */
    private static final String SHOW_DIALOG_MSG = "show_dialog_msg";

    /*使用后弹出键盘时，能把界面向上推*/
    @Bind(R.id.login_soft_status_view)
    SoftKeyBoardSatusView mStatusView;

    @Bind(R.id.login)
    Button mBtnLogin;

    @Bind(R.id.btnSetting)
    ImageView mBtnSetting;

    @Bind(R.id.et_login_uername)
    BoundEditText mEtUsername;

    @Bind(R.id.et_login_password)
    BoundEditText mEtpwd;

    @Bind(R.id.layout_login)
    LinearLayout mLayoutLogin;

    @Bind(R.id.layoutChangeshow)
    RelativeLayout mLayoutChangeShow;

    @Bind(R.id.mIvVisualized)
    ImageView mIvVisualized;

    @Bind(R.id.ivUserIcon)
    MultiShapeView mIvUserIcon;

    /** 注册按钮 */
    @Bind(R.id.mTvRegister)
    TextView mTvRegister;
    /** 忘记密码按钮 */
    @Bind(R.id.tvForgetpwd)
    TextView mForgetPwdTextView;

    @Bind(R.id.mTvVersion)
    TextView mTvVersion;

    /** 屏幕的高度 */
    private int mScreenHeight;

    /** 登陆界面向上移动的高度 */
    private int mScroll_dx;

    /** 是否可见图标 */
    private boolean isVisualized = false;

    /*进度提示框*/
    private ProgressHUD mDialog;
    LoginPresenter  loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
        mStatusView.init(mScreenHeight,mLayoutLogin,mBtnLogin);
        mStatusView.setSoftKeyBoardListener(this);
        mBtnSetting.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mForgetPwdTextView.setOnClickListener(this);
        mEtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isEmpty(s.toString())) {
                    mEtpwd.setText("");
                }
//                afterInputChanged(s, mEtpwd.getText());
                judgeFillLoginParams();
            }
        });
        mEtpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeFillLoginParams();
            }
        });

        mEtpwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                } else if (actionId == EditorInfo.IME_NULL) {
//                    getIPresenter().doRequestLoginAction(mEtUsername.getText().toString(), mEtpwd.getText().toString());
                }
                return false;
            }
        });

        mEtUsername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                KeyboardUtils.openKeybord(LoginActivity.this,mEtUsername);
                return false;
            }
        });
        mEtpwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                KeyboardUtils.openKeybord(LoginActivity.this,mEtpwd);
                return false;
            }
        });
        mLayoutChangeShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPasswordShowClick(view);
            }
        });

    }



    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {
         if (type.equals(BusinessBroadcastUtils.TYPE_LOCAL_REGISTER_SUCCESS_AUTOLOGIN)){
             LoginPerson loginPerson=(LoginPerson)mode;
             mEtUsername.setText(loginPerson.getLoginId());
             mEtpwd.setText("");
         }

    }


    @Override
    public void initUIView() {
        
    }


    public void initData() {
        loginPresenter=new LoginPresenter(this);
        mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        OperaCursorLocation();
        Intent intent = getIntent();
        if (intent != null) {
            String mShowDiaLogMessage = intent.getStringExtra(SHOW_DIALOG_MSG);
            if (mShowDiaLogMessage != null) {
                showMsg(mShowDiaLogMessage);
            }
        }
        String version = "v" + AppInfo.getAppLocalizedVerion(this);
        mTvVersion.setText(version);
        setForgetPwdVisible();
        judgeFillLoginParams();
        mIvUserIcon.setImageResource(R.drawable.ic_login_default_user);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setForgetPwdVisible();
    }

    private void setForgetPwdVisible() {
//        Configuration.AccountSecurity security = ConfigurationManager.get().getAccountSecurity();
//        if (!security.isEnablePasswordEdit()) {
//            mForgetPwdTextView.setVisibility(View.GONE);
//        } else {
//            mForgetPwdTextView.setVisibility(View.VISIBLE);
//        }
    }

    /**
     * 判断是否已经填写用户名和密码
     */
    private void judgeFillLoginParams() {
        if (StringUtils.isEmpty(mEtUsername.getText().toString())
                || StringUtils.isEmpty(mEtpwd.getText().toString())) {
            mBtnLogin.setEnabled(false);
        } else {
            mBtnLogin.setEnabled(true);
        }
    }

    /**
     * 显示弹框信息
     *
     * @param msg
     */
    private void showMsg(String msg) {
        CustomDialog dialog = new CustomDialog(this, false, false);
        dialog.setTitle(getString(R.string.inserting_coil_notification));
        dialog.setBodyText(msg);
        dialog.setCancelVisible(View.GONE);
        dialog.setOKVisible(View.VISIBLE);
        dialog.show();
    }

    /**
     * 操作输入框光标的位置
     */
    private void OperaCursorLocation() {
        if (StringUtils.isEmpty(mEtUsername.getText().toString())) {
            mEtUsername.setFocusable(true);
            mEtUsername.setFocusableInTouchMode(true);
            mEtUsername.requestFocus();
            mEtUsername.findFocus();
        } else if (!StringUtils.isEmpty(mEtUsername.getText().toString()) && StringUtils.isEmpty(mEtpwd.getText().toString())) {
            //让光标自动移到输入密码的地方
            mEtpwd.setFocusable(true);
            mEtpwd.setFocusableInTouchMode(true);
            mEtpwd.requestFocus();
            mEtpwd.findFocus();
        }
    }


    @Override
    public void keyBoardStatus(int w, int h, int oldw, int oldh) {

    }

    @Override
    public void keyBoardVisable(int move) {
        //设置登陆布局向上移动的高度 如果是从登陆按钮开始算起那么 登陆按钮以上不遮挡
        //// TODO: 2017/2/9 move to presenter?
        int[] location = new int[2];
        mTvRegister.getLocationOnScreen(location);
        int btnToBottom = mScreenHeight - location[1] - mTvRegister.getHeight();
        mScroll_dx = btnToBottom > move ? 0 : move - btnToBottom;
//        mScroll_dx=move;
        mLayoutLogin.scrollBy(0, mScroll_dx);
        mTvVersion.setVisibility(View.INVISIBLE);
    }

    @Override
    public void keyBoardInvisable(int move) {
        mLayoutLogin.scrollBy(0, -mScroll_dx);

        mTvVersion.setVisibility(View.VISIBLE);
    }


    public void onPasswordShowClick(View view) {
        if (isVisualized) {
            isVisualized = false;
            //设置密码为隐藏的
            mIvVisualized.setImageResource(R.drawable.ic_login_visualized);
            mEtpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mEtpwd.setSelection(mEtpwd.getText().length());
        } else {
            isVisualized = true;
            //设置EditText的密码为可见的
            mIvVisualized.setImageResource(R.drawable.ic_login_not_visualized);
            mEtpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mEtpwd.setSelection(mEtpwd.getText().length());
        }
    }

    @Override
    public void onClick(View v) {
         if(v==mBtnLogin){
           login();
//           loginPresenter.login(mEtUsername.getText().toString().trim(),mEtpwd.getText().toString().trim());
//
//             KeyboardUtils.closeKeybord(this);

         }
        else  if (v == mBtnSetting) {
            startActivity(new Intent(this, SettingActivity.class));
        } else if (v == mTvRegister) {
//            RegisterNewAccountFragment newAccountFragment=new RegisterNewAccountFragment();
//            FragmentHelper.showFrag(this, R.id.flyt,newAccountFragment,null);
//
//            AddressFragment newAccountFragment=new AddressFragment();

            CheckUserExitFragment accountValidateFragment=new CheckUserExitFragment();
            FragmentHelper.showFrag(this, R.id.flyt,accountValidateFragment,null);
        } else if (v == mForgetPwdTextView) {
//            GetBackPasswordFragment newAccountFragment=new GetBackPasswordFragment();
//            FragmentHelper.showFrag(this, R.id.flyt,newAccountFragment,null);
//            getIPresenter().onForgetPasswordClicked();
        }
    }
    
   public void login(){
       mDialog=new ProgressHUD(this);
       mDialog.show(getContext(),"",true,true,null,null);
       loginPresenter.login(mEtUsername.getText().toString().trim(),mEtpwd.getText().toString().trim());

       KeyboardUtils.closeKeybord(this);
   }

    public void enableLoginButton(boolean isEnable) {
        if (isEnable) {
            mBtnLogin.setEnabled(true);
            mBtnLogin.getBackground().setAlpha(255);
            mBtnLogin.setTextColor(getResources().getColor(R.color.white));
        } else {
            mBtnLogin.setEnabled(false);
            mBtnLogin.getBackground().setAlpha(128);
            mBtnLogin.setTextColor(getResources().getColor(R.color.common_bt_unable_text_color));
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void loadDataStart() {

    }

    @Override
    public void showToast(final  String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();

                ToastUtils.show(LoginActivity.this,text);

            }
        });
    }

    @Override
    public void loginSucess() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                finish();

            }
        });


    }

    @Override
    public void loginFails() {
        mDialog.dismiss();
        showToast("登录失败");
    }
}
