package com.cgvsu.obj;

public class ObjWriterException extends IllegalArgumentException {

    public ObjWriterException(String message) {
        super("Failed to write OBJ model. " + message);
    }
}
