package com.ui.other.tuling.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linlsyf.area.R;
import com.ui.other.tuling.entity.NewsEntity;
import com.ui.other.tuling.util.IsNullOrEmpty;

import java.util.List;

//import butterknife.Bind;
//import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2015/2/5.
 */
public class NewsAdapter extends BaseListAdapter<NewsEntity> {

    public NewsAdapter(Context context, List<NewsEntity> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_news_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final NewsEntity entity = getItem(position);
        if (!IsNullOrEmpty.isEmpty(entity.getIcon())) {
        }
        holder.tvNewsTitle.setText(entity.getArticle() + "");
        holder.tvNewsContent.setText("来自" + entity.getSource());

        return convertView;
    }

    static class ViewHolder {
//        @Bind(R.id.iv_news_icon)
        ImageView ivNewsIcon;
//        @Bind(R.id.tv_news_title)
        TextView tvNewsTitle;
//        @Bind(R.id.tv_news_content)
        TextView tvNewsContent;

        ViewHolder(View view) {
//            ButterKnife.bind(this, view);
        }
    }
}
