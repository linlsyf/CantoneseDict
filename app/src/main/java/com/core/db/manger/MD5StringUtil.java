package com.core.db.manger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

/**
 * 创建者：zw
 * 修改时间：2015-3-2 下午2:01:05
 * 作用：
 */
public class MD5StringUtil {
	/** 
	 * 将字符串转成MD5值 
	 *  
	 * @param string 
	 * @return 
	 */  
	public static String stringToMD5(String string) {  
	    byte[] hash;  
	  
	    try {  
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));  
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	        return null;  
	    } catch (UnsupportedEncodingException e) {  
	        e.printStackTrace();  
	        return null;  
	    }  
	  
	    StringBuilder hex = new StringBuilder(hash.length * 2);  
	    for (byte b : hash) {  
	        if ((b & 0xFF) < 0x10)  
	            hex.append("0");  
	        hex.append(Integer.toHexString(b & 0xFF));  
	    }  
	  
	    return hex.toString();  
	}  
	static int FileHashDefaultChunkSizeForReadingData = 1024;
	/**
	 * 
	* 创建者：qjt
	* 时间：2015-7-17 上午9:15:15
	* 注释：转换文件的md5值 
	* @param file
	* @return
	* @throws FileNotFoundException
	 */
	public static String fileToMD5(File toCalculateFile) throws FileNotFoundException{
		
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			Log.e("FileUtils", "Exception while getting digest", e);
			return null;
		}
		if (!toCalculateFile.exists()){
			return "";
		}
		InputStream is = null;
		try {
				is = new FileInputStream(toCalculateFile);
		
		} catch (FileNotFoundException e) {
			Log.e("FileUtils", "Exception while getting FileInputStream", e);
			return null;
		}

		boolean hasMoreData = true;
		int chunkSizeForReadingData = FileHashDefaultChunkSizeForReadingData;

		long bigFileLength = 1024 * 1024 * 32;
		long filelength = toCalculateFile.length();
		if (filelength > bigFileLength) {
			// 计算大文件md5采用跳跃计算
			byte[] buffer = new byte[chunkSizeForReadingData];
			int readBytesCount;
			try {
				while (hasMoreData) {

					readBytesCount = is.read(buffer);
					if (readBytesCount == 0)
						break;
					if (readBytesCount == -1) {
						hasMoreData = false;
						continue;
					}
					digest.update(buffer, 0, readBytesCount);

					byte[] bufferSkip = new byte[chunkSizeForReadingData * 31];
					readBytesCount = is.read(bufferSkip);
					if (readBytesCount == 0)
						break;
					if (readBytesCount == -1) {
						hasMoreData = false;
						continue;
					}
				}

				byte[] md5sum = digest.digest();
				BigInteger bigInt = new BigInteger(1, md5sum);
				String output = bigInt.toString(16);
				// Fill to 32 chars
				output = String.format("%32s", output).replace(' ', '0');
				return output;
			} catch (IOException e) {
				throw new RuntimeException("Unable to process file for MD5", e);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					 Log.e("FileUtils", "Exception on closing MD5 input stream", e);
				}
			}

		} else {
			// 小文件
			byte[] buffer = new byte[chunkSizeForReadingData];
			int read;
			try {
				while ((read = is.read(buffer)) > 0) {
					digest.update(buffer, 0, read);
				}
				byte[] md5sum = digest.digest();
				BigInteger bigInt = new BigInteger(1, md5sum);
				String output = bigInt.toString(16);
				// Fill to 32 chars
				output = String.format("%32s", output).replace(' ', '0');
				return output;
			} catch (IOException e) {
				throw new RuntimeException("Unable to process file for MD5", e);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					Log.e("FileUtils", "Exception on closing MD5 input stream", e);
				}
			}
		}
		
	}

}
