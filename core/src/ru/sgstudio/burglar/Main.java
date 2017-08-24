package ru.sgstudio.burglar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class Main extends Game {
	public Screen game;
	@Override
	public void create() {
		game = new MyGame(this);
		this.setScreen(game);
	}
	public void setScr(Screen scr) { this.setScreen(scr);}
}
