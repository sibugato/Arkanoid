package objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sibugato.arkanoid_souls.BodyMaker;
import com.sibugato.arkanoid_souls.Constants;
import com.sibugato.arkanoid_souls.Resources;

public class SunAndMoon {

    Sprite sun = new Sprite(new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Sun")));
    Sprite moon = new Sprite(new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Moon")));

    private Body bodySun;
    private Body bodyAnchor;
    private Body bodyMoon;

    public SunAndMoon(int x, int y, World world) {
        bodySun = BodyMaker.createCircle(world,x,y+35,2f, BodyDef.BodyType.DynamicBody, Constants.FILTER_VOID,0.001f,0,0,0,false,"sun");
        bodyMoon = BodyMaker.createCircle(world,x,y-35,2f, BodyDef.BodyType.DynamicBody, Constants.FILTER_VOID,0.001f,0,0,0,false,"moon");
        bodyAnchor = BodyMaker.createCircle(world,x,y,3f, BodyDef.BodyType.StaticBody,Constants.FILTER_VOID,0,0,0,0,false,"anchor");
        BodyMaker.jointRevolute(world,bodyAnchor,bodySun,true,0.1f,0.1f);
        BodyMaker.jointRevolute(world,bodyAnchor,bodyMoon,true,0.1f,0.1f);
        sun.setSize(5f, 5f);
        moon.setSize(5f, 5f);
    }

    public void update(float dt) {
        sun.setPosition(bodySun.getPosition().x-2.5f, bodySun.getPosition().y-2.5f);
        moon.setPosition(bodyMoon.getPosition().x-2.5f, bodyMoon.getPosition().y-2.5f);
    }

    public void render (SpriteBatch sb) {
        sun.draw(sb);
        moon.draw(sb);
    }

    public Body getBody(String Name) {
        if (Name.equalsIgnoreCase("sun")) {
            return bodySun;
        }
        else if (Name.equalsIgnoreCase("moon")) {
            return bodyMoon;
        }
        else {
            return null;
        }
    }
}