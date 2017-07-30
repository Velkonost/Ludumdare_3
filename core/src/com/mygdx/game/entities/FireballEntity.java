package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameScreen;

import static com.mygdx.game.Constants.PIXELS_IN_METER;

/**
 * Created by admin on 29.07.2017.
 */
public class FireballEntity extends Actor {
    private World world;

    public boolean isRemoved = false;
    private GameScreen game;

    private Body body;

    public float x_main, y_main;

    private Fixture fixture;//

    private float x_body = 0f;

    private Texture texture;

    private float xVelocity;

    public float speed = 2f;

    public FireballEntity(Texture texture, GameScreen game, World world, float x, float y, float x_main, float y_main) {
        this.texture = texture;

        this.x_main = x_main;
        this.y_main = y_main;
        this.world = world;
        this.game = game;
        setPosition(x, y);

        BodyDef def = new BodyDef();
        def.position.set(x, y);
        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);
        body.setFixedRotation(true);

        final PolygonShape box = new PolygonShape();
        box.setAsBox(0.4f, 0.4f);

        fixture = body.createFixture(box, 1);
        fixture.setUserData("fireball");

        body.setFixedRotation(false);

        box.dispose();

        xVelocity = -5f + (int)(Math.random() * ((5 + 5f) + 1));

        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        body.setLinearVelocity(xVelocity, -speed);
        ;
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

    public void setUserData(int i){
        fixture.setUserData("fireball"+i);
    }
}
