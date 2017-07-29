package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameScreen;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.Constants.PIXELS_IN_METER;

/**
 * @author Velkonost
 */
public class RocketEntity extends Actor implements InputProcessor {


    //направление движения
    public enum KeysProger {
        LEFT, RIGHT, UP, DOWN
    }

    private Map<KeysProger, Boolean> keys = new HashMap<KeysProger, Boolean>();

    {
        keys.put(KeysProger.LEFT, false);
        keys.put(KeysProger.RIGHT, false);
        keys.put(KeysProger.UP, false);
        keys.put(KeysProger.DOWN, false);

    }

    Vector2 previousPosition;
    private Texture texture;

    public static final float SPEED_ROCKET = 2f;

    private World world;

    private GameScreen game;

    private Body body;

    private Fixture fixture;//

    private boolean isMove = false;


    private float startAngle = 0f, finishAngle = 0f;

    public RocketEntity(Texture texture, GameScreen game, World world, float x, float y) {
        this.texture = texture;

        this.world = world;
        this.game = game;
        previousPosition = new Vector2(getX(), getY());
        setPosition(x, y);

        BodyDef def = new BodyDef();
        def.position.set(x, y);
        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);
        body.setFixedRotation(true);

        final PolygonShape box = new PolygonShape();
        box.setAsBox(0.25f, 0.5f);

        fixture = body.createFixture(box, 0);
        fixture.setUserData("rocket");

        body.setFixedRotation(false);

        box.dispose();

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
        setPosition((body.getPosition().x) * PIXELS_IN_METER,
                (body.getPosition().y) * PIXELS_IN_METER);

        if ((finishAngle > startAngle) && isMove) {
            batch.draw(texture, getX(), getY(), (getWidth()) / 2, (getHeight()) / 2, getWidth(), getHeight(), 1,
                    1, startAngle, 1, 1, 900, 900, false, false);
            startAngle += 2f;
        } else if ((startAngle > finishAngle) && isMove) {
            batch.draw(texture, getX(), getY(), (getWidth()) / 2, (getHeight()) / 2, getWidth(), getHeight(), 1,
                    1, startAngle, 1, 1, 900, 900, false, false);
            startAngle -= 2f;

        } else {
            batch.draw(texture, getX(), getY(), (getWidth()) / 2, (getHeight()) / 2, getWidth(), getHeight(), 1,
                    1, startAngle, 1, 1, 900, 900, false, false);
        }
    }

    public boolean hasMoved(){
        if(previousPosition.x != getX() || previousPosition.y != getY()){
            previousPosition.x = getX();
            previousPosition.y = getY();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.D) {
            rightPressed();
        } else if (keycode == Input.Keys.A) {
            leftPressed();
        } else if (keycode == Input.Keys.W) {
            upPressed();
        } else if (keycode == Input.Keys.S) {
            downPressed();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.D) {
            rightReleased();
        } else if (keycode == Input.Keys.A) {
            leftReleased();
        } else if (keycode == Input.Keys.W) {
            upReleased();
        } else if (keycode == Input.Keys.S) {
            downReleased();
        }
        return true;
    }

    public void boom(boolean boom){
        if(boom) {
            Gdx.input.setInputProcessor(this);
        }else{
            Gdx.input.setInputProcessor(null);
        }
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    //флаг устанавливаем, что движемся влево
    private void leftPressed() {
        keys.get(keys.put(KeysProger.LEFT, true));
    }

    //флаг устанавливаем, что движемся вправо
    private void rightPressed() {
        keys.get(keys.put(KeysProger.RIGHT, true));
    }

    //флаг устанавливаем, что движемся вверх
    private void upPressed() {
        keys.get(keys.put(KeysProger.UP, true));
    }

    //флаг устанавливаем, что движемся вниз
    private void downPressed() {
        keys.get(keys.put(KeysProger.DOWN, true));
    }

    //освобождаем флаги
    private void leftReleased() {
        keys.get(keys.put(KeysProger.LEFT, false));
    }

    private void rightReleased() {
        keys.get(keys.put(KeysProger.RIGHT, false));
    }

    private void upReleased() {
        keys.get(keys.put(KeysProger.UP, false));
    }

    private void downReleased() {
        keys.get(keys.put(KeysProger.DOWN, false));
    }

    public void resetWay(){
        rightReleased();
        leftReleased();
        downReleased();
        upReleased();
    }

    //в зависимости от выбранного направления движения выставляем новое направление движения для персонажа
    public void processInput() {

        if (keys.get(KeysProger.LEFT)) {
            body.setLinearVelocity(-SPEED_ROCKET, body.getLinearVelocity().y);
            finishAngle = 90;
            isMove = true;
        }
        if (keys.get(KeysProger.RIGHT)) {
            body.setLinearVelocity(SPEED_ROCKET, body.getLinearVelocity().y);
            finishAngle = -90;
            isMove = true;
        }
        if (keys.get(KeysProger.UP)) {
            body.setLinearVelocity(body.getLinearVelocity().x, SPEED_ROCKET);
            finishAngle = 0;
            isMove = true;
        }
        if (keys.get(KeysProger.DOWN)) {
            body.setLinearVelocity(body.getLinearVelocity().x, -SPEED_ROCKET);
            finishAngle = startAngle;
            isMove = true;
        }

        //если не выбрано направление, то стоим на месте
        if ((keys.get(KeysProger.LEFT) && keys.get(KeysProger.RIGHT)) || (!keys.get(KeysProger.LEFT) && (!keys.get(KeysProger.RIGHT)))) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);

        }
        if ((keys.get(KeysProger.UP) && keys.get(KeysProger.DOWN)) || (!keys.get(KeysProger.UP) && (!keys.get(KeysProger.DOWN)))) {
            body.setLinearVelocity(body.getLinearVelocity().x, 0);

        }

        if (!keys.get(KeysProger.LEFT) && (!keys.get(KeysProger.RIGHT)) && (!keys.get(KeysProger.UP) && (!keys.get(KeysProger.DOWN)))) {
            isMove = false;
        }
    }
}
