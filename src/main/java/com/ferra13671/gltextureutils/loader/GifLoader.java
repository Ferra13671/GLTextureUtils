package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.GLGif;
import com.ferra13671.gltextureutils.builder.GLGifBuilder;

import java.io.InputStream;
import java.net.URI;

/**
 * An object that represents a loader of gif information for further use.
 *
 * @param <T> the type of object that is a path to gif.
 *
 * @see GLGif
 */
public abstract class GifLoader<T> {
    public static final GifLoader<InputStream> INPUT_STREAM = new GifLoader<>() {
        @Override
        public InputStream load(InputStream path) {
            return path;
        }
    };
    public static final GifLoader<URI> URI = new GifLoader<>() {
        @Override
        public InputStream load(URI path) throws Exception {
            return path.toURL().openStream();
        }
    };
    public static final GifLoader<FileEntry> FILE_ENTRY = new GifLoader<>() {
        @Override
        public InputStream load(FileEntry path) {
            return path.pathMode().streamCreateFunction.apply(path.path());
        }
    };

    /**
     * Loads various data into the gif information.
     *
     * @param path path to gif.
     * @return gif InputStream.
     * @throws Exception various errors that may occur during loading.
     */
    public abstract InputStream load(T path) throws Exception;

    /**
     * Creates a new gif builder that will use this gif loader.
     *
     * @return new gif builder.
     *
     * @see GLGifBuilder
     */
    public GLGifBuilder<T> createGifBuilder() {
        return new GLGifBuilder<>(this);
    }
}
