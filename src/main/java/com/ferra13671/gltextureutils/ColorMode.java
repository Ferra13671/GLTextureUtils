package com.ferra13671.gltextureutils;

import lombok.AllArgsConstructor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 * The color scheme mode of the pixels in the texture.
 */
@AllArgsConstructor
public enum ColorMode {
    RGB(GL11.GL_RGB, GL11.GL_RGB8, 3),
    RGBA(GL11.GL_RGBA, GL11.GL_RGBA8, 4),
    DEPTH(GL30.GL_DEPTH_COMPONENT, GL30.GL_DEPTH_COMPONENT32, 1);

    public final int externalId;
    public final int internalId;
    public final int components;
}