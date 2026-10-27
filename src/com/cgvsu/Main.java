package com.cgvsu;

import com.cgvsu.model.Model;
import com.cgvsu.obj.ObjReader;
import com.cgvsu.obj.ObjWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {

        Path fileName = Path.of("../../3DModels/Faceform/WrapHead.obj");
        String fileContent = Files.readString(fileName);

        System.out.println("Loading model ...");
        Model model = ObjReader.read(fileContent);
        String objString = ObjWriter.write(model, 6);
        Files.writeString(Path.of("test.obj"), objString);
    }
}
