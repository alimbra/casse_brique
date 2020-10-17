package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by blaaziz1u on 26/01/18.
 */

public abstract class Brick {

    protected int numberOfCollisions = 0;
    protected Vector2 position;
    protected Texture texture;
    protected Body bodyBrick;


    protected Brick(Vector2 position) {
        this.position = position;
    }

    protected Brick() {
        position = new Vector2(0,0);
    }

    protected void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        bodyDef.fixedRotation = false;
        //bodyDef.bullet = true;
        bodyBrick = world.createBody(bodyDef);
        bodyBrick.setUserData(this);
        createFixture();
    }

    protected void createFixture() {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(new float[]{position.x * GameWorld.getPixelsToMeters(), position.y * GameWorld.getPixelsToMeters(),
                (position.x + Constantes.BRICK_WIDTH) * GameWorld.getPixelsToMeters(), position.y * GameWorld.getPixelsToMeters(),
                (position.x + Constantes.BRICK_WIDTH) * GameWorld.getPixelsToMeters(), (position.y + Constantes.BRICK_HEIGHT) * GameWorld.getPixelsToMeters(),
                position.x * GameWorld.getPixelsToMeters(), (position.y + Constantes.BRICK_HEIGHT) * GameWorld.getPixelsToMeters()
        });


        fixtureDef.shape = polygonShape;
        bodyBrick.createFixture(fixtureDef);
        polygonShape.dispose();
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getNumberOfCollisions() {
        return numberOfCollisions;
    }

    public Body getBodyBrick() {
        return bodyBrick;
    }

    public Texture getTexture() {
        return texture;
    }

    protected abstract void checkNumberCollisons();

    protected void collide() {
        numberOfCollisions++;
        checkNumberCollisons();
    }


    public void setNumberOfCollisions(int numberOfCollisions) {
        this.numberOfCollisions = numberOfCollisions;
        checkNumberCollisons();
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, position.x, position.y);
    }

}
