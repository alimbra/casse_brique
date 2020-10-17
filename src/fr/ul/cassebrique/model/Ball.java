package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import fr.ul.cassebrique.dataFactories.TextureFactory;

public class Ball {

    public static final float RADIUS = 12f;

    private GameWorld gameWorld;
    private Vector2 position;
    private Body bodyBall;
    private Vector2 velocity;


    public Ball(GameWorld gameWorld) {
        this.gameWorld = gameWorld;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.bullet = true;
        bodyDef.fixedRotation = false;
        bodyBall = gameWorld.getWorld().createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape ballShape = new CircleShape();
        fixtureDef.shape = ballShape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;
        bodyBall.createFixture(fixtureDef);
        ballShape.dispose();
    }

    public Ball(GameWorld gameWorld, Vector2 position) {
        this.gameWorld = gameWorld;
        this.position = position;
        this.position.add(RADIUS, RADIUS);

        // creation du body
        createBallBodyDef(gameWorld.getWorld());

        // creation de la fixture
        createBallFixtureDef();

        //this.setTransform(position, 0f);
        bodyBall.setTransform(position.x * GameWorld.getPixelsToMeters(),
                position.y * GameWorld.getPixelsToMeters(), 0);
    }


    private void createBallBodyDef(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);
        bodyDef.bullet = true;
        bodyDef.fixedRotation = false;

        bodyBall = world.createBody(bodyDef);
        bodyBall.setUserData(this);
    }


    private void createBallFixtureDef() {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;

        // creation du circularshape
        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(Ball.RADIUS * GameWorld.getPixelsToMeters());

        fixtureDef.shape = ballShape;
        bodyBall.createFixture(fixtureDef);

        ballShape.dispose();
    }


    public Body getBodyBall() {
        return bodyBall;
    }


    public Vector2 getPosition() {
        return bodyBall.getPosition();
    }

    public boolean isOutOfBounds() {
        return false;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }


    public void setTransform(Vector2 position, float angle) {
        float x = position.x * GameWorld.getPixelsToMeters();
        float y = position.y * GameWorld.getPixelsToMeters();
        bodyBall.setTransform(position.x, position.y, angle);
    }

    public void setSpeed(float x, float y) {
        bodyBall.setLinearVelocity(x, y);
    }


    public void draw(SpriteBatch spriteBatch) {
        //System.out.println("ballx = " + (bodyBall.getPosition().x - RADIUS) * GameWorld.getPixelsToMeters() + " bally = " + (bodyBall.getPosition().y - RADIUS) * GameWorld.getPixelsToMeters());
        spriteBatch.draw(TextureFactory.getTexBall(), bodyBall.getPosition().x * GameWorld.getMetersToPixels() - RADIUS,
                bodyBall.getPosition().y * GameWorld.getMetersToPixels() - RADIUS);
    }
}
