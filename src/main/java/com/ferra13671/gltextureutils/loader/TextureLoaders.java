package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.builder.GLTextureInfo;
import com.ferra13671.gltextureutils.Utils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;
import com.ferra13671.gltextureutils.ColorMode;
import com.ferra13671.gltextureutils.GLTexture;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A class that stores all the basic texture loaders.
 *
 * @see TextureLoader
 * @see GLTexture
 */
public final class TextureLoaders {
    public static TextureLoader<InputStream> INPUT_STREAM = new TextureLoader<InputStream>() {
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
    public static TextureLoader<FileEntry> FILE_ENTRY = new TextureLoader<FileEntry>() {
        @Override
        public GLTextureInfo load(FileEntry path, ColorMode colorMode) throws Exception {
            return INPUT_STREAM.load(path.getPathMode().streamCreateFunction.apply(path.getPath()), colorMode);
        }
    };
    public static TextureLoader<URL> URL = new TextureLoader<java.net.URL>() {
        @Override
        public GLTextureInfo load(URL path, ColorMode colorMode) throws Exception {
            return INPUT_STREAM.load(path.openStream(), colorMode);
        }
    };
    public static TextureLoader<BufferedImage> BUFFERED_IMAGE = new TextureLoader<BufferedImage>() {
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
}
