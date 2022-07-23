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

	public static GameStateManager gameStateManager;
	private SpriteBatch spriteBatch;
	private static float deltaTime;

	public void create () {
		Constants.CreateConstants();
		deltaTime = Gdx.graphics.getDeltaTime();
		spriteBatch = new SpriteBatch();
		gameStateManager = new GameStateManager();
		ScreenUtils.clear(0, 0, 0, 1);
		gameStateManager.push(new MainMenu(gameStateManager));

	}

	public void render () {
		gameStateManager.update(deltaTime);
		gameStateManager.render(spriteBatch);
	}

	public void dispose () {
		spriteBatch.dispose();
		super.dispose();
	}

	public void resize(int width, int height) {
		gameStateManager.resize(width, height, spriteBatch);
	}

	public void pause() {
		super.pause();
	}

	public void resume() {
		super.resume();
	}
}
