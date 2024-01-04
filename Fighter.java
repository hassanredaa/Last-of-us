package model.characters;

import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;

public class Fighter extends Hero{

	
	public Fighter(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}

	
	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		if (this.isSpecialAction()) {
			int x = this.getActionsAvailable();
			x++;
			this.setActionsAvailable(x);
			super.attack();
		}
		else
			super.attack();

		}

}
