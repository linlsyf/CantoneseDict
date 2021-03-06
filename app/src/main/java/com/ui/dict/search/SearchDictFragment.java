package com.ui.dict.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.core.db.greenDao.entity.Dict;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.Section;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.search.SearchHeadView;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.iflytek.IatDemoUtils;
import com.linlsyf.cantonese.R;
import com.ui.dict.DictTypeEnum;
import com.ui.dict.IdictView;
import com.ui.dict.search.sentenceyy.SearchSentenceFragment;

/**
 * 搜索资源文件
 */

public class SearchDictFragment extends BaseFragment implements IdictView {
    public static String DICT_TYPE="DICT_TYPE";
    public static String DICT_NAME="DICT_NAME";
    SearchDictPresenter persenter;
    DyLayout recycleView;
    private NavigationBar toolbar;
    SearchHeadView searchHeadView;
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
        persenter=new SearchDictPresenter(this);
        recycleView = getViewById(R.id.dyLayout);
        toolbar=getViewById(R.id.toolbar);
        searchHeadView=getViewById(R.id.searchView);

        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), getString(R.string.dict), 0);


        searchHeadView.getBackLayout().setVisibility(View.GONE);


        searchHeadView.getSearchEditText().setFocusable(true);

        searchHeadView.getSearchEditText().setFocusableInTouchMode(true);

        searchHeadView.getSearchEditText().requestFocus();
       int type=  getArguments().getInt(DICT_TYPE);
       persenter.setType(type);
        if (getArguments().containsKey(DICT_NAME)){
            persenter.setSearchKey(getArguments().getString(DICT_NAME));

        }
        persenter.initData();

    }
    @Override
    public void initListener() {
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.LEFT_FIRST) {
                  FragmentHelper.popBackFragment(activity);

                }

            }
        });
//        searchHeadView.getSearchEditText().setDelayMillis(1900L);
        searchHeadView.setOnTextChangerListener(new SearchHeadView.onTextChangerListener() {
            @Override
            public void onTextChanger(String text) {
                if(StringUtils.isEmpty(text)){
                    ToastUtils.show(activity,"请输入关键字");
                }else{
                    persenter.searchByGY( text);
                }
            }

            @Override
            public void onVoiceClick() {
                IatDemoUtils utils=new IatDemoUtils();
                utils.setcallBack(new IatDemoUtils.mcCallBack() {
                    @Override
                    public void call(String msg) {
                        persenter.searchByGY(msg);
                    }
                });
                utils.init(activity);
                utils.onClick();
            }
        });

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {

//         if(type.equals(BusinessBroadcastUtils.TYPE_REFRESH_VIDEO)){
//            persenter.init();
//        }

	}

    @Override
    public void initUI(final Section nextSection) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                KeyboardUtils.closeKeybord(activity);
                recycleView.updateSection(nextSection);
            }
        });
    }
    @Override
    public void showType(VideoBussinessItem item, int type) {
        Bundle bundle=new Bundle();
        bundle.putInt(SearchDictFragment.DICT_TYPE,type);
        if(type== DictTypeEnum.LJ.value()){
            if (null!=item){
                if (null!=item.getBindObject()){
                    Dict dict=(Dict) item.getBindObject();
                    bundle.putString(DICT_NAME,dict.getName());
                }

            }
            FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new SearchSentenceFragment(), bundle);

        }

    }
//
//    @Override
//    public void showItem(final VideoBussinessItem imgBean) {
//        getActivity().runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                if (imgBean.getId().equals(DictTypePresenter.ID_NEWS)){
////                    Bundle bundle=new Bundle();
////                    bundle.putSerializable("messageEntity", entity);
//                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new NewsFragment(), null);
//
//                }
//
//                else if(imgBean.getId().equals(DictTypePresenter.ID_BTDOWNLOAD)){
//                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new VideosFragment(), null);
//
//                }
//               else if(imgBean.getId().equals(DictTypePresenter.ID_TV)){
//                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TVFragment(), null);
//
//                }
//               else if(imgBean.getId().equals(DictTypePresenter.ID_SEARCH)){
//                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TVFragment(), null);
//
//                }
//                else{
//                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new VideoHideListFragment(), null);
//
//                }
//
//            }
//        });
//    }

    @Override
    public void loadDataStart() {

    }


    @Override
    public void openVideo(VideoBussinessItem itemBean) {

    }

    @Override
    public void editType() {

    }


//    @Override
//    public void addLayoutHelper(LayoutHelper helper,boolean isRefresh) {
//        recycleView.addLayoutHelper(helper,isRefresh);
//    }
}
