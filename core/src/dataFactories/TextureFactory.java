package dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by AS on 06/02/2018.
 */

public class TextureFactory {
    public static Texture texBlueBrick=new Texture(Gdx.files.internal("images/Brique1C.png"));
    public static Texture texGreenBrickA=new Texture(Gdx.files.internal("images/Brique2Ca.png"));
    public static Texture texGreenBrickB=new Texture(Gdx.files.internal("images/Brique2Cb.png"));
    public static Texture textBack=new Texture(Gdx.files.internal("images/Fond.png"));
    public static Texture texBall=new Texture(Gdx.files.internal("images/Bille.png"));
    public static Texture texBorder=new Texture(Gdx.files.internal("images/Contour.png"));
    public static Texture texRacket=new Texture(Gdx.files.internal("images/Barre.png"));
    public static Texture texPerteBall =new Texture(Gdx.files.internal("images/PerteBalle.bmp"));
    public static Texture texPerte=new Texture(Gdx.files.internal("images/Perte.bmp"));
    public static Texture texAnime2CA=new Texture(Gdx.files.internal("images/Anim2Ca.png"));
    public static Texture texBravo=new Texture(Gdx.files.internal("images/Bravo.bmp"));


    public static Texture getTexBlueBrick() {
        return texBlueBrick;
    }

    public static Texture getTexGreenBrickA() {
        return texGreenBrickA;
    }

    public static Texture getTexGreenBrickB() {
        return texGreenBrickB;
    }

    public static Texture getTexBall() {
        return texBall;
    }

    public static Texture getTexBorder() {
        return texBorder;
    }

    public static Texture getTexRacket() {
        return texRacket;
    }

    public static Texture getTextBack() {
        return textBack;
    }

    public static Texture getTexBravo() {
        return texBravo;
    }

    public static Texture getTexPerte() {
        return texPerte;
    }

    public static Texture getTexPerteBall() {
        return texPerteBall;
    }

    public static Texture getTexAnime2CA() {
        return texAnime2CA;
    }
}
