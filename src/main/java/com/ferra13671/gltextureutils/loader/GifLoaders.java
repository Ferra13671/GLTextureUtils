package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.GLGif;

import java.io.InputStream;
import java.net.URI;

/**
 * A class that stores all the basic gif loaders.
 *
 * @see GifLoader
 * @see GLGif
 */
public final class GifLoaders {
    public static final GifLoader<InputStream> INPUT_STREAM = new GifLoader<InputStream>() {
        @Override
        public InputStream load(InputStream path) {
            return path;
        }
    };
    public static final GifLoader<URI> URI = new GifLoader<URI>() {
        @Override
        public InputStream load(URI path) throws Exception {
            return path.toURL().openStream();
        }
    };
    public static final GifLoader<FileEntry> FILE_ENTRY = new GifLoader<FileEntry>() {
        @Override
        public InputStream load(FileEntry path) {
            return path.getPathMode().streamCreateFunction.apply(path.getPath());
        }
    };
}
