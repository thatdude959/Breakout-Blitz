package entitys;

import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.utils.Constants.PPM;

public class Ball {
    public Body body;
    public String id;
    private World world;

    public Ball(World world, String id, int x, int y, int radius) {
        this.id = id;
        this.world = world;
        createBallBody(x, y, radius, world);
    }

    public void createBallBody(int x, int y, int radius, World world) {
        BodyDef bDef = new BodyDef();

        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(x / PPM, y / PPM);
        bDef.fixedRotation = true;
        Body pBody = world.createBody(bDef);

        Shape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 1;

        this.body = world.createBody(bDef);
        this.body.createFixture(fixtureDef).setUserData(this);
    }
}