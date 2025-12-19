package com.ferra13671.gltextureutils.builder;

import com.ferra13671.gltextureutils.*;
import com.ferra13671.gltextureutils.loader.GifLoader;

/**
 * An object that represents a gif builder.
 *
 * @param <T> the type of object that is a path to gif.
 *
 * @see GLGif
 */
public class GLGifBuilder<T> {
    /** Gif name. **/
    private String name = null;
    /** Path to gif. **/
    private T path = null;
    /** Gif loader. **/
    private final GifLoader<T> loader;
    /** Texture filtering mode. **/
    private TextureFiltering filtering = null;
    /** Texture wrapping mode. **/
    private TextureWrapping wrapping = null;

    /**
     * @param loader gif loader.
     */
    public GLGifBuilder(GifLoader<T> loader) {
        this.loader = loader;
    }

    /**
     * Sets the gif name.
     *
     * @param name gif name.
     * @return gif builder.
     */
    public GLGifBuilder<T> name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the path to load gif information.
     *
     * @param path path to gif.
     * @return gif builder.
     */
    public GLGifBuilder<T> info(T path) {
        this.path = path;
        return this;
    }

    /**
     * Sets the texture filtering mode.
     *
     * @param filtering texture filtering mode.
     * @return gif builder.
     */
    public GLGifBuilder<T> filtering(TextureFiltering filtering) {
        this.filtering = filtering;
        return this;
    }

    /**
     * Sets the texture wrapping mode.
     *
     * @param wrapping texture wrapping mode.
     * @return gif builder.
     */
    public GLGifBuilder<T> wrapping(TextureWrapping wrapping) {
        this.wrapping = wrapping;
        return this;
    }

    /**
     * Creates a new gif from all the information in the builder.
     *
     * @return new gif.
     *
     * @see GLGif
     */
    public GLGif build() {
        try {
            checkArguments();
            return new GLGif(this.name, this.loader.load(this.path, this.filtering, this.wrapping));
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private void checkArguments() {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null.");
        if (loader == null)
            throw new IllegalArgumentException(String.format("Loader in texture '%s' cannot be null.", name));
        if (path == null)
            throw new IllegalArgumentException(String.format("Path in texture '%s' cannot be null.", name));
        if (filtering == null)
            throw new IllegalArgumentException(String.format("TextureFiltering in texture '%s' cannot be null.", name));
        if (wrapping == null)
            throw new IllegalArgumentException(String.format("TextureWrapping in texture '%s' cannot be null.", name));
    }
}
