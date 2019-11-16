package com.ui.common.filereader;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.business.BusinessBroadcastUtils;
import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.core.db.greenDao.entity.Dict;
import com.easy.recycleview.ContentItemView;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.baseview.config.HeadImageViewConfig;
import com.easy.recycleview.custom.baseview.config.HintTextViewConfig;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.linlsyf.area.R;
import com.tencent.smtt.sdk.TbsReaderView;
import com.ui.common.custom.CustomFragment;
import com.ui.dict.DictTypeEnum;
import com.ui.dict.search.SearchDictFragment;
import com.ui.dict.search.sentenceyy.SearchSentenceFragment;
import com.ui.dict.yueping.DictYuePinyFragment;

import java.util.List;

import static com.ui.dict.search.SearchDictFragment.DICT_NAME;


public class TbsReaderFragment extends BaseFragment implements ITbsReaderView, TbsReaderView.ReaderCallback {
    public static String FILE_PATH="path";
    DyLayout recycleView;
	  TbsReaderPresenter presenter;
    NavigationBar toolbar;
    View mRootLayout;

    TbsReaderView mTbsReaderView;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
    	View rootView=inflater.inflate(R.layout.fragment_common, null);
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

         RelativeLayout contentLayout=getViewById(R.id.contentLayout);


          mTbsReaderView = new TbsReaderView(activity, this);
          //rl_tbsView为装载TbsReaderView的视图
          contentLayout.addView(mTbsReaderView, new RelativeLayout.LayoutParams(
                  ViewGroup.LayoutParams.MATCH_PARENT,
                  ViewGroup.LayoutParams.MATCH_PARENT));
      }
      @Override
    public void initData() {
//          TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "设置", 0);
//          toolbar.resetConfig();
//          mRootLayout.setBackgroundColor(WidgetConfig.getInstance().getBgColor());
      presenter=new TbsReaderPresenter(this);


       Bundle  bundle=getArguments();
         String path=bundle.getString(FILE_PATH);



          Bundle localBundle = new Bundle();
          localBundle.putString("filePath",path);
          localBundle.putString("tempPath", Environment.getExternalStorageDirectory() + "/" + "TbsReaderTemp");


           String type=parseFormat(path);

        boolean result=  mTbsReaderView.preOpen(type,false);

          if (result) {
              mTbsReaderView.openFile(localBundle);
          }
//      presenter.init();
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
    }

    @Override
    public void showType(final VideoBussinessItem item, final int type) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle=new Bundle();
                bundle.putInt(SearchDictFragment.DICT_TYPE,type);
                if (type== DictTypeEnum.SEARCH.value()){
                    if (null!=item){
                        if (null!=item.getBindObject()){
                            Dict dict=(Dict) item.getBindObject();
                            bundle.putString(DICT_NAME,dict.getTranName());
                        }
                    }
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new SearchSentenceFragment(), bundle);

                }else if(type==DictTypeEnum.LJ.value()){
                    if (null!=item){
                        if (null!=item.getBindObject()){
                            Dict dict=(Dict) item.getBindObject();
                            bundle.putString(DICT_NAME,dict.getName());
                        }
                    }
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new SearchSentenceFragment(), bundle);
                }else{

                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new SearchDictFragment(), bundle);
                }
            }
        });
    }
    @Override
    public void updateUIItem(final boolean isPlaying, final DyItemBean imgBean) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View  itemView= recycleView.getItemView(imgBean);
                if(itemView!=null){
                    ContentItemView contentItemView=(ContentItemView) itemView;
                    contentItemView.mTitleTextView.setText(imgBean.getTitle());
                    HintTextViewConfig.load(contentItemView,imgBean);
                    if (!isPlaying){
                        HeadImageViewConfig.load((DyItemBean) imgBean,contentItemView.mHeadImageView);
                    }
                }
            }
        });
    }
    @Override
    public void showAllLearn() {
        Bundle bundle=new Bundle();
        bundle.putInt(SearchDictFragment.DICT_TYPE,DictTypeEnum.MARK.value());
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new SearchDictFragment(), bundle);
    }
    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
        if (type.equals(BusinessBroadcastUtils.TYPE_CHANGE_THEME)) {
            toolbar.resetConfig();
        }
    }
	@Override
	public void initUI(Section section) {
		recycleView.updateSection(section);
		
	}

    @Override
    public void onDetach() {
        super.onDetach();
        //用完回调要注销掉，否则可能会出现内存泄露
//        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void loadDataStart() {
//        initUIView();
//        initData();
    }

    @Override
    public void showToast(String text) {
    ToastUtils.show(getActivity(),text);
    }

    @Override
    public void test() {
        Bundle bundle=new Bundle();
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TbsReaderFragment(), bundle);
    }
    @Override
    public void toYuePing() {
        Bundle bundle=new Bundle();
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new DictYuePinyFragment(), bundle);

    }
    @Override
    public void openCustomView(List<DyItemBean> dataList) {
        CustomFragment inputFragment=new CustomFragment();
        Bundle bundle=new Bundle();
        inputFragment.initDataMap(dataList);
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
}

