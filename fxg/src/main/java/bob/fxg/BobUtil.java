package bob.fxg;

public class BobUtil {

    public static double random(int min, int max) {
        double r = Math.random();        
        return (min + ((max - min) * r));
    }

    /**
     * Liefert eine Zufallszahl von ein bis <max>.
     * @param max das Maximum
     * @return eine Zahl
     */
    public static double random(int max) {
        double v = BobUtil.random(0, Math.abs(max));
        double r = Math.random();
        return (r > 0.5 ? v : v * -1);
    }

}