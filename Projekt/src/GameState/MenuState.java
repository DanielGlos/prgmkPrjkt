package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import GameState.GameStateManager;

import TileMap.Background;

public class MenuState extends GameState{
	
	private Background bg;
	
	private int currentChoice = 0;
	private String options[] = {
			"Start",
			"Options",
			"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		
		try{
			
			bg = new Background("/Backgrounds/download.png", 1);
			bg.setVector(-0.5, 0);
			
			titleColor = new Color(20,143,230);
			titleFont = new Font("Century Gothic", Font.PLAIN, 32);
			
			font = new Font("Arial", Font.PLAIN, 25);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		bg.updateBackground();
	}

	@Override
	public void draw(Graphics2D g) {
		bg.drawBackground(g);
		
		//nadpis
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("PRGMK PRJKT", 210, 120);
		
		//menu
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.black);
			}else {
				g.setColor(Color.red);
			}
			g.drawString(options[i], 280, 200 + i * 50);
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.State.MapTestState);
		}else if(currentChoice == 1) {
			//options
		}else if(currentChoice == 2) {
			System.exit(0);
		}
	}

	@Override
	public void keyPressed(int k) {
		switch(k) {
		case KeyEvent.VK_ENTER:
			select();
			break;
		case KeyEvent.VK_DOWN:
			currentChoice++;;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
			break;
		case KeyEvent.VK_UP:
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
					
		}
		
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

}
