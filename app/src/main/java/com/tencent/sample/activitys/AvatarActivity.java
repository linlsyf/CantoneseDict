package com.tencent.sample.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.linlsyf.area.R;
import com.tencent.connect.common.Constants;
import com.tencent.sample.BaseUIListener;
import com.tencent.sample.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


public class AvatarActivity extends BaseActivity implements OnClickListener {
	private static final int REQUEST_SET_AVATAR = 2;
	private static final int REQUEST_SET_AVATAR2 = 3;
	private static final int REQUEST_SET_DAYNMIC_AVATAR = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBarTitle("用户头像");
		setLeftButtonEnable();
		setContentView(R.layout.avatar_activity);
		findViewById(R.id.set_avatar_btn).setOnClickListener(this);
		findViewById(R.id.set_avatar_btn2).setOnClickListener(this);
		findViewById(R.id.set_dynamic_btn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.set_avatar_btn:
			onClickSetAvatar();
			break;
		case R.id.set_avatar_btn2:
			onClickSetAvatar2();
			break;
		case R.id.set_dynamic_btn:
			onClickSetDynamicAvatar();
			break;
		default:
			break;
		}

	}

    private void onClickSetAvatar() {
        if (MainActivity.ready(AvatarActivity.this)) {
            Intent intent = new Intent();
            // 开启Pictures画面Type设定为image
            intent.setType("image/*");
            // 使用Intent.ACTION_GET_CONTENT这个Action
            intent.setAction(Intent.ACTION_GET_CONTENT);
            // 取得相片后返回本画面
            startActivityForResult(intent, REQUEST_SET_AVATAR);
            // 在 onActivityResult 中调用 doSetAvatar
        }
    }

	private void onClickSetAvatar2() {
		Intent intent = new Intent();
		// 开启Pictures画面Type设定为image
		intent.setType("image/*");
		// 使用Intent.ACTION_GET_CONTENT这个Action
		intent.setAction(Intent.ACTION_GET_CONTENT);
		// 取得相片后返回本画面
		startActivityForResult(intent, REQUEST_SET_AVATAR2);
		// 在 onActivityResult 中调用 doSetAvatar
	}

	private void onClickSetDynamicAvatar() {
		Intent intent = new Intent();
		// 开启Pictures画面Type设定为image
		intent.setType("video/*");
		// 使用Intent.ACTION_GET_CONTENT这个Action
		intent.setAction(Intent.ACTION_GET_CONTENT);
		// 取得相片后返回本画面
		startActivityForResult(intent, REQUEST_SET_DAYNMIC_AVATAR);
		// 在 onActivityResult 中调用 doSetAvatar

	}

    private void doSetAvatar(Uri uri) {
		Bundle params = new Bundle();
		params.putString(Constants.PARAM_AVATAR_URI,uri.toString());
		params.putInt("exitAnim",R.anim.zoomout);
		MainActivity.mTencent.setAvatar(this, params, new BaseUIListener(this));
    }

	private void doSetAvatar2(Uri uri) {
		MainActivity.mTencent.setAvatarByQQ(this, uri, new BaseUIListener(this));
	}

	private void doSetDynamicAvatar(Uri uri) {
		MainActivity.mTencent.setDynamicAvatar(this, uri, new BaseUIListener(this));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_SET_AVATAR && resultCode == Activity.RESULT_OK) {
			if (data != null) {
				doSetAvatar(data.getData());
			} else {
				Util.toastMessage(AvatarActivity.this, "数据为空,请重新选择");
			}
		}else if (requestCode == REQUEST_SET_AVATAR2 && resultCode == Activity.RESULT_OK) {
			if (data != null) {
				doSetAvatar2(data.getData());
			} else {
				Util.toastMessage(AvatarActivity.this, "数据为空,请重新选择");
			}
		} else if (requestCode == REQUEST_SET_DAYNMIC_AVATAR) {
			if (data != null) {
				doSetDynamicAvatar(data.getData());
			} else {
				Util.toastMessage(AvatarActivity.this, "数据为空,请重新选择");
			}
		} else if(requestCode == Constants.REQUEST_EDIT_AVATAR){
			Tencent.onActivityResultData(requestCode, resultCode, data, setAvatarListener);
		} else if (requestCode == Constants.REQUEST_EDIT_DYNAMIC_AVATAR) {
			Tencent.onActivityResultData(requestCode, resultCode, data, setAvatarListener);
		}
	}

	IUiListener setAvatarListener = new IUiListener() {
		@Override
		public void onCancel() {
			Util.toastMessage(AvatarActivity.this, "设置取消");
		}
		@Override
		public void onComplete(Object response) {
			// TODO Auto-generated method stub
			Util.toastMessage(AvatarActivity.this, "设置成功：" + response.toString());
		}
		@Override
		public void onError(UiError e) {
			// TODO Auto-generated method stub
			Util.toastMessage(AvatarActivity.this, "设置失败");
		}
	};
	
}
