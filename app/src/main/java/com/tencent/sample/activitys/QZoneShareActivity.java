
package com.tencent.sample.activitys;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.linlsyf.cantonese.R;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.sample.ThreadManager;
import com.tencent.sample.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class QZoneShareActivity extends BaseActivity implements OnClickListener {

	private static final int MAX_IMAGE = 9;
    private RadioButton mRadioBtnShareTypeImgAndText;
    private RadioButton mRadioBtnShareTypeImg;
    private RadioButton mRadioBtnShareTypeApp;
    private RadioButton mRadioBtnShareTypePublishMood;
    private RadioButton mRadioBtnShareTypePublishVedio;
    private RadioButton mRadioBtnShareTypeMiniProgram;

    private EditText title = null;
    private EditText summary = null;
    private EditText targetUrl = null;
    private EditText videoPath = null;
    private EditText scene = null;
    private EditText callback = null;
    private EditText mETMiniProgramAppid;
    private EditText mETMiniProgramPath;
    private EditText mETMiniProgramType;

    private ImageView videoPicker = null;
    //QZone分享， SHARE_TO_QQ_TYPE_DEFAULT 图文，SHARE_TO_QQ_TYPE_IMAGE 纯图
    private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
    // zivon add

    private LinearLayout mTitleContainerLayout = null;
    private LinearLayout mTargetUrlContainerLayout = null;
    private LinearLayout mImageContainerLayout = null;
    private LinearLayout mVideoPathContainLayout = null;

    private LinearLayout mImageListLayout = null;
    private static final int REQUEST_CODE_PICK_VIDEO = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarTitle("Qzone分享");
        setLeftButtonEnable();
        setContentView(R.layout.qzone_share_activity);

        title = (EditText) findViewById(R.id.shareqq_title);
        targetUrl = (EditText) findViewById(R.id.shareqq_targetUrl);
        summary = (EditText) findViewById(R.id.shareqq_summary);
        videoPath = (EditText) findViewById(R.id.video_targetPath);
        scene = (EditText)findViewById(R.id.et_hulian_extra_scene);
        callback = (EditText)findViewById(R.id.et_hulian_call_back);
        videoPicker = (ImageView) findViewById(R.id.btn_addVideo);
        videoPicker.setOnClickListener(this);

        mTitleContainerLayout = (LinearLayout) findViewById(R.id.qqshare_title_container);
        mTargetUrlContainerLayout = (LinearLayout) findViewById(R.id.qqshare_targetUrl_container);
        mImageContainerLayout = (LinearLayout) findViewById(R.id.qqshare_imageUrl_container);
        mVideoPathContainLayout = (LinearLayout) findViewById(R.id.publish_video_container);

        findViewById(R.id.shareqq_commit).setOnClickListener(this);
        findViewById(R.id.btn_addImage).setOnClickListener(this);
        mRadioBtnShareTypeImgAndText = (RadioButton) findViewById(R.id.QZoneShare_radioBtn_image_text_share);
        mRadioBtnShareTypeImgAndText.setOnClickListener(this);
        mRadioBtnShareTypeImg = (RadioButton)findViewById(R.id.QZoneShare_radioBtn_image_share);
        mRadioBtnShareTypeImg.setOnClickListener(this);

        mRadioBtnShareTypeApp = (RadioButton)findViewById(R.id.QZoneShare_radioBtn_app_share);
        mRadioBtnShareTypeApp.setOnClickListener(this);
        mRadioBtnShareTypePublishMood = (RadioButton)findViewById(R.id.QZoneShare_radioBtn_publish_mood);
        mRadioBtnShareTypePublishMood.setOnClickListener(this);
        mRadioBtnShareTypePublishVedio = (RadioButton)findViewById(R.id.QZoneShare_radioBtn_publish_video);
        mRadioBtnShareTypePublishVedio.setOnClickListener(this);
        mRadioBtnShareTypeMiniProgram = (RadioButton)findViewById(R.id.QZoneShare_radioBtn_mini_program);
        mRadioBtnShareTypeMiniProgram.setOnClickListener(this);
        // mini_program
        mETMiniProgramAppid = (EditText) findViewById(R.id.et_shareqq_mini_program_appid);
        mETMiniProgramPath = (EditText) findViewById(R.id.et_shareqq_mini_program_path);
        mETMiniProgramType = (EditText) findViewById(R.id.et_shareqq_mini_program_type);

        mImageListLayout = (LinearLayout)findViewById(R.id.images_picker_layout);

        checkTencentInstance();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.QZoneShare_radioBtn_image_text_share:
                shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
                mTitleContainerLayout.setVisibility(View.VISIBLE);
                mTargetUrlContainerLayout.setVisibility(View.VISIBLE);
                mImageContainerLayout.setVisibility(View.VISIBLE);
                mVideoPathContainLayout.setVisibility(View.GONE);
                break;
            case R.id.QZoneShare_radioBtn_image_share:
                shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE;
                break;
            case R.id.QZoneShare_radioBtn_app_share:
                shareType = QzoneShare.SHARE_TO_QZONE_TYPE_APP;
                break;
            case R.id.QZoneShare_radioBtn_publish_mood:
                shareType = QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD;
                mTitleContainerLayout.setVisibility(View.GONE);
                mTargetUrlContainerLayout.setVisibility(View.GONE);
                mImageContainerLayout.setVisibility(View.VISIBLE);
                mVideoPathContainLayout.setVisibility(View.GONE);
                break;
            case R.id.QZoneShare_radioBtn_publish_video:
                shareType = QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO;
                mTitleContainerLayout.setVisibility(View.GONE);
                mTargetUrlContainerLayout.setVisibility(View.GONE);
                mImageContainerLayout.setVisibility(View.GONE);
                mVideoPathContainLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.QZoneShare_radioBtn_mini_program:
                shareType = QzoneShare.SHARE_TO_QZONE_TYPE_MINI_PROGRAM;
                mTitleContainerLayout.setVisibility(View.VISIBLE);
                mTargetUrlContainerLayout.setVisibility(View.VISIBLE);
                mImageContainerLayout.setVisibility(View.VISIBLE);
                mVideoPathContainLayout.setVisibility(View.GONE);
                break;
            case R.id.btn_addVideo:
                startPickLocaleVedio(this, REQUEST_CODE_PICK_VIDEO);
                break;
            case R.id.shareqq_commit: // 提交
                final Bundle params = new Bundle();
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
                if (mTitleContainerLayout.isShown()) {
                    params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title.getText().toString());
                }
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary.getText().toString());
                if (mTargetUrlContainerLayout.isShown()) {
                    params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl.getText().toString());
                }

                // 支持传多个imageUrl
                if (mImageContainerLayout.isShown()) {
                    ArrayList<String> imageUrls = new ArrayList<String>();
                    for (int i = 0; i < mImageListLayout.getChildCount(); i++) {
                        LinearLayout addItem = (LinearLayout) mImageListLayout.getChildAt(i);
                        EditText editText = (EditText) addItem.getChildAt(1);
                        imageUrls.add(editText.getText().toString());
                    }
                    params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
                }

                Bundle bundle2 = new Bundle();
                bundle2.putString(QzonePublish.HULIAN_EXTRA_SCENE, scene.getText().toString());
                bundle2.putString(QzonePublish.HULIAN_CALL_BACK, callback.getText().toString());

                params.putBundle(QzonePublish.PUBLISH_TO_QZONE_EXTMAP, bundle2);
                if (shareType == QzoneShare.SHARE_TO_QZONE_TYPE_MINI_PROGRAM) {
                    Bundle miniProgramBundle = new Bundle();

                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_TITLE, title.getText().toString());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary.getText().toString());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl.getText().toString());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_APPID, mETMiniProgramAppid.getText().toString());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_PATH, mETMiniProgramPath.getText().toString());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_TYPE, mETMiniProgramType.getText().toString());
                    miniProgramBundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);

                    // image
                    ArrayList<String> imageUrls = new ArrayList<String>();
                    for (int i = 0; i < mImageListLayout.getChildCount(); i++) {
                        LinearLayout addItem = (LinearLayout) mImageListLayout.getChildAt(i);
                        EditText editText = (EditText) addItem.getChildAt(1);
                        imageUrls.add(editText.getText().toString());
                    }
                    miniProgramBundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
                    doShareToQzone(miniProgramBundle);
                    return;
                }
                if (shareType == QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO) {
                    params.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, videoPath.getText().toString());
                }
                if (shareType == QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT) {
                    doShareToQzone(params);
                } else {
                    doPublishToQzone(params);
                }

                return;
            case R.id.btn_addImage:
                int num = mImageListLayout.getChildCount();
       //         if (num < MAX_IMAGE) {
                    LinearLayout addItem = (LinearLayout) LayoutInflater.from(this).inflate(
                            R.layout.image_picker_layout, null);
                    mImageListLayout.addView(addItem);
                    TextView textView0 = (TextView) addItem.getChildAt(0); // index
                    EditText view1 = (EditText)addItem.getChildAt(1); // editText url
                    View view2 = addItem.getChildAt(2); // picker按钮
                    View view3 = addItem.getChildAt(3); // 删除按钮
                    textView0.setText(String.valueOf(num + 1));
                if(num == 0) {
                    view1.setText(getResources().getString(R.string.qqshare_imageUrl_content));
                }
                    view1.setId(1000 + num); // url EditText
                    view2.setId(2000 + num); // picker
                    view3.setId(3000 + num); // 删除
                    addItem.setId(num);
                    view2.setOnClickListener(this);
                    view3.setOnClickListener(this);
//                } else {
//                    showToast("不能添加更多的图片!!!");
//                }
                return;
        }
        if (id >= 2000 && id < 3000) {
            // 点的是选择图片
            startPickLocaleImage(this, id - 2000);
        } else if (id >= 3000 && id < 4000) {
            // 点的是删除图片
            if (mImageListLayout.getChildCount() > 0) {
                View view = mImageListLayout.findViewById(id - 3000);
                mImageListLayout.removeView(view);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MainActivity.mTencent != null) {
            MainActivity.mTencent.releaseResource();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QZONE_SHARE) {
        	Tencent.onActivityResultData(requestCode,resultCode,data,qZoneShareListener);
        } else if(requestCode == REQUEST_CODE_PICK_VIDEO){
            String path = null;
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.getData() != null) {
                    // 根据返回的URI获取对应的SQLite信息
                    Uri uri = data.getData();
                    path = Util.getPath(this, uri);
                }
            }
            if (path != null) {
                videoPath.setText(path);
            } else {
                showToast("请重新选择视频");
            }
        }else{
        	String path = null;
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.getData() != null) {
                    // 根据返回的URI获取对应的SQLite信息
                    Uri uri = data.getData();
                    path = Util.getPath(this, uri);
                }
            }
            if (path != null) {
            	// 这里很奇葩的方式, 将获取到的值赋值给相应的EditText, 竟然能对应上
            	EditText editText = (EditText)mImageContainerLayout.findViewById(requestCode + 1000);
            	editText.setText(path);
            } else {
                showToast("请重新选择图片");
            }
        }
    }

    private static final void startPickLocaleImage(Activity activity, int requestId) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        if (android.os.Build.VERSION.SDK_INT >= Util.Build_VERSION_KITKAT) {
            intent.setAction(Util.ACTION_OPEN_DOCUMENT);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(
                Intent.createChooser(intent, activity.getString(R.string.str_image_local)), requestId);
    }

    private static final void startPickLocaleVedio(Activity activity, int requestId) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        if (android.os.Build.VERSION.SDK_INT >= Util.Build_VERSION_KITKAT) {
            intent.setAction(Util.ACTION_OPEN_DOCUMENT);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/*");
        activity.startActivityForResult(
                Intent.createChooser(intent, activity.getString(R.string.str_image_local)), requestId);
    }
    
    IUiListener qZoneShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            Util.toastMessage(QZoneShareActivity.this, "onCancel:test ");
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Util.toastMessage(QZoneShareActivity.this, "onError: " + e.errorMessage, "e");
        }

		@Override
		public void onComplete(Object response) {
			// TODO Auto-generated method stub
			 Util.toastMessage(QZoneShareActivity.this, "onComplete: " + response.toString());
		}

    };
    
    /**
     * 用异步方式启动分享
     * @param params
     */
    private void doShareToQzone(final Bundle params) {
        // QZone分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != MainActivity.mTencent) {
                    MainActivity.mTencent.shareToQzone(QZoneShareActivity.this, params, qZoneShareListener);
                }
            }
        });
    }

    private void doPublishToQzone(final Bundle params) {
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (null != MainActivity.mTencent) {
                    MainActivity.mTencent.publishToQzone(QZoneShareActivity.this, params, qZoneShareListener);
                }
            }
        });
    }

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
