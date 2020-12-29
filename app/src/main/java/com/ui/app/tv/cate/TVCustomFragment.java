package com.ui.app.tv.cate;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.cantonese.R;
import com.ui.app.tv.ITVView;
import com.ui.common.infoedit.InformationInputFragment;
import com.utils.OpenFileUtils;

import java.util.ArrayList;
import java.util.List;


public class TVCustomFragment extends BaseFragment implements ITVView {
    TVCustomPresenter persenter;
    DyLayout recycleView;
    private NavigationBar toolbar;
//    LinearLayout editLayout;
    TextView  addTv;
    TextView  selectAllTv;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_common, null);
        setRootView(rootView);
        return rootView;

    }
    @Override
    public void initFragment() {
        initUIView();
        initListener();
    }
    @Override
    public void initUIView() {
        persenter=new TVCustomPresenter(this);
        recycleView = getViewById(R.id.dyLayout);

        toolbar=getViewById(R.id.toolbar);
//        editLayout=getViewById(R.id.editLayout);
        addTv=getViewById(R.id.add);
//        selectAllTv=getViewById(R.id.selectAll);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "本地视频", 0);
        TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.RIGHT_FIRST, "添加", 0);
//        addTv.setText(R.string.hide);
        persenter.init(true);
    }
    @Override
    public void initListener() {
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.RIGHT_FIRST) {
                    InformationInputFragment  inputFragment=new InformationInputFragment();
                    List<DyItemBean> dataList=new ArrayList<>();
                    DyItemBean dyItemBean=new DyItemBean();
                     dyItemBean.setId("content");
                     dyItemBean.setTitle("链接：");
                     dyItemBean.getEidtSettings().setShowEdittext(true);
                    dataList.add(dyItemBean);
                    inputFragment.initDataMap(dataList);
                    inputFragment.setOnUpdateSuccessListener(new InformationInputFragment.OnUpdateSuccessListener() {
                        @Override
                        public void updateSuccess( List<IDyItemBean>  itemBeans) {
//                            String mContent=    data.get("content");
//                            persenter.add(mContent);
                        }
                    });
                     Bundle  bundle=new Bundle();
                     bundle.putString("title","添加播放源");

                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);


                }

            }
        });
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<SelectBean>  selectBeanList= recycleView.getSectionAdapterHelper().getSelect(TVPresenter.KEY_SETTING);
//                persenter.setHide(selectBeanList);

            }
        });
        selectAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isShow=  persenter.setCanEdit();
                if (isShow){
//                    editLayout.setVisibility(View.VISIBLE);
                    addTv.setVisibility(View.VISIBLE);
                    selectAllTv.setVisibility(View.VISIBLE);
                }else{
//                    editLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {

    }

    @Override
    public void initUI(final Section nextSection) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                recycleView.updateSection(nextSection);
            }
        });

    }

    @Override
    public void showItem(VideoBussinessItem itemBean) {

    }

    @Override
    public void openVideoBySelf(final VideoBussinessItem itemBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                OpenFileUtils.openVideoBySelf(getActivity(), itemBean);
            }
        });
    }

    @Override
    public void openVideo(final VideoBussinessItem itemBean) {



        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                OpenFileUtils.openVideo(getActivity(), itemBean.getData());
            }
        });
    }


    @Override
    public void loadDataStart() {

    }
}
