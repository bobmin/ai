package bob.fxg;

import javafx.scene.canvas.GraphicsContext;

public interface BobSketch {

    /**
     * Wird beim Programmstart ausgeführt.
     * 
     * @param context die Programmumgebung
     */
    public void setup(BobContext context);

    /**
     * Wird bei jedem Programmdurchlauf ausgeführt und fordert zur Berechnung
     * eines neuen/aktuellen Zustands auf.
     * 
     * @param context die aktuelle Programmumgebung
     */
    public void update(BobContext context);

    /**
     * Wird bei jedem Programmdurchlauf ausgeführt und fordert die Zeichnung
     * des aktuellen Zustands an.
     * 
     * @param context die aktuelle Programmumgebung
     */
    public void draw(BobContext context, GraphicsContext gc);
    
}
