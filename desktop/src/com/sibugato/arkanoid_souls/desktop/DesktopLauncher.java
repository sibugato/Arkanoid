package com.sibugato.arkanoid_souls.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sibugato.arkanoid_souls.ArkanoidSouls;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = ArkanoidSouls.TITLE;
		config.width = ArkanoidSouls.WIDTH;
		config.height = ArkanoidSouls.HEIGHT;
		new LwjglApplication(new ArkanoidSouls(), config);
	}
}
