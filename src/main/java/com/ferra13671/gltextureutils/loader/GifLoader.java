package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.builder.GLGifBuilder;
import com.ferra13671.gltextureutils.builder.GLGifInfo;
import com.ferra13671.gltextureutils.TextureFiltering;
import com.ferra13671.gltextureutils.TextureWrapping;

public abstract class GifLoader<T> {

    public abstract GLGifInfo load(T path, TextureFiltering filtering, TextureWrapping wrapping) throws Exception;

    public GLGifBuilder<T> createGifBuilder() {
        return new GLGifBuilder<>(this);
    }
}
