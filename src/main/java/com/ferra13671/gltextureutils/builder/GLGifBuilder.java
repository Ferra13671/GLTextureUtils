package com.ferra13671.gltextureutils.builder;

import com.ferra13671.gltextureutils.*;
import com.ferra13671.gltextureutils.loader.GifLoader;

public class GLGifBuilder<T> {
    private String name = null;
    private T path = null;
    private final GifLoader<T> loader;
    private TextureFiltering filtering = null;
    private TextureWrapping wrapping = null;

    public GLGifBuilder(GifLoader<T> loader) {
        this.loader = loader;
    }

    public GLGifBuilder<T> name(String name) {
        this.name = name;
        return this;
    }

    public GLGifBuilder<T> path(T path) {
        this.path = path;
        return this;
    }

    public GLGifBuilder<T> filtering(TextureFiltering filtering) {
        this.filtering = filtering;
        return this;
    }

    public GLGifBuilder<T> wrapping(TextureWrapping wrapping) {
        this.wrapping = wrapping;
        return this;
    }

    public GLGif build() {
        try {
            checkArguments();
            return new GLGif(this.name, this.loader.load(this.path, this.filtering, this.wrapping));
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private void checkArguments() {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null.");
        if (loader == null)
            throw new IllegalArgumentException(String.format("Loader in texture '%s' cannot be null.", name));
        if (path == null)
            throw new IllegalArgumentException(String.format("Path in texture '%s' cannot be null.", name));
        if (filtering == null)
            throw new IllegalArgumentException(String.format("TextureFiltering in texture '%s' cannot be null.", name));
        if (wrapping == null)
            throw new IllegalArgumentException(String.format("TextureWrapping in texture '%s' cannot be null.", name));
    }
}
