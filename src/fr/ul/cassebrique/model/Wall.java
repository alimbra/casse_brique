package fr.ul.cassebrique.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by blaaziz1u on 26/01/18.
 */

public class Wall {

    private GameWorld gameWorld;

    private int nbLignes;
    private int nbColonnes;
    private Brick[][] wall;
    private boolean aleatoire;

    private static final Brick[][] wallinit = {
            {new BlueBrick(), new GreenBrick(), new BlueBrick(), new GreenBrick(), new BlueBrick(), new BlueBrick(), new GreenBrick(), new BlueBrick(), new GreenBrick(), new BlueBrick()},
            {new BlueBrick(), new BlueBrick(), new GreenBrick(), new BlueBrick(), new GreenBrick(), new GreenBrick(), new BlueBrick(), new GreenBrick(), new BlueBrick(), new BlueBrick()},
            {new BlueBrick(), new BlueBrick(), new BlueBrick(), new GreenBrick(), new BlueBrick(), new BlueBrick(), new GreenBrick(), new BlueBrick(), new BlueBrick(), new BlueBrick()},
            {new BlueBrick(), new BlueBrick(), new BlueBrick(), new BlueBrick(), new GreenBrick(), new GreenBrick(), new BlueBrick(), new BlueBrick(), new BlueBrick(), new BlueBrick()},
            {null,  new BlueBrick(), null, null, new BlueBrick(), new BlueBrick(), null, null, new BlueBrick(), null}
    };



    public Wall() {
        nbLignes = wallinit.length;
        nbColonnes = wallinit[0].length;
        setBricks(false);
        setWallBody();
    }


    public Wall(GameWorld gameWorld, boolean aleatoire) {
        this.gameWorld = gameWorld;
        nbLignes = wallinit.length;
        nbColonnes = wallinit[0].length;
        setBricks(aleatoire);
        setWallBody();
    }



    public void setBricks(boolean aleatoire) {
        this.aleatoire = aleatoire;
        if(!this.aleatoire) {
            wall = wallinit;
            wall[0][3].setNumberOfCollisions(1);
            wall[0][6].setNumberOfCollisions(1);
            wall[1][4].setNumberOfCollisions(1);
            wall[1][5].setNumberOfCollisions(1);
        }
        else {
            wall = new Brick[nbLignes][nbColonnes];
            Random random = new Random();
            for (int i = 0; i < nbLignes; i++) {
                for (int j = 0; j < nbColonnes; j++) {
                    float valeur = random.nextFloat();
                    if(valeur < 0.1) {
                        wall[i][j] = null;
                    } else if(valeur < 0.5) {
                        wall[i][j] = new BlueBrick();
                    } else if(valeur < 0.9) {
                        wall[i][j] = new GreenBrick();
                    } else {
                        wall[i][j] = new GreenBrick();
                        wall[i][j].setNumberOfCollisions(1);
                    }
                }
            }
        }
    }


    private void setWallBody() {
        float absOrigin = Constantes.BRICK_RED_WIDTH;
        float ordOrigin = Gdx.graphics.getHeight() - (3 * Constantes.BRICK_RED_HEIGHT) - (nbLignes * Constantes.BRICK_HEIGHT);

        for (int i = nbLignes - 1; i >= 0; i--) {
            for (int j = 0; j < nbColonnes; j++) {
                if(wall[i][j] != null) {
                    wall[i][j].getPosition().add(absOrigin, ordOrigin);
                    wall[i][j].createBody(gameWorld.getWorld());
                }
                absOrigin += Constantes.BRICK_WIDTH;
            }
            absOrigin -= nbColonnes * Constantes.BRICK_WIDTH;
            ordOrigin += Constantes.BRICK_HEIGHT;
        }
    }


    public void draw(SpriteBatch spriteBatch) {
        for (int i = nbLignes - 1; i >= 0; i--) {
            for (int j = 0; j < nbColonnes; j++) {
                if(wall[i][j] != null) {
                    wall[i][j].draw(spriteBatch);
                }
            }

        }
    }

}
