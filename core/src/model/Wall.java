package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;

import dataFactories.TextureFactory;

/**
 * Created by AS on 06/02/2018.
 */

public class Wall {
    private int nbL;
    private int nbC;
    private int nombre;
    private Brick[][] wall;
    static int largeur = TextureFactory.getTexBlueBrick().getWidth();
    static int hauteur = TextureFactory.getTexBlueBrick().getHeight();
    static int largeurBordure = TextureFactory.getTexBorder().getWidth();
    public GameWorld gameWorld;
    static int hauteurBack = TextureFactory.getTextBack().getHeight();
    public static Brick[][] wallInit;
    public ArrayList<Body> bricksDetruitsMEM;

    public Wall(GameWorld gw) {
        nbL = 5;
        nbC = 10;
        this.gameWorld = gw;
        wall = new Brick[nbL][nbC];
        bricksDetruitsMEM = new ArrayList<Body>();

        /*wallInit=new Brick[][]{
                {
                        new BlueBrick(new Vector2(largeurBordure, hauteurBack - 200), gameWorld),
                        new GreenBrick(new Vector2(largeur + largeurBordure, hauteurBack - 200), 2, gameWorld),
                        new GreenBrick(new Vector2(largeur * 2 + largeurBordure, hauteurBack - 200), 2, gameWorld),
                        new GreenBrick(new Vector2(3 * largeur + largeurBordure, hauteurBack - 200), 1, gameWorld),
                        new GreenBrick(new Vector2(4 * largeur + largeurBordure, hauteurBack - 200), 2, gameWorld),
                        new BlueBrick(new Vector2(5 * largeur + largeurBordure, hauteurBack - 200), gameWorld),
                        new BlueBrick(new Vector2(6 * largeur + largeurBordure, hauteurBack - 200), gameWorld),
                        new GreenBrick(new Vector2(7 * largeur + largeurBordure, hauteurBack - 200), 1, gameWorld),
                        new BlueBrick(new Vector2(8 * largeur + largeurBordure, hauteurBack - 200), gameWorld),
                        new GreenBrick(new Vector2(9 * largeur + largeurBordure, hauteurBack - 200), 3, gameWorld)
                }

        };*/

        setBricks(true);

    }

    public void setBricks(boolean remplissageAleatoire) {
        if (!remplissageAleatoire) {
            wall = new Brick[wallInit.length][wallInit[0].length];

            for (int j = 0; j < wallInit.length; j++) {
                for (int k = 0; k < wallInit[0].length; k++) {
                    wall[j][k] = wallInit[j][k];
                    //   System.out.print("ca passe par ici"+wall[j][k]);
                }
            }
        } else {
            for (int i = 0; i < nbL; i++) {
                for (int j = 0; j < nbC; j++) {
                    int rand = (int) (Math.random() * (12 - 0));
                    switch (rand) {
                        case 0:
                            nombre++;
                            wall[i][j] = new BlueBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), gameWorld);
                            break;
                        case 1:
                            nombre++;
                            wall[i][j] = new GreenBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), 2, gameWorld);

                            break;
                        case 2:
                            wall[i][j] = new GreenBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), 3, gameWorld);

                            break;
                        case 3:
                            wall[i][j] = new GreenBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), 3, gameWorld);
                        case 4:
                            wall[i][j] = new GreenBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), 3, gameWorld);
                        case 5:
                            wall[i][j] = new GreenBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), 3, gameWorld);
                        case 6:
                            wall[i][j] = new GreenBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), 3, gameWorld);
                        case 7:
                            wall[i][j] = new GreenBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), 3, gameWorld);
                        case 8:
                            wall[i][j] = new GreenBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), 3, gameWorld);
                        case 9:
                            wall[i][j] = new GreenBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), 3, gameWorld);
                        case 10:
                            wall[i][j] = new GreenBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), 3, gameWorld);
                        case 11:
                            wall[i][j] = new GreenBrick(new Vector2(largeurBordure +
                                    j * largeur, hauteurBack - 200 - hauteur * i), 3, gameWorld);

                    }
                }
            }
        }
    }

    public void draw(SpriteBatch sb) {
        for (int i = 0; i < wall.length; i++) {
            for (int j = 0; j < wall[0].length; j++) {
                wall[i][j].draw(sb);

            }
        }

    }

    public void Bricktouche(Body bodyAdetruire) {
        bricksDetruitsMEM.add(bodyAdetruire);
    }
    public void mettreAjour(){
        for (int i = 0; i < nbL; i++) {
            for (int j = 0; j < nbC; j++) {
                if (wall[i][j].nbCoup==1)
                {
                    if (!wall[i][j].body.isActive()) {
                        wall[i][j].nbCoup = 3;
                        this.nombre =this.nombre -1;

                    }
                }
                else if (wall[i][j].nbCoup==2){
                    if (!wall[i][j].body.isActive()) {
                        wall[i][j].body.setActive(true);
                       // System.out.print(wall[i][j].body.isActive());
                        wall[i][j].nbCoup = 1;
                    }
                }
            }
        }

    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
    public void setNombreRestant(int nombre) {
        this.nombre =this.nombre -nombre;
    }
    public boolean murVide(){
        //System.out.println(nombre);
        if (nombre==0){
            return true;
        }
        return false;
    }

    public void detruire(){
        for (int i = 0; i < nbL; i++) {
            for (int j = 0; j < nbC; j++) {
                if (wall[i][j].nbCoup==1 || wall[i][j].nbCoup==2) {
                    wall[i][j].dispose();
                }
            }
        }
        //System.out.print(nombre);
        nombre=0;
    }

}
