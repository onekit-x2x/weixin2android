package cn.onekit.weixin.core.wx;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsString;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxPhoneContact extends WxPhone {
    private TextView textView, tv;

    private String lastName;//姓氏
    private String middleName;//中间名
    private String firstName;//姓名

    private String mobilePhoneNumber;//手机号
    private String organization;//公司
    private String email;//电子邮件
    private String hostNumber;//公司电话
    private String homePhoneNumber;//住宅电话
    private String remark;//备注
    private String nickName;//昵称
    private String weChatNumber;//微信号
    private String title;//职位


    private String addressCountry;//联系地址国家
    private String addressState;//联系地址省份
    private String addressCity;//联系地址城市
    private String addressStreet;//联系地址街道
    private String addressPostalCode;//联系地址邮政编码

    private String workAddressCountry;//工作地址国家
    private String workAddressState;//工作地址省份
    private String workAddressCity;//工作地址城市
    private String workAddressStreet;//工作地址街道
    private String workAddressPostalCode;//工作地址邮政编码

    private String homeAddressCountry;//住宅地址国家
    private String homeAddressState;//住宅地址省份
    private String homeAddressCity;//住宅地址城市
    private String homeAddressStreet;//住宅地址街道
    private String homeAddressPostalCode;//住宅地址邮政编码

    private String url; //网站

    public void addPhoneContact(Map Dict) {
        Jurisdiction();
        init(Dict);
        //昵称
        nickName = Dict.get("nickName") != null ? (String) Dict.get("nickName") : null;
        //姓氏
        lastName = Dict.get("lastName") != null ? (String) Dict.get("lastName") : null;
        //中间名
        middleName = Dict.get("middleName") != null ? (String) Dict.get("middleName") : null;
        //名字
        firstName = Dict.get("firstName") != null ? (String) Dict.get("firstName") : null;
        //备注
        remark = Dict.get("remark") != null ? (String) Dict.get("remark") : null;
        //手机号
        mobilePhoneNumber = Dict.get("mobilePhoneNumber") != null ? (String) Dict.get("mobilePhoneNumber") : null;
        //公司电话
        hostNumber = Dict.get("hostNumber") != null ? (String) Dict.get("hostNumber") : null;
        //住宅电话
        homePhoneNumber = Dict.get("homePhoneNumber") != null ? (String) Dict.get("homePhoneNumber") : null;
        //住宅地址国家
        homeAddressCountry = Dict.get("homeAddressCountry") != null ? (String) Dict.get("homeAddressCountry") : null;
        //住宅地址省份
        homeAddressState = Dict.get("homeAddressState") != null ? (String) Dict.get("homeAddressState") : null;
        //住宅地址城市
        homeAddressCity = Dict.get("homeAddressCity") != null ? (String) Dict.get("homeAddressCity") : null;
        //住宅地址街道
        homeAddressStreet = Dict.get("homeAddressStreet") != null ? (String) Dict.get("homeAddressStreet") : null;
        //住宅地址邮政编码
        homeAddressPostalCode = Dict.get("homeAddressPostalCode") != null ? (String) Dict.get("homeAddressPostalCode") : null;
        //公司
        organization = Dict.get("organization") != null ? (String) Dict.get("organization") : null;
        //电子邮件
        email = Dict.get("email") != null ? (String) Dict.get("email") : null;
        //职位
        title = Dict.get("title") != null ? (String) Dict.get("title") : null;


        //微信号
        weChatNumber = Dict.get("weChatNumber") != null ? (String) Dict.get("weChatNumber") : null;
        //工作地址国家
        workAddressCountry = Dict.get("workAddressCountry") != null ? (String) Dict.get("workAddressCountry") : null;
        //工作地址省份
        workAddressState = Dict.get("workAddressState") != null ? (String) Dict.get("workAddressState") : null;
        //工作地址城市
        workAddressCity = Dict.get("workAddressCity") != null ? (String) Dict.get("workAddressCity") : null;
        //工作地址街道
        workAddressStreet = Dict.get("workAddressStreet") != null ? (String) Dict.get("workAddressStreet") : null;
        //工作地址邮政编码
        workAddressPostalCode = Dict.get("workAddressPostalCode") != null ? (String) Dict.get("workAddressPostalCode") : null;
        //头像本地文件路径
        String photoFilePath = Dict.get("photoFilePath") != null ? (String) Dict.get("photoFilePath") : null;
        //联系地址国家
        addressCountry = Dict.get("addressCountry") != null ? (String) Dict.get("addressCountry") : null;
        //联系地址省份
        addressState = Dict.get("addressState") != null ? (String) Dict.get("addressState") : null;
        //联系地址城市
        addressCity = Dict.get("addressCity") != null ? (String) Dict.get("addressCity") : null;
        //联系地址街道
        addressStreet = Dict.get("addressStreet") != null ? (String) Dict.get("addressStreet") : null;
        //联系地址邮政编码
        addressPostalCode = Dict.get("addressPostalCode") != null ? (String) Dict.get("addressPostalCode") : null;
        //工作传真
        String workFaxNumber = Dict.get("workFaxNumber") != null ? (String) Dict.get("workFaxNumber") : null;
        //工作电话
        String workPhoneNumber = Dict.get("workPhoneNumber") != null ? (String) Dict.get("workPhoneNumber") : null;
        //网站
        url = Dict.get("url") != null ? (String) Dict.get("url") : null;
        //住宅传真
        String homeFaxNumber = Dict.get("homeFaxNumber") != null ? (String) Dict.get("homeFaxNumber") : null;


        final function success = Dict.get("success") != null ? (function) Dict.get("success") : null;
        final function complete = Dict.get("complete") != null ? (function) Dict.get("complete") : null;
        final function fail = Dict.get("fail") != null ? (function) Dict.get("fail") : null;


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(Intent.ACTION_INSERT,
                        Uri.withAppendedPath(Uri.parse("content://com.android.contacts"),
                                "contacts"));
                addIntent.setType("vnd.android.cursor.dir/person");
                addIntent.setType("vnd.android.cursor.dir/contact");
                addIntent.setType("vnd.android.cursor.dir/raw_contact");
                addIntent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, Phone.TYPE_WORK);
                addIntent.putExtra(ContactsContract.Intents.Insert.NAME, lastName + middleName + firstName);//姓氏+中间名+名字
                addIntent.putExtra(ContactsContract.Intents.Insert.PHONE, mobilePhoneNumber);//手机号
                addIntent.putExtra(ContactsContract.Intents.Insert.COMPANY, organization);//公司
                addIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);//电子邮件
                addIntent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, hostNumber);//公司电话
                addIntent.putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE, homePhoneNumber);//住宅电话
                addIntent.putExtra(ContactsContract.Intents.Insert.POSTAL, homeAddressCountry + homeAddressState + homeAddressCity + homeAddressStreet + homeAddressPostalCode);//住宅
                addIntent.putExtra(ContactsContract.Intents.Insert.NOTES, remark);//备注
                addIntent.putExtra(ContactsContract.Intents.Insert.PHONETIC_NAME, nickName);//昵称
                addIntent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, title);//职位

                try {
                    Android.context.startActivity(addIntent);
                    JsObject res = new JsObject();
//                    res.errMsg = Android.context.getResources().getString(R.string.wx_addPhoneContact_success);
                    success.invoke(res);
                    complete.invoke(res);

                } catch (final Exception e) {
                    e.printStackTrace();
                    wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_addPhoneContact_fail));
//                    res.errMsg = Android.context.getResources().getString(R.string.wx_addPhoneContact_fail);
                    fail.invoke(res);
                    complete.invoke(res);

                }

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Contacts.People.CONTENT_URI);
                Android.context.startActivity(intent);
            }
        });
    }

    public void Jurisdiction() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Android.context, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) Android.context, new String[]{Manifest.permission.READ_CONTACTS},
                        0);//申请权限
            } else {//拥有当前权限
            }
        }
    }

    public void init(final Map Dict) {
        LinearLayout the_radius = new LinearLayout(Android.context);
        the_radius.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        the_radius.setLayoutParams(param);

        textView = new TextView(Android.context);
        textView.setText("创建新联系人");
        textView.setTextSize(20);
        textView.setPadding(20, 15, 0, 10);
        the_radius.addView(textView);

        tv = new TextView(Android.context);
        tv.setTextSize(20);
        tv.setPadding(20, 10, 0, 15);
        tv.setText("添加到现有的联系人");
        the_radius.addView(tv);

        AlertDialog.Builder builder = new AlertDialog.Builder(Android.context);
        builder.setView(the_radius);
        builder.show();
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                function fail = (function) Dict.get("fail");
                fail.invoke(new JsObject() {{
                    put("fail",  new JsString("用户取消"));
                }});
                if (Dict.containsKey("complete")) {
                    function complete = (function) Dict.get("complete");
                    complete.invoke(new JsObject() {{
                        put("complete", new JsString("complete"));
                    }});
                }
            }
        });
    }
}

