package cn.onekit.weixin.app.core.page;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.TabHost;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.onekit.thekit.ASSET;
import cn.onekit.core.OneKit;
import cn.onekit.js.core.Onekit_JS;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.app.core.Onekit_Weixin_App;
import cn.onekit.weixin.core.Onekit_Weixin;


public class TabsActivity extends TabActivity {
    @SuppressLint("StaticFieldLeak")
    public static TabActivity current;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneKit.currentUrl = "/";
        current = this;
        ///////////////
        try {
            setContentView(R.layout.onekit_activity_tabs);
            TabHost tabHost = this.getTabHost();
            JSONObject APP_JSON = Onekit_Weixin.APP_JSON;
            JSONObject tabBar = APP_JSON.getJSONObject("tabBar");
            JSONArray list = tabBar.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject item = list.getJSONObject(i);
                String pagePath = (String) item.get("pagePath");
                Intent page = Onekit_Weixin_App.initIntent(this, pagePath,0);
                String iconPath = (String) item.get("iconPath");
                Bitmap icon ;
                if (!Onekit_JS.isEmpty(iconPath)) {
                    icon = ASSET.loadImage("miniprogram/"+iconPath);
                } else {
                    icon = null;
                }
                BitmapDrawable bitmapDrawable = new BitmapDrawable(icon);
                TabHost.TabSpec tab = tabHost.newTabSpec(String.valueOf(i));
                tab.setIndicator((String) item.get("text"), bitmapDrawable);
                tab.setContent(page);
                tabHost.addTab(tab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
