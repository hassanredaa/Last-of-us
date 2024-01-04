package views;

import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

import model.characters.Fighter;
import engine.Game;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class ChooseFighter extends Scene {
	private MediaPlayer mediaPlayer1;
	private MediaPlayer mediaPlayer2;
	private Fighter hero;
	private Button SkipTrailer;
	private Button Return;



	public ChooseFighter(Stage primaryStage) {
		super(new StackPane(),1280,720);
		MediaView mediaView1 = new MediaView();
        MediaView mediaView2 = new MediaView();
		StackPane stackPane = new StackPane();
		Return= new Button(" Return to previous page ");
		SkipTrailer= new Button(" SkipTrailer ->");

		Return.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff ; -fx-font-size:30");
		Return.setOnAction(e -> {
        Return.setVisible(false);
            try {
	        	mediaPlayer1.stop();
	            mediaView1.setVisible(false);
	            ChooseHero chooseHeroScene = new ChooseHero(primaryStage);
	            primaryStage.setScene(chooseHeroScene);
            }
            catch (Exception e1) {
	             e1.printStackTrace();}
		  });	          		
		 SkipTrailer.setVisible(false);
         Timer timer = new Timer();
         timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> SkipTrailer.setVisible(true));
                }
            }, 4000);
		 try {
             Media p1 = new Media(Paths.get("Videos/fighvid2.mp4").toUri().toString());
             mediaPlayer1 = new MediaPlayer(p1);
             mediaView1.setMediaPlayer(mediaPlayer1);
             mediaPlayer1.play();

         } catch (Exception e1) {
             e1.printStackTrace();
         } 
		 SkipTrailer.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff ; -fx-font-size:30");
	     SkipTrailer.setOnAction(e -> {
	     mediaPlayer1.stop();
	     mediaView1.setVisible(false);
	            try {
	            	
		             Media p1 = new Media(Paths.get("Videos/fighchoose.mp4").toUri().toString());
		             mediaPlayer2 = new MediaPlayer(p1);
		             mediaView2.setMediaPlayer(mediaPlayer2);
		             mediaPlayer2.setCycleCount(MediaPlayer.INDEFINITE);
		             mediaPlayer2.play();
			         SkipTrailer.setVisible(false);
			         Return.setVisible(false);
		         } 
	            catch (Exception e1) {
		             e1.printStackTrace();}
		       	 
		       	stackPane.setOnMousePressed(new EventHandler<MouseEvent>() {
		             @Override
		             public void handle(MouseEvent event) {
		                 double mouseX = event.getX();
		                 double mouseY = event.getY();

		                 if (mouseX >= 0 && mouseX <= 120 && mouseY >= 0 && mouseY <= 60) {
		                 	mediaPlayer2.stop();
		                 	ChooseHero choosehero = new ChooseHero(primaryStage);
		                    primaryStage.setScene(choosehero);
		                 	
		                     }
		                 else if (mouseX >= 160 && mouseX <= 500 && mouseY >= 340 && mouseY <= 720){
		                 	mediaPlayer2.stop();
		                 	hero = (Fighter) Game.availableHeroes.get(0);
			                 Game.startGame(hero);
			                 primaryStage.setScene(new Map(primaryStage));

		                 	   
		                 }
		                 else if (mouseX >= 800 && mouseX <= 1150 && mouseY >= 340 && mouseY <= 720){
			                 mediaPlayer2.stop();
			                 hero = (Fighter) Game.availableHeroes.get(6);
			                 Game.startGame(hero);
			                 primaryStage.setScene(new Map(primaryStage));

		                 }
		               
		             }
		         });
	        });

		 
	        mediaPlayer1.setOnEndOfMedia(() -> {
	            mediaView1.setVisible(false);
	            try {
	            	SkipTrailer.setVisible(false);
		            Return.setVisible(false);
		             Media p1 = new Media(Paths.get("Videos/fighchoose.mp4").toUri().toString());
		             mediaPlayer2 = new MediaPlayer(p1);
		             mediaView2.setMediaPlayer(mediaPlayer2);
		             mediaPlayer2.setCycleCount(MediaPlayer.INDEFINITE);
		             mediaPlayer2.play();

		         } catch (Exception e1) {
		             e1.printStackTrace();}
		       	 
		       		        });
	        stackPane.getChildren().addAll(mediaView1, mediaView2,SkipTrailer,Return);
	        StackPane.setAlignment(SkipTrailer, Pos.TOP_RIGHT);
	        StackPane.setAlignment(Return, Pos.TOP_LEFT);
	        setRoot(stackPane);
	}
}