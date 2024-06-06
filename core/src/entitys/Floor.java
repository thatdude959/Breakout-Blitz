package entitys;

import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.utils.Constants.PPM;

public class Floor {
    public Body body;
    public String id;
    private World world;

    public Floor(World world, String id, float x, float y, float width, float height) {
        this.id = id;
        this.world = world;
        createPaddleBody(x, y, width, height, world);
    }

    private void createPaddleBody(float x, float y, float width, float height, World world) {
        BodyDef bDef = new BodyDef();

        bDef.fixedRotation = true;
        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set(x / PPM, y / PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((float) width / 2 / PPM, (float) height / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 1;

        this.body = world.createBody(bDef);
        this.body.createFixture(fixtureDef).setUserData(this);
    }
}