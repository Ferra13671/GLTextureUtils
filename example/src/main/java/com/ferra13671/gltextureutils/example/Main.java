package com.ferra13671.gltextureutils.example;

import com.ferra13671.gltextureutils.*;
import com.ferra13671.gltextureutils.builder.GLGifInfo;
import com.ferra13671.gltextureutils.builder.GLTextureInfo;
import com.ferra13671.gltextureutils.controller.DefaultGLController;
import com.ferra13671.gltextureutils.loader.GifLoader;
import com.ferra13671.gltextureutils.loader.GifLoaders;
import com.ferra13671.gltextureutils.loader.TextureLoader;
import com.ferra13671.gltextureutils.loader.TextureLoaders;
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
    public static GLGif testGif;

    /*
    Custom texture data loader.
    This example loader should not be used, as loading data within a JAR file is already implemented in the TextureLoaders.FILE_ENTRY loader.
     */
    public static final TextureLoader<String> exampleTextureLoader = new TextureLoader<String>() {
        @Override
        public GLTextureInfo load(String path, ColorMode colorMode) throws Exception {
            return TextureLoaders.INPUT_STREAM.load(Main.class.getClassLoader().getResourceAsStream(path), colorMode);
        }
    };
    /*
    Custom gif data loader.
    This example loader should not be used, as loading data within a JAR file is already implemented in the GifLoaders.FILE_ENTRY loader.
     */
    public static final GifLoader<String> exampleGifLoader = new GifLoader<String>() {
        @Override
        public GLGifInfo load(String path, TextureFiltering filtering, TextureWrapping wrapping) throws Exception {
            return GifLoaders.INPUT_STREAM.load(Main.class.getClassLoader().getResourceAsStream(path), filtering, wrapping);
        }
    };

    public static void main(String[] args) {
        //Create a test window
        createWindow();

        //Sets the current controller for interaction with important OpenGL methods.
        GLTextureSystem.setGlController(new DefaultGLController());

        //Create a texture with the image 'testTexture.jpg'
        testTexture = exampleTextureLoader.createTextureBuilder()
                .name("TestTexture")
                .info("testTexture.jpg")
                .filtering(TextureFiltering.SMOOTH)
                .wrapping(TextureWrapping.DEFAULT)
                .build();

        //Create a gif
        testGif = exampleGifLoader.createGifBuilder()
                .name("TestGif")
                .info("testGif.gif")
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
        drawTexture(0, 0, 200, 200, 0, 0, 1, 1, testTexture);

        //Draw our gif to the screen
        drawTexture(150, 200, 350, 400, 0, 0, 1, 1, testGif);
    }

    public static void drawTexture(float x1, float y1, float x2, float y2, float texPosX1, float texPosY1, float texPosX2, float texPosY2, GlTex texture) {
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
