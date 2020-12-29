package com.ui.widget.imageshower;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


import com.core.CoreApplication;
import com.core.base.BasicActivity;
import com.easy.recycleview.utils.FastClickUtils;
import com.easysoft.utils.lib.imge.ImageUtils;
import com.linlsyf.cantonese.R;
import com.ui.widget.imageshower.ImageLoadingDialog;
import com.ui.widget.zoom.ZoomImageView;

import java.net.MalformedURLException;

public class ImageShower extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageshower);
		ImageView imageView=findViewById(R.id.image);
//		ZoomImageView imageView=findViewById(R.id.image);

		 String  url=   getIntent().getStringExtra("url");

		 try{
			 ImageUtils.getInStance(CoreApplication.instance).load(url,imageView);

		 }catch (Exception e){

		 }

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
//		 imageView.seton


	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//
//		 if (!FastClickUtils.isNotFastClick()){
//			 finish();
//		 }
//
//		return true;
//	}

	@Override
	public void initUIView() throws MalformedURLException {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {

	}

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {

	}
}
