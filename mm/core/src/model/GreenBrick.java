package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import dataFactories.TextureFactory;

/**
 * Created by AS on 06/02/2018.
 */

public class GreenBrick extends Brick {
    public GreenBrick(Vector2 position,int nbCoup,GameWorld gameWorld) {

        super(position, nbCoup,gameWorld);
    }

    @Override
    public void dispose() {
        body.setActive(false);
    }

    public void draw(SpriteBatch spriteBatch){
        if (nbCoup==2) {
            spriteBatch.draw(TextureFactory.getTexGreenBrickA(), position.x, position.y);
            /*ShapeRenderer shapeRenderer=new ShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.polygon(fl);


            shapeRenderer.end();*/
        }
        else if (nbCoup==1){
            spriteBatch.draw(TextureFactory.getTexGreenBrickB(), position.x, position.y);
       /*     ShapeRenderer shapeRenderer=new ShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.polygon(fl);


            shapeRenderer.end();
        */
        }


    }
}
