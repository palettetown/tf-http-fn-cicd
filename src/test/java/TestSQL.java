import org.junit.jupiter.api.Test;

import functions.HelloWorld;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSQL {

    @Test
    public void testLucky() {
        assertEquals(8, HelloWorld.getDataCount());
    }

}