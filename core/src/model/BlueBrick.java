package model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import dataFactories.TextureFactory;

/**
 * Created by AS on 06/02/2018.
 */

public class BlueBrick extends Brick {

    public BlueBrick(Vector2 position,GameWorld gw) {
        super(position, 1,gw);
    }

    public void draw(SpriteBatch spriteBatch){
        if (nbCoup==1) {
            spriteBatch.draw(TextureFactory.getTexBlueBrick(),
                    position.x,
                    position.y);
        }
    }

    @Override
    public void dispose() {
        body.setActive(false);
    }
}
