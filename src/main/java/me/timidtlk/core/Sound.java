package me.timidtlk.core;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    private Clip clip;
    private URL[] soundURL;

    public Sound() {
        soundURL = new URL[30];

        soundURL[0] = getClass().getClassLoader().getResource("sounds/player/jump.wav");
        soundURL[1] = getClass().getClassLoader().getResource("sounds/player/skid.wav");
        soundURL[2] = getClass().getClassLoader().getResource("sounds/player/roll.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
}
