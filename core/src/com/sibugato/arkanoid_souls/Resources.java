package com.sibugato.arkanoid_souls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Resources {

    public static final TextureAtlas TEXTURE_ATLAS = new TextureAtlas("ATLAS.pack");

    private static final Sound BONK_SOUND = Gdx.audio.newSound(Gdx.files.internal("Bonk.ogg"));
    private static final Sound BELL_SOUND = Gdx.audio.newSound(Gdx.files.internal("bell_sound.ogg"));
    private static final Sound DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("You_Died.mp3"));
    private static final Sound PLATFORM_BROKE_SOUND = Gdx.audio.newSound(Gdx.files.internal("Platform_Broke.ogg"));
    private static final Sound RING_FROCE = Gdx.audio.newSound(Gdx.files.internal("RingForce.mp3"));

    private static final Music DAY_SOUND = Gdx.audio.newMusic(Gdx.files.internal("day.ogg"));
    private static final Music NIGHT_SOUND = Gdx.audio.newMusic(Gdx.files.internal("night.ogg"));



    public Resources() {}

    public void play (String sound) {
        if (sound.equalsIgnoreCase("BONK")) BONK_SOUND.play(0.05f);
        else  if (sound.equalsIgnoreCase("PLATFORM_BROKE")) PLATFORM_BROKE_SOUND.play(0.27f);
        else if (sound.equalsIgnoreCase("DEATH")) DEATH_SOUND.play(0.35f);
        else if (sound.equalsIgnoreCase("BELL")) BELL_SOUND.play(0.37f);
        else if (sound.equalsIgnoreCase("FORCE")) RING_FROCE.play(1f);

        else if (sound.equalsIgnoreCase("DAY")) {
            DAY_SOUND.play();
            DAY_SOUND.setLooping(true);
            DAY_SOUND.setVolume(0.5f);
        }
        else if (sound.equalsIgnoreCase("NIGHT")) {
            NIGHT_SOUND.play();
            NIGHT_SOUND.setLooping(true);
            NIGHT_SOUND.setVolume(0);
        }
    }

    public void setVolume (String sound, int volume) {
        if (sound.equalsIgnoreCase("DAY") && DAY_SOUND.getVolume()>0 && volume == 0) DAY_SOUND.setVolume(DAY_SOUND.getVolume()-0.001f);
        else if (sound.equalsIgnoreCase("DAY") && DAY_SOUND.getVolume() < 0.5f && volume == 1) DAY_SOUND.setVolume(DAY_SOUND.getVolume()+0.001f);
        if (sound.equalsIgnoreCase("NIGHT") && NIGHT_SOUND.getVolume()>0 && volume == 0) NIGHT_SOUND.setVolume(NIGHT_SOUND.getVolume()-0.001f);
        else if (sound.equalsIgnoreCase("NIGHT") && NIGHT_SOUND.getVolume() < 0.5f && volume == 1) NIGHT_SOUND.setVolume(NIGHT_SOUND.getVolume()+0.001f);
    }
}
