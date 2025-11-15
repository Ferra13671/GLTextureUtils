package com.ferra13671.gltextureutils.example;

import com.ferra13671.gltextureutils.GLTextureSystem;
import com.ferra13671.gltextureutils.builder.GLTextureInfo;
import com.ferra13671.gltextureutils.controller.DefaultGlController;
import com.ferra13671.gltextureutils.loader.TextureLoader;
import com.ferra13671.gltextureutils.loader.TextureLoaders;
import com.ferra13671.gltextureutils.texture.ColorMode;
import com.ferra13671.gltextureutils.texture.GLTexture;
import com.ferra13671.gltextureutils.texture.TextureFiltering;
import com.ferra13671.gltextureutils.texture.TextureWrapping;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Main {

    private static final FloatBuffer customTextureCords = MemoryUtil.memAlloc(8 * 4).asFloatBuffer();
    private static final FloatBuffer rectVertex = MemoryUtil.memAlloc(8 * 4).asFloatBuffer();

    public static long window;
    public static final int width = 500, height = 500;

    public static GLTexture testTexture;

    /*
    Custom texture data loader.
    This example loader should not be used, as loading data within a JAR file is already implemented in the TextureLoaders.FILE_ENTRY loader.
     */
    public static final TextureLoader<String> exampleLoader = new TextureLoader<String>() {
        @Override
        public GLTextureInfo load(String path, ColorMode colorMode, TextureFiltering filtering, TextureWrapping wrapping) throws Exception {
            return TextureLoaders.INPUT_STREAM.load(Main.class.getClassLoader().getResourceAsStream(path), colorMode, filtering, wrapping);
        }
    };

    public static void main(String[] args) {
        //Create a test window
        createWindow();

        //Sets the current controller for interaction with important OpenGL methods.
        GLTextureSystem.setGlController(new DefaultGlController());

        //Create a texture with the image 'test.jpg'
        testTexture = exampleLoader.createTextureBuilder()
                .name("Test")
                .path("test.jpg")
                .filtering(TextureFiltering.SMOOTH)
                .wrapping(TextureWrapping.DEFAULT)
                .build();

        while (!GLFW.glfwWindowShouldClose(window)) {
            //Clean the screen
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            render();

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        //Close the created window
        GLFW.glfwDestroyWindow(window);
    }

    public static void render() {
        //Setup matrix
        GL11.glViewport(0,0, width, height);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 0, 1);

        //Draw our texture to the screen
        drawTexture(0, 0, width, height, 0, 0, 1, 1, testTexture);
    }

    public static void drawTexture(float x1, float y1, float x2, float y2, float texPosX1, float texPosY1, float texPosX2, float texPosY2, GLTexture texture) {
        rectVertex.put(new float[]{x1,y1, x2,y1, x2,y2, x1,y2}).position(0);
        customTextureCords.put(new float[]{texPosX1, texPosY1,   texPosX2, texPosY1,   texPosX2, texPosY2,   texPosX1, texPosY2}).position(0);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        GL11.glVertexPointer(2, GL11.GL_FLOAT, 0, rectVertex);
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, customTextureCords);

        //Sets the texture as active
        texture.bind();
        GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, 4);

        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    private static void createWindow() {
        GLFW.glfwInit();
        window = GLFW.glfwCreateWindow(width, height, "Example", 0, 0);

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_FOCUS_ON_SHOW, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, GLFW.GLFW_TRUE);
        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();

        GLFW.glfwShowWindow(window);
    }
}
