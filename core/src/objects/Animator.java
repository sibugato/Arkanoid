package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator  {

    private final Animation<TextureRegion> ANIMATION;

    private float time;
    private float frameDuration;
    private TextureRegion[][] tmp;
    private TextureRegion[] animationFrames;

    private final boolean IS_FLIP;
    private boolean isLooping;
    private boolean isEnd = false;


    public Animator(Texture T, int ROWS, int COLUMNS, float Duration, boolean Looping, boolean Flip) {
        isLooping = Looping;
        IS_FLIP = Flip;
        frameDuration = Duration;
        time = 0f;
        tmp =  TextureRegion.split(T, T.getWidth() / COLUMNS, T.getHeight() / ROWS);
        animationFrames = new TextureRegion[ROWS*COLUMNS];

        int index = 0;

        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++)
                animationFrames[index++] = tmp[i][j];

        if (IS_FLIP) for (int i = 0; i < animationFrames.length ; i++)  animationFrames[i].flip(true,false);
        ANIMATION = new Animation<TextureRegion>(Duration, animationFrames);
    }

    public Animator(TextureRegion T, int ROWS, int COLUMNS, float Duration, boolean Looping, boolean Flip) {
        isLooping = Looping;
        IS_FLIP = Flip;
        frameDuration = Duration;
        time = 0f;
        final int width = T.getRegionWidth()/COLUMNS;
        final int height = T.getRegionHeight()/ROWS;
        tmp = new TextureRegion[ROWS][COLUMNS];

        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++)
                tmp[i][j] = new TextureRegion(T, width*j, height*i, width,height );

        animationFrames = new TextureRegion[ROWS*COLUMNS];

        int index = 0;
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++)
                animationFrames[index++] = tmp[i][j];

        if (IS_FLIP) for (int i = 0; i < animationFrames.length ; i++) animationFrames[i].flip(true,false);
        ANIMATION = new Animation<TextureRegion>(Duration, animationFrames);
    }

    public Animator(TextureAtlas T, String name, int FRAMES, float Duration, boolean Looping, boolean Flip) {
        frameDuration = Duration;
        IS_FLIP = Flip;
        time = 0f;
        isLooping = Looping;
        TextureRegion[] animationFrames = new TextureRegion[FRAMES];

        for (int i = 0; i < FRAMES ; i++) animationFrames[i] = T.findRegion(String.format(name+"%d", i));
        if (IS_FLIP) for (int i = 0; i < animationFrames.length ; i++) animationFrames[i].flip(true,false);
        ANIMATION = new Animation<TextureRegion>(Duration, animationFrames);
    }


    public TextureRegion getCurrentFrame() {
        time += Gdx.graphics.getDeltaTime();
        if (time>= frameDuration *animationFrames.length) isEnd = true;
        TextureRegion tmp;
        tmp = ANIMATION.getKeyFrame(time, isLooping);
        return tmp;
    }

    public boolean getIsEnd() {
        return isEnd;
    }

    public void reset() {
        time = 0;
        isEnd = false;
    }

}