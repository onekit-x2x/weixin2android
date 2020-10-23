package cn.onekit.weixin.app.core.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.onekit.core.OneKit;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.Onekit_Weixin;


public class LaunchActivity extends Activity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onekit_activity_launch);
        try {
            JSONObject APP_JSON = Onekit_Weixin.APP_JSON;
            if (APP_JSON.has("tabBar")) {
                startActivity(new Intent(this,TabsActivity.class));
                finish();
                return;
            }
            JSONArray list = APP_JSON.getJSONArray("pages");
            String page = (String) list.get(0);
//            Class clazz = Class.forName(String.format("%s.onekit_%s",getPackageName(), OneKit.url2activity(this,page)));
            Class clazz = Class.forName(OneKit.url2class(this,page));
            startActivity(new Intent(this,clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
