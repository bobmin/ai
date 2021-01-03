package bob.fxg;

public class BobContext {

    public int WIDTH = 600;

    public int HEIGHT = 600;    

    /** die Mausposition */
    public BobVector mouse = new BobVector(0, 0);

    public boolean pause = false;

    public boolean debug = false;

    /** Beschreibt den Zustand der Shift-Taste (gedrückt ja/nein). */
    public boolean shift = false;

}