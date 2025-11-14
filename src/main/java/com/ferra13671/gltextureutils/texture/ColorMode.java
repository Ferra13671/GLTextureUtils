package com.ferra13671.gltextureutils.texture;

import com.ferra13671.gltextureutils.controller.DefaultGlController;

/**
 * @author Ferra13671
 * @LastUpdate 1.6.3
 */

public enum ColorMode {
    RGB(DefaultGlController.GL_RGB),
    RGBA(DefaultGlController.GL_RGBA);

    public final int glId;

    ColorMode(int glId) {
        this.glId = glId;
    }
}