
package com.tencent.sample;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.linlsyf.cantonese.R;
import com.tencent.open.SocialConstants;

import java.net.URLEncoder;

public class GetPkBragParamsDialog extends Dialog implements
        View.OnClickListener {

    public interface OnGetPkBragParamsCompleteListener {
        public void onGetParamsComplete(Bundle params);
    }

    private OnGetPkBragParamsCompleteListener mListener = null;

    private Context mContext = null;

    private Bundle mParams = null;

    private Button mBtCommit = null;
    private TextView mTvMsg = null;
    private TextView mTvSource = null;
    private TextView mTvImg = null;
    private TextView mTvReceiver = null;
    private Spinner mSpOptions = null;

    public GetPkBragParamsDialog(Context context,
            OnGetPkBragParamsCompleteListener listener) {
        super(context, R.style.Dialog_Fullscreen);
        mContext = context;
        mListener = listener;
        mParams = new Bundle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_pk_brag_params_dialog);

        findViews();

        setupViews();
    }

    private void findViews() {
        mTvImg = (TextView) findViewById(R.id.et_img);
        mTvMsg = (TextView) findViewById(R.id.et_msg);
        mTvReceiver = (TextView) findViewById(R.id.et_receiver);
        mTvSource = (TextView) findViewById(R.id.et_source);
        mSpOptions = (Spinner) findViewById(R.id.sp_options);
        mBtCommit = (Button) findViewById(R.id.bt_commit);
    }

    private void setupViews() {
        mBtCommit.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item);
        adapter.add(mContext.getResources().getString(R.string.options1));
        adapter.add(mContext.getResources().getString(R.string.options2));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpOptions.setAdapter(adapter);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        mTvMsg.setText("向某某某发起挑战");
        mTvImg.setText("http://i.gtimg.cn/qzonestyle/act/qzone_app_img/app888_888_75.png");
        mTvReceiver.setText("4BE29C556418DE9A35469164C7B60B50");
    }

    @Override
    public void onClick(View v) {
        if (v == mBtCommit) {
            getInputParams();
            mListener.onGetParamsComplete(mParams);
            this.dismiss();
        }
    }

    private void getInputParams() {
        if (mTvImg.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_IMG_URL, mTvImg.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_IMG_URL, "");
        }
        if (mTvMsg.getText().toString() != null) {
            mParams.putString(SocialConstants.PARAM_SEND_MSG, mTvMsg.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_SEND_MSG, "");
        }
        if (mTvSource.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_SOURCE, URLEncoder.encode(mTvSource.getText().toString()));
        } else {
            mParams.putString(SocialConstants.PARAM_SOURCE, "");
        }
        if (mTvReceiver.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_RECEIVER, mTvReceiver.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_RECEIVER, "");
        }
        if (mSpOptions.getSelectedItem() != null) {
            if(((String) mSpOptions.getSelectedItem()).equals("挑战")) {
                mParams.putString(SocialConstants.PARAM_TYPE, "pk");
            } else {
                mParams.putString(SocialConstants.PARAM_TYPE, "brag");
            }
        }
    }
}
