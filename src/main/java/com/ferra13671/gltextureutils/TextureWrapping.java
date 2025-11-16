package com.ferra13671.gltextureutils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * OpenGL behavior mode when getting a pixel color outside the range [0, 1].
 *
 * @see GLTexture
 */
public enum TextureWrapping {
    /**
     * A mode in which values outside the bounds are compressed to the edges of the texture.
     */
    DEFAULT(GL12.GL_CLAMP_TO_EDGE),
    /**
     * The mode in which the texture will be repeated beyond the boundaries.
     */
    REPEAT(GL11.GL_REPEAT);

    public final int id;

    TextureWrapping(int id) {
        this.id = id;
    }
}
