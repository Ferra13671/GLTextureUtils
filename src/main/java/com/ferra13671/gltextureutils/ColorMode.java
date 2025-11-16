package com.ferra13671.gltextureutils;

import com.ferra13671.gltextureutils.controller.DefaultGlController;

public enum ColorMode {
    RGB(DefaultGlController.GL_RGB),
    RGBA(DefaultGlController.GL_RGBA);

    public final int glId;

    ColorMode(int glId) {
        this.glId = glId;
    }
}