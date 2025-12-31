package com.ferra13671.gltextureutils.atlas;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An object that stores the 2D position of a texture.
 */
@Getter
@AllArgsConstructor
public class TexturePos {
    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;

    /**
     * Normalizes texture 2D position to UV coordinates.
     *
     * @param width the width to which the texture position should be normalized.
     * @param height the height to which the texture position should be normalized.
     * @return UV coordinates of a texture.
     */
    public TextureBorder toBorder(int width, int height) {
        return new TextureBorder(
                (float) this.x1 / width,
                (float) this.y1 / height,
                (float) this.x2 / width,
                (float) this.y2 / height
        );
    }
}
