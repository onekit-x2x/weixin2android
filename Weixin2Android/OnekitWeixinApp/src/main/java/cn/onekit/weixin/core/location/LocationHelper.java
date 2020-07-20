package cn.onekit.weixin.core.location;

import android.content.Context;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

/**
 * Created by lenovo on 2017/9/13.
 */

public class LocationHelper implements TencentLocationListener {

    private Context mContext;
    private TencentLocation mLastLocation;
    private int mLastError;
    private boolean mStarted;

    private Runnable mTmp;

    public LocationHelper(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason) {
        mLastError = error;
      //  Log.e("-----",location.getAddress());
        mLastLocation = location;
        if (mTmp != null) {
            mTmp.run();
        }

        // 自动停止
        stop();
    }

    @Override
    public void onStatusUpdate(String arg0, int arg1, String arg2) {
        // ignore
    }

    public void start(Runnable r) {
        if (!mStarted) {
            mStarted = true;
            mTmp = r;
            TencentLocationRequest request = TencentLocationRequest.create()
                    .setInterval(5000)
                    .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);
            TencentLocationManager locationManager = TencentLocationManager.getInstance(mContext);
            locationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
            locationManager.requestLocationUpdates(request, this);

//            TencentLocationManager.getInstance(mContext)
//                    .requestLocationUpdates(request, this);
        }
    }

    public void stop() {
        if (mStarted) {
            TencentLocationManager.getInstance(mContext).removeUpdates(this);
            mStarted = false;
            mTmp = null;
        }
    }

    public boolean isStarted() {
        return mStarted;
    }

    public TencentLocation getLastLocation() {
        if (mLastError == TencentLocation.ERROR_OK) {
            return mLastLocation;
        }
        return null;
    }
}
