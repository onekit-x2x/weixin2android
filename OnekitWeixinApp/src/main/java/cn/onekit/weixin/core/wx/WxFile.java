package cn.onekit.weixin.core.wx;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import cn.onekit.thekit.Android;
import cn.onekit.core.OneKit;
import cn.onekit.js.JsArray;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsNumber;
import cn.onekit.js.JsString;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.Onekit_Weixin;
import cn.onekit.weixin.core.res.wx_fail;

public class WxFile extends WxExtConfig {
    //待保存文件的路径
    private final String SAVED_DIRECTORY_NAME = "Onekit";

    public void saveFile(Map OBJECT) {
        String tempFilePath = OBJECT.get("tempFilePath") != null ? (String) OBJECT.get("tempFilePath") : null;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        final String savedFilename = String.valueOf(System.currentTimeMillis());
//        Log.e("path",Android.context.getApplicationContext().getFilesDir().getAbsolutePath());
//        java.io.File file = new java.io.File(Environment.getExternalStorageDirectory() + "/"
//                + SAVED_DIRECTORY_NAME, savedFilename + tempFilePath.substring(tempFilePath.lastIndexOf(".")));
//
        String androidTempPath = Onekit_Weixin.tempPath2androidPath(tempFilePath);
        String androidFileDir = Android.context.getExternalFilesDir(null).getPath();
        String shortName = OneKit.getNameFromUrl(androidTempPath);
        String androidUUIDname = OneKit.createUUIDfileName(shortName);
        // String savePath1 = APP.androidPath2storePath(tempFilePath);
//        Log.e("path",Android.context.getApplicationContext().getFilesDir().getAbsolutePath());
        java.io.File file = new java.io.File(androidFileDir, androidUUIDname);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            InputStream inStream = new FileInputStream(androidTempPath); //读入原文件
            FileOutputStream fStream = new FileOutputStream(file.getPath());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inStream.read(buffer)) > 0) {
                fStream.write(buffer, 0, length);
            }
            fStream.close();
            inStream.close();
            JsObject res = new JsObject();
//            res.savedFilePath = OnekitWeixin.androidPath2storePath(file.getPath());
//            res.errMsg = Android.context.getResources().getString(R.string.wx_saveFile_success);
            if (success != null)
                success.invoke(res);
            if (complete != null)
                complete.invoke(res);
        } catch (IOException e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_saveFile_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_saveFile_fail);
            if (fail != null)
                fail.invoke(res);
            if (complete != null)
                complete.invoke(res);
        }


//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            byte[] buffer = tempFilePath.getBytes();
//            bos.write(buffer);
//            cn.onekit.OBJECT map = new OBJECT();
//            map.put("savedFilePath", file.getAbsolutePath());
//            callback.invoke(map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

       /* AJAX.getData(tempFilePath, null, AJAX.Mode.GET, new ACTION2<Boolean,byte[]>() {
            @Override
            public JsObject invoke(Boolean isError, byte[] result) {
                java.io.File file = new java.io.File(Environment.getExternalStorageDirectory() + "/"
                        + SAVED_DIRECTORY_NAME, savedFilename + tempFilePath.substring(tempFilePath.lastIndexOf(".")));
                Log.e("=======", file.getAbsolutePath());
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    bos.write(result);
                    Map map = new HashMap();
                    map.put("savedFilePath", file.getAbsolutePath());
                    callback.invoke(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

    }

    public void getSavedFileList(Map OBJECT) {
        final function success = (OBJECT.get("success") != null) ? (function) OBJECT.get("success") : null;
        final function fail = (OBJECT.get("fail") != null) ? (function) OBJECT.get("fail") : null;
        final function complete = (OBJECT.get("complete") != null) ? (function) OBJECT.get("complete") : null;

        try {
            java.io.File file = new java.io.File(Android.context.getExternalFilesDir(null).getPath());
            java.io.File[] files = file.listFiles();
            JsObject as = new JsObject();
            JsArray filelist = new JsArray();
            for (int i = 0; i < files.length; i++) {
                String androidPath = files[i].getPath();
                String wxStorePath = Onekit_Weixin.androidPath2storePath(androidPath);
                long size = files[i].length();
                long createTime = files[i].lastModified();
                as.put("filePath", new JsString(wxStorePath));
                as.put("size",new JsNumber( size));
                as.put("createTime",new JsNumber( createTime));
                filelist.add(as);
            }
            JsObject res = new JsObject();
//            res.fileList = filelist;
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getSavedFileList_success);
            if (success != null)
                success.invoke(res);
            if (complete != null)
                complete.invoke(res);
        } catch (Exception e) {
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getSavedFileList_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getSavedFileList_fail);
            if (fail != null)
                fail.invoke(res);
            ;
            if (complete != null)
                complete.invoke(res);
        }

//        for(java.io.File f:files){
//            f.lastModified();
//        }
    }

    public void getSavedFileInfo(Map OBJECT) throws Exception {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        String wxStorePath = OBJECT.get("filePath") != null ? (String) OBJECT.get("filePath") : null;
        String androidPath = Onekit_Weixin.storePath2androidPath(wxStorePath);
        java.io.File file = new java.io.File(androidPath);
        String errMsg = "";
        if (!file.exists()) {
            errMsg = "文件不存在！";
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getSavedFileInfo_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getSavedFileInfo_fail);
            if (fail != null)
                fail.invoke(res);
            if (complete != null)
                complete.invoke(res);
            return;
        }
        JsObject res = new JsObject();
//        res.errMsg = Android.context.getResources().getString(R.string.wx_getSavedFileInfo_success);
//        res.size = Photo.getAutoFileSize(file);
//        res.createTime = file.lastModified();
        if (success != null)
            success.invoke(res);
        if (complete != null)
            complete.invoke(res);
    }

    public void removeSavedFile(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        String wxStorePath = OBJECT.get("filePath") != null ? (String) OBJECT.get("filePath") : null;
        String androidPath = Onekit_Weixin.storePath2androidPath(wxStorePath);
        java.io.File file = new java.io.File(androidPath);
        if (!file.exists()) {
            Toast.makeText(Android.context, "文件不存在", Toast.LENGTH_LONG).show();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_removeSavedFile_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_removeSavedFile_fail);
            if (fail != null)
                fail.invoke(res);
            if (complete != null)
                complete.invoke(res);
            return;
        } else {
            file.delete();
            JsObject res = new JsObject();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_removeSavedFile_success);
            if (success != null)
                success.invoke(res);
            if (complete != null)
                complete.invoke(res);
        }
    }

    public void openDocument(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        String tempPath = OBJECT.get("filePath") != null ? (String) OBJECT.get("filePath") : null;
        String androidPath = Onekit_Weixin.tempPath2androidPath(tempPath);
        java.io.File file = new java.io.File(androidPath);
        final String fileType = (OBJECT.get("fileType") != null) ? (String) OBJECT.get("fileType") : getMIMEType(file);
        try {
            openFile(file, fileType);
            JsObject res = new JsObject();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_openDocument_success);
            if (success != null)
                success.invoke(res);
            if (complete != null)
                complete.invoke(res);
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_openDocument_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_openDocument_fail);
            if (fail != null)
                fail.invoke(res);
            if (complete != null)
                complete.invoke(res);
        }
    }

    public void getFileInfo(Map OBJECT) throws Exception {
        final String filePath = OBJECT.get("filePath") != null ? (String) OBJECT.get("filePath") : null;
        final String digestAlgorithm = OBJECT.get("digestAlgorithm") != null ? (String) OBJECT.get("digestAlgorithm") : "md5";
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        String digest;
        java.io.File file = new java.io.File(Onekit_Weixin.storePath2androidPath(filePath));
        switch (digestAlgorithm) {
            case "md5":
                digest = getFileMD5(file);
                break;
            case "sha1":
                try {
                    digest = getFileSha1(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    digest = null;
                    wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getFileInfo_fail));
//                    res.errMsg = Android.context.getResources().getString(R.string.wx_getFileInfo_fail);
                    if (fail != null)
                        fail.invoke(res);
                    if (complete != null)
                        complete.invoke(res);
                }
                break;
            default:
                digest = null;
                break;
        }
        JsObject res = new JsObject();
//        res.errMsg = Android.context.getResources().getString(R.string.wx_getFileInfo_success);
//        res.size = Photo.getAutoFileSize(new File(OnekitWeixin.storePath2androidPath(filePath)));
//        res.digest = digest;
        if (complete != null)
            complete.invoke(res);
        if (success != null)
            success.invoke(res);
    }


    public String getFileSha1(java.io.File file) throws OutOfMemoryError, IOException {

        FileInputStream in = new FileInputStream(file);
        MessageDigest messagedigest;
        try {
            messagedigest = MessageDigest.getInstance("SHA-1");

            byte[] buffer = new byte[1024 * 1024 * 10];
            int len = 0;

            while ((len = in.read(buffer)) > 0) {
                //该对象通过使用 update（）方法处理数据
                messagedigest.update(buffer, 0, len);
            }

            //对于给定数量的更新数据，digest 方法只能被调用一次。在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。
            return byte2hex(messagedigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            throw e;
        } finally {
            in.close();
        }
        return null;
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param //byte[] b byte数组
     * @return String 每个Byte值之间空格分隔
     */
    private String byte2hex(byte[] digest) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < digest.length; n++) {
            stmp = Integer.toHexString(digest[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();

    }

    /**
     * 获取单个文件的MD5值！
     *
     * @param file
     * @return
     */

    public String getFileMD5(java.io.File file) {

        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }


    /**
     * 打开文件
     *
     * @param file
     */
    private void openFile(java.io.File file, String type) throws Exception {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
//获取文件file的MIME类型
        //    String type = getMIMEType(file);
//设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), type);
//跳转
        try {
            Android.context.startActivity(intent); //这里最好try一下，有可能会报错。 //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private String getMIMEType(java.io.File file) {

        String type = "*/*";
        String fName = file.getName();
//获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end.equals("")) return type;
//在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }


    private final String[][] MIME_MapTable = {
//{后缀名，MIME类型}
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".pdf", "application/pdf"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},


//            {".3gp", "video/3gpp"},
//            {".apk", "application/vnd.android.package-archive"},
//            {".asf", "video/x-ms-asf"},
//            {".avi", "video/x-msvideo"},
//            {".bin", "application/octet-stream"},
//            {".bmp", "image/bmp"},
//            {".c", "text/plain"},
//            {".class", "application/octet-stream"},
//            {".conf", "text/plain"},
//            {".cpp", "text/plain"},
//            {".exe", "application/octet-stream"},
//            {".gif", "image/gif"},
//            {".gtar", "application/x-gtar"},
//            {".gz", "application/x-gzip"},
//            {".h", "text/plain"},
//            {".htm", "text/html"},
//            {".html", "text/html"},
//            {".jar", "application/java-archive"},
//            {".java", "text/plain"},
//            {".jpeg", "image/jpeg"},
//            {".jpg", "image/jpeg"},
//            {".js", "application/x-JavaScript"},
//            {".log", "text/plain"},
//            {".m3u", "audio/x-mpegurl"},
//            {".m4a", "audio/mp4a-latm"},
//            {".m4b", "audio/mp4a-latm"},
//            {".m4p", "audio/mp4a-latm"},
//            {".m4u", "video/vnd.mpegurl"},
//            {".m4v", "video/x-m4v"},
//            {".mov", "video/quicktime"},
//            {".mp2", "audio/x-mpeg"},
//            {".mp3", "audio/x-mpeg"},
//            {".mp4", "video/mp4"},
//            {".mpc", "application/vnd.mpohun.certificate"},
//            {".mpe", "video/mpeg"},
//            {".mpeg", "video/mpeg"},
//            {".mpg", "video/mpeg"},
//            {".mpg4", "video/mp4"},
//            {".mpga", "audio/mpeg"},
//            {".msg", "application/vnd.ms-outlook"},
//            {".ogg", "audio/ogg"},
            //            {".png", "image/png"},
//            {".pps", "application/vnd.ms-powerpoint"},
//            {".prop", "text/plain"},
//            {".rc", "text/plain"},
//            {".rmvb", "audio/x-pn-realaudio"},
//            {".rtf", "application/rtf"},
//            {".sh", "text/plain"},
//            {".tar", "application/x-tar"},
//            {".tgz", "application/x-compressed"},
//            {".txt", "text/plain"},
//            {".wav", "audio/x-wav"},
//            {".wma", "audio/x-ms-wma"},
//            {".wmv", "audio/x-ms-wmv"},
//            {".wps", "application/vnd.ms-works"},
//            {".xml", "text/plain"},
//            {".z", "application/x-compress"},
//            {".zip", "application/x-zip-compressed"},
//            {"", "*/*"}

    };

    public void copyFileToDir(String srcFile, String destFile) {
        java.io.File fileDir = new java.io.File(destFile);
		/*if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		String destFile = destDir +"/" + new java.io.File(srcFile).getName();*/
        try {
            InputStream streamFrom = new FileInputStream(srcFile);
            OutputStream streamTo = new FileOutputStream(destFile);
            byte buffer[] = new byte[1024];
            int len;
            while ((len = streamFrom.read(buffer)) > 0) {
                streamTo.write(buffer, 0, len);
            }
            streamFrom.close();
            streamTo.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

