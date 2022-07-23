package states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

    protected OrthographicCamera camera;
    protected GameStateManager gameStateManager;

    public State(GameStateManager gsm) {
        this.gameStateManager = gsm;
        camera = new OrthographicCamera();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render ();
    public abstract void dispose();
    public abstract void resize(int width, int height);

}
