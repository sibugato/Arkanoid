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

    public void ballSpeedLimiter() {
        float x = BODY.getLinearVelocity().x;
        float y = BODY.getLinearVelocity().y;
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
            BODY.setLinearVelocity(x,y);
        }
        else if (absX + absY < speedLimitMin && Stage_1.is_start2) {
            x = x >= 0 ? speedLimitMin * (float) 0.01 * (absX / ((absX + absY) / 100)) : -speedLimitMin * (float) 0.01 * (absX / ((absX + absY) / 100));
            y = y >= 0 ? speedLimitMin * (float) 0.01 * (absY / ((absX + absY) / 100)) : -speedLimitMin * (float) 0.01 * (absY / ((absX + absY) / 100));
            BODY.setLinearVelocity(x,y);
        }
        else if (absX + absY == 0) {
            BODY.setLinearVelocity(0,speedLimitMin);
        }
        else if (absX == 0) {
            BODY.applyLinearImpulse(0.01f,0,0.01f,0,false);
        }
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