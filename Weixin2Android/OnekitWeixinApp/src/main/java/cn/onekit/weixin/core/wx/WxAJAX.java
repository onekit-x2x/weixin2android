package cn.onekit.weixin.core.wx;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import cn.onekit.Android;
import cn.onekit.TheKit;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsString;
import cn.onekit.js.TypedArray;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.weixin.DownloadTask;
import cn.onekit.weixin.RequestTask;
import cn.onekit.weixin.UploadTask;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.app.core.Onekit_Weixin_App;
import cn.onekit.weixin.core.Onekit_Weixin;
import cn.onekit.weixin.core.res.wx_fail;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;


public class WxAJAX extends WxAddress {
    /**
     * getString 返回字符串 需要权限：android.permission.INTERNET
     *
     * @param object 传输数据
     */
    public RequestTask request(final Dict object) {
        final String url = ((JsString) object.get("url")).THIS;
        final String method = (object.get("method") != null && (!object.get("method").equals(""))) ? ((JsString) object.get("method")).THIS.trim() : "GET";
        final Dict header = (object.get("header") != null) ? (Dict) object.get("header") : null;
        final String dataType = (object.get("dataType") != null && (!object.get("dataType").equals(""))) ? ((JsString) object.get("dataType")).THIS.trim() : "json";
        final JsObject data = (object.get("data") != null) ?object.get("data") : null;
        final function fail = object.get("fail") != null ? (function) object.get("fail") : null;
        final function success = object.get("success") != null ? (function) object.get("success") : null;
        final function complete = object.get("complete") != null ? (function) object.get("complete") : null;
        Request.Builder builder = new Request.Builder();
        String fullUrl = url;
        if (header != null) {
            for (Map.Entry entry : header.entrySet()) {
                builder.addHeader(entry.getKey().toString(), (String) entry.getValue());
            }
        }
        if (data != null) {
            if (method.toString().equals("GET")) {
                //get处理
                if (data != null) {
                    fullUrl += "?" + addDataGET(object);
                }
                builder.get();
            } else {
                //post处理
                //如果是<key,value>，就以键值对的形式传参数，
                //如果是不是键值对，就直接变成String的形式传上去
                try {
                    RequestBody body = null;
                    switch (data.getClass().getSimpleName()) {
                        case "OBJECT":
                            FormBody.Builder bodyBuilder = new FormBody.Builder();
                            Dict map = (Dict) data;
                            for (Map.Entry entry : map.entrySet()) {
                                bodyBuilder.add(entry.getKey().toString(), String.valueOf(entry.getValue()));
                            }
                            body = bodyBuilder.build();
                            break;
                        case "String":
                            body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), ((JsString) data).THIS);
                            break;
                        default:
                            if (data instanceof TypedArray) {
                                body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), ((TypedArray) data)._buffer._data);
                            } else {
                                body = null;
                            }
                            break;
                    }
                    switch (method) {
                        case "POST":
                            builder.post(body);
                            break;
                        case "PUT":
                            builder.put(body);
                            break;
                        case "DELETE":
                            builder.delete(body);
                            break;
                        case "HEAD":
                            builder.method("HEAD", body);
                            break;
                        case "OPTIONS":
                            builder.method("OPTIONS", body);
                            break;
                        case "TRACE":
                            builder.method("TRACE", body);
                            break;
                        case "CONNECT":
                            builder.method("CONNECT", body);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        builder.url(fullUrl);
        final Request request = builder.build();
        //执行 返回 调用返回函数
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        RequestTask requestTask = new RequestTask();
        requestTask.call = call;
        requestTask.okHttpClient = okHttpClient;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                wx_fail result = new wx_fail(e.toString());
                //result.fail = e.toString();
//                result.errMsg = Android.context.getResources().getString(R.string.wx_request_fail);
                if (fail != null) {
                    fail.invoke(result);
                }
                if (complete != null) {
                    complete.invoke(result);
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String data = response.body().string();
                if (response.code() == 200) {
                    Dict result = new Dict();/*(HashMap) JSON.parse(data),
                            response.code(),
                            (Dict) JSON.parse(response.headers().toString()),
                            null);*/
//                    result.data = (HashMap) JSON.parse(data);//dataType.equalsIgnoreCase("json") ? (HashMap) JSON.parse(data) : data;
//                    result.statusCode = response.code();
//                    result.header = (Dict) JSON.parse(response.headers().toString());//response.headers();
//                    result.errMsg = Android.context.getResources().getString(R.string.wx_request_success);
                    if (success != null) {
                        success.invoke(result);
                    }
                    if (complete != null) {
                        complete.invoke(result);
                    }
                } else {
                    wx_fail result = new wx_fail(Android.context.getResources().getString(R.string.wx_request_fail));
//                    result.fail = Android.context.getResources().getString(response.code());
//                    result.statusCode = Android.context.getResources().getString(response.code());
//                    result.header = response.headers();
//                    result.errMsg = Android.context.getResources().getString(R.string.wx_request_fail);
                    if (fail != null) {
                        fail.invoke(result);
                    }
                    if (complete != null) {
                        complete.invoke(result);
                    }
                }
            }
        });
        return requestTask;
    }

    /**
     * 上传文件
     */
    private UploadTask uploadTask;

    public UploadTask uploadFile(final Map OBJECT) {
        final String url = OBJECT.get("url") != null ? (String) OBJECT.get("url") : null;
        final String name = OBJECT.get("name") != null ? (String) OBJECT.get("name") : null;
        final String filePath = OBJECT.get("filePath") != null ? (String) OBJECT.get("filePath") : null;
        final Dict header = OBJECT.get("header") != null ? (Dict) OBJECT.get("header") : null;
        final Dict formData = OBJECT.get("formData") != null ? (Dict) OBJECT.get("formData") : null;
        final function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        final function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        final function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        uploadTask = new UploadTask();
        OkHttpClient okHttpClient = new OkHttpClient();
        //检测文件是否存在   不存在直接返回
        String androidPath = Onekit_Weixin.tempPath2androidPath(filePath);
        java.io.File file = new java.io.File(androidPath);
        Request.Builder builder = new Request.Builder();
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        bodyBuilder.addFormDataPart(name, file.getName(), createCustomRequestBody(MediaType.parse("application/octet-stream"), file, new ProgressListener() {
            @Override
            public void onProgress(long totalBytes, long remainingBytes, boolean done) {
                System.out.print((totalBytes - remainingBytes) * 100 / totalBytes + "%");
            }
        }));
        for (Map.Entry entry : formData.entrySet()) {
            bodyBuilder.addFormDataPart(entry.getKey().toString(), String.valueOf(entry.getValue()));
        }
        MultipartBody requestBody = bodyBuilder.build();
        if (header != null) {
            for (Map.Entry entry : header.entrySet()) {
                builder.addHeader(entry.getKey().toString(), (String) entry.getValue());
            }
        }
        Request request = builder.url(url).post(requestBody).build();
        //上传 返回结果 返回
        Call call = okHttpClient.newCall(request);
        uploadTask.call = call;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                wx_fail result = new wx_fail(Android.context.getResources().getString(R.string.wx_uploadFile_fail));
//                result.statusCode = e.toString();
//                result.errMsg = Android.context.getResources().getString(R.string.wx_uploadFile_fail);
                if (fail != null) {
                    fail.invoke(result);
                }
                if (complete != null) {
                    complete.invoke(result);
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String data = response.body().string();
                if (response.code() == 200) {
                    Dict result = new Dict();/*
                            data,
                            response.code());
//                    result.statusCode = response.code();
//                    result.data = data;
//                    result.headers = response.headers();
//                    result.errMsg = Android.context.getResources().getString(R.string.wx_uploadFile_success);*/
                    if (success != null) {
                        success.invoke(result);
                    }
                    if (complete != null) {
                        complete.invoke(result);
                    }
                } else {
                    wx_fail result = new wx_fail(Android.context.getResources().getString(R.string.wx_uploadFile_fail));
//                    result.statusCode = Android.context.getResources().getString(response.code());
//                    result.errMsg = Android.context.getResources().getString(R.string.wx_uploadFile_fail);
                    if (fail != null) {
                        fail.invoke(result);
                    }
                    if (complete != null) {
                        complete.invoke(result);
                    }
                }
            }
        });
        return uploadTask;
    }


    public DownloadTask downloadFile(final Map OBJECT) {
        final String url = OBJECT.get("url") != null ? (String) OBJECT.get("url") : null;
        final function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        final function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        final function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        final DownloadTask downloadTask = new DownloadTask();
        downloadTask.call = call;
        downloadTask.okHttpClient = okHttpClient;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // 下载失败
                wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_downloadFile_fail));
//                res.errMsg = Android.context.getResources().getString(R.string.wx_downloadFile_fail);
//                res.statusCode = e.toString();
                if (fail != null) {
                    fail.invoke(res);
                }
                if (complete != null) {
                    complete.invoke(res);
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
//                String savePath = isExistDir(saveDir);
                String androidTempDir = Android.context.getExternalCacheDir().getPath();
                String shortName = TheKit.getNameFromUrl(url);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    String androidUUIDname = TheKit.createUUIDfileName(shortName);
                    java.io.File file = new java.io.File(androidTempDir, androidUUIDname);
                    String androidTempPath = file.getPath();
                    // Log.i("sss",androidTempPath);
                    final String wxTempPath = Onekit_Weixin.androidPath2tempPath(androidTempPath);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        long totalBytesWritten = sum;
                        long totalBytesExpectedToWrite = total;
                        if (downloadTask.onProgressUpdate != null) {
                            Dict json = new Dict();
                            json.put("progress", new JsNumber(progress));
                            //    Log.v("bi", String.valueOf(progress));
                            json.put("totalBytesWritten",new JsNumber( totalBytesWritten));
                            json.put("totalBytesExpectedToWrite", new JsNumber(totalBytesExpectedToWrite));
                            downloadTask.onProgressUpdate.invoke(json);
                        }
                    }
                    fos.flush();
                    // 下载完成
                    Dict r =new Dict();/* new wx_downloadFile(
                            wxTempPath,
                            null,
                            response.code());
//                    r.data = response.invoke().string();
//                    r.errMsg = Android.context.getResources().getString(R.string.wx_downloadFile_success);
//                    r.statusCode = response.code();
//                    r.tempFilePath = wxTempPath;*/
                    if (success != null) {
                        success.invoke(r);
                    }
                    if (complete != null) {
                        complete.invoke(r);
                    }
                } catch (Exception e) {
                    wx_fail r = new wx_fail(Android.context.getResources().getString(R.string.wx_downloadFile_fail));
//                    r.statusCode = String.valueOf(response.code());
//                    r.errMsg = Android.context.getResources().getString(R.string.wx_downloadFile_fail);
                    if (fail != null) {
                        fail.invoke(r);
                    }
                    if (complete != null) {
                        complete.invoke(r);
                    }
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
        return downloadTask;
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException
     * 判断下载目录是否存在
     */
//    private String isExistDir(String saveDir) throws IOException {
//        // 下载位置
//        java.io.File downloadFile = new java.io.File(Environment.getExternalStorageDirectory(), saveDir);
//        if (!downloadFile.mkdirs()) {
//            downloadFile.createNewFile();
//        }
//        String savePath = downloadFile.getAbsolutePath();
//        return savePath;
//    }


    /**
     * 下载文件
     */
//
//    public void downloadFile(final Dict OBJECT) throws MalformedURLException {
//
//        //申明 全局设置
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request.Builder builder = new Request.Builder();
//        String url = null;
//        JsObject header;
//        if (OBJECT.containsKey("url")) {
//            url = (String) OBJECT.get("url");
//        }
//        if (OBJECT.containsKey("header")) {
//            header = (String) OBJECT.get("header");
//        }
//        final Request request = builder
//                .get()
//                .url(url)
//                .build();
//
//        final Call call = okHttpClient.newCall(request);
//        final DownloadTask downloadTask = new DownloadTask();
//        downloadTask.client = okHttpClient;
//        downloadTask.call = call;
//        int size = 0;
//        int nowSize = 0;
//        final String finalUrl = url;
//        new AsyncTask() {
//            @Override
//            protected JsObject doInBackground(JsObject[] objects) {
//                try {
//                    Response response = call.execute();
//                    InputStream is = response.invoke().byteStream();
//                    int len = 0;
//                    byte[] buf = new byte[128];
//                    //下载文件后的  储存的路径，以及储存的文件的类型，暂时留空
//                    final java.io.File file = new java.io.File(Android.context.getCacheDir(), "new.mp3"
////                            + judgeFile(finalUrl)
//                    );
////                    Log.e("bi","new."+judgeFile(finalUrl));
//                    FileOutputStream fos = new FileOutputStream(file);
//                    int size = 0;
//                    int nowSize = 0;
//                    while ((len = is.read(buf)) != -1) {
//                        nowSize += len;
//                        size = is.available();
//                        downloadTask.progress = nowSize/size*100;
//                        downloadTask.totalBytesWritten = nowSize;
//                        downloadTask.totalBytesExpectedToWrite = size;
//                        fos.write(buf, 0, len);
//                        publishProgress(new OBJECT() {{
//////						  put("progress",(finalNowSize / finalSize)+"");//下载进度百分比
////                            put("totalBytesWritten", finalNowSize); //已经下载的数据长度，单位 Bytes
////                            put("totalBytesExpectedToWrite", finalSize); //预期需要下载的数据总长度，单位 Bytes
//                            downloadTask.onProgressUpdate(
//                                @Override
//                                public JsObject onProgress(JsObject[] res) {
//                                    res[0] = downloadTask.progress;
//                                    put("progress",res[0]);
//                                    res[1] = downloadTask.totalBytesWritten;
//                                    put("totalBytesWritten",res[1]);
//                                    res[2] = downloadTask.totalBytesExpectedToWrite;
//                                    put("totalBytesExpectedToWrite",res[2]);
//                                    return res;
//                                }
//                            });
//                        }});
//                    }
//                    fos.flush();
//                    fos.close();
//                    is.close();
//                    return new JsObject[]{response.code(), file.getPath()};
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//
//            @Override
//            protected void onProgressUpdate(JsObject[] values) {
//                super.onProgressUpdate(values);
//               // Log.e("=============v", values[0] + "===");
//            }
//
//            @Override
//            protected void onPostExecute(JsObject o) {
//                super.onPostExecute(o);
//                try {
//                    final JsObject[] result = (JsObject[]) o;
//                    final String s = "tmp";
//                    if (OBJECT.containsKey("success")) {
//                        final FUNC1 callback = (FUNC1) OBJECT.get("success");
//                        //下载成功后   回调的参数
//                        callback.invoke(new OBJECT() {{
//                            put("code", result[0]);
//                            put("tempFilePath", APP.setPath(s,result[1].toString()));
//                        }});
//                    }
//                    if (OBJECT.containsKey("complete")) {
//                        final FUNC1 complete = (FUNC1) OBJECT.get("complete");
//                        //下载成功后   回调的参数
//                        complete.invoke(new OBJECT() {{
//                            put("complete", "调用成功");
//                        }});
//                    }
//
//                } catch (final Exception e) {
//                    e.printStackTrace();
//                    if (OBJECT.containsKey("fail")) {
//                        final FUNC1 fail = (FUNC1) OBJECT.get("fail");
//                        //下载成功后   回调的参数
//                        fail.invoke(new OBJECT() {{
//                            put("fail", e.toString());
//                        }});
//                    }
//                    if (OBJECT.containsKey("complete")) {
//                        final FUNC1 complete = (FUNC1) OBJECT.get("complete");
//                        //下载成功后   回调的参数
//                        complete.invoke(new OBJECT() {{
//                            put("complete", "调用成功");
//                        }});
//                    }
//                }
//            }
//        }.execute();
//
//
//
//        /*
//        call.enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//				Log.e("bili","下载失败");
//			}
//
//			@Override
//			public void onResponse(Call call, final Response response) throws IOException {
//				Log.e(TAG,"下载成功");
//				InputStream is = response.invoke().byteStream();
//
//				int len = 0;
//				byte[] buf = new byte[128];
//				//下载文件后的  储存的路径，以及储存的文件的类型，暂时留空
//				final java.io.File file = new java.io.File(Android.context.getCacheDir(),"new.jpg");
//				FileOutputStream fos = new FileOutputStream(file);
//
//				while((len = is.read(buf)) != -1){
//					fos.write(buf,0,len);
//				}
//				fos.flush();
//				fos.close();
//				is.close();
//
//				//下载成功后   回调的参数
//                final FUNC1 callback = (FUNC1) OBJECT.get("success");
//				callback.invoke(new cn.onekit.OBJECT(){{
//					put("code",response.code());
//					put("tempFilePath",file.getPath().toString());
//				}});
//			}
//		});*/
//    }
//
////    private String judgeFile(String strUrl) {
////        String suffix = strUrl.substring(strUrl.lastIndexOf(".") + 1);
////        String type = null;
////        if (suffix.equals("MP3")||suffix.equals("MPEG")||
////                suffix.equals("mp3")||suffix.equals("mpeg") ){
////            type = "mpeg";
////        }else if (suffix.equals("JPG")||suffix.equals("jpg")) {
////            type = "jpeg";
////        }else {
////            type = suffix;
////        }
////        return type;
////    }
    private RequestBody createCustomRequestBody(final MediaType contentType, final java.io.File file, final ProgressListener listener) {
        return new RequestBody() {
            //    UploadTask uploadTask = new UploadTask();

            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    //sink.writeAll(source);
                    Buffer buf = new Buffer();
                    Long remaining = contentLength();
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        long totalBytes = contentLength();
                        long remainingBytes = remaining -= readCount;
                        int progress = (int) ((totalBytes - remainingBytes) * 100 / totalBytes);
                        long totalBytesWritten = remainingBytes;
                        long totalBytesExpectedToWrite = totalBytes;
//                        if (uploadTask.onProgressUpdate != null) {
//                            Dict json = new Dict();
//                            json.put("progress", progress);
//                            //  Log.v("bi", String.valueOf(progress));
//                            json.put("totalBytesWritten", totalBytesWritten);
//                            json.put("totalBytesExpectedToWrite", totalBytesExpectedToWrite);
//                            uploadTask.onProgressUpdate.invoke(json);
//                        }
                        listener.onProgress(contentLength(), remaining -= readCount, remaining == 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    protected interface ProgressListener {
        void onProgress(long totalBytes, long remainingBytes, boolean done);
    }


    private String addDataGET(Dict datas) {

        String addUrl = "?";
        try {
            for (Map.Entry<String, JsObject> data : ((Dict) datas.get("data")).entrySet()) {
                addUrl = addUrl + "&" + data.getKey() + "=" + data.getValue();
            }
        } catch (Exception e) {
            //Log.e("hzq","参数为字符串");
            addUrl = addUrl + ((JsString) datas.get("data")).THIS;
        }
        return addUrl;
    }
}

