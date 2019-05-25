package com.core.update;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.core.ServerUrl;
import com.easysoft.utils.lib.http.EasyHttpUtils;
import com.linlsyf.area.R;

import org.json.JSONObject;

import java.io.File;

/**
 * 更新APK
 * @author zw
 */
public class UpdateAPK {
	public static Context context;
	private String VersionName = "";	//当前版本
	private String newVerName = "";	    //新版本名称
	private String newVContent = "";	//新版本内容
	private String newAPPname  = "";    //程序名称
	private JSONObject jo_v;			
	private ProgressBar pb;				//更新时，下载APK时进度条
	private TextView tv;
	/**
	 * 调试信息的，在这里全局判断显示还是隐藏
	 */
	public boolean DEBUG = true;
	private String url;

	public UpdateAPK(Context context){
		this.context = context;
	}
	
	public void Updatecheck() {
		// Toast.makeText(context, "有可用网络！", 3000).show();
		UpdateUtil.loading_process = 0;
		new Thread() {
			public void run() {
				if (UpdateUtil.isConnect(context)) {
					
					//发送意图，通知更新.只有当前版本的BUG修改，才允许互联网在线更新apk
					Message msg = BroadcastHandler.obtainMessage();
					BroadcastHandler.sendMessage(msg);

				}
			}
		}.start();
	}
	
	/**
	 * 把版本1.0.0--->转变成int[]类型，判断
	 * ver[0]是全新产品大改动 ，ver[1]是添加新功能 ，ver[2]是修改BUG的代号
	 * @return
	 */
	private int[] returnVerCode(String VerName){
		if(DEBUG)
		{
			System.out.println("returnVerCode--"+VerName);
		}
		String[] Stirng_tempVer = VerName.split("\\.");
		int[] int_tempVer = new int[3];
		for(int i=0;i<Stirng_tempVer.length;i++){
			if(DEBUG)
			{
				System.out.println("int_tempVer == "+int_tempVer[i]);
			}
			int_tempVer[i] = Integer.parseInt(Stirng_tempVer[i]);
		}
		return int_tempVer;
	}
	
	@SuppressLint("HandlerLeak")
	private Handler BroadcastHandler = new Handler() {
		public void handleMessage(Message msg) 
		{
			Beginning();
		}
	};
	AlertDialog m_AlertDialog = null;
	public void Beginning(){
		LinearLayout ll = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_loadapk, null);
		pb = (ProgressBar) ll.findViewById(R.id.down_pb);
		tv = (TextView) ll.findViewById(R.id.tv);
		Builder builder = new Builder(context);
		builder.setView(ll);
		builder.setTitle("版本更新");
		builder.setNegativeButton("后台下载",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						startservice(context);
						dialog.dismiss();
					}
				});
		builder.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface dialog, int arg1, KeyEvent arg2) {
				
				startservice(context);
				dialog.dismiss();
				
				return true;
			}
		});
		builder.setCancelable(false);
		m_AlertDialog = builder.show();
		  url =ServerUrl.baseUrl+ ServerUrl.updateUrl;

		url="https://github.com/linlsyf/AreaAndroid/releases/download/1.0.0/app.apk";
//		url="app-appCantonese-release-1.0.0"+  +".apk";


//		final String url =ServerUrl.baseUrl+ ServerUrl.updateUrl;


		new Thread() {
			public void run()
			{
				loadFile(url,  VersionUtil.NEW_SAVE_APK_NAME);

			}
		}.start();
	}
	
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case 1:
					//正在下载
					pb.setProgress(msg.arg1);
					UpdateUtil.loading_process = msg.arg1;
					tv.setText("更新进度：" + UpdateUtil.loading_process + "%");
					break;
				case 2:
					if(m_AlertDialog!=null){
						m_AlertDialog.dismiss();
						m_AlertDialog = null;
					}
					//下载成功
					stopservice();
					
//					File filepath = new File(UpdateUtil.getSDPath(),"");
					File apkFile =getDownLoadFile();

					Intent intent = new Intent(Intent.ACTION_VIEW);

//					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//						intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//						Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
//						intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//					} else {
						intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					}


//					intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
//					intent.setDataAndType(Uri.fromFile(new File(filepath, VersionUtil.NEW_SAVE_APK_NAME)),"application/vnd.android.package-archive");
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //调用系统安装需要添加  否则会出现安装过程中突然中断
					context.startActivity(intent);
					Toast.makeText(context, "下载"+"完成", Toast.LENGTH_SHORT).show();
					break;
				case -1:
					if(m_AlertDialog!=null){
						m_AlertDialog.dismiss();
						m_AlertDialog = null;
					}
					//下载失败,下载异常出错
					stopservice();
					Toast.makeText(context, "下载被终止，请检查网络和应用是否有读写内存权限",  Toast.LENGTH_SHORT).show();
					break;
				}
			}
			super.handleMessage(msg);
		}
	};
	public File getDownLoadFile(){
		File filepath = new File(UpdateUtil.getSDPath(),"");
		File file = new File(filepath,VersionUtil.NEW_SAVE_APK_NAME);
		return file;
	}
	
	public void loadFile(String url, String fileName) {
		EasyHttpUtils.getInStance().download(url,  fileName, getDownLoadFile().getAbsolutePath(), new EasyHttpUtils.OnDownloadListener() {
			@Override
			public void onDownloadSuccess() {
				sendMsg(2,0);
			}

			@Override
			public void onDownloading(int progress) {
				sendMsg(1,progress);
			}

			@Override
			public void onDownloadFailed() {
				sendMsg(-1,0);
			}
		});

	}
//	public void loadFile(String url) {
//		HttpClient client = new DefaultHttpClient();
//		HttpGet get = new HttpGet(url);
//		HttpResponse response;
//		try {
//			response = client.execute(get);
//
//			HttpEntity entity = response.getEntity();
//			float length = entity.getContentLength();
//
//			InputStream is = entity.getContent();
//			FileOutputStream fileOutputStream = null;
//			if (is != null) {
////				File filepath = new File(UpdateUtil.getSDPath(),"");
////				File file = new File(filepath,VersionUtil.NEW_SAVE_APK_NAME);
//				File file =getDownLoadFile();
//				fileOutputStream = new FileOutputStream(file);
//				byte[] buf = new byte[1024];
//				int ch = -1;
//				float count = 0;
//				while (( ch = is.read(buf)) != -1) {
//					fileOutputStream.write(buf, 0, ch);
//					count = count+ch;
//					sendMsg(1,(int) (count*100/length));
//				}
//			}
//
//			fileOutputStream.flush();
//			if (fileOutputStream != null) {
//				fileOutputStream.close();
//			}
//			sendMsg(2,0);
//		} catch (Exception e) {
//			sendMsg(-1,0);
//		}
//	}

	private void sendMsg(int flag,int c) {
		Message msg = new Message();
		msg.what = flag;
		msg.arg1=c;
		handler.sendMessage(msg);
	}
	
	public static void stopservice()
	{
		Intent intentstop=new Intent(context, VersionService.class);  
		context.stopService(intentstop);
	}
	
	private void startservice(Context context){
		Intent intent=new Intent(context, VersionService.class);  
		context.startService(intent);
	}
}
