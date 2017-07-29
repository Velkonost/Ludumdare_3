package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

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


        manager.finishLoading();

//        setScreen(new MenuScreen(this));
    }

}

