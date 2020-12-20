package com.ui.other.tuling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.core.base.BaseFragment;
import com.easysoft.widget.fragment.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.area.R;
import com.ui.other.tuling.adapter.ChatMessageAdapter;
import com.ui.other.tuling.constant.TulingParams;
import com.ui.other.tuling.entity.MessageEntity;
import com.ui.other.tuling.util.IsNullOrEmpty;
import com.ui.other.tuling.util.KeyBoardUtil;
import com.ui.other.tuling.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

//import butterknife.Bind;
//import butterknife.ButterKnife;


public class TulingFragemnt extends BaseFragment {

//    @Bind(R.id.lv_message)
    ListView lvMessage;
//    @Bind(R.id.iv_send_msg)
    ImageView ivSendMsg;
//    @Bind(R.id.et_msg)
    EditText etMsg;


    private List<MessageEntity> msgList = new ArrayList<>();
    private ChatMessageAdapter msgAdapter;
    private NavigationBar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.activity_tuling, null);

        setRootView(rootView);
        return rootView;

    }

    @Override
    public void initFragment() {
//        ButterKnife.bind(getActivity());
        initView();
        initData();
        initListener();
        requestApiByRetrofit_RxJava("新闻");
    }

    public void initData() {
        if (msgList.size() == 0) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_LEFT, TimeUtil.getCurrentTimeMillis());
            entity.setText("你好！俺是图灵机器人！\n咱俩聊点什么呢？\n你有什么要问的么？");
            msgList.add(entity);
        }
        msgAdapter = new ChatMessageAdapter(getActivity(), msgList);
        lvMessage.setAdapter(msgAdapter);
        lvMessage.setSelection(msgAdapter.getCount());
    }

    private void initView() {
        lvMessage=getViewById(R.id.lv_message);
        toolbar=getViewById(R.id.toolbar);
        ivSendMsg=getViewById(R.id.iv_send_msg);
        etMsg=getViewById(R.id.et_msg);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "小Q", 0);
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.LEFT_FIRST) {
                    FragmentHelper.popBackFragment(getActivity());
                }

            }
        });
    }

    public void initListener() {

         ivSendMsg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 sendMessage();
             }
         });

        lvMessage.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                KeyBoardUtil.hideKeyboard(getActivity());
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {

    }



    // 给Turing发送问题
    public void sendMessage() {
        String msg = etMsg.getText().toString().trim();

        if (!IsNullOrEmpty.isEmpty(msg)) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_RIGHT, TimeUtil.getCurrentTimeMillis(), msg);
            msgList.add(entity);
            msgAdapter.notifyDataSetChanged();
            etMsg.setText("");


            requestApiByRetrofit_RxJava(msg);
        }
    }


    // 请求图灵API接口，获得问答信息
    private void requestApiByRetrofit_RxJava(final String info) {
//        HttpService 		service=new HttpService();
//        final String url = TulingParams.TULING_URL+"?key="+TulingParams.TULING_KEY+"&info="+info;
////        url= ServerUrl.getFinalUrl(url,json);
//
//        service.request( url , new EasyHttpCallback(new IEasyResponse() {
//            @Override
//            public void onFailure(CallBackResult serviceCallBack) {
//            }
//
//            @Override
//            public void onResponse(CallBackResult serviceCallBack) {
////                if (serviceCallBack.isSucess()){
////                    ResponseMsgData data =JSON.parseObject(serviceCallBack.getResponseMsg().getData().toString(),
////                            ResponseMsgData.class);
////                    List<NewsEntity> orderList =  JSON.parseArray(data.getList().toString(), NewsEntity.class) ;
////
////                    final MessageEntity  messageEntity=new MessageEntity();
////                    messageEntity.setCode(data.getCode());
////
////                    messageEntity.setText(info);
////                    messageEntity.setList(orderList);
////                   getActivity().runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            handleResponseMessage(messageEntity);
////
////                        }
////                    });
////                }
//
//            }
//        }).setOutside(true));
    }


    // 处理获得到的问答信息
    private void handleResponseMessage(MessageEntity entity) {
        if (entity == null) return;

        entity.setTime(TimeUtil.getCurrentTimeMillis());
        entity.setType(ChatMessageAdapter.TYPE_LEFT);

        switch (entity.getCode()) {
            case TulingParams.TulingCode.URL:
                entity.setText(entity.getText() + "，点击网址查看：" + entity.getUrl());
                break;
            case TulingParams.TulingCode.NEWS:
                entity.setText(entity.getText() + "，点击查看");
                break;
        }

        msgList.add(entity);
        msgAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadDataStart() {

    }
}
