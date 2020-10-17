package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import dataFactories.TextureFactory;

/**
 * Created by AS on 06/02/2018.
 */

public class Background {

    private Body body;
    private ChainShape ch;

    private float getX=GameWorld.getPixelsToMeters()*TextureFactory.getTexBorder().getWidth();
    private float getY=0;//GameWorld.getPixelsToMeters()*TextureFactory.getTexBorder().getHeight();

    private float getXp=GameWorld.getPixelsToMeters()*(TextureFactory.getTextBack().getWidth()
                    -2*TextureFactory.getTexBorder().getWidth());
    private float getYp=GameWorld.getPixelsToMeters()*(TextureFactory.getTextBack().getHeight()
                - TextureFactory.getTexBorder().getHeight());
    private GameWorld gameWorld;

    public Background(GameWorld gw){
        gameWorld=gw;
        BodyDef bodyDef=new BodyDef();
        bodyDef.type= BodyDef.BodyType.StaticBody;
        bodyDef.bullet=true;
        bodyDef.fixedRotation=false;
        bodyDef.position.set(0,0);
        body=gameWorld.getMonde().createBody(bodyDef);
        ch=new ChainShape();
        FixtureDef fixtureDef=new FixtureDef();
        Vector2[] liste={new Vector2(getX,getY),new Vector2(getX,getYp),
                new Vector2(getXp,getYp), new Vector2(getXp,getY)};
        ch.createChain(liste);
        fixtureDef.shape=ch;
        fixtureDef.friction=0.5f;
        fixtureDef.restitution=0;

        body.createFixture(fixtureDef);
        body.setUserData("Background");
        ch.dispose();
    }


    public void draw(SpriteBatch spriteBatch){

        spriteBatch.draw(TextureFactory.getTextBack(),
                0,0);
    }

    public float getLargeur(){
        return TextureFactory.getTextBack().getWidth();
    }

    public float getLargeurBordure(){
        return TextureFactory.getTexBorder().getWidth();
    }

}
