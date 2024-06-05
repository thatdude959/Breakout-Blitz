package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import entitys.*;
import handler.ContactListener;
import handler.Updater;

import java.util.ArrayList;

import static com.mygdx.game.utils.Constants.BALL_SPEED;
import static com.mygdx.game.utils.Constants.PPM;

public class BreakoutBlitz extends ApplicationAdapter {
    private OrthographicCamera camera;

    private Box2DDebugRenderer b2dr;
    private World world;

    private Body player, sLeft, sRight, sTop;
    private Floor floor;
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks;
    private ContactListener contactListener;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        world = new World(new Vector2(0, 0), false);
        contactListener = new ContactListener();
        world.setContactListener(contactListener);
        b2dr = new Box2DDebugRenderer();

        paddle = new Paddle(world, "PADDLE", 360, 15, 100, 10);

        bricks = new ArrayList<Brick>();
        for (int i = 0; i < 4; i++) {
            int y = Gdx.graphics.getHeight() - 25 - 40 * i;
            for (int j = 0; j < 9; j++) {
                int x = 40 + 80 * j;
                bricks.add(new Brick(world, "BRICK", x, y, 75, 35));
            }
        }
        //border
        floor = new Floor(world, "FLOOR", Gdx.graphics.getWidth() / 2, -1, Gdx.graphics.getWidth(), 0);
        sTop = Spawner.box(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 0, false, world);
        sLeft = Spawner.box(0, Gdx.graphics.getHeight() / 2, 0, Gdx.graphics.getHeight(), false, world);
        sRight = Spawner.box(Gdx.graphics.getWidth() + 1, Gdx.graphics.getHeight() / 2, 0, Gdx.graphics.getHeight(), false, world);

        ball = new Ball(world, "BALL", 360, 160, 10);
        float theta = (float) (Math.PI / 2);
        ball.body.setLinearVelocity((new Vector2((float) Math.cos(theta), (float) Math.sin(theta)).scl(BALL_SPEED)));
    }

    @Override
    public void render() {
        Updater.update(Gdx.graphics.getDeltaTime(), world, camera, paddle, ball, contactListener);
        ScreenUtils.clear(0, 0, 0, 1);

        b2dr.render(world, camera.combined.scl(PPM));
    }

    @Override
    public void resize(int width, int height) {
        double widthf = width;
        double heightf = height;

        camera.viewportWidth = 720;
        if (widthf / heightf < 1.5) {
            camera.viewportHeight = (float) (camera.viewportWidth * heightf / widthf);
        } else if (widthf / heightf > 1.5) {
            camera.viewportWidth = (float) ((camera.viewportHeight * widthf)/heightf);
        }

        System.out.println(camera.viewportWidth + " " + camera.viewportHeight);
        System.out.println(widthf + " " + heightf);
        System.out.println(widthf / heightf + " h/w " + heightf / widthf);
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
    }
}
