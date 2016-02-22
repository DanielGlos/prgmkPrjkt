package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Slugger extends Enemy {
	
	private BufferedImage[] sprites;
	
	public Slugger(TileMap tm) {
		
		super(tm);
		
		moveSpeed = 0.3;
		maxSpeed = 0.3;
		
		width = 30;
		height = 30;
		cWidth = 20;
		cHeight = 20;
		
		health = maxHealth = 2;
		damage = 1;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Enemies/slugger.gif"
				)
			);
			
			sprites = new BufferedImage[3];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(450);
		
		right = true;
		facingRight = true;
		
	}
	
	private void getNextPosition() {
		
		// movement
		if(left) {
			dX -= moveSpeed;
			if(dX < -maxSpeed) {
				dX = -maxSpeed;
			}
		}
		else if(right) {
			dX += moveSpeed;
			if(dX > maxSpeed) {
				dX = maxSpeed;
			}
		}
		
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xTemp, yTemp);
		
		// check flinching
				if(flinching) {
					long elapsed =
						(System.nanoTime() - flinchTimer) / 1000000;
					if(elapsed > 550) {
						flinching = false;
					}
				}
		
		
		// if it hits a wall, go other direction
		if(right && dX == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if(left && dX == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		//if(notOnScreen()) return;
		
		setMapPosition();
		
		super.draw(g);
		
	}
	
}
