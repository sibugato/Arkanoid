package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sibugato.arkanoid_souls.BodyMaker;
import com.sibugato.arkanoid_souls.Constants;
import com.sibugato.arkanoid_souls.Resources;

public class Bell {

    private Sprite sprite;
    private Vector2 position;
    private Body body;

    private Sprite
            bellImpulse1 = new Sprite(new Texture("bellImpulse.png")),
            bellImpulse2 = new Sprite(new Texture("bellImpulse.png")),
            bellImpulse3 = new Sprite(new Texture("bellImpulse.png"));

    private float bellImpulseAlpha = 0.5f;
    private boolean isHit = false;

    public Bell(float x, float y, World world, String number) {
        position = new Vector2(x,y);
        Vector2[] shape = {new Vector2(0, 0), new Vector2(2.5f, 0), new Vector2(2.5f, 0.225f), new Vector2(1.8f, 2.1f), new Vector2(0.6f, 2.1f),  new Vector2(0, 0.225f),new Vector2(0,0)};
        body = BodyMaker.createChain(world,x,y,shape, BodyDef.BodyType.StaticBody,Constants.FILTER_PLAYER,1,0,0, 0,true,number);

        sprite = new Sprite(Resources.TEXTURE_ATLAS.findRegion("bell"));
        sprite.setSize(2.5f,10);
        sprite.setPosition(x,y-0.59f);
        float bellImpulseSize = 2;
        bellImpulse1.setSize(bellImpulseSize,bellImpulseSize);
        bellImpulse2.setSize(bellImpulseSize,bellImpulseSize);
        bellImpulse3.setSize(bellImpulseSize,bellImpulseSize);
        bellImpulse1.setPosition(x+0.2f,y);
        bellImpulse2.setPosition(x+0.2f,y);
        bellImpulse3.setPosition(x+0.2f,y);
        bellImpulse1.setOrigin(bellImpulse1.getWidth()/2, bellImpulse1.getHeight()/2);
        bellImpulse2.setOrigin(bellImpulse2.getWidth()/2, bellImpulse2.getHeight()/2);
        bellImpulse3.setOrigin(bellImpulse3.getWidth()/2, bellImpulse3.getHeight()/2);

    }

    public void update(float dt) {
        position.set(body.getPosition().x, body.getPosition().y);
    }

    public void render (SpriteBatch sb) {
        sprite.draw(sb);
        if (isHit && bellImpulseAlpha > 0) {

            bellImpulseAlpha = bellImpulseAlpha - 0.002f;

            bellImpulse1.draw(sb);
            bellImpulse1.scale(0.15f);
            bellImpulse1.setAlpha(bellImpulseAlpha);

            bellImpulse2.draw(sb);
            bellImpulse2.scale(0.1f);
            bellImpulse2.setAlpha(bellImpulseAlpha + 0.1f);

            bellImpulse3.draw(sb);
            bellImpulse3.scale(0.05f);
            bellImpulse3.setAlpha(bellImpulseAlpha + 0.2f);
        }
    }

    public Vector2 getPosition() {
        return position;
    }
    public Sprite getSprite() {
        return sprite;
    }
    public Body getBody() {
        return body;
    }
    public void setIsHit() {
        isHit = true;
    }
}