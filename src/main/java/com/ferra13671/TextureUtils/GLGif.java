package com.ferra13671.TextureUtils;

import com.ferra13671.TextureUtils.GifUtils.GifUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ferra13671
 * @LastUpdate 1.3
 */

public class GLGif implements GlTex {
    private int updateDelayMillis;
    private long lastTime;
    private final List<GLTexture> textures = new ArrayList<>();
    private Iterator<GLTexture> iterator;
    private GLTexture currentTexture;


    protected void _fromInputStream(InputStream inputStream, DecompileMode decompileMode, int updateDelayMillis) throws IOException {
        this.updateDelayMillis = updateDelayMillis;
        lastTime = System.currentTimeMillis();
        List<BufferedImage> images;
        ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
        if (decompileMode == DecompileMode.DELTAS) images = GifUtils.decompileDeltas(iis);
        else images = GifUtils.decompileFull(iis);

        for (BufferedImage bufferedImage : images) {
            textures.add(GLTexture.fromBufferedImage(bufferedImage));
        }
        iterator = textures.iterator();
    }

    public void update() {
        if (System.currentTimeMillis() - lastTime >= updateDelayMillis) {
            if (!iterator.hasNext()) iterator = textures.iterator();
            currentTexture = iterator.next();
            lastTime = System.currentTimeMillis();
        }
    }

    @Override
    public void delete() {
        for (GLTexture glTexture : textures) {
            glTexture.delete();
        }
        textures.clear();
    }

    @Override
    public void bind() {
        currentTexture.bind();
    }

    @Override
    public void unBind() {
        currentTexture.unBind();
    }

    @Override
    public int getTexId() {
        return currentTexture.getTexId();
    }

    @Override
    public int getWidth() {
        return currentTexture.getWidth();
    }

    @Override
    public int getHeight() {
        return currentTexture.getHeight();
    }

    public static GLGif fromFile(File file, DecompileMode decompileMode, int updateDelayMillis) {
        try {
            GLGif glGif = new GLGif();
            glGif._fromInputStream(Files.newInputStream(file.toPath()), decompileMode, updateDelayMillis);
            return glGif;
        } catch (IOException e) {
            return null;
        }
    }

    public static GLGif fromInputStream(InputStream inputStream, DecompileMode decompileMode, int updateDelayMillis) {
        try {
            GLGif glGif = new GLGif();
            glGif._fromInputStream(inputStream, decompileMode, updateDelayMillis);
            return glGif;
        } catch (IOException e) {
            return null;
        }
    }

    public enum DecompileMode {
        DELTAS,
        FULL
    }
}
