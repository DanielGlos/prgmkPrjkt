package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Player extends MapObject{

	//PLAYER ATRIBUTES
	private int health;
	private int maxHealth;
	private boolean dead;
	
	//ANIMACIE
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		8, 12
	};
	
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;
	
	
	public Player(TileMap mapa) {
		super(mapa);
		
		width = 103;
		height = 103;
		cWidth = 100;
		cHeight = 100;
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.15;
		
		facingRight = true;
		
		health = maxHealth = 5;
		
		
		try{
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/hodor.gif"));
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 2; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++) {
					if(i != SCRATCHING) {
						bi[j] = spriteSheet.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					}
					else {
						bi[j] = spriteSheet.getSubimage(
								j * width * 2,
								i * height,
								width * 2,
								height
						);
					}
				}
				sprites.add(bi);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		animation = new Animation();
		currAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	
	public void setMap(TileMap map) {
		this.mapa = map;
	}
	
	private void getNextPosition() {
		
		// movement
		if(left) {
			dY = 0;
			dX -= moveSpeed;
			if(dX < -maxSpeed) {
				dX = -maxSpeed;
			}
		}
		else if(right) {
			dY = 0;
			dX += moveSpeed;
			if(dX > maxSpeed) {
				dX = maxSpeed;
			}
		}else if(down) {
			dX = 0;
			dY += moveSpeed;
			if(dY > maxSpeed) {
				dY = maxSpeed;
			}
		}else if(up) {
			dX = 0;
			dY -= moveSpeed;
			if(dY < -maxSpeed) {
				dY = -maxSpeed;
			}
		}
		else {
			if(dX > 0) {
				dX -= stopSpeed;
				if(dX < 0) {
					dX = 0;
				}
			}
			else if(dX < 0) {
				dX += stopSpeed;
				if(dX > 0) {
					dX = 0;
				}
			}
			else if(dY > 0) {
				dY -= stopSpeed;
				if(dY < 0) {
					dY = 0;
				}
			}
			else if(dY < 0) {
				dY += stopSpeed;
				if(dY > 0) {
					dY = 0;
				}
			}
		}
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xTemp, yTemp);
		

		if(left || right) {
			if(currAction != WALKING) {
				currAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(80);
				width = 103;
			}
		}
		else {
			if(currAction != IDLE) {
				currAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(100);
				width = 103;
			}
		}
		
		animation.update();
		
		// set direction
		if(currAction != SCRATCHING && currAction != FIREBALL) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		if(notOnScreen()) return;
		
		setMapPosition();
		super.draw(g);
		
	}
	
	
}
