package com.ui.common.register;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.linlsyf.area.R;
import com.easysoft.utils.lib.system.StringUtils;


/**
 * 
 *<br> 创建者：林党宏
 *<br>时间：2016年5月20日 上午9:41:43
 *<br>注释：用来显示获取验证码倒计时
 */
public class CountdownRunnable implements Runnable {
	private int mReSendTime = 60;
	  TextView coundownButton;
	  Handler handler;
	  Context context;
	  /**倒数时的颜色*/
	private int coundownColor;
	/**重新获取时的颜色*/
	private int resendColor;
	/** 重新获取验证码文字*/
	String mResendTextString="";
	public CountdownRunnable(TextView coundownButton, Handler handler) {
		super();
		this.coundownButton = coundownButton;
		this.handler = handler;
		context=coundownButton.getContext();
	}
	
	public void setTextColor(int countcolor,int resendColor){
		 coundownColor=countcolor;
		 this.resendColor=resendColor;
	}
	 /**
	    *<br> 创建者：林党宏
	    *<br>时间：2016/8/23 15:59
	    *<br>注释：设置重新获取验证码文字
	    */
	public void setResendText(String resendTextString){
		this.mResendTextString=resendTextString;
	}

	@Override
	public void run() {
		if (mReSendTime > 1) {
			mReSendTime--;
			coundownButton.setText("(" + mReSendTime +"s"+ ")后重新获取");
			coundownButton.setEnabled(false);
//			handler.sendEmptyMessageDelayed(0, 1000);
			 handler.postDelayed(this, 1000); 
			 if (coundownColor!=0) {
				coundownButton.setTextColor(coundownColor);
			}else {
				coundownButton.setTextColor(context.getResources().getColor(R.color.gray_color));
			}
		} else {
			mReSendTime = 60;
//			reSendSMSRemainTimeTextView.setVisibility(View.GONE);
//			reSendSMS.setVisibility(View.VISIBLE);
			if (StringUtils.isEmpty(mResendTextString)){
				mResendTextString=context.getString(R.string.resend_validate_code);
			}
			coundownButton.setText(mResendTextString);
			if (resendColor!=0) {
				coundownButton.setTextColor(resendColor);
			}else {
				coundownButton.setTextColor(context.getResources().getColor(R.color.white));
			}

			coundownButton.setEnabled(true);
		}
		
	}
	
};
