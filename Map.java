package views;

import java.awt.Point;
import java.util.ArrayList;

import model.characters.Character;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import engine.Game;
import exceptions.MovementException;
import exceptions.NotEnoughActionsException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.characters.Direction;
import javafx.scene.control.ButtonBar;




public class Map extends Scene {
	private VBox selHero;
	private VBox avalHero;
	private Hero herosel;
	private Zombie targetsel;
	GridPane map;
	
	public Map(Stage primaryStage){
		super(new BorderPane(),1280,720);
		BorderPane root = new BorderPane();
		Image backgroundImage = new Image("background.jpg");
		BackgroundImage background = new BackgroundImage(backgroundImage, null, null, null, null);
        root.setBackground(new Background(background));
		map = new GridPane();
		root.setCenter(map);
	   
        map.setAlignment(Pos.CENTER);
        map.setHgap(5);
        map.setVgap(5);
        Button moveUpButton = new Button("Move Up");
        Button moveDownButton = new Button("Move Down");
        Button moveLeftButton = new Button("Move Left");
        Button moveRightButton = new Button("Move Right");
        Button Attack = new Button("Attack");
        Button Cure = new Button("Cure");
        Button Endturn = new Button("Endturn");
        Button SpecialAction = new Button("Special Action");
        HBox buttonBox = new HBox(10, moveUpButton, moveDownButton, moveLeftButton, moveRightButton,Attack,Cure,SpecialAction,Endturn);
        selHero = new VBox();
	    avalHero = new VBox();
        root.setBottom(buttonBox);
        root.setLeft(selHero);
        root.setRight(avalHero);
        root.setPadding(new Insets(10));
        
        
        for (int row = 14; row >= 0; row--) {
            for (int col = 0; col < 15; col++) {
        		Button tileButton = new Button();
        		if (row == 0 && col == 0){
        			 tileButton.setStyle("-fx-background-color: #000000; ");
                     tileButton.setText("Hero");
                     final int finalRow = row; 
                     final int finalCol = col; 
                     tileButton.setOnAction(event -> handleGridButtonPress(finalRow, finalCol));

        		}
        		else if(Game.map[row][col] instanceof CollectibleCell){
                	if(((CollectibleCell) Game.map[row][col]).getCollectible() instanceof Vaccine){
                    tileButton.setMinSize(40, 40);
                    tileButton.setStyle("-fx-background-color: #ff0000; ");
                    tileButton.setText("Vaccine");

            	}
                	else{
                		 tileButton.setMinSize(40, 40);
                         tileButton.setStyle("-fx-background-color: #ff0bc0; ");
	                       tileButton.setText("Supply");

                	}
                		}
            	else if(Game.map[row][col] instanceof CharacterCell){
            		if((((CharacterCell) Game.map[row][col]).getCharacter()) instanceof Hero ){
                      tileButton.setMinSize(40, 40);
                      final int finalRow = row; 
                      final int finalCol = col; 
                      tileButton.setOnAction(event -> handleGridButtonPress(finalRow, finalCol));

                     }
            		else if((((CharacterCell) Game.map[row][col]).getCharacter()) instanceof Zombie ){
                        tileButton.setMinSize(40, 40);
                        tileButton.setStyle("-fx-background-color: #000000; ");
                        tileButton.setText("Zombie");
                        final int finalRow = row; 
                        final int finalCol = col; 
                        tileButton.setOnAction(event -> handleGridButtonPress(finalRow, finalCol));
}
            		
            		else{
            			tileButton.setMinSize(40, 40);
                        tileButton.setStyle("-fx-background-color: #000000; ");
                        tileButton.setText("empty");
            		}

            	}
              
                map.add(tileButton, col, 14 - row);
            }
        }
        moveRightButton.setOnAction(e -> {
            try {
             herosel.move(Direction.RIGHT);
             updateMapScene();
             updateSelectedHeroDetails(herosel);
             updateAvailableHeroesList();

            } catch (MovementException ex) {
				showErrorDialog(ex.getMessage());
            } catch (NotEnoughActionsException ex) {
				showErrorDialog(ex.getMessage());
            }
        });
        moveLeftButton.setOnAction(e -> {
            try {
             herosel.move(Direction.LEFT);
             updateMapScene();
             updateSelectedHeroDetails(herosel);
             updateAvailableHeroesList();

            } catch (MovementException ex) {
				showErrorDialog(ex.getMessage());
            } catch (NotEnoughActionsException ex) {
				showErrorDialog(ex.getMessage());
            }
        });
        moveUpButton.setOnAction(e -> {
            try {
             herosel.move(Direction.UP);
             updateMapScene();
             updateSelectedHeroDetails(herosel);
             updateAvailableHeroesList();

            } catch (MovementException ex) {
				showErrorDialog(ex.getMessage());
            } catch (NotEnoughActionsException ex) {
				showErrorDialog(ex.getMessage());
            }
        });
        moveDownButton.setOnAction(e -> {
            try {
             herosel.move(Direction.DOWN);
             updateMapScene();
             updateSelectedHeroDetails(herosel);
             updateAvailableHeroesList();

            } catch (MovementException ex) {
				showErrorDialog(ex.getMessage());
            } catch (NotEnoughActionsException ex) {
				showErrorDialog(ex.getMessage());
            }
        });
        Attack.setOnAction(e -> {
             try {
            	herosel.setTarget(targetsel);
				herosel.attack();
				updateMapScene();
	             updateSelectedHeroDetails(herosel);
	             updateAvailableHeroesList();
			} catch (Exception e1) {
				showErrorDialog(e1.getMessage());
			}
             
        });
        Cure.setOnAction(e -> {
            try {
				herosel.cure();
				updateMapScene();
	             updateSelectedHeroDetails(herosel);
	             updateAvailableHeroesList();
			} catch (Exception e1) {
				showErrorDialog(e1.getMessage());
			}            
       });
        
        Endturn.setOnAction(e -> {
            try {
				Game.endTurn();
				updateMapScene();
	             updateSelectedHeroDetails(herosel);
	             updateAvailableHeroesList();
			} catch (Exception e1) {
				showErrorDialog(e1.getMessage());
			}         
       });
        
        SpecialAction.setOnAction(e -> {
        	herosel.setSpecialAction(true);                 
       });
        
        
        setRoot(root);
	}
	
	
	 private void handleGridButtonPress(int row, int col) {
		 if (((CharacterCell) Game.map[row][col]).getCharacter() instanceof Hero){
		 herosel = (Hero) ((CharacterCell) Game.map[row][col]).getCharacter();
		 updateSelectedHeroDetails(herosel);}
		 else{
			 targetsel = (Zombie)((CharacterCell) Game.map[row][col]).getCharacter();
		 }
	    }
	 
	 
	 
	    public void updateSelectedHeroDetails(Hero hero) {
	        selHero.getChildren().clear();
	        String type;
	        if (hero != null) {
	        	if(hero instanceof Fighter){
	        		type = "Fighter";}
	        	else if(hero instanceof Explorer){
	        		type = "Explorer";
	        	}
	        	else{
	        		type = "Medic";
	        	}
	        		
	        	
	            Label nameLabel = new Label("Name: " + hero.getName());
	            Label typeLabel = new Label("Type: " + type);
	            Label hpLabel = new Label("Current HP: " + hero.getCurrentHp());
	            Label attackLabel = new Label("Attack Damage: " + hero.getAttackDmg());
	            Label actionpoints = new Label("Action Points: " + hero.getActionsAvailable());

	            Label suppliesLabel = new Label("Supplies: " + hero.getSupplyInventory().size());
	            Label vaccinesLabel = new Label("Vaccines: " + hero.getVaccineInventory().size());

	            selHero.getChildren().addAll(nameLabel, typeLabel, hpLabel, attackLabel,actionpoints, suppliesLabel, vaccinesLabel);
	        } else {
	            Label noHeroLabel = new Label("No hero selected");
	            selHero.getChildren().add(noHeroLabel);
	        }
	    }
	    
	    
	    
	    private void updateAvailableHeroesList() {
	        // Update the right panel with a list of available heroes
	        avalHero.getChildren().clear();
	        String type;
	        for (Hero hero : Game.heroes) {
	        	if(hero instanceof Fighter){
	        		type = "Fighter";}
	        	else if(hero instanceof Explorer){
	        		type = "Explorer";
	        	}
	        	else{
	        		type = "Medic";
	        	}
	        		
	        	
	            Label nameLabel = new Label("Name: " + hero.getName());
	            Label typeLabel = new Label("Type: " + type);
	            Label hpLabel = new Label("Current HP: " + hero.getCurrentHp());
	            Label attackLabel = new Label("Attack Damage: " + hero.getAttackDmg());
	            Label suppliesLabel = new Label("Supplies: " + hero.getSupplyInventory().size());
	            Label vaccinesLabel = new Label("Vaccines: " + hero.getVaccineInventory().size());

	            avalHero.getChildren().addAll(nameLabel, typeLabel, hpLabel, attackLabel, suppliesLabel, vaccinesLabel);
	        }
	    }
	    
	    
	    private void updateMapScene(){
	    	map.getChildren().clear();
	    	 for (int row = 14; row >= 0; row--) {
	             for (int col = 0; col < 15; col++) {
	         		Button tileButton = new Button();
	         		 if(Game.map[row][col] instanceof CollectibleCell){
	                 	if(((CollectibleCell) Game.map[row][col]).getCollectible() instanceof Vaccine){
	                     tileButton.setMinSize(40, 40);
	                     tileButton.setStyle("-fx-background-color: #ff0000; ");
	                       tileButton.setText("Vaccine");

	             	}
	                 	else{
	                 		 tileButton.setMinSize(40, 40);
	                          tileButton.setStyle("-fx-background-color: #ff0bc0; ");
		                       tileButton.setText("Supply");

	                 	}
	                 		}
	             	else if(Game.map[row][col] instanceof CharacterCell){
	             		if((((CharacterCell) Game.map[row][col]).getCharacter()) instanceof Hero ){
	                       tileButton.setMinSize(40, 40);
	                       tileButton.setStyle("-fx-background-color: #000000; ");
	                       tileButton.setText("Hero");
	                       final int finalRow = row; 
	                       final int finalCol = col; 
	                       tileButton.setOnAction(event -> handleGridButtonPress(finalRow, finalCol));

	                      }
	             		else if((((CharacterCell) Game.map[row][col]).getCharacter()) instanceof Zombie ){
	                         tileButton.setMinSize(40, 40);
	                         tileButton.setStyle("-fx-background-color: #000000; ");
	                         tileButton.setText("Zombie");
	                         final int finalRow = row; 
	                         final int finalCol = col; 
	                         tileButton.setOnAction(event -> handleGridButtonPress(finalRow, finalCol));}
	             		else{
	             			tileButton.setMinSize(40, 40);
	                         tileButton.setStyle("-fx-background-color: #000000; ");
	                         tileButton.setText("empty");
	             		}

	             	}
	               
	                 map.add(tileButton, col, 14 - row);
	             }
	         }
	    }
	    private void showErrorDialog(String message) {
	        Stage dialogStage = new Stage();
	        dialogStage.initModality(Modality.APPLICATION_MODAL);
	        dialogStage.initStyle(StageStyle.UTILITY);
	        
	        Label label = new Label(message);
	        ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
	        
	        Dialog<Void> dialog = new Dialog<>();
	        dialog.setDialogPane(new DialogPane());
	        dialog.getDialogPane().setContent(new VBox(label));
	        dialog.getDialogPane().getButtonTypes().add(closeButton);
	        
	        dialog.setResultConverter(buttonType -> {
	            if (buttonType == closeButton) {
	                dialogStage.close();
	            }
	            return null;
	        });
	        
	        dialog.initOwner(dialogStage);
	        dialog.showAndWait();
	    }


}
