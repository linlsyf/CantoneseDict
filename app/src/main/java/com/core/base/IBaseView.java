package com.core.base;

import android.content.Context;

public interface IBaseView {
    Context getContext();
    void loadDataStart();
    void showToast(String text);
}
