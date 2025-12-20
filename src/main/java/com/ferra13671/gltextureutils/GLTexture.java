package com.ferra13671.gltextureutils;

import com.ferra13671.gltextureutils.controller.GLController;
import com.ferra13671.gltextureutils.builder.GLTextureInfo;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.util.Random;

import static org.lwjgl.stb.STBImage.nstbi_image_free;

/**
 * The basic texture implementation in GLTextureUtils.
 */
public class GLTexture implements GlTex {
    /** Texture id in OpenGL. **/
    protected int texId;

    /** Texture name. **/
    protected final String name;
    /** Texture width. **/
    protected int width;
    /** Texture height. **/
    protected int height;

    /** Texture filtering. **/
    protected TextureFiltering filtering;
    /** Texture wrapping mode. **/
    protected TextureWrapping wrapping;
    /** Pixels color mode. **/
    protected ColorMode colorMode;

    /**
     * @param name texture name.
     * @param colorMode pixels color mode.
     * @param filtering texture filtering mode.
     * @param wrapping texture wrapping mode.
     */
    protected GLTexture(String name, ColorMode colorMode, TextureFiltering filtering, TextureWrapping wrapping) {
        this.name = name;
        this.colorMode = colorMode;
        this.filtering = filtering;
        this.wrapping = wrapping;
        GLTextureSystem.ALL_TEXTURES.add(this);
    }

    /**
     * Loads a texture using GLTextureInfo.
     *
     * @param glTextureInfo texture information.
     * @return loaded texture.
     */
    private GLTexture create(GLTextureInfo glTextureInfo) {
        GLController controller = GLTextureSystem.getGlController();

        controller.run(() -> {
            texId = controller.genTexId();

            controller.bindTexture(texId);
            controller.texParameter(GL11.GL_TEXTURE_2D, 33085, 0);
            controller.texParameter(GL11.GL_TEXTURE_2D, 33082, 0);
            controller.texParameter(GL11.GL_TEXTURE_2D, 33083, 0);
            controller.texParameter(GL11.GL_TEXTURE_2D, 34049, 0.0F);
            applyFiltering(controller);
            applyWrapping(controller);

            this.width = glTextureInfo.getWidth();
            this.height = glTextureInfo.getHeight();

            controller.texImage2D(GL11.GL_TEXTURE_2D, 0, this.colorMode.internalId, this.width, this.height, 0, this.colorMode.externalId, 5121, null);

            if (glTextureInfo.getPixels() != null) {
                long bufferAddress = MemoryUtil.memAddress(glTextureInfo.getPixels());

                controller.pixelStore(GL11.GL_UNPACK_ROW_LENGTH, 0);
                controller.pixelStore(GL11.GL_UNPACK_SKIP_PIXELS, 0);
                controller.pixelStore(GL11.GL_UNPACK_SKIP_ROWS, 0);
                controller.pixelStore(GL11.GL_UNPACK_ALIGNMENT, 4);
                controller.texSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, this.width, this.height, this.colorMode.externalId, 5121, bufferAddress);

                if (glTextureInfo.isUsingStb())
                    nstbi_image_free(bufferAddress);
                else
                    MemoryUtil.memFree(glTextureInfo.getPixels());
            }

            controller.bindTexture(0);
        });

        return this;
    }

    private void applyFiltering(GLController controller) {
        controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, this.filtering.id);
        controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, this.filtering.id);
    }

    private void applyWrapping(GLController controller) {
        controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, this.wrapping.id);
        controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, this.wrapping.id);
    }

    /**
     * Creates a new texture from a specific area of pixels of the current texture.
     *
     * @param u1 initial position of the region along x (from 0 to 1).
     * @param v1 initial position of the region along y (from 0 to 1).
     * @param u2 final position of the region along x (from 0 to 1).
     * @param v2 final position of the region along y (from 0 to 1).
     * @return new texture from a specific area of pixels of the current texture.
     */
    public GLTexture subTexture(float u1, float v1, float u2, float v2) {
        GLController controller = GLTextureSystem.getGlController();
        GLTexture texture = new GLTexture(this.name.concat(String.format("_sub_%s", new Random().nextInt())), this.colorMode, this.filtering, this.wrapping);

        controller.run(() -> {
            texture.texId = controller.genTexId();

            int fbo = GL30.glGenFramebuffers();
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
            GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, this.texId, 0);

            if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE)
                throw new RuntimeException(String.format("An error occurred while creating FrameBuffer for subtexture '%s'.", texture.getName()));

            texture.width = (int) ((u2 - u1) * this.width);
            texture.height = (int) ((v2 - v1) * this.height);

            controller.bindTexture(texture.texId);
            controller.texParameter(GL11.GL_TEXTURE_2D, 33085, 0);
            controller.texParameter(GL11.GL_TEXTURE_2D, 33082, 0);
            controller.texParameter(GL11.GL_TEXTURE_2D, 33083, 0);
            controller.texParameter(GL11.GL_TEXTURE_2D, 34049, 0.0F);
            texture.applyFiltering(controller);
            texture.applyWrapping(controller);

            controller.texImage2D(GL11.GL_TEXTURE_2D, 0, texture.colorMode.internalId, texture.width, texture.height, 0, texture.colorMode.externalId, 5121, null);
            controller.pixelStore(GL11.GL_UNPACK_ROW_LENGTH, 0);
            controller.pixelStore(GL11.GL_UNPACK_SKIP_PIXELS, 0);
            controller.pixelStore(GL11.GL_UNPACK_SKIP_ROWS, 0);
            controller.pixelStore(GL11.GL_UNPACK_ALIGNMENT, 4);

            GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0,
                    0, 0,
                    (int) (u1 * this.width), (int) (v1 * this.height),
                    texture.width, texture.height);

            controller.bindTexture(0);
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
            GL30.glDeleteFramebuffers(fbo);
        });

        return texture;
    }

    @Override
    public void delete() {
        GLTextureSystem.getGlController().deleteTexture(texId);
        GLTextureSystem.ALL_TEXTURES.remove(this);
    }

    @Override
    public void bind() {
        GLTextureSystem.getGlController().bindTexture(getTexId());
    }

    @Override
    public void unbind() {
        GLTextureSystem.getGlController().bindTexture(0);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getTexId() {
        return this.texId;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    /**
     * Creates a new texture.
     *
     * @param name texture name.
     * @param colorMode pixels color mode.
     * @param textureFiltering texture filtering mode.
     * @param textureWrapping texture wrapping mode.
     * @param glTextureInfo texture information.
     * @return new texture,
     */
    public static GLTexture of(String name, ColorMode colorMode, TextureFiltering textureFiltering, TextureWrapping textureWrapping, GLTextureInfo glTextureInfo) {
        return new GLTexture(name, colorMode, textureFiltering, textureWrapping).create(glTextureInfo);
    }
}
