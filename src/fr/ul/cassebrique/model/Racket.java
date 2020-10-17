package fr.ul.cassebrique.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.cassebrique.dataFactories.TextureFactory;

import static fr.ul.cassebrique.model.Constantes.*;

public class Racket {

    public static final float WIDTH = TextureFactory.getTexRacket().getWidth();
    public static final float HEIGHT = TextureFactory.getTexRacket().getHeight();
    public static final float RADIUS = HEIGHT / 2f;
    public static final float MOVE = 10f;

    private GameWorld gameWorld;
    private Vector2 position;

    private static BodyDef bodyDef;
    private Body bodyGauche;
    private Body bodyDroite;
    private Body bodyMilieu;

    Vector2 positionGauche;
    Vector2 positionDroite;
    Vector2 positionMilieu;


    public Racket(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        bodyDef.bullet=true;
       // bodyDef.fixedRotation=false;

        this.position = new Vector2((BRICK_RED_WIDTH + (5 * BRICK_WIDTH)) - (WIDTH / 2), 50);

        this.positionGauche = new Vector2(position);
        this.positionMilieu = new Vector2(position);
        this.positionDroite = new Vector2(position);

        positionGauche.add(RADIUS, RADIUS);
        positionMilieu.add(Racket.HEIGHT, 0);
        positionDroite.add(Racket.WIDTH - RADIUS,RADIUS);

        positionGauche.x = positionGauche.x * GameWorld.getPixelsToMeters();
        positionGauche.y = positionGauche.y * GameWorld.getPixelsToMeters();
        positionDroite.x = positionDroite.x * GameWorld.getPixelsToMeters();
        positionDroite.y = positionDroite.y * GameWorld.getPixelsToMeters();

        World world = gameWorld.getWorld();
        this.bodyGauche = createBody(world, positionGauche);
        this.bodyMilieu = createBody(world, positionDroite);
        this.bodyDroite = createBody(world, positionMilieu);


        createFixtureCircle(this.bodyGauche, positionGauche);
        createFixtureCircle(this.bodyDroite, positionDroite);
        createFixtureMilieu(this.bodyMilieu, positionMilieu);

        /*positionGauche.add(-RADIUS, -RADIUS);
        positionGauche.add(- position.x, - position.y);

        positionMilieu.add(- Racket.HEIGHT, 0);
        positionMilieu.add(- position.x, - position.y);

        positionDroite.add(RADIUS - Racket.WIDTH, - RADIUS);
        positionDroite.add(- position.x, - position.y);*/

        bodyGauche.setTransform(positionGauche, 0);
        bodyMilieu.setTransform(positionMilieu, 0);
        bodyDroite.setTransform(positionDroite, 0);
    }


    private Body createBody(World world, Vector2 position) {
        Body body = world.createBody(bodyDef);
        body.setTransform(position, 0);
        body.setUserData("Racket");
        return body;
    }



    private void createFixtureCircle(Body body, Vector2 position) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;

        //position.x = position.x * GameWorld.getPixelsToMeters();
        //position.y = position.y * GameWorld.getPixelsToMeters();

        CircleShape shape = new CircleShape();
        shape.setPosition(position);
        shape.setRadius(RADIUS * GameWorld.getPixelsToMeters());

        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        shape.dispose();
    }


    private void createFixtureMilieu(Body body, Vector2 positionMilieu) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;

        PolygonShape shape = new PolygonShape();
        /*shape.set(new float[] {
                positionMilieu.x, positionMilieu.y,
                positionMilieu.x, positionMilieu.y + (Racket.HEIGHT * GameWorld.getPixelsToMeters()),
                positionMilieu.x + (Racket.WIDTH - (4 * RADIUS)) * GameWorld.getPixelsToMeters(), positionMilieu.y + (Racket.HEIGHT * GameWorld.getPixelsToMeters()),
                positionMilieu.x + (Racket.WIDTH - (4 * RADIUS)) * GameWorld.getPixelsToMeters(), positionMilieu.y
        });*/
        Vector2[] vector2s = new Vector2[]{new Vector2(positionMilieu.x * GameWorld.getPixelsToMeters(), positionMilieu.y * GameWorld.getPixelsToMeters()),
                              new Vector2(positionMilieu.x * GameWorld.getPixelsToMeters(), (positionMilieu.y + HEIGHT) * GameWorld.getPixelsToMeters()),
                              new Vector2((positionMilieu.x + Racket.WIDTH - HEIGHT) * GameWorld.getPixelsToMeters(), (positionMilieu.y + HEIGHT) * GameWorld.getPixelsToMeters()),
                new Vector2((positionMilieu.x + Racket.WIDTH - HEIGHT) * GameWorld.getPixelsToMeters(), positionMilieu.y  * GameWorld.getPixelsToMeters())
        };
        shape.set(vector2s);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
    }


    private boolean canMoveLeft() {
        return position.x > BORDER_LEFT;
    }

    private boolean canMoveRight() {
        return position.x + WIDTH < BORDER_RIGHT;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }


    public void update() {

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if(canMoveLeft())
                moveLeft();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(canMoveRight())
                moveRight();
        }

    }


    private void moveLeft() {
        position.x -= MOVE;

        //positionGauche.sub(MOVE * GameWorld.getPixelsToMeters(), 0);
        bodyGauche.setTransform(positionGauche.sub(MOVE * GameWorld.getPixelsToMeters(), 0), 0);

        //positionMilieu.sub(MOVE * GameWorld.getPixelsToMeters(), 0);
        bodyMilieu.setTransform(positionMilieu.sub(MOVE * GameWorld.getPixelsToMeters(), 0), 0);

        //positionDroite.sub(MOVE * GameWorld.getPixelsToMeters(), 0);
        bodyDroite.setTransform(positionDroite.sub(MOVE * GameWorld.getPixelsToMeters(), 0), 0);
    }


    private void moveRight() {
        position.x += MOVE;

        //positionGauche.add(MOVE * GameWorld.getPixelsToMeters(), 0);
        bodyGauche.setTransform(positionGauche.add(MOVE * GameWorld.getPixelsToMeters(), 0), 0);

        //positionMilieu.add(MOVE * GameWorld.getPixelsToMeters(), 0);
        bodyMilieu.setTransform(positionMilieu.add(MOVE * GameWorld.getPixelsToMeters(), 0), 0);

        //positionDroite.add(MOVE * GameWorld.getPixelsToMeters(), 0);
        bodyDroite.setTransform(positionDroite.add(MOVE * GameWorld.getPixelsToMeters(), 0), 0);
    }


    public void draw(SpriteBatch spriteBatch) {
        //System.out.println("bodyGauche.x = " + bodyGauche.getPosition().x);
        //System.out.println("position x = " + position.x);
        //System.out.println("bodyGauche x = " + bodyGauche.getPosition().x + " et y = " + bodyGauche.getPosition().y);
        //System.out.println("bodyMilieu x = " + bodyMilieu.getPosition().x + " et y = " + bodyMilieu.getPosition().y);
        //System.out.println("bodyDroite x = " + bodyDroite.getPosition().x + " et y = " + bodyDroite.getPosition().y);
        spriteBatch.draw(TextureFactory.getTexRacket(), position.x, position.y);

    }
}
