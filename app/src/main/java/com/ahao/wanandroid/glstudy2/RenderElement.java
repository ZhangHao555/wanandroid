package com.ahao.wanandroid.glstudy2;

import java.nio.FloatBuffer;

public abstract class RenderElement {
    public abstract void render();
    protected FloatBuffer vertexBuffer;

    public static final int BYTES_PRT_FLOAT = 4;
}
