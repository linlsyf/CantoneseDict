package com.ui.dict.start;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.business.BusinessBroadcastUtils;
import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.core.db.greenDao.entity.Dict;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.inter.IDyItemBean;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.search.SearchHeadView;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.iflytek.IatDemoUtils;
import com.linlsyf.cantonese.R;
import com.ui.common.infoedit.InformationInputFragment;
import com.ui.common.select.SelectFragment;
import com.ui.common.tts.TtsHelper;
import com.ui.dict.DictTypeEnum;
import com.ui.dict.search.SearchDictFragment;
import com.ui.dict.search.sentenceyy.SearchSentenceFragment;

import java.util.List;

import static com.ui.dict.search.SearchDictFragment.DICT_NAME;

/**
 * 搜索资源文件
 */

public class DictStudyFragment extends BaseFragment implements IStartView {
    DictStudyPresenter persenter;
    DyLayout recycleView;
    private NavigationBar toolbar;
    SearchHeadView searchHeadView;
    private WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_common, null);
        setRootView(rootView);
       return rootView;

    }
    @Override
    public void initFragment() {
       // initData();
       initUIView();
       initListener();
    }
    @Override
    public void initUIView() {
        recycleView = getViewById(R.id.dyLayout);
        toolbar=getViewById(R.id.toolbar);
        searchHeadView=getViewById(R.id.searchView);
        webview=getViewById(R.id.webview);
        searchHeadView.setVisibility(View.VISIBLE);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), getString(R.string.dict), 0);
        TopBarBuilder.buildOnlyText(toolbar,getActivity(), NavigationBar.Location.RIGHT_FIRST,getString(R.string.change),0);
        searchHeadView.getBackLayout().setVisibility(View.GONE);

        searchHeadView.getSearchEditText().setFocusable(true);
        searchHeadView.getSearchEditText().setFocusableInTouchMode(true);
        searchHeadView.getSearchEditText().requestFocus();

        if (persenter==null){
            persenter=new DictStudyPresenter(this);
        }

        persenter.initData();
        TtsHelper.initWeb(webview);

//        WebSettings webSettings = webview.getSettings();
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setSupportZoom(true);
//        //与js交互必须设置
//        webSettings.setJavaScriptEnabled(true);
//        webview.loadUrl("file:///android_asset/cantonsprak.html");
////        webview.loadUrl("file:///android_asset/html.html");
////        webview.addJavascriptInterface(this,"android");
//        webview.addJavascriptInterface(this,"android");

    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initListener() {

        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.LEFT_FIRST) {
                  FragmentHelper.popBackFragment(activity);

                }   else if (location== NavigationBar.Location.RIGHT_FIRST) {


                    SelectFragment inputFragment=new SelectFragment();

                    inputFragment.setOnUpdateSuccessListener(new SelectFragment.OnUpdateSuccessListener() {
                        @Override
                        public void updateSuccess( List<IDyItemBean> itemBeans) {
                     persenter.chaneReadCount(itemBeans);
                        }
                    });


                    List<DyItemBean> dataList=persenter.getReadListCount();


                    Bundle bundle=new Bundle();

                    inputFragment.initDataMap(dataList);

                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);

                }

            }
        });
//        searchHeadView.getSearchEditText().setDelayMillis(1900L);
        searchHeadView.setOnTextChangerListener(new SearchHeadView.onTextChangerListener() {
            @Override
            public void onTextChanger(String text) {
                if(StringUtils.isEmpty(text)){
//                    ToastUtils.show(activity,"请输入关键字");
                    persenter.initData();
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
        BusinessBroadcastUtils.sendBroadcast(activity,BusinessBroadcastUtils.TYPE_YY_REFRESH_HOME_COUNT,null);
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
                recycleView.updateSection(nextSection);
    }

    @Override
    public void showType(final  VideoBussinessItem item,final int type) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Bundle bundle=new Bundle();
                bundle.putInt(SearchDictFragment.DICT_TYPE,type);


                if (type==DictTypeEnum.SEARCH.value()){
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
    public void loadDataStart() {

    }

    @Override
    public void openVideo(VideoBussinessItem itemBean) {

    }

    @Override
    public void editType(List<DyItemBean> dataList) {
        InformationInputFragment inputFragment=new InformationInputFragment();
        inputFragment.setOnUpdateSuccessListener(new InformationInputFragment.OnUpdateSuccessListener() {
            @Override
            public void updateSuccess( List<IDyItemBean>  itemBeans) {
                persenter.updateMsg(itemBeans);
            }
        });

        Bundle bundle=new Bundle();

        inputFragment.initDataMap(dataList);

        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);

    }

    @Override
    public void speak(String textYuey) {

        TtsHelper.speak(webview,textYuey);

    }

}
