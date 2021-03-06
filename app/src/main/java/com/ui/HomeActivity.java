package com.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.business.BusinessBroadcastUtils;
import com.core.base.BaseFragment;
import com.core.base.BasicActivity;
import com.core.base.GlobalConstants;
import com.core.utils.BackHandledInterface;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.tabview.adapter.BaseAdapter;
import com.easysoft.widget.tabview.adapter.MainViewAdapter;
import com.easysoft.widget.tabview.widget.TabContainerView;
import com.linlsyf.cantonese.R;
import com.ui.catalog.CatalogFragment;
import com.ui.dict.DictHomeFragment;
import com.ui.login.IlogInView;
import com.ui.login.LoginActivity;
import com.ui.login.LoginPresenter;
import com.ui.setting.SettingFragment;
import com.utils.NaviHightUtils;
import com.utils.ThemeHelper;


public class HomeActivity extends BasicActivity implements IlogInView,IHomeView , BackHandledInterface {
	TabContainerView tabContainerView;
	LoginPresenter loginPresenter;
	 long mExitTime=0;
	 BaseFragment mBackHandedFragment;
	 boolean hadIntercept;
	public static final int REQUEST_READ_STOR_PERMISSION = 10111; //拨号请求码
	@Override
    protected void onCreate( Bundle savedInstanceState) {
		setTheme(ThemeHelper.getStoreTheme(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
//      getWindow().getDecorView().post(new Runnable() {
//            @Override
//            public void run() {
				tabContainerView = (TabContainerView) findViewById(R.id.tab_container);

				WellComeHelper  wellComeHelper=new WellComeHelper();
				wellComeHelper.init(HomeActivity.this);

				MainViewAdapter mainViewAdapter=new MainViewAdapter(getSupportFragmentManager(),
						new Fragment[] {new DictHomeFragment(),new CatalogFragment(),new SettingFragment()});

				mainViewAdapter.setIconImageArray(new int[] {R.drawable.home_black,R.drawable.tab_catalog_white,  R.drawable.setting_black});
				mainViewAdapter.setSelectedIconImageArray(new int[] {R.drawable.home_blue,R.drawable.tab_catalog_blue, R.drawable.setting_blue});
				mainViewAdapter.setTabNameArray(new String[] {"首页","分类","设置"});
				mainViewAdapter.setHasMsgIndex(0);
				tabContainerView.setAdapter(mainViewAdapter);

		NaviHightUtils.setHight( this);


		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , 1);

		if(checkReadPermission(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_READ_STOR_PERMISSION)){

		}
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_SETTINGS} , 1);

		if(checkReadPermission(Manifest.permission.WRITE_SETTINGS, REQUEST_READ_STOR_PERMISSION)){

		}



	}


	public boolean checkReadPermission(String string_permission,int request_code) {
		boolean flag = false;
		if (ContextCompat.checkSelfPermission(this, string_permission) ==  PackageManager.PERMISSION_GRANTED) {//已有权限
			flag = true;
		} else {//申请权限
			ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
		}
		return flag;
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
		switch (requestCode) {
			case REQUEST_READ_STOR_PERMISSION: //拨打电话
				if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
					 ToastUtils.show(this,"请求权限失败");
				} else {//成功
					// call("tel:"+"10086");
					int i=0;
				}
				break;
		}
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
		BusinessBroadcastUtils.sendBroadcast(this,BusinessBroadcastUtils.TYPE_APP_EXIT,null);

		}
	}

	@Override
	protected void onResume() {
		super.onResume();


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
	public void loadDataStart() {

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
