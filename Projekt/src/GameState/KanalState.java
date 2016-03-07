package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Entity.Player;
import Entity.Interact.Dvere;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class KanalState extends GameState{

	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private Dvere back;
	
	public KanalState(GameStateManager gsm, GameState parentState) {
		this.gsm = gsm;
		this.player = ((MapTestState)parentState).getPlayer();
		
	}
	
	
	@Override
	public void init() {
		tileMap = new TileMap(60);
		tileMap.loadTiles("/Tilesets/Tilesets.gif");
		tileMap.loadMap("/Maps/kanalMap.map");
		
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/test.png", 0.1);
		
		
		player.setMap(tileMap);
		player.setMapPosition();
		player.setPosition(100, 300);
		
		back = new Dvere(tileMap);
		back.setPosition(19 * tileMap.getTileSize() , tileMap.getTileSize() * 4);
		
	}

	@Override
	public void update() {
		player.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getPosX(),GamePanel.HEIGHT / 2 - player.getPosY());
		
		
		if(back.intersects(player)) {
			gsm.setState(GameStateManager.State.MapTestState);
			((MapTestState)gsm.getCurrentState()).returned();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.drawBackground(g);
		tileMap.draw(g);
		player.draw(g);
		
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
