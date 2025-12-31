package com.ferra13671.gltextureutils.atlas;

import com.ferra13671.gltextureutils.*;
import com.ferra13671.gltextureutils.loader.TextureLoaders;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * A single large (or not) texture containing many other subtextures.
 * For each subtexture, the atlas stores its UV coordinates, which can be got using the texture name.
 * <p>
 * TextureAtlas allows you to combine multiple draw calls for each small texture into one, which will use the atlas.
 * This allows for optimized rendering of a large number of objects with different textures.
 */
public class TextureAtlas implements GlTex {
    private final GLTexture texture;
    private final HashMap<String, TextureBorder> borders = new HashMap<>();

    /**
     * @param name texture name.
     * @param textures list of textures that should be added to the atlas.
     */
    public TextureAtlas(String name, List<GLTexture> textures) {
        textures.sort(Comparator.comparingInt(tex -> tex.getWidth() * tex.getHeight()));
        Collections.reverse(textures);

        TextureAtlasScheme scheme = calculateAtlasScheme(textures);

        this.texture = TextureLoaders.INPUT_STREAM.createTextureBuilder()
                .name(name)
                .info(scheme.getTextureWidth(), scheme.getTextureHeight())
                .build();

        scheme.getPoses().forEach((tex, texturePos) -> {
            this.texture.drawImage(tex, texturePos.getX1(), texturePos.getY1());
            this.borders.put(tex.getName(), texturePos.toBorder(this.texture.getWidth(), this.texture.getHeight()));
        });
    }

    private static TextureAtlasScheme calculateAtlasScheme(List<GLTexture> textures) {
        HashMap<GLTexture, TexturePos> poses = new HashMap<>();

        int xLength = (int) Math.sqrt(textures.size());

        int maxHeight = 0;
        int maxWidth = 0;
        int x = 0;
        int y = 0;

        int i = 0;
        for (GLTexture tex : textures) {
            if (i % xLength == 0) {
                x = 0;
                y += maxHeight;
                maxHeight = 0;
            }

            poses.put(tex, new TexturePos(x, y, x + tex.getWidth(), y + tex.getHeight()));

            x += tex.getWidth();
            maxHeight = Math.max(maxHeight, tex.getHeight());
            maxWidth = Math.max(maxWidth, x);

            i++;
        }

        return new TextureAtlasScheme(maxWidth, y + maxHeight, poses);
    }

    /**
     * Returns the UV coordinates of a subtexture in the atlas.
     *
     * @param texture texture for which you need to find UV coordinates.
     * @return UV coordinates or null if none were found.
     */
    public TextureBorder getBorder(GLTexture texture) {
        return this.borders.get(texture.getName());
    }

    /**
     * Returns the UV coordinates of a subtexture in the atlas.
     *
     * @param textureName texture name for which you need to find UV coordinates.
     * @return UV coordinates or null if none were found.
     */
    public TextureBorder getBorder(String textureName) {
        return this.borders.get(textureName);
    }

    @Override
    public void delete() {
        this.texture.delete();
    }

    @Override
    public void bind() {
        this.texture.bind();
    }

    @Override
    public void unbind() {
        this.texture.unbind();
    }

    @Override
    public TextureFiltering getFiltering() {
        return this.texture.getFiltering();
    }

    @Override
    public void setFiltering(TextureFiltering textureFiltering) {
        this.texture.setFiltering(textureFiltering);
    }

    @Override
    public TextureWrapping getWrapping() {
        return this.texture.getWrapping();
    }

    @Override
    public void setWrapping(TextureWrapping textureWrapping) {
        this.texture.setWrapping(textureWrapping);
    }

    @Override
    public ColorMode getColorMode() {
        return this.texture.getColorMode();
    }

    @Override
    public int getWidth() {
        return this.texture.getWidth();
    }

    @Override
    public int getHeight() {
        return this.texture.getHeight();
    }

    @Override
    public int getTexId() {
        return this.texture.getTexId();
    }
}
