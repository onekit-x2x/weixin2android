package cn.onekit.weixin.core.nfc;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

import cn.onekit.js.ArrayBuffer;
import cn.onekit.js.Dict;
import cn.onekit.js.core.function;

public class NfcService extends HostApduService {
    public static function callback;
    public static byte[] data;
    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        if(callback==null){
            return null;
        }
        callback.invoke(new Dict());//1,new ArrayBuffer(commandApdu),1));
        return data;
    }

    @Override
    public void onDeactivated(int reason) {

    }
}
