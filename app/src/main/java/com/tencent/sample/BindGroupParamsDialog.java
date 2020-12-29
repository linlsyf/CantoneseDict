package com.tencent.sample;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.linlsyf.cantonese.R;

import static com.tencent.open.SocialOperation.*;

public class BindGroupParamsDialog extends Dialog implements
		View.OnClickListener {

    TextView tvGameUnionId;
    TextView tvGameUnionName;

    private final OnGetParamsCompleteListener mListener;

    public interface OnGetParamsCompleteListener {
		public void onGetParamsComplete(String organizationId, String organizationName);
	}

    public BindGroupParamsDialog(Context context, OnGetParamsCompleteListener listener) {
        super(context, R.style.Dialog_Fullscreen);
        mListener = listener;
    }

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bind_group_params_dialog);
        tvGameUnionId = (TextView) findViewById(R.id.et_game_unionid);
        tvGameUnionName = (TextView) findViewById(R.id.et_game_union_name);
        findViewById(R.id.bt_commit).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_commit:
            if(null != mListener){
                mListener.onGetParamsComplete(tvGameUnionId.getText() + "", tvGameUnionName.getText() + "");
            }
            this.dismiss();
			break;

		default:
			break;
		}
	}

}
