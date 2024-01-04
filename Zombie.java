package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;
	
	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	public void onCharacterDeath() {
			int x = this.getLocation().x;
			int y = this.getLocation().y;
			Game.zombies.remove(this);
	        ((CharacterCell)Game.map[x][y]).setCharacter(null);
			ZOMBIES_COUNT--;
	        Game.spawnZombies(1);
	}
	public void attack() throws InvalidTargetException , NotEnoughActionsException {
		if (this.getTarget() != null) {
			this.getTarget().setCurrentHp(getTarget().getCurrentHp() - this.getAttackDmg());
			this.getTarget().defend(this);
			if(this.getTarget().getCurrentHp() <=0) {
				this.getTarget().onCharacterDeath();
				this.setTarget(null);
			}
		}
		else {
			ArrayList<Point> adjacent= getAdjCharacter(this.getLocation());
			for(int i = 0; i < adjacent.size();i++) {
				setTarget(((CharacterCell)Game.map[adjacent.get(i).x][adjacent.get(i).y]).getCharacter());		
				super.attack();
				break;
				}

		}
	}
	
	public ArrayList<Point> getAdjCharacter(Point p) {
		int x=p.x;
		int y=p.y;
	    ArrayList<Point> adjacentPoint = new ArrayList<>();
	    for (int i = x-1; i <=x+1; i++) {
	        for (int j = x-1; j <= y+1; j++) {
	        	if(isAdjacent(new Point(this.getLocation()),new Point(i,j)) && isInBound(new Point(i,j)))
	        		if (Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter() instanceof Hero  && !p.equals(new Point(i,j))) {
	        			adjacentPoint.add(new Point(i, j));
	        			
	        		}
	        }
	}
    	return adjacentPoint;

	        }
	public boolean isInBound(Point p) {
	    double x = p.getX();
	    double y = p.getY();
	    return x >= 0 && x < 15 && y >= 0 && y < 15;
	}
}