package cn.onekit.css;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.onekit.OneKit;
import cn.onekit.css.core.CSS2JSON;
import thekit.ASSET;

public class CSSStyleSheet extends StyleSheet {
    private List<CSSRule> _cssRules;
    CSS2JSON CSS2JSON;

    public CSSStyleSheet(String prev, CSS2JSON CSS2JSON) {
        super(prev);
        _cssRules = new ArrayList();
        this.CSS2JSON=CSS2JSON;
    }

    public List<CSSRule> cssRules(){
        return _cssRules;
    }
    private Map<String,String> _keyframes;
    public Map<String,String> keyframes(){
        return _keyframes;
    }
    private void keyframes(Map<String,String> keyframes){
         _keyframes=keyframes;
    }
    public boolean load(String currentUrl,String url)  {
        try {
            String path = OneKit.fixPath(currentUrl,url);
            String css = ASSET.loadString(prev+path);
            Map<String,Object> jSON = CSS2JSON.parse(css);

            ////////////////////////
            this.keyframes((Map<String, String>) jSON.get("keyframes"));
            List<Map<String,Object>> jsons = (List<Map<String, Object>>) jSON.get("css");
            for (int j = 0; j < jsons.size(); j++) {
                Map<String,Object> json = jsons.get(j);
                CSSStyleRule cssStyleRule = new CSSStyleRule();
                if (json.get("key").equals("import")) {
                    String url2 = (String) json.get("value");
                    this.load(path, url2);
                    continue;
                }
                cssStyleRule.selectorText((String) json.get("key"));
                List<Map<String,String>> values = (List<Map<String,String>>) json.get("value");
                for (int v = 0; v < values.size(); v++) {
                    Map<String,String> jsn =  values.get(v);
                    String priority = jsn.get("priority");
                    cssStyleRule.style().setProperty(jsn.get("key"),jsn.get("value"), priority!=null && priority.equals("important") ? 10000 : 0);
                }
                this.cssRules().add(cssStyleRule);
            }
            return true;
        }catch (IOException ex){
            //ex.printStackTrace();
            Log.e("[Onekit CSS?]=========",url);
            //ex.printStackTrace();
            return false;
        }
    }
}
