package com.sibugato.arkanoid_souls;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputController implements InputProcessor {

    public static boolean isLeftKeyDown = false;
    public static boolean isRightKeyDown = false;
    public static boolean isUpKeyDown = false;
    public static boolean isDownKeyDown = false;
    public static boolean isZKeyDown = false;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            isLeftKeyDown = true;
            return isLeftKeyDown;
        }
        else if (keycode == Input.Keys.RIGHT) {
            isRightKeyDown = true;
            return isRightKeyDown;
        }
        if (keycode == Input.Keys.UP) {
            isUpKeyDown = true;
            return isUpKeyDown;
        }
        if (keycode == Input.Keys.DOWN) {
            isDownKeyDown = true;
            return isDownKeyDown;
        }
        if (keycode == Input.Keys.Z) {
            isZKeyDown = true;
            return isZKeyDown;
        }
        else return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            isLeftKeyDown = false;
            return isLeftKeyDown;
        }
        else if (keycode == Input.Keys.RIGHT) {
            isRightKeyDown = false;
            return isRightKeyDown;
        }
        if (keycode == Input.Keys.UP) {
            isUpKeyDown = false;
            return isUpKeyDown;
        }
        if (keycode == Input.Keys.DOWN) {
            isDownKeyDown = false;
            return isDownKeyDown;
        }
        if (keycode == Input.Keys.Z) {
            isZKeyDown = false;
            return isZKeyDown;
        }
        else return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Constants.isTouchDown = true;
        Constants.isTouchUp = false;
        return Constants.isTouchDown;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Constants.isTouchUp = true;
        Constants.isTouchDown = false;
        Constants.isTouchDragged = false;
        return Constants.isTouchUp;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Constants.isTouchDragged = true;
        return Constants.isTouchDragged;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}

