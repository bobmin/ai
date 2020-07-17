package bob.fxg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BobSketch {

    private static final int COUNT = 3;

    /** die bewegten Punkten */
    private BobMover[] mover = new BobMover[COUNT];

    /** die anziehende Kraft */
    private BobVector goal = new BobVector(0, 0);

    public void setup(BobContext context) {
        for (int i=0; i<COUNT; i++) {
            int x = (int) (Math.random() * context.WIDTH);
            int y = (int) (Math.random() * context.HEIGHT);
            mover[i] = new BobMover(String.valueOf(i), x, y);
        }
    }

    public void update(BobContext context) {
        // die Kraft
        goal.set(context.mouse);
        // die Punkte
        for (int i=0; i<COUNT; i++) {
            BobMover m = mover[i];
            // ausrichten
            if (goal.getY() != 0 && goal.getY() != 0) {
                BobVector attraction = goal.subtract(m.getPosition());
                attraction.normalize();
                attraction.multiply(3);
                m.applyForce(attraction);
            }
            // bewegen
            m.moveWithin(context.WIDTH, context.HEIGHT);
        }
    }

    public void draw(BobContext context, GraphicsContext gc) {
        for (int i=0; i<COUNT; i++) {
            BobMover m = mover[i];
            m.draw(gc);
        }
        gc.setFill(Color.BLUE);
        gc.fillRect(goal.getX() - 10, goal.getY() - 10, 10, 10);
    }

}