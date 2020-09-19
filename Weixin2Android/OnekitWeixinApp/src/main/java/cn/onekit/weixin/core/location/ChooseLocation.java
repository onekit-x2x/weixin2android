package cn.onekit.weixin.core.location;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import java.io.IOException;

import cn.onekit.js.Array;
import cn.onekit.js.Dict;
import cn.onekit.js.JSON;
import cn.onekit.js.JsNumber;
import cn.onekit.js.JsString;
import cn.onekit.weixin.app.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChooseLocation extends Activity implements
        View.OnTouchListener {

    private final static int chooselocation = 20000;
    private TencentLocation mLocation;
    private Array locations;
    private int flag = 0;
    private ListView listView;
    private String keyword = null;
    private MapView mMapView;
    private static TencentMap mTencentMap;
    private ImageView imageView1, imageView2;
    private String latitude;
    private String longitude;
    private TextView textView;
    private String title;
    private String address;
    private Double lat;
    private Double lng;

    private LocationHelper mLocationHelper;
    private final android.location.Location mCenter = new android.location.Location("");

    private Marker mLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onekit_chooselocation);
        mLocationHelper = new LocationHelper(ChooseLocation.this);
        permissions();
        intview();
        doMyLoc();
        OnClickListener();
    }

    private void doMyLoc() {
        if (mLocationHelper.getLastLocation() != null) {
            animateTo(mLocationHelper.getLastLocation()); // 已有最新位置
        } else if (mLocationHelper.isStarted()) {
            // Log.e("ddddd", "正在定位");
        } else {
            //  Log.e("sssssss", "开始定位");
            mLocationHelper.start(new Runnable() {
                public void run() {
                    animateTo(mLocationHelper.getLastLocation());
                }
            });
        }
    }

    private void animateTo(TencentLocation location) {
        if (location == null) {
            return;
        }
        //将地图位置移到中心坐标
        mMapView.getController().animateTo(Utils.of(location));
        // 修改 mapview 中心点
        mMapView.getController().setCenter(Utils.of(location));
        // 注意一定要更新当前位置 mCenter
        updatePosition();
        mLocation = location;

        Get();
    }

    private void updatePosition() {
        double lat = mTencentMap.getMapCenter().getLatitude();
        double lng = mTencentMap.getMapCenter().getLongitude();
        mCenter.setLatitude(lat);
        mCenter.setLongitude(lng);

    }


    private void intview() {
        textView = findViewById(R.id.chooselocation_textview);
        imageView2 = findViewById(R.id.chooselocation_imageview2);
        imageView1 = findViewById(R.id.chooselocation_imageview1);
        listView = findViewById(R.id.chooselocation_listview);
        mMapView = findViewById(R.id.chooselocation_mapview);
        mTencentMap = mMapView.getMap();
        mMapView.setOnTouchListener(this);
        mTencentMap.setZoom(16);
        updatePosition();
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            updatePosition();
            onTouchGet(mTencentMap.getMapCenter().getLatitude(), mTencentMap.getMapCenter().getLongitude());
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        mMapView.onStop();
        super.onStop();
    }


    private void OnClickListener() {
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseLocation.this, Locationsearch.class);
                intent.putExtra("latitude", mLocation.getLatitude());
                intent.putExtra("longitude", mLocation.getLongitude());
                ChooseLocation.this.startActivityForResult(intent, chooselocation);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("title", title == null ? mLocation.getName() : title);
                intent.putExtra("address", address == null ? mLocation.getAddress() : address);
                intent.putExtra("lat", lat == null ? String.valueOf(mLocation.getLatitude()) : lat);
                intent.putExtra("lng", lng == null ? String.valueOf(mLocation.getLongitude()) : lng);
                setResult(1000, intent);
                finish();

            }
        });
    }

    private void Get() {// latitude等于空时为定位自己的位置 不等于空时为 搜索定位
        String format = "http://apis.map.qq.com/ws/place/v1/search?boundary=nearby(%s,%s,%s)%s&page_index=1&page_size=10&orderby=_distance&key=%s";
        String url = String.format(format, latitude != null ? latitude : mLocation.getLatitude(), longitude != null ? longitude : mLocation.getLongitude(), 4000, keyword != null ? "&keyword=" + keyword : "", "3EBBZ-EZYK6-ASKS2-EVXMH-OXEAS-U3FVZ");
        new LoadOkHttpClientTask().execute(url);
    }

    private void onTouchGet(double lat, double log) {//手指移动定位
        String format = "http://apis.map.qq.com/ws/place/v1/search?boundary=nearby(%s,%s,%s)%s&page_index=1&page_size=10&orderby=_distance&key=%s";
        String url = String.format(format, lat, log, 4000, keyword != null ? "&keyword=" + keyword : "", "3EBBZ-EZYK6-ASKS2-EVXMH-OXEAS-U3FVZ");
        new LoadOkHttpClientTask().execute(url);
    }

    class LoadOkHttpClientTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {

            Dict json = (Dict) JSON.parse(s);
            locations = (Array) json.get("data");
            listView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return locations.size();
                }

                @Override
                public Object getItem(int i) {
                    return null;
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    ViewHolder holder = null;
                    if (view == null) {
                        view = LayoutInflater.from(ChooseLocation.this).inflate(
                                R.layout.onekit_chooselocation_list_item, null);
                        holder = new ViewHolder(view);
                        view.setTag(holder);
                    } else {
                        holder = (ViewHolder) view.getTag();
                    }
                    Dict location = (Dict) locations.get(i);
                    holder.text_name.setText(((JsString)location.get("title")).THIS);
                    holder.text_address.setText(((JsString) location.get("address")).THIS);
                    holder.image.setVisibility(View.GONE);
                    return view;
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    ViewHolder holder = new ViewHolder(view);
                    switch (flag) {
                        case 0:
                            holder.image.setVisibility(View.VISIBLE);
                            flag = 1;
                            break;
                        case 1:
                            holder.image.setVisibility(View.GONE);
                            flag = 0;
                            break;
                    }
                    Dict location = (Dict) locations.get(i);
                    title = ((JsString) location.get("title")).THIS;
                    address = ((JsString) location.get("address")).THIS;
                    Dict s = (Dict) location.get("location");
                    lat = ((JsNumber) s.get("lat")).THIS.doubleValue();
                    lng = ((JsNumber) s.get("lng")).THIS.doubleValue();

                    LatLng latLngLocation = new LatLng(Double.valueOf(lat).doubleValue(), Double.valueOf(lng).doubleValue());
                    if (mLocationMarker == null) {
                        mLocationMarker = mTencentMap.addMarker(new MarkerOptions().position(latLngLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marka)));
                    } else {
                        mLocationMarker.setPosition(latLngLocation);
                    }


                }
            });

        }
    }

    static class ViewHolder {
        private TextView text_name;
        private TextView text_address;
        private ImageView image;

        public ViewHolder(View v) {
            text_name = v.findViewById(R.id.text_name);
            text_address = v.findViewById(R.id.text_address);
            image = v.findViewById(R.id.image);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case chooselocation: {
                if (resultCode == 888888) {
                    latitude = data.getStringExtra("lat");
                    longitude = data.getStringExtra("lng");
                    if (latitude != null) {
                        double la = Double.valueOf(latitude).doubleValue();
                        double lo = Double.valueOf(longitude).doubleValue();
                        mMapView.getController().animateTo(Utils.of(la, lo));
                        mMapView.getController().setCenter(Utils.of(la, lo));
                        Get();//获取搜索地址的经纬度
                    }
                }
            }
            default:
                break;
        }
    }
}
