
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
import android.widget.Toast;

import com.linlsyf.cantonese.R;
import com.tencent.open.SocialConstants;

import java.net.URLEncoder;
import java.util.Vector;

public class GetReactiveParamsDialog extends Dialog implements
        View.OnClickListener {

    public interface OnGetReactiveParamsCompleteListener {
        public void onGetParamsComplete(Bundle params);
    }

    private OnGetReactiveParamsCompleteListener mListener = null;

    private Context mContext = null;

    // private HashMap<String, Object> mHmParams = null;
    private Bundle mParams = null;

    private Vector<String> mOpenids = new Vector<String>();

    private Button mBtCommit = null;

    private TextView mTvTitle = null;
    private TextView mTvMsg = null;
    private TextView mTvImg = null;
    private TextView mTvReceiver = null;
    private TextView mTvSource = null;
    private TextView mTvRecimg = null;
    private TextView mTvRecimgDesc = null;

    public GetReactiveParamsDialog(Context context, OnGetReactiveParamsCompleteListener listener) {
        super(context, R.style.Dialog_Fullscreen);
        // TODO Auto-generated constructor stub
        mContext = context;
        mListener = listener;
        // mHmParams = new HashMap<String, Object>();
        mParams = new Bundle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_reactive_params_dialog);
        findViews();
        setupViews();
    }

    private void findViews() {
        mTvTitle = (TextView) findViewById(R.id.et_title);
        mTvMsg = (TextView) findViewById(R.id.et_msg);
        mTvImg = (TextView) findViewById(R.id.et_img);
        mTvReceiver = (TextView) findViewById(R.id.et_receiver);
        mTvSource = (TextView) findViewById(R.id.et_source);
        mBtCommit = (Button) findViewById(R.id.bt_commit);
        mTvRecimg = (TextView) findViewById(R.id.et_recimg);
        mTvRecimgDesc = (TextView) findViewById(R.id.et_recimg_desc);
		
		// add by carlyhuang 2013-4-6 修改赋值默认字段的地方
		mTvTitle.setText("title测试字段");
        mTvMsg.setText("msg测试字段");
        mTvImg.setText("http://i.gtimg.cn/qzonestyle/act/qzone_app_img/app888_888_75.png");
        
        mTvRecimgDesc.setText("发送者获得礼物的图片描述");
        mTvRecimg.setText("http://i.gtimg.cn/qzonestyle/act/qzone_app_img/app888_888_75.png");
    }

    private void setupViews() {
        mBtCommit.setOnClickListener(this);

        /*ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item);
        adapter2.add(mContext.getResources().getString(R.string.ask_gift_only1));
        adapter2.add(mContext.getResources().getString(R.string.ask_gift_only2));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpOnly.setAdapter(adapter2);*/

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onClick(View arg0) {
        if (arg0 == mBtCommit) {
            getInputParams();
            mListener.onGetParamsComplete(mParams);
            this.dismiss();
            // if (mHmParams.size() > 0) {
            // mListener.onGetParamsComplete(mHmParams);
            // this.dismiss();
            // } else {
            // mListener.onGetParamsComplete(null);
            // this.dismiss();
            // }
        } else {
            Toast.makeText(mContext, "Openid must not be empty",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void getInputParams() {
        // receiver，好友召回无receiver
        /*if (checkInput(mTvReceiver.getText().toString())){
            mParams.putString(SocialConstants.PARAM_RECEIVER, mTvReceiver.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_RECEIVER, "");
        }*/

        // title
         if (checkInput(mTvTitle.getText().toString())){
            mParams.putString(SocialConstants.PARAM_TITLE, mTvTitle.getText().toString());
        } else {
            //mParams.putString(Constants.PARAM_TITLE, "title字段测试");
			mParams.putString(SocialConstants.PARAM_TITLE, "");
        }

        // msg
        if (checkInput(mTvMsg.getText().toString())){
            mParams.putString(SocialConstants.PARAM_SEND_MSG, mTvMsg.getText().toString());
        } else {
            // mParams.putString(Constants.PARAM_SEND_MSG, "msg字段测试");
            mParams.putString(SocialConstants.PARAM_SEND_MSG, "");
        }

        // img
        if (checkInput(mTvImg.getText().toString())){
            mParams.putString(SocialConstants.PARAM_IMG_URL, mTvImg.getText().toString());
            mParams.putString(SocialConstants.PARAM_SEND_IMG, mTvImg.getText().toString());
        } else {
            // mParams.putString(Constants.PARAM_IMG_URL,"http://i.gtimg.cn/qzonestyle/act/qzone_app_img/app888_888_75.png");
            mParams.putString(SocialConstants.PARAM_IMG_URL, "");
        }
        
        if (checkInput(mTvRecimg.getText().toString())) {
        	mParams.putString(SocialConstants.PARAM_REC_IMG, mTvRecimg.getText().toString());
        } else {
        	mParams.putString(SocialConstants.PARAM_REC_IMG, "");
        }
        
        if (checkInput(mTvRecimgDesc.getText().toString())) {
        	mParams.putString(SocialConstants.PARAM_REC_IMG_DESC, mTvRecimgDesc.getText().toString());
        } else {
        	mParams.putString(SocialConstants.PARAM_REC_IMG_DESC, "");
        }

        // exclude，好友召回无exclude
        /*if (checkInput(mTvExclude.getText().toString() )){
            mParams.putString("exclude", mTvExclude.getText().toString());
        }*/

        // specified
        if (checkInput(mTvReceiver.getText().toString())){
            mParams.putString("receiver", mTvReceiver.getText().toString());
        }

        // only，好友召回无需only
        /*if (mSpOnly.getSelectedItem() != null) {
            mParams.putString("only", (String) mSpOnly.getSelectedItem());
        }*/

        // source
        if (checkInput(mTvSource.getText().toString())){
            mParams.putString(SocialConstants.PARAM_SOURCE,
                    URLEncoder.encode(mTvSource.getText().toString()));
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
