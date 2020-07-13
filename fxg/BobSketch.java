import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BobSketch {

    BobMover[] mover = new BobMover[10];

    public void setup(BobContext context) {
        for (int i=0; i<10; i++) {
            int x = (int) (Math.random() * context.WIDTH);
            int y = (int) (Math.random() * context.HEIGHT);
            mover[i] = new BobMover(String.valueOf(i), x, y);
        }
    }

    public void update(BobContext context) {
        for (int i=0; i<10; i++) {
            BobMover m = mover[i];
            m.update(context.WIDTH, context.HEIGHT);
        }
    }

    public void draw(BobContext context, GraphicsContext gc) {
        gc.setFill(Color.RED);
        for (int i=0; i<10; i++) {
            BobMover m = mover[i];
            m.draw(gc);
        }
    }

}