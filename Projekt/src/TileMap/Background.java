package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Background {
	
	private BufferedImage backgroundImage;
	private double bgPosX;
	private double bgPosY;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	
	public Background(String adresa, double moveScale) {
		try{
			backgroundImage = ImageIO.read(getClass().getResourceAsStream(adresa));
			this.moveScale = moveScale;
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setBackgroundPosition(double x, double y) {
		bgPosX = (x * moveScale) % GamePanel.WIDTH;
		bgPosY = (y * moveScale) % GamePanel.HEIGHT;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	
	public void updateBackground() {
		bgPosX += dx;
		bgPosY += dy;
	}
	
	public void drawBackground(Graphics2D g) {
		if(bgPosX < -GamePanel.WIDTH) {
			bgPosX = 0;
		}
		g.drawImage(backgroundImage, (int) bgPosX, (int) bgPosY,null);
		g.drawImage(backgroundImage, (int) bgPosX + GamePanel.WIDTH, (int) bgPosY,null);
	}
	
}
