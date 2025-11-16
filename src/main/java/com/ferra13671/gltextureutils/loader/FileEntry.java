package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.PathMode;

/**
 * An object representing a file path. Used in {@link TextureLoaders#FILE_ENTRY} and {@link GifLoaders#FILE_ENTRY}.
 */
public class FileEntry {
    /** File path. **/
    private final String path;
    /** File path mode. **/
    private final PathMode pathMode;

    /**
     * @param path file path.
     * @param pathMode file path mode.
     */
    public FileEntry(String path, PathMode pathMode) {
        this.path = path;
        this.pathMode = pathMode;
    }

    /**
     * Returns the file path.
     *
     * @return file path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Return the file path mode.
     *
     * @return file path mode.
     */
    public PathMode getPathMode() {
        return pathMode;
    }
}
