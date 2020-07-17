package bob.fxg;

/**
 * Ein Punkt im 2D-Raum.
 * 
 * @author maik@btmx.net
 * 
 */
public class BobVector {

    /** der X-Wert */
    private double x = 0d;

    /** der Y-Wert */
    private double y = 0d;

    /**
     * Instanziiert einen Punkt im 2D-Raum.
     * @param x der X-Wert
     * @param y der Y-Wert
     */
    public BobVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public BobVector(BobVector other) {
        this.x = other.x;
        this.y = other.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double value) {
        this.x = value;
    }

    public double getY() {
        return y;
    }

    public void setY(double value) {
        this.y = value;
    }

    public void set(BobVector value) {
        this.x = value.x;
        this.y = value.y;
    }

    public void multiply(double value) {
        x *= value;
        y *= value;
    }

    public void add(BobVector value) {
        x += value.x;
        y += value.y;
    }    

    public BobVector subtract(BobVector value) {        
        double otherX = this.x - value.x;
        double otherY = this.y - value.y;
        return new BobVector(otherX, otherY);
    }

    /**
     * Liefert die Länge vom Vektor.
     * @return eine Zahl
     */
    public double magnitude() {
        double xx = (x * x);
        double yy = (y * y);
        return Math.sqrt(xx + yy);
    }

    public void normalize() {
        double m = magnitude();
        this.x = (x / m);
        this.y = (y / m);
    }

}