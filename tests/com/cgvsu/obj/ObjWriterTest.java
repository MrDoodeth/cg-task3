package com.cgvsu.obj;

import com.cgvsu.math.Vector2;
import com.cgvsu.math.Vector3;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ObjWriterTest {

    private static final int PRECISION = 6;
    private static final String COMPLETE_OBJ = """
            v 0.000000 0.000000 0.000000
            v 1.000000 0.000000 0.000000
            v 0.000000 1.000000 0.000000
            vt 0.000000 0.000000
            vt 1.000000 0.000000
            vt 0.000000 1.000000
            vn 0.000000 0.000000 1.000000
            f 1 2 3
            f 1/3 2/2 3/1
            f 1//1 2//1 3//1
            f 1/3/1 2/2/1 3/1/1
            """;

    @Test
    void testWritesEmptyModelAsEmptyString() {
        assertEquals("", ObjWriter.write(new Model(), PRECISION));
    }

    @Test
    void testWritesAllSupportedFaceForms() {
        Model model = completeTriangleModel();
        model.addPolygon(polygon(indices(0, 1, 2), indices(), indices()));
        model.addPolygon(polygon(indices(0, 1, 2), indices(2, 1, 0), indices()));
        model.addPolygon(polygon(indices(0, 1, 2), indices(), indices(0, 0, 0)));
        model.addPolygon(polygon(indices(0, 1, 2), indices(2, 1, 0), indices(0, 0, 0)));

        assertEquals(COMPLETE_OBJ, ObjWriter.write(model, PRECISION));
    }

    @Test
    void testWritesPolygonWithMoreThanThreeVerticesWithoutTriangulation() {
        Model model = new Model();
        model.addVertex(new Vector3(0, 0, 0));
        model.addVertex(new Vector3(1, 0, 0));
        model.addVertex(new Vector3(1, 1, 0));
        model.addVertex(new Vector3(0, 1, 0));
        model.addPolygon(polygon(indices(0, 1, 2, 3), indices(), indices()));

        assertEquals("""
                v 0.000000 0.000000 0.000000
                v 1.000000 0.000000 0.000000
                v 1.000000 1.000000 0.000000
                v 0.000000 1.000000 0.000000
                f 1 2 3 4
                """, ObjWriter.write(model, PRECISION));
    }

    @Test
    void testNumberFormattingDoesNotDependOnDefaultLocale() {
        Locale previousLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.GERMANY);
            Model model = new Model();
            model.addVertex(new Vector3(1.5f, -2.25f, -0.0f));

            assertEquals("v 1.500000 -2.250000 -0.000000\n", ObjWriter.write(model, PRECISION));
        } finally {
            Locale.setDefault(previousLocale);
        }
    }

    @Test
    void testRoundsCoordinatesToSixDecimalPlaces() {
        Model model = new Model();
        model.addVertex(new Vector3(1.2345678f, -1.2345678f, 0));

        assertEquals(
                "v 1.234568 -1.234568 0.000000\n",
                ObjWriter.write(model, PRECISION));
    }

    @Test
    void testRejectsNullModel() {
        ObjWriterException exception = assertThrows(
                ObjWriterException.class,
                () -> ObjWriter.write(null, PRECISION));

        assertEquals("Failed to write OBJ model. Model must not be null.", exception.getMessage());
    }

    @Test
    void testWriterAndReaderRoundTripKeepsCanonicalRepresentation() {
        Model model = completeTriangleModel();
        model.addPolygon(polygon(indices(0, 1, 2), indices(0, 1, 2), indices(0, 0, 0)));
        String serialized = ObjWriter.write(model, PRECISION);

        Model restored = ObjReader.read(serialized);

        assertEquals(serialized, ObjWriter.write(restored, PRECISION));
    }

    @Test
    void testReaderAndWriterPreserveCanonicalObjWithEveryFaceForm() {
        Model model = ObjReader.read(COMPLETE_OBJ);

        assertEquals(COMPLETE_OBJ, ObjWriter.write(model, PRECISION));
    }

    @Test
    void testRejectsNegativePrecision() {
        ObjWriterException exception = assertThrows(
                ObjWriterException.class,
                () -> ObjWriter.write(new Model(), -1));

        assertEquals("Failed to write OBJ model. Precision must not be negative.", exception.getMessage());
    }

    private static Model completeTriangleModel() {
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

    private static Polygon polygon(
            List<Integer> vertexIndices,
            List<Integer> textureIndices,
            List<Integer> normalIndices) {
        return new Polygon(vertexIndices, textureIndices, normalIndices);
    }

    private static List<Integer> indices(Integer... values) {
        return List.of(values);
    }
}
