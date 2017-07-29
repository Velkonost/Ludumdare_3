package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.entities.*;

import java.util.ArrayList;

/**
 * @author Velkonost
 */
public class GameScreen extends BaseScreen {

    private final float UPDATE_TIME = 1/60f;
    private float timer;

    private Stage stage;

    private World world;
    public MainGame game;

    private Box2DDebugRenderer renderer;

    BitmapFont font;
    SpriteBatch sp;
    CharSequence str;

    private ArrayList<WallEntity> wall;

    private int amountResources = 0;

    private OrthographicCamera camera;

    private RocketEntity rocket;
    private EarthEntity earth;
    private MarsEntity mars;
    private FireballEntity fireball;

    private Texture rocketTexture;
    private Texture earthTexture;
    private Texture marsTexture;
    private Texture background;
    private Texture fireballTexture;

    private boolean haveResource = false;

    public GameScreen(MainGame game) {
        super(game);
        this.game = game;
        stage = new Stage(new FitViewport(1280, 720));
        world = new World(new Vector2(0, -50), true);

        wall = new ArrayList<WallEntity>();
    }

    @Override
    public void show() {

        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(64, 36);
        camera.translate(0, 1);

        getTextures();

        rocket = new RocketEntity(rocketTexture, this, world, 9f, 7f);
        earth = new EarthEntity(earthTexture, this, world, 1f, 0f);
        mars = new MarsEntity(marsTexture, this, world, 12f, 7f);
        fireball = new FireballEntity(fireballTexture, this, world, 5f, 7f, rocket.getX(), rocket.getY());

        sp = new SpriteBatch();

        wall.add(new WallEntity(world, 10f, -1f, 30f, 1f));
        wall.add(new WallEntity(world, -1f, 0f, 1f, 30f));
        wall.add(new WallEntity(world, 14f, 0f, 1f, 30f));
        wall.add(new WallEntity(world, 10f, 8f, 30f, 1f));

        rocket.boom(true);
        stage.addActor(fireball);
        stage.addActor(rocket);
        stage.addActor(earth);
        stage.addActor(mars);

        for (WallEntity aWall : wall) {
            stage.addActor(aWall);
        }

        font = new BitmapFont();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if ((fixtureA.getUserData().equals("mars") && fixtureB.getUserData().equals("rocket"))
                        || (fixtureA.getUserData().equals("rocket") && fixtureB.getUserData().equals("mars"))) {
                    haveResource = true;
                }

                if ((fixtureA.getUserData().equals("earth") && fixtureB.getUserData().equals("rocket"))
                        || (fixtureA.getUserData().equals("rocket") && fixtureB.getUserData().equals("earth"))) {
                    if (haveResource) {
                        haveResource = false;
                        amountResources ++;
                    }

                }

            }
            @Override
            public void endContact(Contact contact) {

                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

            }
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        fireball.x_main = rocket.getX();
        fireball.y_main = rocket.getY();

        sp.begin();

        sp.end();

        stage.act();

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, 1280, 720);
        font.getData().setScale(3, 3);
        font.draw(stage.getBatch(), String.valueOf(amountResources), Gdx.graphics.getWidth()-200,  Gdx.graphics.getHeight()-50);
        stage.getBatch().end();

        rocket.processInput();
        world.step(delta, 6, 2);
        camera.update();
        renderer.render(world, camera.combined);

        stage.draw();


    }

    private void getTextures() {

        rocketTexture = game.getManager().get("player_main.png");
        earthTexture = game.getManager().get("earth.png");
        marsTexture = game.getManager().get("mars.png");
        fireballTexture = game.getManager().get("fireball.png");
        background = game.getManager().get("background.png");

    }

    public void hide() {
        rocket.detach();
        earth.detach();
        mars.detach();
        fireball.detach();

        rocket.remove();
        mars.remove();
        earth.remove();
        fireball.remove();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        renderer.dispose();
    }

}
