package com.ferra13671.gltextureutils;

import com.ferra13671.gltextureutils.loader.TextureLoaders;
import lombok.AllArgsConstructor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Gif disposal method.
 */
@AllArgsConstructor
public enum Disposal {
    /**
     * The frame is a complement to the previous frame, so the finished frame will be a combination of this and the previous frame.
     */
    None((prevImage, imageData) -> {
        BufferedImage bufferedImage = new BufferedImage(prevImage.getWidth(), prevImage.getHeight(), prevImage.getType());
        Graphics2D graphics = bufferedImage.createGraphics();

        graphics.drawImage(prevImage, 0, 0, null);
        graphics.drawImage(imageData.getRight(), imageData.getLeft()[0], imageData.getLeft()[1], null);
        graphics.dispose();

        return bufferedImage;
    }, frames -> {}),
    /**
     * An empty frame will be added after this frame.
     */
    ToBackground((frames, imageData) -> imageData.getRight(), frames -> {
        GLGifFrame frame = frames.get(frames.size() - 1);
        frames.add(new GLGifFrame(
                TextureLoaders.BUFFERED_IMAGE.createTextureBuilder()
                        .name(frame.getTexture().getName() + "-extra")
                        .info(new BufferedImage(frame.getTexture().getWidth(), frame.getTexture().getHeight(), frame.getImage().getType()))
                        .filtering(frame.getTexture().filtering)
                        .wrapping(frame.getTexture().wrapping)
                        .build(),
                frame.getImage(),
                0
        ));
    }),
    /**
     * After this frame, the previous frame will be added.
     */
    ToPrevious((frames, imageData) -> imageData.getRight(), frames -> {
        GLGifFrame prevFrame = frames.get(frames.size() - 2);
        frames.add(new GLGifFrame(prevFrame.getTexture(), prevFrame.getImage(), 0));
    });

    public final BiFunction<BufferedImage, Pair<int[], BufferedImage>, BufferedImage> disposalFunction;
    public final Consumer<List<GLGifFrame>> extraConsumer;
}
