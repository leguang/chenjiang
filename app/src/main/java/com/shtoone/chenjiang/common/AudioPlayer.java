package com.shtoone.chenjiang.common;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.shtoone.chenjiang.R;

/**
 * Author：leguang on 2016/11/29 0029 14:15
 * Email：langmanleguang@qq.com
 */
public class AudioPlayer {
    private static SoundPool mSoundPool;

    /**
     * 初始化
     */
    public static void init(Context mContext) {
        if (mSoundPool == null) {
            mSoundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
            // 初始化声音
            mSoundPool.load(mContext, R.raw.abnormal, 1);// 1
            mSoundPool.load(mContext, R.raw.connect, 1);// 2
            mSoundPool.load(mContext, R.raw.nextb, 1);// 3
            mSoundPool.load(mContext, R.raw.nextf, 1);// 4
            mSoundPool.load(mContext, R.raw.normal, 1);// 5
            mSoundPool.load(mContext, R.raw.overrun, 1);// 6
            mSoundPool.load(mContext, R.raw.unconnect, 1);// 7
            mSoundPool.load(mContext, R.raw.warning, 1);// 8
        }
    }

    /**
     * 播放声音
     *
     * @param soundID
     */
    public static void play(int soundID) {
        if (mSoundPool == null) {
            throw new Error("Please initialize first!");
        }
        mSoundPool.play(soundID, 1, 1, 0, 0, 1);
    }

    public static void onDestroy() {
        if (mSoundPool != null) {
            mSoundPool = null;
        }
    }
}
