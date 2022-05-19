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

    private ExtendViewport evp;
    private Texture background  = new Texture("Main_Menu_Background.jpg");
    InputController INPUT = new InputController();
    SpriteBatch sb = new SpriteBatch();


    public MainMenu(GameStateManager gsm) {
        super(gsm);
        Gdx.input.setInputProcessor(INPUT);
        camera.setToOrtho(false, (float)  ArkanoidSouls.WIDTH/20, (float) ArkanoidSouls.HEIGHT/20);
        evp = new ExtendViewport((float) ArkanoidSouls.WIDTH/20, (float) ArkanoidSouls.HEIGHT/20,
                (float) ArkanoidSouls.WIDTH*2, (float) ArkanoidSouls.HEIGHT*2, camera);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))  gsm.push(new Stage_1(gsm));
        if(Constants.isTouchUp) { Constants.isTouchUp = false; gsm.push(new Stage_1(gsm));}
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render() {
        sb.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(background,0,0,background.getWidth()/20,background.getHeight()/20);
        sb.end();
    }

    @Override
    public void dispose() { }

    public void resize (int width, int height) {
        evp.update(width, height, false);
        sb.setProjectionMatrix(camera.combined);

    }
}
