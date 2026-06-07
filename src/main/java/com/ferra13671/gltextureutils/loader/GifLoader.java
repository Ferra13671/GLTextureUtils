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
public interface GifLoader<T> {
    GifLoader<InputStream> INPUT_STREAM = path -> path;
    GifLoader<URI> URI = path -> path.toURL().openStream();
    GifLoader<FileEntry> FILE_ENTRY = path -> path.pathMode().streamCreateFunction.apply(path.path());

    /**
     * Loads various data into the gif information.
     *
     * @param path path to gif.
     * @return gif InputStream.
     * @throws Exception various errors that may occur during loading.
     */
    InputStream load(T path) throws Exception;

    /**
     * Creates a new gif builder that will use this gif loader.
     *
     * @return new gif builder.
     *
     * @see GLGifBuilder
     */
    default GLGifBuilder<T> createGifBuilder() {
        return new GLGifBuilder<>(this);
    }
}
