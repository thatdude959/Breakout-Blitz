package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.game.utils.Constants.BALL_SPEED;
import static com.mygdx.game.utils.Constants.PPM;

public class Updater {
    public static void update(float deltaTime, World world, OrthographicCamera camera, Body paddle, Body ball) {
        world.step(1 / 60f, 6, 2);
        cameraUpdate(deltaTime, camera);
        inputUpdate(deltaTime, paddle);

        Vector2 ballVel = ball.getLinearVelocity();
        ballVel.nor();
        ball.setLinearVelocity(ballVel.scl(BALL_SPEED));
    }

    private static void inputUpdate(float deltaTime, Body paddle) {
        int horizontalVelocity = 0;
        //checks paddle collision with left wall
        if (Gdx.input.isKeyPressed(Input.Keys.A) && paddle.getPosition().x * PPM - 50 >= 0) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                horizontalVelocity -= 5;
            } else {
                horizontalVelocity -= 1;
            }
        }
        //checks paddle collision with right wall
        if (Gdx.input.isKeyPressed(Input.Keys.D) && paddle.getPosition().x * PPM + 50 <= Gdx.graphics.getWidth()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                horizontalVelocity += 5;
            } else {
                horizontalVelocity += 1;
            }
        }
        paddle.setLinearVelocity(horizontalVelocity * 10, paddle.getLinearVelocity().y);
    }

    private static void cameraUpdate(float deltaTime, OrthographicCamera camera) {
        camera.position.set(new Vector3((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2, 0));
        camera.update();
    }
}
