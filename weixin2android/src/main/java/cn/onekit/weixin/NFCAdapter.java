package cn.onekit.weixin;



import java.util.Map;

import cn.onekit.js.core.function;

public class NFCAdapter {
    public enum Tech{
        ndef,
        nfcA,
        nfcB,
        isoDep,
        nfcF,
        nfcV,
        mifareClassic,
        mifareUltralight

    }
    public IsoDep getIsoDep(){
        return null;
    }
    public MifareClassic getMifareClassic(){
        return null;
    }
    public MifareUltralight getMifareUltralight(){
        return null;
    }
    public Ndef getNdef(){
        return null;
    }
    public NfcA getNfcA(){
        return null;
    }
    public NfcB getNfcB(){
        return null;
    }
    public NfcF getNfcF(){
        return null;
    }
    public NfcV getNfcV(){
        return null;
    }
    public void offDiscovered(function callback){

    }
    public void onDiscovered(function callback){

    }
    public void startDiscovery(function callback){

    }
    public void stopDiscovery(function callback){

    }



    private class IsoDep {
        public void close(Map OBJECT){

        }
        public void getHistoricalBytes(Map OBJECT){

        }
        public void connect(Map OBJECT){

        }
        public void getMaxTransceiveLength(Map OBJECT){

        }
        public void isConnected(Map OBJECT){

        }
        public void setTimeout(Map OBJECT){

        }
        public void transceive(Map OBJECT){

        }
    }

    private class MifareClassic {
        public void close(Map OBJECT){

        }

        public void connect(Map OBJECT){

        }
        public void getMaxTransceiveLength(Map OBJECT){

        }
        public void isConnected(Map OBJECT){

        }
        public void setTimeout(Map OBJECT){

        }
        public void transceive(Map OBJECT){

        }
    }

    private class MifareUltralight {
        public void close(Map OBJECT){

        }

        public void connect(Map OBJECT){

        }
        public void getMaxTransceiveLength(Map OBJECT){

        }
        public void isConnected(Map OBJECT){

        }
        public void setTimeout(Map OBJECT){

        }
        public void transceive(Map OBJECT){

        }
    }

    private class Ndef {
        public void close(Map OBJECT){

        }

        public void connect(Map OBJECT){

        }

        public void isConnected(Map OBJECT){

        }
        public void setTimeout(Map OBJECT){

        }
        public void writeNdefMessage(Map OBJECT){

        }
        public void offNdefMessage(function callback){

        }
        public void onNdefMessage(function callback){

        }
    }

    private class NfcA {
        public void close(Map OBJECT){

        }

        public void connect(Map OBJECT){

        }
        public void getMaxTransceiveLength(Map OBJECT){

        }
        public void isConnected(Map OBJECT){

        }
        public void setTimeout(Map OBJECT){

        }
        public void transceive(Map OBJECT){

        }
        public void getAtqa(Map OBJECT){

        }
        public void getSak(Map OBJECT){

        }
    }

    private class NfcB {
        public void close(Map OBJECT){

        }

        public void connect(Map OBJECT){

        }
        public void getMaxTransceiveLength(Map OBJECT){

        }
        public void isConnected(Map OBJECT){

        }
        public void setTimeout(Map OBJECT){

        }
        public void transceive(Map OBJECT){

        }
    }

    private class NfcF {
        public void close(Map OBJECT){

        }

        public void connect(Map OBJECT){

        }
        public void getMaxTransceiveLength(Map OBJECT){

        }
        public void isConnected(Map OBJECT){

        }
        public void setTimeout(Map OBJECT){

        }
        public void transceive(Map OBJECT){

        }
    }

    private class NfcV {
        public void close(Map OBJECT){

        }

        public void connect(Map OBJECT){

        }
        public void getMaxTransceiveLength(Map OBJECT){

        }
        public void isConnected(Map OBJECT){

        }
        public void setTimeout(Map OBJECT){

        }
        public void transceive(Map OBJECT){

        }
    }
}
