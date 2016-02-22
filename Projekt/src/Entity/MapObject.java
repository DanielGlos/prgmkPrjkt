package Entity;

import Main.GamePanel;
import TileMap.Tile.*;
import TileMap.TileMap;
import java.awt.Rectangle;

public class MapObject {
	
	//TILE INFO
	
	protected TileMap mapa;
	protected int tileSize;
	protected double xMap;
	protected double yMap;
	
	//POZICIA A VEKTOR
	protected double posX;
	protected double posY;
	protected double dX;
	protected double dY;
	
	//ROZMERY;
	protected int width;
	protected int height;
	
	//ROZMERY COLLISION BOXU
	protected int cWidth;
	protected int cHeight;
	
	//PREMENNE PRE VYPOCET KOLIZII
	protected int currRow;
	protected int currCol;
	protected double xDest;
	protected double yDest;
	protected double xTemp;
	protected double yTemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	//ANIMACIA
	protected Animation animation;
	protected int currAction;
	protected int prevAction;
	protected boolean facingRight;
	
	//POHYB
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	
	//POHYBOVE ATRIBUTY
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	
	
	public MapObject(TileMap mapa) {
		this.mapa = mapa;
		tileSize = this.mapa.getTileSize();
	}
	
	//KOLIZIA S INYMI MAP OBJEKTAMI
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int) posX - cWidth, (int) posY - cHeight, cWidth, cHeight);
	}
	//ZISTUJE CI SA MAP OBJECT NEDOTYKA BLOCKED DLAZDICE
	public void calculateCorners(double x, double y) {
		int leftTile = (int) (posX - cWidth / 2) / tileSize;
		int rightTile = (int) (posX + cWidth / 2 - 1) / tileSize;
		int topTile = (int) (posY - cHeight / 2) / tileSize;
		int bottomTile = (int) (posY + cHeight / 2 - 1) / tileSize;
		
		
		int tl = mapa.getDlazdica(topTile, leftTile).ordinal();
		int tr = mapa.getDlazdica(topTile, rightTile).ordinal();
		int bl = mapa.getDlazdica(bottomTile, leftTile).ordinal();
		int br = mapa.getDlazdica(bottomTile, rightTile).ordinal();
		
		topLeft = tl == Dlazdice.BLOCKED.ordinal();
		topRight = tr == Dlazdice.BLOCKED.ordinal();
		bottomLeft= bl == Dlazdice.BLOCKED.ordinal();
		bottomRight = br == Dlazdice.BLOCKED.ordinal();
	}
	
	public void checkTileMapCollision() {
		currCol = (int) posX / tileSize;
		currRow = (int) posY / tileSize;
		
		xDest = posX + dX;
		yDest = posY + dY;
		
		/**
		 * Chybali tu tieto dva riadky sice sme nastavili posX aj posY ale v update sa vzdy zavola setPosition s parametrami
		 * xTemp a yTemp a tie neboli inicializovane
		 */
		xTemp = posX;
		yTemp = posY;
		
		calculateCorners(posX, yDest);
		if(dY < 0) {
			if(topLeft || topRight) {
				dY = 0;
				yTemp = currRow * tileSize + cHeight / 2;
			}else {
				yTemp += dY;
			}
		}
		if(dY > 0) {
			if(bottomLeft || bottomRight) {
				dY = 0;
				yTemp = (currRow +1) * tileSize - cHeight / 2;
			}else {
				yTemp += dY;
			}
		}
		
		calculateCorners(xDest, posY);
		if(dX < 0) {
			if(topLeft || bottomLeft) {
				dX = 0;
				xTemp = currCol * tileSize + cWidth / 2; 
			}else {
				xTemp += dX;
			}
		}
		if(dX > 0) {
			if(topRight || bottomRight) {
				dX = 0;
				xTemp = (currCol +1) * tileSize - cWidth /2;
			}else {
				xTemp += dX;
			}
		}
	}
	
	public int getPosX() { return (int)posX; }
	public int getPosY() { return (int)posY; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cWidth; }
	public int getCHeight() { return cHeight; }
	
	public void setPosition(double x, double y) {
		posX = x;
		posY = y;
	}
	
	public void setVector(double dx, double dy) {
		dX = dx;
		dY = dy;
	}
	
	public void setMapPosition() {
		xMap = mapa.getPosX();
		yMap = mapa.getposY();
	}
	
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	
	public boolean notOnScreen() {
		return posX + xMap + width < 0 ||
				posX + xMap - width > GamePanel.WIDTH ||
				posY + yMap + height < 0 ||
				posY + yMap - height > GamePanel.HEIGHT;
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(facingRight) {
			g.drawImage(animation.getImage(), (int) (posX + xMap - width /2), (int) (posY + yMap - height / 2), null);
		}else {
			g.drawImage(animation.getImage(), (int) (posX + xMap - width /2 + width), (int) (posY + yMap - height / 2), - width, height, null);
		}
	}
	
}
