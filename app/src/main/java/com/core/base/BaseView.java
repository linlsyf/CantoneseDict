package com.core.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public abstract class BaseView extends View {

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
