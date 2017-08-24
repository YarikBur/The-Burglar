package ru.sgstudio.burglar.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

/**
 * Click Handler
 * @author Yarik
 * @version 1.0
 */

public class KeyManager {
	/** @return boolean */
	public boolean getPressedLeft(){
		if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) return true;
		else return false;
	}
	/** @return boolean */
	public boolean getPressedRight(){
		if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) return true;
		else return false;
	}
	/** @return boolean */
	public boolean getPressedRestart(){
		if(Gdx.input.isKeyPressed(Keys.R)) return true;
		else return false;
	}
}
