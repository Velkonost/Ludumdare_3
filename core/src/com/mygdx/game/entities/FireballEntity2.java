//package com.mygdx.game.entities;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.physics.box2d.*;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.mygdx.game.GameScreen;
//
//import static com.mygdx.game.Constants.PIXELS_IN_METER;
//import static com.mygdx.game.entities.RocketEntity.SPEED_ROCKET;
//
///**
// * Created by admin on 29.07.2017.
// */
//public class FireballEntity2 extends Actor {
//    private World world;
//
//    private GameScreen game;
//
//    private Body body;
//
//    public float x_main, y_main;
//
//    private Fixture fixture;//
//
//    private Texture texture;
//
//    private boolean moveUp = false;
//    private float x_body = 1.5f;
//
//    public FireballEntity2(Texture texture, GameScreen game, World world, float x, float y, float x_main, float y_main) {
//        this.texture = texture;
//
//        this.x_main = x_main;
//        this.y_main = y_main;
//        this.world = world;
//        this.game = game;
//        setPosition(x, y);
//
//        BodyDef def = new BodyDef();
//        def.position.set(x, y);
//        def.type = BodyDef.BodyType.DynamicBody;
//
//        body = world.createBody(def);
//        body.setFixedRotation(true);
//
//        final PolygonShape box = new PolygonShape();
//        box.setAsBox(0.2f, 0.1f);
//
//        fixture = body.createFixture(box, 1000000000);
//        fixture.setUserData("fireball");
//
//        body.setFixedRotation(false);
//
//        box.dispose();
//
//        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
//    }
//
//    public void detach() {
//        body.destroyFixture(fixture);
//        world.destroyBody(body);
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        if (getY() > 640) moveUp = false;
//        if (getY() < 0) moveUp = true;
//
//        if (moveUp) {
//            body.setLinearVelocity(body.getLinearVelocity().x, SPEED_ROCKET);
//        } else {
//            body.setLinearVelocity(body.getLinearVelocity().x, -SPEED_ROCKET);
//        }
//
//        x_body-=0.02f;
//        setPosition((x_body+body.getPosition().x) * PIXELS_IN_METER,
//                (body.getPosition().y) * PIXELS_IN_METER);
//        //body.setTransform(x_main, y_main, 90);
//
//        if(moveUp) {
//            batch.draw(texture, getX(), getY(), (getWidth()) / 2, (getHeight()) / 2, getWidth(), getHeight(), 1f,
//                    1, 45, 0, 0, 900, 900, false, false);
//        }else{
//            batch.draw(texture, getX(), getY(), (getWidth()) / 2, (getHeight()) / 2, getWidth(), getHeight(), 1f,
//                    1, -45, 0, 0, 900, 900, false, false);
//        }
//    }
//}
