package views;

import java.io.IOException;
import java.nio.file.Paths;

import engine.Game;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChooseHero extends Scene {
	private MediaPlayer mediaPlayer1;

    
	public ChooseHero(Stage primaryStage) {
		super(new GridPane(), 1280, 720);
        MediaView mediaView1 = new MediaView();
 
        try {
            Media p1 = new Media(Paths.get("Videos/type111.mp4").toUri().toString());
            mediaPlayer1 = new MediaPlayer(p1);
            mediaView1.setMediaPlayer(mediaPlayer1);
            mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
     
        Text text1= new Text("fighter");
        GridPane gridPane = new GridPane();
        
        gridPane.add(mediaView1,0,0);
        text1.setX(88);

        // Play the videos
        mediaPlayer1.play();        
        gridPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double mouseX = event.getX();
                double mouseY = event.getY();

                // Check if the click position is within a certain range
                if (mouseX >= 0 && mouseX <= 425 && mouseY >= 0 && mouseY <= 720) {
                	mediaPlayer1.stop();
                	ChooseMedic choosemed = new ChooseMedic(primaryStage);
                    primaryStage.setScene(choosemed);
                    }
                else if (mouseX >= 426 && mouseX <= 853 && mouseY >= 0 && mouseY <= 720){
                	mediaPlayer1.stop();
                	ChooseExp chooseexp = new ChooseExp(primaryStage);
                    primaryStage.setScene(chooseexp);    
                }
                else{
                	mediaPlayer1.stop();
                	ChooseFighter choosefighter = new ChooseFighter(primaryStage);
                    primaryStage.setScene(choosefighter);                	           
                }
            }
        });
         setRoot(gridPane);
         
         try {
			Game.loadHeroes("Heroes.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}   
}
