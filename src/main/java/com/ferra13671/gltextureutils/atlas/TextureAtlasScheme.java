package com.ferra13671.gltextureutils.atlas;

import com.ferra13671.gltextureutils.GLTexture;
import lombok.AllArgsConstructor;

import java.util.HashMap;

public record TextureAtlasScheme(int textureWidth, int textureHeight, HashMap<GLTexture, TexturePos> poses) {
}
