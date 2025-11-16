package com.ferra13671.gltextureutils;

import java.awt.image.BufferedImage;

/**
 * An object representing a gif frame.
 *
 * @see GLGif
 */
public class GLGifFrame {
    /** Gif frame texture. **/
    private final GLTexture texture;
    /** Gif frame image. **/
    private final BufferedImage image;
    /** Gif frame update delay. **/
    private final int delay;

    /**
     * @param texture gif frame texture.
     * @param image gif frame image.
     * @param delay gif frame update delay.
     */
    public GLGifFrame(GLTexture texture, BufferedImage image, int delay) {
        this.texture = texture;
        this.image = image;
        this.delay = delay;
    }

    /**
     * Return gif frame texture.
     *
     * @return gif frame texture.
     */
    public GLTexture getTexture() {
        return texture;
    }

    /**
     * Return gif frame image.
     *
     * @return gif frame image.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Returns gif frame update time.
     *
     * @return gif frame update time.
     */
    public int getDelay() {
        return delay;
    }
}
