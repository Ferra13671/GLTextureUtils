package com.ferra13671.gltextureutils.atlas;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An object that stores the UV coordinates of a texture.
 */
@Getter
@AllArgsConstructor
public class TextureBorder {
    private final float u1;
    private final float v1;
    private final float u2;
    private final float v2;
}
