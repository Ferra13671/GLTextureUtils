package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.Utils;
import com.ferra13671.gltextureutils.builder.GLTextureBuilder;
import com.ferra13671.gltextureutils.ColorMode;
import com.ferra13671.gltextureutils.builder.GLTextureInfo;
import com.ferra13671.gltextureutils.GLTexture;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An object that represents a loader of texture information for further use.
 *
 * @param <T> the type of object that is a path to image.
 *
 * @see GLTexture
 */
public abstract class TextureLoader<T> {
    public static final TextureLoader<InputStream> INPUT_STREAM = new TextureLoader<>() {
        @Override
        public GLTextureInfo load(InputStream path, ColorMode colorMode) throws Exception {
            ByteBuffer buffer = Utils.readStream(path);
            buffer.rewind();

            AtomicReference<GLTextureInfo> glTextureInfo = new AtomicReference<>(null);
            Utils.tryGenerate(buffer, path, memoryStack -> {
                IntBuffer xBuffer = memoryStack.mallocInt(1);
                IntBuffer yBuffer = memoryStack.mallocInt(1);
                IntBuffer channelsBuffer = memoryStack.mallocInt(1);
                ByteBuffer byteBuffer = STBImage.stbi_load_from_memory(buffer, xBuffer, yBuffer, channelsBuffer, colorMode == ColorMode.RGBA ? 4 : 3);

                if (byteBuffer != null)
                    glTextureInfo.set(new GLTextureInfo(byteBuffer, xBuffer.get(0), yBuffer.get(0), true));
            });
            return glTextureInfo.get();
        }
    };
    public static final TextureLoader<FileEntry> FILE_ENTRY = new TextureLoader<>() {
        @Override
        public GLTextureInfo load(FileEntry path, ColorMode colorMode) throws Exception {
            return INPUT_STREAM.load(path.pathMode().streamCreateFunction.apply(path.path()), colorMode);
        }
    };
    public static final TextureLoader<URL> URL = new TextureLoader<>() {
        @Override
        public GLTextureInfo load(URL path, ColorMode colorMode) throws Exception {
            return INPUT_STREAM.load(path.openStream(), colorMode);
        }
    };
    public static final TextureLoader<BufferedImage> BUFFERED_IMAGE = new TextureLoader<>() {
        @Override
        public GLTextureInfo load(BufferedImage path, ColorMode colorMode) {
            GLTextureInfo glTextureInfo;

            int[] pixels = new int[path.getWidth() * path.getHeight()];
            path.getRGB(0, 0, path.getWidth(), path.getHeight(), pixels, 0, path.getWidth());

            ByteBuffer byteBuffer = MemoryUtil.memAlloc((path.getWidth() * path.getHeight() * 4));

            for (int y = 0; y < path.getHeight(); y++) {
                for (int x = 0; x < path.getWidth(); x++) {
                    int pixel = pixels[y * path.getWidth() + x];
                    byteBuffer.put(Utils.getRed(pixel));
                    byteBuffer.put(Utils.getGreen(pixel));
                    byteBuffer.put(Utils.getBlue(pixel));
                    byteBuffer.put(Utils.getAlpha(pixel));
                }
            }
            byteBuffer.flip();

            glTextureInfo = new GLTextureInfo(byteBuffer, path.getWidth(), path.getHeight(), false);

            return glTextureInfo;
        }
    };

    /**
     * Loads various data into the texture information.
     *
     * @param path path to image.
     * @param colorMode color mode.
     * @return texture information.
     * @throws Exception various errors that may occur during loading.
     *
     * @see GLTextureInfo
     */
    public abstract GLTextureInfo load(T path, ColorMode colorMode) throws Exception;

    /**
     * Creates a new texture builder that will use this texture loader.
     *
     * @return new texture builder.
     *
     * @see GLTextureBuilder
     */
    public GLTextureBuilder<T> createTextureBuilder() {
        return new GLTextureBuilder<>(this);
    }
}
