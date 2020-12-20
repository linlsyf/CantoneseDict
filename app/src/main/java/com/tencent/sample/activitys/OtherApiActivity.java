package com.tencent.sample.activitys;

import org.json.JSONException;
import org.json.JSONObject;

import com.linlsyf.area.R;
import com.tencent.connect.UserInfo;
import com.tencent.sample.BaseUIListener;
import com.tencent.sample.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class OtherApiActivity extends BaseActivity implements OnClickListener {
	private static final String SCOPE = "get_user_info,get_simple_userinfo,get_user_profile,get_app_friends,upload_photo,"
			+ "add_share,add_topic,list_album,upload_pic,add_album,set_user_face,get_vip_info,get_vip_rich_info";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setBarTitle("其他");
		setLeftButtonEnable();
		setContentView(R.layout.other_api_activity);
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_container);
		for (int i = 0; i < linearLayout.getChildCount(); i++) {
			View view = linearLayout.getChildAt(i);
			if (view instanceof Button) {
				view.setOnClickListener(this);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.main_is_SupportShareToQQ_btn: {
				boolean isurpport = com.tencent.tauth.Tencent.isSupportShareToQQ(OtherApiActivity.this);
				Util.showResultDialog(OtherApiActivity.this, "isSupportShareToQQ = " + isurpport, "结果");
			}
			break;

			case R.id.main_is_SupportPushToQZone_btn: {
				boolean isurpport = com.tencent.tauth.Tencent.isSupportPushToQZone(OtherApiActivity.this);
				Util.showResultDialog(OtherApiActivity.this, "isSupportPushToQZone = " + isurpport, "结果");
			}
			break;

			case R.id.main_is_report_dau_btn: {
				MainActivity.mTencent.reportDAU();
			}
			break;
            case R.id.get_openid_btn:
                onClickOpenId();
                break;
            case R.id.check_login_btn:
                onClickCheckLogin();
                break;

		}
	}

	private void onClickOpenId() {
		if (MainActivity.ready(OtherApiActivity.this)) {
			UserInfo info = new UserInfo(this,MainActivity.mTencent.getQQToken());
			info.getOpenId(new OtherApiListener("m_me", true,this));
			Util.showProgressDialog(OtherApiActivity.this, null, null);
		}
	}

	private void onClickCheckLogin() {
		if (MainActivity.ready(OtherApiActivity.this)) {
			MainActivity.mTencent.checkLogin(new BaseUIListener(this));
			Util.showProgressDialog(OtherApiActivity.this, null, null);
		}
	}

	/**
	 *
	 * @author zivonchen
	 *
	 */
	private class OtherApiListener extends BaseUIListener {
		private String mScope = "all";
        private Boolean mNeedReAuth = false;
        private Activity mActivity;
    	public OtherApiListener(String scope, boolean needReAuth,
				Activity activity) {
			super(activity);
			this.mScope = scope;
			this.mNeedReAuth = needReAuth;
			this.mActivity = activity;
		}
		@Override
		public void onComplete(Object response) {
			try {
				final Activity activity = OtherApiActivity.this;
				JSONObject json = (JSONObject)response;
				int ret = json.getInt("ret");
				if (ret == 0) {
					Message msg = mHandler.obtainMessage(0, mScope);
					Bundle data = new Bundle();
					data.putString("response", response.toString());
					msg.setData(data);
					mHandler.sendMessage(msg);
				} else if (ret == 100030) {
					if (mNeedReAuth) {
						Runnable r = new Runnable() {
							@Override
                            public void run() {
								MainActivity.mTencent.reAuth(activity,
										mScope, new BaseUIListener(OtherApiActivity.this));
							}
						};
						OtherApiActivity.this.runOnUiThread(r);
					}
				}else {
					Message msg = mHandler.obtainMessage(0,mScope);
					Bundle data = new Bundle();
					data.putString("response", response.toString());
					msg.setData(data);
					mHandler.sendMessage(msg);
				}
			} catch (JSONException e) {
				Util.toastMessage(OtherApiActivity.this,
						"onComplete() JSONException: " + response.toString());
			}
			Util.dismissDialog();
		}
	}

	/**
	 * 异步显示结果
	 */
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Context context = OtherApiActivity.this;
			String scope = msg.obj.toString();
			String response = msg.getData().getString("response");
			if (response != null) {
				// 换行显示
				response = response.replace(",", "\r\n");
			}
			AlertDialog dialog = new AlertDialog.Builder(context)
					.setMessage(response).setNegativeButton("知道啦", null)
					.create();
			dialog.setTitle(scope);
			dialog.show();
		};
	};
}
