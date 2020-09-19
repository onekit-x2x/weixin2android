package cn.onekit.weixin.core.wx;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.Dict;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxNavigationBar extends WxMonitor {
    private ActionBar actionBar;
    private ImageView imageView;
    private View actionbarLayout;
    private TextView textview;

    private void init() {
        actionBar = ((Activity)Android.context).getActionBar();
        actionbarLayout = LayoutInflater.from(Android.context).inflate(R.layout.onekit_actionbar, null);
        imageView = (ImageView) actionbarLayout.findViewById(R.id.right_imbt);
    }

    public void setNavigationBarTitle(Map OBJECT) {
        String title = OBJECT.get("title") != null ? (String) OBJECT.get("title") : "WeChat";
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            init();
            actionBar.setDisplayShowHomeEnabled(false);// 给左上角图标的左边加上一个返回的图标 设成false，则没有程序图标
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            textview = (TextView) actionbarLayout.findViewById(R.id.text);
            textview.setText(title);
            imageView.setVisibility(View.GONE);
            actionBar.setCustomView(actionbarLayout);
            Dict res = new Dict();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_setNavigationBarTitle_success);// "setNavigationBarTitle : ok";
//            res.title = Android.context.getClass().getSimpleName();
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_setNavigationBarTitle_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_setNavigationBarTitle_fail);// "setNavigationBarTitle : fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public void showNavigationBarLoading(Map OBJECT) {
        if (actionBar == null)
            init();
        actionBar.setDisplayShowHomeEnabled(false);// 给左上角图标的左边加上一个返回的图标 设成false，则没有程序图标
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        imageView.setImageResource(R.drawable.animation1);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
        actionBar.setCustomView(actionbarLayout);
    }

    public void hideNavigationBarLoading(Map OBJECT) {
        new myTask().execute();
    }

    public void setNavigationBarColor(Map OBJECT) {
        String frontColor = OBJECT.get("frontColor") != null ? (String) OBJECT.get("frontColor") : null;
        String backgroundColor = OBJECT.get("backgroundColor") != null ? (String) OBJECT.get("backgroundColor") : null;
        JsObject animation = OBJECT.get("backgroundColor") != null ? (JsObject) OBJECT.get("animation") : null;
        int animationDuration = OBJECT.get("animation.duration") != null ? (int) OBJECT.get("animation.duration") : 0;
        String animationTimingFunc = OBJECT.get("animation.timingFunc") != null ? (String) OBJECT.get("animation.timingFunc") : null;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        if (actionBar == null)
            return;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(((String) OBJECT.get("backgroundColor")))));
        textview.setTextColor(Color.parseColor(frontColor));
        imageView.setColorFilter(Color.parseColor(frontColor));
    }

    class myTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            imageView.setVisibility(View.GONE);
        }
    }
}

