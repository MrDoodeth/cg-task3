package com.cgvsu.math;

public final class Vector2 extends Vector {
    private final float x;
    private final float y;

    public Vector2(float x, float y) {
        requireFinite(x, y);
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector2 vector)) {
            return false;
        }
        return approximatelyEquals(x, vector.x) && approximatelyEquals(y, vector.y);
    }
}
