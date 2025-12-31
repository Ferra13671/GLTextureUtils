package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.PathMode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An object representing a file path. Used in {@link TextureLoaders#FILE_ENTRY} and {@link GifLoaders#FILE_ENTRY}.
 */
@AllArgsConstructor
@Getter
public class FileEntry {
    /** File path. **/
    private final String path;
    /** File path mode. **/
    private final PathMode pathMode;
}
