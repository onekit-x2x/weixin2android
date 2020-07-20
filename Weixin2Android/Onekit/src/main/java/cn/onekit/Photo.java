package cn.onekit;

import java.io.FileInputStream;

/**
 * Created by lenovo on 2017/5/16.
 * 照片
 */

public class Photo {

    public static Long getAutoFileSize(java.io.File file) throws Exception {
        long size;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
           size =0;
        }
        return size;
    }}
