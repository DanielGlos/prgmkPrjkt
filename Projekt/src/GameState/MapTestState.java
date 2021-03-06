package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Entity.Enemy;
import Entity.Player;
import Entity.Enemies.Slugger;
import Entity.Interact.Dvere;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class MapTestState extends GameState{
	
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private Enemy slugger;
	private Dvere kanal;
	
	private double savedPosX;
	private double savedPosY;
	
	public MapTestState(GameStateManager gsm){
		this.gsm = gsm;
		init();
		
	}
	
	
	@Override
	public void init() {

		tileMap = new TileMap(60);
		tileMap.loadTiles("/Tilesets/Tilesets.gif");
		tileMap.loadMap("/Maps/noveTile.map");
		
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/test.png", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(200, 200);
		
		
		kanal = new Dvere(tileMap);
		kanal.setPosition(600, 420);
		
		//populateNPCs();
		
	}
	
	private void populateNPCs() {
		//hodor = new Hodor(tileMap);
		slugger = new Slugger(tileMap);
		slugger.setPosition(100, 100);
	}
	
	public void returned() {
		player.setMap(tileMap);
		player.setPosition(savedPosX, savedPosY);
		player.setMapPosition();
	}

	@Override
	public void update() {
		player.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getPosX(),GamePanel.HEIGHT / 2 - player.getPosY());
		
		//bg.setBackgroundPosition(tileMap.getPosX(), tileMap.getposY());
		
		//slugger.update();
		
		if(player.intersects(kanal)) {
			savedPosX = kanal.getPosX() + 100;
			savedPosY = kanal.getPosY();
			gsm.setState(GameStateManager.State.KanalState);
			gsm.getCurrentState().init();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.drawBackground(g);
		tileMap.draw(g);
		player.draw(g);
		
		//slugger.draw(g);
		
	}


	public Player getPlayer() {
		return player;
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
