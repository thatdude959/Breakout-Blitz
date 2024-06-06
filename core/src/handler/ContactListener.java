package handler;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import entitys.Ball;
import entitys.Brick;
import entitys.Floor;
import entitys.Paddle;
import screens.GameScreen;

import java.util.ArrayList;

import static com.mygdx.game.utils.Constants.PPM;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
    public ArrayList<Body> destroyList;
    public boolean isPaddleHit;
    public boolean isFloorHit;
    public float paddleHitLocation;

    public ContactListener() {
        destroyList = new ArrayList<Body>();
        isPaddleHit = false;
        isFloorHit = false;
        paddleHitLocation = 0;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        if (isPaddleContact(fa, fb)) {
            Ball ball;
            Paddle paddle;
            if (fa.getUserData() instanceof Ball) {
                ball = (Ball) fa.getUserData();
                paddle = (Paddle) fb.getUserData();
            } else {
                ball = (Ball) fb.getUserData();
                paddle = (Paddle) fa.getUserData();
            }
            if (contact.getWorldManifold().getNumberOfContactPoints() > 0) {
                if (contact.getWorldManifold().getNormal().y > 0.99){
                    Vector2 worldHitPoint = contact.getWorldManifold().getPoints()[0];
                    Vector2 paddleLocation = paddle.body.getPosition();
                    float paddleWidth = 150 / PPM;
                    paddleHitLocation = Math.max(-1, Math.min(1, (worldHitPoint.x - paddleLocation.x) / paddleWidth * 2));
                    isPaddleHit = true;
                }
            }
        }

        if (isBrickContact(fa, fb)) {
            Ball ball;
            Brick brick;
            if (fa.getUserData() instanceof Ball) {
                ball = (Ball) fa.getUserData();
                brick = (Brick) fb.getUserData();
            } else {
                ball = (Ball) fb.getUserData();
                brick = (Brick) fa.getUserData();
            }
            destroyList.add(brick.body);
        }

        if (isFloorContact(fa, fb)) {
            Ball ball;
            Floor floor;
            if (fa.getUserData() instanceof Ball) {
                ball = (Ball) fa.getUserData();
                floor = (Floor) fb.getUserData();
            } else {
                ball = (Ball) fb.getUserData();
                floor = (Floor) fa.getUserData();
            }
            destroyList.add(ball.body);
            isFloorHit = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean isPaddleContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Paddle && b.getUserData() instanceof Ball) {
            return true;
        }
        if (a.getUserData() instanceof Ball && b.getUserData() instanceof Paddle) {
            return true;
        }
        return false;
    }

    private boolean isBrickContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Brick && b.getUserData() instanceof Ball) {
            return true;
        }
        if (a.getUserData() instanceof Ball && b.getUserData() instanceof Brick) {
            return true;
        }
        return false;
    }

    private boolean isFloorContact(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Floor && b.getUserData() instanceof Ball) {
            return true;
        }
        if (a.getUserData() instanceof Ball && b.getUserData() instanceof Floor) {
            return true;
        }
        return false;
    }
}
