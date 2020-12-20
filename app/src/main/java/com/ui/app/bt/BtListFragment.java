package com.ui.app.bt;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.Section;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.area.R;
import com.utils.OpenFileUtils;

//import com.xunlei.downloadlib.XLTaskHelper;
//import com.xunlei.downloadlib.parameter.XLTaskInfo;


public class BtListFragment extends BaseFragment implements IBTTVView {
    BtListPresenter persenter;
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
        persenter=new BtListPresenter(this);
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
//                    InformationInputFragment inputFragment=new InformationInputFragment();
//                    inputFragment.setOnUpdateSuccessListener(new InformationInputFragment.OnUpdateSuccessListener() {
//                        @Override
//                        public void updateSuccess(String mContent, String id) {
////                            mContent="magnet:?xt=urn:btih:JXWTFCQ6CSNEQVO3AXBHVNQCLV3WWU7Y";
//                            persenter.add(mContent);
//                        }
//                    });
//                    Bundle  bundle=new Bundle();
//                    bundle.putString("title","下载列表");
//
//                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);


                }

            }
        });
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<SelectBean> selectBeanList= recycleView.getSectionAdapterHelper().getSelect(TVPresenter.KEY_SETTING);
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
    public void startDownload(final  long taskId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handler.sendMessage(handler.obtainMessage(0,taskId));

            }
        });
    }
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                long taskId = (long) msg.obj;
//                XLTaskInfo taskInfo = XLTaskHelper.instance(CoreApplication.getAppContext()).getTaskInfo(taskId);
//
//                String hint=convertFileSize(taskInfo.mFileSize)
//                                + "/:" +convertFileSize(taskInfo.mDownloadSize)
//                                + " " + convertFileSize(taskInfo.mDownloadSpeed)
//                                + "/s dcdnSpeed:" + convertFileSize(taskInfo.mAdditionalResDCDNSpeed);
//                DyItemBean itembean= recycleView.getSectionAdapterHelper().getItem(BtListPresenter.KEY_SETTING,taskId+"");
//                  itembean.setHint(hint);
//                recycleView.getSectionAdapterHelper().updateItem(BtListPresenter.KEY_SETTING,itembean);
//
//                handler.sendMessageDelayed(handler.obtainMessage(0, taskId), 1000);
            }
        }
    };


    @Override
    public void loadDataStart() {

    }
}
