package com.ferra13671.gltextureutils.loader;

import com.ferra13671.gltextureutils.builder.GLGifInfo;
import com.ferra13671.gltextureutils.gif.DecompileMode;

/**
 * @author Ferra13671
 * @LastUpdate 1.6
 */

public interface GifLoader {

    GLGifInfo load(DecompileMode decompileMode) throws Exception;
}
