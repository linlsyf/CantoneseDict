
package com.tencent.sample.activitys;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.linlsyf.cantonese.R;
import com.tencent.connect.UserInfo;
import com.tencent.sample.BaseUIListener;
import com.tencent.sample.Util;

public class AccountInfoActivity extends BaseActivity implements OnClickListener {
	private UserInfo mInfo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_info_activity);
        setBarTitle("获取用户资料");
        setLeftButtonEnable();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_container);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(this);
            }
        }
        mInfo = new UserInfo(this, MainActivity.mTencent.getQQToken());

    }

    @Override
    public void onClick(View v) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        switch (v.getId()) {
            case R.id.user_info_btn:
                onClickUserInfo();
                v.startAnimation(shake);
                break;

        }
    }

    private void onClickUserInfo() {
        if (MainActivity.ready(this)) {
        	mInfo.getUserInfo(new BaseUIListener(this,"get_simple_userinfo"));
            Util.showProgressDialog(this, null, null);
        }
    }

}
