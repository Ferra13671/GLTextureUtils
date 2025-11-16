package com.ferra13671.gltextureutils;

import com.ferra13671.gltextureutils.controller.DefaultGLController;

/**
 * The color scheme mode of the pixels in the texture.
 */
public enum ColorMode {
    /**
     * The mode in which the color in a pixel will be stored in RGB (Red, Green, Blue) format.
     */
    RGB(DefaultGLController.GL_RGB),
    /**
     * The mode in which the color in a pixel will be stored in RGBA (Red, Green, Blue, Alpha) format.
     */
    RGBA(DefaultGLController.GL_RGBA);

    public final int glId;

    ColorMode(int glId) {
        this.glId = glId;
    }
}