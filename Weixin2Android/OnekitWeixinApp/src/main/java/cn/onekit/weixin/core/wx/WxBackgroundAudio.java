package cn.onekit.weixin.core.wx;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Map;

import cn.onekit.Android;
import cn.onekit.js.Dict;
import cn.onekit.js.core.function;
import cn.onekit.weixin.BackgroundAudioManager;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.res.wx_fail;


public class WxBackgroundAudio extends WxBackground {
    private MediaPlayer mediaPlayer;
    private int play = 2; // 0暂停 1播放 2停止
    private String dataUrl;
    private SeekBar seekBar;



    //获取后台音乐播放状态
    public void getBackgroundAudioPlayerState(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        if (mediaPlayer != null) {
            Dict res = new Dict();
//            res.duration = mediaPlayer.getDuration() / 1000;//选定音频的长度
//            res.currentPosition = mediaPlayer.getCurrentPosition() / 1000;//	选定音频的播放位置
//            res.status = play;//播放状态
//            res.downloadPercent = seekBar.getMax() * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
//            res.dataUrl = dataUrl;
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getBackgroundAudioPlayerState_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } else {
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_getBackgroundAudioPlayerState_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_getBackgroundAudioPlayerState_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    //使用后台播放器播放音乐
    public void playBackgroundAudio(final Map OBJECT) {
        seekBar = new SeekBar(Android.context);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.stop();//停止播放
            mediaPlayer.reset();//使其回到空闲状态
            mediaPlayer.release();//释放资源
            mediaPlayer = null;

            mediaPlayer = new MediaPlayer();
        }
        dataUrl = OBJECT.get("dataUrl") != null ? (String) OBJECT.get("dataUrl") : null;
        String title = OBJECT.get("title") != null ? (String) OBJECT.get("title") : null;//音乐标题
        String coverImgUrl = OBJECT.get("coverImgUrl") != null ? (String) OBJECT.get("coverImgUrl") : null;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        try {
            mediaPlayer.setDataSource(dataUrl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    BackgroundAudioManager.mediaPlayer = mediaPlayer;
                    play = 1;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    seekBar.setProgress(0);
                }
            });
            Dict res = new Dict();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_playBackgroundAudio_success); //"playBackgroundAudio: ok";
            if (complete != null) {
                complete.invoke(res);
            }
            if (success != null) {
                success.invoke(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_playBackgroundAudio_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_playBackgroundAudio_fail);// "playBackgroundAudio: fail: dataUrl is null or nil";
            if (complete != null) {
                complete.invoke(res);
            }
            if (fail != null) {
                fail.invoke(res);
            }
        }
    }

    //暂停播放音乐
    public void pauseBackgroundAudio(Map OBJECT) {
        if (mediaPlayer == null)
            return;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();//暂停播放
        }
        play = 0;
    }

    //停止播放音乐
    public void stopBackgroundAudio(Map OBJECT) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();//停止播放
            mediaPlayer.reset();//使其回到空闲状态
            mediaPlayer.release();//释放资源
            mediaPlayer = null;
            BackgroundAudioManager.mediaPlayer = null;
            play = 2;
        }
    }

    //控制音乐播放进度
    public void seekBackgroundAudio(Map OBJECT) {
        int position = OBJECT.get("position") != null ? (int) ((float) OBJECT.get("position")) : 0;
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        if (mediaPlayer != null && position != 0) {
            mediaPlayer.seekTo(position);
            Dict res = new Dict();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_seekBackgroundAudio_success);//"seekBackgroundAudio : OK";
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } else {
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_seekBackgroundAudio_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_seekBackgroundAudio_fail);// "seekBackgroundAudio : fail: seek fail";
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    //监听音乐播放
    public void onBackgroundAudioPlay(function OBJECT) {
        if (play == 1) {
            Dict res = new Dict();
            //注释,待解放
//            res.errMsg = "onPlay is ok";
            OBJECT.invoke(res);
        }

    }

    //监听音乐暂停
    public void onBackgroundAudioPause(function OBJECT) {
        if (play == 0) {
            /*
            cn.onekit.OBJECT res = new OBJECT();
            res.put("", "onPause is ok");
            OBJECT.invoke(res);
            */
        }
    }

    //监听音乐停止
    public void onBackgroundAudioStop(function OBJECT) {
        if (play == 2) {
            Dict res = new Dict();
//            res.dataUrl = "dataUrl";
            OBJECT.invoke(res);
        }
    }

    public BackgroundAudioManager getBackgroundAudioManager() {
      //  BackgroundAudioManager.backgroundAudioManager = new BackgroundAudioManager();
        return new BackgroundAudioManager();

    }
}

