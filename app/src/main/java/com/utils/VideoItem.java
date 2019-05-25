package com.utils;

import android.graphics.Bitmap;

import com.core.db.greenDao.entity.VideoDB;

public class VideoItem extends VideoDB {
	private String id;
	private String name;
	private String data;
	private String thumbPath;

	private long duration;
	private String durationString;
	private long size;
	private Bitmap bitmap;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public String getDurationString() {
		return durationString;
	}

	public void setDurationString(String durationString) {
		this.durationString = durationString;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public VideoItem setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		return this;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
