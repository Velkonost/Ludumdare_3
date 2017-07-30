package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameScreen;

import static com.mygdx.game.Constants.*;

/**
 * @author Velkonost
 */
public class EarthEntity extends Actor {

    private World world;

    private GameScreen game;

    private Body body;

    private Fixture fixture;//

    private Texture texture;

    private boolean moveUp = false;

    public EarthEntity(Texture texture, GameScreen game, World world, float x, float y) {
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
        box.setAsBox(0.4f, 0.4f);

        fixture = body.createFixture(box, 1000000000);
        fixture.setUserData("earth");

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
        if (getY() > 600) moveUp = false;
        if (getY() < 50) moveUp = true;

        if (moveUp) {
            body.setLinearVelocity(body.getLinearVelocity().x, PLANET_UP_SPEED);
        } else {
            body.setLinearVelocity(body.getLinearVelocity().x, -PLANET_DOWN_SPEED);
        }

        setPosition((body.getPosition().x) * PIXELS_IN_METER,
                (body.getPosition().y) * PIXELS_IN_METER);


        batch.draw(texture, getX(), getY(), (getWidth()) / 2, (getHeight()) / 2, getWidth(), getHeight(), 1,
                    1, 0, 1, 1, 900, 900, false, false);
    }
}
