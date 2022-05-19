package com.sibugato.arkanoid_souls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.WorldManifold;

import objects.Ball;

public class ContactWatcher implements ContactListener {

    // for(int j = 0; j < manifold.getNumberOfContactPoints(); j++) {}

    Resources sound = new Resources();

    public void beginContact(Contact contact) { Fixture Fix_A = contact.getFixtureA(); Fixture Fix_B = contact.getFixtureB(); Body Body_A = contact.getFixtureA().getBody(); Body Body_B = contact.getFixtureA().getBody();
        if(Fix_A.getUserData() != null && Fix_B.getUserData().equals("ball") && !Fix_A.getUserData().equals("platform0") && !Fix_A.getUserData().equals("bell1") && !Fix_A.getUserData().equals("bell2")) {
            Ball.isBounce = true;
        }
        if (Fix_A.getUserData() != null && Fix_A.getUserData().equals("vortex")) {
            contact.setEnabled(false);
            Body_A.getFixtureList().get(0).setFilterData(Constants.FILTER_VOID);
            Constants.isVortexActive = true;
            System.out.println(1);
        }

    }

    public void endContact(Contact contact) { Fixture Fix_A = contact.getFixtureA(); Fixture Fix_B = contact.getFixtureB(); Body Body_A = contact.getFixtureA().getBody(); Body Body_B = contact.getFixtureA().getBody();
        if(Fix_A.getUserData() != null && Fix_B.getUserData().equals("ball") && !Fix_A.getUserData().equals("platform0")) Ball.RAGE = true;
    }


    public void preSolve (Contact contact, Manifold oldManifold) {
        WorldManifold manifold = contact.getWorldManifold();
        for(int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
            if(contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals("vortex")) {
                contact.setEnabled(false);
                contact.getFixtureA().getBody().getFixtureList().get(0).setFilterData(Constants.FILTER_ACTIVE_VORTEX);
                Constants.isVortexActive = true;
            }
            if(contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals("vortex")) {
                contact.setEnabled(false);
                contact.getFixtureB().getBody().getFixtureList().get(0).setFilterData(Constants.FILTER_ACTIVE_VORTEX);
                Constants.isVortexActive = true;
            }
        }
    }


    public void postSolve(Contact contact, ContactImpulse impulse) { Fixture Fix_A = contact.getFixtureA(); Fixture Fix_B = contact.getFixtureB(); Body Body_A = contact.getFixtureA().getBody(); Body Body_B = contact.getFixtureA().getBody();
        WorldManifold manifold = contact.getWorldManifold();

        if (Fix_B.getUserData().equals("ball") && (Fix_A.getUserData().equals("bell1"))) {
            Body_A.getFixtureList().get(0).setFilterData(Constants.FILTER_VOID);
            Constants.isBell1Hit = true;
            sound.play("bell");
            Gdx.input.vibrate(2000);
        }

        else if (Fix_B.getUserData().equals("ball") && (Fix_A.getUserData().equals("bell2"))) {
            Body_A.getFixtureList().get(0).setFilterData(Constants.FILTER_VOID);
            Constants.isBell2Hit = true;
            sound.play("bell");
            Gdx.input.vibrate(2000);
        }

        if (Fix_A.getUserData().equals("ball") && (Fix_B.getUserData().equals("circle"))) {
            sound.play("FORCE");
        }

        if (Fix_B.getUserData().equals("ball") && (Fix_A.getUserData().equals("platform1"))) {
            sound.play("PLATFORM_BROKE");
        }

        if (Fix_B.getUserData().equals("ball") && Fix_A.getUserData().equals("platform5"))
            Body_A.getFixtureList().get(0).setUserData("platform4");

        else if (Fix_B.getUserData().equals("ball") && Fix_A.getUserData().equals("platform4"))
            Body_A.getFixtureList().get(0).setUserData("platform3");

        else if (Fix_B.getUserData().equals("ball") && Fix_A.getUserData().equals("platform3"))
        Body_A.getFixtureList().get(0).setUserData("platform2");

        else if (Fix_B.getUserData().equals("ball") && Fix_A.getUserData().equals("platform2"))
        Body_A.getFixtureList().get(0).setUserData("platform1");

        else if (Fix_B.getUserData().equals("ball") && Fix_A.getUserData().equals("platform1"))
        {Body_A.getFixtureList().get(0).setUserData("platform0"); Body_A.getFixtureList().get(0).setFilterData(Constants.FILTER_VOID);

            //if (!Stage_1.destroy_list.contains(Body_A))  {Stage_1.destroy_list.add(Body_A); Stage_1.deliting = true; }

        }}

}


