package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.BreakoutBlitz;
import entitys.*;
import handler.ContactListener;
import handler.Updater;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.game.utils.Constants.BALL_SPEED;
import static com.mygdx.game.utils.Constants.PPM;

public class GameScreen extends ScreenAdapter {
    BreakoutBlitz game;
    private OrthographicCamera camera;

    private Box2DDebugRenderer b2dr;
    private World world;

    private Body player, sLeft, sRight, sTop;
    private Floor floor;
    private Paddle paddle;
    private Ball ball;
    public static ArrayList<Brick> bricks;
    private ContactListener contactListener;
    private int justCreated = 0;

    public GameScreen(BreakoutBlitz game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        world = new World(new Vector2(0, 0), false);
        contactListener = new ContactListener();
        world.setContactListener(contactListener);
        b2dr = new Box2DDebugRenderer();
        //border
        floor = new Floor(world, "FLOOR", (float) Gdx.graphics.getWidth() / 2, -1, Gdx.graphics.getWidth(), 0);
        sTop = Spawner.box(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 0, false, world);
        sLeft = Spawner.box(0, Gdx.graphics.getHeight() / 2, 0, Gdx.graphics.getHeight(), false, world);
        sRight = Spawner.box(Gdx.graphics.getWidth() + 1, Gdx.graphics.getHeight() / 2, 0, Gdx.graphics.getHeight(), false, world);

        paddle = new Paddle(world, "PADDLE", 360, 15, 150, 10);

        bricks = new ArrayList<Brick>();
        for (int i = 0; i < 4; i++) {
            int y = Gdx.graphics.getHeight() - 25 - 40 * i;
            for (int j = 0; j < 9; j++) {
                int x = 40 + 80 * j;
                bricks.add(new Brick(world, "BRICK", x, y, 75, 35));
            }
        }

        ball = new Ball(world, "BALL", 360, 40, 10);

        Random r = new Random();
        float random = 0.2f + r.nextFloat() * (0.8f - 0.2f);
        float theta = random;
        ball.body.setLinearVelocity((new Vector2((float) Math.cos(theta), (float) Math.sin(theta)).scl(BALL_SPEED)));
    }

    @Override
    public void render(float delta) {
        Updater.update(Gdx.graphics.getDeltaTime(), world, camera, paddle, ball, contactListener);
        ScreenUtils.clear(0, 0, 0, 1);

        if (contactListener.isFloorHit) {
            game.setScreen(new EndScreen(game));
        }

        if (bricks.isEmpty()) {
            contactListener.destroyList.clear();
            game.setScreen(new GameScreen(game));
        }

        b2dr.render(world, camera.combined.scl(PPM));
        if (justCreated == 1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            justCreated++;
        } else if (justCreated != 2) {
            justCreated++;
        }
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public void resize(int width, int height) {
        camera.viewportWidth = 720;
        if ((double) width / (double) height < 1.5) {
            camera.viewportHeight = (float) (camera.viewportWidth * (double) height / (double) width);
        } else if ((double) width / (double) height > 1.5) {
            camera.viewportWidth = (float) ((camera.viewportHeight * (double) width) / (double) height);
        }
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
    }
}