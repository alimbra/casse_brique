package model;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import javax.swing.text.Position;

import dataFactories.TextureFactory;

/**
 * Created by AS on 06/02/2018.
 */

public abstract class Brick  {
    protected Vector2 position;
    protected int nbCoup;
    protected Body body;
    protected GameWorld gameWorld;
    protected float[] fl;

    public Brick(Vector2 position,int nbCoup,GameWorld gw){
        gameWorld=gw;
        this.position=position;
        this.nbCoup=nbCoup;
        if(nbCoup==1 || nbCoup==2) {
            float x = (position.x)*GameWorld.getPixelsToMeters();
            float y = (position.y)*GameWorld.getPixelsToMeters();
            float x1 = (position.x + TextureFactory.getTexBlueBrick().getWidth())*GameWorld.getPixelsToMeters();
            float y1 = (position.y + TextureFactory.getTexBlueBrick().getHeight())*GameWorld.getPixelsToMeters();

            BodyDef bodyDef = new BodyDef();
            bodyDef.fixedRotation = false;
            bodyDef.bullet = true;
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(0, 0);
            FixtureDef fixtureDef = new FixtureDef();
            body = gameWorld.getMonde().createBody(bodyDef);
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.set(new Vector2[]{new Vector2(x, y), new Vector2(x, y1),
                    new Vector2(x1, y1), new Vector2(x1, y)});
            fixtureDef.shape = polygonShape;
            fixtureDef.density = 1;
            fixtureDef.friction = 0;
            fixtureDef.restitution = 1;
            body.createFixture(fixtureDef);
            body.setUserData("Brick");
            fl=new float[]{x, y,x, y1,x1, y1,x1, y};
            polygonShape.dispose();

        }
    }
    public abstract void dispose();
    public abstract void draw(SpriteBatch spriteBatch);
}
