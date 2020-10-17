package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ModelInfluencer;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;

import sun.security.provider.certpath.Vertex;

/**
 * Created by AS on 22/03/2018.
 */

public class Ball3D extends Ball {
    private ModelInstance boule3D;
    private ModelBatch m;
    private Environment environment;
    private Quaternion q;

    public Ball3D(Vector2 position, GameWorld gameWorld) {
        super(position, gameWorld);
        ModelBuilder mb=new ModelBuilder();
        Model model=mb.createSphere( 2, 2, 2, 40,
                40, new Material(TextureAttribute.createDiffuse(new Texture(Gdx.files.internal("images/Badlogic.jpg")))),
                VertexAttributes.Usage.Position|
                VertexAttributes.Usage.Normal|
                VertexAttributes.Usage.TextureCoordinates

        );
        boule3D=new ModelInstance(model);
        environment=new Environment();
        ColorAttribute colorAttribute=new ColorAttribute(ColorAttribute.Ambient);
        ColorAttribute colorAttribute1=new ColorAttribute(ColorAttribute.createSpecular(Color.GREEN));

        m=new ModelBatch();
    }

    @Override
    public void draw(SpriteBatch sb) {
        //sb.draw(m,0,0);
    }
}
