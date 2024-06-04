package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

import java.security.PublicKey;

import static com.mygdx.game.utils.Constants.PPM;

public class TotorialBox {
    public Body body;
    public String id;

    public TotorialBox(World world, String id, float x, float y) {
        this.id = id;
        createBoxBody(x, y, world);
    }

    private void createBoxBody(float x, float y, World world) {
        BodyDef bDef = new BodyDef();
        bDef.fixedRotation = true;
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(x / PPM, y / PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((float) 32 / 2 / PPM, (float) 32 / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;

        this.body = world.createBody(bDef);
        this.body.createFixture(fixtureDef).setUserData(this);
    }

    public void hit(){
        System.out.println(id + "hit");
    }
}
