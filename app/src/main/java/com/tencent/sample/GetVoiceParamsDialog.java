
package com.tencent.sample;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.linlsyf.cantonese.R;
import com.tencent.open.SocialConstants;

import java.net.URLEncoder;

public class GetVoiceParamsDialog extends Dialog implements
        View.OnClickListener {

    public interface OnGetVoiceParamsCompleteListener {
        public void onGetParamsComplete(Bundle params);
        
        public void selectPhoto(Bundle params);
    }

    private OnGetVoiceParamsCompleteListener mListener = null;

    private Context mContext = null;

    private Bundle mParams = null;

    private Button mBtCommit = null;
    private TextView mTvSource = null;
    private TextView mTvPicurl = null;
    private Button mBtPicImage = null;
    private Spinner mSpOnly = null;
    private TextView mTvReceiver = null;
    private TextView mTvExclude = null;
    private TextView mTvSpecified = null;
    private ImageView mIvPhoto = null;
    private Bitmap mBitmap = null;
    
    public GetVoiceParamsDialog(Context context,
            OnGetVoiceParamsCompleteListener listener, Bundle params) {
        super(context, R.style.Dialog_Fullscreen);
        // TODO Auto-generated constructor stub
        mContext = context;
        mListener = listener;
        if(params != null){
            mParams = params;
        } else{
            mParams = new Bundle();            
        }
        
        mBitmap = mParams.getParcelable(SocialConstants.PARAM_IMG_DATA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_voice_params_dialog);

        findViews();

        setupViews();
    }

    private void findViews() {
        mTvSource = (TextView) findViewById(R.id.et_source);
        mTvPicurl = (TextView) findViewById(R.id.et_picurl);
        mBtPicImage = (Button) findViewById(R.id.bt_imgselecter);
        mSpOnly = (Spinner) findViewById(R.id.sp_only);
        mTvReceiver = (TextView) findViewById(R.id.et_receiver);
        mTvExclude = (TextView) findViewById(R.id.et_exclude);
        mTvSpecified = (TextView) findViewById(R.id.et_specified);
        mBtCommit = (Button) findViewById(R.id.bt_commit);
        mIvPhoto = (ImageView)findViewById(R.id.iv_photo);
        
        if(mBitmap != null){
            mIvPhoto.setImageBitmap(mBitmap);
        }
        mTvPicurl.setText("http://login.imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        mBtPicImage.setText("select image");
        mBtPicImage.setOnClickListener(this);
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
        } else if (v == mBtPicImage) {
            getInputParams();
            mListener.selectPhoto(mParams);
            if(mBitmap != null){
                mBitmap.recycle();
                mBitmap = null;
            }
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
            mParams.putString(SocialConstants.PARAM_IMG_URL, mTvPicurl.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_IMG_URL, "");
        }
        
        // pic select
        if(mBitmap != null){
            mParams.putParcelable(SocialConstants.PARAM_IMG_DATA, mBitmap);
        }
        
        // only
        if (mSpOnly.getSelectedItem() != null) {
            mParams.putString(SocialConstants.PARAM_ONLY, (String) mSpOnly.getSelectedItem());
        }
        
        // receiver
        if (mTvReceiver.getText().toString().length() != 0){
            mParams.putString(SocialConstants.PARAM_RECEIVER, mTvReceiver.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_RECEIVER, "");
        }
        // exclude
        if (mTvExclude.getText().length() != 0){
            mParams.putString(SocialConstants.PARAM_EXCLUDE, mTvExclude.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_EXCLUDE, "");
        }
        // specified
        if (mTvSpecified.getText().length() != 0){
            mParams.putString(SocialConstants.PARAM_SPECIFIED, mTvSpecified.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_SPECIFIED, "");
        }
    }
}
