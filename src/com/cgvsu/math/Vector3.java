package com.cgvsu.math;

public final class Vector3 extends Vector {
    private final float x;
    private final float y;
    private final float z;

    public Vector3(float x, float y, float z) {
        requireFinite(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector3 vector)) {
            return false;
        }
        return approximatelyEquals(x, vector.x)
                && approximatelyEquals(y, vector.y)
                && approximatelyEquals(z, vector.z);
    }
}
