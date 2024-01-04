package model.characters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public abstract class Hero extends Character {
	

		private int actionsAvailable;
		private int maxActions;
		private ArrayList<Vaccine> vaccineInventory;
		private ArrayList<Supply> supplyInventory;
		private boolean specialAction;
		
	
		
		public Hero(String name,int maxHp, int attackDmg, int maxActions) {
			super(name,maxHp, attackDmg);
			this.maxActions = maxActions;
			this.actionsAvailable = maxActions;
			this.vaccineInventory = new ArrayList<Vaccine>();
			this.supplyInventory=new ArrayList<Supply>();
			this.specialAction=false;
		
		}



		public boolean isSpecialAction() {
			return specialAction;
		}



		public void setSpecialAction(boolean specialAction) {
			this.specialAction = specialAction;
		}



		public int getActionsAvailable() {
			return actionsAvailable;
		}



		public void setActionsAvailable(int actionsAvailable) {
			this.actionsAvailable = actionsAvailable;
		}



		public int getMaxActions() {
			return maxActions;
		}



		public ArrayList<Vaccine> getVaccineInventory() {
			return vaccineInventory;
		}


		public ArrayList<Supply> getSupplyInventory() {
			return supplyInventory;
		}
		
		public void attack() throws InvalidTargetException, NotEnoughActionsException {
			if (this.actionsAvailable == 0)
				throw new NotEnoughActionsException();
			super.attack();
			this.setActionsAvailable(this.getActionsAvailable()-1);
		}
		
		public void move(Direction d) throws MovementException, NotEnoughActionsException {
			if (this.actionsAvailable == 0)
				throw new NotEnoughActionsException();
			else if(this.getCurrentHp()<=0)
				this.onCharacterDeath();
			Point p = this.getLocation();
		    Point oldP = new Point(p.x, p.y); 
		    if (d.equals(Direction.UP)) {
		        p.x++;
		    } else if (d.equals(Direction.DOWN)) {
		        p.x--;
		    } else if (d.equals(Direction.RIGHT)) {
		        p.y++;
		    } else if (d.equals(Direction.LEFT)){
		        p.y--;
		    }
		    if (!isInBound(p)) {
		        throw new MovementException();}
		    if(Game.map[p.x][p.y] instanceof CharacterCell && ((CharacterCell)Game.map[p.x][p.y]).getCharacter()!= null) {
	    		throw new MovementException();}
		    if(Game.map[p.x][p.y] instanceof CollectibleCell)
	    		((CollectibleCell)Game.map[p.x][p.y]).getCollectible().pickUp(this); 
		    else if (Game.map[p.x][p.y] instanceof TrapCell)
		    	this.setCurrentHp(this.getCurrentHp() - ((TrapCell)Game.map[p.x][p.y]).getTrapDamage());
	    	
		    this.actionsAvailable--;
	    	this.setLocation(p);
	        Game.map[p.x][p.y] = new CharacterCell(this);
	        ((CharacterCell)Game.map[oldP.x][oldP.y]).setCharacter(null);
	        if (this.getCurrentHp() <= 0) {
				this.onCharacterDeath();
		        Game.map[p.x][p.y] = new CharacterCell(null,false);
	        }else
	        	updateAdjacentCellsVisibility(p);
		}
		    
		    
		

		public void updateAdjacentCellsVisibility(Point p) {
		    int x = p.x;
		    int y = p.y;
		    for (int i = x - 1; i <= x + 1; i++) {
		        for (int j = y - 1; j <= y + 1; j++) {
		            Point adjacentPoint = new Point(i, j);
		            if (isInBound(adjacentPoint) && Game.map[i][j] != null) {
		                Game.map[i][j].setVisible(true);
		            }
		        }
		    }
		}

		
		public boolean isInBound(Point p) {
		    double x = p.getX();
		    double y = p.getY();
		    return x >= 0 && x < 15 && y >= 0 && y < 15;
		}
		
		 

		public void useSpecial() throws InvalidTargetException, NotEnoughActionsException, MovementException, NoAvailableResourcesException {
			if (!this.getSupplyInventory().isEmpty()) {
				((Collectible)getSupplyInventory().get(0)).use(this);
				this.setSpecialAction(true);
			}
			else
				throw new NoAvailableResourcesException();
		}
		
		public void cure() throws InvalidTargetException, NotEnoughActionsException, NoAvailableResourcesException {
			if (getTarget() == null)
				throw new InvalidTargetException();
			else if(this.actionsAvailable==0) {
				throw new NotEnoughActionsException("No enough Action points");
			}
			if(!(isAdjacent(this.getLocation(), getTarget().getLocation()))) {
				throw new InvalidTargetException();}
			else if(getTarget() instanceof Hero) {
				throw new InvalidTargetException();}
			
			if(!getVaccineInventory().isEmpty()) {
				((Collectible)getVaccineInventory().get(0)).use(this);
				Zombie.ZOMBIES_COUNT--;
				this.actionsAvailable--;
			}
		}
			
		

		public void SpawnHero() {
			Random rand = new Random();
	        int index = rand.nextInt(Game.availableHeroes.size()); 
	        Hero hero = Game.availableHeroes.get(index); 
	        Game.heroes.add(hero);
	        Game.availableHeroes.remove(index);
		}
		public void onCharacterDeath() {
				int x = this.getLocation().x;
				int y = this.getLocation().y;
				Game.heroes.remove(this);
		        ((CharacterCell)Game.map[x][y]).setCharacter(null);
			
		}

}
