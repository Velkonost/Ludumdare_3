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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Velkonost
 */
public class GameScreen extends BaseScreen {

    private final float UPDATE_TIME = 1/60f;
    private float timer = 0;

    private Stage stage;

    private World world;
    public MainGame game;

    private Box2DDebugRenderer renderer;

    BitmapFont font;
    SpriteBatch sp;
    CharSequence str;

    private ArrayList<WallEntity> wall;
    private ArrayList<LightEntity> light;

    private int amountResources = 0;

    private OrthographicCamera camera;

    private RocketEntity rocket;
    private EarthEntity earth;
    private MarsEntity mars;
    private FireballEntity fireball;
//    private static FireballEntity2 fireball2;

    private Texture rocketTexture;
    private Texture earthTexture;
    private Texture marsTexture;
    private Texture background;
    private Texture fireballTexture;

    private ArrayList<FireballEntity> fireballs;
    private ArrayList<Integer> fireballs_del;

    private boolean haveResource = false;

    public GameScreen(MainGame game) {
        super(game);
        this.game = game;
        stage = new Stage(new FitViewport(1280, 720));
        world = new World(new Vector2(0, -50), true);

        wall = new ArrayList<WallEntity>();
        light = new ArrayList<LightEntity>();
    }

    @Override
    public void show() {

        fireballs = new ArrayList<FireballEntity>();
        fireballs_del = new ArrayList<Integer>();
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(64, 36);
        camera.translate(0, 1);

        getTextures();

        rocket = new RocketEntity(rocketTexture, this, world, 9f, 7f);
        earth = new EarthEntity(earthTexture, this, world, 1f, 0f);
        mars = new MarsEntity(marsTexture, this, world, 12f, 7f);
//        fireball2 = new FireballEntity2(fireballTexture, this, world, 4.5f, 7f, rocket.getX(), rocket.getY());

        sp = new SpriteBatch();

        wall.add(new WallEntity(world, 10f, -1f, 30f, 1f));
        wall.add(new WallEntity(world, -1f, 0f, 1f, 30f));
        wall.add(new WallEntity(world, 14f, 0f, 1f, 30f));
        wall.add(new WallEntity(world, 10f, 8f, 30f, 1f));

        light.add(new LightEntity(rocketTexture, this, world, 0f, 8f, "top left"));
        light.add(new LightEntity(rocketTexture, this, world, 13f, 8f, "top right"));
        light.add(new LightEntity(rocketTexture, this, world, 0f, 0f, "bot left"));
        light.add(new LightEntity(rocketTexture, this, world, 13f, 0f, "bot right"));

        rocket.boom(true);
//        stage.addActor(fireball);
//        stage.addActor(fireball2);
        stage.addActor(rocket);
        stage.addActor(earth);
        stage.addActor(mars);

        for (WallEntity aWall : wall) {
            stage.addActor(aWall);
        }
        for (LightEntity aLight : light) {
            stage.addActor(aLight);
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
                for(int i = 0; i<fireballs.size(); i++) {
                    if ((fixtureA.getUserData().equals("fireball"+i) && fixtureB.getUserData().equals("wall"))) {
                        fireballs_del.add(i);
                        System.out.println("fireball"+i);
                    } else if ((fixtureB.getUserData().equals("fireball"+i) && fixtureA.getUserData().equals("wall"))) {
                        fireballs_del.add(i);
                        System.out.println("fireball"+i);
                    } else if ((fixtureA.getUserData().equals("fireball"+i) && fixtureB.getUserData().equals("rocket"))) {
                        rocket.health -= 10;
                        fireballs_del.add(i);
                        System.out.println("fireball"+i);
                    } else if ((fixtureB.getUserData().equals("fireball"+i) && fixtureA.getUserData().equals("rocket"))) {
                        rocket.health -= 10;
                        fireballs_del.add(i);
                        System.out.println("fireball"+i);
                    } else if ((fixtureA.getUserData().equals("fireball"+i) && fixtureB.getUserData().equals("earth"))) {
                        fireballs_del.add(i);
                        System.out.println("fireball"+i);
                    } else if ((fixtureB.getUserData().equals("fireball"+i) && fixtureA.getUserData().equals("earth"))) {
                        fireballs_del.add(i);
                        System.out.println("fireball"+i);
                    } else if ((fixtureA.getUserData().equals("fireball"+i) && fixtureB.getUserData().equals("mars"))) {
                        fireballs_del.add(i);
                        System.out.println("fireball"+i);
                    } else if ((fixtureB.getUserData().equals("fireball"+i) && fixtureA.getUserData().equals("mars"))) {
                        fireballs_del.add(i);
                        System.out.println("fireball"+i);
                    }
                }

                if ((fixtureA.getUserData().equals("light") && fixtureB.getUserData().equals("rocket"))
                        || (fixtureA.getUserData().equals("rocket") && fixtureB.getUserData().equals("light"))) {
                    rocket.health += 10;
                    if (rocket.health > 100) rocket.health = 100;
                }
//                else if ((fixtureA.getUserData().equals("light")) && (fixtureB.getUserData().equals("light"))) {
//                    fixtureA.setUserData("delete");
//                    fixtureB.setUserData("delete");
//                    deleteByUserData();
//                }



            }
            @Override
            public void endContact(Contact contact) {

                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

            }
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
//                Fixture fixtureA = contact.getFixtureA();
//                Fixture fixtureB = contact.getFixtureB();
//                if ((fixtureA.getUserData().equals("light")) && (fixtureB.getUserData().equals("light"))) {
//                    fixtureA.setUserData("delete");
//                    fixtureB.setUserData("delete");
//                    deleteByUserData();
//                }

            }
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

//    public void deleteByUserData() {
//        for (LightEntity aLight : light) {
//            if (aLight.getBody().getUserData().equals("delete")) {
////                aLight.remove();
//                light.remove(aLight);
////                aLight.addAction(Actions.removeActor());
//
//            }
//
//        }
//    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(fireballs_del.size()>0){
            for(int i = 0; i<fireballs_del.size(); i++){
                if(!fireballs.get(fireballs_del.get(i)).isRemoved) {
                    fireballs.get(fireballs_del.get(i)).addAction(Actions.removeActor());
                    fireballs.get(fireballs_del.get(i)).removeFixture();
                    world.destroyBody(fireballs.get(fireballs_del.get(i)).getFixture().getBody());
                }
            }
        }
        timer+=delta;
        if(timer>3){
            Map<String, FireballEntity> fireballNames = new HashMap<String, FireballEntity>();
            fireballNames.put("fireball"+(fireballs.size()-1),new FireballEntity(fireballTexture, this, world, 5.5f, 6f, rocket.getX(), rocket.getY()));
            fireballs.add(fireballNames.get("fireball"+(fireballs.size()-1)));
            fireballs.get(fireballs.size()-1).setUserData(fireballs.size()-1);
            stage.addActor(fireballs.get(fireballs.size()-1));
            timer = 0;
        }
        stage.act();

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, 1280, 720);
        font.getData().setScale(1, 1);
        font.draw(stage.getBatch(), "Resources: " + String.valueOf(amountResources), Gdx.graphics.getWidth()-200,  Gdx.graphics.getHeight()-50);
        font.draw(stage.getBatch(), "Energy: " + String.valueOf(rocket.health), Gdx.graphics.getWidth()-200,  Gdx.graphics.getHeight()-100);
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
      //  fireball.detach();
//        fireball2.detach();

        rocket.remove();
        mars.remove();
        earth.remove();
    //    fireball.remove();
//        fireball2.remove();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        renderer.dispose();
    }

 
}
