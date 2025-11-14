package com.ferra13671.gltextureutils.gif;

import com.ferra13671.gltextureutils.utils.gif.GifUtils;

/**
 * @author Ferra13671
 * @LastUpdate 1.6.3
 */

public enum DecompileMode {
    DELTAS(GifUtils::decompileDeltas),
    FULL(GifUtils::decompileFull);

    public final GifDecompiler gifDecompiler;

    DecompileMode(GifDecompiler gifDecompiler) {
        this.gifDecompiler = gifDecompiler;
    }
}