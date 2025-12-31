package com.ferra13671.gltextureutils;

import com.ferra13671.gltextureutils.controller.GLController;
import com.ferra13671.gltextureutils.builder.GLTextureInfo;
import lombok.Getter;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.Random;

import static org.lwjgl.stb.STBImage.nstbi_image_free;

/**
 * The basic texture implementation in GLTextureUtils.
 */
public class GLTexture implements GlTex {
    /** Texture id in OpenGL. **/
    protected int texId;

    /** Texture name. **/
    @Getter
    protected final String name;
    /** Texture width. **/
    protected int width;
    /** Texture height. **/
    protected int height;

    /** Texture filtering. **/
    protected TextureFiltering filtering = null;
    /** Texture wrapping mode. **/
    protected TextureWrapping wrapping = null;
    /** Pixels color mode. **/
    protected ColorMode colorMode;

    /**
     * @param name texture name.
     * @param colorMode pixels color mode.
     */
    protected GLTexture(String name, ColorMode colorMode) {
        this.name = name;
        this.colorMode = colorMode;
        GLTextureSystem.addTexture(this);
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
            prepareDefaultTextureParameters(controller);

            this.width = glTextureInfo.getWidth();
            this.height = glTextureInfo.getHeight();

            controller.texImage2D(GL11.GL_TEXTURE_2D, 0, this.colorMode.internalId, this.width, this.height, 0, this.colorMode.externalId, GL11.GL_UNSIGNED_BYTE, null);

            if (glTextureInfo.getPixels() != null) {
                long bufferAddress = MemoryUtil.memAddress(glTextureInfo.getPixels());

                prepareDefaultPixelStore(controller);
                controller.texSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, this.width, this.height, this.colorMode.externalId, GL11.GL_UNSIGNED_BYTE, bufferAddress);

                if (glTextureInfo.isUsingStb())
                    nstbi_image_free(bufferAddress);
                else
                    MemoryUtil.memFree(glTextureInfo.getPixels());
            }

            controller.bindTexture(0);
        });

        return this;
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
    public GLTexture cutTexture(float u1, float v1, float u2, float v2) {
        GLController controller = GLTextureSystem.getGlController();
        GLTexture texture = new GLTexture(this.name.concat(String.format("_sub_%s", new Random().nextInt())), this.colorMode);

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
            prepareDefaultTextureParameters(controller);
            texture.setFiltering(this.filtering);
            texture.setWrapping(this.wrapping);

            controller.texImage2D(GL11.GL_TEXTURE_2D, 0, texture.colorMode.internalId, texture.width, texture.height, 0, texture.colorMode.externalId, GL11.GL_UNSIGNED_BYTE, null);
            prepareDefaultPixelStore(controller);

            controller.copyTexSubImage2D(
                    GL11.GL_TEXTURE_2D,
                    0,
                    0,
                    0,
                    (int) (u1 * this.width),
                    (int) (v1 * this.height),
                    texture.width,
                    texture.height);

            controller.bindTexture(0);
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
            GL30.glDeleteFramebuffers(fbo);
        });

        return texture;
    }

    /**
     * Draws the required texture on the current.
     *
     * @param texture required texture.
     * @param x the X-axis position at which the texture will be drawn.
     * @param y the Y-axis position at which the texture will be drawn.
     * @return current texture.
     */
    public GLTexture drawImage(GLTexture texture, int x, int y) {
        GLController controller = GLTextureSystem.getGlController();

        controller.run(() -> {
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(texture.getWidth() * texture.getHeight() * texture.getColorMode().components);

            controller.bindTexture(texture.getTexId());
            controller.getTexImage(GL11.GL_TEXTURE_2D, 0, texture.getColorMode().externalId, GL11.GL_UNSIGNED_BYTE, byteBuffer);
            byteBuffer.flip();

            controller.bindTexture(getTexId());
            controller.texSubImage2D(GL11.GL_TEXTURE_2D, 0, x, y, texture.getWidth(), texture.getHeight(), texture.getColorMode().externalId, GL11.GL_UNSIGNED_BYTE, MemoryUtil.memAddress(byteBuffer));
            controller.bindTexture(0);

            setFiltering(this.filtering);
            setWrapping(this.wrapping);

            MemoryUtil.memFree(byteBuffer);
        });

        return this;
    }

    private static void prepareDefaultTextureParameters(GLController controller) {
        controller.texParameter(GL11.GL_TEXTURE_2D, 33085, 0);
        controller.texParameter(GL11.GL_TEXTURE_2D, 33082, 0);
        controller.texParameter(GL11.GL_TEXTURE_2D, 33083, 0);
        controller.texParameter(GL11.GL_TEXTURE_2D, 34049, 0.0F);
    }

    private static void prepareDefaultPixelStore(GLController controller) {
        controller.pixelStore(GL11.GL_UNPACK_ROW_LENGTH, 0);
        controller.pixelStore(GL11.GL_UNPACK_SKIP_PIXELS, 0);
        controller.pixelStore(GL11.GL_UNPACK_SKIP_ROWS, 0);
        controller.pixelStore(GL11.GL_UNPACK_ALIGNMENT, 4);
    }

    @Override
    public void delete() {
        GLTextureSystem.getGlController().deleteTexture(texId);
        GLTextureSystem.removeTexture(this);
    }

    @Override
    public void bind() {
        GLTextureSystem.getGlController().bindTexture(getTexId());
    }

    @Override
    public void unbind() {
        GLTextureSystem.getGlController().bindTexture(0);
    }

    @Override
    public TextureFiltering getFiltering() {
        return this.filtering;
    }

    @Override
    public void setFiltering(TextureFiltering filtering) {
        GLController controller = GLTextureSystem.getGlController();

        if (filtering != null) {
            bind();
            controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filtering.id);
            controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filtering.id);
        } else if (this.filtering != null) {
            bind();
            controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, TextureFiltering.DEFAULT.id);
            controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, TextureFiltering.DEFAULT.id);
        }

        this.filtering = filtering;
    }

    @Override
    public TextureWrapping getWrapping() {
        return this.wrapping;
    }

    @Override
    public void setWrapping(TextureWrapping wrapping) {
        GLController controller = GLTextureSystem.getGlController();

        if (wrapping != null) {
            bind();
            controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrapping.id);
            controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrapping.id);
        } else if (this.wrapping != null) {
            bind();
            controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, TextureWrapping.DEFAULT.id);
            controller.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, TextureWrapping.DEFAULT.id);
        }

        this.wrapping = wrapping;
    }

    @Override
    public ColorMode getColorMode() {
        return this.colorMode;
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
     * @param glTextureInfo texture information.
     * @return new texture,
     */
    public static GLTexture of(String name, ColorMode colorMode, GLTextureInfo glTextureInfo) {
        return new GLTexture(name, colorMode).create(glTextureInfo);
    }
}
