package fr.ul.cassebrique.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.cassebrique.views.GameScreen;

import java.util.ArrayList;
import java.util.List;

import static fr.ul.cassebrique.model.Constantes.*;

/**
 * Created by blaaziz1u on 26/01/18.
 */

public class GameWorld {

    private static float METERS_TO_PIXELS = 250f;
    private static float PIXELS_TO_METERS = 1 / METERS_TO_PIXELS;

    private GameScreen gameScreen;
    private Background background;
    private Wall wall;
    private Racket racket;
    private World world;
    private List<Ball> ballList;
    private ArrayList<Body> listBodytoRemove;

    //private Box2DDebugRenderer renderer = new Box2DDebugRenderer();
    //private OrthographicCamera camera = new OrthographicCamera();


    public GameWorld(GameScreen gameScreen) {
        Box2D.init();
        this.world = new World(new Vector2(0, 0), true);
        this.gameScreen = gameScreen;
        this.background = new Background(world);
        this.wall = new Wall(this, true);
        this.racket = new Racket(this);
        this.ballList = new ArrayList<Ball>(3);
        this.listBodytoRemove = new ArrayList<Body>();

        int j = (int) (-3 * ((BRICK_RED_HEIGHT - BALL_HEIGHT) / 2));
        Ball ball;

        for (int i = 0; i < 3; i++, j+= BRICK_RED_HEIGHT) {
            if(i == 0) {
                ball = new Ball(this, new Vector2((BRICK_RED_WIDTH + (5 * BRICK_WIDTH) - (BALL_WIDTH / 2)), (50 + Racket.HEIGHT)));
                ball.setSpeed(0.8f, 1f);
            }
            else {
                ball = new Ball(this, new Vector2(((WORLD_WIDTH - BALLS_AREA_WIDTH) +
                        (BALLS_AREA_WIDTH / 2) - (BALL_HEIGHT / 2)), j));
            }
            //ball.setTransform(ball.getPosition(), 0);
            ballList.add(ball);
        }

        setWorldListener();
        //camera.position.set(0, 0 , 0);
        //camera.setToOrtho(false);
        //camera.update();
    }


    private void setWorldListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if(contact.isTouching()) {
                    Fixture a = contact.getFixtureA();
                    Fixture b = contact.getFixtureB();
                    WorldManifold worldManifold = contact.getWorldManifold();

                    if(a.getBody().getUserData() instanceof Ball) {
                        if(b.getBody().getUserData() instanceof Brick) {
                            Brick brick = (Brick) b.getBody().getUserData();
                            brick.collide();
                            if(brick instanceof BlueBrick || (brick instanceof GreenBrick && brick.getNumberOfCollisions() == 2))
                                listBodytoRemove.add(b.getBody());
                        }
                    } else if(a.getBody().getUserData() instanceof Brick) {
                        if(b.getBody().getUserData() instanceof Ball) {
                            Brick brick = (Brick) a.getBody().getUserData();
                            brick.collide();
                            if(brick instanceof BlueBrick || (brick instanceof GreenBrick && brick.getNumberOfCollisions() == 2))
                                listBodytoRemove.add(a.getBody());
                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }


    private void clearBodies() {
        for(Body body: listBodytoRemove) {
            world.destroyBody(body);
        }
        listBodytoRemove.clear();
    }


    public void draw(SpriteBatch spriteBatch) {
        //spriteBatch.setProjectionMatrix(camera.combined);
        background.draw(spriteBatch);
        wall.draw(spriteBatch);
        racket.draw(spriteBatch);
        for(Ball ball : ballList)
            ball.draw(spriteBatch);
        //System.out.println("ballx = " + ballList.get(0).getPosition().x + " bally = " + ballList.get(0).getPosition().y);
        //renderer.render(world, camera.combined);
    }

    public void update() {
        clearBodies();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        //racket.update();
    }

    public Racket getRacket() {
        return racket;
    }

    public World getWorld() {
        return world;
    }

    public static float getMetersToPixels() {
        return METERS_TO_PIXELS;
    }


    public static float getPixelsToMeters() {
        return PIXELS_TO_METERS;
    }

    public void dispose() {
        world.dispose();
        ballList.clear();
        //renderer.dispose();
    }
}
