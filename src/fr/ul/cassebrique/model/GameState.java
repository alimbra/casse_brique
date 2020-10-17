package fr.ul.cassebrique.model;

public class GameState {

    public enum State {
        Running,
        BallLoss,
        GameOver,
        Won,
        Pause,
        Quit
    }

    private State state;


    public GameState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
