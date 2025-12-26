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
    /** Image info. **/
    private GLTextureInfo info = null;
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
     * Sets the path to load image information.
     *
     * @param path path to image.
     * @return texture builder.
     */
    public GLTextureBuilder<T> info(T path) {
        return info(path, this.colorMode);
    }

    /**
     * Sets the path to load image information.
     *
     * @param path path to image.
     * @param colorMode pixels color mode.
     * @return texture builder.
     */
    public GLTextureBuilder<T> info(T path, ColorMode colorMode) {
        try {
            this.info = this.loader.load(path, colorMode);
            this.colorMode = colorMode;
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
        return this;
    }

    /**
     * Sets the path to load image information.
     *
     * @param width texture width;
     * @param height texture height;
     * @return texture builder.
     */
    public GLTextureBuilder<T> info(int width, int height) {
        return info(width, height, this.colorMode);
    }

    /**
     * Sets the path to load image information.
     *
     * @param width texture width;
     * @param height texture height;
     * @param colorMode pixels color mode.
     * @return texture builder.
     */
    public GLTextureBuilder<T> info(int width, int height, ColorMode colorMode) {
        this.info = new GLTextureInfo(null, width, height, false);
        this.colorMode = colorMode;
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

            GLTexture texture = GLTexture.of(this.name, this.colorMode, this.info);

            texture.setFiltering(this.filtering);
            texture.setWrapping(this.wrapping);

            return texture;
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private void checkArguments() {
        if (this.name == null)
            throw new IllegalArgumentException("Name cannot be null.");
        if (this.loader == null)
            throw new IllegalArgumentException(String.format("Loader in texture '%s' cannot be null.", this.name));
        if (this.info == null)
            throw new IllegalArgumentException(String.format("Texture information in texture '%s' cannot be null", this.name));
        if (this.colorMode == null)
            throw new IllegalArgumentException(String.format("ColorMode in texture '%s' cannot be null.", this.name));
    }
}
