package com.sibugato.arkanoid_souls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Resources {

    public static final TextureAtlas TEXTURE_ATLAS = new TextureAtlas("ATLAS.pack");

    private static final Sound BONK_SOUND = Gdx.audio.newSound(Gdx.files.internal("Bonk.ogg"));
    private static final Sound BELL_SOUND = Gdx.audio.newSound(Gdx.files.internal("bell_sound.ogg"));
    private static final Sound DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("You_Died.mp3"));
    private static final Sound PLATFORM_BROKE_SOUND = Gdx.audio.newSound(Gdx.files.internal("Platform_Broke.ogg"));
    private static final Sound RING_FORCE_SOUND = Gdx.audio.newSound(Gdx.files.internal("RingForce.mp3"));

    private static final Music DAY_SOUND = Gdx.audio.newMusic(Gdx.files.internal("day.ogg"));
    private static final Music NIGHT_SOUND = Gdx.audio.newMusic(Gdx.files.internal("night.ogg"));

    private static float masterVolume = 1;

    public void play (String sound) {
        switch (sound) {
            case ("BONK") :
                BONK_SOUND.play(0.2f*masterVolume);
                break;
            case ("PLATFORM_BROKE") :
                PLATFORM_BROKE_SOUND.play(0.27f*masterVolume);
                break;
            case ("DEATH") :
                DEATH_SOUND.play(0.5f*masterVolume);
                break;
            case ("BELL") :
                BELL_SOUND.play(0.37f*masterVolume);
                break;
            case ("FORCE") :
                RING_FORCE_SOUND.play(1f*masterVolume);
                break;
            case ("DAY") :
                DAY_SOUND.play();
                DAY_SOUND.setLooping(true);
                DAY_SOUND.setVolume(0.5f*masterVolume);
                break;
            case ("NIGHT") :
                NIGHT_SOUND.play();
                NIGHT_SOUND.setLooping(true);
                NIGHT_SOUND.setVolume(0*masterVolume);
                break;
        }
    }

    public void switchDayAndNightEnvironmentVolume(String sound, int volume) {
        if (sound.equalsIgnoreCase("DAY") && DAY_SOUND.getVolume()>0 && volume == 0) {
            DAY_SOUND.setVolume(DAY_SOUND.getVolume()-0.001f);
        }
        else if (sound.equalsIgnoreCase("DAY") && DAY_SOUND.getVolume() < 0.5f*masterVolume && volume == 1) {
            DAY_SOUND.setVolume(DAY_SOUND.getVolume()+0.001f);
        }
        if (sound.equalsIgnoreCase("NIGHT") && NIGHT_SOUND.getVolume()>0 && volume == 0) {
            NIGHT_SOUND.setVolume(NIGHT_SOUND.getVolume()-0.001f);
        }
        else if (sound.equalsIgnoreCase("NIGHT") && NIGHT_SOUND.getVolume() < 0.5f*masterVolume && volume == 1) {
            NIGHT_SOUND.setVolume(NIGHT_SOUND.getVolume()+0.001f);
        }
    }

    public static float getMasterVolume() {
        return masterVolume;
    }

    public static void setMasterVolume(float value) {
        masterVolume = value;
    }
}
