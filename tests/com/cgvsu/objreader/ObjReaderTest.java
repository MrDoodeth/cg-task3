package com.cgvsu.objreader;

import com.cgvsu.math.Vector3;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ObjReaderTest {

    @Test
    public void testParseVertex01() {
        List<String> wordsInLineWithoutToken = List.of("1.01", "1.02", "1.03");
        Vector3 result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3 expectedResult = new Vector3(1.01f, 1.02f, 1.03f);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testParseVertex02() {
        List<String> wordsInLineWithoutToken = List.of("ab", "o", "ba");
        ObjReaderException exception = assertThrows(
                ObjReaderException.class,
                () -> ObjReader.parseVertex(wordsInLineWithoutToken, 10));

        assertEquals("Error parsing OBJ file on line: 10. Failed to parse float value.", exception.getMessage());
    }

    @Test
    public void testParseVertex03() {
        List<String> wordsInLineWithoutToken = List.of("1.0", "2.0");
        ObjReaderException exception = assertThrows(
                ObjReaderException.class,
                () -> ObjReader.parseVertex(wordsInLineWithoutToken, 10));

        assertEquals("Error parsing OBJ file on line: 10. Too few vertex arguments.", exception.getMessage());
    }

    @Test
    public void testParseVertex04() {
        List<String> wordsInLineWithoutToken = List.of("1.0", "2.0", "3.0", "4.0");
        ObjReaderException exception = assertThrows(
                ObjReaderException.class,
                () -> ObjReader.parseVertex(wordsInLineWithoutToken, 10));

        assertEquals("Error parsing OBJ file on line: 10. Too many vertex arguments.", exception.getMessage());
    }

    @Test
    void testReadsIndentedLinesAndInlineComments() {
        String content = """
                  v 0 0 0
                v 1 0 0 # right vertex
                v 0 1 0

                f 1 2 3
                """;

        var model = ObjReader.read(content);

        assertEquals(3, model.getVertices().size());
        assertEquals(1, model.getPolygons().size());
    }
}
