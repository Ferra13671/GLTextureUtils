package com.ferra13671.gltextureutils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 * The color scheme mode of the pixels in the texture.
 */
public enum ColorMode {
    RGB(GL11.GL_RGB, GL11.GL_RGB8, 3),
    RGBA(GL11.GL_RGBA, GL11.GL_RGBA8, 4),
    DEPTH(GL30.GL_DEPTH_COMPONENT, GL30.GL_DEPTH_COMPONENT32, 1);

    public final int externalId;
    public final int internalId;
    public final int components;

    ColorMode(int externalId, int internalId, int components) {
        this.externalId = externalId;
        this.internalId = internalId;
        this.components = components;
    }
}