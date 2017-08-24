package ru.sgstudio.burglar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.sgstudio.burglar.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = 800;
		config.height = 600;
		config.title = "The Bulgar";
		new LwjglApplication(new Main(), config);
	}
}
