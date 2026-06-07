package com.ferra13671.gltextureutils.builder;

import java.nio.ByteBuffer;

/**
 * An object representing texture information.
 *
 * @param pixels   Pixels buffer.
 * @param width    Texture width.
 * @param height   Texture height.
 * @param usingStb Whether stb is used when loading a texture or not.
 */
public record GLTextureInfo(ByteBuffer pixels, int width, int height, boolean usingStb) {
}
