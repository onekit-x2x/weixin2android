package demo.css;

import cn.onekit.js.Array;
import cn.onekit.weixin.app.core.WeixinClass;

public class onekit_lib_Arrays extends WeixinClass {
    public  static Array copyOf(Array array,int length){
        Array result = array;
        if(result.getLength()>=length){
            result.setLength(length);
        }else{
            while(result.getLength()<length){
                result.push(0);
            }
        }
        return result;
    }
    public static boolean equals(Array array1,Array array2){
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
