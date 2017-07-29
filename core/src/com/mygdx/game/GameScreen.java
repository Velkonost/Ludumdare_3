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
import com.mygdx.game.entities.RocketEntity;

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

    private OrthographicCamera camera;

    private RocketEntity rocket;

    private Texture rocketTexture;

    public GameScreen(MainGame game) {
        super(game);
        this.game = game;
        stage = new Stage(new FitViewport(1280, 720));
        world = new World(new Vector2(0, 0), true);

    }

    @Override
    public void show() {

        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(16, 9);
        camera.translate(0, 1);

        getTextures();

        sp = new SpriteBatch();



        font = new BitmapFont();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

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

        stage.addActor(rocket);
        rocket.boom(true);

//        if(a%60>9){
//            str = ""+(int)floor(a/60)+":"+a%60;
//        }else{
//            str = ""+(int)floor(a/60)+":0"+a%60;
//        }

//        sp.begin();
//        font.getData().setScale(3, 3);
//        font.draw(sp, str, Gdx.graphics.getWidth()-200,  Gdx.graphics.getHeight()-50);
//        sp.end();

        stage.act();

        rocket.processInput();

        stage.getCamera().position.set(rocket.getX(), rocket.getY(), 0);


        world.step(delta, 6, 2);
        camera.update();
        renderer.render(world, camera.combined);
        stage.draw();

    }

    private void getTextures() {



//        if (choosenVlog.equals("myach1")) {
//            playerVlogerTexture = game.getManager().get("myachhero.png");
//            playerVlogerCameraTexture = game.getManager().get("myachheroCamera.png");
//        }
//        else {
//            playerVlogerTexture = game.getManager().get("myach2hero.png");
//            playerVlogerCameraTexture = game.getManager().get("myach2heroCamera.png");
//        }
//
//
//        if(choosenProg.equals("player1")) {
//            playerProgerTexture = game.getManager().get("player1hero.png");
//        } else if (choosenProg.equals("player2")) {
//            playerProgerTexture = game.getManager().get("player2hero.png");
//        }
//
//        for (int i = 1; i <= 3; i++) {
//            botsIdleTexture.add(
//                    (Texture) game.getManager().get("player" + i + "hero.png")
//            );
//        }
//
//        phoneTexture = game.getManager().get("mobile.png");
//        botIdleTexture = game.getManager().get("myach2hero.png");
    }

    public void hide() {
        rocket.detach();

        rocket.remove();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        renderer.dispose();
    }

}
