package Main;

import java.awt.Dimension;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import GameState.GameStateManager;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{

		//DIMENSIONS
		public static final int WIDTH = 640;
		public static final int HEIGHT = 480;
		public static final int SCALE = 2;
		
		//GAME THREAD
		private Thread thread;
		private boolean running;
		private int FPS = 60;
		private long targetTime = 1000/FPS;
		
		//IMAGE
		private BufferedImage image;
		private Graphics2D g;
		
		//GAME STATE MANAGER
		private GameStateManager gsm;
	
	
		public GamePanel() {
			super();
			setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
			setFocusable(true);
			requestFocus();
		}
		
		public void addNotify() {
			super.addNotify();
			if(thread == null) {
				thread = new Thread(this);
				addKeyListener(this);
				thread.start();
			}
		}
	
	
		@Override
		public void run() {
			init();
			
			long start;
			long elapsed;
			long wait;
			
			
			while(running) {
				start = System.nanoTime();
				
				update();
				draw();
				drawToScreen();
				
				elapsed = System.nanoTime() - start;
				
				wait = targetTime - elapsed / 1000000;
				if(wait < 0) wait = 5;
				try{
					Thread.sleep(wait);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	
	

		private void draw() {
			gsm.draw(g);
		}
		
		private void drawToScreen() {
			Graphics g2 = getGraphics();
			g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
			g2.dispose();
		}

		private void update() {
			gsm.update();
		}

		public void init() {
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
			g = (Graphics2D)image.getGraphics();
			
			running = true;
			
			gsm = new GameStateManager();
		}
	
	@Override
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
