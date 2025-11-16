package com.ferra13671.gltextureutils.builder;

import com.ferra13671.gltextureutils.ColorMode;
import com.ferra13671.gltextureutils.GLTexture;
import com.ferra13671.gltextureutils.TextureFiltering;
import com.ferra13671.gltextureutils.TextureWrapping;
import com.ferra13671.gltextureutils.loader.TextureLoader;

/**
 * An object that represents a texture builder.
 *
 * @param <T> the type of object that is a path to image.
 *
 * @see GLTexture
 */
public class GLTextureBuilder<T> {
    /** Texture name. **/
    private String name = null;
    /** Path to image. **/
    private T path = null;
    /** Texture loader. **/
    private final TextureLoader<T> loader;
    /** Pixels color mode. **/
    private ColorMode colorMode = ColorMode.RGBA;
    /** Texture filtering mode. **/
    private TextureFiltering filtering = null;
    /** Texture wrapping mode. **/
    private TextureWrapping wrapping = null;

    /**
     * @param loader texture loader.
     *
     * @see TextureLoader
     */
    public GLTextureBuilder(TextureLoader<T> loader) {
        this.loader = loader;
    }

    /**
     * Sets the texture name.
     *
     * @param name texture name.
     * @return texture builder.
     */
    public GLTextureBuilder<T> name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the path to image.
     *
     * @param path path to image.
     * @return texture builder.
     */
    public GLTextureBuilder<T> path(T path) {
        this.path = path;
        return this;
    }

    /**
     * Sets the pixels color mode.
     *
     * @param colorMode pixels color mode.
     * @return texture builder.
     */
    public GLTextureBuilder<T> colorMode(ColorMode colorMode) {
        this.colorMode = colorMode;
        return this;
    }

    /**
     * Sets the texture filtering mode.
     *
     * @param filtering texture filtering mode.
     * @return texture builder.
     */
    public GLTextureBuilder<T> filtering(TextureFiltering filtering) {
        this.filtering = filtering;
        return this;
    }

    /**
     * Sets the texture wrapping mode.
     *
     * @param wrapping texture wrapping mode.
     * @return texture builder.
     */
    public GLTextureBuilder<T> wrapping(TextureWrapping wrapping) {
        this.wrapping = wrapping;
        return this;
    }

    /**
     * Creates a new texture from all the information in the builder.
     *
     * @return new texture.
     *
     * @see GLTexture
     */
    public GLTexture build() {
        try {
            checkArguments();
            return GLTexture.of(name, loader.load(path, colorMode, filtering, wrapping));
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
        if (colorMode == null)
            throw new IllegalArgumentException(String.format("ColorMode in texture '%s' cannot be null.", name));
        if (filtering == null)
            throw new IllegalArgumentException(String.format("TextureFiltering in texture '%s' cannot be null.", name));
        if (wrapping == null)
            throw new IllegalArgumentException(String.format("TextureWrapping in texture '%s' cannot be null.", name));
    }
}
