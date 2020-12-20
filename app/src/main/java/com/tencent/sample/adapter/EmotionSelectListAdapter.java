package com.tencent.sample.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.linlsyf.area.R;

import java.util.ArrayList;
import java.util.List;

public class EmotionSelectListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    final List<Uri> mData;

    public EmotionSelectListAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<Uri>();
    }

    public void updateData(List<Uri> infos) {
        if (infos == null) {
            if (mData.size() != 0) {
                mData.clear();
                notifyDataSetChanged();
            }
            return;
        }

        mData.clear();
        mData.addAll(infos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= 0 && position < mData.size()){
            return mData.get(position);
        }else{
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if( convertView == null ){
            convertView = mInflater.inflate(R.layout.emotion_pic_item, parent, false);
            holder = new ViewHolder();
            holder.pathText = (TextView)convertView.findViewById(R.id.file_path_tv);
            holder.headImage = (ImageView)convertView.findViewById(R.id.select_iv);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        final String path = mData.get(position).toString();

        holder.pathText.setText(path);
        return convertView;
    }


    public static class ViewHolder{
        ImageView headImage;
        TextView pathText;
    }
}
