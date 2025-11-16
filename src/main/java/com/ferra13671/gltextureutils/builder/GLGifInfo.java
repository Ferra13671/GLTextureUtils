package com.ferra13671.gltextureutils.builder;

import com.ferra13671.gltextureutils.TextureFiltering;
import com.ferra13671.gltextureutils.TextureWrapping;

import java.io.InputStream;

public class GLGifInfo {
    private final InputStream inputStream;
    private final TextureFiltering filtering;
    private final TextureWrapping wrapping;

    public GLGifInfo(InputStream inputStream, TextureFiltering filtering, TextureWrapping wrapping) {
        this.inputStream = inputStream;
        this.filtering = filtering;
        this.wrapping = wrapping;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public TextureFiltering getFiltering() {
        return filtering;
    }

    public TextureWrapping getWrapping() {
        return wrapping;
    }
}
