package com.ahao.particles;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class IndexBuffer {
    private final int bufferId;

    public IndexBuffer(short[] vertexData) {
        final int[] buffers = new int[1];

        GLES20.glGenBuffers(buffers.length, buffers, 0);
        if (buffers[0] == 0) {
            throw new RuntimeException("could not create a new vertex buffer object");
        }

        bufferId = buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, buffers[0]);

        ShortBuffer vertexArray = ByteBuffer.allocate(vertexData.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(vertexData);

        vertexArray.position(0);

        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, vertexArray.capacity() * 2, vertexArray, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, bufferId);

        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, dataOffset);
        GLES20.glEnableVertexAttribArray(attributeLocation);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public int getBufferId() {
        return bufferId;
    }
}
