package com.ferra13671.gltextureutils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 * The color scheme mode of the pixels in the texture.
 */
public enum ColorMode {
    RGB(GL11.GL_RGB, GL11.GL_RGB8),
    RGBA(GL11.GL_RGBA, GL11.GL_RGBA8),
    DEPTH(GL30.GL_DEPTH_COMPONENT, GL30.GL_DEPTH_COMPONENT32);

    public final int externalId;
    public final int internalId;

    ColorMode(int externalId, int internalId) {
        this.externalId = externalId;
        this.internalId = internalId;
    }
}