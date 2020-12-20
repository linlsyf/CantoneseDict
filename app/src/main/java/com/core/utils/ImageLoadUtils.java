package com.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.core.CoreApplication;
import com.easy.recycleview.custom.baseview.inter.IloadImage;
import com.easysoft.utils.lib.imge.ImageUtils;

public class ImageLoadUtils extends ImageUtils  implements IloadImage {
	
	static ImageLoadUtils utils;
	static ImageUtils systemImageUtils;
	 public static ImageLoadUtils getInStance(){
		 if(utils==null){
			 utils=new ImageLoadUtils();
		 }
		 systemImageUtils=ImageUtils.getInStance(CoreApplication.getAppContext());
		 return  utils;
	 }
//
	public void load(final String url, final ImageView myImageView){

		systemImageUtils.load(url,myImageView);

	}

	@Override
	public void load(Bitmap bitmap, ImageView imageView) {
		 imageView.setImageBitmap(bitmap);
//		systemImageUtils.load(bitmap,imageView);
	}

	public void loadPath(final String path, final ImageView myImageView){
		systemImageUtils.loadPath(path,myImageView);

	}
	public void loadResourceId(int id,ImageView myImageView){
		myImageView.setImageResource(id);
	//	systemImageUtils.loadResourceId(id,myImageView);
	}
	  public static class BitmapUtil {


	        public static Bitmap zoomBitmap(Bitmap bitmap, float scale) {
	            int width = bitmap.getWidth();// 鑾峰彇鍘熷浘鐨勫
	            int height = bitmap.getHeight();// 鑾峰彇鍘熷浘鐨勯珮
	            Matrix matrix = new Matrix();// 鍒涘缓Matrix鐭╅樀瀵硅薄
	            matrix.setScale(scale, scale);// 璁剧疆瀹介珮鐨勭缉鏀炬瘮
	            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	        }

	        public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
	            return new BitmapDrawable(context.getResources(), bitmap);
	        }

	        public static Bitmap getBitmapById(Context context, int id) {
	            Drawable drawable = context.getResources().getDrawable(id);
	            return DrawableUtil.drawableToBitmap(drawable);
	        }

	        public static Bitmap buildRoundBitmap(Bitmap bitmap, float round) {
	            int width = bitmap.getWidth();
	            int height = bitmap.getHeight();
	            RectF rectF = new RectF(0, 0, width, height);

	            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	            paint.setColor(Color.GREEN);

	            Bitmap roundBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	            Canvas canvas = new Canvas(roundBitmap);
	            canvas.drawBitmap(bitmap, 0, 0, paint);
	            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	            canvas.drawRoundRect(rectF, round, round, paint);

	            return roundBitmap;
	        }
	    }

	    public static class DrawableUtil {

	        public static Bitmap drawableToBitmap(Drawable drawable) {
	            int width = drawable.getIntrinsicWidth();
	            int height = drawable.getIntrinsicHeight();
	            return buildBitmap(drawable, width, height);
	        }

	        public static Bitmap drawableToBitmap(Drawable drawable, float scale) {
	            int width = (int) (drawable.getIntrinsicWidth() * scale);
	            int height = (int) (drawable.getIntrinsicHeight() * scale);
	            return buildBitmap(drawable, width, height);
	        }

	        private static Bitmap buildBitmap(Drawable drawable, int width, int height) {
	            Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
	            Canvas canvas = new Canvas(bitmap);
	            drawable.setBounds(0, 0, width, height);
	            drawable.draw(canvas);
	            return bitmap;
	        }

	    }



}
