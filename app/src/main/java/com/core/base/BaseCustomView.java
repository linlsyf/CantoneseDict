package com.core.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/***
 * 
 * <br>创建者：ldh
 * <br>修改时间：2015年5月26日 下午6:20:33 
 * <br>注释：tab_my的自定义view的父类
 */
public class BaseCustomView extends LinearLayout{
	protected Context mContext;
/**父控件*/
	protected View  rootView;
	
	public BaseCustomView(Context context) {
		super(context,null);
//		this.BaseCustomView(context,null);
	}
	

	public BaseCustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.setOrientation(VERTICAL);
		initUI(context);
	}

	/**快速获得view*/
	@SuppressWarnings("unchecked")
	public final <E extends View> E getViewById(int id) {
		
		if(rootView != null){
			return (E) rootView.findViewById(id);
		}
		return null;
	}
	/**
	 * 创建者：ldh
	 * 时间：2015年6月1日 上午10:45:55
	 * 注释：初始化界面
	 * @param context
	 */
	public void initUI(Context context) {

	}
	public void initData() {

	}

	public void initListener(OnClickListener listener) {
		
	}
}
