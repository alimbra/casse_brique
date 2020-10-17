package model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import java.util.Random;

import dataFactories.TextureFactory;

/**
 * Created by AS on 07/02/2018.
 */

public class Ball {
    public static int rayon=12;
    private GameWorld gw;
    private Body body;
    CircleShape circleShape=new CircleShape();
    public Ball(Vector2 position,GameWorld gameWorld){
        gw=gameWorld;

        BodyDef bodyDef=new BodyDef();
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.bullet=true;
        bodyDef.fixedRotation=false;
        bodyDef.position.set(0,0);

        body=gw.getMonde().createBody(bodyDef);
        FixtureDef fixtureDef=new FixtureDef();
        circleShape.setRadius(Ball.rayon*GameWorld.getPixelsToMeters());


        fixtureDef.shape=circleShape;
        fixtureDef.density=1;
        fixtureDef.friction=0;
        fixtureDef.restitution=1;
        body.createFixture(fixtureDef);
        body.setTransform(new Vector2(
                position.x*GameWorld.getPixelsToMeters(),position.y*GameWorld.getPixelsToMeters()),0);
        body.setUserData("Ball");

        circleShape.dispose();
    }
    public void draw(SpriteBatch sb){

        sb.draw(TextureFactory.getTexBall()
                ,(body.getPosition().x*GameWorld.getMetersToPixels()-Ball.rayon)
                ,(body.getPosition().y*GameWorld.getMetersToPixels()-Ball.rayon));
       /* ShapeRenderer shapeRenderer=new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(body.getPosition().x,body.getPosition().y,20);


        shapeRenderer.end();*/

    }

    public Body getBody() {
        return body;
    }

    public void setSpeed(){
        body.setLinearVelocity(-1.5f,1.5f);
    }
    public float getX(){
        return body.getPosition().x;
    }
    public float getY(){
        return body.getPosition().y;
    }
    public boolean estSortieJeu(){
        if (body.getPosition().x+rayon*GameWorld.getPixelsToMeters()<0
        || body.getPosition().y+rayon*GameWorld.getPixelsToMeters()<0){
//            System.out.println("pos"+body.getPosition());

            return true;
        }
        return false;
    }

}
