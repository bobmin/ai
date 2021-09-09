package bob.fxg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BobSketchImpl implements BobSketch {

    private static final int COUNT = 3;
    private static final int MAX_SPEED = 10;
    private static final int MAX_IMPULS = 3;

    /** die bewegten Punkten */
    private BobMover[] mover = new BobMover[COUNT];

    /** die anziehende Kraft */
    private BobVector goal = new BobVector(0, 0);

    public void setup(BobContext context) {
        for (int i=0; i<COUNT; i++) {
            int x = (int) (Math.random() * context.WIDTH);
            int y = (int) (Math.random() * context.HEIGHT);
            mover[i] = new BobMover(
                    String.valueOf(i), x, y, BobUtil.random(1, MAX_SPEED));
            BobVector impuls = new BobVector(
                    BobUtil.random(MAX_IMPULS),
                    BobUtil.random(MAX_IMPULS));
            System.out.printf("[%d] impuls %s%n", i, impuls.getDisplay());
            mover[i].applyForce(impuls); 
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
                attraction.multiply(0.3);
                m.applyForce(attraction);
            }
            // bewegen (im Rahmen von Breite und Höhe)
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

	public void select(double x, double y) {
        for (int i=0; i<COUNT; i++) {
            BobMover m = mover[i];
            if (m.contains(x, y)) {
                m.setSelected(true);
            } else {
                m.setSelected(false);
            }
        }
    }
    
}
