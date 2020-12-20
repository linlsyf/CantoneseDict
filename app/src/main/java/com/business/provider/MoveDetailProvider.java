package com.business.provider;

import com.business.provider.bean.Actress;
import com.business.provider.bean.Movie;
import com.business.provider.bean.MovieDetail;
import com.core.CoreApplication;
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.ui.HttpService;

import java.util.List;

/**
 * Created by Administrator on 2019/7/25 0025.
 */

public class MoveDetailProvider {


    public static void parseMovieDetails( Movie m,final pareseMoveDetailCallBack callBack  ) {
//        HttpService service=new HttpService();
//        service.request(m.getLink(),new IEasyResponse() {
//            @Override
//            public void onFailure(CallBackResult callBackResult) {
//                callBack.onFailure(CoreApplication.getAppContext().getString(R.string.exec_fail));
//            }
//
//            @Override
//            public void onResponse(CallBackResult callBackResult) {
//
//                   if (null!=callBackResult&&callBackResult.isSucess()){
//                       String html=callBackResult.getResponseMsg().getData().toString();
//                       MovieDetail detail= AVMOProvider.parseMoviesDetail(html);
//                       detail.actresses= AVMOProvider.parseActresses(html);
//                       if (callBack!=null){
//                           callBack.onResponse(detail);
//                       }
//                } else{
//                       callBack.onFailure(CoreApplication.getAppContext().getString(R.string.exec_fail));
//
//                   }
//
//
//
//            }
//        });
    }

    public interface  pareseMoveDetailCallBack{
        void  onResponse(MovieDetail detail);
         void  onFailure(String msg);

    }
}
