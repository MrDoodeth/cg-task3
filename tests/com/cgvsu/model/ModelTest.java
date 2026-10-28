package com.cgvsu.model;

import com.cgvsu.math.Vector2;
import com.cgvsu.math.Vector3;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModelTest {

    @Test
    void testAcceptsPolygonWhoseIndicesReferenceModelData() {
        Model model = completeModel();
        model.addPolygon(new Polygon(List.of(0, 1, 2), List.of(0, 1, 2), List.of(0, 0, 0)));

        assertEquals(1, model.getPolygons().size());
    }

    @Test
    void testRejectsNullData() {
        Model model = new Model();

        assertThrows(NullPointerException.class, () -> model.addVertex(null));
        assertThrows(NullPointerException.class, () -> model.addTextureVertex(null));
        assertThrows(NullPointerException.class, () -> model.addNormal(null));
        assertThrows(NullPointerException.class, () -> model.addPolygon(null));
    }

    @Test
    void testRejectsPolygonIndicesOutsideModelData() {
        Model model = completeModel();

        assertThrows(IllegalArgumentException.class,
                () -> model.addPolygon(new Polygon(List.of(0, 1, 3), List.of(), List.of())));
        assertThrows(IllegalArgumentException.class,
                () -> model.addPolygon(new Polygon(List.of(0, 1, 2), List.of(0, 1, 3), List.of())));
        assertThrows(IllegalArgumentException.class,
                () -> model.addPolygon(new Polygon(List.of(0, 1, 2), List.of(), List.of(0, 0, 1))));
        assertEquals(0, model.getPolygons().size());
    }

    @Test
    void testExposesReadOnlyCollections() {
        Model model = completeModel();

        assertThrows(UnsupportedOperationException.class,
                () -> model.getVertices().add(new Vector3(3, 3, 3)));
    }

    private static Model completeModel() {
        Model model = new Model();
        model.addVertex(new Vector3(0, 0, 0));
        model.addVertex(new Vector3(1, 0, 0));
        model.addVertex(new Vector3(0, 1, 0));
        model.addTextureVertex(new Vector2(0, 0));
        model.addTextureVertex(new Vector2(1, 0));
        model.addTextureVertex(new Vector2(0, 1));
        model.addNormal(new Vector3(0, 0, 1));
        return model;
    }
}
