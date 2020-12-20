package com.ui.other.tuling.adapter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.easysoft.widget.fragment.FragmentHelper;
import com.linlsyf.area.R;
import com.ui.other.tuling.constant.TulingParams;
import com.ui.other.tuling.entity.MessageEntity;
import com.ui.other.tuling.news.NewsFragment;
import com.ui.other.tuling.news.newdetail.NewDetailFragment;
import com.ui.other.tuling.util.SpecialViewUtil;
import com.ui.other.tuling.util.TimeUtil;

import java.util.List;

public class ChatMessageAdapter extends BaseListAdapter<MessageEntity> {

    private FragmentActivity mContext;

    public static final int TYPE_LEFT = 0;
    public static final int TYPE_RIGHT = 1;

    public ChatMessageAdapter(FragmentActivity context, List<MessageEntity> list) {
        super(context, list);
        mContext = context;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getType() == TYPE_LEFT) {
            return TYPE_LEFT;
        }
        return TYPE_RIGHT;
    }

    private View createViewByType(int position) {
        if (getItem(position).getType() == TYPE_LEFT) {
            return mInflater.inflate(R.layout.item_conversation_left, null);
        }
        return mInflater.inflate(R.layout.item_conversation_right, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = createViewByType(position);
        }

        final MessageEntity entity = getItem(position);

        TextView tvTime = ViewHolder.get(convertView, R.id.tv_time);
        TextView btvMessage = ViewHolder.get(convertView, R.id.btv_message);

        if (isDisplayTime(position)) {
            tvTime.setVisibility(View.VISIBLE);
            tvTime.setText(TimeUtil.friendlyTime(mContext, entity.getTime()));
        } else {
            tvTime.setVisibility(View.GONE);
        }

        switch (entity.getCode()) {
            case TulingParams.TulingCode.URL:
                btvMessage.setText(SpecialViewUtil.getSpannableString(entity.getText(), entity.getUrl()));
                break;
            case TulingParams.TulingCode.NEWS:
                btvMessage.setText(SpecialViewUtil.getSpannableString(entity.getText(), "点击查看"));
                break;
            default:
                btvMessage.setText(entity.getText());
                break;
        }
        btvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (entity.getCode()) {
                    case TulingParams.TulingCode.URL:
//                        NavigateManager.gotoDetailActivity(mContext, entity.getUrl());


                        Bundle bundleDetail=new Bundle();
                        bundleDetail.putString("url", entity.getUrl());
                        FragmentHelper.showFrag(mContext, R.id.container_framelayout, new NewDetailFragment(), bundleDetail);


                        break;
                    case TulingParams.TulingCode.NEWS:

                        Bundle bundle=new Bundle();
                        bundle.putSerializable("messageEntity", entity);
                        FragmentHelper.showFrag(mContext, R.id.container_framelayout, new NewsFragment(), bundle);

//                        NavigateManager.gotoNewsActivity(mContext, entity);
                        break;
                }
            }
        });





        return convertView;
    }

    //  一分钟内的请求与回复不显示时间
    public boolean isDisplayTime(int position) {
        if (position > 0) {
            if ((getItem(position).getTime() - getItem(position-1).getTime()) > 60 * 1000) {
                return true;
            } else {
                return false;
            }
        } else if (position == 0) {
            return true;
        } else {
            return false;
        }
    }


}
