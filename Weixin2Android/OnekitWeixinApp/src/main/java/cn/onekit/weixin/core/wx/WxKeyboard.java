package cn.onekit.weixin.core.wx;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import cn.onekit.Android;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsString;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxKeyboard extends WxInvoice {
    private EditText edt;
    private boolean isHide = false;
    private InputMethodManager imm;
    private MyGlobalLayoutListener listener;
    private MyTextChangedListener textChangedListener;
    private MyEditorActionListener editorActionListener;

    public void hideKeyboard(Map object) {
        function success = object.get("success") != null ? (function) object.get("success") : null;
        function fail = object.get("fail") != null ? (function) object.get("fail") : null;
        function complete = object.get("complete") != null ? (function) object.get("complete") : null;
        try {
            if (edt == null) {
                edt = ((Activity)Android.context).findViewById(R.id.edt_keyboard);
            }
            imm = (InputMethodManager) ((Activity)Android.context).getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromInputMethod(edt.getWindowToken(), 0);
            Dict res = new Dict();
//            res.errMsg = ((Activity)Android.context).getResources().getString(R.string.wx_hideKeyboard_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }

        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_hideKeyboard_fail));
//            res.errMsg = ((Activity)Android.context).getResources().getString(R.string.wx_hideKeyboard_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void onKeyboardInput(final function callback) {
        if (edt == null) {
            edt = ((Activity)Android.context).findViewById(R.id.edt_keyboard);
        }
        textChangedListener = new MyTextChangedListener(callback);
        edt.addTextChangedListener(textChangedListener);
    }

    public void offKeyboardInput(function callback) {
        if (edt == null) {
            edt = ((Activity)Android.context).findViewById(R.id.edt_keyboard);
        }
        if (textChangedListener != null) {
            edt.removeTextChangedListener(textChangedListener);
        }
    }

    public void onKeyboardConfirm(final function callback) {
        if (edt == null) {
            edt = ((Activity)Android.context).findViewById(R.id.edt_keyboard);
        }
        editorActionListener = new MyEditorActionListener(callback);
        edt.setOnEditorActionListener(editorActionListener);
    }

    public void offKeyboardConfirm(function callback) {
        if (edt == null) {
            edt = ((Activity)Android.context).findViewById(R.id.edt_keyboard);
        }
        if (editorActionListener != null) {
            editorActionListener = null;
            edt.setOnEditorActionListener(editorActionListener);
        }
    }

    public void onKeyboardComplete(final function callback) {
        if (edt == null) {
            edt = ((Activity)Android.context).findViewById(R.id.edt_keyboard);
        }
        final View rootView = edt.getRootView();
        listener = new MyGlobalLayoutListener(rootView, callback);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    public void offKeyboardComplete(function callback) {
        if (edt == null) {
            edt = ((Activity)Android.context).findViewById(R.id.edt_keyboard);
        }
        if (listener != null) {
            edt.getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    public void showKeyboard(Map object) {
        String defaultValue = object.get("defaultValue") != null ? (String) object.get("defaultValue") : null;
        int maxLength = object.get("maxLength") != null ? (int) object.get("maxLength") : 0;
        boolean multiple = object.get("multiple") != null ? (boolean) object.get("multiple") : false;
        boolean confirmHold = object.get("confirmHold") != null ? (boolean) object.get("confirmHold") : false;
        String confirmType = object.get("confirmType") != null ? (String) object.get("confirmType") : null;
        function success = (object.get("success") != null) ? (function) object.get("success") : null;
        function fail = (object.get("fail") != null) ? (function) object.get("fail") : null;
        function complete = (object.get("complete") != null) ? (function) object.get("complete") : null;
        try {
            ((Activity)Android.context).setContentView(R.layout.onekit_keyboard);
            imm = (InputMethodManager) ((Activity)Android.context).getSystemService(Context.INPUT_METHOD_SERVICE);
            edt = ((Activity)Android.context).findViewById(R.id.edt_keyboard);
            edt.setText(defaultValue);
            edt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
            if (!multiple) {
                edt.setSingleLine();
            }
            isHide = confirmHold;
            setConfirmType(confirmType, edt);
            imm.showSoftInput(edt, 0);
            Dict res = new Dict();
//            res.errMsg = ((Activity)Android.context).getResources().getString(R.string.wx_showKeyboard_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_showKeyboard_fail));
//            res.errMsg = ((Activity)Android.context).getResources().getString(R.string.wx_showKeyboard_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    private void setConfirmType(String confirmType, EditText view) {
        switch (confirmType) {
            case "done":
                view.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
            case "next":
                view.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                break;
            case "search":
                view.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                break;
            case "go":
                view.setImeOptions(EditorInfo.IME_ACTION_GO);
                break;
            case "send":
                view.setImeOptions(EditorInfo.IME_ACTION_SEND);
                break;
        }
    }

    class MyEditorActionListener implements TextView.OnEditorActionListener {
        private function callback;

        public MyEditorActionListener(function callback) {
            this.callback = callback;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE && isHide) {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
            Dict object = new Dict();
            object.put("value", new JsString((String) v.getText()));
            callback.invoke(object);
            return true;
        }
    }

    class MyTextChangedListener implements TextWatcher {
        private function callback;

        public MyTextChangedListener(function callback) {
            this.callback = callback;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Dict object = new Dict();
            object.put("value", new JsString(s.toString()));
            callback.invoke(object);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class MyGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private View view;
        private function callback;

        public MyGlobalLayoutListener(View view, function callback) {
            this.view = view;
            this.callback = callback;
        }

        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            view.getWindowVisibleDisplayFrame(r);
            int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
            if (heightDiff < 100) {
                Dict object = new Dict();
                object.put("value", new JsString(edt.getText().toString()));
                callback.invoke(object);
            }
        }
    }
}

