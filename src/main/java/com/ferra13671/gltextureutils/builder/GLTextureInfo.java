package com.ferra13671.gltextureutils.builder;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * An object representing texture information.
 */
@AllArgsConstructor
@Getter
public class GLTextureInfo {
    /** Pixels buffer. **/
    private final ByteBuffer pixels;
    /** Texture width. **/
    private final int width;
    /** Texture height. **/
    private final int height;
    /** Whether stb is used when loading a texture or not. **/
    private final boolean usingStb;
}
