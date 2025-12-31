package com.ferra13671.gltextureutils.atlas;

import com.ferra13671.gltextureutils.GLTexture;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public class TextureAtlasScheme {
    private final int textureWidth;
    private final int textureHeight;
    private final HashMap<GLTexture, TexturePos> poses;
}
