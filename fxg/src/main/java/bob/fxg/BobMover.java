package bob.fxg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;

public class BobMover {

    private static final double MAX_SPEED = 25d;
    
    private final String name;

    private final BobVector position;
    
    private final BobVector velocity;

    private final BobVector acceleration;

    private final BobVector lastAcceleration;

    public BobMover(String name, int x, int y) {
        this.name = name;
        position = new BobVector(x, y);
        velocity = new BobVector(BobUtil.random(5), BobUtil.random(5));
        acceleration = new BobVector(0, 0);
        lastAcceleration = new BobVector(0, 0);
    }

    public void applyForce(BobVector value) {
        acceleration.add(value);
    }

    public void moveWithin(int width, int height) {
        // Objekt bewegen
        velocity.add(acceleration);
        if (velocity.magnitude() > MAX_SPEED) {
            velocity.normalize();
            velocity.multiply(MAX_SPEED);
        }
        position.add(velocity);
        lastAcceleration.set(acceleration);
        acceleration.multiply(0);
        
        // Kanten beachten
        if (position.getX() <= 0 || position.getX()  >= width) {
            velocity.setX(velocity.getX() * -1);
        }
        if (position.getY() <= 0 || position.getY() >= height) {
            velocity.setY(velocity.getY() * -1);
        }
    }
    
    public void draw(GraphicsContext gc) {
        
        // Richtung + Kraft
        draw(gc, velocity, Color.GRAY);
        draw(gc, lastAcceleration, Color.BLUE);
        
        // der Punkt
        gc.fillOval(position.getX() - 5, position.getY() - 5, 10, 10);

        // die Beschriftung
        Font font = Font.font( "Helvetica", FontWeight.BOLD, 12);
        FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);

        gc.setFont(font);

        float fontOffsetY = 0f + fm.getLineHeight();
        gc.fillText("Mover " + name, position.getX() + 6, position.getY() + fontOffsetY);

        fontOffsetY += fm.getLineHeight();
        gc.fillText(position.getX() + ":" + position.getY(), position.getX() + 6, position.getY() + fontOffsetY);

        fontOffsetY += fm.getLineHeight();
        gc.fillText(velocity.getX() + ":" + velocity.getY(), position.getX() + 6, position.getY() + fontOffsetY);

    }

    private void draw(GraphicsContext gc, BobVector value, Color color) {
        gc.setStroke(color);        
        gc.setLineWidth(1);
        BobVector posCopy = new BobVector(position);
        BobVector valCopy = new BobVector(value);
        valCopy.multiply(10);
        posCopy.add(valCopy);
        // direction.multiply(3d);
        gc.strokeLine(position.getX(), position.getY(), posCopy.getX(), posCopy.getY());
    }
    
    public BobVector getPosition() {
        return position;
    }
}