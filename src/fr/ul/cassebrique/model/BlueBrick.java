package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import fr.ul.cassebrique.dataFactories.TextureFactory;

/**
 * Created by blaaziz1u on 02/02/18.
 */

public class BlueBrick extends Brick {


    public BlueBrick(Vector2 position) {
        super(position);
        texture = TextureFactory.getTexBlueBrick();
    }


    public BlueBrick() {
        super();
        texture = TextureFactory.getTexBlueBrick();
    }


    @Override
    protected void checkNumberCollisons() {
        if(numberOfCollisions == 1)
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
