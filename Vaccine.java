package model.collectibles;

import java.awt.Point;
import java.util.Random;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.Hero;
import model.world.CharacterCell;

public class Vaccine implements Collectible {

	public Vaccine() {
	}

	public void pickUp(Hero h) {
		h.getVaccineInventory().add(this);
	}

	public void use(Hero h) throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException {
		if (h.getTarget() == null)
			throw new InvalidTargetException("no target");
		if (h.getActionsAvailable() == 0)
			throw new NotEnoughActionsException("no actions");
		if (!h.getVaccineInventory().isEmpty()) {
			h.getVaccineInventory().remove(this);
			int x1 = (h.getTarget().getLocation().x);
			int y1 = (h.getTarget().getLocation().y);
			Game.zombies.remove(h.getTarget());
			Random rand = new Random();
	        int index = rand.nextInt(Game.availableHeroes.size()); 
	        Hero hero = Game.availableHeroes.get(index);
	        Game.availableHeroes.remove(index);
	        Point p = new Point();
	        p.x = x1;
	        p.y = y1;
	        hero.setLocation(p);
	        ((CharacterCell)Game.map[x1][y1]).setCharacter(hero);
	        Game.heroes.add(hero);
	        }
		else {
			throw new NoAvailableResourcesException("No available resources");
		}
	}

}
