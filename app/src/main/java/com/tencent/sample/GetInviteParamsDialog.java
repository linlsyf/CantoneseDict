
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

public class GetInviteParamsDialog extends Dialog implements
        View.OnClickListener {

    public interface OnGetInviteParamsCompleteListener {
        public void onGetParamsComplete(Bundle params);
    }

    private OnGetInviteParamsCompleteListener mListener = null;

    private Context mContext = null;

    private Bundle mParams = null;

    private Button mBtCommit = null;
    private TextView mTvSource = null;
    private TextView mTvPicurl = null;
    private TextView mTvDesc = null;
    private Spinner mSpOnly = null;
    private TextView mTvReceiver = null;
    private TextView mTvExclude = null;
    private TextView mTvSpecified = null;

    public GetInviteParamsDialog(Context context,
            OnGetInviteParamsCompleteListener listener) {
        super(context, R.style.Dialog_Fullscreen);
        // TODO Auto-generated constructor stub
        mContext = context;
        mListener = listener;
        mParams = new Bundle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_invite_params_dialog);

        findViews();

        setupViews();
    }

    private void findViews() {
        mTvSource = (TextView) findViewById(R.id.et_source);
        mTvPicurl = (TextView) findViewById(R.id.et_picurl);
        mTvDesc = (TextView) findViewById(R.id.et_desc);
        mSpOnly = (Spinner) findViewById(R.id.sp_only);
        mTvReceiver = (TextView) findViewById(R.id.et_receiver);
        mTvExclude = (TextView) findViewById(R.id.et_exclude);
        mTvSpecified = (TextView) findViewById(R.id.et_specified);
        mBtCommit = (Button) findViewById(R.id.bt_commit);

        mTvPicurl.setText("http://login.imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        mTvDesc.setText("AndroidSdk_2_0: invite description!");
    }

    private void setupViews() {
        mBtCommit.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item);
        adapter.add("0");
        adapter.add("1");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpOnly.setAdapter(adapter);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == mBtCommit) {
            getInputParams();
            mListener.onGetParamsComplete(mParams);
            this.dismiss();
        }
    }

    private void getInputParams() {
        // source
        if (mTvSource.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_SOURCE, URLEncoder.encode(mTvSource.getText().toString()));
        } else {
            mParams.putString(SocialConstants.PARAM_SOURCE, "");
        }
        // picurl
        if (mTvPicurl.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_APP_ICON, mTvPicurl.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_APP_ICON, "");
        }
        // desc
        if (mTvDesc.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_APP_DESC, mTvDesc.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_APP_DESC, "");
        }
        // only
        if (mSpOnly.getSelectedItem() != null) {
            mParams.putString("only", (String) mSpOnly.getSelectedItem());
        }
        // receiver
        if (mTvReceiver.getText().toString().length() != 0){
            mParams.putString(SocialConstants.PARAM_RECEIVER, mTvReceiver.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_RECEIVER, "");
        }
        // exclude
        if (mTvExclude.getText().length() != 0){
            mParams.putString("exclude", mTvExclude.getText().toString());
        } else {
            mParams.putString("exclude", "");
        }
        // specified
        if (mTvSpecified.getText().length() != 0){
            mParams.putString("specified", mTvSpecified.getText().toString());
        } else {
            mParams.putString("specified", "");
        }
    }
}
