package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Entity.Enemy;
import Entity.Player;
import Entity.Enemies.Slugger;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class MapTestState extends GameState{
	
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private Enemy slugger;
	
	public MapTestState(GameStateManager gsm){
		this.gsm = gsm;
		init();
		
	}
	
	
	@Override
	public void init() {

		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/testMap.map");
		
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/test.png", 0.1);
		
		player = new Player(tileMap);
		player.setPosition((GamePanel.WIDTH * GamePanel.SCALE) / 2, (GamePanel.HEIGHT * GamePanel.SCALE) / 2);
		
		populateNPCs();
		
	}
	
	private void populateNPCs() {
		//hodor = new Hodor(tileMap);
		slugger = new Slugger(tileMap);
		slugger.setPosition(100, 100);
	}
	
	

	@Override
	public void update() {
		player.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getPosX(),GamePanel.HEIGHT / 2 - player.getPosY());
		
		bg.setBackgroundPosition(tileMap.getPosX(), tileMap.getposY());
		
		slugger.update();
	}

	@Override
	public void draw(Graphics2D g) {
		bg.drawBackground(g);
		tileMap.draw(g);
		player.draw(g);
		
		slugger.draw(g);
		
	}


	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
	}


	@Override
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
	}

}
