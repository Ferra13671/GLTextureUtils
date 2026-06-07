package com.ferra13671.gltextureutils.atlas;

/**
 * An object that stores the 2D position of a texture.
 */
public record TexturePos(int x1, int y1, int x2, int y2) {
    /**
     * Normalizes texture 2D position to UV coordinates.
     *
     * @param width  the width to which the texture position should be normalized.
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
