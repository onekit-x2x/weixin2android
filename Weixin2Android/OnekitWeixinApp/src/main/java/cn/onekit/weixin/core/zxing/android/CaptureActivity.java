package cn.onekit.weixin.core.zxing.android;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import thekit.android.Android;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.zxing.RGBLuminanceSource;
import cn.onekit.weixin.core.zxing.Utils;
import cn.onekit.weixin.core.zxing.camera.CameraManager;
import cn.onekit.weixin.core.zxing.view.ViewfinderView;

/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 */
public final class CaptureActivity extends Activity
        implements SurfaceHolder.Callback {

    private int CAMERA_OK = 2000;
    private static final int REQUEST_CODE = 201;
    private static final String TAG = CaptureActivity.class.getSimpleName();
    private boolean onlyFromCamera;
    // 相机控制
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private IntentSource source;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    // 电量控制
    private InactivityTimer inactivityTimer;
    // 声音、震动控制
    private BeepManager beepManager;

    private ImageButton imageButton_back, iamge;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    /**
     * OnCreate中初始化一些辅助类，如InactivityTimer（休眠）、Beep（声音）以及AmbientLight（闪光灯）
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        permissions();

        onlyFromCamera = getIntent().getBooleanExtra("onlyFromCamera", false);
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.onekit_activity_capture);

        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        init();
    }

    private void init() {
        imageButton_back = (ImageButton) findViewById(R.id.capture_imageview_back);
        iamge = (ImageButton) findViewById(R.id.iamge);

        imageButton_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iamge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openOptionsMenu();
            }
        });
    }

    private void permissions() {
        // android 6.0 相机动态权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0以上

            int checkPermission = checkSelfPermission(Manifest.permission.CAMERA);

            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);//后面的1为请求码
                return;
            } else {
               // initCamera(holder);//zxing二维码扫描，需要摄像头权限
            }
        } else {//6.0以下
           // initCamera(holder); //zxing二维码扫描，需要摄像头权限

        }

    }



    //重写onCreateOptionMenu(Menu menu)方法，当菜单第一次被加载时调用
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //通过代码的方式来添加Menu
        //menu.add()里面有四个参数依次是：
        //第一个，组别。
        //第二个，ID。是menu识别编号，供识别menu用的。很重要。
        //第三个，顺序。这个参数的大小决定菜单出现的先后顺序。顺序是参数由小到大，菜单从左到右，从上到下排列。一行最多三个。
        //第四个，显示文本。
        int itemld = Menu.FIRST - 1;
        if (onlyFromCamera == true) {
            itemld = Menu.FIRST + 1;
        }
        menu.add(0, Menu.FIRST, 100, "将扫一扫添加到桌面" + "                         ");
        menu.add(0, itemld, 200, "从相册选取二维码" + "                         ");
        return true;
    }

    //重写OptionsItemSelected(MenuItem item)来响应菜单项(MenuItem)的点击事件（根据id来区分是哪个item）
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST:
                String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
                Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);

                // 不允许重复创建
                addShortcutIntent.putExtra("duplicate", false);// 经测试不是根据快捷方式的名字判断重复的
                // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
                // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
                // 屏幕上没有空间时会提示
                // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式

                // 名字
                addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "扫一扫");

                // 图标
                addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                        Intent.ShortcutIconResource.fromContext(Android.context,
                                R.drawable.ic_launcher));

                // 设置关联程序
                Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
                launcherIntent.setClass(Android.context, CaptureActivity.class);
                launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);

                addShortcutIntent
                        .putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);

                // 发送广播
                sendBroadcast(addShortcutIntent);

                break;
            case Menu.FIRST + 1:
                Intent innerIntent = new Intent();
                if (Build.VERSION.SDK_INT < 19) {
                    innerIntent.setAction(Intent.ACTION_GET_CONTENT);
                } else {
                    innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                }
                innerIntent.setType("image/*");
                Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
                startActivityForResult(wrapperIntent, REQUEST_CODE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager必须在这里初始化，而不是在onCreate()中。
        // 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
        // 当扫描框的尺寸不正确时会出现bug
        cameraManager = new CameraManager(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);
        handler = null;

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        inactivityTimer.onResume();

        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler.restartPreviewAndDecode();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    /**
     * 扫描成功，处理反馈信息
     *
     * @param rawResult
     * @param barcode
     * @param scaleFactor
     */

    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();
        boolean fromLiveScan = (barcode != null);

        //这里处理解码完成后的结果，此处将参数回传到Activity处理
        if (fromLiveScan) {
            beepManager.playBeepSoundAndVibrate();
            //   Toast.makeText(this, "扫描成功", Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            intent.putExtra("codedContent", rawResult.getText());
            intent.putExtra("codedBitmap", barcode);
            intent.putExtra("isSuccess", fromLiveScan);
            setResult(RESULT_OK, intent);

        } else {
            //    Toast.makeText(this, "扫描失败", Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            intent.putExtra("codedContent", rawResult.getText());
            intent.putExtra("codedBitmap", barcode);
            intent.putExtra("isSuccess", fromLiveScan);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    /**
     * 初始化Camera
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    /**
     * 显示底层错误信息并退出应用
     */
    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                finish();
            }
        }
        if (requestCode == CAMERA_OK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //这里已经获取到了摄像头的权限，想干嘛干嘛了可以

            } else {
                //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                Toast.makeText(CaptureActivity.this, "请手动打开相机权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String photo_path;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE:
                String[] proj = {MediaStore.Images.Media.DATA};
                // 获取选中图片的路径
                Cursor url = getContentResolver().query(data.getData(),
                        proj, null, null, null);
                if (url.moveToFirst()) {
                    int column_index = url
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    photo_path = url.getString(column_index);
                    if (photo_path == null) {
                        photo_path = Utils.getPath(getApplicationContext(),
                                data.getData());
                    }
                }
                url.close();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Result result = parseQRcodeBitmap(photo_path);
                        if (result == null) {
                            Looper.prepare();
                            Intent intent = getIntent();
                            intent.putExtra("codedContent", "图片格式有误");
                            intent.putExtra("isSuccess", false);
                            setResult(RESULT_OK, intent);
                            // Toast.makeText(getApplicationContext(), "图片格式有误", Toast.LENGTH_SHORT).show();
                            Looper.loop();


                        } else {
                            // 数据返回
                            String recode = recode(result.toString());
                            Intent intent = getIntent();
                            intent.putExtra("isSuccess", true);
                            intent.putExtra("codedContent", recode);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                }).start();
                break;
        }
    }

    /**
     * 中文乱码
     * 暂时解决大部分的中文乱码 但是还有部分的乱码无法解决 .
     */
    private String recode(String str) {
        String formart = "";
        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder().canEncode(str);
            if (ISO) {
                formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
            } else {
                formart = str;
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return formart;
    }

    //解析二维码图片,返回结果封装在Result对象中
    private Result parseQRcodeBitmap(String bitmapPath) {
        //解析转换类型UTF-8
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        //获取到待解析的图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果我们把inJustDecodeBounds设为true，那么BitmapFactory.decodeFile(String path, Options opt)
        //并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath, options);

        options.inSampleSize = options.outHeight / 400;
        if (options.inSampleSize <= 0) {
            options.inSampleSize = 1; //防止其值小于或等于0
        }
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
        //将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        //开始解析
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }


}
