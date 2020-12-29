package com.ui.dict.search.sentenceyy;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.Section;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.search.SearchHeadView;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.cantonese.R;



/**
 * 搜索资源文件
 */

public class SearchSentenceFragment extends BaseFragment implements ISearchSentenceView {
    public static String DICT_TYPE="DICT_TYPE";
    public static String DICT_NAME="DICT_NAME";
    SearchSentencePresenter persenter;
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
        persenter=new SearchSentencePresenter(this);
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
        searchHeadView.setVisibility(View.GONE);
//        searchHeadView.getSearchEditText().setDelayMillis(1900L);
//        searchHeadView.setOnTextChangerListener(new SearchHeadView.onTextChangerListener() {
//            @Override
//            public void onTextChanger(String text) {
//                if(StringUtils.isEmpty(text)){
//                    ToastUtils.show(activity,"请输入关键字");
//                }else{
//                    persenter.searchByGY( text);
//                }
//            }
//        });

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
    public void showItem(final VideoBussinessItem imgBean) {



    }

    @Override
    public void loadDataStart() {

    }





}
