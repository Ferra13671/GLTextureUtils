package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.GLGif;
import com.ferra13671.gltextureutils.builder.GLGifBuilder;
import com.ferra13671.gltextureutils.builder.GLGifInfo;
import com.ferra13671.gltextureutils.TextureFiltering;
import com.ferra13671.gltextureutils.TextureWrapping;

/**
 * An object that represents a loader of gif information for further use.
 *
 * @param <T> the type of object that is a path to gif.
 *
 * @see GLGif
 * @see GifLoaders
 */
public abstract class GifLoader<T> {

    /**
     * Loads various data into the gif information.
     *
     * @param path path to gif.
     * @param filtering texture filtering mode.
     * @param wrapping openGL behavior mode when getting a pixel color outside the range [0, 1].
     * @return gif information.
     * @throws Exception various errors that may occur during loading.
     *
     * @see GLGifInfo
     */
    public abstract GLGifInfo load(T path, TextureFiltering filtering, TextureWrapping wrapping) throws Exception;

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
