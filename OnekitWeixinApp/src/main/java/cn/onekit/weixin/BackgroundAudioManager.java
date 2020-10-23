package cn.onekit.weixin;


import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;

import cn.onekit.js.JsObject;
import cn.onekit.js.JsString;
import cn.onekit.js.JsObject_;
import cn.onekit.js.core.function;

public  class BackgroundAudioManager {
    private int duration ;      //当前音频的长度
    private int currentTime ;       //当前音频的播放位置
    private Boolean paused ;        //当前是是否暂停或停止状态，true 表示暂停或停止，false 表示正在播放
    private String src ;        //音频的数据源
    private int startTime;      //音频开始播放的位置
    private int buffered ;      //音频缓冲的时间点
    private String title  ;     //音频标题，用于做原生音频播放器音频标题
    private  String epname ;         //专辑名
    private String singer ;         //歌手名，
    private String coverImgUrl ;        //封面图url
    private String webUrl  ;        //页面链接
    public Uri uri;
    public static MediaPlayer mediaPlayer =new MediaPlayer();
    private MediaMetadataRetriever mediaMetadataRetriever;
    //    private BackgroundAudioManager manager;
   // static BackgroundAudioManager backgroundAudioManager;
    public BackgroundAudioManager(){

        mediaMetadataRetriever = new MediaMetadataRetriever();
    }


    public void play(Object obj){
        if(!mediaPlayer.isPlaying()&&mediaPlayer!=null){
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    onEnded(new function() {
                        @Override
                        public JsObject_ invoke(JsObject_... arguments) {
                            return null;
                        }
                        //                            @Override
//                            public JsObject invoke(JsObject arg1) {
//                                Console.log(arg1);
//                                return null;
//                            }
                    });
                }
            });

        }

    }
    public void pause(JsObject_ obj){
        if(mediaPlayer.isPlaying()&&mediaPlayer!=null)
            mediaPlayer.pause();
    }
    public void stop(JsObject_ obj){
        if(mediaPlayer!=null)
            mediaPlayer.stop();
    }
    public void seek(Integer position){
        if(mediaPlayer!=null)
            mediaPlayer.seekTo(position*1000);
    }
    public void onEnded(function res){
        if(mediaPlayer!=null){
            JsObject obj = new JsObject();
            obj.put("onEnded",new JsString("播放完成"));
            res.invoke(obj);
        }
    }


    public void onCanplay(function res){
        if(mediaPlayer!=null){
            JsObject obj = new JsObject();
            obj.put("onCanplay",new JsString("能播放"));
            res.invoke(obj);
        }
    }
    public void onPlay(function res){
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            JsObject obj = new JsObject();
            obj.put("onPlay",new JsString("正在播放"));
            res.invoke(obj);
        }
    }
    public void onPause(function res){
        if(mediaPlayer!=null&&(!mediaPlayer.isPlaying())){
            JsObject obj = new JsObject();
            obj.put("onPause",new JsString("暂停中"));
            res.invoke(obj);
        }
    }
    public void onStop(function res){
        if(mediaPlayer==null){
            JsObject obj = new JsObject();
            obj.put("onStop",new JsString("停止"));
            res.invoke(obj);
        }
    }


    public void setSrc(String src) {
        this.src = src;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEpname(String epname) {
        this.epname = epname;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentTime() {
        return mediaPlayer.getCurrentPosition();
    }

    public Boolean getPaused() {
        return paused;
    }

    public String getSrc() {
        return "";
    }

    public int getStartTime() {
        return 0;
    }

    public int getBuffered() {
        return 0;
    }

    public String getTitle() {
        return mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
    }

    public String getEpname() {
        return "";
    }

    public String getSinger() {
        return "";
    }

    public String getCoverImgUrl() {
        return "";
    }

    public String getWebUrl() {
        return "";
    }
}
