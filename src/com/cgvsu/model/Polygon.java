package com.cgvsu.model;

import java.util.List;
import java.util.Objects;

public final class Polygon {
    private final List<Integer> vertexIndices;
    private final List<Integer> textureVertexIndices;
    private final List<Integer> normalIndices;

    public Polygon(
            List<Integer> vertexIndices,
            List<Integer> textureVertexIndices,
            List<Integer> normalIndices) {
        this.vertexIndices = copyIndices(vertexIndices, "Vertex");
        this.textureVertexIndices = copyIndices(textureVertexIndices, "Texture vertex");
        this.normalIndices = copyIndices(normalIndices, "Normal");

        if (this.vertexIndices.size() < 3) {
            throw new IllegalArgumentException("A polygon must contain at least three vertex indices.");
        }
        requireOptionalIndicesMatch(this.textureVertexIndices, "texture vertex");
        requireOptionalIndicesMatch(this.normalIndices, "normal");
    }

    public List<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public List<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public List<Integer> getNormalIndices() {
        return normalIndices;
    }

    private List<Integer> copyIndices(List<Integer> indices, String type) {
        Objects.requireNonNull(indices, type + " indices must not be null.");
        for (Integer index : indices) {
            Objects.requireNonNull(index, type + " indices must not be null.");
            if (index < 0) {
                throw new IllegalArgumentException(type + " indices must be non-negative.");
            }
        }
        return List.copyOf(indices);
    }

    private void requireOptionalIndicesMatch(List<Integer> indices, String type) {
        if (!indices.isEmpty() && indices.size() != vertexIndices.size()) {
            throw new IllegalArgumentException(
                    "A polygon must contain either no " + type + " indices or one for every vertex.");
        }
    }
}
