package views ;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

public class GameGUI extends Application {
    private StackPane gamePane;
    private Button startButton;
    private MediaView backgroundView;
    private MediaPlayer backgroundPlayer;

    public GameGUI() {
      
    }

    
    public void start(Stage stage) {
    	gamePane = new StackPane();
        startButton = new Button("Start Game");
        backgroundView = new MediaView();

        startButton.setVisible(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> startButton.setVisible(true));
            }
        }, 10000);
        try {
            Media backgroundMedia = new Media(Paths.get("Videos/backgvideo1.mp4").toUri().toString());
            backgroundPlayer = new MediaPlayer(backgroundMedia);
            backgroundView.setMediaPlayer(backgroundPlayer);
            backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundPlayer.play();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        startButton.setOnAction(e -> {
            backgroundPlayer.stop();
            stage.setScene(new Welcome(stage));
            });
            
        gamePane.getChildren().addAll(backgroundView, startButton);
        StackPane.setAlignment(startButton, Pos.BOTTOM_CENTER);



        Scene scene = new Scene(gamePane,1280,720);
        stage.setScene(scene);
        
    	Image icon = new Image("icon.jpeg");
    	stage.getIcons().add(icon);
    	stage.setTitle("The Last of us");
    	stage.show();
    	
    }
    public static void changeScene(Stage stage, Scene newScene) {
        stage.setScene(newScene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}