package com.ferra13671.gltextureutils;

import java.awt.image.BufferedImage;

/**
 * An object representing a gif frame.
 *
 * @param texture Gif frame texture.
 * @param image   Gif frame image.
 * @param delay   Gif frame update delay.
 * @see GLGif
 */
public record GLGifFrame(GLTexture texture, BufferedImage image, int delay) {
}
