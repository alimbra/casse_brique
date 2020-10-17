package controls;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import model.GameState;
import views.GameScreen;

/**
 * Created by AS on 19/03/2018.
 */

public class Listener implements InputProcessor {
    GameScreen gameScreen;
    GameState gameState=new GameState();

    public Listener(GameScreen gm){
        gameScreen=gm;
    }
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE){
            gameState.setState(GameState.State.Quit);
            gameScreen.setState(gameState);

            return true;
        }
        if (keycode == Input.Keys.SPACE){
            if (gameScreen.getState().getState()==GameState.State.Pause){
                gameState.setState(GameState.State.Running);
                gameScreen.setState(gameState);
                gameScreen.remettreGame();
            }
            else {
                gameState.setState(GameState.State.Pause);
                gameScreen.setState(gameState);
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
