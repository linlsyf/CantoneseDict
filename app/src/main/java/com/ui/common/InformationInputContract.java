package com.ui.common;



public interface InformationInputContract {

    interface IInformationInputView {

        /**
         * 是否保存信息成功
         * @param isSucceed
         */
        void saveSucceed(boolean isSucceed);

        /**
         * 保存失败信息
         * @param msg
         */
        void saveError(String msg);
    }

    interface IInformationInputPresenter extends IBasePresenter{

        /**保存信息*/
        void saveInfo(String string, String id);
    }

  
}
