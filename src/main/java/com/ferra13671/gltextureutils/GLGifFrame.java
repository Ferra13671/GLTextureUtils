package com.ferra13671.gltextureutils;

import java.awt.image.BufferedImage;

public class GLGifFrame {
    private final GLTexture texture;
    private final BufferedImage image;
    private final int delay;

    public GLGifFrame(GLTexture texture, BufferedImage image, int delay) {
        this.texture = texture;
        this.image = image;
        this.delay = delay;
    }

    public GLTexture getTexture() {
        return texture;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getDelay() {
        return delay;
    }
}
