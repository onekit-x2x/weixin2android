package cn.onekit.weixin.core.wx;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.JsArray;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsNumber;
import cn.onekit.js.JsString;
import cn.onekit.js.JsObject_;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;

public class WxBluetooth extends WxBLE {
    private function _onBluetoothAdapterStateChange;
    private function _onBluetoothDeviceFound;
    JsArray _objects=new JsArray() ;
    protected Map<String,BluetoothDevice> _devices = new HashMap();
    private BluetoothAdapter _bluetoothAdapter;

    public void openBluetoothAdapter(Map obj) {
        function success = obj != null && obj.containsKey("success") ? (function) obj.get("success") : null;
        function fail = obj != null && obj.containsKey("fail") ? (function) obj.get("fail") : null;
        function complete = obj != null && obj.containsKey("complete") ? (function) obj.get("complete") : null;
        //
        final BluetoothManager bluetoothManager =
                (BluetoothManager) Android.context.getSystemService(Context.BLUETOOTH_SERVICE);
        _bluetoothAdapter = bluetoothManager.getAdapter();
        if (_bluetoothAdapter != null) {
            JsObject result = new JsObject();
            if (success != null) {
                success.invoke(result);
            }
            if (complete != null) {
                complete.invoke(result);
            }
        } else {
            wx_fail result = new wx_fail(Android.context.getResources().getString(R.string.wx_openBluetoothAdapter_fail));
//            result.errMsg = ACTIVITY.context.getResources().getString(R.string.wx_openBluetoothAdapter_fail);
            if (fail != null) {
                fail.invoke(result);
            }
            if (complete != null) {
                complete.invoke(result);
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void closeBluetoothAdapter(Map obj) {
        function success = obj!=null && obj.containsKey("success")?(function)obj.get("success"):null;
        function fail = obj!=null && obj.containsKey("fail")?(function)obj.get("fail"):null;
        function complete = obj!=null && obj.containsKey("complete")?(function)obj.get("complete"):null;
        //
        _bluetoothAdapter.disable();
        _bluetoothAdapter=null;
    }

    @SuppressLint("MissingPermission")
    JsObject _device2object(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
        return new JsObject() {{
            put("name", new JsString(device.getName()));
            put("deviceId", new JsString(device.getAddress()));
            put("RSSI", new JsNumber(rssi));
            //  put("advertisData",device);
            // put("advertisServiceUUIDs",new ArrayBuffer(scanRecord));
            //  put("localName",);
            //  put("serviceData",);
        }};
    }
    public void getBluetoothAdapterState(Map obj) {
        function success = obj!=null && obj.containsKey("success")?(function)obj.get("success"):null;
        function fail = obj!=null && obj.containsKey("fail")?(function)obj.get("fail"):null;
        function complete = obj!=null && obj.containsKey("complete")?(function)obj.get("complete"):null;
        //
    }

    public void onBluetoothAdapterStateChange(function callback) {
        _onBluetoothAdapterStateChange=callback;
    }

    @SuppressLint({"NewApi","MissingPermission"})
    public void startBluetoothDevicesDiscovery(Map obj) {
        function success = obj != null && obj.containsKey("success") ? (function) obj.get("success") : null;
        function fail = obj != null && obj.containsKey("fail") ? (function) obj.get("fail") : null;
        function complete = obj != null && obj.containsKey("complete") ? (function) obj.get("complete") : null;
        JsArray services = obj != null && obj.containsKey("services") ? (JsArray) obj.get("services") : null;
        final boolean allowDuplicatesKey = obj != null && obj.containsKey("allowDuplicatesKey") ? (boolean) obj.get("allowDuplicatesKey") : false;
        //
        _objects.clear();
        _scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                BluetoothDevice device = result.getDevice();
                String deviceId = device.getAddress();
                if(!allowDuplicatesKey && _devices.containsKey(deviceId)){
                    return;
                }
                //
                _devices.put(deviceId,device);
                //
                JsObject dvc = _device2object(device, result.getRssi(), result.getScanRecord().getBytes());
                Integer index=null;
                for(int i=0;i<_objects.size();i++){
                    JsObject item = (JsObject)_objects.get(i);
                    if(item.get("deviceId").equals(deviceId)){
                        index=i;
                        break;
                    }
                }
                if(index!=null){
                    _objects.set(index,dvc);
                }else{
                    _objects.add(dvc);
                }

                if(_onBluetoothDeviceFound!=null){
                    _onBluetoothDeviceFound.invoke(new JsObject(){{
                        put("devices",_objects);
                    }});
                }
            }

        };
        BluetoothLeScanner scanner=_bluetoothAdapter.getBluetoothLeScanner();
        if (services != null) {
            List<ScanFilter> filters = new ArrayList();
            int i=0;
            for(JsObject_ service : services ){
                ScanFilter filter =  new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString(((JsString)service).THIS)).build();
                filters.add(filter);
            }
            ScanSettings settings = new ScanSettings.Builder().build();
            scanner.startScan(filters,settings,_scanCallback);
        } else {
            scanner.startScan(_scanCallback);
        }
    }
      ScanCallback _scanCallback;

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void stopBluetoothDevicesDiscovery(Map obj) {
        function success = obj!=null && obj.containsKey("success")?(function)obj.get("success"):null;
        function fail = obj!=null && obj.containsKey("fail")?(function)obj.get("fail"):null;
        function complete = obj!=null && obj.containsKey("complete")?(function)obj.get("complete"):null;
        //
        _bluetoothAdapter.getBluetoothLeScanner().stopScan(_scanCallback);
        if(success!=null){success.invoke(new JsObject());}
    }

    public void getConnectedBluetoothDevices(Map obj) {
        function success = obj!=null && obj.containsKey("success")?(function)obj.get("success"):null;
        function fail = obj!=null && obj.containsKey("fail")?(function)obj.get("fail"):null;
        function complete = obj!=null && obj.containsKey("complete")?(function)obj.get("complete"):null;
        //
    }

    public void onBluetoothDeviceFound(function callback) {
        _onBluetoothDeviceFound = callback;
    }

    public void getBluetoothDevices(Map obj) {
        function success = obj!=null && obj.containsKey("success")?(function)obj.get("success"):null;
        function fail = obj!=null && obj.containsKey("fail")?(function)obj.get("fail"):null;
        function complete = obj!=null && obj.containsKey("complete")?(function)obj.get("complete"):null;
        //
        if(success!=null){
            success.invoke(new JsObject(){{
                put("devices",_objects);
            }});
        }
    }
}
