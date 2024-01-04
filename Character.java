package model.characters;
import model.characters.*;
import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;
import model.world.CharacterCell;


public abstract class Character {
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;

	
	public Character() {
	}
	

	public Character(String name, int maxHp, int attackDmg) {
		this.name=name;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
		this.attackDmg = attackDmg;
		
	}
		
	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}
	
	public String getName() {
		return name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if(currentHp < 0) 
			this.currentHp = 0;
		else if(currentHp > maxHp) 
			this.currentHp = maxHp;
		else 
			this.currentHp = currentHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}
	
	public void attack() throws InvalidTargetException , NotEnoughActionsException {	
		if(getTarget() == null) 
			throw new InvalidTargetException();
		if(!(isAdjacent(this.location, target.location)))
			throw new InvalidTargetException();
		if(this instanceof Hero && target instanceof Hero)
			throw new InvalidTargetException();
		if(this instanceof Zombie && target instanceof Zombie)
			throw new InvalidTargetException();
		
		target.currentHp = target.currentHp - this.attackDmg;
		target.defend(this);
		if(target.currentHp <=0) {
			target.onCharacterDeath();
			this.setTarget(null);
		}
		if(this.currentHp <=0)
			this.onCharacterDeath();
	
	}
	
	public void defend(Character c) {
		c.currentHp=c.currentHp-(this.getAttackDmg()/2);
		if (c.currentHp <= 0) {
			c.onCharacterDeath();
		}
		
	}
	
	public void onCharacterDeath() {
			}
	
	
	public boolean isAdjacent(Point p1, Point p2) {
	    int xDiff = Math.abs(p1.x - p2.x);
	    int yDiff = Math.abs(p1.y - p2.y);
	    int maxDiff = Math.max(xDiff, yDiff);
	    return maxDiff <= 1;
	}


//	public ArrayList<Point> getAdjCharacter() {
//		int x=location.x;
//		int y=location.y;
//	    ArrayList<Point> adjacentPoint = new ArrayList<>();
//	    for (int i = x-1; i <=x+1; i++) {
//	        for (int j = x-1; j <= y+1; j++) {
//	        	if(isAdjacent(new Point(this.getLocation()),new Point(i,j)) && isInBound(new Point(i,j)) && !(new Point(x,y)).equals(new Point(i,j)))
//
//	        			adjacentPoint.add(new Point(i, j));
//	        			
//	        		
//	        }
//	}
//    	return adjacentPoint;
//
//	        }
	public boolean isInBound(Point p) {
	    double x = p.getX();
	    double y = p.getY();
	    return x >= 0 && x < 15 && y >= 0 && y < 15;
	}

}
