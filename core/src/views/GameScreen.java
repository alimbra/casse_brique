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

import controls.Listener;
import dataFactories.SoundFactory;
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
    private Listener listener;

    public GameScreen(){
        sb=new SpriteBatch();
        gw=new GameWorld(this);
        listener=new Listener(this);
        state=new GameState();
        camera=new OrthographicCamera(TextureFactory.getTextBack().getWidth(),TextureFactory.getTextBack().getHeight());
        camera.position.set(TextureFactory.getTextBack().getWidth()/2,TextureFactory.getTextBack().getHeight()/2,0);
        float width=Gdx.graphics.getWidth();
        float widthback=TextureFactory.getTextBack().getWidth();
        ratio=widthback/width;
        Gdx.input.setInputProcessor(listener);
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
        else if(state.getState()==GameState.State.Quit){
            Gdx.app.exit();
        }
        else if(state.getState()==GameState.State.Pause){
            this.pauseGame();
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

    public void pauseGame() {
        if (gw.balls.size!=0){
            gw.balls.get(0).stopper();
        }
    }
    public void remettreGame() {
        if (gw.balls.size!=0){
            gw.balls.get(0).remettreVit();
        }
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
        float x=Gdx.input.getAccelerometerY();
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() * ratio > gw.getRacket().getX()+10+largeurRacket/2) {
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
        if (gw.balls.size==0){
            state.setState(GameState.State.GameOver);
            SoundFactory.getPerte().play(1);

        }
        else if (gw.balls.get(0).estSortieJeu()){
            state.setState(GameState.State.BallLoss);
           //System.out.print(gw.balls.size);
            SoundFactory.getPerteBalle().play(1);
            gw.balls.removeIndex(0);
            gw.mettreAjourPositions();
        }

        if (gw.getWall().murVide()){
            SoundFactory.getVictorire().play(1);
            state.setState(GameState.State.Won);
        }

        //l accelerometre

        if (x>1){
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
                }
            }
        }
        else if (x<-1){
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
    }

    public void redemarrerJeu(){
        gw.redemarrer(state.getState());
        state.setState(GameState.State.Running);
    }

    public void setState(GameState state) {
        this.state.setState(state.getState());
    }

    public GameState getState() {
        return state;
    }
}
