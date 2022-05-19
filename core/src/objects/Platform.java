package objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sibugato.arkanoid_souls.BodyMaker;
import com.sibugato.arkanoid_souls.Constants;
import com.sibugato.arkanoid_souls.Resources;

public class Platform {

    private Vector2 position;
    private Body body;

    Sprite sprite = new Sprite();
    TextureRegion texture_1hp = new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Platform1hp"));
    TextureRegion texture_2hp = new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Platform2hp"));
    TextureRegion texture_3hp = new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Platform3hp"));
    TextureRegion texture_4hp = new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Platform4hp"));
    TextureRegion texture_5hp = new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Platform5hp"));
    TextureRegion texture = new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Platform"));

    public Platform(float x, float y, World world, float angle, boolean isDestructible) {
        if (isDestructible) {
            body = BodyMaker.createBox(world,x,y,1.5f,0.5f, BodyDef.BodyType.DynamicBody,Constants.FILTER_PLATFORM,
                    1.5f,11,0.1f,1, true,"platform5");
        }
        else {
            body = BodyMaker.createBox(world, x, y, 1.5f, 0.5f, BodyDef.BodyType.StaticBody, Constants.FILTER_PLATFORM,
                    0.8f, 0.85f, 0.15f, 1, true, "platform");
        }

        position = new Vector2(x,y);
        body.setTransform(position, angle);
        body.setSleepingAllowed(false);
        sprite.setSize(3f, 1f);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
    }

    public void update(float dt) { position.set(body.getPosition()); }

    public void render(SpriteBatch sb) {
        sprite.setPosition(body.getPosition().x-1.5f,body.getPosition().y-0.5f);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

        if (body.getFixtureList().get(0).getUserData().equals("platform5")) sprite.setRegion(texture_5hp);
        else if (body.getFixtureList().get(0).getUserData().equals("platform4")) sprite.setRegion(texture_4hp);
        else if (body.getFixtureList().get(0).getUserData().equals("platform3")) sprite.setRegion(texture_3hp);
        else if (body.getFixtureList().get(0).getUserData().equals("platform2")) sprite.setRegion(texture_2hp);
        else if (body.getFixtureList().get(0).getUserData().equals("platform1")) sprite.setRegion(texture_1hp);
        else sprite.setRegion(texture);

        if (!body.getFixtureList().get(0).getUserData().equals("platform0")) sprite.draw(sb);
    }

    public Vector2 getPosition() { return position; }
    public Body getBody() {return body;}



}

