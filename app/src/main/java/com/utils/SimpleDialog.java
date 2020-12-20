package com.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easysoft.widget.utils.ResourcesUtil;
import com.linlsyf.area.R;


public class SimpleDialog {

    private static int DIVIDER_HEIGHT;

    private static int ITEM_PADDING;

    private static int ITEM_TEXTSIZE;

    private static int DIVIDER_COLOR;

    private Context mContext;

    public static Dialog mDialog;

    private LinearLayout mRootView;

    private View.OnClickListener mListener;

    private boolean mDismissOnClick;

    private SimpleDialog(Context context) {
        mContext = context;
        mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = new LinearLayout(context);
        mRootView.setBackgroundColor(Color.WHITE);
        mRootView.setOrientation(LinearLayout.VERTICAL);
        mDialog.setContentView(mRootView);

        initFields();
    }

    private void initFields() {
//        if (DIVIDER_HEIGHT == 0) {
//            synchronized (SimpleDialog.class) {
//                if (DIVIDER_HEIGHT == 0) {
//                    DIVIDER_HEIGHT = (int) (0.5 + ResourcesUtil.getResourcesDimen(mContext, R.dimen.dividingline_width));
//                    ITEM_PADDING = (int) (0.5 + ResourcesUtil.getResourcesDimen(mContext, R.dimen.basedialog_item_padding));
//                    ITEM_TEXTSIZE = (int) (0.5 + ResourcesUtil.getResourcesDimen(mContext, R.dimen.basedialog_item_textsize));
//                    DIVIDER_COLOR = ResourcesUtil.getResourcesColor(mContext, R.color.spacing_line_color);
//                }
//            }
//        }
    }

    public static SimpleDialog init(Context context) {
        return new SimpleDialog(context);
    }

    public SimpleDialog setCancelable(boolean cancelable) {
        mDialog.setCancelable(cancelable);
        return this;
    }

    public SimpleDialog setCancelOnTouchOutSide(boolean cancelOnTouchOutSide) {
        mDialog.setCanceledOnTouchOutside(cancelOnTouchOutSide);
        return this;
    }

    public SimpleDialog setShowOnIM(boolean showOnIM) {
        if (showOnIM) {
            mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        } else {
            mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }
        return this;
    }

    public SimpleDialog addItem(String item, Object tag, int textsize, int color) {
        TextView tv = new TextView(mContext);
        tv.setBackgroundResource(R.drawable.selector_basedialog_item_bg);
        tv.setPadding(ITEM_PADDING, ITEM_PADDING, ITEM_PADDING ,ITEM_PADDING);
        if (textsize > 0) {
            tv.setTextSize(textsize);
        } else {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, ITEM_TEXTSIZE);
        }
        if (color != 0) {
            tv.setTextColor(color);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        tv.setText(item);
        tv.setTag(tag);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(v);
                }
                if (mDismissOnClick) {
                    dismiss();
                }
            }
        });
        int count = mRootView.getChildCount();
        if (count > 0) {
            View view = new View(mContext);
            view.setBackgroundColor(DIVIDER_COLOR);
            mRootView.addView(view, new LinearLayout.LayoutParams(-1, DIVIDER_HEIGHT));
        }
        mRootView.addView(tv, new LinearLayout.LayoutParams(-1, -1));
        return this;
    }

    public SimpleDialog setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
        return this;
    }

    public SimpleDialog setDismissOnClick(boolean dismissOnClick) {
        mDismissOnClick = dismissOnClick;
        return this;
    }

    public void show() {
        mDialog.show();
    }

    public static void dismiss() {
        mDialog.dismiss();
    }
}
