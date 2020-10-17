package model;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by AS on 20/03/2018.
 */

public class BallStatique2D extends Ball {

    public BallStatique2D(Vector2 position, GameWorld gameWorld) {
        super(position, gameWorld);
        rayon=12;
    }
}
