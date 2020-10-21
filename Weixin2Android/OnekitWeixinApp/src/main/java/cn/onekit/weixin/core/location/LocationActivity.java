package cn.onekit.weixin.core.location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import cn.onekit.thekit.Android;
import cn.onekit.weixin.app.R;

public class LocationActivity extends MapActivity implements TencentLocationListener {

    private Button reques;
    TextView textView1, textView2;
    ImageView imageView, imageView1, imageView2;
    private static final String TAG = "Location";
    private MapView mMapView;
    private static TencentMap mTencentMap;
    private Marker mLocationMarker;
    double latitude;
    double longitude;
    float scale;
    String name;
    String address;
    LatLng latLngLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onekit_activity_location);

        init();
        permissions();
        intview();
        initloading();

        Context context = this;
        TencentLocationListener listener = this;
        TencentLocationRequest request = TencentLocationRequest.create();
        TencentLocationManager locationManager = TencentLocationManager.getInstance(context);
        locationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        locationManager.requestLocationUpdates(request, listener);
        OnClickListener();
    }


    private void init() {
        Bundle bundle = this.getIntent().getExtras();
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");
        scale = bundle.getFloat("scale");
        name = bundle.getString("name");
        address = bundle.getString("address");
    }

    private void initloading() {
        textView1.setText(name);
        textView1.setTextColor(Color.parseColor("black"));
        textView2.setText(address);
        textView2.setTextColor(Color.parseColor("gray"));
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.wxapp_button_loading_1));
    }

    private void intview() {
        reques = findViewById(R.id.request);
        textView1 = findViewById(R.id.Locationimagetext1);
        textView2 = findViewById(R.id.Locationimagetext2);
        imageView = findViewById(R.id.Locationimage);
        imageView1 = findViewById(R.id.Locationimage1);
        imageView2 = findViewById(R.id.Locationimage2);
        mMapView = findViewById(R.id.locationview);
        mTencentMap = mMapView.getMap();
        mTencentMap.setCenter(new LatLng(latitude, longitude));//设置中心点
        mTencentMap.setZoom((int) scale);

    }

    private void OnClickListener() {
        reques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTencentMap.setCenter(latLngLocation);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOptionsMenu();
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Android.context, "功能在完善中", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, Menu.FIRST, 100, "街景" + "                                                             ");
        //腾讯地图包名
        if (checkMapAppsIsExist(Android.context, "com.tencent.map"))
            menu.add(0, Menu.FIRST + 1, 200, "腾讯地图" + "                                                             ");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString().trim()) {
            case "街景":
//                Intent intent = new Intent(cn.onekit.weixin.Map.LocationActivity.this, cn.onekit.weixin.Map.StreetView.class);
                Intent intent = new Intent(LocationActivity.this, null);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
                break;
            case "腾讯地图":
                //分享    ShareSDK
                String TX_HEAD = "qqmap://map/routeplan?type=drive";
                //起点名称
                String TX_FROM = "&from=";
                //起点的经纬度
                String TX_FROMCOORD = "&fromcoord=";
                //终点名称
                String TX_TO = "&to=";
                //终点的经纬度
                String TX_TOCOORD = "&tocoord=";
                /**
                 本参数取决于type参数的取值
                 公交：type=bus，policy有以下取值
                 0：较快捷
                 1：少换乘
                 2：少步行
                 3：不坐地铁
                 驾车：type=drive，policy有以下取值
                 0：较快捷
                 1：无高速
                 2：距离
                 policy的取值缺省为0
                 */
                String TX_END = "&policy=1&referer=myapp";
                String endName = "";
                double endLatitude = 0;
                double endLongitude = 0;
                Intent intent1 = new Intent();
                intent1.setData(Uri.parse(TX_HEAD + TX_FROM + name + TX_FROMCOORD + latitude +
                        "," + longitude + TX_TO + endName + TX_TOCOORD + endLatitude +
                        "," + endLongitude + TX_END));
                startActivity(intent1);

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkMapAppsIsExist(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason) {
        if (error == TencentLocation.ERROR_OK) {
            //    mLocation = location;
            latLngLocation = new LatLng(latitude, longitude);
            mTencentMap.setCenter(latLngLocation);
            if (mLocationMarker == null) {
                mLocationMarker = mTencentMap.addMarker(new MarkerOptions().position(latLngLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marka)));
            } else {
                mLocationMarker.setPosition(latLngLocation);
            }
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
    }

    //腾讯地图动态权限
    private void permissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            if (checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 0);
            }
        }
    }
}
