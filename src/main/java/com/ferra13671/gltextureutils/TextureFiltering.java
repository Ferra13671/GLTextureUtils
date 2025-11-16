package com.ferra13671.gltextureutils;

import org.lwjgl.opengl.GL11;

/**
 * Texture filtering mode when it is decreased/increased.
 *
 * @see GLTexture
 */
public enum TextureFiltering {
    /**
     * A texture filtering mode in which the color of a floating-point coordinate will be taken as the color of the closest pixel to the coordinate.
     * <p>
     * If you draw a texture with this mode in an increased state, the transition between pixels will be clearly visible.
    */
    DEFAULT(GL11.GL_NEAREST),
    /**
     * A texture filtering mode in which the color of a floating-point coordinate will be taken as a transition color between nearby pixels, favoring the closest one.
     * <p>
     * If you draw a texture with this mode in an increased state, the transition between pixels will be smooth and unnoticeable.
     */
    SMOOTH(GL11.GL_LINEAR);

    /** Texture filtering mode id in OpenGL. **/
    public final int id;

    TextureFiltering(int id) {
        this.id = id;
    }
}
