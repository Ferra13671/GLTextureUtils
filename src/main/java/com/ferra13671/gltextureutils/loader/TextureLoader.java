package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.builder.GLTextureBuilder;
import com.ferra13671.gltextureutils.ColorMode;
import com.ferra13671.gltextureutils.TextureFiltering;
import com.ferra13671.gltextureutils.TextureWrapping;
import com.ferra13671.gltextureutils.builder.GLTextureInfo;

public abstract class TextureLoader<T> {

    public abstract GLTextureInfo load(T path, ColorMode colorMode, TextureFiltering filtering, TextureWrapping wrapping) throws Exception;

    public GLTextureBuilder<T> createTextureBuilder() {
        return new GLTextureBuilder<>(this);
    }
}
