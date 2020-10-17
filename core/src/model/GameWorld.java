package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import dataFactories.SoundFactory;
import dataFactories.TextureFactory;
import views.GameScreen;

/**
 * Created by AS on 06/02/2018.
 */

public class GameWorld {
    private GameScreen gm;
    private Wall wall;
    private Background background;
    private Racket racket;
    public  Array<Ball> balls=new Array<Ball>();
    private World monde;

    private static final float METERS_TO_PIXELS=250;
    private static final float PIXELS_TO_METERS=1/250f;

    public GameWorld(GameScreen gameScreen){
        gm=gameScreen;

        monde=new World(new Vector2(0,0),true);
        wall=new Wall(this);
        background=new Background(this);
        racket=new Racket(this);

        //la balle principale
        Vector2 positionBALL1=new Vector2((racket.getPosition().x+racket.getRacketLargeur()/2),
                (racket.getPosition().y+racket.getRacketHauteur()+Ball.rayon));
        balls.add(new Ball(positionBALL1,this));
        //        balls.add(new BallDynamique2D(positionBALL1,this));

        //la ball reserve 1
        Vector2 positionBALL2=new Vector2(background.getLargeur()-background.getLargeurBordure()/2,
                background.getLargeurBordure()/2);
        balls.add(new Ball(positionBALL2,this));


        //la ball reserve 2
        Vector2 positionBALL3=new Vector2(background.getLargeur()-background.getLargeurBordure()/2,
                3*background.getLargeurBordure()/2);
        balls.add(new Ball(positionBALL3,this));

        balls.get(0).setSpeed();
        monde.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                /**
                 * detection de la BD de la racket
                 */
                if (contact.getFixtureB().getBody().getUserData().equals("Ball")
                        && contact.getFixtureA().getBody().getUserData().equals("BDRacket"))
                {
                    //System.out.println(contact.getFixtureB().getBody().getLinearVelocity());
                    contact.getFixtureB().getBody().setLinearVelocity(
                            contact.getFixtureB().getBody().getLinearVelocity().x+racket.getRacketLargeur()*PIXELS_TO_METERS/2,
                            contact.getFixtureB().getBody().getLinearVelocity().y
                            );
                    //System.out.println(contact.getFixtureB().getBody().getPosition());
                    SoundFactory.getImpact().play(1);
                }
                /**
                 * detection de la BG de la racket
                 */
                if (contact.getFixtureB().getBody().getUserData().equals("Ball")
                        && contact.getFixtureA().getBody().getUserData().equals("BGRacket"))
                {
                    //System.out.println(contact.getFixtureB().getBody().getLinearVelocity());
                    contact.getFixtureB().getBody().setLinearVelocity(
                            contact.getFixtureB().getBody().getLinearVelocity().x-racket.getRacketLargeur()*PIXELS_TO_METERS/2,
                            contact.getFixtureB().getBody().getLinearVelocity().y
                    );
                    //System.out.println(contact.getFixtureB().getBody().getPosition());
                    SoundFactory.getImpact().play(1);

                }
                /**
                 * detection de la Milieu de la racket
                 */
                if (contact.getFixtureB().getBody().getUserData().equals("Ball")
                        && contact.getFixtureA().getBody().getUserData().equals("BodyMilieu"))
                {
                    SoundFactory.getImpact().play(1);
                }
                /**
                 * detection du background
                 *
                 */

                if (contact.getFixtureB().getBody().getUserData().equals("Ball")
                        && contact.getFixtureA().getBody().getUserData().equals("Background"))
                {
                    Vector2 normale=contact.getWorldManifold().getNormal();
                    //System.out.println(contact.getFixtureB().getBody().getLinearVelocity());
                    if (normale.y>0){
                        contact.getFixtureB().getBody().setLinearVelocity(
                                contact.getFixtureB().getBody().getLinearVelocity().x,
                                contact.getFixtureB().getBody().getLinearVelocity().y
                                        -contact.getFixtureB().getBody().getLinearVelocity().y*0.01f
                        );
                    }
                    //System.out.println(contact.getFixtureB().getBody().getLinearVelocity());



                }

                /**
                 * detection de la brick
                 */
                if (contact.getFixtureB().getBody().getUserData().equals("Ball")
                        && contact.getFixtureA().getBody().getUserData().equals("Brick"))
                {
                    wall.Bricktouche(contact.getFixtureA().getBody());
                    Vector2 normale=contact.getWorldManifold().getNormal();
                    /*contact.getFixtureB().getBody().setLinearVelocity(
                            normale.x*contact.getFixtureB().getBody().getLinearVelocity().x,
                            contact.getFixtureB().getBody().getLinearVelocity().y
                    );*/
                    if (normale.y>0){
                        contact.getFixtureB().getBody().setLinearVelocity(
                                contact.getFixtureB().getBody().getLinearVelocity().x,
                                contact.getFixtureB().getBody().getLinearVelocity().y
                                        +contact.getFixtureB().getBody().getLinearVelocity().y*0.01f
                        );
                    }
                   // System.out.println(contact.getFixtureB().getBody().getLinearVelocity());
                    SoundFactory.getCollision().play(1);
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

    public void draw(SpriteBatch sb){
        update();
                background.draw(sb);
        wall.draw(sb);
        racket.draw(sb);
        for (Ball b:balls) {
            b.draw(sb);
        }
      //  System.out.println(" x "+balls.get(1).getBody().getPosition().x*getMetersToPixels());
      //  System.out.println(" y "+balls.get(1).getBody().getPosition().y*getMetersToPixels());

    }

    public void update() {

        monde.step(Gdx.graphics.getDeltaTime(),6,2);

        for (Body b:wall.bricksDetruitsMEM){
            b.setActive(false);
        }
        wall.bricksDetruitsMEM.clear();
        wall.mettreAjour();
    }

    public Racket getRacket() {
        return racket;
    }

    public static float getMetersToPixels() {
        return METERS_TO_PIXELS;
    }

    public static float getPixelsToMeters() {
        return PIXELS_TO_METERS;
    }

    public World getMonde() {
        return monde;
    }
    public void supprimerBall(){
        if (balls.size!=0){
            balls.removeIndex(0);
        }
    }
    public void ajouterBall(){
        if (balls.size==1){
            //la ball reserve
            Vector2 positionBALL2=new Vector2(background.getLargeur()-background.getLargeurBordure()/2,
                    background.getLargeurBordure()/2);
            balls.add(new Ball(positionBALL2,this));
        }
        else {
            //la ball reserve

            Vector2 positionBALL2=new Vector2(background.getLargeur()-background.getLargeurBordure()/2,
                    (balls.size-1)*(background.getLargeurBordure())+(background.getLargeurBordure()/2));
            balls.add(new Ball(positionBALL2,this));

        }
    }

    public Array<Ball> getBalls() {
        return balls;
    }

    public Wall getWall() {
        return wall;
    }
    public void redemarrer(GameState.State state){
        if (state == GameState.State.GameOver){
            this.repositionnerRacket();
            this.remettreBalls();
            wall.detruire();
            wall.setBricks(true);
        }
        if (state==GameState.State.BallLoss){
            if (balls.size!=0) {
                this.repositionnerRacket();
                this.placerBilleReserve();
                this.setSpeedNVBall();
                //System.out.println(balls.size);
            }
        }
        else if (state==GameState.State.Won){
            ajouterBall();
            wall.detruire();
            wall.setBricks(true);
            repositionnerRacket();
            this.placerBilleReserve();
            this.setSpeedNVBall();
        }
    }

    public void repositionnerRacket() {
        racket.setPosition(new Vector2((TextureFactory.getTextBack().getWidth()- racket.getRacketLargeur())/2, 50));
        racket.getBodyBD().setTransform(0,0,0);
        racket.getBodyMileu().setTransform(0,0,0);
        racket.getBodyBG().setTransform(0,0,0);
    }

    public void placerBilleReserve(){
        Vector2 position=new Vector2(racket.getPosition().x+racket.getRacketLargeur()/2,
                (racket.getPosition().y+racket.getRacketHauteur()+Ball.rayon));
        balls.get(0).getBody().setTransform(new Vector2(
                position.x*GameWorld.getPixelsToMeters(),position.y*GameWorld.getPixelsToMeters()),0);
    }

    public void setSpeedNVBall(){
        balls.get(0).setSpeed();
    }

    public void mettreAjourPositions() {
        for (int i = 1; i <balls.size; i++) {
            Vector2 positionBALL=new Vector2(background.getLargeur()-background.getLargeurBordure()/2,
                    (i-1)*(background.getLargeurBordure())+(background.getLargeurBordure()/2));
            balls.get(i).getBody().setTransform(positionBALL.x*GameWorld.getPixelsToMeters(),
                    positionBALL.y*GameWorld.getPixelsToMeters(),0);
        }
    }
    public void remettreBalls(){
        balls.clear();
        //la balle principale
        Vector2 positionBALL1=new Vector2((racket.getPosition().x+racket.getRacketLargeur()/2),
                (racket.getPosition().y+racket.getRacketHauteur()+Ball.rayon));
        balls.add(new Ball(positionBALL1,this));

        //la ball reserve 1
        Vector2 positionBALL2=new Vector2(background.getLargeur()-background.getLargeurBordure()/2,
                background.getLargeurBordure()/2);
        balls.add(new Ball(positionBALL2,this));


        //la ball reserve 2
        Vector2 positionBALL3=new Vector2(background.getLargeur()-background.getLargeurBordure()/2,
                3*background.getLargeurBordure()/2);
        balls.add(new Ball(positionBALL3,this));

        balls.get(0).setSpeed();
    }


}
