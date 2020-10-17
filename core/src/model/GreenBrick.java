package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import dataFactories.TextureFactory;

/**
 * Created by AS on 06/02/2018.
 */

public class GreenBrick extends Brick {
    private float tempsAnime;
    public GreenBrick(Vector2 position,int nbCoup,GameWorld gameWorld) {
        super(position, nbCoup,gameWorld);
        nomRessource="images/Anim2Ca.pack";
        TextureAtlas textureAtlas=new TextureAtlas(Gdx.files.internal(nomRessource));
        Array<TextureAtlas.AtlasRegion> listeIms =textureAtlas.findRegions("Anim2Ca");
        animation= new Animation(0.05f, listeIms, Animation.PlayMode.LOOP);
        tempsAnime=0;
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
            tempsAnime=tempsAnime+Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = (TextureRegion) animation.getKeyFrame(tempsAnime, true);
            spriteBatch.draw(currentFrame,position.x,position.y);
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
