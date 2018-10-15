package com.quangtd.qsokoban.domain.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Animation {
    private ArrayList<Bitmap> frames;
    private int currentFrame;
    private int numFrames;
    private int count;
    private int delay = 10;
    private int timesPlayed;
    private int[] delays;

    public Animation() {
        timesPlayed = 0;
    }

    public void setFrames(ArrayList<Bitmap> frames) {
        this.frames = frames;
        currentFrame = 0;
        count = 0;
        timesPlayed = 0;
        this.delays = null;
        numFrames = frames.size();
    }

    public void setFrames(ArrayList<Bitmap> frames, int delay) {
        setFrames(frames);
        this.delay = delay;

    }

    public void setFrames(ArrayList<Bitmap> frames, int[] delays) {
        setFrames(frames);
        this.delays = delays;
    }

    public void setFrame(int i) {
        currentFrame = i;
    }

    public void update() {
        synchronized (frames) {
            if (delays == null) {
                if (delay == -1) {
                    return;
                }
                count++;
                if (count == delay) {
                    currentFrame++;
                    count = 0;
                }
                if (currentFrame == numFrames) {
                    currentFrame = 0;
                    timesPlayed++;
                }
            } else {
                count++;
                if (count == delays[currentFrame]) {
                    currentFrame++;
                    count = 0;
                }
                if (currentFrame == numFrames) {
                    currentFrame = 0;
                    timesPlayed++;
                }
            }

        }

    }

    public int getFrame() {
        return currentFrame;
    }

    public Bitmap getImage() {
        return frames.get(currentFrame);
    }

    public boolean hasPlayedOnce() {
        return timesPlayed > 0;
    }

    public boolean hasPlayed(int i) {
        return timesPlayed == i;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }


}
