package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        if (isTutorialContact(fa, fb)) {
            TotorialBox tba = (TotorialBox) fa.getUserData();
            TotorialBox tbb = (TotorialBox) fb.getUserData();

            if (tbb.id == "OBJ1") {

            } else {

            }
            if (tbb.id == "OBJ2") {

            } else {

            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) return;
        if (fa.getUserData() == null || fb.getUserData() == null) return;

        System.out.println("Collision stoped");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean isTutorialContact(Fixture a, Fixture b) {
        return (a.getUserData() instanceof TotorialBox && b.getUserData() instanceof TotorialBox);
    }
}
