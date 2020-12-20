
package com.tencent.sample;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.linlsyf.area.R;

public class GetGradeParamsDialog extends Dialog implements
        View.OnClickListener {

    public interface OnGetGradeParamsCompleteListener {
        public void onGetParamsComplete(Bundle params);
    }

    private OnGetGradeParamsCompleteListener mListener = null;

    private Bundle mParams = null;

    private Button mBtCommit = null;
    private TextView mTvGradeHint = null;

    public GetGradeParamsDialog(Context context,
            OnGetGradeParamsCompleteListener listener) {
        super(context, R.style.Dialog_Fullscreen);
        // TODO Auto-generated constructor stub
        mListener = listener;
        mParams = new Bundle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_grade_params_dialog);

        findViews();

        setupViews();
    }

    private void findViews() {
        mTvGradeHint = (TextView) findViewById(R.id.et_grade_hint);
        mBtCommit = (Button) findViewById(R.id.bt_commit);
    }

    private void setupViews() {
        mBtCommit.setOnClickListener(this);
        mTvGradeHint.setText("亲，给个好评吧");
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
        if (mTvGradeHint.getText().toString().length() != 0) {
            mParams.putString("comment", mTvGradeHint.getText().toString());
        } else {
            mParams.putString("comment", "");
        }
    }
}
