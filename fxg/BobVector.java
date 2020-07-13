public class BobVector {

    public int x = 0;

    public int y = 0;

    public BobVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(BobVector value) {
        x += value.x;
        y += value.y;
    }

}