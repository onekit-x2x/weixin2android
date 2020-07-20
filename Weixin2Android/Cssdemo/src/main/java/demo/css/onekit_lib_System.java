package demo.css;

import cn.onekit.js.Array;

public class onekit_lib_System {
    public static void arraycopy(Array src, int srcPos,Array dest,int destPos,int length){
        for(int i=0;i<length;i++){
            dest.set(destPos+i,src.get(srcPos+i));
        }
    }
}
