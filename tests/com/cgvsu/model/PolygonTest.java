package com.cgvsu.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PolygonTest {

    @Test
    void testCreatesPolygonWithConsistentIndices() {
        Polygon polygon = new Polygon(
                List.of(0, 1, 2),
                List.of(2, 1, 0),
                List.of(0, 0, 0));

        assertEquals(List.of(0, 1, 2), polygon.getVertexIndices());
        assertThrows(UnsupportedOperationException.class, () -> polygon.getVertexIndices().add(3));
    }

    @Test
    void testRejectsFewerThanThreeVertices() {
        assertThrows(IllegalArgumentException.class,
                () -> new Polygon(List.of(0, 1), List.of(), List.of()));
    }

    @Test
    void testRejectsPartiallySpecifiedOptionalIndices() {
        assertThrows(IllegalArgumentException.class,
                () -> new Polygon(List.of(0, 1, 2), List.of(0, 1), List.of()));
        assertThrows(IllegalArgumentException.class,
                () -> new Polygon(List.of(0, 1, 2), List.of(), List.of(0, 1)));
    }

    @Test
    void testRejectsNegativeAndNullIndices() {
        assertThrows(IllegalArgumentException.class,
                () -> new Polygon(List.of(0, -1, 2), List.of(), List.of()));
        assertThrows(NullPointerException.class,
                () -> new Polygon(Arrays.asList(0, null, 2), List.of(), List.of()));
        assertThrows(NullPointerException.class,
                () -> new Polygon(null, List.of(), List.of()));
        assertThrows(NullPointerException.class,
                () -> new Polygon(List.of(0, 1, 2), null, List.of()));
        assertThrows(NullPointerException.class,
                () -> new Polygon(List.of(0, 1, 2), List.of(), null));
    }

    @Test
    void testCopiesInputLists() {
        List<Integer> source = new ArrayList<>(List.of(0, 1, 2));
        Polygon polygon = new Polygon(source, List.of(), List.of());

        source.set(0, 10);

        assertEquals(List.of(0, 1, 2), polygon.getVertexIndices());
    }
}
