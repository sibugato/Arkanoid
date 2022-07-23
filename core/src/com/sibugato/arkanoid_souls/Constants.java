package com.sibugato.arkanoid_souls;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import java.util.ArrayList;

public class Constants {

    private static final short BIT_PLAYER = 1;
    private static final short BIT_BALL = 2;
    private static final short BIT_PLATFORM = 4;
    private static final short BIT_VOID = 8;
    private static final short BIT_WALLS = 16;
    private static final short BIT_LIGHT = 32;
    private static final short BIT_GROUND = 64;
    private static final short BIT_CIRCLE = 128;
    private static final short BIT_VORTEX = 256;
    private static final short BIT_ACTIVE_VORTEX = 512;

    public static final Filter FILTER_VOID = new Filter();
    public static final Filter FILTER_LIGHT = new Filter();
    public static final Filter FILTER_PLAYER = new Filter();
    public static final Filter FILTER_PLATFORM = new Filter();
    public static final Filter FILTER_BALL = new Filter();
    public static final Filter FILTER_WALLS = new Filter();
    public static final Filter FILTER_GROUND = new Filter();
    public static final Filter FILTER_CIRCLE = new Filter();
    public static final Filter FILTER_VORTEX = new Filter();
    public static final Filter FILTER_ACTIVE_VORTEX = new Filter();

    public static ArrayList<Body> destroyList = new ArrayList<>();

    public static boolean isBodyDeliting = false;
    public static boolean isBell1Hit = false;
    public static boolean isBell2Hit = false;
    public static boolean isVortexActive = false;

    public static boolean isTouchUp = false;
    public static boolean isTouchDown = false;
    public static boolean isTouchDragged = false;

    public static boolean isRestartAllowed = false;
    public static boolean isGameRestarted = false;



    public static void CreateConstants() {
        FILTER_VOID.categoryBits = BIT_VOID;
        FILTER_VOID.maskBits = 0;

        FILTER_LIGHT.categoryBits = BIT_LIGHT;
        FILTER_LIGHT.maskBits = BIT_PLAYER | BIT_PLATFORM | BIT_GROUND | BIT_ACTIVE_VORTEX;

        FILTER_PLAYER.categoryBits = BIT_PLAYER;
        FILTER_PLAYER.maskBits = BIT_BALL | BIT_LIGHT;

        FILTER_PLATFORM.categoryBits = BIT_PLATFORM;
        FILTER_PLATFORM.maskBits = BIT_BALL | BIT_WALLS | BIT_PLATFORM | BIT_LIGHT | BIT_CIRCLE;

        FILTER_BALL.categoryBits = BIT_BALL;
        FILTER_BALL.maskBits = BIT_WALLS | BIT_PLAYER | BIT_PLATFORM | BIT_BALL | BIT_CIRCLE | BIT_VORTEX;

        FILTER_WALLS.categoryBits = BIT_WALLS;
        FILTER_WALLS.maskBits = BIT_BALL | BIT_PLATFORM;

        FILTER_GROUND.categoryBits = BIT_GROUND;
        FILTER_GROUND.maskBits = BIT_LIGHT;

        FILTER_CIRCLE.categoryBits = BIT_CIRCLE;
        FILTER_CIRCLE.maskBits = BIT_PLATFORM | BIT_BALL;

        FILTER_VORTEX.categoryBits = BIT_VORTEX;
        FILTER_VORTEX.maskBits = BIT_BALL;

        FILTER_ACTIVE_VORTEX.categoryBits = BIT_ACTIVE_VORTEX;
        FILTER_ACTIVE_VORTEX.maskBits = BIT_LIGHT;
    }

    public static void resetConstants() {
        isBell1Hit = false;
        isBell2Hit = false;
        isVortexActive = false;
        isTouchUp = false;
        isTouchDown = false;
        isTouchDragged = false;
        isRestartAllowed = false;
    }
}

