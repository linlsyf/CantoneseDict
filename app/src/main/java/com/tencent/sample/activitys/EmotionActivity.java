package com.tencent.sample.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.linlsyf.cantonese.R;
import com.tencent.connect.common.Constants;
import com.tencent.connect.emotion.QQEmotion;
import com.tencent.sample.BaseUIListener;
import com.tencent.sample.Util;
import com.tencent.sample.adapter.EmotionSelectListAdapter;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;


public class EmotionActivity extends BaseActivity implements OnClickListener {
	private static String TAG = "EmotionActivity";
	private static final int REQUEST_SET_EMTION = 1000;
	private ListView mListView;
    private EmotionSelectListAdapter mAdapter;
    private ArrayList<Uri> mPathList = new ArrayList<Uri>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBarTitle("设置表情");
		setLeftButtonEnable();
		setContentView(R.layout.emotion_activity);
		findViewById(R.id.set_emotion_btn).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.item_list);
        initData();
	}

	private void initData(){
        mAdapter = new EmotionSelectListAdapter(this);
        mListView.setAdapter(mAdapter);

        for (int i = 0; i < 9; i++) {
            mPathList.add(Uri.parse("null"));
        }

        mAdapter.updateData(mPathList);
        mListView.setOnItemClickListener(itemClickListener);
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (view.getTag() != null && view.getTag() instanceof EmotionSelectListAdapter.ViewHolder) {
                final EmotionSelectListAdapter.ViewHolder holder = (EmotionSelectListAdapter.ViewHolder) view.getTag();
				android.util.Log.i(TAG,  "onItemClick = position =" + position);
				onClickSetEmotion(position);

//                if (parent.getAdapter() != null) {
//                    ((BaseAdapter)parent.getAdapter()).notifyDataSetChanged();
//                }
            }
        }
    };

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.set_emotion_btn:
            saveQQEmotion();
			break;
		default:
			break;
		}

	}

	private void saveQQEmotion(){
	    ArrayList<Uri> resultList = new ArrayList<Uri>();
        for (int i = 0; i < mPathList.size(); i++) {
            Uri path = mPathList.get(i);
            String strPath = path.toString();
            if(path!= null && !"null".equals(strPath)){
                resultList.add(path);
            }
        }
        if(resultList.size() > 0){
        	MainActivity.mTencent.setEmotions(this,resultList,new BaseUIListener(this));
        }
    }

    private void onClickSetEmotion(int position) {
		Intent intent = new Intent();
		intent.putExtra("position", position);
		// 开启Pictures画面Type设定为image
		intent.setType("image/*");
		// 使用Intent.ACTION_GET_CONTENT这个Action
		intent.setAction(Intent.ACTION_GET_CONTENT);
		// 取得相片后返回本画面
		int dealrequestCode = REQUEST_SET_EMTION + position;
		startActivityForResult(intent, dealrequestCode);
		// 在 onActivityResult 中调用 doSetAvatar
	}

    private void addEmotion(Uri path, int position){
		android.util.Log.i(TAG, "position = " + position + ", path=" + path);
		mPathList.set(position, path);
		if(mAdapter != null){
			mAdapter.updateData(mPathList);
		}
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == Constants.REQUEST_EDIT_EMOTION){
			Tencent.onActivityResultData(requestCode, resultCode, data, setEmotionListener);
		}else{
			if(resultCode == Activity.RESULT_OK){
				int position = requestCode - REQUEST_SET_EMTION;
				Uri path = null;
				if (resultCode == Activity.RESULT_OK) {
					if (data != null && data.getData() != null) {
						// 根据返回的URI获取对应的SQLite信息
                        path = data.getData();
						//path = uri.toString();//Util.getPath(this, uri);
					}
				}
				if (path != null) {
					addEmotion(path, position);
				} else {
					showToast("请重新选择图片");
				}
			}
		}
	}

	IUiListener setEmotionListener = new IUiListener() {
		@Override
		public void onCancel() {
			Util.toastMessage(EmotionActivity.this, "设置取消");
		}
		@Override
		public void onComplete(Object response) {
			// TODO Auto-generated method stub
			Util.toastMessage(EmotionActivity.this, "设置成功：" + response.toString());
		}
		@Override
		public void onError(UiError e) {
			// TODO Auto-generated method stub
			Util.toastMessage(EmotionActivity.this, "设置失败");
		}
	};

	Toast mToast = null;
	private void showToast(String text) {
		if (mToast != null && !super.isFinishing()) {
			mToast.setText(text);
			mToast.show();
			return;
		}
		mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		mToast.show();
	}
	
}
