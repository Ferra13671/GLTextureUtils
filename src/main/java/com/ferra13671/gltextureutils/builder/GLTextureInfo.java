package com.ferra13671.gltextureutils.builder;

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
    /** Whether stb is used when loading a texture or not. **/
    private final boolean usingStb;

    /**
     * @param pixels pixels buffer.
     * @param width texture width.
     * @param height texture height.
     * @param usingStb whether stb is used when loading a texture or not.
     */
    public GLTextureInfo(ByteBuffer pixels, int width, int height, boolean usingStb) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
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
     * Returns whether stb is used when loading a texture or not.
     *
     * @return whether stb is used when loading a texture or not.
     */
    public boolean isUsingStb() {
        return usingStb;
    }
}
