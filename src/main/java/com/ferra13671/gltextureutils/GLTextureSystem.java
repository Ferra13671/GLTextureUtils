package com.ferra13671.gltextureutils;

import com.ferra13671.gltextureutils.controller.DefaultGlController;
import com.ferra13671.gltextureutils.controller.GlController;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GLTextureSystem {
    protected static final List<GlTex> ALL_TEXTURES = new CopyOnWriteArrayList<>();
    /**
     * If you are using lwjgl that is not 3.3.3, or if you are using a modified
     * version of lwjgl (as in Minecraft), then some methods may be slightly different,
     * so that each time not to transfer the utility to different versions was made {@link GlController},
     * with which you can customize what the code will do in this or that method. The default
     * is {@link DefaultGlController}, which does exactly what the default code should do.
     */
    private static GlController glController = new DefaultGlController();

    public static void setGlController(GlController controller) {
        glController = controller;
    }

    public static GlController getGlController() {
        return glController;
    }

    public static void close() {
        ALL_TEXTURES.forEach(GlTex::delete);
        ALL_TEXTURES.clear();
    }
}
