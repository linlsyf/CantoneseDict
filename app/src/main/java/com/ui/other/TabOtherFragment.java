package com.ui.other;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.core.base.BaseFragment;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.area.R;
import com.ui.other.tuling.TulingFragemnt;
import com.ui.other.view.OtherContentView;

//import butterknife.ButterKnife;


public class TabOtherFragment extends BaseFragment implements IOtherView{
    OtherPresenter presenter;
    OtherContentView recycleView;
    protected Context mContext;


    private RelativeLayout mNativeSpotAdLayout;
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

    	View rootView=inflater.inflate(R.layout.fragment_tab_other, null);
    	setRootView(rootView);
       return rootView;

    }

    @Override
    public void initFragment() {
      initUIView();
       initData();
    }

    @Override
    public void initUIView() {
        recycleView= getViewById(R.id.recycleView);

//        ButterKnife.bind(getActivity());
        NavigationBar toolbar=getViewById(R.id.toolbar);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "资讯", 0);
    }

    @Override
    public void initData() {
        presenter=new OtherPresenter(this);
        presenter.init();
        mContext=getActivity();

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void updateSection(Section section) {
        recycleView.updateSection(section);

    }

    @Override
    public void updateItem(DyItemBean imgBean) {

    }


    @Override
    public void showNews() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle=new Bundle();
                FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TulingFragemnt(), bundle);

            }
        });
    }

    @Override
    public void showApay() {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(getActivity(),PayDemoActivity.class));
//
//            }
//        });
    }

    @Override
    public void loadDataStart() {

    }
}

