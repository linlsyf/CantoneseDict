package com.ui.dict.translate;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.business.baidu.translate.SearchParam;
import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.core.db.greenDao.entity.Dict;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.Section;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.search.SearchHeadView;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.iflytek.IatDemoUtils;
import com.linlsyf.cantonese.R;
import com.ui.common.tts.TtsHelper;
import com.ui.dict.DictTypeEnum;
import com.ui.dict.search.SearchDictFragment;
import com.ui.dict.search.sentenceyy.SearchSentenceFragment;

import static com.ui.dict.search.SearchDictFragment.DICT_NAME;

/**
 * 搜索资源文件
 */

public class TranslateFragment extends BaseFragment implements ITranslateView {
    TranslatePresenter persenter;
    DyLayout recycleView;
    private NavigationBar toolbar;
    SearchHeadView searchHeadView;
    private int type;
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
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), getString(R.string.translate), 0);
        TopBarBuilder.buildOnlyText(toolbar,getActivity(), NavigationBar.Location.RIGHT_FIRST,getString(R.string.change),0);
        persenter=new TranslatePresenter(this);

        searchHeadView.setVisibility(View.VISIBLE);
        searchHeadView.getBackLayout().setVisibility(View.GONE);

        searchHeadView.getSearchEditText().setFocusable(true);
        searchHeadView.getSearchEditText().setFocusableInTouchMode(true);
        searchHeadView.getSearchEditText().requestFocus();
        persenter.search("不开心");
        webview=getViewById(R.id.webview);

        TtsHelper.initWeb(webview);
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

                }
                else if (location== NavigationBar.Location.RIGHT_FIRST) {
                    String srcType="yue";
                    String desType="zh";
                    String typeName="普通话-粤语";

                    if (type==0){
                        typeName="粤语-普通话";
                        type=1;
                    }else{
                        type=0;
                        srcType="zh";
                        desType="yue";
                    }

                    SearchParam  searchParam=new SearchParam();
                     searchParam.setType(type);
                     searchParam.setTypeName(typeName);
                     searchParam.setFrom(srcType);
                     searchParam.setTo(desType);

                  persenter.changeTranslateType(searchParam);

                }

            }
        });
        searchHeadView.setOnTextChangerListener(new SearchHeadView.onTextChangerListener() {
            @Override
            public void onTextChanger(String text) {
                persenter.search(text);

            }

            @Override
            public void onVoiceClick() {
                IatDemoUtils  utils=new IatDemoUtils();
                utils.setcallBack(new IatDemoUtils.mcCallBack() {
                    @Override
                    public void call(String msg) {
                        persenter.search(msg);
                    }
                });
                utils.init(activity);
                utils.onClick();
            }
        });
    }
    @Override
    public void speak(String textYuey) {

        TtsHelper.speak(webview,textYuey);

    }
    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {

//        if(type.equals(BusinessBroadcastUtils.TYPE_YY_REFRESH_HOME_COUNT)){
//        }else if(type.equals(BusinessBroadcastUtils.TYPE_RELOGIN)){
//
//        }

	}

    @Override
    public void initUI(final Section nextSection) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                KeyboardUtils.closeKeybord(activity);
                recycleView.updateSection(nextSection);
            }
        });

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
    public void loadDataStart() {

    }

    @Override
    public void showToast(String text) {
        super.showToast(text);
    }

    @Override
    public void openVideo(VideoBussinessItem itemBean) {

    }



}
