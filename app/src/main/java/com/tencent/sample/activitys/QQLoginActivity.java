package com.tencent.sample.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.business.BusinessBroadcastUtils;
import com.core.base.BasicActivity;
import com.core.base.GlobalConstants;
import com.linlsyf.area.R;
import com.tencent.connect.UnionInfo;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.AuthAgent;
import com.tencent.connect.common.Constants;
import com.tencent.open.im.IM;
import com.tencent.open.log.SLog;
import com.tencent.open.miniapp.MiniApp;
import com.tencent.sample.PermissionMgr;
import com.tencent.sample.TencentUser;
import com.tencent.sample.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

import javax.microedition.khronos.opengles.GL;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.GET_TASKS;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class QQLoginActivity extends BasicActivity {
    private static final String TAG = QQLoginActivity.class.getName();
    public static final String KEY_FORCE_QR_LOGIN = "KEY_FORCE_QR_LOGIN";
    public static String mAppid;

	private static final String SHARE_PREF_NAME = "openSdk.pref";
	private static final String KEY_TARGET_QQ_UIN = "target.uin";
	private static final String KEY_TARGET_QQ_MINIAPP_ID = "target.miniappid";

	private static final String OPEN_CONNECT_DEMO_MINI_APP_ID = "1108108864";
	private static final String OPEN_CONNECT_DEMO_MINI_APP_PATH = "pages/tabBar/index/index";

    private static final String[] PERMISSIONS = new String[] {INTERNET, ACCESS_NETWORK_STATE, WRITE_EXTERNAL_STORAGE,
            ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, ACCESS_LOCATION_EXTRA_COMMANDS,
            CHANGE_WIFI_STATE, ACCESS_WIFI_STATE, READ_PHONE_STATE, GET_TASKS};

	private Button mNewLoginButton;
    private Button mServerSideLoginBtn;
	private TextView mUserInfo;
	private ImageView mUserLogo;
    private UserInfo mInfo;
	private EditText mEtAppid = null;
	private EditText mEtTargetUin = null, mEtTargetMiniAppId = null, mEtTargetMiniAppPath = null, mEtTargetMiniAppVersion = null;
	private AlertDialog.Builder mTargetUinBuilder = null, mTargetMiniAppIdBuilder = null;
	public static Tencent mTencent;
    private static Intent mPrizeIntent = null;
    private static boolean isServerSideLogin = false;
	private CheckBox mQrCk;
    private CheckBox mCheckForceQr;
    private CheckBox mOEMLogin;
    private int mChosenIMType;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "-->onCreate");
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//
		// 固定竖屏
		setContentView(R.layout.activity_main_new);
		initViews();

		mAppid="1106656045";

		    if (mTencent == null) {
		        mTencent = Tencent.createInstance(mAppid, this);
		}
		
        // 获取有奖分享的intent信息
        if (null != getIntent()) {
            mPrizeIntent = getIntent();
        }

		login();
    }

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		PermissionMgr.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	}

    /**
     * 有奖分享处理，未接入有奖分享可以不考虑
     */
    private void handlePrizeShare() {
    }


	@Override
	protected void onStart() {
		Log.d(TAG, "-->onStart");
		PermissionMgr.getInstance().requestPermissions(this);

		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "-->onResume");
        // 有奖分享处理
        handlePrizeShare();
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "-->onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "-->onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "-->onDestroy");
		super.onDestroy();

	}

	private void initViews() {
		mNewLoginButton = (Button) findViewById(R.id.new_login_btn);
		mServerSideLoginBtn = (Button) findViewById(R.id.server_side_login_btn);

		mQrCk = (CheckBox) findViewById(R.id.ck_qr);
        mCheckForceQr = (CheckBox) findViewById(R.id.check_force_qr);
        mCheckForceQr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.getId() == R.id.check_force_qr) {
                    mQrCk.setChecked(b);
                }
            }
        });
        mOEMLogin = (CheckBox)findViewById(R.id.check_oem_login);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_container);
		OnClickListener listener = new NewClickListener();
		for (int i = 0; i < linearLayout.getChildCount(); i++) {
			View view = linearLayout.getChildAt(i);
			if (view instanceof Button) {
				view.setOnClickListener(listener);
			}
		}
		mNewLoginButton.setOnClickListener(listener);
		mQrCk.setOnClickListener(listener);
		mUserInfo = (TextView) findViewById(R.id.user_nickname);
		mUserLogo = (ImageView) findViewById(R.id.user_logo);
		updateLoginButton();
	}

	private void updateLoginButton() {
		if (mTencent != null && mTencent.isSessionValid()) {
            if (isServerSideLogin) {
                mNewLoginButton.setTextColor(Color.BLUE);
                mNewLoginButton.setText("登录");
                mServerSideLoginBtn.setTextColor(Color.RED);
                mServerSideLoginBtn.setText("退出Server-Side账号");
            } else {
                mNewLoginButton.setTextColor(Color.RED);
                mNewLoginButton.setText("退出帐号");
                mServerSideLoginBtn.setTextColor(Color.BLUE);
                mServerSideLoginBtn.setText("Server-Side登陆");
            }
		} else {
			mNewLoginButton.setTextColor(Color.BLUE);
			mNewLoginButton.setText("登录");
            mServerSideLoginBtn.setTextColor(Color.BLUE);
            mServerSideLoginBtn.setText("Server-Side登陆");
		}
	}

	private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);
					new Thread(){

						@Override
						public void run() {
							JSONObject json = (JSONObject)response;
							if(json.has("figureurl")){
								Bitmap bitmap = null;
								try {
									bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
								} catch (JSONException e) {
									SLog.e(TAG, "Util.getBitmap() jsonException : " + e.getMessage());
								}
								Message msg = new Message();
								msg.obj = bitmap;
								msg.what = 1;
								mHandler.sendMessage(msg);
							}
						}

					}.start();
				}

				@Override
				public void onCancel() {

				}
			};
			mInfo = new UserInfo(this, mTencent.getQQToken());
			mInfo.getUserInfo(listener);
//			GlobalConstants.init().setQQToken(mTencent.getQQToken());//ldh

//			BusinessBroadcastUtils.sendBroadcast(QQLoginActivity.this,BusinessBroadcastUtils.TYPE_LOGIN_QQ,null);
              finish();
//			GlobalConstants.init().getQQUser();

		} else {
			mUserInfo.setText("");
			mUserInfo.setVisibility(View.GONE);
			mUserLogo.setVisibility(View.GONE);
		}
	}

	private void getUnionId() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {
				@Override
				public void onError(UiError e) {
					Toast.makeText(QQLoginActivity.this,"onError",Toast.LENGTH_LONG).show();
				}

				@Override
				public void onComplete(final Object response) {
					if(response != null){
						JSONObject jsonObject = (JSONObject)response;
						try {
							String unionid = jsonObject.getString("unionid");
							Util.showResultDialog(QQLoginActivity.this, "unionid:\n"+unionid, "onComplete");
							Util.dismissDialog();
						}catch (Exception e){
							Toast.makeText(QQLoginActivity.this,"no unionid",Toast.LENGTH_LONG).show();
						}
					}else {
						Toast.makeText(QQLoginActivity.this,"no unionid",Toast.LENGTH_LONG).show();
					}
				}

				@Override
				public void onCancel() {
					Toast.makeText(QQLoginActivity.this,"onCancel",Toast.LENGTH_LONG).show();
				}
			};
			UnionInfo unionInfo = new UnionInfo(this, mTencent.getQQToken());
			unionInfo.getUnionId(listener);
		} else {
			Toast.makeText(this,"please login frist!",Toast.LENGTH_LONG).show();
		}
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				JSONObject response = (JSONObject) msg.obj;
				if (response.has("nickname")) {
					try {
						mUserInfo.setVisibility(View.VISIBLE);
						mUserInfo.setText(response.getString("nickname"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}else if(msg.what == 1){
				Bitmap bitmap = (Bitmap)msg.obj;
				mUserLogo.setImageBitmap(bitmap);
				mUserLogo.setVisibility(View.VISIBLE);
			}
		}

	};

	private void onClickLogin() {
		if (!mTencent.isSessionValid()) {
		    this.getIntent().putExtra(AuthAgent.KEY_FORCE_QR_LOGIN, mCheckForceQr.isChecked());

		    if (mOEMLogin.isChecked()) {
				mTencent.loginWithOEM(this, "all", loginListener, mQrCk.isChecked(),
						"10000144","10000144","xxxx");
			} else {
				if (!mQrCk.isChecked()) {
					mTencent.login(this, "all", loginListener);
				} else {
					mTencent.login(this, "all", loginListener, true);
				}
			}
            isServerSideLogin = false;
			Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
		} else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(this);
                mTencent.login(this, "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
		    mTencent.logout(this);
            // 第三方也可以选择注销的时候不去清除第三方的targetUin/targetMiniAppId
            saveTargetUin("");
            saveTargetMiniAppId("");
			updateUserInfo();
			updateLoginButton();
		}
	}

	public void login(){
		if (!mTencent.isSessionValid()) {
			this.getIntent().putExtra(AuthAgent.KEY_FORCE_QR_LOGIN, mCheckForceQr.isChecked());

			if (mOEMLogin.isChecked()) {
				mTencent.loginWithOEM(this, "all", loginListener, mQrCk.isChecked(),
						"10000144","10000144","xxxx");
			} else {
				if (!mQrCk.isChecked()) {
					mTencent.login(this, "all", loginListener);
				} else {
					mTencent.login(this, "all", loginListener, true);
				}
			}
			isServerSideLogin = false;
			Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
		}
	}

	  public void logOut(){
//		  if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
//			  mTencent.logout(this);
//			  mTencent.login(this, "all", loginListener);
//			  isServerSideLogin = false;
//			  Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
//			  return;
//		  }
		  mTencent.logout(this);
		  // 第三方也可以选择注销的时候不去清除第三方的targetUin/targetMiniAppId
		  saveTargetUin("");
		  saveTargetMiniAppId("");
		  updateUserInfo();
		  updateLoginButton();
	  }
	/**
	 * 1.此处做了优化，点击聊天/语音/视频会去做token的校验
	 * 2.根据情况进行自动登录并且回调
	 * 3.回调后根据不同情况拉起不同业务
	 */
	private void onClickIm() {
		// 调试的时候，使用mTencent.isSessionValid()，因为是当次的
		// 实际使用的时候，使用存储时间更长的sharePreference，mTencent.checkSessionValid(mAppid)
		if (mTencent.isSessionValid()) {
			// 拉起AIO
			// 如果还是失败,意味着token不为空且不过期，但是由于修改密码/被锁定等原因需重新授权，则需重新校验(暂时看看QQ这边是否会有这种情况)
            // jumpIMWithType(mChosenIMType);
			buildUinDialog();
		} else {
			// 根据产品的更改，登录自己完成授权
			Toast.makeText(this, R.string.please_click_login_btn, Toast.LENGTH_LONG).show();
		}
	}

	private void buildUinDialog() {
		mEtTargetUin = new EditText(QQLoginActivity.this);
		mTargetUinBuilder = new AlertDialog.Builder(QQLoginActivity.this).setTitle("请输入Target的QQ号")
				.setCancelable(false)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(mEtTargetUin)
				.setPositiveButton("Commit", mTargetUinCommitListener);
		mTargetUinBuilder.show();
	}

    /**
     * 根据传参拉起不同的IM业务
     *
     * @param type
     */
	private void jumpIMWithType(int type) {
	    int ret = IM.IM_UNKNOWN_TYPE;
	    if (type == Constants.IM_AIO) {
            ret = mTencent.startIMAio(this, getTargetUin(), getPackageName());
        } else if (type == Constants.IM_AUDIO_CHAT) {
	        ret = mTencent.startIMAudio(this, getTargetUin(), getPackageName());
        } else if (type == Constants.IM_VIDEO_CHAT) {
            ret = mTencent.startIMVideo(this, getTargetUin(), getPackageName());
        }
	    if (ret != IM.IM_SUCCESS) {
            Toast.makeText(getApplicationContext(),
                    "start IM conversation failed. error:" + ret,
                    Toast.LENGTH_LONG).show();
        }
    }

	/**
	 * 修改为无需登录授权
	 */
	private void onClickMiniApp() {
		// 拉起小程序/小游戏
		buildMiniAppIdDialog();
	}

	private void buildMiniAppIdDialog() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View miniAppContentView = layoutInflater.inflate(R.layout.dialog_qqminiapp, null);
		mEtTargetMiniAppId = (EditText) miniAppContentView.findViewById(R.id.mini_app_id_edit);
		mEtTargetMiniAppPath = (EditText) miniAppContentView.findViewById(R.id.mini_app_path_edit);
		mEtTargetMiniAppVersion = (EditText) miniAppContentView.findViewById(R.id.mini_app_version_edit);
		// MiniAppId 1108108864 此处和AppId 222222对应
		mEtTargetMiniAppId.setText(OPEN_CONNECT_DEMO_MINI_APP_ID);
		mEtTargetMiniAppPath.setText(OPEN_CONNECT_DEMO_MINI_APP_PATH);
		mEtTargetMiniAppVersion.setText(MiniApp.MINIAPP_VERSION_RELEASE);
		mTargetMiniAppIdBuilder = new AlertDialog.Builder(QQLoginActivity.this).setTitle(R.string.qqconnect_enter_tartget_mini_app_id_tip)
				.setCancelable(false)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(miniAppContentView)
				.setPositiveButton("Commit", mTargetMiniAppIdCommitListener);
		mTargetMiniAppIdBuilder.show();
	}

	/**
	 * 拉起小程序/小游戏
	 * appid能够直接拉取到该应用主体对应的apptype是小程序还是小游戏
	 * 此处暂时无需声明MiniApp的类型
	 */
	private void launchMiniApp() {
		int ret = MiniApp.MINIAPP_UNKNOWN_TYPE;
		ret = mTencent.startMiniApp(this, getTargetMiniAppId(), mEtTargetMiniAppPath.getText().toString(),
				mEtTargetMiniAppVersion.getText().toString());
		if (ret != MiniApp.MINIAPP_SUCCESS) {
			// 互联demo针对纯输入出错的地方进行文字提示
			String errorStr = "";
			if (ret == MiniApp.MINIAPP_ID_EMPTY) {
				errorStr = getString(R.string.qqconnect_mini_app_id_empty);
			} else if (ret == MiniApp.MINIAPP_ID_NOT_DIGIT) {
				errorStr = getString(R.string.qqconnect_mini_app_id_not_digit);
			}
			StringBuilder builder = new StringBuilder();
			builder.append("start miniapp failed. error:")
					.append(ret)
					.append(" ")
					.append(errorStr);
			Toast.makeText(getApplicationContext(),
					builder.toString(),
					Toast.LENGTH_LONG).show();
		}
	}

    private void onClickServerSideLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.loginServerSide(this, "all", loginListener);
            isServerSideLogin = true;
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
            if (!isServerSideLogin) { // SSO模式的登陆，先退出，再进行Server-Side模式登陆
                mTencent.logout(this);
                mTencent.loginServerSide(this, "all", loginListener);
                isServerSideLogin = true;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
            mTencent.logout(this);
            isServerSideLogin = false;
            updateUserInfo();
            updateLoginButton();
        }
    }

	public static boolean ready(Context context) {
		if (mTencent == null) {
			return false;
		}
		boolean ready = mTencent.isSessionValid()
				&& mTencent.getQQToken().getOpenId() != null;
		if (!ready) {
            Toast.makeText(context, "login and get openId first, please!",
					Toast.LENGTH_SHORT).show();
        }
		return ready;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    Log.d(TAG, "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
	    if (requestCode == Constants.REQUEST_LOGIN ||
	    	requestCode == Constants.REQUEST_APPBAR) {
	    	Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
	    }

	    super.onActivityResult(requestCode, resultCode, data);
	}

	public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }

	IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
        	Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
            updateLoginButton();
        }
    };

	IUiListener aioLoginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
			// sdk在QQToken的作了持久化,此处只是存在了内存
			initOpenidAndToken(values);
			updateUserInfo();


			// 此处统一逻辑，因为帮用户做了登录，所以也需修改相应信息
			updateLoginButton();
			// 判断target的QQ号有没有填写
			// 方便测试同事测试，此处每次都输入
			buildUinDialog();
		}
	};

	@Override
	public void initUIView() throws MalformedURLException {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {

	}

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {

	}

//	@Override
//	public void onBackPressed() {
//		super.onBackPressed();
//	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(QQLoginActivity.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(QQLoginActivity.this, "返回为空", "登录失败");
                return;
            }

			TencentUser  user=	JSON.parseObject( response.toString(), TencentUser.class);

//            GlobalConstants.getInstance().setQQUser(user);

//			GlobalConstants.init().set
//			Util.showResultDialog(QQLoginActivity.this, response.toString(), "登录成功");//ldh
            // 有奖分享处理
            handlePrizeShare();
			doComplete((JSONObject)response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Util.toastMessage(QQLoginActivity.this, "onError: " + e.errorDetail);
			Util.dismissDialog();
		}

		@Override
		public void onCancel() {
			Util.toastMessage(QQLoginActivity.this, "onCancel: ");
			Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
		}
	}

	private DialogInterface.OnClickListener mAppidCommitListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				// 用输入的appid
				String editTextContent = mEtAppid.getText().toString().trim();
				if (!TextUtils.isEmpty(editTextContent)) {
				    mAppid = editTextContent;
				} else {
                    Toast.makeText(QQLoginActivity.this, "appid为空，请重新设置", Toast.LENGTH_LONG).show();
                    return;
                }
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				// 默认appid
				break;
			}
			mTencent = Tencent.createInstance(mAppid, QQLoginActivity.this);
            // 有奖分享处理
            handlePrizeShare();
		}
	};

	private DialogInterface.OnClickListener mTargetUinCommitListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					// 用输入的targetUin
					String editTextContent = mEtTargetUin.getText().toString().trim();
					if (!TextUtils.isEmpty(editTextContent)) {
						saveTargetUin(editTextContent);
					} else {
						Toast.makeText(QQLoginActivity.this, "targetUin为空，请重新设置", Toast.LENGTH_LONG).show();
						return;
					}
					break;
			}
			// 拉起会话
            jumpIMWithType(mChosenIMType);
		}
	};

	private DialogInterface.OnClickListener mTargetMiniAppIdCommitListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					// 用输入的targetMiniAppId
					String editTextId = mEtTargetMiniAppId.getText().toString().trim();
					if (!TextUtils.isEmpty(editTextId)) {
						saveTargetMiniAppId(editTextId);
					} else {
						Toast.makeText(QQLoginActivity.this, getString(R.string.qqconnect_mini_app_id_empty), Toast.LENGTH_LONG).show();
						return;
					}
					// 校验版本类型
					String editTextVersion = mEtTargetMiniAppVersion.getText().toString();
					if (!MiniApp.OPEN_CONNECT_DEMO_MINI_APP_VERSIONS.contains(editTextVersion)) {
						Toast.makeText(QQLoginActivity.this, getString(R.string.qqconnect_mini_app_version_wrong), Toast.LENGTH_LONG).show();
						return;
					}
					break;
			}
			launchMiniApp();
		}
	};

	/**
	 * 设置成持久化，后续可以不用每次弹出
	 * @param targetUin
	 */
	private void saveTargetUin(String targetUin) {
		if (targetUin != null) {
			SharedPreferences share = this.getSharedPreferences(SHARE_PREF_NAME, 0);
			SharedPreferences.Editor editor = share.edit();
			editor.putString(KEY_TARGET_QQ_UIN, targetUin);
			editor.commit();
		}
	}

	private String getTargetUin() {
		SharedPreferences share = this.getSharedPreferences(SHARE_PREF_NAME, 0);
		return share.getString(KEY_TARGET_QQ_UIN, null);
	}

	/**
	 * 可以保存下来，开发者可以参考demo的保存方式
	 * @param miniAppId
	 */
	private void saveTargetMiniAppId(String miniAppId) {
		if (miniAppId != null) {
			SharedPreferences share = this.getSharedPreferences(SHARE_PREF_NAME, 0);
			SharedPreferences.Editor editor = share.edit();
			editor.putString(KEY_TARGET_QQ_MINIAPP_ID, miniAppId);
			editor.commit();
		}
	}

	private String getTargetMiniAppId() {
		SharedPreferences share = this.getSharedPreferences(SHARE_PREF_NAME, 0);
		return share.getString(KEY_TARGET_QQ_MINIAPP_ID, null);
	}

	class NewClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Context context = v.getContext();
			Animation shake = AnimationUtils.loadAnimation(context,
					R.anim.shake);
			Class<?> cls = null;
			boolean isAppbar = false;
			switch (v.getId()) {
			case R.id.new_login_btn:
				onClickLogin();
				v.startAnimation(shake);
				return;
			case R.id.main_aio_btn:
				mChosenIMType = Constants.IM_AIO;
				onClickIm();
				v.startAnimation(shake);
				return;
			case R.id.main_audio_chat_btn:
				mChosenIMType = Constants.IM_AUDIO_CHAT;
				onClickIm();
				v.startAnimation(shake);
				return;
			case R.id.main_video_chat_btn:
				mChosenIMType = Constants.IM_VIDEO_CHAT;
				onClickIm();
				v.startAnimation(shake);
				return;
			case R.id.main_mini_app_btn:
				onClickMiniApp();
				v.startAnimation(shake);
				return;
			case R.id.ck_qr:
				if(mQrCk.isChecked()){
					Toast.makeText(QQLoginActivity.this,"没有装手q时支持二维码登录，一般用于电视等设备",Toast.LENGTH_LONG).show();
				}
				return;
			case R.id.server_side_login_btn:
			    onClickServerSideLogin();
			    v.startAnimation(shake);
			    return;
			case R.id.main_sso_btn:
				{
					if (mTencent.isSupportSSOLogin(QQLoginActivity.this)) {
						Toast.makeText(QQLoginActivity.this, "支持SSO登陆", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(QQLoginActivity.this, "不支持SSO登陆", Toast.LENGTH_SHORT).show();
					}
				}
			    return;
			case R.id.main_getInfo_btn:
				cls = AccountInfoActivity.class;
				break;
			case R.id.app_get_unionid:
				getUnionId();
				break;
			case R.id.main_qqShare_btn:
				cls = QQShareActivity.class;
				break;
			case R.id.main_qzoneShare_btn:
				cls = QZoneShareActivity.class;
				break;
			case R.id.main_is_qq_installed_btn:
				Toast.makeText(QQLoginActivity.this, mTencent.isQQInstalled(QQLoginActivity.this) + "" , Toast.LENGTH_SHORT).show();
				break;
			case R.id.check_token_valid:
				JSONObject jsonObject = null;
				boolean isValid = mTencent.checkSessionValid(mAppid);
				if(!isValid) {
					Util.showResultDialog(QQLoginActivity.this, "token过期，请调用登录接口拉起手Q授权登录", "登录失败");
					return;
				} else {
					jsonObject = mTencent.loadSession(mAppid);
					mTencent.initSessionCache(jsonObject);
				}
				Util.showResultDialog(QQLoginActivity.this, jsonObject.toString(), "登录成功");
				updateUserInfo();
				updateLoginButton();
				break;

			case R.id.main_avatar_btn:
				cls = AvatarActivity.class;
				break;
			case R.id.main_emotion_btn:
				cls = EmotionActivity.class;
				break;

			case R.id.main_others_btn:
				cls = OtherApiActivity.class;
				break;

			}
			v.startAnimation(shake);
			if (cls != null) {
				Intent intent = new Intent(context, cls);
				if (isAppbar) { //APP内应用吧登录需接收登录结果
					startActivityForResult(intent, Constants.REQUEST_APPBAR);
				} else {
					context.startActivity(intent);
				}
			}
		}
	}
}
