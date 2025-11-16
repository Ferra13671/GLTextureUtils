package com.ferra13671.gltextureutils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import com.ferra13671.gltextureutils.loader.FileEntry;

/**
 * The mode of the path used in FileEntry.
 *
 * @see FileEntry
 */
public enum PathMode {
    /**
     * The mode in which the path is treated as a path in a jar file.
     */
    INSIDE_JAR(path ->
        PathMode.class.getClassLoader().getResourceAsStream(path)
    ),
    /**
     * The mode in which the path is treated as a path to a specific file.
     */
    OUTSIDE_JAR(path -> {
        try {
            return Files.newInputStream(Paths.get(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });

    public final Function<String, InputStream> streamCreateFunction;

    PathMode(Function<String, InputStream> streamCreateFunction) {
        this.streamCreateFunction = streamCreateFunction;
    }
}
