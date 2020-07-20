package cn.onekit.weixin.app.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.HashMap;

import cn.onekit.ASSET;
import cn.onekit.TheKit;
import cn.onekit.weixin.app.core.page.TabsActivity;

import static cn.onekit.TheKit.createUUID;
import static cn.onekit.TheKit.querystring2extras;
import static cn.onekit.TheKit.url2class;

public class Onekit_Weixin_App {
    static {
        TheKit.tabsActivityClass = TabsActivity.class;
    }
    public static Intent initIntent(Context context, String url, int channelID) throws ClassNotFoundException {

        String[] array = url.split("\\?");
        String uri = array[0];
        //
        String fullname = url2class(context, uri);
        Class clazz = Class.forName(fullname);
        Intent intent = new Intent(context, clazz);
        Bundle extras;
        if (array.length > 1) {
            String querystring = array[1];
            extras=querystring2extras(querystring);
        }else{
            extras=new Bundle();
        }
        extras.putInt("onekit_channelID",channelID);
        intent.putExtras(extras);
        return intent;
    }
}