package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.utils.Constants.PPM;

public class Spawner {
    public static Body box(int x, int y, int width, int height, boolean isKinimatic, World world) {
        BodyDef def = new BodyDef();

        if (isKinimatic) {
            def.type = BodyDef.BodyType.KinematicBody;
        } else {
            def.type = BodyDef.BodyType.StaticBody;
        }

        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;
        Body pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        // Box2D takes HALF width and HALF height from center
        shape.setAsBox((float) width / 2 / PPM, (float) height / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 1;

        pBody.createFixture(fixtureDef);
        shape.dispose();

        return pBody;
    }

    public static Body ball(int x, int y, int radius, World world) {
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;
        Body pBody = world.createBody(def);

        Shape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 1;

        pBody.createFixture(fixtureDef);
        shape.dispose();

        return pBody;
    }
}
