package com.ferra13671.gltextureutils.builder;

import com.ferra13671.gltextureutils.TextureFiltering;
import com.ferra13671.gltextureutils.TextureWrapping;

import java.io.InputStream;

/**
 * An object representing gif information.
 */
public class GLGifInfo {
    /** Gif InputStream. **/
    private final InputStream inputStream;
    /** Texture filtering mode. **/
    private final TextureFiltering filtering;
    /** Texture wrapping mode. **/
    private final TextureWrapping wrapping;

    /**
     * @param inputStream gif InputStream.
     * @param filtering texture filtering mode.
     * @param wrapping texture wrapping mode.
     */
    public GLGifInfo(InputStream inputStream, TextureFiltering filtering, TextureWrapping wrapping) {
        this.inputStream = inputStream;
        this.filtering = filtering;
        this.wrapping = wrapping;
    }

    /**
     * Returns gif InputStream.
     *
     * @return gif InputStream.
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * Returns texture filtering mode.
     *
     * @return texture filtering mode.
     */
    public TextureFiltering getFiltering() {
        return filtering;
    }

    /**
     * Returns texture wrapping mode.
     *
     * @return texture wrapping mode.
     */
    public TextureWrapping getWrapping() {
        return wrapping;
    }
}
