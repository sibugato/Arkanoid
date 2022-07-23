package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.sibugato.arkanoid_souls.Resources;

import java.util.Random;

public class Bird {

    private Sprite bird = new Sprite();
    private Sprite bird2 = new Sprite();
    private final Sprite CHAIN = new Sprite(new Texture("chain.png"));
    private final Sprite CHAIN2 = new Sprite(new Texture("chain.png"));
    private final Animator ANIMATION_RIGHT = new Animator(Resources.TEXTURE_ATLAS.findRegion("AnimationBird"),3,3,0.1f,true,false);
    private final Animator ANIMATION_LEFT = new Animator(Resources.TEXTURE_ATLAS.findRegion("AnimationBird"),3,3,0.1f,true,true);
    private final Animator ANIMATION_RIGHT_2 = new Animator(Resources.TEXTURE_ATLAS.findRegion("AnimationBird"),3,3,0.105f,true,false);
    private final Animator ANIMATION_LEFT_2 = new Animator(Resources.TEXTURE_ATLAS.findRegion("AnimationBird"),3,3,0.105f,true,true);
    private Vector2 position;
    private Platform platform;
    private boolean isReverse;
    private float speed = 5;

    public Bird (int x, int y, World world) {
        position = new Vector2(x,y);
        platform = new Platform(x,y,world,0,false);
        bird.setSize(2.7f,2.7f);
        bird.setOrigin(bird.getWidth()/2, bird.getHeight()/2);
        bird2.setSize(2.7f,2.7f);
        bird2.setOrigin(bird.getWidth()/2, bird.getHeight()/2);
        CHAIN.setSize(1,1*(CHAIN.getHeight()/ CHAIN.getWidth()));
        CHAIN.setOrigin(bird.getWidth()/2, bird.getHeight()/2);
        CHAIN2.setSize(1,1*(CHAIN.getHeight()/ CHAIN.getWidth()));
        CHAIN2.setOrigin(bird.getWidth()/2, bird.getHeight()/2);
        CHAIN.setRotation(15);
        CHAIN2.setRotation(-15);
    }

    public void update(float dt) {

        if (platform.getPosition().x <= -10) {
            isReverse = true;
            speed = (float)((new Random().nextInt(5)-2) + 5);
        }
        else if(platform.getPosition().x >=34) {
            isReverse =false;
            speed = (float)((new Random().nextInt(5)-2) + 5);
        }

        if (isReverse) {
            platform.getBody().setTransform(platform.getPosition().x+= speed* Gdx.graphics.getDeltaTime(), platform.getPosition().y,platform.getBody().getAngle() + (float)((new Random().nextInt(3)-1)*0.003));
        }
        else {
            platform.getBody().setTransform(platform.getPosition().x-= speed* Gdx.graphics.getDeltaTime(), platform.getPosition().y,platform.getBody().getAngle() + (float)((new Random().nextInt(3)-1)*0.003));
        }

        if (platform.getBody().getAngle() > 0.15) {
            platform.getBody().setTransform(platform.getPosition().x, platform.getPosition().y, (float)0.15);
        }
        else if (platform.getBody().getAngle() < -0.15) {
            platform.getBody().setTransform(platform.getPosition().x, platform.getPosition().y, (float)-0.15);
        }

        float x = platform.getPosition().x;
        float y = platform.getPosition().y;
        bird.setPosition(x-3f, y+0.9f);
        bird2.setPosition(x+0.4f, y+1.05f);
        CHAIN.setPosition(x-2.2f, y+0.2f);
        CHAIN2.setPosition(x+1.2f, y+0.05f);
    }

    public void render (SpriteBatch sb) {
       if (isReverse) {
           bird.setRegion(ANIMATION_RIGHT.getCurrentFrame());
           bird2.setRegion(ANIMATION_RIGHT_2.getCurrentFrame());
       }
       else {
           bird.setRegion(ANIMATION_LEFT.getCurrentFrame());
           bird2.setRegion(ANIMATION_LEFT_2.getCurrentFrame());
       }
        CHAIN.draw(sb);
        CHAIN2.draw(sb);
        bird.draw(sb);
        bird2.draw(sb);
        platform.render(sb);
    }
}