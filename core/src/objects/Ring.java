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

public class Ring {

    private Sprite sprite = new Sprite(new Texture("LOTR.png"));
    private Vector2 position;
    private Body body;
    float rotate = 0;



    public Ring(int x, int y, World world) {
        position = new Vector2(x,y);
        body = BodyMaker.createCircle(world,x,y,3f, BodyDef.BodyType.StaticBody, Constants.FILTER_CIRCLE, 0.1f, 0.1f,0.1f,0,false,"circle");
        sprite.setSize(6,6);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.setPosition(x-3f,y-3f);
    }

    public void update(float dt) {
        rotate += 20 * Gdx.graphics.getDeltaTime();;
        sprite.setRotation(rotate);
    }

    public void render (SpriteBatch sb) {
        //sb.draw(animation.get_current_frame(),body.getPosition().x-2,body.getPosition().y-2,4,4 );
        sprite.draw(sb);
    }


    public Vector2 get_position() { return position; }
    public Body getBody() {return body;}
    public Sprite getSprite() { return sprite; }

}