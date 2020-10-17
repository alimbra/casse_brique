package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by blaaziz1u on 02/02/18.
 */

public class GreenBrick extends Brick {


    public GreenBrick(Vector2 position) {
        super(position);
        texture = TextureFactory.getTexGreenBrickA();
    }


    public GreenBrick() {
        super();
        texture = TextureFactory.getTexGreenBrickA();
    }

    @Override
    protected void checkNumberCollisons() {
        if(numberOfCollisions == 1) {
            texture = TextureFactory.getTexGreenBrickB();
        } else if(numberOfCollisions == 2)
            texture = null;
    }


    @Override
    public void setNumberOfCollisions(int numberOfCollisions) {
        super.setNumberOfCollisions(numberOfCollisions);
        checkNumberCollisons();
    }


    @Override
    public void draw(SpriteBatch spriteBatch) {
        if(texture != null)
            spriteBatch.draw(texture, position.x, position.y);
    }
}

