package com.ui.common.gallery;

import android.os.Bundle;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFragment;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.SwipOnRefreshListener;
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
import com.ui.widget.snaphelper.CardRvAdapter;
import com.ui.widget.snaphelper.CustomSnapHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 输入信息界面
 */
public class GalleryFragment extends BaseFragment implements ICustomView {


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
    public  OnRightClickCallBack onRightClickCallBack;
    private DyLayout recycleView;
    public static final String KEY_INTNET_LIST="KEY_INTNET_LIST";
    CustomPresenter inputPresenter;
    private List<DyItemBean> itemBeanList =new ArrayList<>();
    private String viewType="TYPE_EDIT";
    private RelativeLayout contentLayout;
    private Section mSection;
    public boolean mIsDestory;
    SwipOnRefreshListener onRefreshListener;
    private boolean hasInit;
    private RecyclerView rv;

    public void setOnRefreshListener(SwipOnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

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
    public interface OnRightClickCallBack{
        void call(Map data);
    }

   @Override
    public int getLayoutResId() {
        return R.layout.fragment_gallery;
    }
            @Override
              public void initFragment() {
	            initUIView();
	            initData();
	            initListener();
                hasInit=true;
          }

                 @Override
              public void initUIView() {
                     rv = getViewById(R.id.rv);
 rv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                     rv.setAdapter(new CardRvAdapter(activity,  itemBeanList));

                     CustomSnapHelper mMySnapHelper = new CustomSnapHelper();
                     mMySnapHelper.attachToRecyclerView(rv);

    }

    @Override
	public void initListener() {

    }

    @Override
    public boolean onBackPressed() {

        mIsDestory=true;
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
         mSection=section;
            recycleView.updateSection(section);

    }

    @Override
    public void select(List<IDyItemBean> selectList) {

        listener.updateSuccess(selectList);
        FragmentHelper.popBackFragment(activity);


    }


    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		// TODO Auto-generated method stub
		
	}



}
