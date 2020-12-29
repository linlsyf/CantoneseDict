/**
 * @author azraellong
 * @date 2013-2-18
 */

package com.tencent.sample;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.linlsyf.cantonese.R;

/**
 * @author azraellong
 */
public class GetShareToQQParamsDialog extends Dialog implements
        View.OnClickListener {
    TextView title;
    TextView imageUrl;
    TextView targetUrl;
    TextView summary;
    TextView appName;//app名称，用于手Q显示返回

    ShareToQQParamsListener paramsListener;
    
    interface ShareToQQParamsListener{
        void onComplete(Bundle params);
    }
    
    /**
     * @param context
     */
    public GetShareToQQParamsDialog(Context context, ShareToQQParamsListener listener) {
        super(context);
        paramsListener = listener;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.shareqq_commit){
            Bundle bundle = new Bundle();
            bundle.putString("title", title.getText() + "");
            bundle.putString("imageUrl", imageUrl.getText() + "");
            bundle.putString("targetUrl", targetUrl.getText() + "");
            bundle.putString("summary", summary.getText() + "");
            bundle.putString("appName", appName.getText() + "");
            
            if(null != paramsListener){
                paramsListener.onComplete(bundle);
            }
            this.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.get_shareqq_params_dialog);
        
        title = (TextView) findViewById(R.id.shareqq_title);
        imageUrl = (TextView) findViewById(R.id.shareqq_image_url);
        targetUrl = (TextView) findViewById(R.id.shareqq_target_url);
        summary = (TextView) findViewById(R.id.shareqq_summary);
        appName = (TextView) findViewById(R.id.shareqq_app_name);
        
        findViewById(R.id.shareqq_commit).setOnClickListener(this);
    }

}
