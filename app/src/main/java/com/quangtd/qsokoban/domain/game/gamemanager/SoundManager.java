package com.quangtd.qmazes.game.gamemanager;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.quangtd.qmazes.R;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by quang.td95@gmail.com
 * on 9/2/2018.
 */

public class SoundManager {
    private static SoundManager instance;
    private boolean isMute = false;

    /**
     * @return mute or not
     */
    public boolean toggleMute() {
        isMute = !isMute;
        if (isMute) {
            stopSoundClockTick();
        } else {

        }
        return isMute;
    }

    private SoundManager() {

    }

    public static synchronized SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
//        if (!instance.loaded) {
//            instance.setup(context);
//        }
        return instance;
    }

    // Số luồng âm thanh phát ra tối đa.
    private static final int MAX_STREAMS = 5;
    private SoundPool soundPool;

    private AudioManager audioManager;
    // Chọn loại luồng âm thanh để phát nhạc.
    private static final int streamType = AudioManager.STREAM_MUSIC;
    private volatile boolean loaded;
    private float volume;
    private int soundIdTouch;
    private int soundIdBulletShoot;
    private int soundIdBulletDestroy;
    private int soundIdPlayerDestroy;
    private int soundIdTimeUp;
    private int soundIdClockTicking;

    public void setup(Context context) {
        // Đối tượng AudioManager sử dụng để điều chỉnh âm lượng.
        audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        // Chỉ số âm lượng hiện tại của loại luồng nhạc cụ thể (streamType).
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);
        // Chỉ số âm lượng tối đa của loại luồng nhạc cụ thể (streamType).
        float maxVolumeIndex = (float) audioManager.getStreamMaxVolume(streamType);
        // Âm lượng  (0 --> 1)
        this.volume = currentVolumeIndex / maxVolumeIndex;
        // Với phiên bản Android SDK >= 21
        if (Build.VERSION.SDK_INT >= 21) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // Với phiên bản Android SDK < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // Sự kiện SoundPool đã tải lên bộ nhớ thành công.
        this.soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> loaded = true);

        soundIdTouch = this.soundPool.load(context, R.raw.toggle_switch, 1);
        soundIdBulletDestroy = this.soundPool.load(context, R.raw.bullet_destroy, 1);
        soundIdBulletShoot = this.soundPool.load(context, R.raw.bullet_shoot, 1);
        soundIdPlayerDestroy = this.soundPool.load(context, R.raw.died, 1);
        soundIdClockTicking = this.soundPool.load(context, R.raw.clock_ticking, 1);
        soundIdTimeUp = this.soundPool.load(context, R.raw.time_up, 1);
    }

    public void playTouchSound() {
        playSound(soundIdTouch);
    }

    public void playBulletShoot() {
        playSound(soundIdBulletShoot);
    }

    public void playBulletDestroy() {
        playSound(soundIdBulletDestroy);
    }

    public void playPlayerDied() {
        playSound(soundIdPlayerDestroy);
    }

    public void playSoundTimeUp() {
        playSound(soundIdTimeUp);
    }


    public boolean playSoundClockTick() {
        return playSound(soundIdClockTicking, true);
    }

    public boolean stopSoundClockTick() {
        if (loaded) {
            this.soundPool.stop(streamLoopId);
            return true;
        }
        return false;
    }

    private void playSound(int soundId) {
        playSound(soundId, false);
    }

    private int streamLoopId;

    private boolean playSound(int soundId, boolean loop) {
        if (isMute) return false;
        if (loaded) {
            float leftVolumn = volume;
            float rightVolumn = volume;
            // Trả về ID của luồng mới phát ra.
            int streamId = this.soundPool.play(soundId, leftVolumn, rightVolumn, 1, loop ? -1 : 0, 1f);
            if (loop) {
                streamLoopId = streamId;
            }
            return true;
        } else {
            return false;
        }
    }
}
