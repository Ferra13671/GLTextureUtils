package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.builder.GLTextureBuilder;
import com.ferra13671.gltextureutils.ColorMode;
import com.ferra13671.gltextureutils.builder.GLTextureInfo;
import com.ferra13671.gltextureutils.GLTexture;

/**
 * An object that represents a loader of texture information for further use.
 *
 * @param <T> the type of object that is a path to image.
 *
 * @see GLTexture
 * @see TextureLoaders
 */
public abstract class TextureLoader<T> {

    /**
     * Loads various data into the texture information.
     *
     * @param path path to image.
     * @param colorMode color mode.
     * @return texture information.
     * @throws Exception various errors that may occur during loading.
     *
     * @see GLTextureInfo
     */
    public abstract GLTextureInfo load(T path, ColorMode colorMode) throws Exception;

    /**
     * Creates a new texture builder that will use this texture loader.
     *
     * @return new texture builder.
     *
     * @see GLTextureBuilder
     */
    public GLTextureBuilder<T> createTextureBuilder() {
        return new GLTextureBuilder<>(this);
    }
}
