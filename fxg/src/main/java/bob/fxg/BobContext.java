package bob.fxg;

/**
 * Vermittelt zwischen den verschieden Programmteilnehmern.
 */
public class BobContext {

    public enum KeyState {
        OFF("Aus", false),
        OFF_TO_ON("An", true),
        ON("An", true),
        ON_TO_OFF("Aus", false);
        final String label;
        final boolean active;
        KeyState(String label, boolean active) {
            this.label = label;
            this.active = active;
        }
        String getLabel() {
            return label;
        }
        boolean isActive() {
            return active;
        }
        boolean isNotActive() {
            return !isActive();
        }
    }

    public int WIDTH = 600;

    public int HEIGHT = 600;    

    /** die Mausposition */
    public BobVector mouse = new BobVector(0, 0);

    public KeyState pauseState = KeyState.OFF;

    public boolean debug = false;

    /** Beschreibt den Zustand der Shift-Taste (gedrückt ja/nein). */
    public boolean shift = false;

}