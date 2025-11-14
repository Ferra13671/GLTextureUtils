package com.ferra13671.gltextureutils.utils.gif;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Ferra13671
 * @LastUpdate 1.6.3
 */

public class GifData {
    public final List<BufferedImage> images;
    public final int updateDelay;

    public GifData(List<BufferedImage> images, int updateDelay) {
        this.images = images;
        this.updateDelay = updateDelay;
    }
}
