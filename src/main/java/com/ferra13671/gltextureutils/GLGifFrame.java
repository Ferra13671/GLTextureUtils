package com.ferra13671.gltextureutils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.image.BufferedImage;

/**
 * An object representing a gif frame.
 *
 * @see GLGif
 */
@Getter
@AllArgsConstructor
public class GLGifFrame {
    /** Gif frame texture. **/
    private final GLTexture texture;
    /** Gif frame image. **/
    private final BufferedImage image;
    /** Gif frame update delay. **/
    private final int delay;
}
