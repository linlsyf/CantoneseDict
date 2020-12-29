package com.ui.app.bt;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.business.BusinessBroadcastUtils;
import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.linlsyf.cantonese.R;
import com.ui.video.IVideoHomeView;

import java.util.List;

//import com.turn.ttorrent.client.Client;
//import com.turn.ttorrent.client.SharedTorrent;


//import com.xunlei.downloadlib.XLTaskHelper;
//import com.xunlei.downloadlib.parameter.XLTaskInfo;


public class BtDownloadFragment extends BaseFragment implements IVideoHomeView {
    BtdownloadPresenter persenter;
    DyLayout recycleView;
    EditText inputUrl;
    Button btnDownload;
    TextView tvStatus;
    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/downloads/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_bt_download, null);
        setRootView(rootView);
        return rootView;

    }

    @Override
    public void initFragment() {
        initUIView();
        initListener();
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                long taskId = (long) msg.obj;
//                XLTaskInfo taskInfo = XLTaskHelper.instance(CoreApplication.getAppContext()).getTaskInfo(taskId);
//                tvStatus.setText(
//                        "fileSize:" + convertFileSize(taskInfo.mFileSize)
//                                + " downSize:" +convertFileSize(taskInfo.mDownloadSize)
//                                + " speed:" + convertFileSize(taskInfo.mDownloadSpeed)
//                                + "/s dcdnSpeed:" + convertFileSize(taskInfo.mAdditionalResDCDNSpeed)
//                                + "/s filePath:" +DOWNLOAD_PATH + XLTaskHelper.instance(CoreApplication.getAppContext()).getFileName(inputUrl.getText().toString())
//                );
                handler.sendMessageDelayed(handler.obtainMessage(0, taskId), 1000);
            }
        }
    };


    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f M" : "%.1f M", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f K" : "%.1f K", f);
        } else
            return String.format("%d B", size);
    }


    @Override
    public void initUIView() {
        persenter = new BtdownloadPresenter(this);
//        btnDownload = getViewById(R.id.btn_down);
//        tvStatus = getViewById(R.id.tv_status);
//        btnDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new MyTask().execute();
//            }
//        });

//        android:hint="thunder://QUFmdHA6Ly95Z2R5ODp5Z2R5OEB5ZzQ1LmR5ZHl0dC5uZXQ6ODExNi9bJUU5JTk4JUIzJUU1JTg1JTg5JUU3JTk0JUI1JUU1JUJEJUIxd3d3LnlnZHk4Lm5ldF0lRTUlQUYlQkIlRTUlQTQlQTIlRTclOTIlQjAlRTYlQjglQjglRTglQUUlQjAuSEQuNzIwcC4lRTUlOUIlQkQlRTglOEIlQjElRTUlOEYlOEMlRTglQUYlQUQlRTQlQjglQUQlRTUlQUQlOTcubWt2Wlo="
//        magnet:?xt=urn:btih:fa9205652ffb56fac125b499114c9f730e74f322


//        XLTaskHelper.init(CoreApplication.getAppContext());
//        inputUrl =getViewById(R.id.magEt);
//        btnDownload =getViewById(R.id.btn_down);
//        tvStatus = getViewById(R.id.tv_status);
//        btnDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!TextUtils.isEmpty(inputUrl.getText())) {
//                    long taskId = 0;
//                    try {
//                        taskId = XLTaskHelper.instance(CoreApplication.getAppContext()).addThunderTask(inputUrl.getText().toString(),DOWNLOAD_PATH,null);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    handler.sendMessage(handler.obtainMessage(0,taskId));
//                }
//            }
//        });
    }

    @Override
    public void initListener() {
//        toolbar.setNavigationBarListener(new NavigationBarListener() {
//
//            @Override
//            public void onClick(ViewGroup containView, NavigationBar.Location location) {
//                if (location == NavigationBar.Location.RIGHT_FIRST) {
//                    boolean isShow = persenter.setCanEdit();
//                    if (isShow) {
//                        editLayout.setVisibility(View.VISIBLE);
//                        addTv.setVisibility(View.VISIBLE);
//                        selectAllTv.setVisibility(View.VISIBLE);
//                    } else {
//                        editLayout.setVisibility(View.GONE);
//                    }
//
//
//                }
//
//            }
//        });
//        addTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<SelectBean>  selectBeanList= recycleView.getSectionAdapterHelper().getSelect(TvCatePresenter.KEY_SETTING);
//                persenter.setHide(selectBeanList);
//
//            }
//        });
//        selectAllTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isShow=  persenter.setCanEdit();
//                if (isShow){
//                    editLayout.setVisibility(View.VISIBLE);
//                    addTv.setVisibility(View.VISIBLE);
//                    selectAllTv.setVisibility(View.VISIBLE);
//                }else{
//                    editLayout.setVisibility(View.GONE);
//                }
//            }
//        });

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void loadDataStart() {

    }

    private class MyTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            startDownload();
            return null;
        }
    }

    private void startDownload() {


//
//        // TODO Auto-generated method stub
//        // First, instantiate the Client object.
//        Client client;
//        try {
//            client = new Client(
//                    // This is the interface the client will listen on (you might need something
//                    // else than localhost here).
//                    InetAddress.getLocalHost(),
//
//                    // Load the torrent from the torrent file and use the given
//                    // output directory. Partials downloads are automatically recovered.
//                    SharedTorrent.fromFile(
//                            new File(DOWNLOAD_PATH + "test.torrent"),
//                            new File(DOWNLOAD_PATH)));
//            // You can optionally set download/upload rate limits
//            // in kB/second. Setting a limit to 0.0 disables rate
//            // limits.
//            client.setMaxDownloadRate(50.0);
//            client.setMaxUploadRate(50.0);
//
//            // At this point, can you either call download() to download the torrent and
//            // stop immediately after...
//            client.download();
//
//            // Or call client.share(...) with a seed time in seconds:
//            // client.share(3600);
//            // Which would seed the torrent for an hour after the download is complete.
//
//            // Downloading and seeding is done in background threads.
//            // To wait for this process to finish, call:
//            client.waitForCompletion();
//            // At any time you can call client.stop() to interrupt the download.
//            client.addObserver(new Observer() {
//
//                @Override
//                public void update(Observable observable, Object arg) {
//                    // TODO Auto-generated method stub
//                    Client client = (Client) observable;
//                    final float progress = client.getTorrent().getCompletion();
//                    // Do something with progress.
////                    System.out.println("progress="+progress);
//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tvStatus.setText((int) progress);
//                        }
//                    });
//                }
//            });
//        } catch (NoSuchAlgorithmException | IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }





	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {

         if(type.equals(BusinessBroadcastUtils.TYPE_REFRESH_VIDEO)){
           persenter.init();
        }
//        if(type.equals(BusinessBroadcastUtils.TYPE_LOGIN_FAILS)){
//
//            noticeTv.setVisibility(View.VISIBLE);
//        }
//        else if(type.equals(BusinessBroadcastUtils.TYPE_GOODS_ADD_SUCESS)){
//            persenter.list();
//        }
	}

    @Override
    public void initUI(final Section nextSection) {
//        getActivity().runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                recycleView.updateSection(nextSection,true);
//            }
//        });

    }

    @Override
    public void showItem(final VideoBussinessItem imgBean) {
//        getActivity().runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                if (imgBean.getId().equals(TvCatePresenter.ID_NEWS)){
////                    Bundle bundle=new Bundle();
////                    bundle.putSerializable("messageEntity", entity);
//                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new NewsFragment(), null);
//
//                }
//
//                else if(imgBean.getId().equals(TvCatePresenter.ID_BTDOWNLOAD)){
//
//                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TVDownloadFragment(), null);
//
//                }
//                else{
//                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new VideoHideListFragment(), null);
//
//                }
//
//            }
//        });
    }

    @Override
    public void openVideo(VideoBussinessItem itemBean) {

    }

    @Override
    public void showCustomView(List<DyItemBean> dataList) {

    }


//    @Override
//    public void addLayoutHelper(LayoutHelper helper,boolean isRefresh) {
//        recycleView.addLayoutHelper(helper,isRefresh);
//    }
}
