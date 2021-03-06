package com.ui.dict;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.business.BusinessBroadcastUtils;
import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.core.db.greenDao.entity.Dict;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.widget.config.WidgetConfig;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.search.SearchHeadView;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.cantonese.R;
import com.ui.common.custom.CustomFragment;
import com.ui.dict.search.SearchDictFragment;
import com.ui.dict.search.sentenceyy.SearchSentenceFragment;
import com.ui.dict.start.DictStudyFragment;
import com.ui.dict.translate.TranslateFragment;
import com.ui.dict.view.DictMainView;
import com.ui.dict.yueping.DictYuePinyFragment;
import com.utils.ThemeHelper;

import java.util.List;

import static com.ui.dict.search.SearchDictFragment.DICT_NAME;

/**
 * 搜索资源文件
 */

public class DictHomeFragment extends BaseFragment implements IdictHomeView {
    DictHomePresenter persenter;
    DyLayout recycleView;
    private NavigationBar toolbar;
    SearchHeadView searchHeadView;
    View mRootLayout;
    DictMainView mainView;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mainView= new DictMainView(activity);
        setRootView(mainView);
       return mainView;

    }
    @Override
    public void initFragment() {
       initUIView();
        initData();
       initListener();
    }
    @Override
    public void initUIView() {
        mRootLayout = getViewById(R.id.rootLayout);
        toolbar=getViewById(R.id.toolbar);

    }

    @Override
    public void initData() {

        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), getString(R.string.dict), 0);

        TopBarBuilder.buildOnlyImageById(toolbar,getActivity(), NavigationBar.Location.RIGHT_FIRST, ThemeHelper.getStoreThemeIcon(activity));
        persenter=new DictHomePresenter(this);
        persenter.initAssets();
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

                     BusinessBroadcastUtils.sendBroadcast(activity,BusinessBroadcastUtils.TYPE_CHANGE_THEME_WB,null);
                }
            }
        });
        mainView.tvBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           	BusinessBroadcastUtils.sendBroadcast(activity,BusinessBroadcastUtils.Type_Local_HOME_PAGE_CHANGE,1);
            }
        });
        mainView.startLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//             boolean isInit=   persenter.initAssets();
//             if (isInit){
                startStudy();
//             }

            }
        });
        mainView.translateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTranslate();
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
        if(type.equals(BusinessBroadcastUtils.TYPE_YY_REFRESH_HOME_COUNT)){
//            persenter.initData();
        }
        else if(type.equals(BusinessBroadcastUtils.TYPE_CHANGE_THEME)){
            mRootLayout.setBackgroundColor(WidgetConfig.getInstance().getBgColor());
            toolbar.resetConfig();
//            persenter.initData();
        }
        else if(type.equals(BusinessBroadcastUtils.TYPE_RELOGIN)){
        }

	}
    public void openCustomView(List<DyItemBean> dataList) {
        CustomFragment inputFragment=new CustomFragment();

        Bundle bundle=new Bundle();

        inputFragment.initDataMap(dataList);

        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);
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
//        initData();

    }


    @Override
    public void openVideo(VideoBussinessItem itemBean) {

    }

    @Override
    public void startStudy() {
      Bundle bundle=new Bundle();
//       bundle.putInt(FragmentHelper.AnimationsType,FragmentTransactionExtended.CUBE);
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new DictStudyFragment(), bundle);

    }

    @Override
    public void showAllLearn() {


        Bundle bundle=new Bundle();
        bundle.putInt(SearchDictFragment.DICT_TYPE,DictTypeEnum.MARK.value());

        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new SearchDictFragment(), bundle);
    }

    @Override
    public void toTranslate() {
        Bundle bundle=new Bundle();
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TranslateFragment(), bundle);

    }

    @Override
    public void toYuePing() {
        Bundle bundle=new Bundle();
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new DictYuePinyFragment(), bundle);

    }


}
