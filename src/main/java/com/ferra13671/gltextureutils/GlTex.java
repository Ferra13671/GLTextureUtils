package com.ferra13671.gltextureutils;

public interface GlTex {

    void delete();

    void bind();

    void unbind();

    int getWidth();

    int getHeight();

    int getTexId();
}
