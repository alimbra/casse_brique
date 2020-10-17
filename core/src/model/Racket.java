package model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import dataFactories.TextureFactory;

/**
 * Created by AS on 06/02/2018.
 */

public class Racket {
    private GameWorld gw;
    private int racketHauteur;
    private int racketLargeur;
    private Body bodyBG;
    private Body bodyBD;
    private Body bodyMileu;
    PolygonShape polygonShape;
    FixtureDef fixtureDef=new FixtureDef();
    FixtureDef fixtureDefBG=new FixtureDef();
    FixtureDef fixtureDefBD=new FixtureDef();
    public CircleShape circleShape1=new CircleShape();
    public CircleShape circleShape=new CircleShape();
    public float[] posx;



    private float bDiametre;
    private float brayon;

    Vector2 position;//=new Vector2((TextureFactory.getTextBack().getWidth()- racketLargeur)/2, 50);


    public Racket(GameWorld gameWorld){
        gw=gameWorld;

        racketHauteur =TextureFactory.getTexRacket().getHeight();
        racketLargeur =TextureFactory.getTexRacket().getWidth();
        position=new Vector2((TextureFactory.getTextBack().getWidth()- racketLargeur)/2, 50);

        bDiametre =racketHauteur;
        brayon=bDiametre/2;

        float xM = (position.x + bDiametre)*GameWorld.getPixelsToMeters();
        float yM = position.y*GameWorld.getPixelsToMeters();

        float x1M= (position.x + racketLargeur - bDiametre)*GameWorld.getPixelsToMeters();
        float y1M= (position.y + bDiametre)*GameWorld.getPixelsToMeters();

        float xBG= (position.x+brayon)*GameWorld.getPixelsToMeters();
        float xBD= (position.x + racketLargeur - bDiametre+brayon)*GameWorld.getPixelsToMeters();

        BodyDef bodyDef=new BodyDef();
        bodyDef.bullet=true;
        bodyDef.fixedRotation=false;
        bodyDef.type= BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        bodyMileu=gw.getMonde().createBody(bodyDef);
        bodyBD=gw.getMonde().createBody(bodyDef);
        bodyBG=gw.getMonde().createBody(bodyDef);

        //milieu
        polygonShape=new PolygonShape();
        posx=new float[]{xM,yM,xM,y1M,x1M,y1M,x1M,yM};
        polygonShape.set(new Vector2[]{new Vector2(xM,yM),new Vector2(xM,y1M),new Vector2(x1M,y1M),new Vector2(x1M,yM)});
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;
        bodyMileu.createFixture(fixtureDef);

        circleShape.setRadius(brayon*GameWorld.getPixelsToMeters());
        circleShape.setPosition(new Vector2(xBG,yM+brayon*GameWorld.getPixelsToMeters()));
        fixtureDefBG.density=1;
        fixtureDefBG.friction=0;
        fixtureDefBG.restitution=1;
        fixtureDefBG.shape=circleShape;

        bodyBG.createFixture(fixtureDefBG);

        circleShape1.setRadius(brayon*GameWorld.getPixelsToMeters());
        circleShape1.setPosition(new Vector2(xBD,yM+brayon*GameWorld.getPixelsToMeters()));
        fixtureDefBD.density=1;
        fixtureDefBD.friction=0;
        fixtureDefBD.restitution=1;
        fixtureDefBD.shape=circleShape1;

        bodyBD.createFixture(fixtureDefBD);
        bodyBG.setUserData("BGRacket");
        bodyBD.setUserData("BDRacket");
        bodyMileu.setUserData("BodyMilieu");
        //bodyBD.setTransform(xBD,yM+brayon*GameWorld.getPixelsToMeters(),0);
        //bodyBG.setTransform(xBG,yM+brayon*GameWorld.getPixelsToMeters(),0);
        //bodyMileu.setTransform(xM,yM+brayon*GameWorld.getPixelsToMeters(),0);


    }
    public int getRacketHauteur() {
        return racketHauteur;
    }

    public int getRacketLargeur() {
        return racketLargeur;
    }

    public void setPosition(Vector2 pos){
        position=pos;
        //bodyMileu.getPosition().set(position.x,position.y);

        //System.out.print(bodyMileu.getPosition()+"\n");

    }

    public Body getBodyBD() {
        return bodyBD;
    }

    public Body getBodyBG() {
        return bodyBG;
    }

    public Body getBodyMileu() {
        return bodyMileu;
    }

    public int getX(){
        return (int)position.x;
    }
    public int getY(){
        return (int)position.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void left(){
        position.sub(10,0);
    }
    public void right(){
        position.add(10,0);
    }

    public void draw(SpriteBatch sb){
        sb.draw(TextureFactory.getTexRacket(),position.x,position.y);
        ShapeRenderer shapeRenderer=new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        //shapeRenderer.polygon(posx);

    }


}
