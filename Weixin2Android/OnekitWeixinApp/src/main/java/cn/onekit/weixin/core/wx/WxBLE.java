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

import cn.onekit.Android;
import cn.onekit.js.Array;
import cn.onekit.js.ArrayBuffer;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsString;
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
                        _createBLEConnections.get(deviceId).invoke(new Dict());
                    }
                    break;
                default:
                    break;
            }
            if(_onBLEConnectionStateChange!=null){
                _onBLEConnectionStateChange.invoke(new Dict(){{

                }});
            }
        }
        @Override
        public void onCharacteristicChanged(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            if (_onBLECharacteristicValueChange != null) {
                _onBLECharacteristicValueChange.invoke(new Dict(){{
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
                final Array result = new Array();
                for (BluetoothGattService service : services) {
                    Dict svc = _service2object(service);
                    result.add(svc);
                }

                _getBLEDeviceServices.invoke(new Dict() {{
                    put("services", result);
                }});

            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            if(_writeBLECharacteristicValue!=null){
                _writeBLECharacteristicValue.invoke(new Dict(){{
                }});
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            if(_readBLECharacteristicValue!=null){
                _readBLECharacteristicValue.invoke(new Dict(){{
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

    Dict _service2object(final BluetoothGattService service) {
        return new Dict() {{
            put("uuid",new JsString(service.getUuid().toString().toUpperCase()));
        }};
    }
    private Dict _characteristic2object(final BluetoothGattCharacteristic characteristic) {
        return new Dict() {{
            put("uuid",new JsString(characteristic.getUuid().toString().toUpperCase()));
        }};
    }
    public void onBLECharacteristicValueChange(function callback) {
        _onBLECharacteristicValueChange=callback;
    }

    public void onBLEConnectionStateChange(function callback) {
        _onBLEConnectionStateChange=callback;
    }

    public void notifyBLECharacteristicValueChange(Map object) {
        function success = object!=null && object.containsKey("success")?(function)object.get("success"):null;
        function fail = object!=null && object.containsKey("fail")?(function)object.get("fail"):null;
        function complete = object!=null && object.containsKey("complete")?(function)object.get("complete"):null;
        String deviceId = object != null && object.containsKey("deviceId") ? (String) object.get("deviceId") : null;
        String serviceId = object != null && object.containsKey("serviceId") ? (String) object.get("serviceId") : null;
        String characteristicId = object != null && object.containsKey("characteristicId") ? (String) object.get("characteristicId") : null;
        boolean state = (boolean) object.get("state");
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
            success.invoke(new Dict(){{

            }});
        }
    }

    public void writeBLECharacteristicValue(Map object) {
        function success = object!=null && object.containsKey("success")?(function)object.get("success"):null;
        function fail = object!=null && object.containsKey("fail")?(function)object.get("fail"):null;
        function complete = object!=null && object.containsKey("complete")?(function)object.get("complete"):null;
        String deviceId = object != null && object.containsKey("deviceId") ? (String) object.get("deviceId") : null;
        String serviceId = object != null && object.containsKey("serviceId") ? (String) object.get("serviceId") : null;
        String characteristicId = object != null && object.containsKey("characteristicId") ? (String) object.get("characteristicId") : null;
        ArrayBuffer value = object != null && object.containsKey("value") ? (ArrayBuffer) object.get("value") : null;
        //
        _writeBLECharacteristicValue = success;
        BluetoothGatt gatt = _gatts.get(deviceId);
        BluetoothGattService service = gatt.getService(UUID.fromString(serviceId));
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(characteristicId));
        characteristic.setValue(value._data);
        gatt.writeCharacteristic(characteristic);
    }

    public void readBLECharacteristicValue(Map object) {
        function success = object != null && object.containsKey("success") ? (function) object.get("success") : null;
        function fail = object != null && object.containsKey("fail") ? (function) object.get("fail") : null;
        function complete = object != null && object.containsKey("complete") ? (function) object.get("complete") : null;
        String deviceId = object != null && object.containsKey("deviceId") ? (String) object.get("deviceId") : null;
        String serviceId = object != null && object.containsKey("serviceId") ? (String) object.get("serviceId") : null;
        String characteristicId = object != null && object.containsKey("characteristicId") ? (String) object.get("characteristicId") : null;
        //
        _readBLECharacteristicValue = success;
        BluetoothGatt gatt = _gatts.get(deviceId);
        BluetoothGattService service = gatt.getService(UUID.fromString(serviceId));
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(characteristicId));
        gatt.readCharacteristic(characteristic);
    }


    public void getBLEDeviceCharacteristics(Map object) {
        function success = object!=null && object.containsKey("success")?(function)object.get("success"):null;
        function fail = object!=null && object.containsKey("fail")?(function)object.get("fail"):null;
        function complete = object!=null && object.containsKey("complete")?(function)object.get("complete"):null;
        String deviceId = object != null && object.containsKey("deviceId") ? (String) object.get("deviceId") : null;
        String serviceId = object != null && object.containsKey("serviceId") ? (String) object.get("serviceId") : null;
        //
        BluetoothGatt gatt = _gatts.get(deviceId);
        BluetoothGattService service = gatt.getService(UUID.fromString(serviceId));
        List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
        final Array result = new Array();
        for (BluetoothGattCharacteristic characteristic : characteristics) {
            Dict chr = _characteristic2object(characteristic);
            result.add(chr);
        }
        if (success != null) {
            success.invoke(new Dict() {{
                put("characteristics", result);
            }});
        }
    }

    public void getBLEDeviceServices(Map object) {
        function success = object != null && object.containsKey("success") ? (function) object.get("success") : null;
        function fail = object != null && object.containsKey("fail") ? (function) object.get("fail") : null;
        function complete = object != null && object.containsKey("complete") ? (function) object.get("complete") : null;
        String deviceId = object != null && object.containsKey("deviceId") ? (String) object.get("deviceId") : null;
        //
        _getBLEDeviceServices = success;
        BluetoothDevice device =((WX)this)._devices.get(deviceId);
        BluetoothGatt gatt = _gatts.get(deviceId);
        gatt.discoverServices();
    }

    public void createBLEConnection(Map object) {
        function success = object!=null && object.containsKey("success")?(function)object.get("success"):null;
        function fail = object!=null && object.containsKey("fail")?(function)object.get("fail"):null;
        function complete = object!=null && object.containsKey("complete")?(function)object.get("complete"):null;
        String deviceId = object!=null && object.containsKey("deviceId")?(String)object.get("deviceId"):null;
        //
        BluetoothDevice device = ((WX)this)._devices.get(deviceId);
        _createBLEConnections.put(deviceId,success);
        device.connectGatt(Android.context, false,_callback);
    }

    public void closeBLEConnection(Map object) {
        function success = object != null && object.containsKey("success") ? (function) object.get("success") : null;
        function fail = object != null && object.containsKey("fail") ? (function) object.get("fail") : null;
        function complete = object != null && object.containsKey("complete") ? (function) object.get("complete") : null;
        String deviceId = object != null && object.containsKey("deviceId") ? (String) object.get("deviceId") : null;
        //
        BluetoothGatt gatt = _gatts.get(deviceId);
        gatt.disconnect();
        if (success != null) {
            success.invoke(new Dict());
        }
    }
}

