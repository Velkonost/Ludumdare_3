package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
<<<<<<< HEAD
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		new LwjglApplication(new MyGdxGame(), config);
=======
		new LwjglApplication(new MainGame(), config);

		config.width = 1280;
		config.height = 720;
>>>>>>> ec9b036ffcee83b5c64ea9ce8adfe1d1e27d1886
	}
}
