package ru.sgstudio.burglar;

import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.sgstudio.burglar.utils.KeyManager;
import ru.sgstudio.burglar.utils.Tiles;

public class MyGame implements Screen {
	Map<String, TextureRegion> swithes;
	
	private static float time = 0;
	private static float t=0;
	private static float r=0;
	private static int m=1, s=30;
	private static long startTime;
	
	private static int[] code= {0, 0, 0};
	
	private static float clicked[]= {
			4, 8, 11, 15, 19, 22, 26, 29, 33, 36,
			40, 44, 47, 51, 55, 58, 62, 65, 69, 73,
			76, 80, 84, 87, 91, 95, 98, 102, 105, 109,
			113, 116, 120, 123, 127, 131, 134, 138, 141, 145,
			148, 152, 156, 159, 163, 167, 170, 174, 177, 181,
			185, 188, 192, 195, 199, 202, 206, 210, 214, 217,
			220, 224, 228, 231, 235, 238, 242, 245, 249, 253,
			256, 260, 263, 267, 270, 274, 278, 281, 285, 288,
			292, 296, 299, 303, 306, 310, 314, 317, 321, 324,
			382, 331, 335, 339, 342, 346, 350, 353, 357, 0};
	
	Sound open;
	Sound click;
	
	SpriteBatch batch;
	Texture img;
	Sprite lock;
	KeyManager key;
	BitmapFont font;
	Tiles tiles;
	Main main;
	
	public MyGame(Main main) { this.main=main; }

	public void create () {
		Random rnd = new Random(System.currentTimeMillis());
		for(int I=0;I<3;I++) {
			int number = 0 + rnd.nextInt(99 - 0 + 1);
			code[I] = number;
//			if(I==0 || I==2) System.out.print(code[I]+" ");
//			else System.out.print(-(code[I])+" ");
		}

		startTime = System.currentTimeMillis();
		
		font = new BitmapFont();
		batch = new SpriteBatch();
		key = new KeyManager();
		tiles = new Tiles();
		
		tiles.createAtlas("lock1.png", 1, 2);
		swithes = tiles.getTextureRegion();
		img = new Texture("bg.png");
		lock = new Sprite(new Texture("lock.png"));
		open = Gdx.audio.newSound(Gdx.files.internal("songs/open.mp3"));
		click = Gdx.audio.newSound(Gdx.files.internal("songs/click.mp3"));
	}
	
	boolean play=true, victory=false;
	
	private void renderPlay() {
		if(time!=(System.currentTimeMillis() - startTime) / 50){
			pressed();
			time++;
			if(t==20) {
				timer();
			}
			else t++;
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		lock.setPosition(213, 80);
		toggleSwitches();
		font.draw(batch, "Time left: "+m+":"+s, 5, Gdx.graphics.getHeight()-10);
		open();
		if(lock.getRotation()==360 || lock.getRotation()==-360) lock.setRotation(0);
//		font.draw(batch, "deg: "+lock.getRotation() + " rCode: "+rCode, 5, Gdx.graphics.getHeight()-25);
		lock.draw(batch);
		batch.end();
	}
	
	private void renderVictory() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.setColor(Color.GREEN);
		font.draw(batch, "YOU WIN!", 50, Gdx.graphics.getHeight()/2-5);
		batch.end();
	}
	
	private void renderDefeat() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.setColor(Color.RED);
		font.draw(batch, "YOU LOST!", 50, Gdx.graphics.getHeight()/2-5);
		batch.end();
	}
	
	@Override
	public void render (float delta) {
		if(play) {
			renderPlay();
		} else {
			if(victory) {
				renderVictory();
			} else {
				renderDefeat();
			}
			if(key.getPressedRestart()) main.setScr(main.game);
		}
	}

	private void toggleSwitches() {
		for(int I=0;I<code.length;I++) {
			if(I==0 && i>I) batch.draw(swithes.get("tiles0_1"), 70, Gdx.graphics.getHeight()-I*100-150);
			else if(I==1 && i>I) batch.draw(swithes.get("tiles0_1"), 70, Gdx.graphics.getHeight()-I*100-150);
			else if(I==2 && i>I) batch.draw(swithes.get("tiles0_1"), 70, Gdx.graphics.getHeight()-I*100-150);
			else batch.draw(swithes.get("tiles0_0"), 70, Gdx.graphics.getHeight()-I*100-150);
		}
	}
	
	private void timer() {
		s--;
		if(s<=0) {
			if(m>0) {
				m=0;
				s+=59;
			}
			else s=0;
		}
		t=0;
		if(m<=0 && s<=0) {
			play=false;
			if(oneL && twoL && threeL) victory=true;
			else victory=false;
		}
	}
	
	int i=0;
	float rCode=0;
	boolean oneL=false, twoL=false, threeL=false;
	boolean one=false, two=false, three=false;
	boolean clickedB=true;
	private void open() {
		if(i==0 && lock.getRotation()==clicked[code[0]] && !oneL && rCode==0) {
			one=true;
			oneL=true;
			i++;
			rCode=r;
		}
		if(rCode>r) {
			if(!oneL && rCode>0) rCode=r;
			if(oneL && twoL) {
				i=0;
				oneL=false;
				twoL=false;
			}
			if(i==1 && lock.getRotation()==-(clicked[code[1]]) && !twoL && rCode==clicked[code[0]]) {
				two=true;
				twoL=true;
				i++;
				rCode=r;
			}
		}
		if(rCode<r) {
			if(!oneL && rCode<0) rCode=r;
			if(oneL && !twoL) {
				i=0;
				oneL=false;
			}
			if(oneL && twoL && threeL) {
				i=0;
				oneL=false;
				twoL=false;
				threeL=false;
			}
			if(i==2 && lock.getRotation()==clicked[code[2]] && !threeL && rCode==-(clicked[code[1]])) {
				three=true;
				threeL=true;
				i++;
				rCode=r;
			}
		}
		
		for(int I=0;I<clicked.length;I++) {
			if(clicked[I]==rCode || -(clicked[I])==rCode) {
				if(one) {
					open.play();
					one=false;
				}
				if(two) {
					open.play();
					two=false;
				}
				if(three) {
					open.play();
					three=false;
				}
			} else if(clicked[I]==r || -(clicked[I])==r) {
				if(clickedB) click.play();
				clickedB=false;
			}
		}
		
//		font.draw(batch, "I: "+i, 5, Gdx.graphics.getHeight()-40);
//		if(rCode>r)System.out.println("--");
//		if(rCode<r) System.out.println("++");
	}
	
	private void pressed() {
		if(key.getPressedLeft()) {
			lock.rotate(-1);
			clickedB=true;
		}
		if(key.getPressedRight()) {
			lock.rotate(1);
			clickedB=true;
		}
		r=lock.getRotation();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		lock.getTexture().dispose();
		time=0;
		t=0;
		r=0;
		m=1; s=30;
		play=true; victory=false;
	}

	@Override
	public void show() {
		this.create();
	}

	@Override
	public void hide() {
		this.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}