package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import application.MainMenu;

import application.classicMode;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	protected final String timeStyle = "-fx-background-color: transparent; -fx-background-image: url('application/resources/green_buttonRe.png');";
	
    protected static final int W = 6;
    protected static final int H = 7;
    protected static final int SIZE = 90;
    protected final String FONT_PATH = "src/application/resources/kenvector_future.ttf";
	
    protected int min=0;
    Scene scene;
	static Stage mainStage;
	static Pane root = new Pane();
	MainMenu mainMenu = new MainMenu();
	static classicMode star= new classicMode();

    private Color[] colors = new Color[] {
            Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW
    };
    protected IntegerProperty score = new SimpleIntegerProperty();


    @Override
    public void start(Stage primaryStage) throws Exception {
    	root.setPrefSize(540, 630);
    	root.getChildren().add(mainMenu.getPane());
    	mainStage = primaryStage;
    	scene = new Scene(root);
        mainStage.setTitle("BEJEWELED");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
	mainStage.getIcons().add(
		new Image("application/resources/BejeweledIcon.png"));
        mainStage.show();
    }
    
    
    public static void exitGame() {
    	mainStage.close();
    }
    
    public static void getStart() {
    	classicMode star = new classicMode();
    	star.createContent();
    	root.getChildren().remove(0);
    	root.getChildren().add(star.getRoot());
    	mainStage.setWidth(700);
    	mainStage.setHeight(900);
    	mainStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
