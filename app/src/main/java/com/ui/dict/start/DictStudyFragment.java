package com.ui.dict.start;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.business.BusinessBroadcastUtils;
import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.core.db.greenDao.entity.Dict;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.bean.DyItemBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easysoft.utils.lib.system.FragmentHelper;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.widget.search.SearchHeadView;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.area.R;
import com.ui.dict.DictTypeEnum;
import com.ui.dict.search.SearchDictFragment;
import com.ui.dict.search.sentenceyy.SearchSentenceFragment;
import com.ui.common.infoedit.InformationInputFragment;

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
        searchHeadView.setVisibility(View.VISIBLE);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), getString(R.string.dict), 0);
//        TopBarBuilder.buildOnlyText(toolbar,getActivity(), NavigationBar.Location.RIGHT_FIRST,getString(R.string.change),0);
        searchHeadView.getBackLayout().setVisibility(View.GONE);

        searchHeadView.getSearchEditText().setFocusable(true);
        searchHeadView.getSearchEditText().setFocusableInTouchMode(true);
        searchHeadView.getSearchEditText().requestFocus();
        persenter=new DictStudyPresenter(this);

        persenter.initData();
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


                    List<DyItemBean> dataList= persenter.getReadListCount();
                    InformationInputFragment inputFragment=new InformationInputFragment();
                    inputFragment.setViewType("TYPE_SELECT");
                    inputFragment.setOnUpdateSuccessListener(new InformationInputFragment.OnUpdateSuccessListener() {
                        @Override
                        public void updateSuccess( List<IDyItemBean>  itemBeans) {
                            persenter.updateReadConfig(itemBeans);
                        }
                    });

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
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                KeyboardUtils.closeKeybord(activity);
                recycleView.updateSection(nextSection,true);
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
    public void showToast(String text) {
        super.showToast(text);
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


//    @Override
//    public void addLayoutHelper(LayoutHelper helper,boolean isRefresh) {
//        recycleView.addLayoutHelper(helper,isRefresh);
//    }
}
