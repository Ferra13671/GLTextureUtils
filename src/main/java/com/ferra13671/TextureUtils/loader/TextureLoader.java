package com.ferra13671.TextureUtils.loader;

import com.ferra13671.TextureUtils.builder.GLTextureBuilder;
import com.ferra13671.TextureUtils.texture.ColorMode;
import com.ferra13671.TextureUtils.texture.TextureFiltering;
import com.ferra13671.TextureUtils.texture.TextureWrapping;
import com.ferra13671.TextureUtils.builder.GLTextureInfo;

/**
 * @author Ferra13671
 * @LastUpdate 1.6.3
 */

public abstract class TextureLoader<T> {

    public abstract GLTextureInfo load(T path, ColorMode colorMode, TextureFiltering filtering, TextureWrapping wrapping) throws Exception;

    public GLTextureBuilder<T> createTextureBuilder() {
        return new GLTextureBuilder<>(this);
    }
}
