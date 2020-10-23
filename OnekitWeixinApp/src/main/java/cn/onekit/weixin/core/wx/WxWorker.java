package cn.onekit.weixin.core.wx;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Map;

import cn.onekit.js.core.function;

public class WxWorker extends WxWindow {

    MyHandler handler;//定义handler
    Handler handler2;
    Message msg;

    public WxWorker createWorker() {
        WxWorker worker = new WxWorker();
        return worker;
    }
    public void onMessage(function callback) {
        handler=new MyHandler();//在主线程中实现handler，就会在主线程中用Looper处理其msg
        new MyAcceptThread().start();//开启接收信息线程（先开，因为handler2是在这个线程里实现的）
    }
    public void postMessage(Map message) {

    }
    public void terminate() {

    }
    class MySendThread extends Thread{
        public void run(){
            int i=0;
            while(i!=10){
                Bundle bundle=new Bundle();
                bundle.putString("num","第"+i+"次");//bundle中也可以放序列化或包裹化的类对象数据
                msg=handler.obtainMessage();//每发送一次都要重新获取
                msg.setData(bundle);
                handler.sendMessage(msg);//用handler向主线程发送信息

                msg=handler2.obtainMessage();
                msg.what=i;
                handler2.sendMessage(msg);//用handler2向myAcceptThread线程发送信息

                //休眠3秒，需要异常处理
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    }

    //    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
    //接收信息的线程类
    @SuppressLint("HandlerLeak")
    class MyAcceptThread extends Thread{
        public void run(){
            Looper.prepare();//准备Looper对象
            //在分线程中实现handler2，就会在分线程中处理其msg
            handler2=new Handler(){//这是用匿名内部类生成一个handler对象
                public void handleMessage(Message msg) {

                }
            };
            //调用Looper的loop方法后，Looper对象将不断从消息队列中取出消息对象并交给handleMessage处理
            //没有消息时该线程会阻塞
            Looper.loop();
        }
    }

    //自定义handler类
    class MyHandler extends Handler{
        @Override
        //接收别的线程的信息并处理
        public void handleMessage(Message msg) {
            Bundle bundle=msg.getData();

        }
    }
}

