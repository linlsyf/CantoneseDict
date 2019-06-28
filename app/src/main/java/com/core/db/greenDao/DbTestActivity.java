package com.core.db.greenDao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.core.CoreApplication;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.entity.User;
import com.core.db.greenDao.gen.UserDao;
import com.linlsyf.area.R;

import java.util.List;
import java.util.Random;

public class DbTestActivity extends Activity implements View.OnClickListener {

    private Button mAdd,mDelete,mUpdate,mFind;
    private TextView mContext;
    private User mUser;
    private UserDao mUserDao;
    Random random;
    long id=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);
        initView();
        initEvent();
        mUserDao = GlobalConstants.getInstance().getDaoSession().getUserDao();
         random=new Random();

    }

    private void initEvent() {
        mAdd.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mFind.setOnClickListener(this);
    }

    private void initView() {
        mContext = (TextView) findViewById(R.id.textView);
        mAdd = (Button) findViewById(R.id.button);
        mDelete = (Button) findViewById(R.id.button2);
        mUpdate = (Button) findViewById(R.id.button3);
        mFind = (Button) findViewById(R.id.button4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                addDate();
                break;
            case R.id.button2:
                deleteDate();
                break;
            case R.id.button3:
                updateDate();
                break;
            case R.id.button4:
                findDate();
                break;
        }
    }

    /**
     * 增加数据
     */
    private void addDate() {
        id=random.nextLong();
        mUser = new User(id,"anye3");
        mUserDao.insert(mUser);//添加一个
        mContext.setText(mUser.getName());
    }

    /**
     * 删除数据
     */
    private void deleteDate() {
        deleteUserById(id);
    }

    /**
     * 根据主键删除User
     *
     * @param id User的主键Id
     */
    public void deleteUserById(long id) {
        mUserDao.deleteByKey(id);
    }

    /**
     * 更改数据
     */
    private void updateDate() {
        mUser = new User(id,"anye0803");
        mUserDao.update(mUser);
    }

    /**
     * 查找数据
     */
    private void findDate() {
        List<User> users = mUserDao.loadAll();
        String userName = "";
        for (int i = 0; i < users.size(); i++) {
            userName += users.get(i).getName()+",";
        }
        mContext.setText("查询全部数据==>"+userName);
    }
}
