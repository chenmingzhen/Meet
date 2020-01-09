package com.example.framework.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import androidx.annotation.RequiresApi;

public class MediaPlayerManager {

    //播放
    private static final int MEDIA_STATUS_PLAY = 0;
    //暂停
    private static final int MEDIA_STATUS_PAUSE = 1;
    //停止
    private static final int MEDIA_STATUS_STOP = 2;
    private static final int H_PROGRESS = 1000;
    private static int MEDIA_STATUS = MEDIA_STATUS_STOP;
    private MediaPlayer mMediaPlayer;
    //声明自定义接口
    private OnMusicProgressListener musicProgressListener;

    /**
     * 计算歌曲进度
     * 1.开始播放的时候就循环计算时长
     * 2.将进度对外抛出
     */
    private Handler mHandler = new Handler (new Handler.Callback () {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case H_PROGRESS:
                    if (musicProgressListener != null) {
                        //拿到当前时长

                        int currentPosition = getCurrentPosition ();
                        float mProgress = (float) (currentPosition);
                        float mDuration = (float) (getDuration ());
                        int pos = (int) ((mProgress / mDuration) * 100);
                        /*int pos =(int)(((float)currentPosition)/((float)getDuration ())*100);*/
                        musicProgressListener.onProgress (currentPosition, pos);
                        mHandler.sendEmptyMessageDelayed (H_PROGRESS, 1000);
                    }
                    break;
            }
            return false;
        }
    });

    public MediaPlayerManager() {
        this.mMediaPlayer = new MediaPlayer ();
    }

    /**
     * 播放
     *
     * @param path
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startPlay(AssetFileDescriptor path) {
        mMediaPlayer.reset ();
        try {
            mMediaPlayer.setDataSource (path);
            mMediaPlayer.prepare ();
            mMediaPlayer.start ();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            mHandler.sendEmptyMessage (H_PROGRESS);
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    /**
     * 暂停播放
     */
    public void pausePlay() {

        if (isPlay ()) {
            MEDIA_STATUS = MEDIA_STATUS_PAUSE;
            mMediaPlayer.pause ();
            mHandler.removeMessages (H_PROGRESS);
        }

    }

    public boolean isPlay() {
        return mMediaPlayer.isPlaying ();
    }

    /**
     * 继续播放
     */
    public void continuePlay() {
        mMediaPlayer.start ();
        MEDIA_STATUS = MEDIA_STATUS_PLAY;
        mHandler.sendEmptyMessage (H_PROGRESS);
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        mMediaPlayer.stop ();
        MEDIA_STATUS = MEDIA_STATUS_STOP;
        mHandler.removeMessages (H_PROGRESS);
    }

    /**
     * @return 获取当前位置
     */
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition ();
    }

    /**
     * @return 总时长
     */
    public int getDuration() {
        return mMediaPlayer.getDuration ();
    }

    public void setLooping(boolean isLooping) {
        mMediaPlayer.setLooping (isLooping);
    }

    /**
     * 跳转位置
     *
     * @param ms
     */
    public void seekTo(int ms) {
        mMediaPlayer.seekTo (ms);
    }

    /**
     * 播放完成
     *
     * @param listener
     */
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        mMediaPlayer.setOnCompletionListener (listener);
    }

    /**
     * 播放错误
     *
     * @param listener
     */
    public void setOnErrorListener(MediaPlayer.OnErrorListener listener) {
        mMediaPlayer.setOnErrorListener (listener);
    }

    /**
     * 播放进度
     *
     * @param listener
     */
    public void setOnProgressListener(OnMusicProgressListener listener) {
        musicProgressListener = listener;
    }

    public interface OnMusicProgressListener {
        void onProgress(int progress, int pos);
    }
}
