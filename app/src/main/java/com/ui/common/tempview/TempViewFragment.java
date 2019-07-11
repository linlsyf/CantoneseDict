package com.ui.common.tempview;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.linlsyf.area.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 输入信息界面
 */
public class TempViewFragment extends BaseFragment implements ITempView {


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
    public static final String KEY_INTNET_LIST="KEY_INTNET_LIST";
    TempViewPresenter inputPresenter;
    private List<DyItemBean> itemBeanList =new ArrayList<>();
    private String viewType="TYPE_EDIT";
    private RelativeLayout contentLayout;

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
                     contentLayout=getViewById(R.id.contentLayout);

            	 toolbar=getViewById(R.id.toolbar);
                     Bundle bundle=   getArguments();

                     TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.LEFT_FIRST, "返回", 0);

      		   TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), getString(R.string.common_title), 0);
                     inputPresenter=new TempViewPresenter(this);
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

    }
        public void initDataMap(List<DyItemBean> dataList){
        this.itemBeanList =dataList;
        }

    @Override
    public void initUI(Section section) {



           // recycleView.updateSection(section);

    }

    @Override
    public void select(List<IDyItemBean> selectList) {

        listener.updateSuccess(selectList);
        FragmentHelper.popBackFragment(activity);


    }

    @Override
    public void initAddView(List<IDyItemBean> selectList) {



        IDyItemBean bean= selectList.get(0);
        TextView itemTextView=new TextView(activity);
        itemTextView.setText(bean.getTitle());
        contentLayout.addView( itemTextView);

    }
    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		// TODO Auto-generated method stub
		
	}



}
