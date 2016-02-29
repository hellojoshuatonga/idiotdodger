package com.idiotdodger.utils;

import com.idiotdodger.GameSettings;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by root on 2/29/16.
 */
public enum SoundManager {
    ONGAME(GameSettings.SOUND_ONGAME),
    MENU(GameSettings.SOUND_MENU),
    DEAD(GameSettings.SOUND_DEAD),
    CLICK(GameSettings.SOUND_CLICK);

    public enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume;

    private Clip clip;

    SoundManager(String soundFileName) {
        try {
            URL soundUrl = getClass().getClassLoader().getResource(soundFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundUrl);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException |
                LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Play the sound file
     * */
    public void play() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop();

            clip.setFramePosition(0);
            clip.start();
        }
    }

    /**
     * Play the music forever! :)
     * */
    public void playForever() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop();

            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Check if the clip is still running
     *
     * @return clip.isRunning still running
     * */
    public boolean isRunning() {
        return clip.isRunning();
    }


    /**
     * Stop the clip from playing
     * */
    public void stop() {
        if (clip.isRunning())
            clip.stop();
    }

    public static void init() {
        values();
    }

}
