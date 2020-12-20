package com.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.business.bean.VideoBussinessItem;
import com.easysoft.utils.lib.string.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VideoUtils {
	private static StringBuilder mFormatBuilder = new StringBuilder();
	private static Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

	static ArrayList listItems ;



	private static final String[] VIDEOTHUMBNAIL_TABLE = new String[] {
			MediaStore.Video.Media._ID, // 0
			MediaStore.Video.Media.DATA, // 1 from android.provider.MediaStore.VideoDB
	};
	 public  static ArrayList<VideoItem> getVideodData(final Context context) {


		 try {
			 listItems = new ArrayList<VideoItem>();
			 Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
			 String[] projection = {MediaStore.Video.Media.DISPLAY_NAME,
					 MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE,
					 MediaStore.Video.Media.DATA};
			 Cursor cursor = getContentResolver(context).query(uri, projection, null, null, null);
			 while (cursor.moveToNext()) {
				 String name = cursor.getString(0);//文件名
				 long duration = cursor.getLong(1);//文件时长
				 long size = cursor.getLong(2);//文件大小
				 String data = cursor.getString(3);//文件路径
				 File storeFile = new File(data);
				 if (!storeFile.exists()) {
					 continue;
				 }
				 VideoItem item = new VideoItem();
				 item.setName(name);
				 item.setDuration(duration);
				 item.setDurationString(stringForTime((int) duration));
				 item.setSize(size);
				 item.setData(data);
				 String thumbPath = "";
				 item.setThumbPath(thumbPath);
//				 item.setBitmap(getThumbnail(data, 200, 100));
				 listItems.add(item);
			 }

			 cursor.close();


		 } catch(Exception e){

		 }

		 return listItems;
//	            }
//	        }).start();

	    }
	/**
	 * 把毫秒转换成：1:20:30这里形式
	 * @param timeMs
	 * @return
	 */
	public static String stringForTime(int timeMs) {
		int totalSeconds = timeMs / 1000;
		int seconds = totalSeconds % 60;


		int minutes = (totalSeconds / 60) % 60;


		int hours = totalSeconds / 3600;


		mFormatBuilder.setLength(0);
		if (hours > 0) {
			return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
		} else {
			return mFormatter.format("%02d:%02d", minutes, seconds).toString();
		}
	}

	protected static ContentResolver getContentResolver(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		
	
		return contentResolver;
	}




	private  static Bitmap getThumbnail(final String imagePath, int width, int height) {
		Bitmap bitmap =null;
//				try {
//					mmrc.setMediaDataSource(imagePath);
//					//注意这里传的是微秒
//					 bitmap = mmrc.getScaledFrameAtTime(2 * 1000 * 1000, MediaMetadataRetrieverCompat.OPTION_CLOSEST,
//							100, 100);
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
		return bitmap;
	}


	public static  boolean checkPermission(Activity activity) {
		boolean isHas=false;
		if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
		  isHas=true;
		}
		return isHas;
	}
	public static boolean isContainChinese(String str) {

		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}
	public static String readOrgText(InputStream inputStream)
	{
		String result = "";//如果path是传递过来的参数，可以做一个非目录的判断
			try {
				if (inputStream != null)
				{
					BufferedReader buffreader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
					String line;
					//分行读取
					while (( line = buffreader.readLine()) != null) {
						if (StringUtils.isNotEmpty(line)){
							try{
								result=result+line;
							}catch (Exception e){
								System.out.print(e.getMessage());
							}


						}
//						newList.add(line+"\n");
					}
					inputStream.close();
				}
			}
			catch (Exception e)
			{
				Log.d("TestFile", "The File doesn't not exist.");
			}


		return result;
	}

	public static String radTxtFile(String strFilePath,List<VideoItem> newList)
	{
		String path = strFilePath;
		//打开文件
		File file = new File(path);
		//如果path是传递过来的参数，可以做一个非目录的判断
		if (file.isDirectory())
		{
			Log.d("TestFile", "The File doesn't not exist.");
		}
		else
		{
			try {
				InputStream instream = new FileInputStream(file);
				if (instream != null)
				{
//					InputStreamReader inputreader = new InputStreamReader(instream);
					BufferedReader buffreader = new BufferedReader(new InputStreamReader(instream, "utf-8"));
					String line;
					//分行读取
					while (( line = buffreader.readLine()) != null) {
						 if (StringUtils.isNotEmpty(line)){
						 	try{
								String[]  data= line.split(",");
								VideoItem item=new VideoItem();
								item.setName(data[0]);
								item.setData(data[1]);
								newList.add(item);
							}catch (Exception e){
						 		System.out.print(e.getMessage());
							}


						 }
//						newList.add(line+"\n");
					}
					instream.close();
				}
			}
			catch (Exception e)
			{
				Log.d("TestFile", "The File doesn't not exist.");
			}

		}
		return strFilePath;
	}

}

