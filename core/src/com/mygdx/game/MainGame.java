package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Velkonost
 */
public class MainGame extends Game {


    private AssetManager manager;

    public AssetManager getManager() {
        return manager;
    }

    @Override
    public void create () {
        manager = new AssetManager();
        manager.load("badlogic.jpg", Texture.class);
        manager.load("player_main.png", Texture.class);
        manager.load("earth.png", Texture.class);
        manager.load("mars.png", Texture.class);
        manager.load("background.png", Texture.class);
        manager.load("fireball.png", Texture.class);
        manager.load("light.png", Texture.class);
        manager.load("accepthelp.png", Texture.class);

        manager.finishLoading();

        setScreen(new GameScreen(this));
    }

}

