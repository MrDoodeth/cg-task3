package com.cgvsu.obj;

import com.cgvsu.math.Vector2;
import com.cgvsu.math.Vector3;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.List;
import java.util.Locale;

public final class ObjWriter {

    private static final char LINE_SEPARATOR = '\n';

    //Нужно, чтобы не создавали утилитарный класс от балды
    private ObjWriter() {
    }

    public static String write(Model model, int precision) {
        if (model == null) {
            throw new ObjWriterException("Model must not be null.");
        }
        if (precision < 0) {
            throw new ObjWriterException("Precision must not be negative.");
        }

        StringBuilder result = new StringBuilder();
        appendVector3List(result, Obj.VERTEX_TOKEN, model.getVertices(), precision);
        appendVector2List(result, Obj.TEXTURE_TOKEN, model.getTextureVertices(), precision);
        appendVector3List(result, Obj.NORMAL_TOKEN, model.getNormals(), precision);
        appendPolygons(result, Obj.FACE_TOKEN, model.getPolygons());

        return result.toString();
    }

    private static void appendVector3List(
            StringBuilder result,
            String token,
            List<Vector3> vectors,
            int precision) {
        for (Vector3 vector : vectors) {
            result.append(token).append(' ')
                    .append(format(vector.getX(), precision)).append(' ')
                    .append(format(vector.getY(), precision)).append(' ')
                    .append(format(vector.getZ(), precision)).append(LINE_SEPARATOR);
        }
    }

    private static void appendVector2List(
            StringBuilder result,
            String token,
            List<Vector2> vectors,
            int precision) {
        for (Vector2 vector : vectors) {
            result.append(token).append(' ')
                    .append(format(vector.getX(), precision)).append(' ')
                    .append(format(vector.getY(), precision)).append(LINE_SEPARATOR);
        }
    }

    private static void appendPolygons(StringBuilder result, String token, List<Polygon> polygons) {
        for (Polygon polygon : polygons) {
            result.append(token);
            appendPolygonElements(result, polygon);
            result.append(LINE_SEPARATOR);
        }
    }

    private static void appendPolygonElements(StringBuilder result, Polygon polygon) {
        List<Integer> vertexIndices = polygon.getVertexIndices();
        List<Integer> textureIndices = polygon.getTextureVertexIndices();
        List<Integer> normalIndices = polygon.getNormalIndices();
        boolean hasTextures = !textureIndices.isEmpty();
        boolean hasNormals = !normalIndices.isEmpty();

        for (int index = 0; index < vertexIndices.size(); index++) {
            result.append(' ')
                    .append(toObjIndex(vertexIndices.get(index)));
            if (hasTextures) {
                result.append('/')
                        .append(toObjIndex(textureIndices.get(index)));
            } else if (hasNormals) {
                result.append('/');
            }

            if (hasNormals) {
                result.append('/').append(toObjIndex(normalIndices.get(index)));
            }
        }
    }

    private static int toObjIndex(int modelIndex) {
        return modelIndex + 1;
    }

    private static String format(float value, int precision) {
        return String.format(Locale.ROOT, "%." + precision + "f", value);
    }
}
