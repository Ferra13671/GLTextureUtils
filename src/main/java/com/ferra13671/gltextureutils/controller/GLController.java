package com.ferra13671.gltextureutils.controller;

import java.nio.ByteBuffer;

/**
 * GLController allows you to override the execution of standard OpenGL methods, thereby adapting to various other utilities that may have their own implementations of these methods.
 */
public interface GLController {

    void run(Runnable runnable);

    int genTexId();

    void deleteTexture(int id);

    void bindTexture(int id);

    void texParameter(int target, int pname, int param);

    void texParameter(int target, int pname, float param);

    void texImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels);

    void texSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, long pixels);

    void pixelStore(int pname, int param);
}
