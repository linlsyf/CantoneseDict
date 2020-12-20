package com.iflytek;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.speech.setting.IatSettings;
import com.iflytek.speech.util.FucUtil;
import com.iflytek.speech.util.JsonParser;
import com.linlsyf.area.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class IatDemoNewUtils   {
	private static String TAG = IatDemoNewUtils.class.getSimpleName();
	private static FragmentActivity activity;
	// 语音听写对象
	private SpeechRecognizer mIat;
	// 语音听写UI
	private RecognizerDialog mIatDialog;
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();


	private Toast mToast;
	private SharedPreferences mSharedPreferences;
	// 引擎类型
	private String mEngineType = SpeechConstant.TYPE_CLOUD;

	private boolean mTranslateEnable = false;
	private String resultType = "json";

	private boolean cyclic = false;//音频流识别是否循环调用

	private StringBuffer buffer = new StringBuffer();

	Handler han = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0x001) {
				executeStream();
			}
		}
	};
	private mcCallBack mCallBack;


	public  void init(FragmentActivity context) {
		activity=context;

		// 初始化识别无UI识别对象
		// 使用SpeechRecognizer对象，可根据回调消息自定义界面；
		mIat = SpeechRecognizer.createRecognizer(activity, mInitListener);
		
		// 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
		// 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
		mIatDialog = new RecognizerDialog(activity, mInitListener);

		mSharedPreferences = activity.getSharedPreferences(IatSettings.PREFER_NAME,
				Activity.MODE_PRIVATE);
		mToast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
	
	}




	int ret = 0; // 函数调用返回值




	public void onClick() {
		if( null == mIat ){
			// 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
			this.showTip( "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化" );
			return;
		}
		
	
			// 移动数据分析，收集开始听写事件

			buffer.setLength(0);
			mIatResults.clear();
			// 设置参数
			setParam();
			boolean isShowDialog = mSharedPreferences.getBoolean(
					activity.getString(R.string.pref_key_iat_show), true);
			if (isShowDialog) {
				// 显示听写对话框
				mIatDialog.setListener(mRecognizerDialogListener);
				mIatDialog.show();
				showTip(activity.getString(R.string.text_begin));
			} else {
				// 不显示听写对话框
				ret = mIat.startListening(mRecognizerListener);
				if (ret != ErrorCode.SUCCESS) {
					showTip("听写失败,错误码：" + ret+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
				} else {
					showTip(activity.getString(R.string.text_begin));
				}
			}
		
	}

	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败，错误码：" + code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
			}
		}
	};

	/**
	 * 上传联系人/词表监听器。
	 */
	private LexiconListener mLexiconListener = new LexiconListener() {

		@Override
		public void onLexiconUpdated(String lexiconId, SpeechError error) {
			if (error != null) {
				showTip(error.toString());
			} else {
				showTip(activity.getString(R.string.text_upload_success));
			}
		}
	};

	/**
	 * 听写监听器。
	 */
	private RecognizerListener mRecognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			// 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
			showTip("开始说话");
		}

		@Override
		public void onError(SpeechError error) {
			// Tips：
			// 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
			if(mTranslateEnable && error.getErrorCode() == 14002) {
				showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
			} else {
				showTip(error.getPlainDescription(true));
			}
		}

		@Override
		public void onEndOfSpeech() {
			// 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
			showTip("结束说话");
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			Log.d(TAG, results.getResultString());
			if (resultType.equals("json")) {
				if( mTranslateEnable ){
					printTransResult( results );
				}else{
					printResult(results);
				}
			}
			if (isLast & cyclic) {
				// TODO 最后的结果
				Message message = Message.obtain();
				message.what = 0x001;
				han.sendMessageDelayed(message,100);
			}
		}

		@Override
		public void onVolumeChanged(int volume, byte[] data) {
			showTip("当前正在说话，音量大小：" + volume);
			Log.d(TAG, "返回音频数据："+data.length);
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			//		String sid = obj.activity.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			//		Log.d(TAG, "session id =" + sid);
			//	}
		}
	};

	private void printResult(RecognizerResult results) {
		String text = JsonParser.parseIatResult(results.getResultString());

		String sn = null;
		// 读取json结果中的sn字段
		try {
			JSONObject resultJson = new JSONObject(results.getResultString());
			sn = resultJson.optString("sn");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mIatResults.put(sn, text);

		StringBuffer resultBuffer = new StringBuffer();
		for (String key : mIatResults.keySet()) {
			resultBuffer.append(mIatResults.get(key));
		}


	}

	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult results, boolean isLast) {
			if( mTranslateEnable ){
				printTransResult( results );
			}else{
				printResult(results);
			}
			
		}

		/**
		 * 识别回调错误.
		 */
		public void onError(SpeechError error) {
			if(mTranslateEnable && error.getErrorCode() == 14002) {
				showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
			} else {
				showTip(error.getPlainDescription(true));
			}
		}

	};


	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}

	/**
	 * 参数设置
	 * 
	 * @return
	 */
	public void setParam() {
		// 清空参数
		mIat.setParameter(SpeechConstant.PARAMS, null);

		// 设置听写引擎
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
		// 设置返回结果格式
		mIat.setParameter(SpeechConstant.RESULT_TYPE, resultType);
		
		this.mTranslateEnable = mSharedPreferences.getBoolean( this.activity.getString(R.string.pref_key_translate), false );
		if( mTranslateEnable ){
			Log.i( TAG, "translate enable" );
			mIat.setParameter( SpeechConstant.ASR_SCH, "1" );
			mIat.setParameter( SpeechConstant.ADD_CAP, "translate" );
			mIat.setParameter( SpeechConstant.TRS_SRC, "its" );
		}

		String lag = mSharedPreferences.getString("iat_language_preference",
				"mandarin");
		if (lag.equals("en_us")) {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
			mIat.setParameter(SpeechConstant.ACCENT, null);
			
			if( mTranslateEnable ){
				mIat.setParameter( SpeechConstant.ORI_LANG, "en" );
				mIat.setParameter( SpeechConstant.TRANS_LANG, "cn" );
			}
		} else {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// 设置语言区域
			mIat.setParameter(SpeechConstant.ACCENT, lag);
			
			if( mTranslateEnable ){
				mIat.setParameter( SpeechConstant.ORI_LANG, "cn" );
				mIat.setParameter( SpeechConstant.TRANS_LANG, "en" );
			}
		}
		//此处用于设置dialog中不显示错误码信息
		//mIat.setParameter("view_tips_plain","false");

		// 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
		mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
		
		// 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
		mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));
		
		// 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
		mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));
		
		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
	}
	
	private void printTransResult (RecognizerResult results) {
		String trans  = JsonParser.parseTransResult(results.getResultString(),"dst");
		String oris = JsonParser.parseTransResult(results.getResultString(),"src");

		if( TextUtils.isEmpty(trans)||TextUtils.isEmpty(oris) ){
			showTip( "解析结果失败，请确认是否已开通翻译功能。" );
		}
	}
	public  interface mcCallBack extends Serializable {

		void call(String msg);
	}
	public void setcallBack(mcCallBack callBack){
		this.mCallBack=callBack;

	}

	public void onDestroy() {

		if( null != mIat ){
			// 退出时释放连接
			mIat.cancel();
			mIat.destroy();
		}
	}



	//执行音频流识别操作
	private void executeStream() {
		buffer.setLength(0);
		mIatResults.clear();
		// 设置参数
		setParam();
		// 设置音频来源为外部文件
		mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
		// 也可以像以下这样直接设置音频文件路径识别（要求设置文件在sdcard上的全路径）：
		// mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-2");
		 //mIat.setParameter(SpeechConstant.ASR_SOURCE_PATH, "sdcard/XXX/XXX.pcm");
		ret = mIat.startListening(mRecognizerListener);
		if (ret != ErrorCode.SUCCESS) {
			showTip("识别失败,错误码：" + ret+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
		} else {
			byte[] audioData = FucUtil.readAudioFile(activity, "iattest.wav");

			if (null != audioData) {
				showTip(activity.getString(R.string.text_begin_recognizer));
				// 一次（也可以分多次）写入音频文件数据，数据格式必须是采样率为8KHz或16KHz（本地识别只支持16K采样率，云端都支持），
				// 位长16bit，单声道的wav或者pcm
				// 写入8KHz采样的音频时，必须先调用setParameter(SpeechConstant.SAMPLE_RATE, "8000")设置正确的采样率
				// 注：当音频过长，静音部分时长超过VAD_EOS将导致静音后面部分不能识别。
				// 音频切分方法：FucUtil.splitBuffer(byte[] buffer,int length,int spsize);
				mIat.writeAudio(audioData, 0, audioData.length);

				mIat.stopListening();
			} else {
				mIat.cancel();
				showTip("读取音频流失败");
			}
		}
	}
}
