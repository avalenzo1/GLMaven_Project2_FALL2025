import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Start Screen");
		
		 Rectangle rect = new Rectangle (50, 40, 50, 50);


	     rect.setArcHeight(50);
	     rect.setArcWidth(50);
	     rect.setFill(Color.VIOLET);

	     RotateTransition rt = new RotateTransition(Duration.millis(5000), rect);
	     rt.setByAngle(270);
	     rt.setCycleCount(4);
	     rt.setAutoReverse(true);
	     SequentialTransition seqTransition = new SequentialTransition (
	         new PauseTransition(Duration.millis(500)),
	         rt
	     );
	     seqTransition.play();
	     
	     FadeTransition ft = new FadeTransition(Duration.millis(5000), rect);
	     ft.setFromValue(1.0);
	     ft.setToValue(0.3);
	     ft.setCycleCount(4);
	     ft.setAutoReverse(true);

	     ft.play();

         VBox center = new VBox();

        Image imgPlay = new Image(getClass().getResourceAsStream("pictures/play_btn.png"));
        ImageView imgViewPlay = new ImageView(imgPlay);
        imgViewPlay.setFitWidth(300);
        imgViewPlay.setPreserveRatio(true);


        //Exit Button
        Image exitPic = new Image("C:\\Users\\ajaj4\\OneDrive\\Desktop\\Junior\\Semester1\\Cs342\\GLMaven_Project2_FALL2025\\src\\main\\resources\\pictures\\exitPicture.png");
        ImageView exitView = new ImageView(exitPic);
        exitView.setFitWidth(50);
        exitView.setFitHeight(50);
        Button exitButton = new Button();
        exitButton.setStyle("-fx-background-color: transparent;");
        exitButton.setGraphic(exitView);

        Button playButton = new Button();
        playButton.setStyle("-fx-background-color: transparent");
        playButton.setGraphic(imgViewPlay);

        center.getChildren().addAll(playButton);


	     BorderPane root = new BorderPane();
	     root.setCenter(center);
         root.setTop(exitButton);
         root.setStyle("-fx-background-color: #D4AF37;");
	     
	     Scene scene = new Scene(root, 500,400);
         primaryStage.setScene(scene);
         primaryStage.show();
		
				
		//Start Screen

        Button button = new Button("Click Me");
	}

}
