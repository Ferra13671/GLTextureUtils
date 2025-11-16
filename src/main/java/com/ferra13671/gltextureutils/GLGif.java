package com.ferra13671.gltextureutils;

import com.ferra13671.gltextureutils.builder.GLGifInfo;
import com.ferra13671.gltextureutils.loader.TextureLoaders;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GLGif implements GlTex {
    protected final List<GLGifFrame> frames = new ArrayList<>();

    protected GLGifFrame currentFrame;
    protected int currentFrameId = 0;

    protected long lastUpdateTime = System.currentTimeMillis();

    public GLGif(String name, GLGifInfo info) {
        try {
            ImageInputStream stream = ImageIO.createImageInputStream(info.getInputStream());
            Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);

            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                reader.setInput(stream);

                int numFrames = reader.getNumImages(true);
                BufferedImage prevImage = null;
                for (int i = 0; i < numFrames; i++) {
                    BufferedImage image = reader.read(i);
                    IIOMetadata metadata = reader.getImageMetadata(i);
                    Node nodeMetadata = metadata.getAsTree(
                            metadata.getNativeMetadataFormatName()
                    );

                    AtomicInteger delay = new AtomicInteger(-1);
                    AtomicReference<int[]> offsets = new AtomicReference<>(new int[]{0, 0});
                    AtomicReference<Disposal> disposal = new AtomicReference<>(null);

                    Utils.checkNode(
                            nodeMetadata,
                            node -> {
                                if (node.getNodeName().equals("GraphicControlExtension")) {
                                    NamedNodeMap attrs = node.getAttributes();
                                    String delayStr = attrs.getNamedItem("delayTime").getNodeValue();

                                    delay.set(Integer.parseInt(delayStr) * 10);


                                    String d = attrs.getNamedItem("disposalMethod").getNodeValue();

                                    disposal.set(
                                            (d.equalsIgnoreCase("none") || d.equalsIgnoreCase("doNotDispose")) ?
                                                    Disposal.None
                                                    : d.equalsIgnoreCase("restoreToBackgroundColor") ?
                                                    Disposal.ToBackground
                                                    : d.equalsIgnoreCase("restoreToPrevious") ?
                                                    Disposal.ToPrevious : null
                                    );
                                }

                                if (node.getNodeName().equals("ImageDescriptor")) {
                                    NamedNodeMap attrs = node.getAttributes();

                                    offsets.set(new int[]{
                                            Integer.parseInt(attrs.getNamedItem("imageLeftPosition").getNodeValue()),
                                            Integer.parseInt(attrs.getNamedItem("imageTopPosition").getNodeValue())
                                    });
                                }
                            }
                    );


                    if (prevImage == null)
                        prevImage = image;
                    image = disposal.get().disposalFunction.apply(prevImage, new Pair<>(offsets.get(), image));

                    this.frames.add(
                            new GLGifFrame(
                                    TextureLoaders.BUFFERED_IMAGE.createTextureBuilder()
                                        .name(name.concat("-frame" + numFrames))
                                        .path(image)
                                        .filtering(info.getFiltering())
                                        .wrapping(info.getWrapping())
                                        .build(),
                                    image,
                                    delay.get())
                    );

                    disposal.get().extraConsumer.accept(this.frames);

                    prevImage = image;
                }
            }

            this.currentFrame = this.frames.get(0);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public void delete() {
        for (GLGifFrame frame : this.frames)
            frame.getTexture().delete();
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
    public int getWidth() {
        return this.currentFrame.getTexture().getWidth();
    }

    @Override
    public int getHeight() {
        return this.currentFrame.getTexture().getHeight();
    }

    @Override
    public int getTexId() {
        updateFrame();
        return this.currentFrame.getTexture().getTexId();
    }

    private void updateFrame() {
        while (this.lastUpdateTime + this.currentFrame.getDelay() <= System.currentTimeMillis())
            setCurrentFrame(this.currentFrameId++ % (this.frames.size() - 1));
    }

    private void setCurrentFrame(int frameId) {
        this.lastUpdateTime = this.lastUpdateTime + this.currentFrame.getDelay();
        this.currentFrame = this.frames.get(frameId);
    }
}
