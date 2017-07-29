package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Velkonost
 */
public class WinScreen extends BaseScreen {
    public MainGame game;
    private SpriteBatch LS;
    BitmapFont font;
    float CAM_W = 1280, CAM_H = 720;
    private Texture losePik;
    private Sprite losePikch;
    ///winPikch 720*480

    public WinScreen(MainGame game) {
        super();
        this.game = game;
    }

    public void show() {
        LS = new SpriteBatch();
        losePik = game.getManager().get("lose.jpg");
        font = new BitmapFont();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        LS.begin();
        LS.draw(losePik, 0, 0);
        font.getData().setScale(3, 3);
        font.draw(LS, "LOSE :-(", Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 50);

        LS.end();
    }
}