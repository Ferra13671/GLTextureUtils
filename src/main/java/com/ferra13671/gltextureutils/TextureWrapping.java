package com.ferra13671.gltextureutils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

/**
 * OpenGL behavior mode when getting a pixel color outside the range [0, 1].
 *
 * @see GLTexture
 */
public enum TextureWrapping {
    /**
     * A mode in which values outside the boundaries will return the color of the pixel closest to the boundary.
     */
    DEFAULT(GL12.GL_CLAMP_TO_EDGE),
    /**
     * The mode in which the texture will be repeated beyond the boundaries.
     */
    REPEAT(GL11.GL_REPEAT),
    /**
     * Works the same as REPEAT, except that each repetition of the texture mirrors it.
     */
    MIRROR_REPEAT(GL14.GL_MIRRORED_REPEAT);

    public final int id;

    TextureWrapping(int id) {
        this.id = id;
    }
}
