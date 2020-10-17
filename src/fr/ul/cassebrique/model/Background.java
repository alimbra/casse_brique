package fr.ul.cassebrique.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.cassebrique.dataFactories.TextureFactory;

public class Background {

    private Body bodyBackGround;
//    private Body rectangle;

    public Background(World world) {
        createBody(world);
        createFixture();
    }


    private void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, 0);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        //bodyDef.bullet=true;
        //bodyDef.fixedRotation=false;
        bodyBackGround = world.createBody(bodyDef);
        bodyBackGround.setUserData(this);
    }


    private void createFixture() {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 1;

        ChainShape chainShape = new ChainShape();
        chainShape.createChain(new float[]{
                Constantes.BRICK_RED_WIDTH * GameWorld.getPixelsToMeters(), 0,
                Constantes.BRICK_RED_WIDTH * GameWorld.getPixelsToMeters(), (Constantes.WORLD_HEIGHT - Constantes.BRICK_RED_HEIGHT) * GameWorld.getPixelsToMeters(),
                (Constantes.WORLD_WIDTH - 2 * Constantes.BRICK_RED_WIDTH) * GameWorld.getPixelsToMeters(), (Constantes.WORLD_HEIGHT - Constantes.BRICK_RED_HEIGHT)* GameWorld.getPixelsToMeters(),
                (Constantes.WORLD_WIDTH - 2 * Constantes.BRICK_RED_WIDTH)* GameWorld.getPixelsToMeters(), 0,
                });

        fixtureDef.shape = chainShape;
        bodyBackGround.createFixture(fixtureDef);
        chainShape.dispose();
    }


    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(TextureFactory.getTexBackground(), 0, 0);
//        spriteBatch.draw(TextureFactory.getTexBackground(), rectangle.getPosition().x, rectangle.getPosition().y);
    }
}
