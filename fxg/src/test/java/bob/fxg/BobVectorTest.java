package bob.fxg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BobVectorTest {
    
    @Test
    public void testMagnitude() {
        BobVector x = new BobVector(4, 2);
        double m = x.magnitude();
        System.out.println("magnitude[4,2] = " + m);
        assertEquals(4.47213595499958d, m);
    }

}