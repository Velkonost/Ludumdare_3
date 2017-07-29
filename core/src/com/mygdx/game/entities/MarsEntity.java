package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameScreen;

import static com.mygdx.game.Constants.PIXELS_IN_METER;
import static com.mygdx.game.entities.RocketEntity.SPEED_ROCKET;

/**
 * @author Velkonost
 */
public class MarsEntity extends Actor {

    private World world;

    private GameScreen game;

    private Body body;

    private Fixture fixture;//

    private Texture texture;


    public MarsEntity(Texture texture, GameScreen game, World world, float x, float y) {
        this.texture = texture;

        this.world = world;
        this.game = game;
        setPosition(x, y);

        BodyDef def = new BodyDef();
        def.position.set(x, y);
        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);
        body.setFixedRotation(true);

        final PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);

        fixture = body.createFixture(box, 1);
        fixture.setUserData("mars");

        body.setFixedRotation(false);

        box.dispose();

        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        if (getY() < 70f) {
            body.setLinearVelocity(body.getLinearVelocity().x, SPEED_ROCKET);
//        } else {
//            body.setLinearVelocity(body.getLinearVelocity().x, -SPEED_ROCKET);
//        }
        System.out.println();
        setPosition((body.getPosition().x) * PIXELS_IN_METER,
                (body.getPosition().y) * PIXELS_IN_METER);


        batch.draw(texture, getX(), getY(), (getWidth()) / 2, (getHeight()) / 2, getWidth(), getHeight(), 1,
                1, 0, 1, 1, 900, 900, false, false);
    }
}

