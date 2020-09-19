package cn.onekit.weixin.core.wx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import thekit.android.Android;
import thekit.COLOR;
import cn.onekit.js.JsBoolean;
import cn.onekit.js.Dict;
import cn.onekit.js.JsString;
import cn.onekit.js.core.Onekit_JS;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxDialog extends WxDeviceMotion {
    private  LinearLayout the_loading_mask;

    public  void showLoading(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        String title = OBJECT.get("title") != null ? (String) OBJECT.get("title") : null;
        boolean mask = OBJECT.get("mask") != null ? (boolean) OBJECT.get("mask") : false;
        int window_flags;
        if (!mask) {
            window_flags = 0x00000010;
        } else {
            window_flags = 0x0000008;
        }
        the_loading_mask = new LinearLayout(application);
        the_loading_mask.setFocusable(false);
        the_loading_mask.setBackgroundColor(Color.GRAY);
        the_loading_mask.getBackground().setAlpha(0);
        the_loading_mask.setOrientation(LinearLayout.VERTICAL);
        the_loading_mask.setGravity(Gravity.CENTER);
        LinearLayout the_radius = new LinearLayout(application);
        //the_radius.setBackgroundResource(R.drawable.radius);
        the_radius.getBackground().setAlpha(100);
        the_radius.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.
                LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        the_radius.setGravity(Gravity.CENTER);
        the_loading_mask.addView(the_radius, param);
        try {
            ImageView imageView = new ImageView(application);
            imageView.setPadding(40, 40, 40, 40);
           // imageView.setImageResource(R.drawable.animation1);
            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
            the_radius.addView(imageView);
            TextView tv = new TextView(application);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(17);
            tv.setText(title);
            tv.setTextColor(Color.BLACK);
//        tv.getBackground().setAlpha(0);
            tv.setPadding(40, 0, 40, 40);
            the_radius.addView(tv);
            WindowManager wmManager = (WindowManager) application.getSystemService(application.WINDOW_SERVICE);
            WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
            DisplayMetrics dm = new DisplayMetrics();
            wmManager.getDefaultDisplay().getMetrics(dm);
            wmParams.format = 1;
            wmParams.flags = window_flags;
            wmParams.width = dm.widthPixels;
            wmParams.height = dm.heightPixels;
            wmManager.addView(the_loading_mask, wmParams);  //创建View
            Dict res = new Dict();
//            res.errMsg = application.getResources().getString(R.string.wx_showLoading_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(application.getResources().getString(R.string.wx_showLoading_fail));
//            res.errMsg = application.getResources().getString(R.string.wx_showLoading_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    //结束等待框
    public  void hideLoading(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        //如果已经设定了themask才会调用删除
        if (the_loading_mask != null) {
            WindowManager wmManager = (WindowManager) application.getSystemService(application.WINDOW_SERVICE);
            wmManager.removeView(the_loading_mask);
            the_loading_mask = null;
            Dict res = new Dict();
//            res.errMsg = application.getResources().getString(R.string.wx_hideLoading_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } else {
            wx_fail res = new wx_fail(application.getResources().getString(R.string.wx_hideLoading_fail));
//            res.errMsg = application.getResources().getString(R.string.wx_hideLoading_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    //隐藏消息提示框
    public  void hideToast(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
          //  toast.cancel();
            Dict res = new Dict();
//            res.errMsg = application.getResources().getString(R.string.wx_hideToast_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //toast.cancel();
            wx_fail res = new wx_fail(application.getResources().getString(R.string.wx_hideToast_fail));
//            res.errMsg = application.getResources().getString(R.string.wx_hideToast_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

  //  private  Toast toast;
    private  LinearLayout the_toast_mask;
ImageView iconImageView(){
    return null;//view.findViewById(R.id.iconImageView);
}
View view;
    public  void showToast(Dict OBJECT) {
        String title = ((JsString) OBJECT.get("title")).THIS;
        String icon = OBJECT.get("icon") != null ? ((JsString) OBJECT.get("icon")).THIS.trim() : "success";
        final String image = OBJECT.get("image") != null ? ((JsString) OBJECT.get("image")).THIS.trim() : null;
        int duration = OBJECT.get("duration") != null ? Onekit_JS.number(OBJECT.get("duration"), 1500, 1500).intValue() : 1500;
        boolean mask = OBJECT.get("mask") != null ? ((JsBoolean) OBJECT.get("mask") ).THIS: false;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        // Handler handler= new Handler(){
        //   @Override
        //  public void handleMessage(Message msg) {
        try {
           //    toast = new Toast(Android.context);
            Toast  toast = Toast.makeText(Android.context, "Toast提示消息", Toast.LENGTH_LONG);
            //toast.setGravity(Gravity.CENTER, 0, 0);
        //       toast.setDuration(Toast.LENGTH_LONG);//duration>=2000?Toast.LENGTH_LONG:Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);//设置对齐方式
          //   view=View.inflate(application, R.layout.onekit_toast, null);

/*
           if(image!=null) {
               iconImageView().setImageBitmap(ASSET.loadImage(image,false));
           }else{
            iconImageView().setImageResource(application.getResources().getIdentifier(String.format("R.drawable.onekit_toast_%s",icon),"drawable",application.getPackageName()));
           }
           */
          //  toast.setView(view);

            toast.show();
            Dict res = new Dict();
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(application.getResources().getString(R.string.wx_showToast_fail));
//            res.errMsg = application.getResources().getString(R.string.wx_showToast_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    public  void showModal(Map OBJECT) {
        String title = OBJECT.get("title") != null ? ((String) OBJECT.get("title")).trim() : null;
        String content = OBJECT.get("content") != null ? ((String) OBJECT.get("content")).trim() : null;
        String showCancel = OBJECT.get("showCancel") != null ? String.valueOf(OBJECT.get("showCancel")).trim() : "true";
        String cancelText = OBJECT.get("cancelText") != null ? ((String) OBJECT.get("cancelText")).trim() : null;
        String cancelColor = OBJECT.get("cancelColor") != null ? ((String) OBJECT.get("cancelColor")).trim() : "#000000";
        String confirmText = OBJECT.get("confirmText") != null ? ((String) OBJECT.get("confirmText")).trim() : null;
        String comfirmColor = OBJECT.get("comfirmColor") != null ? ((String) OBJECT.get("comfirmColor")).trim() : "#3CC51F";
        final function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        final function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        final function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(application);
            // 设置对话框标题
            TextView tv = new TextView(application);
            tv.setText(title);    //内容
            tv.setTextSize(18);//字体大小
            tv.setPadding(0, 40, 0, 0);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(COLOR.parse("#302E35"));//颜色
            builder.setCustomTitle(tv);//不是setTitle()
            // 设置显示的内容
            builder.setMessage(content);
            if (showCancel.equals("false")) {
                builder.setPositiveButton(confirmText, new DialogInterface.OnClickListener() {// 添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件
                        Dict res = new Dict();
                        if (success != null) {
                            success.invoke(res);
                        }
                        if (complete != null) {
                            complete.invoke(res);
                        }
                    }
                });
            } else if (showCancel.equals("true")) {
                builder.setPositiveButton(confirmText, new DialogInterface.OnClickListener() {// 添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件
                        Dict res = new Dict();
//                        res.confirm = true;
//                        res.cancel = false;
//                        res.errMsg = application.getResources().getString(R.string.wx_showModal_success);
                        if (success != null) {
                            success.invoke(res);
                        }
                        if (complete != null) {
                            complete.invoke(res);
                        }
                    }
                });
                builder.setNegativeButton(cancelText, new DialogInterface.OnClickListener() {// 添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {// 响应事件
                        Dict res = new Dict();
//                        res.confirm = false;
//                        res.cancel = true;
//                        res.errMsg = application.getResources().getString(R.string.wx_showModal_success);
                        if (success != null) {
                            success.invoke(res);
                        }
                        if (complete != null) {
                            complete.invoke(res);
                        }
                    }
                });
            }
            builder.show();// 在按键响应事件中显示此对话框
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(application.getResources().getString(R.string.wx_showModal_fail));
//            res.errMsg = application.getResources().getString(R.string.wx_showModal_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }
/*
    private  NDialog nDialog;

    public  void showActionSheet(Map OBJECT) {
        List itemListArry = OBJECT.get("itemList") != null ? (List) OBJECT.get("itemList") : null;
        String itemColor = OBJECT.get("itemColor") != null ? ((String) OBJECT.get("itemColor")).trim() : "#000000";
        final function<wx_showActionSheet> success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        final function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        final function<wx_fail> fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        String[] itemList = new String[itemListArry.size()];
        int i = 0;
        for (JsObject s : itemListArry) {
            itemList[i] = s.toString();
            i++;
        }
        try {
            if (itemList.length <= 6) {
                nDialog = new NDialog(application);
                nDialog.setItems(itemList);
                nDialog.setItemGravity(Gravity.CENTER); // 选择框item位置
                nDialog.setItemColor(COLOR.parse(itemColor)); // 选择框item字体颜色
                nDialog.setItemHeigh(50); // 选择框item高度
                nDialog.setItemSize(16); // 选择框item字体大小
                nDialog.setDividerHeigh(1); // 选择框分割线高度
                nDialog.setAdapter(null);
                nDialog.setDividerColor(COLOR.parse("#c1c1c1")); // 选择框分割线颜色
                nDialog.setHasDivider(true); // 选择框是否要分割线
                nDialog.setOnChoiceListener(new NDialog.OnChoiceListener() {
                    @Override
                    public void onClick(String item, final int which) {
                        wx_showActionSheet res = new wx_showActionSheet();
                        res.tapIndex = which;
                        res.errMsg = application.getResources().getString(R.string.wx_showActionSheet_success);
                        if (success != null) {
                            success.invoke(res);
                        }
                        if (complete != null) {
                            complete.invoke(res);
                        }
                    }
                }).create(NDialog.CHOICE).show();  // NDialog.CHOICE  构建选择弹窗
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail();
            res.errMsg = application.getResources().getString(R.string.wx_showActionSheet_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }
*/
    //是否显示透明蒙层
    public  void addMask(boolean if_add_mask) {
        if (if_add_mask) {
            the_toast_mask = new LinearLayout(application);
            the_toast_mask.setBackgroundColor(Color.GRAY);
            the_toast_mask.getBackground().setAlpha(0);

            WindowManager wmManager = (WindowManager) application.getSystemService(application.WINDOW_SERVICE);
            WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

            DisplayMetrics dm = new DisplayMetrics();
            wmManager.getDefaultDisplay().getMetrics(dm);

            wmParams.format = 1;
            wmParams.flags = 0x0000008;
            wmParams.width = dm.widthPixels;
            wmParams.height = dm.heightPixels;
            wmManager.addView(the_toast_mask, wmParams);  //创建View
        }
    }

    public  void removeMask() {
        //如果已经设定了themask才会调用删除
        if (the_toast_mask != null) {
            WindowManager wmManager = (WindowManager) application.getSystemService(application.WINDOW_SERVICE);
            wmManager.removeView(the_toast_mask);
        } else {
            Log.e("removeMask", "the null value");
        }
    }

}
