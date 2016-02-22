package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import Main.GamePanel;
import TileMap.Tile.Dlazdice;

public class TileMap {
	
	//AKTUALNA POZICIA
	private double posX;
	private double posY;
	
	//HRANICE MAPY
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	
	private double tween;
	
	//MAPA
	private int[][] mapa;
	private int tileSize;
	private int numRowsMapa;
	private int numColsMapa;
	private int widthMapa;
	private int heightMapa;
	
	//TILE SET
	private BufferedImage tileSet;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	//VYKRESLOVANIE (vykreslujeme len dlazdice v okne nie celu mapu)
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.WIDTH / tileSize + 4;
		numColsToDraw = GamePanel.HEIGHT / tileSize + 4;
		tween = 0.07;
	}
	
	public void loadTiles(String adresa) {
		
		try{
			tileSet = ImageIO.read(getClass().getResourceAsStream(adresa));
			numTilesAcross = tileSet.getWidth() / tileSize;
			//2 je podla poctu riadkov dlazdic v zdrojovom obrazku
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subImage;
			for(int col = 0; col < numTilesAcross; col++) {
				subImage = tileSet.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subImage, Dlazdice.NORMAL);
				subImage = tileSet.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subImage, Dlazdice.BLOCKED);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String adresa) {
		
		try{
			InputStream input = getClass().getResourceAsStream(adresa);
			BufferedReader reader = new BufferedReader( new InputStreamReader(input));
			numColsMapa = Integer.parseInt(reader.readLine());
			numRowsMapa = Integer.parseInt(reader.readLine());
			mapa = new int[numRowsMapa][numColsMapa];
			widthMapa = numColsMapa * tileSize;
			heightMapa = numRowsMapa * tileSize;
			
			xMax = 0;
			xMin = GamePanel.WIDTH - widthMapa;
			yMax = 0;
			yMin = GamePanel.HEIGHT - heightMapa;
			
			
			//Mapa je ulozena ako textovy subor obsahujuci cisla dlazdic
			//oddelene medzerou, treba ju rozparsovat
			//Nezabudnut ze prve dva riadky sme uz precitali
			String medzera = " ";
			for(int row = 0; row < numRowsMapa; row ++) {
				String riadokMapy = reader.readLine();
				String[] cislaDlazdic = riadokMapy.split(medzera);
				for(int col = 0; col < numColsMapa; col++) {
					mapa[row][col] = Integer.parseInt(cislaDlazdic[col]);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTileSize() { return tileSize; }
	public int getPosX() { return (int)posX; }
	public int getposY() { return (int)posY; }
	public int getWidthMapa() { return widthMapa; }
	public int getHeightMapa() { return heightMapa; }
	
	
	public Dlazdice getDlazdica(int row, int col) {
		int cisloDlazdice = mapa[row][col];
		int r = cisloDlazdice / numTilesAcross;
		int c = cisloDlazdice % numTilesAcross;
		return tiles[r][c].getDlazdica();
	}
	
	public void setTween(double t) { tween = t; }
	
	public void setPosition(double x, double y) {
		posX += (x - posX) * tween;
		posY += (y - posY) * tween;
		
		skontrolujHranice();
		
		colOffset = (int) - posX / tileSize;
		rowOffset = (int) - posY / tileSize;
	}
	
	private void skontrolujHranice() {
		if(posX < xMin) posX = xMin;
		if(posX > xMax) posX = xMax;
		if(posY < yMin) posY = yMin;
		if(posY > yMax) posY = yMax;
	}
	
	public void draw(Graphics2D g) {
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			if(row >= numRowsMapa) break;
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				if(col >= numColsMapa) break;
				if(mapa[row][col] == 0) continue;
				
				int cisloDlazdice = mapa[row][col];
				int r = cisloDlazdice / numTilesAcross;
				int c = cisloDlazdice % numTilesAcross;
				
				
				g.drawImage(tiles[r][c].getTileImage(),
									(int) posX + col * tileSize,
									(int) posY + row * tileSize,
									 null);
			}
		}
	}
	

}
