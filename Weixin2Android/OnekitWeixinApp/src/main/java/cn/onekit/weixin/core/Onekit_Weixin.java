package cn.onekit.weixin.core;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.HashMap;

import thekit.android.ASSET;

import static cn.onekit.OneKit.createUUID;

public class Onekit_Weixin {

    public static JSONObject APP_JSON;
    static {
        try {
            APP_JSON = ASSET.loadJSON("miniprogram/app.json");
        } catch (FileNotFoundException e) {
            //throw new Error("未找到入口 app.json 文件，或者文件读取失败，请检查后重新编译。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HashMap<String,String> tempPaths = new HashMap();
    public static String tempPath2androidPath(String wxTempPath) {
        return tempPaths.get(wxTempPath);
    }
    public static String androidPath2tempPath(String androidPath) {
        String uuid=  createUUID();
        String ext = androidPath.substring(androidPath.lastIndexOf("."),androidPath.length());
        String wxTempPath = String.format("wxfile://tmp_oneki%s%s",uuid,ext);
        tempPaths.put(wxTempPath,androidPath);
        return wxTempPath;
    }

    public static String storePath2androidPath(String wxStorePath) {
        return storePaths.get(wxStorePath);
    }
    public static String androidPath2storePath(String androidPath) {
        String uuid=  createUUID();
        String ext = androidPath.substring(androidPath.lastIndexOf("."),androidPath.length());
        String wxStorePath = String.format("wxfile://store/oneki%s%s",uuid,ext);
        storePaths.put(wxStorePath,androidPath);
        return wxStorePath;
    }
    private static HashMap<String,String> storePaths = new HashMap();

}