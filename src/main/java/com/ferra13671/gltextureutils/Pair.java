package com.ferra13671.gltextureutils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pair<A, B> {
    private A left;
    private B right;
}
