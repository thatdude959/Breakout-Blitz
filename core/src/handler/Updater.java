package handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import entitys.Ball;
import entitys.Brick;
import entitys.Paddle;
import screens.GameScreen;

import static com.mygdx.game.utils.Constants.BALL_SPEED;
import static com.mygdx.game.utils.Constants.PPM;

public class Updater {

    public static void update(float deltaTime, World world, OrthographicCamera camera, Paddle paddle, Ball ball, ContactListener contactListener) {
        world.step(1 / 60f, 6, 2);
        cameraUpdate(deltaTime, camera);
        inputUpdate(deltaTime, paddle);

        if (contactListener.destroyList != null) {
            for (Body body : contactListener.destroyList) {
                world.destroyBody(body);
            }
            for (Body body : contactListener.destroyList) {
                GameScreen.bricks.removeIf(brick -> brick.body == body);
            }
            contactListener.destroyList.clear();
        }

        Vector2 ballVel = ball.body.getLinearVelocity();
        if (ballVel.angleDeg() > 10 && ballVel.angleDeg() < 0) {
            ballVel.setAngleDeg(20);
        }
        if (ballVel.angleDeg() > 350 && ballVel.angleDeg() < 360) {
            ballVel.setAngleDeg(20);
        }
        if (ballVel.angleDeg() > 170 && ballVel.angleDeg() < 190) {
            ballVel.setAngleDeg(160);
        }
        ballVel.nor();

        if (contactListener.isPaddleHit) {
            float angle = ballVel.angleDeg();
            angle = angle - contactListener.paddleHitLocation * 45;
            angle = Math.max(20, Math.min(160, angle));
            ballVel = new Vector2(1, 0).rotateDeg(angle);

            contactListener.isPaddleHit = false;
            contactListener.paddleHitLocation = 0;
        }

        ball.body.setLinearVelocity(ballVel.scl(BALL_SPEED));

    }

    private static void inputUpdate(float deltaTime, Paddle paddle) {
        float horizontalVelocity = 0;
        //checks paddle collision with left wall
        if (Gdx.input.isKeyPressed(Input.Keys.A) && paddle.body.getPosition().x * PPM - 50 >= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                horizontalVelocity -= 1.75f;
            } else {
                horizontalVelocity -= 1;
            }
        }
        //checks paddle collision with right wall
        if (Gdx.input.isKeyPressed(Input.Keys.D) && paddle.body.getPosition().x * PPM + 50 <= Gdx.graphics.getWidth()) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                horizontalVelocity += 1.75f;
            } else {
                horizontalVelocity += 1;
            }
        }
        paddle.body.setLinearVelocity(horizontalVelocity * 10, paddle.body.getLinearVelocity().y);
    }

    private static void cameraUpdate(float deltaTime, OrthographicCamera camera) {
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }
}
