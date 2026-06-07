package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.PathMode;

/**
 * An object representing a file path. Used in {@link TextureLoaders#FILE_ENTRY} and {@link GifLoaders#FILE_ENTRY}.
 *
 * @param path     File path.
 * @param pathMode File path mode.
 */
public record FileEntry(String path, PathMode pathMode) {
}
