package bob.fxg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;

public class BobMover {

    private final String name;
    
    private final double maxSpeed;
    
    private final BobVector position;
    
    private final BobVector velocity;

    private final BobVector acceleration;

    private final BobVector lastAcceleration;

    public BobMover(String name, int x, int y, double maxSpeed) {
        this.name = name;
        this.maxSpeed = maxSpeed;
        position = new BobVector(x, y);
        velocity = new BobVector(0, 0);
        acceleration = new BobVector(0, 0);
        lastAcceleration = new BobVector(0, 0);
    }

    public void applyForce(BobVector value) {
        acceleration.add(value);
    }

    public void moveWithin(int width, int height) {
        // Objekt bewegen
        velocity.add(acceleration);
        if (velocity.magnitude() > maxSpeed) {
            velocity.normalize();
            velocity.multiply(maxSpeed);
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
        gc.fillText(position.getDisplay(), position.getX() + 6, position.getY() + fontOffsetY);

        fontOffsetY += fm.getLineHeight();
        gc.fillText(velocity.getDisplay(), position.getX() + 6, position.getY() + fontOffsetY);

        fontOffsetY += fm.getLineHeight();
        gc.fillText(lastAcceleration.getDisplay(), position.getX() + 6, position.getY() + fontOffsetY);

    }

    private void draw(GraphicsContext gc, BobVector value, Color color) {
        gc.setStroke(color);        
        gc.setLineWidth(1);
        BobVector positionCopy = new BobVector(position);
        BobVector valueCopy = new BobVector(value);
        valueCopy.multiply(10);
        positionCopy.add(valueCopy);
        // direction.multiply(3d);
        gc.strokeLine(position.getX(), position.getY(), positionCopy.getX(), positionCopy.getY());
    }
    
    public BobVector getPosition() {
        return position;
    }
}