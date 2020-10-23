package cn.onekit.weixin.core.wx;

import java.util.Map;

import cn.onekit.js.core.function;

public class WxTabBar extends WxSystem {

    public void setTabBarBadge(Map OBJECT) {
        int index = OBJECT.get("index") != null ? (int) OBJECT.get("index") : 0;
        String text = OBJECT.get("text") != null ? (String) OBJECT.get("text") : null;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

    }

    public void removeTabBarBadge(Map OBJECT) {
        int index = OBJECT.get("index") != null ? (int) OBJECT.get("index") : 0;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

    }

    public void showTabBarRedDot(Map OBJECT) {
        int index = OBJECT.get("index") != null ? (int) OBJECT.get("index") : 0;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

    }

    public void hideTabBarRedDot(Map OBJECT) {
        int index = OBJECT.get("index") != null ? (int) OBJECT.get("index") : 0;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

    }

    public void setTabBarStyle(Map OBJECT) {
        String color = OBJECT.get("color") != null ? (String) OBJECT.get("color") : null;
        String selectedColor = OBJECT.get("selectedColor") != null ? (String) OBJECT.get("selectedColor") : null;
        String backgroundColor = OBJECT.get("backgroundColor") != null ? (String) OBJECT.get("backgroundColor") : null;
        String borderStyle = OBJECT.get("borderStyle") != null ? (String) OBJECT.get("borderStyle") : null; // tabbar上边框的颜色， 仅支持 black/white
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;


    }

    public void setTabBarItem(Map OBJECT) {
        int index = OBJECT.get("index") != null ? (int) OBJECT.get("index") : 0;
        String text = OBJECT.get("text") != null ? (String) OBJECT.get("text") : null;
        String iconPath = OBJECT.get("iconPath") != null ? (String) OBJECT.get("iconPath") : null;
        String selectedIconPath = OBJECT.get("selectedIconPath") != null ? (String) OBJECT.get("selectedIconPath") : null;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

    }

    public void showTabBar(Map OBJECT) {
        boolean aniamtion = OBJECT.get("aniamtion") != null ? (boolean) OBJECT.get("aniamtion") : null;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

    }

    public void hideTabBar(Map OBJECT) {
        boolean aniamtion = OBJECT.get("aniamtion") != null ? (boolean) OBJECT.get("aniamtion") : null;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;

    }
}
