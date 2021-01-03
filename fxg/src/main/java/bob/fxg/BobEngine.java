package bob.fxg;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class BobEngine extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage theStage) {
        theStage.setTitle("JavaFx Game, Version 0.1");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        BobContext context = new BobContext();

        Canvas canvas = new Canvas(context.WIDTH, context.HEIGHT);
        root.getChildren().add(canvas);

        /**
         * Der Zwischenspeicher für die Tatstatur. Einige Tasten sollen hin und
         * her geschaltet werden können.
         */
        List<String> toggleCache = new ArrayList<>();

        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();

                // only add once... prevent duplicates
                boolean added = false;
                if ("SHIFT".equals(code)) {
                    context.shift = true;
                } else if (!toggleCache.contains(code)) {
                    toggleCache.add(code);
                    added = true;
                }

                System.out.println("key pressed: " + code + ", added=" + added);
            }
        });

        theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if ("SHIFT".equals(code)) {
                    context.shift = false;
                } else {
                    toggleCache.remove(code);
                }
                System.out.println("key released: " + code);
            }
        });

        theScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                double x = e.getX();
                double y = e.getY();
                context.mouse.setX(x);
                context.mouse.setY(y);
                System.out.println("click at (" + x + ":" + y + "), shift=" + context.shift);
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();
        reset(gc);

        BobSketchImpl sketch = new BobSketchImpl();
        sketch.setup(context);

        // Image earth = new Image("earth.png");

        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {                
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                                
                gc.clearRect(0, 0, context.WIDTH, context.HEIGHT);
                
                // background image clears canvas
                // gc.drawImage(earth, x, y);
                
                // FIXME: Event-Bus einführen (Key/Mouse unabhängig von Animation)

                if (context.shift 
                        && 0 < context.mouse.getX() 
                        && 0 < context.mouse.getY()) {
                    // Mover markieren
                    sketch.select(context.mouse.getX(), context.mouse.getY());
                    // Mausaktion konsumieren
                    context.mouse.multiply(0);
                }

                if (!context.pause) {
                    // neuen Zustand berechnen
                    sketch.update(context);
                }

                // den Zustand zeichnen
                sketch.draw(context, gc);

                reset(gc);

                // Rahmen
                gc.strokeRect(0, 0, context.WIDTH, context.HEIGHT);


                // Fußzeile
                StringBuffer footer = new StringBuffer();
                footer.append("Pause: ").append(context.pause ? "ON" : "OFF");
                footer.append("  ");
                footer.append("Debug: ").append(context.debug ? "ON" : "OFF");
                gc.fillText(footer.toString(), 10, context.HEIGHT - 10);
                // gc.strokeText(footer, 10, context.HEIGHT - 10);

                if (toggleCache.contains("P")) {
                    context.pause = !context.pause;
                    toggleCache.remove("P");
                }

            }
        }.start();

        theStage.show();
    }

    private void reset(GraphicsContext gc) {
        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 16);
        gc.setFont( theFont );
        gc.setFill( Color.GRAY);
        gc.setStroke( Color.LIGHTGREY);
        gc.setLineWidth(1);
    }

}