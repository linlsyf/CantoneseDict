package com.core.base;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.core.utils.BackHandledInterface;
import com.easy.recycleview.DyLayout;
import com.easy.recycleview.bean.Section;
import com.easysoft.utils.lib.system.ToastUtils;

import java.net.MalformedURLException;


public abstract class BaseFragment extends Fragment implements BaseUiInterface,IBaseView{
	
	private View rootView =null;
	public int tagId =0;
	public boolean isshowdialog = true;
    protected FragmentActivity activity;
	private boolean mHaveLoadData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		activity=getActivity();
		this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏  
		super.onCreate(savedInstanceState);
		
	    registerBoradcastReceiver();

		this.mBackHandledInterface = (BackHandledInterface)activity;
	    //Fragment的 onCreate（）基本上不做其他事，必须重写initFragment()
	}
	//2
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	    super.onCreateView(inflater, container, savedInstanceState);
	    View view = getRootView();
	    if(view==null){
	    	if(getLayoutResId()!=0)
	    	{
	    		view = inflater.inflate(getLayoutResId(), container, false);	
	    	}
	    }
	    setRootView(view);
	    return view;
	}
    //3
    @Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 拦截触摸事件防止泄露下去
				return true;
			}
		});
		super.onViewCreated(view, savedInstanceState);
		
		initFragment();
	}
    
    //4
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//初始化之后，重绘展现UI
		this.rootView.invalidate();
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		// 如果还没有加载过数据 && 用户切换到了这个fragment
		// 那就开始加载数据
		if (!mHaveLoadData && isVisibleToUser) {
			loadDataStart();
			mHaveLoadData = true;
		}
	}
    
	/**必须实现这个函数*/
	public void initFragment(){};
	
	@Override
	public void initUIView() throws MalformedURLException {
		
	}
	
	@Override
	public void initData() {
		
	}
	
	@Override
	public void initListener() {
		
		
	}
	protected BackHandledInterface mBackHandledInterface;

	/**
	 * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
	 * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
	 * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
	 */
	public  abstract boolean onBackPressed();
	@Override
	public void onStart() {
		super.onStart();
		//告诉FragmentActivity，当前Fragment在栈顶
		mBackHandledInterface.setSelectedFragment(this);
	}

	/**获取的就是静态布局view，如需重绘，需要在initUI中重写*/
	protected View getRootView() {return rootView;}
	protected void setRootView(View rootView) {this.rootView = rootView;}
	
	/**使用布局id的方式添加视图*/
	public int getLayoutResId() {return 0;}
	
	int getTagId() {
		return tagId;
	}
	protected void setTagId(int tagId) {
		this.tagId = tagId;
	}
	
	int getFragmentId(){
		return this.getId();
	}
	
	/**注释：简化view 获取，不用强制转换*/
	@SuppressWarnings("unchecked")
	public final <E extends View> E getViewById(int id) {
		View v = getRootView();
		if(v != null){
			return (E) v.findViewById(id);
		}
		return null;
	}

	public   void  updateSection(final DyLayout recycleView, final Section section ){


		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				recycleView.updateSection(section);
			}
		});
	}

	public int getLayoutResId(String layoutName) {
		if(layoutName == null){
			return 0;
		}
		return this.getResources().getIdentifier(layoutName, "layout",activity.getPackageName());
	}



	@Override
	public void onDetach() {
		super.onDetach();
		rootView =null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	

	boolean isregisterBoradcastReceiver = false; 
	/**
	 * 消息监听的广播
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(GlobalConstants.getInstance().getBroadCastReceiverActionName())){
				
				String type = intent.getStringExtra(MSG_TYPE);
				Object mode = intent.getSerializableExtra(MSG_MODE);
				
				if(type!=null){getBroadcastReceiverMessage(type,mode);}
				//DebugUtil.setLog(BROAD_CAST_RECEIVER_ACTION_NAME, "收到广播消息");
			}
		}
	};
	/**
	 * 创建者：zw
	 * 时间：2015-5-12 下午2:36:17
	 * 注释：注册广播 ,用于消息监听
	 */
	private void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(GlobalConstants.getInstance().getBroadCastReceiverActionName());

        activity.registerReceiver(mBroadcastReceiver, myIntentFilter); 
        isregisterBoradcastReceiver = true;
        //DebugUtil.setLog(TAG, "注册广播");
    } 
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if(isregisterBoradcastReceiver){
			activity.unregisterReceiver(mBroadcastReceiver);
			//DebugUtil.setLog(TAG, "注销广播");
			isregisterBoradcastReceiver = false;
		}
	}
	@Override
	public Context getContext() {
		return activity;
	}

	@Override
	public void showToast(final int id) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastUtils.show(activity,activity.getResources().getString(id));

			}
		});
	}
	@Override
	public void showToast(final String text) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastUtils.show(activity,text);

			}
		});
	}
}
