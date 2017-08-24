package ru.sgstudio.burglar.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Used to cut pictures
 * <p>Methods: <b>createAtlas</b></p>
 * @author Yarik
 * @version 1.0
 */

public class Tiles {
	
	
	private static Texture texture = null;
	private static int Lines = 0;
	private static int Columns = 0;
	
	private static Map<String, TextureRegion> textureRegions = null;
	
	/**
	 * Allows you to create an atlas(textureRegions)
	 * @param tiles - Path to the picture
	 * @param lines - Number of lines
	 * @param columns - Number of columns
	 */
	
	public void createAtlas(String tiles, int lines, int columns){
		texture = new Texture(tiles);
		textureRegions = null;
		textureRegions = new HashMap<String, TextureRegion>();
		Lines = lines;
		Columns = columns;
		createAtlas();
	}
	
	private void createAtlas(){
		TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth()/Columns, texture.getHeight()/Lines);
		for(int y=0;y<Lines;y++) for(int x=0;x<Columns;x++) textureRegions.put("tiles"+y+"_"+x, tmp[y][x]);
		Lines=0; Columns=0; texture=null;
	}
	
	/**
	 * Returns the cut atlas
	 * @return textureRegions
	 */
	
	public Map<String, TextureRegion> getTextureRegion(){ return textureRegions; }
}
