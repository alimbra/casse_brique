package fr.ul.cassebrique.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.ul.cassebrique.dataFactories.TextureFactory;
import fr.ul.cassebrique.model.Constantes;
import fr.ul.cassebrique.model.GameState;
import fr.ul.cassebrique.model.GameWorld;

/**
 * Created by blaaziz1u on 26/01/18.
 */

public class GameScreen extends ScreenAdapter {

    //private ShapeRenderer renderer;
    //private OrthographicCamera camera;
    //private Viewport viewport;

    private SpriteBatch spriteBatch;
    private GameWorld gameWorld;
    private GameState gameState;


    public GameScreen(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        this.gameWorld = new GameWorld(this);
        this.gameState = new GameState(GameState.State.Running);
        //spriteBatch.setProjectionMatrix(camera.combined);
    }


   /* @Override
    public void show() {
        super.show();
        this.camera = new OrthographicCamera();
        this.camera.position.set(0, 0, 0);
        this.camera.setToOrtho(false);
        this.viewport = new FitViewport(Constantes.WORLD_WIDTH, Constantes.WORLD_HEIGHT, camera);
        this.renderer = new ShapeRenderer();
        this.viewport.apply(true);
    }*/


    @Override
    public void render(float delta) {
        super.render(delta);
        gameWorld.getRacket().update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        gameWorld.draw(spriteBatch);
        spriteBatch.end();
        gameWorld.update();
        //renderer.ren
    }


    @Override
    public void dispose() {
        spriteBatch.dispose();
        gameWorld.dispose();
    }
}
