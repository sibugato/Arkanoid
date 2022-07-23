package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sibugato.arkanoid_souls.ArkanoidSouls;
import com.sibugato.arkanoid_souls.BodyMaker;
import com.sibugato.arkanoid_souls.Constants;
import com.sibugato.arkanoid_souls.InputController;
import com.sibugato.arkanoid_souls.Resources;

public class Player {

    private final Animator ANIMATION_RUN_LEFT = new Animator(Resources.TEXTURE_ATLAS.findRegion("AnimationRun"),1,8,0.07f,true, false);
    private final Animator ANIMATION_RUN_RIGHT = new Animator(Resources.TEXTURE_ATLAS.findRegion("AnimationRun"),1,8,0.07f,true, true);
    private final Animator ANIMATION_DEATH_LEFT = new Animator(Resources.TEXTURE_ATLAS.findRegion("AnimationDeath"),1,11,0.1f,false, false);
    private final Animator ANIMATION_DEATH_RIGHT = new Animator(Resources.TEXTURE_ATLAS.findRegion("AnimationDeath"),1,11,0.1f,false, true);
    private final TextureRegion TEXTURE_STAY_LEFT = new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Stay"));
    private final TextureRegion TEXTURE_STAY_RIGHT = new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Stay"));

    public boolean isFacingRight = true;
    public boolean isAlive = true;
    public boolean isRun = false;

    private Vector2 position;
    private Body body;

    public Player(int x, int y, World world) {
        Vector2[] shape = {new Vector2(0, 0), new Vector2(3.99f, 0), new Vector2(3.99f, 0.225f), new Vector2(3.4f, 0.695f), new Vector2(0.6f, 0.695f),  new Vector2(0, 0.225f),new Vector2(0,0)};
        body = BodyMaker.createChain(world,x,y,shape, BodyDef.BodyType.StaticBody,Constants.FILTER_PLAYER,1,0,0, 0,true,"shield");
        position = new Vector2(x, y);
        TEXTURE_STAY_LEFT.flip(true,false);
    }

    public void update (float dt) {
        position.set(body.getPosition());
    }

    public void render (SpriteBatch sb) {
        sb.draw(animationUpdate(),position.x+0.5f,position.y-2.25f,3,3);
        if (isAlive) {
            sb.draw(Resources.TEXTURE_ATLAS.findRegion("Shield"), position.x, position.y,4,0.65f);
        }
    }

    public void move (String direction, Vector3 touchPos) {
        if (isAlive) {
            switch (direction) {
                case ("right"):
                    body.setTransform(position.x+= 15* Gdx.graphics.getDeltaTime(), position.y, 0);
                    isRun = true;
                    isFacingRight = true;
                    break;
                case ("left"):
                    body.setTransform(position.x-= 15* Gdx.graphics.getDeltaTime(), position.y, 0);
                    isRun = true;
                    isFacingRight = false;
                    break;
                case ("stop"):
                    isRun = false;
                    break;
                case ("touch"):
                    if (touchPos.x >= body.getPosition().x && touchPos.x - body.getPosition().x >= 2.5f) {
                        isFacingRight = true;
                        isRun = true;
                        body.setTransform(position.x+= 15* Gdx.graphics.getDeltaTime(), position.y, 0);
                        Constants.isTouchUp = false;
                        }
                    else if (touchPos.x - body.getPosition().x < 1.5f) {
                        isFacingRight = false;
                        isRun = true;
                        body.setTransform(position.x-= 15* Gdx.graphics.getDeltaTime(), position.y, 0);
                        }
                    break;
            }
            if (position.x < 0) {
                body.setTransform(0, body.getPosition().y,0);
            }
            if (position.x > (float) ArkanoidSouls.WIDTH / 20-4) {
                body.setTransform((float) ArkanoidSouls.WIDTH / 20-4, body.getPosition().y,0);
            }
        }
    }

    public TextureRegion animationUpdate() {
        if (isAlive) {
            if (isRun && InputController.isRightKeyDown && !InputController.isLeftKeyDown) {
                return ANIMATION_RUN_LEFT.getCurrentFrame();
            }
            else if (isRun && InputController.isLeftKeyDown && !InputController.isRightKeyDown) {
                return ANIMATION_RUN_RIGHT.getCurrentFrame();
            }
            else if (isRun && !isFacingRight) {
                return ANIMATION_RUN_RIGHT.getCurrentFrame();
            }
            else if (isRun && isFacingRight) {
                return ANIMATION_RUN_LEFT.getCurrentFrame();
            }
            else if (isFacingRight) {
                return TEXTURE_STAY_RIGHT;
            }
            else {
                return TEXTURE_STAY_LEFT;
            }
        }
        else {
            if (isFacingRight) {
                return ANIMATION_DEATH_LEFT.getCurrentFrame();
            }
            else {
                return ANIMATION_DEATH_RIGHT.getCurrentFrame();
            }
        }
    }

    public Body getBody() {
        return body;
    }
}
