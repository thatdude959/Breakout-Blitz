package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import static com.mygdx.game.utils.Constants.BALL_SPEED;
import static com.mygdx.game.utils.Constants.PPM;

public class BreakoutBlitz extends ApplicationAdapter {
    private OrthographicCamera camera;

    private Box2DDebugRenderer b2dr;
    private World world;

    private Body paddle, ball, sLeft, sRight, sTop, sBottom, brick;
//    private ArrayList<Body> bricks;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();
        paddle = Spawner.box(360, 15, 100, 10, true, world);
        ball = Spawner.ball(360, 180, 10, world);
        brick = Spawner.box(360, 240, 70, 35, false, world);
        //border
        sBottom = Spawner.box(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth(), 0, false, world);
        sTop = Spawner.box(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 0, false, world);
        sLeft = Spawner.box(0, Gdx.graphics.getHeight() / 2, 0, Gdx.graphics.getHeight(), false, world);
        sRight = Spawner.box(Gdx.graphics.getWidth() + 1, Gdx.graphics.getHeight() / 2, 0, Gdx.graphics.getHeight(), false, world);

        float theta = 0.7f;
        ball.setLinearVelocity(new Vector2((float) Math.cos(theta), (float) Math.sin(theta)).scl(BALL_SPEED));
    }

    @Override
    public void render() {
        Updater.update(Gdx.graphics.getDeltaTime(), world, camera, paddle, ball);
        ScreenUtils.clear(0, 0, 0, 1);

        b2dr.render(world, camera.combined.scl(PPM));
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
    }
}
