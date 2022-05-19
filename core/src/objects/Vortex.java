package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.sibugato.arkanoid_souls.BodyMaker;
import com.sibugato.arkanoid_souls.Constants;
import com.sibugato.arkanoid_souls.Resources;

public class Vortex {

    private final Sprite SPRITE = new Sprite(new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("Vortex1")));
    private final Animator ANIMATION = new Animator(new TextureRegion(Resources.TEXTURE_ATLAS.findRegion("AnimationVortex")), 3,4,0.1f,false, false);
    private final Vector2 POSITION;
    private final Body BODY;

    private float rotate = 0;

    public Vortex (int x, int y, World world) {
        POSITION = new Vector2(x,y);
        SPRITE.setSize(4,4);
        SPRITE.setOrigin(SPRITE.getWidth()/2, SPRITE.getHeight()/2);
        SPRITE.setPosition(x-2, y-2);
        BODY = BodyMaker.createCircle(world, x, y, 1.5f, BodyDef.BodyType.StaticBody, Constants.FILTER_VORTEX, 0.5f,0,1,0,false,"vortex");
    }

    public void update(float dt) {
        if (SPRITE.getScaleX() <36) {
            rotate += 200 * Gdx.graphics.getDeltaTime();
        }
        else Gdx.app.exit();


        SPRITE.setRotation(rotate);
        if (Constants.isVortexActive) SPRITE.setRegion(ANIMATION.getCurrentFrame());
    }

    public void render (SpriteBatch sb) {
        SPRITE.draw(sb);
    }

    public Vector2 getPosition() { return POSITION; }
    public Body getBody() {return BODY;}
    public Sprite getSPRITE() { return SPRITE; }
    public boolean getAnimationIsEnd() { return ANIMATION.getIsEnd();}

}