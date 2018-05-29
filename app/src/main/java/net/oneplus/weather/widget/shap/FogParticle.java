package net.oneplus.weather.widget.shap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;
import net.oneplus.weather.widget.anim.BaseAnimation;
import net.oneplus.weather.widget.anim.FogAnimation;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class FogParticle extends SimpleShape {
    public static final float SPEED_UNIT = 0.3f;
    float X;
    float Z;
    private ShortBuffer indexBuffer;
    short[] indices;
    private BaseAnimation mFogAnimation;
    private float mRadius;
    float mXStartSpeed;
    float mYStartSpeed;
    float mZStartSpeed;
    float[] vertices;

    public FogParticle() {
        this(-0.5f, 1.0f, 0.0f);
    }

    public FogParticle(float x, float y, float z) {
        this.X = 0.42573112f;
        this.Z = 0.8506508f;
        this.mRadius = 0.02f;
        this.indices = new short[]{(short) 0, (short) 4, (short) 1, (short) 0, (short) 9, (short) 4, (short) 9, (short) 5, (short) 4, (short) 4, (short) 5, (short) 8, (short) 4, (short) 8, (short) 1, (short) 8, (short) 10, (short) 1, (short) 8, (short) 3, (short) 10, (short) 5, (short) 3, (short) 8, (short) 5, (short) 2, (short) 3, (short) 2, (short) 7, (short) 3, (short) 7, (short) 10, (short) 3, (short) 7, (short) 6, (short) 10, (short) 7, (short) 11, (short) 6, (short) 11, (short) 0, (short) 6, (short) 0, (short) 1, (short) 6, (short) 6, (short) 1, (short) 10, (short) 9, (short) 0, (short) 11, (short) 9, (short) 11, (short) 2, (short) 9, (short) 2, (short) 5, (short) 7, (short) 2, (short) 11};
        this.mFogAnimation = new FogAnimation();
        setXYZ(x, y, z);
        this.X *= this.mRadius;
        this.Z *= this.mRadius;
        this.mXStartSpeed = x;
        this.mYStartSpeed = y;
        this.mZStartSpeed = z;
        ByteBuffer ibb = ByteBuffer.allocateDirect(this.indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        this.indexBuffer = ibb.asShortBuffer();
        this.indexBuffer.put(this.indices);
        this.indexBuffer.position(0);
        init(x, y, z);
    }

    public void setAnimation(BaseAnimation anim) {
        this.mFogAnimation = anim;
    }

    public void init(float x, float y, float z) {
        setXYZ(x, y, z);
        this.vertices = new float[]{(-this.X) + getX(), getY() + 0.0f, this.Z + getZ(), this.X + getX(), getY() + 0.0f, this.Z + getZ(), (-this.X) + getX(), getY() + 0.0f, (-this.Z) + getZ(), this.X + getX(), getY() + 0.0f, (-this.Z) + getZ(), getX() + 0.0f, this.Z + getY(), this.X + getZ(), getX() + 0.0f, this.Z + getY(), (-this.X) + getZ(), getX() + 0.0f, (-this.Z) + getY(), this.X + getZ(), getX() + 0.0f, (-this.Z) + getY(), (-this.X) + getZ(), this.Z + getX(), this.X + getY(), getZ() + 0.0f, (-this.Z) + getX(), this.X + getY(), getZ() + 0.0f, this.Z + getX(), (-this.X) + getY(), getZ() + 0.0f, (-this.Z) + getX(), (-this.X) + getY(), getZ() + 0.0f};
        setVertices(this.vertices);
    }

    public void draw(GL10 gl) {
        super.draw(gl);
        gl.glVertexPointer(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, 5126, 0, getByteBuffer());
        gl.glDrawElements(RainSurfaceView.RAIN_LEVEL_RAINSTORM, this.indices.length, 5123, this.indexBuffer);
    }

    public void move() {
        float[] speed = this.mFogAnimation.next();
        init(getX() + (speed[0] * 0.3f), getY() + (speed[1] * 0.3f), getZ() + (speed[2] * 0.3f));
    }
}
