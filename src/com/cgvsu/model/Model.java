package com.cgvsu.model;
import com.cgvsu.math.Vector2;
import com.cgvsu.math.Vector3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Model {
    private final List<Vector3> vertices = new ArrayList<>();
    private final List<Vector2> textureVertices = new ArrayList<>();
    private final List<Vector3> normals = new ArrayList<>();
    private final List<Polygon> polygons = new ArrayList<>();

    public void addVertex(Vector3 vertex) {
        vertices.add(Objects.requireNonNull(vertex, "Vertex must not be null."));
    }

    public void addTextureVertex(Vector2 textureVertex) {
        textureVertices.add(Objects.requireNonNull(textureVertex, "Texture vertex must not be null."));
    }

    public void addNormal(Vector3 normal) {
        normals.add(Objects.requireNonNull(normal, "Normal must not be null."));
    }

    public void addPolygon(Polygon polygon) {
        Objects.requireNonNull(polygon, "Polygon must not be null.");
        requireIndicesInRange(polygon.getVertexIndices(), vertices.size(), "vertex");
        requireIndicesInRange(polygon.getTextureVertexIndices(), textureVertices.size(), "texture vertex");
        requireIndicesInRange(polygon.getNormalIndices(), normals.size(), "normal");
        polygons.add(polygon);
    }

    public List<Vector3> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public List<Vector2> getTextureVertices() {
        return Collections.unmodifiableList(textureVertices);
    }

    public List<Vector3> getNormals() {
        return Collections.unmodifiableList(normals);
    }

    public List<Polygon> getPolygons() {
        return Collections.unmodifiableList(polygons);
    }

    private void requireIndicesInRange(List<Integer> indices, int upperBound, String type) {
        for (Integer index : indices) {
            if (index >= upperBound) {
                throw new IllegalArgumentException(
                        "Polygon " + type + " index " + index + " is outside model data.");
            }
        }
    }
}
