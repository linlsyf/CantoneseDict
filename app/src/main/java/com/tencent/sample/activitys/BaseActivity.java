package com.tencent.sample.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linlsyf.cantonese.R;
import com.tencent.sample.AppConstants;
import com.tencent.sample.Util;
import com.tencent.tauth.Tencent;

public class BaseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.base_activity_with_titlebar);
		setTitle(null);
		setLeftButton("返回", new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

    protected void checkTencentInstance() {
        if (null == MainActivity.mTencent) {
            final EditText mEtAppid = new EditText(this);
            mEtAppid.setText(MainActivity.mAppid);
            final DialogInterface.OnClickListener mAppidCommitListener = new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            // 用输入的appid
                            String editTextContent = mEtAppid.getText().toString().trim();
                            if (!TextUtils.isEmpty(editTextContent)) {
                                MainActivity.mAppid = editTextContent;
                            }
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            // 默认appid
                            break;
                    }
                    MainActivity.mTencent = Tencent.createInstance(MainActivity.mAppid, BaseActivity.this);
                }
            };

            new AlertDialog.Builder(this).setTitle("请输入APP_ID").setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_info).setView(mEtAppid)
                    .setPositiveButton("Commit", mAppidCommitListener)
                    .setNegativeButton("Cancel", mAppidCommitListener).show();
        }
    }

	@Override
    protected void onDestroy() {
        super.onDestroy();
        Util.release();
    }

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			Util.dismissDialog();
		}
		return super.onKeyDown(keyCode, event);
	}
	public void setLeftButtonEnable(){
		((Button)findViewById(R.id.left_button)).setVisibility(View.VISIBLE);
	}
	@Override
	public void setContentView(int layoutResID) {
		LayoutInflater inflater = LayoutInflater.from(this);
		((LinearLayout)findViewById(R.id.layout_content)).addView(inflater.inflate(layoutResID, null));
	}

	public void setLeftButton(String title, OnClickListener listener){
		Button leftButton = (Button)findViewById(R.id.left_button);
		leftButton.setText(title);
		leftButton.setOnClickListener(listener);
	}

	public void setLeftButton(int strId, OnClickListener listener){
		Button leftButton = (Button)findViewById(R.id.left_button);
		leftButton.setText(getResources().getString(strId));
		leftButton.setOnClickListener(listener);
	}

	public void setBarTitle(String title){
		((TextView)findViewById(R.id.base_title)).setText(title);
	}
}
