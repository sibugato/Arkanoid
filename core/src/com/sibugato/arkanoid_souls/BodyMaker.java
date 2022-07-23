package com.sibugato.arkanoid_souls;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class BodyMaker {

    public static Body createBox (World world, float x, float y, float width, float height, BodyDef.BodyType BodyType, Filter filter, float density, float friction, float restitution, float gravity, boolean Bullet, String UserData) {

        BodyDef bDef = new BodyDef();
        bDef.type = BodyType;
        bDef.position.set(x,y);

        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(width,height);
        fDef.shape = shape;
        fDef.density =density;
        fDef.friction = friction;
        fDef.restitution = restitution;
        fDef.filter.set(filter);

        Body body = world.createBody(bDef);
        body.createFixture(fDef);
        body.setBullet(Bullet);
        body.setGravityScale(gravity);
        body.getFixtureList().get(0).setUserData(UserData);
        return body;
    }

    public static Body createCircle (World world, float x, float y, float radius, BodyDef.BodyType BodyType, Filter filter, float density, float friction, float restitution, float gravity, boolean Bullet, String UserData) {

        BodyDef bDef = new BodyDef();
        bDef.type = BodyType;
        bDef.position.set(x,y);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(radius);
        fDef.shape = shape;
        fDef.density =density;
        fDef.friction = friction;
        fDef.restitution = restitution;
        fDef.filter.set(filter);

        Body body = world.createBody(bDef);
        body.createFixture(fDef);
        body.setBullet(Bullet);
        body.setGravityScale(gravity);
        body.getFixtureList().get(0).setUserData(UserData);
        return body;
    }

    public static Body createChain (World world, float x, float y, Vector2[] Vectors, BodyDef.BodyType BodyType, Filter filter, float density, float friction, float restitution, float gravity, boolean Bullet, String UserData) {

        BodyDef bDef = new BodyDef();
        bDef.type = BodyType;
        bDef.position.set(x,y);

        FixtureDef fDef = new FixtureDef();
        ChainShape shape = new ChainShape();

        shape.createChain(Vectors);
        fDef.shape = shape;
        fDef.density =density;
        fDef.friction = friction;
        fDef.restitution = restitution;
        fDef.filter.set(filter);

        Body body = world.createBody(bDef);
        body.createFixture(fDef);
        body.setBullet(Bullet);
        body.setGravityScale(gravity);
        body.getFixtureList().get(0).setUserData(UserData);
        return body;
    }

    public static void jointRevolute (World world, Body BodyMain, Body BodySecond, boolean EnableMotor, float motorSpeed, float maxMotorTorque) {
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(BodySecond, BodyMain, BodyMain.getWorldCenter());
        revoluteJointDef.enableMotor = EnableMotor;
        revoluteJointDef.motorSpeed = motorSpeed;
        revoluteJointDef.maxMotorTorque = maxMotorTorque;
        world.createJoint(revoluteJointDef);
    }
}
