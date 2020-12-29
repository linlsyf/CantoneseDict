package com.tencent.sample;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.linlsyf.cantonese.R;

public class GetLbsParamsDialog extends Dialog implements
View.OnClickListener {
	
	private Context mContext = null;
	private OnGetLbsParamsCompleteListener mListener = null;
	private Bundle mParams = null;
	
	private TextView mTvPage = null;
    private TextView mTvLatitude = null;
    private TextView mTvLongitude = null;
    private TextView mTvDistance = null;
    private TextView mTvReqnum = null;
    private Button mBtCommit = null;
	
	public interface OnGetLbsParamsCompleteListener {
        public void onGetParamsComplete(Bundle params);
    }

	public GetLbsParamsDialog(Context context, OnGetLbsParamsCompleteListener listener) {
        super(context, R.style.Dialog_Fullscreen);
        mContext = context;
        mListener = listener;
        mParams = new Bundle();
    }
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_lbs_params_dialog);
        findViews();
    }
	
	private void findViews() {
		mTvPage = (TextView) findViewById(R.id.et_page);
		mTvLatitude = (TextView) findViewById(R.id.et_latitude);
		mTvLongitude = (TextView) findViewById(R.id.et_longitude);
		mTvDistance = (TextView) findViewById(R.id.et_distance);
		mTvReqnum = (TextView) findViewById(R.id.et_reqnum);
        
		mBtCommit = (Button) findViewById(R.id.bt_commit);
		mBtCommit.setOnClickListener(this);
		
		getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	@Override
	public void onClick(View arg0) {
		if (arg0 == mBtCommit) {
			getInputParams();
            mListener.onGetParamsComplete(mParams);
            this.dismiss();
		} else {
            Toast.makeText(mContext, "Openid must not be empty",
                    Toast.LENGTH_SHORT).show();
        }
	}
	
	private void getInputParams() {
		if (checkInput(mTvPage.getText().toString())){
            mParams.putString("page", mTvPage.getText().toString());
        }
		
		if (checkInput(mTvLatitude.getText().toString())){
            mParams.putString("latitude", mTvLatitude.getText().toString());
        }
		
		if (checkInput(mTvLongitude.getText().toString())){
            mParams.putString("longitude", mTvLongitude.getText().toString());
        }
		
		if (checkInput(mTvDistance.getText().toString())){
            mParams.putString("distance", mTvDistance.getText().toString());
        }
		
		if (checkInput(mTvReqnum.getText().toString())){
            mParams.putString("reqnum", mTvReqnum.getText().toString());
        }
	}
	
	boolean checkInput(String s) {
        if (null == s || s.length() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
