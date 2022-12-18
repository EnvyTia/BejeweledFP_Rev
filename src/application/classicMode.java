package application;

//import static application.game.GameBase.HEIGHT;
//import static application.game.GameBase.TILE_SIZE;
//import static application.game.GameBase.WIDTH;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//import application.lastPanel;
import javafx.animation.AnimationTimer;
//import application.Main.Jewel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

public class classicMode extends Main{
	private Jewel selected = null;
    private List<Jewel> jewels;
    private Color[] colors = new Color[] {
            Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW
    };

    private Label timeLabel;
	boolean flagStop = true;
	private int miliseconds=90;
	private int seconds;
	private int minutes=1;
	private DecimalFormat dFormat = new DecimalFormat("00");
	private String sMinutes;
	private String sSeconds;
	public VBox box = new VBox();
    
    private IntegerProperty score = new SimpleIntegerProperty();
	public void createContent() {
        root.setPrefSize(W * SIZE, H * SIZE);

        jewels = IntStream.range(6, W * H)
                .mapToObj(i -> new Point2D(i % W, i / W))
                .map(Jewel::new)
                .collect(Collectors.toList());

        root.getChildren().addAll(jewels);

        Text textScore = new Text();
        textScore.setTranslateX(5);
        textScore.setTranslateY(70);
        textScore.setFont(Font.font(30));
        try {
        	textScore.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
		} catch (FileNotFoundException e1) {
			textScore.setFont(Font.font("Verdana", 23));
		}
        textScore.textProperty().bind(score.asString("Score: [%d]"));

        createTimeLabel();
        box.getChildren().add(timeLabel);
        root.getChildren().add(box);
        root.getChildren().add(textScore);
        createGameLoop();
        
    }
	private class Jewel extends Parent {
        private Circle circle = new Circle(SIZE / 2);

        public Jewel(Point2D point) {
            circle.setCenterX(SIZE / 2);
            circle.setCenterY(SIZE / 2);
            circle.setFill(colors[new Random().nextInt(colors.length)]);

            setTranslateX(point.getX() * SIZE);
            setTranslateY(point.getY() * SIZE);
            getChildren().add(circle);

            setOnMouseClicked(event -> {
                if (selected == null) {
                    selected = this;
                    min+=10;
                }
                else {
                    swap(selected, this);
                    checkState();
                    selected = null;
                }
            });
        }

        public void randomize() {
            circle.setFill(colors[new Random().nextInt(colors.length)]);
        }

        public int getColumn() {
            return (int)getTranslateX() / SIZE;
        }

        public int getRow() {
            return (int)getTranslateY() / SIZE;
        }

        public void setColor(Paint color) {
            circle.setFill(color);
        }

        public Paint getColor() {
            return circle.getFill();
        }
    }
	private void checkState() {
        Map<Integer, List<Jewel>> rows = jewels.stream().collect(Collectors.groupingBy(Jewel::getRow));
        Map<Integer, List<Jewel>> columns = jewels.stream().collect(Collectors.groupingBy(Jewel::getColumn));

        rows.values().forEach(this::checkCombo);
        columns.values().forEach(this::checkCombo);
    }
	private void checkCombo(List<Jewel> jewelsLine) {
        Jewel jewel = jewelsLine.get(0);
        long count = jewelsLine.stream().filter(j -> j.getColor() != jewel.getColor()).count();
        if (count == 0) {
            score.set(score.get() + (1010-min));
            jewelsLine.forEach(Jewel::randomize);
            min=0;
        }
    }
	private void swap(Jewel a, Jewel b) {
        Paint color = a.getColor();
        a.setColor(b.getColor());
        b.setColor(color);
    }
	public Pane getRoot() {
    	return root;
    }
	
	private void createGameLoop() {
		AnimationTimer gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				miliseconds--;
				if(flagStop && miliseconds==0) {
				miliseconds = 90;
				seconds--;
				sSeconds = dFormat.format(seconds);
				sMinutes = dFormat.format(minutes);	
				timeLabel.setText(sMinutes + ":" + sSeconds);
				
				if(seconds==-1) {
					seconds = 59;
					minutes--;
					sSeconds = dFormat.format(seconds);
					sMinutes = dFormat.format(minutes);	
					timeLabel.setText(sMinutes + ":" + sSeconds);
				}
				if(minutes==0 && seconds==0) {
					flagStop = false;
					/*program yang tereksekusi saat waktu habis*/
						Fin();
					}
				}
			}
			
		};
		gameTimer.start();
		if(flagStop == false)gameTimer.stop();
	}
				
	
	private void createTimeLabel() {
		timeLabel = new Label();
		timeLabel.setPrefHeight(45);
		timeLabel.setPrefWidth(190);
		timeLabel.setStyle(timeStyle);
		timeLabel.setText("01:00");
		timeLabel.setAlignment(Pos.CENTER);
		try {
			timeLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 18));
		}
		catch (FileNotFoundException e) {
			timeLabel.setFont(Font.font("Verdana", 23));
		}
        
	}
	public static void Fin() {
//	autoclose program
		mainStage.close();
    }
	
}
