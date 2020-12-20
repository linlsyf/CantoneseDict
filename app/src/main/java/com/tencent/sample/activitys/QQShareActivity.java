package com.tencent.sample.activitys;

import com.linlsyf.area.R;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.sample.ThreadManager;
import com.tencent.sample.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class QQShareActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

    private View mContainer_title = null;
    private View mContainer_summary = null;
    private View mContainer_audioUrl = null;
    private View mContainer_targetUrl = null;
    private View mContainer_imgUrl = null;
    private View mContainer_appName = null;

    private TextView title = null;
    private TextView imageUrl = null;
    private TextView targetUrl = null;
    private TextView summary = null;
    private TextView appName = null;// app名称，用于手Q显示返回
    private TextView arkInfo = null;

    // zivon add
    private RadioButton mRadioBtn_localImage = null;
    private RadioButton mRadioBtn_netImage = null;
    private CheckBox mCheckBox_qzoneAutoOpen = null;
    private CheckBox mCheckBox_qzoneItemHide = null;

    private RadioButton mRadioBtnShareTypeDefault;
    private RadioButton mRadioBtnShareTypeAudio;
    private RadioButton mRadioBtnShareTypeImg;
    private RadioButton mRadioBtnShareTypeApp;
    private RadioButton mRadioBtnShareTypeMiniProgram;
    private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
    private EditText mEditTextAudioUrl;

    private int mExtarFlag = 0x00;
    private View mContainer_miniProgramAppid;
    private View mContainer_miniProgramPath;
    private View mContainer_miniProgramType;
    private View mContainer_arkInfo;
    private EditText mETMiniProgramAppid;
    private EditText mETMiniProgramPath;
    private EditText mETMiniProgramType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarTitle("QQ分享");
        setLeftButtonEnable();
        setContentView(R.layout.qq_share_activity);

        title = (TextView) findViewById(R.id.shareqq_title);
        imageUrl = (TextView) findViewById(R.id.shareqq_image_url);
        targetUrl = (TextView) findViewById(R.id.shareqq_target_url);
        summary = (TextView) findViewById(R.id.shareqq_summary);
        appName = (TextView) findViewById(R.id.shareqq_app_name);
        arkInfo = (TextView) findViewById(R.id.shareqq_ark_edit);

        findViewById(R.id.shareqq_commit).setOnClickListener(this);

        mEditTextAudioUrl = (EditText) findViewById(R.id.et_shareqq_audioUrl);

        // mContainer_qzone_special = findViewById(R.id.qzone_specail_radio_container);
        mContainer_title = findViewById(R.id.qqshare_title_container);
        mContainer_summary = findViewById(R.id.qqshare_summary_container);
        mContainer_audioUrl = findViewById(R.id.qqshare_audioUrl_container);
        mContainer_targetUrl = findViewById(R.id.qqshare_targetUrl_container);
        mContainer_imgUrl = findViewById(R.id.qqshare_imageUrl_container);
        mContainer_appName = findViewById(R.id.qqshare_appName_container);
        mContainer_arkInfo = findViewById(R.id.qqshare_arkInfo_container);
        mContainer_miniProgramAppid = findViewById(R.id.qqshare_appid_container);
        mContainer_miniProgramPath = findViewById(R.id.qqshare_mini_program_path_container);
        mContainer_miniProgramType = findViewById(R.id.qqshare_mini_program_type_container);

        mRadioBtn_netImage = (RadioButton) findViewById(R.id.radioBtn_net_image);
        mRadioBtn_netImage.setOnClickListener(this);
        mRadioBtn_localImage = (RadioButton) findViewById(R.id.radioBtn_local_image);
        mRadioBtn_localImage.setOnClickListener(this);
        mRadioBtnShareTypeDefault = (RadioButton) findViewById(R.id.radioBtn_share_type_default);
        mRadioBtnShareTypeDefault.setOnClickListener(this);
        mRadioBtnShareTypeAudio = (RadioButton) findViewById(R.id.radioBtn_share_type_audio);
        mRadioBtnShareTypeAudio.setOnClickListener(this);
        mRadioBtnShareTypeImg = (RadioButton)findViewById(R.id.radioBtn_share_type_image);
        mRadioBtnShareTypeImg.setOnClickListener(this);
        mRadioBtnShareTypeApp = (RadioButton)findViewById(R.id.radioBtn_share_type_app);
        mRadioBtnShareTypeApp.setOnClickListener(this);
        mRadioBtnShareTypeMiniProgram = (RadioButton)findViewById(R.id.radioBtn_share_type_mini_program);
        mRadioBtnShareTypeMiniProgram.setOnClickListener(this);

        mCheckBox_qzoneItemHide = (CheckBox) findViewById(R.id.checkBox_qzone_item_hide);
        mCheckBox_qzoneItemHide.setOnCheckedChangeListener(this);
        mCheckBox_qzoneAutoOpen = (CheckBox) findViewById(R.id.checkBox_qzone_auto_open);
        mCheckBox_qzoneAutoOpen.setOnCheckedChangeListener(this);

        // mini_program
        mETMiniProgramAppid = (EditText) findViewById(R.id.et_shareqq_mini_program_appid);
        mETMiniProgramPath = (EditText) findViewById(R.id.et_shareqq_mini_program_path);
        mETMiniProgramType = (EditText) findViewById(R.id.et_shareqq_mini_program_type);

        initShareUI(shareType);

        checkTencentInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shareqq_commit: // 提交
                final Bundle params = new Bundle();
                if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
                	params.putString(QQShare.SHARE_TO_QQ_TITLE, title.getText().toString());
                    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl.getText().toString());
                    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary.getText().toString());
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl.getText().toString());
                } else {
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageUrl.getText().toString());
                }

                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName.getText().toString());
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, mExtarFlag);
                if (shareType == QQShare.SHARE_TO_QQ_TYPE_AUDIO) {
                    params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, mEditTextAudioUrl.getText().toString());
                }
                if ((mExtarFlag & QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN) != 0) {
                    showToast("在好友选择列表会自动打开分享到qzone的弹窗~~~");
                } else if ((mExtarFlag & QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE) != 0) {
                    showToast("在好友选择列表隐藏了qzone分享选项~~~");
                }
                // 小程序
                if (shareType == QQShare.SHARE_TO_QQ_MINI_PROGRAM) {
                    Bundle miniProgramBundle = new Bundle();

                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_TITLE, title.getText().toString());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary.getText().toString());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl.getText().toString());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl.getText().toString());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_APPID, mETMiniProgramAppid.getText().toString());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_PATH, mETMiniProgramPath.getText().toString());
                    miniProgramBundle.putString(QQShare.SHARE_TO_QQ_MINI_PROGRAM_TYPE, mETMiniProgramType.getText().toString());
                    miniProgramBundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
                    doShareToQQ(miniProgramBundle);
                    return;
                }

                if (QQShare.SHARE_TO_QQ_TYPE_IMAGE != shareType) {
                    /*QQ分享增加ARK*/
                    String arkStr = arkInfo.getText().toString();
                    params.putString(QQShare.SHARE_TO_QQ_ARK_INFO, arkStr);
                    /*QQ分享增加ARK end*/
                }

                doShareToQQ(params);
                return;
            case R.id.radioBtn_net_image: // 定向分享网络图片
                if (shareType == QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
                    // 纯图分享只能支持本地图片
                    mRadioBtn_localImage.setChecked(true);
                    startPickLocaleImage(this);
                    showToast("纯图分享只支持本地图片");
                } else {
                    imageUrl.setText(R.string.qqshare_imageUrl_content);
                }
                return;
            case R.id.radioBtn_local_image: // 定向分享本地图片
                startPickLocaleImage(this);
                return;
            case R.id.radioBtn_share_type_audio:
                shareType = QQShare.SHARE_TO_QQ_TYPE_AUDIO;
                break;
            case R.id.radioBtn_share_type_default:
                shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
                break;
            case R.id.radioBtn_share_type_image:
                shareType = QQShare.SHARE_TO_QQ_TYPE_IMAGE;
                break;
            case R.id.radioBtn_share_type_app:
                shareType = QQShare.SHARE_TO_QQ_TYPE_APP;
                break;
            case R.id.radioBtn_share_type_mini_program:
                shareType = QQShare.SHARE_TO_QQ_MINI_PROGRAM;
                break;
        }
        initShareUI(shareType);
    }

    /**
     * 初始化UI
     * @param shareType
     */
    private void initShareUI(int shareType) {
        arkInfo.setText(R.string.qqshare_ark_content);

        switch (shareType) {
            case QQShare.SHARE_TO_QQ_TYPE_IMAGE:

                arkInfo.setText("");

                mContainer_title.setVisibility(View.GONE);
                mContainer_summary.setVisibility(View.GONE);
                mContainer_audioUrl.setVisibility(View.GONE);
                mContainer_targetUrl.setVisibility(View.GONE);
                mContainer_imgUrl.setVisibility(View.VISIBLE);
                mContainer_appName.setVisibility(View.GONE);
                mRadioBtn_localImage.setChecked(true);
                imageUrl.setText(null);
                //startPickLocaleImage(this);
                targetUrl.setVisibility(View.VISIBLE);
                mContainer_miniProgramAppid.setVisibility(View.GONE);
                mContainer_miniProgramPath.setVisibility(View.GONE);
                mContainer_miniProgramType.setVisibility(View.GONE);
                mContainer_arkInfo.setVisibility(View.VISIBLE);
                return;
            case QQShare.SHARE_TO_QQ_TYPE_AUDIO:
                mContainer_audioUrl.setVisibility(View.VISIBLE);
                title.setText("不要每天陪我聊天因为我害怕会喜欢上你");
                imageUrl.setText("http://y.gtimg.cn/music/photo_new/T002R300x300M000003KIU6V02sS7C.jpg?max_age=2592000");
                mEditTextAudioUrl
                        .setText("http://dl.stream.qqmusic.qq.com/C400004A3WK61kSH3c.m4a?guid=1567768602&vkey=2A63FD1434006630168B72AF423E57EDE303C93EE613D951DFFF27CCD1B6C7EBAE4F4AD5F8F3B681A6132C070B431D0140AD72D11C5AB90A&uin=&fromtag=154");
                targetUrl
                        .setText("http://c.y.qq.com/v8/playsong.html?songid=109325260&songmid=000kuo2H2xJqfA&songtype=0&source=mqq&_wv=1");
                summary.setText("乔紫乔");
                appName.setText("QQ音乐");
                targetUrl.setVisibility(View.VISIBLE);
                break;
            case QQShare.SHARE_TO_QQ_TYPE_DEFAULT:
                targetUrl.setVisibility(View.VISIBLE);
                mContainer_audioUrl.setVisibility(View.GONE);
                title.setText(R.string.qqshare_title_content);
                imageUrl.setText(R.string.qqshare_imageUrl_content);
                targetUrl.setText(R.string.qqshare_targetUrl_content);
                summary.setText(R.string.qqshare_summary_content);
                appName.setText(R.string.qqshare_appName_content);
                break;
            case QQShare.SHARE_TO_QQ_TYPE_APP:
                targetUrl.setVisibility(View.GONE);
                title.setText("【推荐】《Microsoft Excel》");
                summary.setText("办公|57.4MB|785万次下载|4.6/5星");
                imageUrl.setText("http://url.cn/424xgoi");
                targetUrl.setText("http://url.cn/424xgot");
                break;
            case QQShare.SHARE_TO_QQ_MINI_PROGRAM:
                mContainer_miniProgramAppid.setVisibility(View.VISIBLE);
                mContainer_miniProgramPath.setVisibility(View.VISIBLE);
                mContainer_miniProgramType.setVisibility(View.VISIBLE);
                mContainer_appName.setVisibility(View.GONE);
                mContainer_arkInfo.setVisibility(View.GONE);
                targetUrl.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                summary.setVisibility(View.VISIBLE);
                mContainer_title.setVisibility(View.VISIBLE);
                mContainer_summary.setVisibility(View.VISIBLE);
                mContainer_targetUrl.setVisibility(View.VISIBLE);
                title.setText("QQ互联");
                summary.setText("QQ小程序");
                imageUrl.setText("http://www.3wyu.com/wp-content/uploads/6e0eaf15gy1fvr5tnm2dfj20f108gtad.jpg");
                targetUrl.setText(R.string.qqshare_targetUrl_content);
                return;
        }
        mContainer_title.setVisibility(View.VISIBLE);
        mContainer_summary.setVisibility(View.VISIBLE);
        mContainer_targetUrl.setVisibility(View.VISIBLE);
        mContainer_imgUrl.setVisibility(View.VISIBLE);
        mContainer_appName.setVisibility(View.VISIBLE);
        mContainer_arkInfo.setVisibility(View.VISIBLE);
        mContainer_miniProgramAppid.setVisibility(View.GONE);
        mContainer_miniProgramPath.setVisibility(View.GONE);
        mContainer_miniProgramType.setVisibility(View.GONE);
        mRadioBtn_netImage.setChecked(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
        	Tencent.onActivityResultData(requestCode,resultCode,data,qqShareListener);
        } else if (requestCode == 0) {
        	String path = null;
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.getData() != null) {
                    // 根据返回的URI获取对应的SQLite信息
                    Uri uri = data.getData();
                    path = Util.getPath(this, uri);
                }
            }
            if (path != null) {
                imageUrl.setText(path);
            } else {
            	if(shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE){
            		showToast("请重新选择图片");
            	}
            }
        }
    }

    private static final void startPickLocaleImage(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        if (android.os.Build.VERSION.SDK_INT >= Util.Build_VERSION_KITKAT) {
            intent.setAction(Util.ACTION_OPEN_DOCUMENT);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(
                Intent.createChooser(intent, activity.getString(R.string.str_image_local)), 0);
    }
    
    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
                Util.toastMessage(QQShareActivity.this, "onCancel: ");
            }
        }
        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Util.toastMessage(QQShareActivity.this, "onComplete: " + response.toString());
        }
        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Util.toastMessage(QQShareActivity.this, "onError: " + e.errorMessage, "e");
        }
    };

    private void doShareToQQ(final Bundle params) {
        // QQ分享要在主线程做
        if (null != MainActivity.mTencent) {
            MainActivity.mTencent.shareToQQ(QQShareActivity.this, params, qqShareListener);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MainActivity.mTencent != null) {
            MainActivity.mTencent.releaseResource();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        switch (buttonView.getId()) {
            case R.id.checkBox_qzone_auto_open:
                if (mCheckBox_qzoneItemHide.isChecked()) {
                    mCheckBox_qzoneAutoOpen.setChecked(false);
                    showToast("Qzone隐藏选项打开时, 不能自动弹Qzone窗口");
                } else {
                    if (isChecked) {
                        // 最后一个二进制位置为1, 其他位不变
                        mExtarFlag |= QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN;
                    } else {
                        // 最后一个二进制位置为0, 其他位不变
                        mExtarFlag &= (0xFFFFFFFF - QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                    }
                }
                return;
            case R.id.checkBox_qzone_item_hide:
                if (mCheckBox_qzoneAutoOpen.isChecked()) {
                    mCheckBox_qzoneItemHide.setChecked(false);
                    showToast("Qzone自动弹窗选项打开时, 不能隐藏Qzone Item.");
                } else {
                    if (isChecked) {
                        // 倒数第二位置为1, 其他位不变
                        mExtarFlag |= QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE;
                    } else {
                        // 倒数第二位置为0, 其他位不变
                        mExtarFlag &= (0xFFFFFFFF - QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                    }
                }
                return;
        }

    }
}
