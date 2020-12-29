package com.tencent.sample;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.linlsyf.cantonese.R;

/**
 * 用来显示从数据库拉取的照片信息
 * 
 * @author stevcao
 * 
 */
public class PhotoListDialog extends Dialog {
	private Context mContext;
	private JSONObject mJson;
	private List<PhotoInfo> mPhotoList;
	private ListView mListView;
	private ImageView mBigimage;
	private static final String TAG = "PhotoListDialog";

	/**
	 * 
	 * @param context
	 * @param json 执行Tensent.listPhoto()获取的json对象
	 */
	public PhotoListDialog(Context context, JSONObject json) {
		super(context);
		this.mJson = json;
		this.mContext = context;
		mPhotoList = new ArrayList<PhotoInfo>();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_list);
		setTitle(R.string.str_photo_list);
		findView();
		try {
			initData();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		initListView();
		setClick();
	}

	private void findView() {
		this.mListView = (ListView) findViewById(R.id.photo_list);
		this.mListView.setVerticalScrollBarEnabled(true);
		mBigimage = (ImageView) findViewById(R.id.show_image);
	}

	private void initData() throws JSONException {
		JSONArray photoArray = mJson.getJSONArray("photos");
		for (int i = 0; i < photoArray.length(); i++) {
			JSONObject photo = photoArray.getJSONObject(i);
			PhotoInfo info = new PhotoInfo();
			info.large_url = photo.getJSONObject("large_image")
					.getString("url");
			info.small_url = photo.getString("small_url");
			info.name = photo.getString("name");
			mPhotoList.add(info);
		}
		new Thread(){

			@Override
			public void run() {
				if (mPhotoList.size() != 0) {
					Bitmap bitmap = getbitmap(mPhotoList.get(0).large_url);
					Message msg = new Message();
					msg.what = 1;
					msg.obj = bitmap;
					mHandler.sendMessage(msg);
				}
			}
			
		}.start();
	}

	private void initListView() {
		PhotoListAdapter adapter = new PhotoListAdapter(mPhotoList);
		mListView.setAdapter(adapter);
	}

	private void setClick() {
		this.mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					 int position, long id) {
				final int index = position;
				new Thread() {

					@Override
					public void run() {
						Bitmap bitmap = getbitmap(mPhotoList
								.get(index).large_url);
						Message msg = new Message();
						msg.what = 1;
						msg.obj = bitmap;
						mHandler.sendMessage(msg);
					}
					
				}.start();
				
			}

		});
	}
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				int position = msg.arg1;
				Bitmap bitmap = (Bitmap) msg.obj;
				if (mListView.getChildAt(position) != null) {
					ImageView image = (ImageView) mListView.getChildAt(
							position).findViewById(R.id.photo_image);
					image.setImageBitmap(bitmap);
				}
			}else if (msg.what == 1) {
				Bitmap bitmap = (Bitmap)msg.obj;
				mBigimage.setImageBitmap(bitmap);
			}
		}

	};
	class PhotoListAdapter extends BaseAdapter {

		private List<PhotoInfo> mList;
		private LayoutInflater mInflater;
		List<Bitmap> mBitmapList;



		public PhotoListAdapter(List<PhotoInfo> infoList) {
			super();
			this.mList = infoList;
			mInflater = LayoutInflater.from(mContext);
			mBitmapList = new ArrayList<Bitmap>();
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < mList.size(); i++) {
						Bitmap bitmap = getbitmap(mList.get(i).small_url);
						mBitmapList.add(bitmap);
						if (bitmap != null) {
							Message msg = new Message();
							msg.what = 0;
							msg.arg1 = i;
							msg.obj = bitmap;
							mHandler.sendMessage(msg);
						}
					}
				}
			}).start();
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.photo_list_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.photo_name);
				holder.image = (ImageView) convertView
						.findViewById(R.id.photo_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(mList.get(position).name);
			if (position < mBitmapList.size()) {
				holder.image.setImageBitmap(mBitmapList.get(position));
			}
			return convertView;
		}

		class ViewHolder {
			TextView name;
			ImageView image;
		}
	}
	/** 
     * 根据一个网络连接(String)获取bitmap图像 
     *  
     * @param imageUri 
     * @return 
     * @throws MalformedURLException 
     */  
    public static Bitmap getbitmap(String imageUri) {  
        Log.v(TAG, "getbitmap:" + imageUri);
        // 显示网络上的图片  
        Bitmap bitmap = null;  
        try {  
            URL myFileUrl = new URL(imageUri);  
            HttpURLConnection conn = (HttpURLConnection) myFileUrl  
                    .openConnection();  
            conn.setDoInput(true);  
            conn.connect();  
            InputStream is = conn.getInputStream();  
            bitmap = BitmapFactory.decodeStream(is);  
            is.close();  
  
            Log.v(TAG, "image download finished." + imageUri);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {  
            e.printStackTrace();  
            Log.v(TAG, "getbitmap bmp fail---");
            bitmap = null;
        }  
        return bitmap;  
    }  
	/**
	 * 用来存储显示的照片信息
	 * 
	 * @author stevcao
	 * 
	 */
	class PhotoInfo {
		public String name;
		public String large_url;
		public String small_url;
	}
}
