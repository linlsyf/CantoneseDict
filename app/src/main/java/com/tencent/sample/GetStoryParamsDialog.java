
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

public class GetStoryParamsDialog extends Dialog implements
        View.OnClickListener {

    public interface OnGetStoryParamsCompleteListener {
        public void onGetParamsComplete(Bundle params);
    }

    private OnGetStoryParamsCompleteListener mListener = null;

    private Context mContext = null;

    private Bundle mParams = null;
    private Button mBtCommit = null;
    private TextView mTvTitle = null;
    private TextView mTvDescription = null;
    private TextView mTvSummary = null;
    private TextView mTvPlayurl = null;
    private TextView mTvShareurl = null;
    private TextView mTvSource = null;
    private TextView mTvPics = null;
    private Spinner mSpAct = null;

    public GetStoryParamsDialog(Context context,
            OnGetStoryParamsCompleteListener listener) {
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
        setContentView(R.layout.get_story_params_dialog);

        findViews();

        setupViews();
    }

    private void findViews() {
        mTvTitle = (TextView) findViewById(R.id.et_title);
        mTvDescription = (TextView) findViewById(R.id.et_description);
        mTvSummary = (TextView) findViewById(R.id.et_summary);
        mTvPlayurl = (TextView) findViewById(R.id.et_playurl);
        mTvShareurl = (TextView) findViewById(R.id.et_shareurl);
        mTvSource = (TextView) findViewById(R.id.et_source);
        mTvPics = (TextView) findViewById(R.id.et_pics);
        mSpAct = (Spinner) findViewById(R.id.sp_act);
        mBtCommit = (Button) findViewById(R.id.bt_commit);
    }

    private void setupViews() {
        mBtCommit.setOnClickListener(this);
        
        mTvTitle.setText("AndroidSdk_2_0:UiStory title");
        mTvPics.setText("http://login.imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        mTvDescription.setText("AndroidSdk_2_0: UiStory comment");
        mTvSummary.setText("AndroidSdk_2_0: UiStory summary");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item);
        adapter.add(mContext.getResources().getString(R.string.act1));
        adapter.add(mContext.getResources().getString(R.string.act2));
        adapter.add(mContext.getResources().getString(R.string.act3));
        adapter.add(mContext.getResources().getString(R.string.act4));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpAct.setAdapter(adapter);

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
        if (mTvTitle.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_TITLE, mTvTitle.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_TITLE, "");
        }
        if (mTvDescription.getText().toString() != null) {
            mParams.putString(SocialConstants.PARAM_COMMENT, mTvDescription.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_COMMENT, "");
        }
        if (mTvSummary.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_SUMMARY, mTvSummary.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_SUMMARY, "");
        }
        if (mTvPlayurl.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_PLAY_URL, mTvPlayurl.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_PLAY_URL, "");
        }
        if (mTvShareurl.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_SHARE_URL, mTvShareurl.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_SHARE_URL, "");
        }
        if (mTvSource.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_SOURCE, URLEncoder.encode(mTvSource.getText().toString()));
        } else {
            mParams.putString(SocialConstants.PARAM_SOURCE, "");
        }
        if (mTvPics.getText().toString().length() != 0) {
            mParams.putString(SocialConstants.PARAM_IMAGE, mTvPics.getText().toString());
        } else {
            mParams.putString(SocialConstants.PARAM_IMAGE, "");
        }
        if (mSpAct.getSelectedItem() != null) {
            mParams.putString(SocialConstants.PARAM_ACT, (String) mSpAct.getSelectedItem());
        }
        // 分享暂时不支持传receiver模式
//        if (mOpenids.size() > 0) {
//            String[] openids = new String[mOpenids.size()];
//            for (int i = 0; i < mOpenids.size(); i++) {
//                openids[i] = mOpenids.get(i);
//                Log.e("add receiver", "add " + mOpenids.get(i));
//            }
//            mParams.putStringArray(Constants.PARAM_RECEIVER, openids);
//        }
    }
}
