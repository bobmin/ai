package bob.fxg;

public class BobUtil {

    public static int random(int min, int max) {
        double r = Math.random();        
        return (int) (min + ((max - min) * r));
    }

    public static int random(int max) {
        int v = BobUtil.random(1, max);
        double r = Math.random();
        return (r > 0.5 ? v : v * -1);
    }

}