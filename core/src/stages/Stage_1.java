package stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sibugato.arkanoid_souls.ArkanoidSouls;
import com.sibugato.arkanoid_souls.BodyMaker;
import com.sibugato.arkanoid_souls.Constants;
import com.sibugato.arkanoid_souls.ContactWatcher;
import com.sibugato.arkanoid_souls.InputController;
import com.sibugato.arkanoid_souls.Resources;
import com.talosvfx.talos.runtime.ParticleEffectDescriptor;
import com.talosvfx.talos.runtime.ParticleEffectInstance;
import com.talosvfx.talos.runtime.render.SpriteBatchParticleRenderer;

import java.util.ArrayList;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import objects.Animator;
import objects.Ball;
import objects.Bell;
import objects.Bird;
import objects.Ring;
import objects.Platform;
import objects.Player;
import objects.SunAndMoon;
import objects.Vortex;
import states.GameStateManager;
import states.State;

public class Stage_1 extends State {

    private final InputController INPUT_CONTROLLER = new InputController();
    private final Vector2 GRAVITY_VECTOR = new Vector2(0,-0.00f);
    private final Vector3 TOUCH_POSITION = new Vector3(0,0,0);
    private final World WORLD = new World(GRAVITY_VECTOR, true);
    private final Resources SOUND;

    private final ExtendViewport EXTENDED_VIEWPORT;
    private Viewport viewport;
    private final Box2DDebugRenderer DEBUG_RENDERER = new Box2DDebugRenderer();
    private final SpriteBatch SPRITE_BATCH;
    private PolygonSpriteBatch batch;
    private SpriteBatchParticleRenderer defaultRenderer;
    private ParticleEffectInstance effect;
    public static RayHandler rayHandler;
    public static BitmapFont font;

    private Texture background  = new Texture("Background_stage_1.jpg");
    private Sprite youDiedScreen = new Sprite(new Texture("YouDiedScreen.jpg"));
    private Sprite arrow = new Sprite();
    private Animator arrowAnimation = new Animator(Resources.TEXTURE_ATLAS.findRegion("AnimationArrow"),1,18,0.8f,false,false);

    private ArrayList<Platform> platforms = new ArrayList<>();
    private Player player;
    private Ball ball;
    private SunAndMoon sunAndMoon;
    private Vortex vortex;
    private Body bodyWalls;
    private Body bodyGround;
    private Bird bird1, bird2;
    private Ring ring;
    private Bell bell1, bell2;
    private PointLight lightSun1, lightSun2, lightMoon1, lightMoon2, lightBall, portalLight;;

    private boolean is_start = false;
    private boolean is_start2 = false;
    private boolean is_arrow = true;
    private boolean is_vortex_glowing = false;
    private float x_speed = 0;
    private float bell1Alpha = 1;
    private float bell2Alpha = 1;
    private float ringAlpha = 1;
    private float ballAlpha = 1;
    private float youDiedScreenAlpha = 0;

    private int width = 0;
    private int height = 0;


    public Stage_1(GameStateManager gsm) {

        //general stage configuration
        super(gsm);
        Constants.isGameRestarted = false;
        Constants.resetConstants();
        Gdx.input.setInputProcessor(INPUT_CONTROLLER);
        camera.setToOrtho(false, (float)  ArkanoidSouls.WIDTH/20, (float) ArkanoidSouls.HEIGHT/20);
        EXTENDED_VIEWPORT = new ExtendViewport((float) ArkanoidSouls.WIDTH/20, (float) ArkanoidSouls.HEIGHT/20, (float) ArkanoidSouls.WIDTH*2, (float) ArkanoidSouls.HEIGHT*2, camera);
        WORLD.setContactListener(new ContactWatcher());
        SOUND = new Resources();
        SOUND.play("DAY");
        SOUND.play("NIGHT");
        SPRITE_BATCH = new SpriteBatch();
        youDiedScreen.setSize(24,40);

        // objects creating
        ball = new Ball(-10,10, WORLD);
        player = new Player(10,3, WORLD);
        vortex = new Vortex(12,20, WORLD);
        sunAndMoon = new SunAndMoon(12,0, WORLD);
        bell1 = new Bell(1,31, WORLD, "bell1");
        bell2 = new Bell(20.6f,31, WORLD, "bell2");
        bird1 = new Bird(30,10, WORLD);
        bird2 = new Bird(-42,30, WORLD);
        ring = new Ring(12,20, WORLD);
        font = new BitmapFont();

        Vector2[] shapeWalls = {new Vector2(0, 0), new Vector2(0, (float) ArkanoidSouls.HEIGHT/20), new Vector2((float) ArkanoidSouls.WIDTH/20, (float) ArkanoidSouls.HEIGHT/20), new Vector2((float) ArkanoidSouls.WIDTH/20, 0)};
        bodyWalls = BodyMaker.createChain(WORLD,0,0,shapeWalls, BodyDef.BodyType.StaticBody,Constants.FILTER_WALLS,0,0,0,0,false,"wall");

        bodyGround = BodyMaker.createBox(WORLD,-40,0,120,3, BodyDef.BodyType.StaticBody,Constants.FILTER_GROUND,0,0,0,0,false,"ground");
        create_platforms();
        create_light();
        //create_particles();    !!(temporarily disabled)!!
        arrow.setSize(1,1.327f);
        arrow.setPosition(11.5f,4.8f);
        arrow.setOrigin(arrow.getWidth()/2,-2f);

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font2.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.color = Color.GREEN;
        fontParameter.size = 100;
        fontParameter.magFilter = Texture.TextureFilter.Linear;
        fontParameter.minFilter = Texture.TextureFilter.Linear;
        font = fontGenerator.generateFont(fontParameter);
        font.getData().setScale(0.03f);
    }

    public void update(float dt) {
        handleInput();
        for (Platform P : platforms) {
            P.update(dt);
        }
        if (!is_arrow) {
            ball.update(dt);
        }
        player.update(dt);
        vortex.update(dt);
        sunAndMoon.update(dt);
        bird1.update(dt);
        bird2.update(dt);
        ring.update(dt);
        ballSpeedLimiter();
        ballOutTheScreenCheck();
        if (Ball.isBounce) {
            Ball.isBounce = false;
            SOUND.play("BONK");
        }

        if (arrowAnimation.getIsEnd()) {
            is_arrow = false;
            is_start = true;
        }

        if (is_start && !is_start2) {
            float R = -arrow.getRotation();
            float X = 12 + R*0.045f;
            float Y = 5 - Math.abs(R*0.01f);
            ball.getBody().setTransform(X,Y,0);
            ball.getBody().setLinearVelocity( R*0.1f, R >= 0 ? 10 -R*0.1f : 10 + R*0.1f);
            is_start2 = true;
        }

        if (Constants.isBell1Hit && bell1Alpha > 0.01f) {
            bell1Alpha = bell1Alpha - 0.01f;
            bell1.getSprite().setAlpha(bell1Alpha);
            bell1.setIsHit();
            WORLD.setGravity(new Vector2(0,-9.8f));
        }

        if (Constants.isBell2Hit && bell2Alpha > 0.01f) {
            bell2Alpha = bell2Alpha - 0.01f;
            bell2.getSprite().setAlpha(bell2Alpha);
            bell2.setIsHit();
            WORLD.setGravity(new Vector2(0,-9.8f));

        }

        if (Constants.isBell1Hit && Constants.isBell2Hit && ringAlpha > 0.01f) {
            ringAlpha = ringAlpha - 0.01f;
            ring.getSprite().setAlpha(ringAlpha);
            ring.getBody().getFixtureList().get(0).setFilterData(Constants.FILTER_VOID);
        }
        if (Constants.isBell1Hit && Constants.isBell2Hit) {
            activatePortal();
        }
        if (Constants.isVortexActive && vortex.getAnimationIsEnd()) {
            endGame();
        }
        if (Constants.isVortexActive && ballAlpha > 0.1f) {
            ballAlpha -= 0.01f;
            ball.getSPRITE().setAlpha(ballAlpha);
            ball.getBody().getFixtureList().get(0).setFilterData(Constants.FILTER_VOID);
        }
        musicMasterVolume();
    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        WORLD.step(1/60f,4,4);
        if (!WORLD.isLocked() && !Constants.destroyList.isEmpty()) {
            body_delete();
        }
        SPRITE_BATCH.begin();
        SPRITE_BATCH.draw(background,0,0,24,40);
        if (is_arrow) {
            arrow.setRegion(arrowAnimation.getCurrentFrame());
            arrow.draw(SPRITE_BATCH);
        }
        else {
            ball.render(SPRITE_BATCH);
        }
        SPRITE_BATCH.setProjectionMatrix(camera.combined);
        // render_particles();    !!temporarily disabled)!!
        SPRITE_BATCH.setProjectionMatrix(camera.combined);
        player.render(SPRITE_BATCH);
        ring.render(SPRITE_BATCH);
        for (Platform P : platforms) {
            P.render(SPRITE_BATCH);
        }
        sunAndMoon.render(SPRITE_BATCH);
        bird1.render(SPRITE_BATCH);
        bird2.render(SPRITE_BATCH);
        bell1.render(SPRITE_BATCH);
        bell2.render(SPRITE_BATCH);
        vortex.render(SPRITE_BATCH);
        // if (is_start2) font.draw(SPRITE_BATCH,String.valueOf((int)Math.abs(ball.getBody().getLinearVelocity().x) + (int)Math.abs(ball.getBody().getLinearVelocity().y)), 22, 3,0,1,true);
        // else font.draw(SPRITE_BATCH,"0", 22.1f, 3,0.5f,1,true);
        SPRITE_BATCH.end();

        //DEBUG_RENDERER.render(WORLD, camera.combined);
        rayHandler.updateAndRender();

        if (!player.isAlive && !Constants.isVortexActive) {
            SPRITE_BATCH.begin();
            if (youDiedScreenAlpha <= 0.99f) {
                youDiedScreenAlpha = youDiedScreenAlpha + 0.0035f;
                youDiedScreen.setAlpha(youDiedScreenAlpha);
            }
            else if (youDiedScreenAlpha >0.99f) {
                Constants.isRestartAllowed = true;
            }
            youDiedScreen.draw(SPRITE_BATCH);
            SPRITE_BATCH.end();
        }
    }

    public void create_particles() {

        //We need a viewport for proper camerawork
        viewport = new FitViewport(ArkanoidSouls.WIDTH/20, ArkanoidSouls.HEIGHT/20);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);


        // We may need polygon sprite batch to render more complex VFX such us beams
        batch = new PolygonSpriteBatch();
        batch.setProjectionMatrix(viewport.getCamera().projection);


        // Prepare the texture atlas.
        // Normally just load Texture Atlas,  but for the sake of demo this will be creating fake atlas from just one texture.
        TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("fire.png")));
        TextureAtlas textureAtlas = new TextureAtlas();
        textureAtlas.addRegion("fire", textureRegion);


        //Creating particle effect instance from particle effect descriptor
        ParticleEffectDescriptor effectDescriptor = new ParticleEffectDescriptor(Gdx.files.internal("fire.p"), textureAtlas);
        effect = effectDescriptor.createEffectInstance();
        defaultRenderer = new SpriteBatchParticleRenderer();
        effect.setPosition(-8.5f,-15);
        defaultRenderer.setBatch(batch);
    }

    public void render_particles() {
        float delta = Gdx.graphics.getDeltaTime();
        effect.update(delta);
        // now to render
        // Gdx.gl.glClearColor(0, 0, 0, 1);
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        effect.render(defaultRenderer);
        batch.end();
    }

    protected void handleInput() {
        if (Constants.isRestartAllowed) {
            Constants.isGameRestarted = true;
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                gameStateManager.push(new Stage_1(gameStateManager));
            }
            if(Gdx.input.justTouched()) {
                Constants.isTouchUp = false; gameStateManager.push(new TemporaryScreen(gameStateManager));
            }
        }

        if (!is_arrow) {
            if (InputController.isRightKeyDown && !InputController.isLeftKeyDown) {
                player.move("right", TOUCH_POSITION);
            }
            if (InputController.isLeftKeyDown && !InputController.isRightKeyDown) {
                player.move("left", TOUCH_POSITION);
            }
            if (!InputController.isLeftKeyDown && !InputController.isRightKeyDown) {
                player.move("stop", TOUCH_POSITION);
            }
            if (Gdx.input.isTouched()) {
            TOUCH_POSITION.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(TOUCH_POSITION);
            player.move("touch",TOUCH_POSITION);
            }
            else if (Constants.isTouchUp) {
            Constants.isTouchUp = false;
            }
        }

        else {
            if (InputController.isRightKeyDown && !InputController.isLeftKeyDown && arrow.getRotation()<=60 && arrow.getRotation()>= -60) {
                arrow.setRotation(arrow.getRotation() -2);
            }
            if (InputController.isLeftKeyDown && !InputController.isRightKeyDown && arrow.getRotation()>=-60 && arrow.getRotation()<=60) {
                arrow.setRotation(arrow.getRotation() +2);
            }
            if (Gdx.input.isTouched()) {
            TOUCH_POSITION.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(TOUCH_POSITION);
            arrow.setRotation(-(TOUCH_POSITION.x-12) * 7.5f );
            }
            if (arrow.getRotation()<-60) {
                arrow.setRotation(-60);
            }
            else if (arrow.getRotation()>60) {
                arrow.setRotation(60);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                is_arrow = false;
                is_start = true;
            }
            else if (Constants.isTouchUp && !Constants.isGameRestarted) {
                is_arrow = false;
                is_start = true;
                Constants.isTouchUp = false;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {}
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {}
    }

    public void create_platforms() {
        platforms.add(new Platform(4,20, WORLD,0,false));
        platforms.add(new Platform(20,20, WORLD,0,false));
        platforms.add(new Platform(7,27, WORLD,90,false));
        platforms.add(new Platform(17,27, WORLD,180,false));
        platforms.add(new Platform(3.5f,24.5f, WORLD,125,true));
        platforms.add(new Platform(20.5f,24.5f, WORLD,145,true));
        platforms.add(new Platform(12,28, WORLD,1.56f,true));

        platforms.add(new Platform(3.3f,15, WORLD,0,true));
        platforms.add(new Platform(6.8f,15, WORLD,0,true));
        platforms.add(new Platform(10.3f,15, WORLD,0,true));
        platforms.add(new Platform(13.8f,15, WORLD,0,true));
        platforms.add(new Platform(17.3f,15, WORLD,0,true));
        platforms.add(new Platform(20.8f,15, WORLD,0,true));

        platforms.add(new Platform(2f,13f, WORLD,0.35f,true));
        platforms.add(new Platform(22f,13f, WORLD,-0.35f,true));

        platforms.add(new Platform(2f,39, WORLD,0,true));
        platforms.add(new Platform(6f,39, WORLD,0,true));
        platforms.add(new Platform(10f,39, WORLD,0,true));
        platforms.add(new Platform(14.1f,39, WORLD,0,true));
        platforms.add(new Platform(18f,39, WORLD,0,true));
        platforms.add(new Platform(22f,39, WORLD,0,true));
    }

    public void create_light() {
        rayHandler = new RayHandler(WORLD);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.setAmbientLight(0,0,0,0.25f);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(7);
        //rayHandler.setGammaCorrection(true);

        Color ball_c = new Color(1,1,1,1f);
        lightBall = new PointLight(rayHandler,100, ball_c,2f,15,3);
        lightBall.attachToBody(ball.getBody());

        Color sun_c = new Color(0.8f,0.8f,0,0.5f);
        Color sun_c_2 = new Color(0.5f,0.5f,0.5f,1f);
        lightSun1 = new PointLight(rayHandler,1000, sun_c,40,15,3);
        lightSun1.attachToBody(sunAndMoon.getBody("sun"));
        lightSun1.setXray(false);
        lightSun1.setContactFilter(Constants.FILTER_LIGHT);
        lightSun2 = new PointLight(rayHandler,1000, sun_c_2,60,15,3);
        lightSun2.attachToBody(sunAndMoon.getBody("sun"));
        lightSun2.setXray(true);
        lightSun2.setContactFilter(Constants.FILTER_LIGHT);

        Color moon_c = new Color(0,0,0.3f,0.75f);
        Color moon_c2 = new Color(0.15f,0.15f,0.50f,0.25f);
        lightMoon1 = new PointLight(rayHandler,1000, moon_c,11,15,3);
        lightMoon1.attachToBody(sunAndMoon.getBody("moon"));
        lightMoon1.setXray(false);
        lightMoon1.setContactFilter(Constants.FILTER_LIGHT);
        lightMoon2 = new PointLight(rayHandler,1000, moon_c2,35,15,3);
        lightMoon2.attachToBody(sunAndMoon.getBody("moon"));
        lightMoon2.setXray(true);
        lightMoon2.setContactFilter(Constants.FILTER_LIGHT);
    }

    public void musicMasterVolume() {
        if (is_vortex_glowing) {
            SOUND.switchDayAndNightEnvironmentVolume("Day", 0);
            SOUND.switchDayAndNightEnvironmentVolume("Night", 0);
        }
        else if (sunAndMoon.getBody("sun").getPosition().y > 0) {
            SOUND.switchDayAndNightEnvironmentVolume("Day", 1);
            SOUND.switchDayAndNightEnvironmentVolume("Night", 0);
        }
        else {
            SOUND.switchDayAndNightEnvironmentVolume("Day", 0);
            SOUND.switchDayAndNightEnvironmentVolume("Night", 1);
        }
    }

    public void ballSpeedLimiter() {
        float x = ball.getBody().getLinearVelocity().x;
        float y = ball.getBody().getLinearVelocity().y;
        float absX = Math.abs(x);
        float absY = Math.abs(y);
        int speedLimitMax = 31;
        int speedLimitMin = 11;

        /*
        if (absX + absY < 20) {
            font.setColor(Color.GREEN);
        }
        else if (absX + absY >= 20 && absX + absY < 25) {
            font.setColor(Color.YELLOW);
        }
        else {
            font.setColor(Color.RED);
        }
        */

        if (absX + absY > speedLimitMax) {
            x = x >= 0 ? speedLimitMax * (float) 0.01 * (absX / ((absX + absY) / 100)) : -speedLimitMax * (float) 0.01 * (absX / ((absX + absY) / 100));
            y = y >= 0 ? speedLimitMax * (float) 0.01 * (absY / ((absX + absY) / 100)) : -speedLimitMax * (float) 0.01 * (absY / ((absX + absY) / 100));
            ball.getBody().setLinearVelocity(x,y);
        }
        else if (absX + absY < speedLimitMin && is_start2) {
            x = x >= 0 ? speedLimitMin * (float) 0.01 * (absX / ((absX + absY) / 100)) : -speedLimitMin * (float) 0.01 * (absX / ((absX + absY) / 100));
            y = y >= 0 ? speedLimitMin * (float) 0.01 * (absY / ((absX + absY) / 100)) : -speedLimitMin * (float) 0.01 * (absY / ((absX + absY) / 100));
            ball.getBody().setLinearVelocity(x,y);
        }
        else if (absX + absY == 0) {
            ball.getBody().setLinearVelocity(0,speedLimitMin);
        }
        else if (absX == 0) {
            ball.getBody().applyLinearImpulse(0.01f,0,0.01f,0,false);
        }
    }

    public void activatePortal() {
        if (!is_vortex_glowing) {
            portalLight = new PointLight(rayHandler, 100, Color.BLUE, 0, 40, 3);
            portalLight.attachToBody(vortex.getBody());
            portalLight.setXray(true);
            is_vortex_glowing = true;
        }
        if (portalLight.getDistance() < 10) {
            portalLight.setDistance(portalLight.getDistance() + 0.01f);
        }
    }

    public void endGame() {
        if (vortex.getSPRITE().getScaleX() < 37) {
            vortex.getSPRITE().scale(0.06f);
        }
        lightSun1.setDistance(lightSun1.getDistance()-0.15f);
        lightSun2.setDistance(lightSun2.getDistance()-0.15f);
        lightMoon1.setDistance(lightMoon1.getDistance()-0.15f);
        lightMoon2.setDistance(lightMoon2.getDistance()-0.15f);
        lightBall.setDistance(lightBall.getDistance()-0.15f);
        portalLight.setDistance(portalLight.getDistance()-0.15f);
    }

    public void ballOutTheScreenCheck() {
        if ((ball.getPosition().y <= 1 && player.isAlive)
                || (ball.getPosition().y >= 40 && Constants.isVortexActive && player.isAlive)
                || (ball.getPosition().x <= 0 && Constants.isVortexActive && player.isAlive)
                || (ball.getPosition().x >= 24 && Constants.isVortexActive && player.isAlive)) {
            player.isAlive = false;
            SOUND.play("DEATH");
        }
    }

    public void body_delete() {
        for (Body B : Constants.destroyList) {
            if (!WORLD.isLocked() && B != null && Constants.isBodyDeleting) {
                WORLD.destroyBody(B);
                Constants.isBodyDeleting = false;
                SOUND.play("platform_broke");
            }
        }
        Constants.destroyList.clear();
    }

    public void dispose() {
        background.dispose();
        rayHandler.dispose();
        batch.dispose();
        SPRITE_BATCH.dispose();
        DEBUG_RENDERER.dispose();
        WORLD.dispose();
    }

    public void resize (int width, int height) {
        EXTENDED_VIEWPORT.update(width, height, false);
        SPRITE_BATCH.setProjectionMatrix(camera.combined);
        rayHandler.setCombinedMatrix(camera.combined);
        this.width = width;
        this.height = height;
    }
}





