package com.ui.dict.yueping;


import android.content.Context;
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
import com.easy.recycleview.inter.IDyItemBean;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.search.SearchHeadView;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.iflytek.IatDemoUtils;
import com.linlsyf.cantonese.R;
import com.ui.common.infoedit.InformationInputFragment;
import com.ui.dict.DictTypeEnum;
import com.ui.dict.search.SearchDictFragment;
import com.ui.dict.search.sentenceyy.SearchSentenceFragment;
import com.ui.dict.view.CentImgView;

import java.util.List;

import static com.ui.dict.search.SearchDictFragment.DICT_NAME;

/**
 * 搜索资源文件
 */

public class DictYuePinyFragment extends BaseFragment implements IDictYuePiny {
    DictYuePinyresenter persenter;
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

        recycleView.initCustomViewCallBack(new DyLayout.CustomViewCallBack() {
            @Override
            public View getCustomView(Context context, int type) {
                View  itemView =null;
             if (type==4){
                    itemView=new CentImgView(context);
                }
//
                return itemView;

            }
        });
//        });
        toolbar=getViewById(R.id.toolbar);
        searchHeadView=getViewById(R.id.searchView);
        searchHeadView.setVisibility(View.GONE);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), getString(R.string.learn_base_yp), 0);

        searchHeadView.getBackLayout().setVisibility(View.GONE);

//        searchHeadView.getSearchEditText().setFocusable(true);
//        searchHeadView.getSearchEditText().setFocusableInTouchMode(true);
//        searchHeadView.getSearchEditText().requestFocus();
        persenter=new DictYuePinyresenter(this);

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
