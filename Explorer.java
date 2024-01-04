package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;

public class Explorer extends Hero {
	

	public Explorer(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}

	public void useSpecial() throws InvalidTargetException, NotEnoughActionsException, MovementException, NoAvailableResourcesException {
		super.useSpecial();
	    Cell[][] map = Game.map;
		for (int i = 0; i < map.length; i++) {
	        for (int j = 0; j < map[i].length; j++) {
	            map[i][j].setVisible(true);
	        }
	    }
	}
		
	}
	

	

