package com.example.service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class FileService {

    public List<String> readAllLine(final String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save(final List<String> lines, final String path) {
        try (FileWriter writer = new FileWriter(path)) {
            for (Iterator<String> it = lines.iterator(); it.hasNext();) {
                writer.write(it.next());
                if (it.hasNext()) {
                    writer.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
