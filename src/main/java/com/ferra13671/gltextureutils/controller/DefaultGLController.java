package com.ferra13671.gltextureutils.controller;

import org.lwjgl.opengl.GL11;
import java.nio.ByteBuffer;

/**
 * The basic implementation of GLController, in which all standard OpenGL methods are not overridden.
 */
public class DefaultGLController implements GLController {

    @Override
    public void run(Runnable runnable) {
        runnable.run();
    }

    @Override
    public int genTexId() {
        return GL11.glGenTextures();
    }

    @Override
    public void deleteTexture(int id) {
        GL11.glDeleteTextures(id);
    }

    @Override
    public void bindTexture(int id) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    @Override
    public void texParameter(int target, int pname, int param) {
        GL11.glTexParameteri(target, pname, param);
    }

    @Override
    public void texParameter(int target, int pname, float param) {
        GL11.glTexParameterf(target, pname, param);
    }

    @Override
    public void texImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, ByteBuffer pixels) {
        GL11.glTexImage2D(target, level, internalFormat, width, height, border, format, type, pixels);
    }

    @Override
    public void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, long pixels) {
        GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, pixels);
    }

    @Override
    public void copyTexSubImage2D(int target, int level, int xOffset, int yOffset, int x, int y, int width, int height) {
        GL11.glCopyTexSubImage2D(target, level, xOffset, yOffset, x, y, width, height);
    }

    @Override
    public void getTexImage(int tex, int level, int externalFormat, int type, ByteBuffer pixels) {
        GL11.glGetTexImage(tex, level, externalFormat, type, pixels);
    }

    @Override
    public void pixelStore(int pname, int param) {
        GL11.glPixelStorei(pname, param);
    }
}
