package com.cgvsu.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VectorTest {

    @Test
    void testComparesCoordinatesWithTolerance() {
        Vector3 first = new Vector3(1, 2, 3);
        Vector3 second = new Vector3(1 + 0.5e-6f, 2, 3);

        assertEquals(first, second);
    }

    @Test
    void testDistinguishesVectorsOfDifferentDimensions() {
        assertNotEquals(new Vector2(1, 2), new Vector3(1, 2, 0));
        assertNotEquals(new Vector3(1, 2, 0), new Vector2(1, 2));
    }

    @Test
    void testRejectsNonFiniteCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> new Vector2(Float.NaN, 0));
        assertThrows(IllegalArgumentException.class, () -> new Vector3(0, Float.POSITIVE_INFINITY, 0));
        assertThrows(IllegalArgumentException.class, () -> new Vector3(0, 0, Float.NEGATIVE_INFINITY));
    }
}
