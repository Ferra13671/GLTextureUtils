package com.ferra13671.TextureUtils;

import com.ferra13671.TextureUtils.Controller.DefaultGlController;
import com.ferra13671.TextureUtils.Controller.GlController;

/**
 * @author Ferra13671
 * @LastUpdate 1.0
 */

public class GLTextureSystem {
    /**
     * If you are using lwjgl that is not 3.3.3, or if you are using a modified
     * version of lwjgl (as in Minecraft), then some methods may be slightly different,
     * so that each time not to transfer the utility to different versions was made {@link GlController},
     * with which you can customize what the code will do in this or that method. The default
     * is {@link DefaultGlController}, which does exactly what the default code should do.
     */
    protected static GlController glController = new DefaultGlController();

    public static void setGlController(GlController controller) {
        glController = controller;
    }
}
