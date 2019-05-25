package com.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video;


public class ThumbnailQuery {
    private static final String TAG = ThumbnailQuery.class.getSimpleName();

    public static String queryImageThumbnailByPath(Context context, String path) {
        Uri uri = Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[] { Images.Media._ID };
        String selection = Images.Media.DATA + " = ? ";
        String[] selectionArgs = new String[] { path };

        Cursor cursor = query(context, uri, projection, selection,
                selectionArgs);
        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(Images.Media._ID));
        }
        cursor.close();
        if (id == -1) {
            return null;
        }

        uri = Images.Thumbnails.EXTERNAL_CONTENT_URI;
        projection = new String[] { Images.Thumbnails.DATA };
        selection = Images.Thumbnails.IMAGE_ID + " = ? ";
        selectionArgs = new String[] { String.valueOf(id) };

        cursor = query(context, uri, projection, selection, selectionArgs);
        String thumbnail = null;
        if (cursor.moveToFirst()) {
            int idxData = cursor.getColumnIndex(Images.Thumbnails.DATA);
            thumbnail = cursor.getString(idxData);
        }
        cursor.close();
        return thumbnail;
    }

    public static String queryVideoThumbnailByPath(Context context, String path) {
        Uri uri = Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[] { Video.Media._ID };
        String selection = Video.Media.DATA + " = ? ";
        String[] selectionArgs = new String[] { path };

        Cursor cursor = query(context, uri, projection, selection,
                selectionArgs);
        int mediaId = -1;
        if (cursor.moveToFirst()) {
            int idxId = cursor.getColumnIndex(Video.Media._ID);
            mediaId = cursor.getInt(idxId);
        }
        cursor.close();
        if (mediaId == -1) {
            return null;
        }

        uri = Video.Thumbnails.EXTERNAL_CONTENT_URI;
        projection = new String[] { Video.Thumbnails.DATA };
        selection = Video.Thumbnails.VIDEO_ID + " =  ? ";
        selectionArgs = new String[] { String.valueOf(mediaId) };

        cursor = query(context, uri, projection, selection, selectionArgs);
        String thumbnail = null;
        if (cursor.moveToFirst()) {
            int idxData = cursor.getColumnIndex(Video.Thumbnails.DATA);
            thumbnail = cursor.getString(idxData);
        }
        cursor.close();
        return thumbnail;
    }

    private static Cursor query(Context context, Uri uri, String[] projection,
                                String selection, String[] selectionArgs) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(uri, projection, selection, selectionArgs,
                null);
        return cursor;
    }
}
