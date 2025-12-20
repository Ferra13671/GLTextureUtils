package com.ferra13671.gltextureutils;

import com.ferra13671.gltextureutils.controller.DefaultGLController;
import com.ferra13671.gltextureutils.controller.GLController;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Main class GLTextureSystem.
 */
public class GLTextureSystem {
    /**
     * List of all textures and gifs created in GLTextureUtils.
     *
     * @see GLTexture
     * @see GLGif
     */
    private static final List<GlTex> ALL_TEXTURES = new CopyOnWriteArrayList<>();
    /**
     * Controller allows you to change the call of standard OpenGL methods.
     * <p>
     * The default is {@link DefaultGLController}, which calls standard OpenGL methods.
     *
     * @see GLController
     * @see DefaultGLController
     */
    private static GLController glController = new DefaultGLController();

    /**
     * Sets a new glController.
     *
     * @param controller new glController.
     *
     * @see GLController
     */
    public static void setGlController(GLController controller) {
        glController = controller;
    }

    /**
     * Adds texture to list of all textures and gifs.
     *
     * @param texture texture.
     */
    public static void addTexture(GlTex texture) {
        ALL_TEXTURES.add(texture);
    }

    /**
     * Removes texture from list of all textures and gifs.
     *
     * @param texture texture.
     */
    public static void removeTexture(GlTex texture) {
        ALL_TEXTURES.remove(texture);
    }

    /**
     * Returns the current glController.
     *
     * @return current glController.
     *
     * @see GLController
     */
    public static GLController getGlController() {
        return glController;
    }

    /**
     * Closes all textures and gifs created during the entire operation of GLTextureSystem.
     *
     * @see GLTexture
     * @see GLGif
     */
    public static void close() {
        ALL_TEXTURES.forEach(GlTex::delete);
        ALL_TEXTURES.clear();
    }
}
