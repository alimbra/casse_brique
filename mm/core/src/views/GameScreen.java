package views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.TimerTask;

import dataFactories.TextureFactory;
import model.Ball;
import model.GameState;
import model.GameWorld;

/**
 * Created by AS on 06/02/2018.
 */

public class GameScreen extends ScreenAdapter {
    private SpriteBatch sb;
    private GameWorld gw;
    private GameState state;
    private Timer.Task temps;
    private Timer timer=new Timer();
    private int i=0;
    private OrthographicCamera camera;
    private float ratio;
    public GameScreen(){
        sb=new SpriteBatch();
        gw=new GameWorld(this);
        state=new GameState();
        camera=new OrthographicCamera(TextureFactory.getTextBack().getWidth(),TextureFactory.getTextBack().getHeight());
        camera.position.set(TextureFactory.getTextBack().getWidth()/2,TextureFactory.getTextBack().getHeight()/2,0);
        ratio=TextureFactory.getTextBack().getWidth()/Gdx.graphics.getWidth();

        temps=new Timer.Task() {
            @Override
            public void run() {
                /*System.out.println("ca passe"+i);
                i++;*/
                redemarrerJeu();
            }
        };
    }

    public void  render(float delta){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_TEXTURE_2D);
        camera.update();
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        gw.draw(sb);

        if (state.getState()==GameState.State.Won) {
            sb.draw(TextureFactory.getTexBravo(), TextureFactory.getTextBack().getWidth()/4,
                    TextureFactory.getTextBack().getHeight()/3);
            if (!temps.isScheduled()){
                Timer.instance().scheduleTask(temps,3);
            }
        }
        else if (state.getState()==GameState.State.BallLoss) {
            //System.out.println("ball loss");
            sb.draw(TextureFactory.getTexPerteBall(),TextureFactory.getTextBack().getWidth()/4,
                    TextureFactory.getTextBack().getHeight()/3);
            //redemarrerJeu();
            if (!temps.isScheduled()){
                Timer.instance().scheduleTask(temps,3);
            }
        }
        else if (state.getState()==GameState.State.GameOver){
            sb.draw(TextureFactory.getTexPerte(), TextureFactory.getTextBack().getWidth() / 4,
                    TextureFactory.getTextBack().getHeight() / 3);
            if (!temps.isScheduled()) {
                Timer.instance().scheduleTask(temps, 3);
            }
        }
        /**
         * verifier si le jeu est encore en cours pour
         * mettre a jour le jeu
         */
        if (state.getState()== GameState.State.Running) {
            update();
        }
        sb.end();
    }
    public void dispose(){
        sb.dispose();
    }

    /**
     * je dois absolument commenter
     *
     */
    public void update(){
        int largeurBordure=TextureFactory.getTexBorder().getWidth();
        int largeur=TextureFactory.getTextBack().getWidth();
        int largeurRacket=gw.getRacket().getRacketLargeur();
        Vector2 positionRacketBody=new Vector2(gw.getRacket().getBodyMileu().getPosition());
        Vector2 positionBGBody=new Vector2(gw.getRacket().getBodyBG().getPosition());
        Vector2 positionBDBody=new Vector2(gw.getRacket().getBodyBD().getPosition());

        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() * ratio > gw.getRacket().getX()+10+largeurRacket/2) {
                System.out.println(ratio);
                if (gw.getRacket().getX() + largeurRacket < largeur - largeurBordure * 2) {

                    if(gw.getRacket().getX()+largeurRacket + 10>=largeur - largeurBordure * 2){
                        gw.getRacket().setPosition(new Vector2(largeur - largeurBordure * 2-largeurRacket, gw.getRacket().getY()));

                    }
                    else {

                        gw.getRacket().getPosition().add(10,0);
                        gw.getRacket().getBodyMileu().setTransform(gw.getRacket().getBodyMileu().getPosition()
                                .add(10*GameWorld.getPixelsToMeters(),0),0);
                        gw.getRacket().getBodyBG().setTransform(gw.getRacket().getBodyBG().getPosition()
                                .add(10*GameWorld.getPixelsToMeters(),0),0);
                        gw.getRacket().getBodyBD().setTransform(gw.getRacket().getBodyBD().getPosition()
                                .add(10*GameWorld.getPixelsToMeters(),0),0);


                        //  System.out.println("ici1  "+Gdx.input.getX()+" " +gw.getRacket().getX());
                         // System.out.println(gw.getRacket().getPosition());
                          //System.out.println(gw.getRacket().getBodyMileu().getPosition());
                    }
                }
            } else if (Gdx.input.getX()* ratio< gw.getRacket().getX()-10 +largeurRacket/2 ) {
                if (gw.getRacket().getX() > largeurBordure) {

                    if (gw.getRacket().getX()-10<largeurBordure){

                        gw.getRacket().setPosition(new Vector2(largeurBordure, gw.getRacket().getY()));

                    }
                    else {
                        gw.getRacket().getPosition().sub(10,0);
                        gw.getRacket().getBodyMileu().setTransform(gw.getRacket().getBodyMileu().getPosition()
                                .sub(10*GameWorld.getPixelsToMeters(),0),0);
                        gw.getRacket().getBodyBG().setTransform(gw.getRacket().getBodyBG().getPosition()
                                .sub(10*GameWorld.getPixelsToMeters(),0),0);
                        gw.getRacket().getBodyBD().setTransform(gw.getRacket().getBodyBD().getPosition()
                                .sub(10*GameWorld.getPixelsToMeters(),0),0);


                        //   System.out.println(gw.getRacket().getPosition());
                        //    System.out.println(gw.getRacket().getBodyMileu().getPosition());
                    }
                }
            }
        }

        //si les deplacement par left et right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if (gw.getRacket().getX()+largeurRacket<largeur-largeurBordure*2){

                //pour ne pas depasser de peu les bordures
                if (gw.getRacket().getX()+largeurRacket+10>largeur-largeurBordure*2) {

                    gw.getRacket().setPosition(new Vector2(largeur-largeurBordure*2-largeurRacket, gw.getRacket().getY()));
                }
                else {
                    gw.getRacket().setPosition(new Vector2(gw.getRacket().getX() + 10, gw.getRacket().getY()));
                    gw.getRacket().getBodyMileu().setTransform(gw.getRacket().getBodyMileu().getPosition()
                            .add(10*GameWorld.getPixelsToMeters(),0),0);
                    gw.getRacket().getBodyBG().setTransform(gw.getRacket().getBodyBG().getPosition()
                            .add(10*GameWorld.getPixelsToMeters(),0),0);
                    gw.getRacket().getBodyBD().setTransform(gw.getRacket().getBodyBD().getPosition()
                            .add(10*GameWorld.getPixelsToMeters(),0),0);

                    //System.out.println(gw.getRacket().getPosition());
                    //System.out.println(gw.getRacket().getBodyMileu().getPosition());
                }
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){

            if (gw.getRacket().getX()>largeurBordure) {
                //pour ne pas depasser de peu les bordures
                if (gw.getRacket().getX()-10<largeurBordure){
                    gw.getRacket().setPosition(new Vector2(largeurBordure, gw.getRacket().getY()));
                }
                else {
                    gw.getRacket().setPosition(new Vector2(gw.getRacket().getX() - 10, gw.getRacket().getY()));
                    gw.getRacket().getBodyMileu().setTransform(gw.getRacket().getBodyMileu().getPosition()
                            .sub(10*GameWorld.getPixelsToMeters(),0),0);
                    gw.getRacket().getBodyBG().setTransform(gw.getRacket().getBodyBG().getPosition()
                            .sub(10*GameWorld.getPixelsToMeters(),0),0);
                    gw.getRacket().getBodyBD().setTransform(gw.getRacket().getBodyBD().getPosition()
                            .sub(10*GameWorld.getPixelsToMeters(),0),0);
                }
            }
        }
/*
        System.out.println("milieu est "+gw.getRacket().getBodyMileu().getPosition());
        System.out.println("BD est "+gw.getRacket().getBodyBD().getPosition());
        System.out.println("BG est "+gw.getRacket().getBodyBG().getPosition());
        System.out.println("positioX est "+gw.getRacket().getPosition().x*GameWorld.getPixelsToMeters());

        System.out.println( "ball " +gw.balls.get(0).getBody().getPosition());*/
        if (gw.balls.size==0){
            state.setState(GameState.State.GameOver);
        }
        else if (gw.balls.get(0).estSortieJeu()){
            state.setState(GameState.State.BallLoss);
           //System.out.print(gw.balls.size);
            gw.balls.removeIndex(0);
            gw.mettreAjourPositions();
        }

        if (gw.getWall().murVide()){
            state.setState(GameState.State.Won);
        }
    }

    public void redemarrerJeu(){

        gw.redemarrer(state.getState());
        state.setState(GameState.State.Running);
    }
}
