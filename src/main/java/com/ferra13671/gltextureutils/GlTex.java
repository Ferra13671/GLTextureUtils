package com.ferra13671.gltextureutils;

/**
 * An interface that implements methods that every texture object must have.
 * <p>
 * This interface is implemented in {@link GLTexture} and {@link GLGif}.
 */
public interface GlTex {

    /**
     * Deletes all texture information in OpenGL.
     * Once deleted, the texture can no longer be used.
     */
    void delete();

    /**
     * Sets this texture as active in OpenGL.
     */
    void bind();

    /**
     * Sets the active texture in OpenGL to 0 (i.e. no texture)
     */
    void unbind();

    /**
     * Returns the width of the texture.
     *
     * @return width of the texture.
     */
    int getWidth();

    /**
     * Returns the height of the texture.
     *
     * @return height of the texture.
     */
    int getHeight();

    /**
     * Returns the texture id in OpenGL.
     *
     * @return texture id in OpenGL.
     */
    int getTexId();
}
