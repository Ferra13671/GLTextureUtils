package com.ferra13671.gltextureutils.builder;

import com.ferra13671.gltextureutils.ColorMode;
import com.ferra13671.gltextureutils.TextureFiltering;
import com.ferra13671.gltextureutils.TextureWrapping;

import java.nio.ByteBuffer;

/**
 * An object representing texture information.
 */
public class GLTextureInfo {
    /** Pixels buffer. **/
    private final ByteBuffer pixels;
    /** Texture width. **/
    private final int width;
    /** Texture height. **/
    private final int height;
    /** Pixels color mode. **/
    private final ColorMode colorMode;
    /** Texture filtering mode. **/
    private final TextureFiltering filtering;
    /** Texture wrapping mode. **/
    private final TextureWrapping wrapping;
    /** Whether stb is used when loading a texture or not. **/
    private final boolean usingStb;

    /**
     * @param pixels pixels buffer.
     * @param width texture width.
     * @param height texture height.
     * @param colorMode pixels color mode.
     * @param filtering texture filtering mode.
     * @param wrapping texture wrapping mode.
     * @param usingStb whether stb is used when loading a texture or not.
     */
    public GLTextureInfo(ByteBuffer pixels, int width, int height, ColorMode colorMode, TextureFiltering filtering, TextureWrapping wrapping, boolean usingStb) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.colorMode = colorMode;
        this.filtering = filtering;
        this.wrapping = wrapping;
        this.usingStb = usingStb;
    }

    /**
     * Returns pixels buffer.
     *
     * @return pixels buffer.
     */
    public ByteBuffer getPixels() {
        return pixels;
    }

    /**
     * Return texture width.
     *
     * @return texture width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns texture height.
     *
     * @return texture height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns pixels color mode.
     *
     * @return pixels color mode.
     */
    public ColorMode getColorMode() {
        return colorMode;
    }

    /**
     * Returns texture filtering mode.
     *
     * @return texture filtering mode.
     */
    public TextureFiltering getFiltering() {
        return filtering;
    }

    /**
     * Returns texture wrapping mode.
     *
     * @return texture wrapping mode.
     */
    public TextureWrapping getWrapping() {
        return wrapping;
    }

    /**
     * Returns whether stb is used when loading a texture or not.
     *
     * @return whether stb is used when loading a texture or not.
     */
    public boolean isUsingStb() {
        return usingStb;
    }
}
