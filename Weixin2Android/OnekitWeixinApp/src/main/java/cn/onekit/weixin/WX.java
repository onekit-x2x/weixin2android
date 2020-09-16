package cn.onekit.weixin;

import android.app.Activity;
import android.util.Log;

import thekit.android.Android;
import cn.onekit.weixin.app.Map;
import cn.onekit.weixin.app.core.map.tencent.MapContext_Tencent2D;
import cn.onekit.weixin.app.core.map.tencent.MapContext_Tencent3D;
import cn.onekit.weixin.core.wx.WxRoute;
import cn.onekit.weixin.core.wx.WxWXML;

public class WX extends WxWXML {
    public MapContext createMapContext(String mapId) {
        Activity activity = (Activity) Android.context;
        Map map = activity.findViewById(activity.getResources().getIdentifier(mapId, "id", activity.getPackageName()));
        return createMapContext(map);
    }
    public MapContext createMapContext(Map map) {
        Log.d("************************", "createMapContext: "+map.getEnable3D());
        if (map.getEnable3D()) {
            return new MapContext_Tencent3D(map);
        } else {
            return new MapContext_Tencent2D(map);
        }
    }

    public AudioContext createAudioContext(String audioId) {
        return null;
    }
}
