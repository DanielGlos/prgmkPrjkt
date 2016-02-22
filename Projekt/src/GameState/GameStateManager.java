package GameState;

import java.util.ArrayList;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private State currentState;
	
	public enum State{
		MenuState,
		MapTestState
	}
	
	public GameStateManager() {
		
		gameStates = new ArrayList<GameState>();
		
		currentState = State.MenuState;
		gameStates.add(new MenuState(this));
		gameStates.add(new MapTestState(this));
	}
	
	public void setState(State s) {
		currentState = s;
		gameStates.get(currentState.ordinal());
	}
	
	public void update() {
		gameStates.get(currentState.ordinal()).update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState.ordinal()).draw(g);
	}
	
	public void keyPressed(int k) {
		gameStates.get(currentState.ordinal()).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState.ordinal()).keyReleased(k);
	}
	
}
