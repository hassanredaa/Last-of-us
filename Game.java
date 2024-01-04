package engine;


import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NotEnoughActionsException;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Game {
	
	public static Cell [][] map = new Cell[15][15];
	public static ArrayList <Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList <Hero> heroes =  new ArrayList<Hero>();
	public static ArrayList <Zombie> zombies =  new ArrayList<Zombie>();
	
	
	
		
	public static void loadHeroes(String filePath)  throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Hero hero=null;
			switch (content[1]) {
			case "FIGH":
				hero = new Fighter(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			case "MED":  
				hero = new Medic(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3])) ;
				break;
			case "EXP":  
				hero = new Explorer(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			}
			availableHeroes.add(hero);
			line = br.readLine();
		}
		br.close();
	}
	public static void startGame(Hero h) {
		for (int i = 0; i<15;i++) {
			for (int j = 0; j<15;j++) {
				map[i][j] = new CharacterCell();}}
		((CharacterCell)Game.map[0][0]).setCharacter(h);			
		h.setLocation(new Point(0,0));
		availableHeroes.remove(h);
		heroes.add(h);
		spawnVaccines();
	    spawnSupplies();
	    spawnTraps();
	    spawnZombies(10);
	    map[0][1].setVisible(true);
		map[1][1].setVisible(true);
		map[1][0].setVisible(true);
		map[0][0].setVisible(true);
	}
	public static void spawnVaccines() {
		int x = (int)(Math.random() * (15));
        int y = (int)(Math.random() * (15));
		for (int i = 0; i < 5; i++) {
			while(!(map[x][y] instanceof CharacterCell) || ((CharacterCell)map[x][y]).getCharacter() != null){	    		
				x = (int)(Math.random() * (15));
		        y = (int)(Math.random() * (15));
		        
	    }
	        map[x][y] = new CollectibleCell(new Vaccine());
	}
	}
	    public static void spawnSupplies() {
	    	int x = (int)(Math.random() * (15));
	        int y = (int)(Math.random() * (15));
			for (int i = 0; i < 5; i++) {
				while(!(map[x][y] instanceof CharacterCell) || ((CharacterCell)map[x][y]).getCharacter() != null){		    		
					x = (int)(Math.random() * (14 - 0 + 1));
			        y = (int)(Math.random() * (14 - 0 + 1));
			        
		    }
		        map[x][y] = new CollectibleCell(new Supply());
		}
	    }
	    public static void spawnZombies(int s) {
	    	int x = (int)(Math.random() * (15));
	        int y = (int)(Math.random() * (15));
			for (int i = 0; i < s; i++) {
		    	while(!(map[x][y] instanceof CharacterCell) || ((CharacterCell)map[x][y]).getCharacter() != null){
		    		x = (int)(Math.random() * (15));
			        y = (int)(Math.random() * (15));
			        
		    }
		        Zombie zombie = new Zombie();
		        zombie.setLocation(new Point(x,y));
		        zombies.add(zombie);
	            ((CharacterCell)map[x][y]).setCharacter(zombie);
	        }
	    }
	    
	    public static void spawnTraps() {
	    	int x = (int)(Math.random() * (15));
	        int y = (int)(Math.random() * (15));
			for (int i = 0; i < 5; i++) {
				while(!(map[x][y] instanceof CharacterCell) || ((CharacterCell)map[x][y]).getCharacter() != null){		    		
					x = (int)(Math.random() * (15));
			        y = (int)(Math.random() * (15));
			        
		    }
	            map[x][y] = new TrapCell();
	        }
	    }
	    
	    public static boolean checkWin() {
	    	if (heroes.size()<5)
	    		return false;
	    	for (int i = 0; i<15;i++) {
				for (int j = 0; j<15;j++) {
					if(map[i][j] instanceof CollectibleCell && ((CollectibleCell) map[i][j]).getCollectible() instanceof Vaccine)
						return false;
				}
			}
	    	for (int x = 0; x < heroes.size(); x++) {
	    		Hero h = heroes.get(x);
	    		if (!h.getVaccineInventory().isEmpty())
	    			return false;
	    	}
	    	return true;
	    }
	    
	    public static boolean checkGameOver() {
	    	
	    	if(heroes.size() == 0)
	    		return true;
	    	int C_V = 0;
	    	for (int i = 0; i<15;i++) {
				for (int j = 0; j<15;j++) {
					if(map[i][j] instanceof CollectibleCell && ((CollectibleCell) map[i][j]).getCollectible() instanceof Vaccine)
						C_V++;
				}
			}
	    	
	    	for (int x = 0; x < heroes.size(); x++) {
	    		Hero h = heroes.get(x);
	    		if (!h.getVaccineInventory().isEmpty() || C_V != 0)
	    			return false;
	    	}
	    	if (C_V >0)
	    		return false;
	    	
	    	return true;
	    }
	    public static void endTurn() throws InvalidTargetException, NotEnoughActionsException, MovementException {
	    	for(int i = 0; i<zombies.size();i++) {
	    		zombies.get(i).attack();
	    		zombies.get(i).setTarget(null);

	    	}
	    	for(int j = 0; j<heroes.size();j++) {
	    		Hero hero = heroes.get(j);
	    		hero.setActionsAvailable(hero.getMaxActions());
	    		hero.setTarget(null);
	    		hero.setSpecialAction(false);
	    		Point p = hero.getLocation();
	    		map[p.x][p.y].setVisible(true);
	    		updateAdjacentCellsVisibility(p);
	    	}
	    	spawnZombies(1);
	    }
	    
	    
	    public static void updateAdjacentCellsVisibility(Point p) {
		    int x = p.x;
		    int y = p.y;
		    for (int i = x - 1; i <= x + 1; i++) {
		        for (int j = y - 1; j <= y + 1; j++) {
		            Point adjacentPoint = new Point(i, j);
		            if (isInBound(adjacentPoint)) {
		                map[i][j].setVisible(true);
		            }
		        }
		    }
		}
	   
	    public static boolean isInBound(Point p) {
		    double x = p.getX();
		    double y = p.getY();
		    return x >= 0 && x < 15 && y >= 0 && y < 15;
		}
	}

