package com;


import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;

import com.linlsyf.cantonese.R;

public class KeyboardUtils {

    private KeyboardView keyboardView;
    private com.MyImeService myImeService;
    private Keyboard k1;// 字母键盘

    public KeyboardUtils(com.MyImeService myImeService1, KeyboardView keyboardView1) {
        super();
        keyboardView = keyboardView1;
        keyboardView.setOnKeyboardActionListener(listener);
        myImeService = myImeService1;
        k1 = new Keyboard(myImeService.getApplicationContext(), R.xml.qwerty);
        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(true);
    }

    private OnKeyboardActionListener listener = new OnKeyboardActionListener() {

        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            InputConnection ic = myImeService.getCurrentInputConnection();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
//				myImeService.deleteText();
                    ic.deleteSurroundingText(1,0);
                    break;
                case Keyboard.KEYCODE_CANCEL:
//				myImeService.hideInputMethod();
                    break;
                case Keyboard.KEYCODE_DONE:
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_NUMPAD_ENTER));
                    break;
                default:
//				myImeService.commitText(Character.toString((char) primaryCode));
                    ic.commitText(String.valueOf((char)primaryCode),1);
                    break;
            }
        }
    };

}
