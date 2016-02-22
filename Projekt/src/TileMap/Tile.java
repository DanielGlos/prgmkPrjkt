package TileMap;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage tileImage;
	private Dlazdice dlazdica;
	
	//TYPY DLAZDIC
	public enum Dlazdice {
		NORMAL,
		BLOCKED
	}
	
	public Tile(BufferedImage tileImage, Dlazdice dlazdica) {
		this.tileImage = tileImage;
		this.dlazdica = dlazdica;
	}
	
	public BufferedImage getTileImage() { return tileImage; }
	public Dlazdice getDlazdica() { return dlazdica; }
	
}
