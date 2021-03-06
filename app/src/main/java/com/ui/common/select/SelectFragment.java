package com.ui.common.select;

import android.os.Bundle;
import android.view.ViewGroup;

import com.core.base.BaseFragment;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.bean.Section;
import com.easy.recycleview.custom.baseview.EdittextLayoutView;
import com.easy.recycleview.inter.IDyItemBean;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.cantonese.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 输入信息界面
 */
public class SelectFragment extends BaseFragment implements ISelectView {

   
    EdittextLayoutView mEt;

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";
    public static final String TYPE_SELECT = "TYPE_SELECT";

    /**标题*/
    private String mTitle;
    /**输入框内容*/
    private String mContent;

    private String mId;

	private NavigationBar toolbar;

    public OnUpdateSuccessListener listener;
    private DyLayout recycleView;
    public static final String KEY_INTNET_CLOSE="KEY_INTNET_CLOSE";


    SelectPresenter inputPresenter;
    private List<DyItemBean> itemBeanList =new ArrayList<>();
    private String viewType="TYPE_EDIT";

    private boolean isClose=true;

    public void setOnUpdateSuccessListener(OnUpdateSuccessListener listener) {
        this.listener = listener;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    @Override
    public void loadDataStart() {

    }


    public interface OnUpdateSuccessListener{
        void updateSuccess(List<IDyItemBean> itemBeans);
    }

   @Override
    public int getLayoutResId() {

        return R.layout.fragment_common;
    }
            @Override
              public void initFragment() {
            	
	            initUIView();
	            initData();
	            initListener();
          }

                 @Override
              public void initUIView() {
                     recycleView = getViewById(R.id.dyLayout);
            	 toolbar=getViewById(R.id.toolbar);
                     Bundle bundle=   getArguments();

                     TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.LEFT_FIRST, "返回", 0);

      		   TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "选择", 0);
//      		   TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.RIGHT_FIRST, "确定", 0);
                     inputPresenter=new SelectPresenter(this);
                     inputPresenter.setViewType(viewType);

                     inputPresenter.init(itemBeanList);
    }

    @Override
	public void initListener() {
    	toolbar.setNavigationBarListener(new NavigationBarListener() {
      
			@Override
			public void onClick(ViewGroup containView, NavigationBar.Location location) {
				  switch (location){
                  case LEFT_FIRST:
                	  KeyboardUtils.closeKeybord(getActivity());
                      FragmentHelper.popBackFragment(getActivity());
                      break;
                  case RIGHT_FIRST:
                      List<IDyItemBean>  itemBeans=recycleView.getSectionAdapterHelper().getDataArrayList();

                	  listener.updateSuccess( itemBeans);
                	  KeyboardUtils.closeKeybord(getActivity());
                      FragmentHelper.popBackFragment(getActivity());
//                      getIPresenter().saveInfo(mEt.getText(),mId);
                      break;
              }
				
			}
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        mTitle = bundle.getString(TITLE);
        mContent = bundle.getString(CONTENT);
        mId = bundle.getString(ID);
        isClose=bundle.getBoolean(KEY_INTNET_CLOSE,true);

    }
        public void initDataMap(List<DyItemBean> dataList){
        this.itemBeanList =dataList;
        }

    @Override
    public void initUI(Section section) {
            recycleView.updateSection(section);

    }

    @Override
    public void select(List<IDyItemBean> selectList) {

        listener.updateSuccess(selectList);
        if (isClose){

            FragmentHelper.popBackFragment(activity);
        }


    }

    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
//        if(type.equals(BusinessBroadcastUtils.TYPE_CHANGE_THEME_RESTART_ACTIVITY)) {
//            Handler handler = new Handler();
//
//            handler.postDelayed(new Runnable() {
//
//                @Override
//
//                public void run() {
//                    inputPresenter.refresh();
//
//                }
//
//            }, 500);
//
//
//        }
		
	}



}
