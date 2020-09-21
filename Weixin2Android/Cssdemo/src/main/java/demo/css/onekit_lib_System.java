package demo.css;

import cn.onekit.js.JsArray;

public class onekit_lib_System {
    public static void arraycopy(JsArray src, int srcPos, JsArray dest, int destPos, int length){
        for(int i=0;i<length;i++){
            dest.set(destPos+i,src.get(srcPos+i));
        }
    }
}
