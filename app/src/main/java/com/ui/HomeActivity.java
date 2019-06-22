package com.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.business.BusinessBroadcastUtils;
import com.core.base.BaseFragment;
import com.core.base.BasicActivity;
import com.core.base.GlobalConstants;
import com.core.utils.BackHandledInterface;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.tabview.adapter.MainViewAdapter;
import com.easysoft.widget.tabview.listener.OnTabSelectedListener;
import com.easysoft.widget.tabview.widget.Tab;
import com.easysoft.widget.tabview.widget.TabContainerView;
import com.linlsyf.area.R;
import com.ui.dict.DictHomeFragment;
import com.ui.login.IlogInView;
import com.ui.login.LoginActivity;
import com.ui.login.LoginPresenter;
import com.ui.setting.SettingFragment;
import com.utils.ThemeHelper;


public class HomeActivity extends BasicActivity implements IlogInView,IHomeView , BackHandledInterface {
	TabContainerView tabContainerView;
	LoginPresenter loginPresenter;
	HomePresenter  homePresenter;
	 long mExitTime=0;
	 BaseFragment mBackHandedFragment;
	 boolean hadIntercept;

	@Override
    protected void onCreate( Bundle savedInstanceState) {
		setTheme(ThemeHelper.getStoreTheme(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
      getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {

				WellComeHelper  wellComeHelper=new WellComeHelper();
				wellComeHelper.init(HomeActivity.this);



				MainViewAdapter mainViewAdapter=new MainViewAdapter(getSupportFragmentManager(),
						new Fragment[] {new DictHomeFragment(),new SettingFragment()});

				mainViewAdapter.setIconImageArray(new int[] {R.drawable.home_black, R.drawable.setting_black});
				mainViewAdapter.setSelectedIconImageArray(new int[] {R.drawable.home_blue,R.drawable.setting_blue});
				mainViewAdapter.setTabNameArray(new String[] {"首页","设置"});


				mainViewAdapter.setHasMsgIndex(0);
				tabContainerView.setAdapter(mainViewAdapter);
				tabContainerView.setOnTabSelectedListener(new OnTabSelectedListener() {
					@Override
					public void onTabSelected(Tab tab) {

					}
				});

				homePresenter=new HomePresenter(HomeActivity.this);
				tabContainerView.resetConfig();

			}
        });

         tabContainerView = (TabContainerView) findViewById(R.id.tab_container);


	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int num = getSupportFragmentManager().getBackStackEntryCount();

		if (num==0&&keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


			exit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - mExitTime) > 2000) {
			ToastUtils.show(this, "再按一次退出"+getString(R.string.app_name));

			mExitTime = System.currentTimeMillis();
		} else {
			//MyConfig.clearSharePre(this, "users");
		BusinessBroadcastUtils.sendBroadcast(this,BusinessBroadcastUtils.TYPE_APP_EXIT,null);

		}
	}

	@Override
	protected void onResume() {
		super.onResume();

//		 if (CoreApplication.getInstance().isDubug){
////		      gotoMainOrloginUI(wellComeActivity);
//		 }else

		if (GlobalConstants.getInstance().getAppType()==GlobalConstants.TYPE_SHOP_APP){
			if(StringUtils.isEmpty(BusinessBroadcastUtils.USER_VALUE_LOGIN_ID)){
				Intent  homeIntent=new Intent(this,LoginActivity.class);
				startActivity(homeIntent);
			}else if (BusinessBroadcastUtils.loginUser==null){
				login();
			}
		}

	}
   private void login(){
	   loginPresenter=new LoginPresenter(this);
	   loginPresenter.login(BusinessBroadcastUtils.USER_VALUE_LOGIN_ID,BusinessBroadcastUtils.USER_VALUE_PWD);

   }
	@Override
	public void initUIView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loginSucess() {
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				BusinessBroadcastUtils.sendBroadcast(getContext(), BusinessBroadcastUtils.TYPE_RELOGIN_SUCESS, null);
//
//			}
//		});
	}

	@Override
	public void loginFails() {
//		BusinessBroadcastUtils.sendBroadcast(getContext(), BusinessBroadcastUtils.TYPE_RELOGIN_FAILS, null);

	}
	@Override
	public void setSelectedFragment(BaseFragment selectedFragment) {
		this.mBackHandedFragment = selectedFragment;
	}
	@Override
	public void onBackPressed() {
		if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){
			if(getSupportFragmentManager().getBackStackEntryCount() == 0){
				super.onBackPressed();
			}else{
				getSupportFragmentManager().popBackStack();
			}
		}
	}
	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		if(type.equals(BusinessBroadcastUtils.Type_Local_HOME_PAGE_CHANGE)){
			tabContainerView.getViewPager().setCurrentItem((int)mode);
		}
		else if(type.equals(BusinessBroadcastUtils.TYPE_RELOGIN)){


			login();
		}
		else if(type.equals(BusinessBroadcastUtils.TYPE_CHANGE_THEME)){


			 setTheme((int)mode);
			tabContainerView.resetConfig();
		}
		else if(type.equals(BusinessBroadcastUtils.TYPE_CHANGE_THEME_RESTART_ACTIVITY)){

			setTheme((int)mode);
//			ThemeUtils.switchThemeConfig(this,(int)mode);
			startActivity(new Intent(this, HomeActivity.class));
			finish();
			//覆盖activity动画效果

			overridePendingTransition(0, 0);

//		     onRestart();
		}

	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void showToast(final String text) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastUtils.show(HomeActivity.this,text);

			}
		});
	}
}
