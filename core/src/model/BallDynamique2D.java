package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by AS on 20/03/2018.
 */

public class BallDynamique2D extends Ball {
    private TextureAtlas ballAtlas;
    private Array<Sprite> imagesBall;
    private int numimage;
    private float tempsPasse;
    float angle;

    public BallDynamique2D(Vector2 position, GameWorld gameWorld) {

        super(position, gameWorld);
        ballAtlas=new TextureAtlas(Gdx.files.internal("images/Boule.pack"));
        imagesBall=ballAtlas.createSprites("boule");

    }

    public void calcul(){
        tempsPasse=tempsPasse+Gdx.graphics.getDeltaTime();
        float dist=getBody().getLinearVelocity().len();

        angle=(float) dist/(float)Ball.rayon;
        float image= (float) ((angle*imagesBall.size)/2*Math.PI);
        numimage= (int) ((numimage+image)%imagesBall.size);
    }

    @Override
    public void draw(SpriteBatch sb) {
        this.calcul();
        Sprite s=imagesBall.get(numimage);
        s.setOriginCenter();
        s.setRotation(angle);
        s.setBounds(getBody().getPosition().x*GameWorld.getMetersToPixels()-rayon,
                getBody().getPosition().y*GameWorld.getMetersToPixels()-rayon,2*rayon,2*rayon);
        /*s.setBounds(getBody().getPosition().x*GameWorld.getMetersToPixels()-rayon,
                getBody().getPosition().y*GameWorld.getMetersToPixels()-rayon
                ,2*rayon,2*rayon
        );*/
        sb.draw(s,
                getBody().getPosition().x*GameWorld.getMetersToPixels()-rayon,
                getBody().getPosition().y*GameWorld.getMetersToPixels()-rayon,
                2*rayon,2*rayon
        );
    }
}
