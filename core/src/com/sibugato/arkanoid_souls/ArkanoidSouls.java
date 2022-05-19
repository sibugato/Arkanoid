package com.sibugato.arkanoid_souls;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import stages.MainMenu;
import states.GameStateManager;

public class ArkanoidSouls extends Game {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Arkanoid Souls";

	public static GameStateManager gsm;
	private SpriteBatch sb;
	private static float dt;

	public void create () {
		Constants.CreateConstants();
		dt  = Gdx.graphics.getDeltaTime();
		sb = new SpriteBatch();
		gsm = new GameStateManager();
		ScreenUtils.clear(0, 0, 0, 1);
		gsm.push(new MainMenu(gsm));

	}

	public void render () {
		gsm.update(dt);
		gsm.render(sb);
	}

	public void dispose () {
		sb.dispose();
		super.dispose();
	}

	public void resize(int width, int height) {
		gsm.resize(width, height, sb);
	}

	public void pause() {
		super.pause();
	}

	public void resume() {
		super.resume();
	}
}
