package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Velkonost
 */
public class MenuScreen extends BaseScreen implements InputProcessor {
    public MainGame game;
    private SpriteBatch LS;
    BitmapFont font;
    float CAM_W = 1280, CAM_H = 720;
    private Texture losePik;
    private Sprite losePikch;
    ///winPikch 720*480

    private GameScreen mGameScreen;

    private int score;

    public MenuScreen(MainGame game) {
        this.game = game;
    }

    public void show() {
        LS = new SpriteBatch();
        losePik = game.getManager().get("menuStart.png");
        font = new BitmapFont();

        mGameScreen = new GameScreen(game);
        Gdx.input.setInputProcessor(this);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        LS.begin();
        LS.draw(losePik, 0, 0);

        LS.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ENTER) {
            game.setScreen(mGameScreen);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
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
}