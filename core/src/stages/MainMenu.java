package stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.sibugato.arkanoid_souls.ArkanoidSouls;
import com.sibugato.arkanoid_souls.Constants;
import com.sibugato.arkanoid_souls.InputController;

import states.GameStateManager;
import states.State;

public class MainMenu extends State {

    private ExtendViewport extendViewport;
    private Texture background  = new Texture("Main_Menu_Background.jpg");
    InputController inputController = new InputController();
    SpriteBatch spriteBatch = new SpriteBatch();

    public MainMenu(GameStateManager gsm) {
        super(gsm);
        Gdx.input.setInputProcessor(inputController);
        extendViewport = new ExtendViewport((float) ArkanoidSouls.WIDTH/20, (float) ArkanoidSouls.HEIGHT/20, (float) ArkanoidSouls.WIDTH*2, (float) ArkanoidSouls.HEIGHT*2, camera);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gameStateManager.push(new Stage_1(gameStateManager));
        }
        if (Constants.isTouchUp) {
            Constants.isTouchUp = false; gameStateManager.push(new Stage_1(gameStateManager));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(background,0,0,background.getWidth()/20,background.getHeight()/20);
        spriteBatch.end();
    }

    @Override
    public void dispose() {}

    public void resize (int width, int height) {
        extendViewport.update(width, height, false);
        spriteBatch.setProjectionMatrix(camera.combined);
    }
}
