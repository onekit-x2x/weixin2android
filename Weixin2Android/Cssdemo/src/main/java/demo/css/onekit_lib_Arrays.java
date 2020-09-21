package demo.css;

import cn.onekit.js.JsArray;
import cn.onekit.weixin.app.core.WeixinClass;

public class onekit_lib_Arrays extends WeixinClass {
    public  static JsArray copyOf(JsArray array, int length){
        JsArray result = array;
        if(result.getLength()>=length){
            result.setLength(length);
        }else{
            while(result.getLength()<length){
                result.push(0);
            }
        }
        return result;
    }
    public static boolean equals(JsArray array1, JsArray array2){
        if(array1.getLength()!=array2.getLength()){
            return false;
        }
        for(int i=0;i<array1.getLength();i++){
            if(!array1.get(i).equals(array2.get(i))){
                return false;
            }
        }
        return true;
    }
}
