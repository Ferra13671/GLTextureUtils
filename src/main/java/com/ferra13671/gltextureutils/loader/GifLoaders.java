package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.GLGif;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.net.URI;

/**
 * A class that stores all the basic gif loaders.
 *
 * @see GifLoader
 * @see GLGif
 */
//TODO move to GifLoader
@UtilityClass
public class GifLoaders {
    public final GifLoader<InputStream> INPUT_STREAM = new GifLoader<>() {
        @Override
        public InputStream load(InputStream path) {
            return path;
        }
    };
    public final GifLoader<URI> URI = new GifLoader<>() {
        @Override
        public InputStream load(URI path) throws Exception {
            return path.toURL().openStream();
        }
    };
    public final GifLoader<FileEntry> FILE_ENTRY = new GifLoader<>() {
        @Override
        public InputStream load(FileEntry path) {
            return path.pathMode().streamCreateFunction.apply(path.path());
        }
    };
}
