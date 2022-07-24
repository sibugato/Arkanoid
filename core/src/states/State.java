package states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.sibugato.arkanoid_souls.ArkanoidSouls;

import stages.Stage_1;

public abstract class State {

    protected OrthographicCamera camera = new OrthographicCamera();
    protected GameStateManager gameStateManager;

    public State(GameStateManager gsm) {
        this.gameStateManager = gsm;
        camera.setToOrtho(false, (float)  ArkanoidSouls.WIDTH/20, (float) ArkanoidSouls.HEIGHT/20);
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render ();
    public abstract void dispose();
    public abstract void resize(int width, int height);

}
