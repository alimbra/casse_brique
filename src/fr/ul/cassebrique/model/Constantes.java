package fr.ul.cassebrique.model;

import fr.ul.cassebrique.dataFactories.TextureFactory;

public class Constantes {

    // dimensions du GameWorld
    public static final float WORLD_WIDTH = TextureFactory.getTexBackground().getWidth();
    public static final float WORLD_HEIGHT = TextureFactory.getTexBackground().getHeight();

    // dimensions d'une brique du mur
    public static final float BRICK_WIDTH = TextureFactory.getTexBlueBrick().getWidth();
    public static final float BRICK_HEIGHT = TextureFactory.getTexBlueBrick().getHeight();

    // dimensions d'une bille
    public static final float BALL_WIDTH = TextureFactory.getTexBall().getWidth();
    public static final float BALL_HEIGHT = TextureFactory.getTexBall().getHeight();

    // dimensions d'une brique rouge du mur d'enceinte
    public static final float BRICK_RED_WIDTH = TextureFactory.getTexContour().getWidth();
    public static final float BRICK_RED_HEIGHT = TextureFactory.getTexContour().getHeight();

    // largeur de la zone des billes
    public static final float BALLS_AREA_WIDTH = BRICK_RED_WIDTH;

    // frontières zone de jeu
    public static final float BORDER_LEFT = BRICK_RED_WIDTH;
    public static final float BORDER_RIGHT = BRICK_RED_WIDTH + (10 * BRICK_WIDTH);
    public static final float BORDER_TOP = BRICK_RED_HEIGHT * 13;
    public static final float BORDER_BOTTOM = 0;

}
