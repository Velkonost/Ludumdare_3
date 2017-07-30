package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameScreen;

import static com.mygdx.game.Constants.PIXELS_IN_METER;

/**
 * @author Velkonost
 */
public class LightEntity extends Actor {
    private World world;

    private GameScreen game;

    private Body body;

    public boolean isRemoved = false;

    public float x_main, y_main;

    private Fixture fixture;//

    private float x_body = 0f;

    private Texture texture;

    private float xVelocity;

    private String direction;

    public float speed = 2f;

    public LightEntity(Texture texture, GameScreen game, World world, float x, float y, String direction, int userData) {
        this.texture = texture;

        this.direction = direction;
        this.world = world;
        this.game = game;
        setPosition(x, y);

        BodyDef def = new BodyDef();
        def.position.set(x, y);
        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);
        body.setFixedRotation(true);

        final PolygonShape box = new PolygonShape();
        box.setAsBox(0.25f, 0.5f);

        fixture = body.createFixture(box, 1);
        fixture.setUserData("light"+userData);

        body.setFixedRotation(false);

        box.dispose();

//        xVelocity = -3f + (int) (Math.random() * ((3 + 3f) + 1));

        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public Body getBody() {
        return body;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (direction.equals("top left")) {
            body.setLinearVelocity(speed, -speed);
        } else if (direction.equals("top right")) {
            body.setLinearVelocity(-speed, -speed);
        } else if (direction.equals("bot left")) {
            body.setLinearVelocity(speed, speed);
        } else if (direction.equals("bot right")) {
            body.setLinearVelocity(-speed, speed);
        }


//        x_body += 0.01f;
        setPosition((body.getPosition().x) * PIXELS_IN_METER,
                (body.getPosition().y) * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());

    }

    public void removeFixture(){
        isRemoved = true;
    }

    public Fixture getFixture() {
        return fixture;
    }
}