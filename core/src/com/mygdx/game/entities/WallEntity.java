package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.Constants.PIXELS_IN_METER;

/**
 * @author Velkonost
 */
public class WallEntity extends Actor {
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    float width, height, x, y, dwidth, dheight;

    public WallEntity(World world, float x, float y, float width, float height) {
//        this.texture = texture;
        this.world = world;

//        x -= 1.5f;
//        y -= 0.5f;
        this.width = width;
        this.height = height;

        this.dwidth = dwidth;
        this.dheight = dheight;

        setPosition(x, y);


        BodyDef def = new BodyDef();
        def.position.set(x, y);
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height/ 2);
        fixture = body.createFixture(shape, 1);
        fixture.setUserData("wall");
        shape.dispose();

        setSize(width * PIXELS_IN_METER, height * PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x) * PIXELS_IN_METER, (body.getPosition().y) * PIXELS_IN_METER);
//        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}