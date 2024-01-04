package model.characters;

import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

public class Medic extends Hero {
	//Heal amount  attribute - quiz idea
	

	public Medic(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
		
	}
	
	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException, MovementException {

        if(getTarget()==null)
            throw new InvalidTargetException("you dont have a target");
        if(getTarget()instanceof Zombie)
            throw new InvalidTargetException("you can only heal heroes");
        if (getTarget().getLocation().distance(getLocation())>=2)
            throw new InvalidTargetException("your target is not adjacent");
        super.useSpecial();
        getTarget().setCurrentHp(getTarget().getMaxHp());

}
}