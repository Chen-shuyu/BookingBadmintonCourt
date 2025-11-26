package com.Taifex.utility;

// File: ProfileCopier.java
import java.io.*;
import java.nio.file.*;

public class ProfileCopier {

    public static void copyProfile(String src, String dest) throws Exception {
        Path from = Paths.get(src);
        Path to = Paths.get(dest);

        if (Files.exists(to)) {
            return;
        }

        Files.walk(from).forEach(path -> {
            try {
                Path target = to.resolve(from.relativize(path).toString());
                if (Files.isDirectory(path)) {
                    Files.createDirectories(target);
                } else {
                    Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

