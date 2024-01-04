package views;

import java.nio.file.Paths;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Welcome extends Scene{
	private MediaView backgroundView;
    private MediaPlayer backgroundPlayer;
    private StackPane gamePane;
    private Scene scene;
    private Button chooseHero;
    private Button viewMap;



	public Welcome(Stage primaryStage) {
		super(new StackPane(), 1280, 720);
        gamePane = (StackPane) getRoot();
        chooseHero = new Button("Choose your Hero");
        viewMap = new Button("Take a look on our Fantastic Map ;)");
        backgroundView = new MediaView();
        
        try {
            Media backgroundMedia = new Media(Paths.get("Videos/Welcome1.mp4").toUri().toString());
            backgroundPlayer = new MediaPlayer(backgroundMedia);
            backgroundView.setMediaPlayer(backgroundPlayer);
            backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundPlayer.play();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        chooseHero.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff ; -fx-font-size:30");
        viewMap.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff ; -fx-font-size:30");
        
        chooseHero.setOnAction(e -> {
            backgroundPlayer.stop();
            ChooseHero chooseHeroScene = new ChooseHero(primaryStage);
            primaryStage.setScene(chooseHeroScene);
            
        });

        gamePane.getChildren().addAll(backgroundView,chooseHero,viewMap);
        StackPane.setAlignment(chooseHero, Pos.CENTER_RIGHT);
        StackPane.setAlignment(viewMap, Pos.CENTER_LEFT);



      }

    	 public void show(Stage primaryStage) {
    		 primaryStage.setScene(this);
    	        primaryStage.setTitle("Welcome");
    	        primaryStage.show();
    }


}
