package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sibugato.arkanoid_souls.BodyMaker;
import com.sibugato.arkanoid_souls.Constants;
import com.sibugato.arkanoid_souls.Resources;

import stages.Stage_1;

public class Ball {

    private final Sprite SPRITE;
    private final Vector2 POSITION;
    private final Body BODY;
    private float rotate = 11f;
    public static boolean isBounce = false;

    public Ball (int x, int y, World world) {
        BODY = BodyMaker.createCircle(world,x,y,0.45f, BodyDef.BodyType.DynamicBody,Constants.FILTER_BALL,0.05f,0,1,0, true,"ball");
        POSITION = new Vector2(x,y);
        SPRITE = new Sprite(new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Ball")));
        SPRITE.setSize(4,4);
        SPRITE.setOrigin(SPRITE.getWidth()/2, SPRITE.getHeight()/2);
    }

    public void update(float dt) {
        POSITION.set(BODY.getPosition());
        SPRITE.setPosition(POSITION.x-2f, POSITION.y-2f);
        SPRITE.setRotation(rotate += 2000 * Gdx.graphics.getDeltaTime());
        BODY.setLinearVelocity(BODY.getLinearVelocity().x*1.001f, BODY.getLinearVelocity().y*1.0001f);
    }

    public void render (SpriteBatch sb) {
        SPRITE.draw(sb);
    }

    public Vector2 getPosition() {
        return POSITION;
    }

    public Body getBody() {
        return BODY;
    }

    public  Sprite getSPRITE() {
        return SPRITE;
    }
}