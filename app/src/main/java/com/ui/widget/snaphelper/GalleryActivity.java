package com.ui.widget.snaphelper;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easy.recycleview.bean.DyItemBean;
import com.linlsyf.area.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView rv;
    private List<DyItemBean> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);
        rv = (RecyclerView) findViewById(R.id.rv);

             // getIntent().getse

        mDataList = new ArrayList<>();


        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(new CardRvAdapter(this, mDataList));
//        LinearSnapHelper mLinearSnapHelper = new LinearSnapHelper();
//        mLinearSnapHelper.attachToRecyclerView(rv);
        CustomSnapHelper mMySnapHelper = new CustomSnapHelper();
        mMySnapHelper.attachToRecyclerView(rv);
    }
}
