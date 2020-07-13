import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BobMover {

    private final String name;

    private final BobVector position;
    
    private final BobVector velocity;

    public BobMover(String name, int x, int y) {
        this.name = name;
        position = new BobVector(x, y);
        velocity = new BobVector(BobUtil.random(5), BobUtil.random(5));
    }

    public void update(int width, int height) {
        position.add(velocity);
        if (position.x  > width) {
            position.x = 0;
        } else if (position.x < 0) {
            position.x = width;
        }
        if (position.y > height) {
            position.y = 0;
        } else if (position.y < 0) {
            position.y = height;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.LIMEGREEN);
        gc.setLineWidth(1);
        // der Punkt
        gc.fillOval(position.x - 5, position.y - 5, 10, 10);
        // die Beschriftung
        Font font = Font.font( "Helvetica", FontWeight.BOLD, 12);
        gc.setFont(font);
        gc.fillText(name, position.x + 6, position.y + 9);
    }
    
}