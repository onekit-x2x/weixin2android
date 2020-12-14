package cn.onekit.weixin.core.wx;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.onekit.thekit.Android;
import cn.onekit.js.JsArray;
import cn.onekit.js.ArrayBuffer;
import cn.onekit.js.JsObject;
import cn.onekit.js.JsString;
import cn.onekit.js.core.function;
import cn.onekit.weixin.WX;

public class WxBLE extends WxBattery {
    BluetoothGattCallback _callback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.e("========",status+" "+newState);
            super.onConnectionStateChange(gatt,status,newState);
            String deviceId = gatt.getDevice().getAddress();
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    _gatts.put(deviceId,gatt);
                    if(_createBLEConnections.containsKey(deviceId)){
                        _createBLEConnections.get(deviceId).invoke(new JsObject());
                    }
                    break;
                default:
                    break;
            }
            if(_onBLEConnectionStateChange!=null){
                _onBLEConnectionStateChange.invoke(new JsObject(){{

                }});
            }
        }
        @Override
        public void onCharacteristicChanged(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            if (_onBLECharacteristicValueChange != null) {
                _onBLECharacteristicValueChange.invoke(new JsObject(){{
                    put("deviceId",new JsString(gatt.getDevice().getAddress()));
                    put("serviceId",new JsString(characteristic.getService().getUuid().toString()));
                    put("characteristicId",new JsString(characteristic.getUuid().toString()));
                    put("value",new ArrayBuffer(characteristic.getValue()));
                }});
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (_getBLEDeviceServices != null) {
                List<BluetoothGattService> services = gatt.getServices();
                final JsArray result = new JsArray();
                for (BluetoothGattService service : services) {
                    JsObject svc = _service2object(service);
                    result.add(svc);
                }

                _getBLEDeviceServices.invoke(new JsObject() {{
                    put("services", result);
                }});

            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            if(_writeBLECharacteristicValue!=null){
                _writeBLECharacteristicValue.invoke(new JsObject(){{
                }});
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            if(_readBLECharacteristicValue!=null){
                _readBLECharacteristicValue.invoke(new JsObject(){{
                }});
            }
        }
    };
    private Map<String, function> _createBLEConnections=new HashMap();
    private function _onBLECharacteristicValueChange;
    private function _onBLEConnectionStateChange;
    private Map<String, BluetoothGatt> _gatts=new HashMap();
    private function _writeBLECharacteristicValue;
    private function _readBLECharacteristicValue;
    private function _getBLEDeviceServices;

    JsObject _service2object(final BluetoothGattService service) {
        return new JsObject() {{
            put("uuid",new JsString(service.getUuid().toString().toUpperCase()));
        }};
    }
    private JsObject _characteristic2object(final BluetoothGattCharacteristic characteristic) {
        return new JsObject() {{
            put("uuid",new JsString(characteristic.getUuid().toString().toUpperCase()));
        }};
    }
    public void onBLECharacteristicValueChange(function callback) {
        _onBLECharacteristicValueChange=callback;
    }

    public void onBLEConnectionStateChange(function callback) {
        _onBLEConnectionStateChange=callback;
    }

    public void notifyBLECharacteristicValueChange(Map obj) {
        function success = obj!=null && obj.containsKey("success")?(function)obj.get("success"):null;
        function fail = obj!=null && obj.containsKey("fail")?(function)obj.get("fail"):null;
        function complete = obj!=null && obj.containsKey("complete")?(function)obj.get("complete"):null;
        String deviceId = obj != null && obj.containsKey("deviceId") ? (String) obj.get("deviceId") : null;
        String serviceId = obj != null && obj.containsKey("serviceId") ? (String) obj.get("serviceId") : null;
        String characteristicId = obj != null && obj.containsKey("characteristicId") ? (String) obj.get("characteristicId") : null;
        boolean state = (boolean) obj.get("state");
        //
        BluetoothGatt gatt = _gatts.get(deviceId);
        BluetoothGattService service = gatt.getService(UUID.fromString(serviceId));
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(characteristicId));
        gatt.setCharacteristicNotification(characteristic,state);
        if(state){
            for(BluetoothGattDescriptor dp: characteristic.getDescriptors()){
                if (dp != null) {
                    if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
                        dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    } else if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) {
                        dp.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
                    }
                    gatt.writeDescriptor(dp);
                }
            }
        }
        if(success!=null){
            success.invoke(new JsObject(){{

            }});
        }
    }

    public void writeBLECharacteristicValue(Map obj) {
        function success = obj!=null && obj.containsKey("success")?(function)obj.get("success"):null;
        function fail = obj!=null && obj.containsKey("fail")?(function)obj.get("fail"):null;
        function complete = obj!=null && obj.containsKey("complete")?(function)obj.get("complete"):null;
        String deviceId = obj != null && obj.containsKey("deviceId") ? (String) obj.get("deviceId") : null;
        String serviceId = obj != null && obj.containsKey("serviceId") ? (String) obj.get("serviceId") : null;
        String characteristicId = obj != null && obj.containsKey("characteristicId") ? (String) obj.get("characteristicId") : null;
        ArrayBuffer value = obj != null && obj.containsKey("value") ? (ArrayBuffer) obj.get("value") : null;
        //
        _writeBLECharacteristicValue = success;
        BluetoothGatt gatt = _gatts.get(deviceId);
        BluetoothGattService service = gatt.getService(UUID.fromString(serviceId));
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(characteristicId));
        characteristic.setValue(value._data);
        gatt.writeCharacteristic(characteristic);
    }

    public void readBLECharacteristicValue(Map obj) {
        function success = obj != null && obj.containsKey("success") ? (function) obj.get("success") : null;
        function fail = obj != null && obj.containsKey("fail") ? (function) obj.get("fail") : null;
        function complete = obj != null && obj.containsKey("complete") ? (function) obj.get("complete") : null;
        String deviceId = obj != null && obj.containsKey("deviceId") ? (String) obj.get("deviceId") : null;
        String serviceId = obj != null && obj.containsKey("serviceId") ? (String) obj.get("serviceId") : null;
        String characteristicId = obj != null && obj.containsKey("characteristicId") ? (String) obj.get("characteristicId") : null;
        //
        _readBLECharacteristicValue = success;
        BluetoothGatt gatt = _gatts.get(deviceId);
        BluetoothGattService service = gatt.getService(UUID.fromString(serviceId));
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(characteristicId));
        gatt.readCharacteristic(characteristic);
    }


    public void getBLEDeviceCharacteristics(Map obj) {
        function success = obj!=null && obj.containsKey("success")?(function)obj.get("success"):null;
        function fail = obj!=null && obj.containsKey("fail")?(function)obj.get("fail"):null;
        function complete = obj!=null && obj.containsKey("complete")?(function)obj.get("complete"):null;
        String deviceId = obj != null && obj.containsKey("deviceId") ? (String) obj.get("deviceId") : null;
        String serviceId = obj != null && obj.containsKey("serviceId") ? (String) obj.get("serviceId") : null;
        //
        BluetoothGatt gatt = _gatts.get(deviceId);
        BluetoothGattService service = gatt.getService(UUID.fromString(serviceId));
        List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
        final JsArray result = new JsArray();
        for (BluetoothGattCharacteristic characteristic : characteristics) {
            JsObject chr = _characteristic2object(characteristic);
            result.add(chr);
        }
        if (success != null) {
            success.invoke(new JsObject() {{
                put("characteristics", result);
            }});
        }
    }

    public void getBLEDeviceServices(Map obj) {
        function success = obj != null && obj.containsKey("success") ? (function) obj.get("success") : null;
        function fail = obj != null && obj.containsKey("fail") ? (function) obj.get("fail") : null;
        function complete = obj != null && obj.containsKey("complete") ? (function) obj.get("complete") : null;
        String deviceId = obj != null && obj.containsKey("deviceId") ? (String) obj.get("deviceId") : null;
        //
        _getBLEDeviceServices = success;
        BluetoothDevice device =((WX)this)._devices.get(deviceId);
        BluetoothGatt gatt = _gatts.get(deviceId);
        gatt.discoverServices();
    }

    public void createBLEConnection(Map obj) {
        function success = obj!=null && obj.containsKey("success")?(function)obj.get("success"):null;
        function fail = obj!=null && obj.containsKey("fail")?(function)obj.get("fail"):null;
        function complete = obj!=null && obj.containsKey("complete")?(function)obj.get("complete"):null;
        String deviceId = obj!=null && obj.containsKey("deviceId")?(String)obj.get("deviceId"):null;
        //
        BluetoothDevice device = ((WX)this)._devices.get(deviceId);
        _createBLEConnections.put(deviceId,success);
        device.connectGatt(Android.context, false,_callback);
    }

    public void closeBLEConnection(Map obj) {
        function success = obj != null && obj.containsKey("success") ? (function) obj.get("success") : null;
        function fail = obj != null && obj.containsKey("fail") ? (function) obj.get("fail") : null;
        function complete = obj != null && obj.containsKey("complete") ? (function) obj.get("complete") : null;
        String deviceId = obj != null && obj.containsKey("deviceId") ? (String) obj.get("deviceId") : null;
        //
        BluetoothGatt gatt = _gatts.get(deviceId);
        gatt.disconnect();
        if (success != null) {
            success.invoke(new JsObject());
        }
    }
    public void setBLEMTU(Map OBJECT){

    }
    public void offBLEConnectionStateChange(function callback){

    }
    public void offBLECharacteristicValueChange(function callback){

    }
    public void makeBluetoothPair(Map OBJECT){

    }
    public void getBLEDeviceRSSI(Map OBJECT){

    }


}

