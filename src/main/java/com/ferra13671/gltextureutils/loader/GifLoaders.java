package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.GLGif;
import com.ferra13671.gltextureutils.TextureFiltering;
import com.ferra13671.gltextureutils.TextureWrapping;
import com.ferra13671.gltextureutils.builder.GLGifInfo;

import java.io.InputStream;
import java.net.URI;

/**
 * A class that stores all the basic gif loaders.
 *
 * @see GifLoader
 * @see GLGif
 */
public final class GifLoaders {
    public static final GifLoader<InputStream> INPUT_STREAM = new GifLoader<InputStream>() {
        @Override
        public GLGifInfo load(InputStream path, TextureFiltering filtering, TextureWrapping wrapping) {
            return new GLGifInfo(path, filtering, wrapping);
        }
    };
    public static final GifLoader<URI> URI = new GifLoader<java.net.URI>() {
        @Override
        public GLGifInfo load(java.net.URI path, TextureFiltering filtering, TextureWrapping wrapping) throws Exception {
            return new GLGifInfo(path.toURL().openStream(), filtering, wrapping);
        }
    };
    public static final GifLoader<FileEntry> FILE_ENTRY = new GifLoader<FileEntry>() {
        @Override
        public GLGifInfo load(FileEntry path, TextureFiltering filtering, TextureWrapping wrapping) {
            return new GLGifInfo(path.getPathMode().streamCreateFunction.apply(path.getPath()), filtering, wrapping);
        }
    };
}
