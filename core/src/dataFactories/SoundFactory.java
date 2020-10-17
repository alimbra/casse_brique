package dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by AS on 19/03/2018.
 */

public class SoundFactory {
    public static Sound collision=Gdx.audio.newSound(Gdx.files.internal("sounds/collision.wav"));
    public static Sound impact=Gdx.audio.newSound(Gdx.files.internal("sounds/impact.mp3"));
    public static Sound perte=Gdx.audio.newSound(Gdx.files.internal("sounds/perte.mp3"));
    public static Sound perteBalle=Gdx.audio.newSound(Gdx.files.internal("sounds/perteBalle.wav"));
    public static Sound victorire=Gdx.audio.newSound(Gdx.files.internal("sounds/victoire.mp3"));

    public static Sound getCollision() {
        return collision;
    }

    public static Sound getImpact() {
        return impact;
    }

    public static Sound getPerte() {
        return perte;
    }

    public static Sound getPerteBalle() {
        return perteBalle;
    }

    public static Sound getVictorire() {
        return victorire;
    }
}
