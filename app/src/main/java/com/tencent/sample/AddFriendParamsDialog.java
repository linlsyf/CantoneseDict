package com.tencent.sample;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.linlsyf.area.R;

import static com.tencent.open.SocialOperation.*;

public class AddFriendParamsDialog extends Dialog implements
		View.OnClickListener {
	
	TextView fopenid;
    TextView label;
    TextView message;
    
    private OnGetAddFriendParamsCompleteListener mListener;

	public interface OnGetAddFriendParamsCompleteListener {
		public void onGetParamsComplete(Bundle params);
	}
	
	public AddFriendParamsDialog(Context context, OnGetAddFriendParamsCompleteListener listener) {
        super(context, R.style.Dialog_Fullscreen);
        mListener = listener;
    }
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_friend_params_dialog);
        
        fopenid = (TextView) findViewById(R.id.et_friend_openid);
        label = (TextView) findViewById(R.id.et_friend_label);
        message = (TextView) findViewById(R.id.et_friend_msg);
        
        fopenid.setText("86298EC35AEA46553FFAF51E594BA99F");
        
        findViewById(R.id.bt_commit).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_commit:
			Bundle bundle = new Bundle();
            bundle.putString(GAME_FRIEND_OPENID, fopenid.getText() + "");
            bundle.putString(GAME_FRIEND_LABEL, label.getText() + "");
            bundle.putString(GAME_FRIEND_ADD_MESSAGE, message.getText() + "");
            
            if(null != mListener){
            	mListener.onGetParamsComplete(bundle);
            }
            this.dismiss();
			break;

		default:
			break;
		}
	}

}
