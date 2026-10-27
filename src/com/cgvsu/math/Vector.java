package com.cgvsu.math;

public sealed abstract class Vector permits Vector2, Vector3 {
    private static final float EPSILON = 1e-6f;

    protected static void requireFinite(float... coordinates) {
        for (float coordinate : coordinates) {
            if (!Float.isFinite(coordinate)) {
                throw new IllegalArgumentException("Vector coordinates must be finite.");
            }
        }
    }

    protected static boolean approximatelyEquals(float first, float second) {
        return Math.abs(first - second) <= EPSILON;
    }
}
