package net.oneplus.weather.widget.shap;

import java.nio.FloatBuffer;

public abstract class SimpleShape extends BaseShape {
    private FloatBuffer mVerBuffer;
    private float[] mVertices;

    protected void initVertex() {
        this.mVerBuffer = floatToBuffer(this.mVertices);
    }

    public void setVertices(float[] vertices) {
        this.mVertices = vertices;
        initVertex();
    }

    public float[] getVertices() {
        return this.mVertices;
    }

    public FloatBuffer getByteBuffer() {
        return this.mVerBuffer;
    }
}
