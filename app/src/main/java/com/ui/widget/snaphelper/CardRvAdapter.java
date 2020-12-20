package com.ui.widget.snaphelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.easy.recycleview.bean.CentLayoutConfig;
import com.easy.recycleview.bean.DyItemBean;
import com.linlsyf.area.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/6.
 */

public class CardRvAdapter extends RecyclerView.Adapter<CardRvAdapter.ItemViewHolder> {
    private List<DyItemBean> list;
    private Context context;

    public CardRvAdapter(Context context, List<DyItemBean> dataList) {
        this.context = context;
        this.list = dataList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        CentLayoutConfig centLayoutConfig=list.get(position).getCentLayoutConfig();
        if (centLayoutConfig!=null){

//            Glide.with(context).load(list.get(position).getCentLayoutConfig().getImgUrl()).into(holder.iv);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
