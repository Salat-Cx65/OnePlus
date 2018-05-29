package net.oneplus.weather.widget.openglbase;

import android.os.SystemClock;
import com.android.volley.DefaultRetryPolicy;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;
import javax.microedition.khronos.opengles.GL10;
import net.oneplus.weather.api.WeatherType;

public class Rain {
    public static int Z_RANDOM_RANGE;
    protected static float[][] colorValue;
    protected float HEIGHT_CHANGE_RANGE;
    protected float HEIGHT_START_OFFSET;
    protected float RECT_WIDTH_CHANGE_RANGE;
    protected float WIDTH_CHANGE_RANGE;
    protected float WIDTH_START_OFFSET;
    protected int X_RANDOM_RANGE;
    protected int Y_OFFSET_LIMIT;
    protected int Y_RANDOM_RANGE;
    float[] alphaArray;
    float[][] colorArray;
    protected float[][] colorValueNight;
    protected boolean isDay;
    protected float mAlpha;
    protected int numLines;
    protected int numOfOneGroup;
    protected int numRect;
    protected FloatBuffer vertex;
    float[] vertexArray;
    protected FloatBuffer vertexRect;
    float[] vertexRectArray;
    float[] widthArray;

    static {
        Z_RANDOM_RANGE = 30;
        colorValue = new float[][]{new float[]{1.0f, 1.0f, 1.0f}, new float[]{1.0f, 1.0f, 1.0f}, new float[]{1.0f, 0.7921569f, 0.0f}};
    }

    protected void RandomLine(int index) {
        Random random = new Random(SystemClock.currentThreadTimeMillis() * ((long) index));
        float x = (((float) (this.X_RANDOM_RANGE * random.nextInt(10000))) / 10000.0f) - ((float) (this.X_RANDOM_RANGE / 2));
        float y = ((float) this.Y_RANDOM_RANGE) * (0.5f + (((float) random.nextInt(10000)) / 10000.0f));
        float randomZ = (((float) random.nextInt(10000)) / 10000.0f) - 0.5f;
        float z = ((float) Z_RANDOM_RANGE) * randomZ;
        this.vertexArray[this.numOfOneGroup * index] = x;
        this.vertexArray[(this.numOfOneGroup * index) + 1] = y;
        this.vertexArray[(this.numOfOneGroup * index) + 2] = z;
        this.vertexArray[(this.numOfOneGroup * index) + 3] = x;
        float radio = this.HEIGHT_START_OFFSET + (((float) random.nextInt(10000)) / 10000.0f);
        float[] fArr = this.vertexArray;
        int i = (this.numOfOneGroup * index) + 4;
        float f = this.HEIGHT_CHANGE_RANGE;
        if (radio > 1.0f) {
            radio = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        }
        fArr[i] = (f * radio) + y;
        this.vertexArray[(this.numOfOneGroup * index) + 5] = z;
        if (this.isDay) {
            this.colorArray[index] = colorValue[random.nextInt(10000) % colorValue.length];
        } else {
            this.colorArray[index] = this.colorValueNight[random.nextInt(10000) % this.colorValueNight.length];
        }
        this.alphaArray[index] = 1.0f - Math.abs(randomZ / 0.5f);
        this.widthArray[index] = this.WIDTH_CHANGE_RANGE * (this.WIDTH_START_OFFSET + (((float) random.nextInt(10000)) / 10000.0f));
    }

    protected void RandomRect(int index) {
        Random random = new Random(SystemClock.currentThreadTimeMillis() * ((long) index));
        float x = (((float) (this.X_RANDOM_RANGE * random.nextInt(10000))) / 10000.0f) - ((float) (this.X_RANDOM_RANGE / 2));
        float y = ((float) this.Y_RANDOM_RANGE) * (0.5f + (((float) random.nextInt(10000)) / 10000.0f));
        float nextInt = ((float) Z_RANDOM_RANGE) * ((((float) random.nextInt(10000)) / 10000.0f) - 0.5f);
        nextInt = (float) ((-Z_RANDOM_RANGE) / 2);
        if (this.isDay) {
            this.colorArray[index] = colorValue[random.nextInt(10000) % colorValue.length];
        } else {
            this.colorArray[index] = this.colorValueNight[random.nextInt(10000) % this.colorValueNight.length];
        }
        float widht = this.RECT_WIDTH_CHANGE_RANGE * (this.WIDTH_START_OFFSET + (((float) random.nextInt(10000)) / 10000.0f));
        float radio = this.HEIGHT_START_OFFSET + (((float) random.nextInt(10000)) / 10000.0f);
        this.vertexRectArray[index * 12] = x;
        this.vertexRectArray[(index * 12) + 1] = y;
        this.vertexRectArray[(index * 12) + 2] = nextInt;
        this.vertexRectArray[(index * 12) + 3] = x;
        this.vertexRectArray[(index * 12) + 4] = ((this.HEIGHT_CHANGE_RANGE * radio) * 2.0f) + y;
        this.vertexRectArray[(index * 12) + 5] = nextInt;
        this.vertexRectArray[(index * 12) + 6] = (0.04f * widht) + x;
        this.vertexRectArray[(index * 12) + 7] = y;
        this.vertexRectArray[(index * 12) + 8] = nextInt;
        this.vertexRectArray[(index * 12) + 9] = (0.04f * widht) + x;
        this.vertexRectArray[(index * 12) + 10] = ((this.HEIGHT_CHANGE_RANGE * radio) * 2.0f) + y;
        this.vertexRectArray[(index * 12) + 11] = nextInt;
    }

    protected void RandomLineInit(int index) {
        Random random = new Random(SystemClock.currentThreadTimeMillis() * ((long) index));
        float x = (((float) (this.X_RANDOM_RANGE * random.nextInt(10000))) / 10000.0f) - ((float) (this.X_RANDOM_RANGE / 2));
        float y = ((float) (this.Y_RANDOM_RANGE * 4)) * ((((float) random.nextInt(10000)) / 10000.0f) - 0.5f);
        float randomZ = (((float) random.nextInt(10000)) / 10000.0f) - 0.5f;
        float z = ((float) Z_RANDOM_RANGE) * randomZ;
        this.vertexArray[this.numOfOneGroup * index] = x;
        this.vertexArray[(this.numOfOneGroup * index) + 1] = y;
        this.vertexArray[(this.numOfOneGroup * index) + 2] = z;
        this.vertexArray[(this.numOfOneGroup * index) + 3] = x;
        float radio = this.HEIGHT_START_OFFSET + (((float) random.nextInt(10000)) / 10000.0f);
        float[] fArr = this.vertexArray;
        int i = (this.numOfOneGroup * index) + 4;
        float f = this.HEIGHT_CHANGE_RANGE;
        if (radio > 1.0f) {
            radio = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        }
        fArr[i] = (f * radio) + y;
        this.vertexArray[(this.numOfOneGroup * index) + 5] = z;
        if (this.isDay) {
            this.colorArray[index] = colorValue[random.nextInt(10000) % colorValue.length];
        } else {
            this.colorArray[index] = this.colorValueNight[random.nextInt(10000) % this.colorValueNight.length];
        }
        this.alphaArray[index] = 1.0f - Math.abs(randomZ / 0.5f);
        this.widthArray[index] = this.WIDTH_CHANGE_RANGE * (this.WIDTH_START_OFFSET + (((float) random.nextInt(10000)) / 10000.0f));
    }

    public Rain() {
        this.numLines = 300;
        this.numRect = 5;
        this.numOfOneGroup = 6;
        this.X_RANDOM_RANGE = 20;
        this.Y_RANDOM_RANGE = 20;
        this.Y_OFFSET_LIMIT = -60;
        this.HEIGHT_START_OFFSET = 0.6666667f;
        this.WIDTH_START_OFFSET = 0.2f;
        this.HEIGHT_CHANGE_RANGE = 1.5f;
        this.WIDTH_CHANGE_RANGE = 5.0f;
        this.RECT_WIDTH_CHANGE_RANGE = 5.0f;
        this.colorValueNight = new float[][]{new float[]{0.5058824f, 0.6666667f, 0.83137256f}, new float[]{0.6431373f, 0.50980395f, 0.0f}};
        this.mAlpha = 1.0f;
        this.isDay = false;
        this.vertexArray = new float[(this.numLines * this.numOfOneGroup)];
        this.vertexRectArray = new float[(this.numRect * 12)];
        this.colorArray = (float[][]) Array.newInstance(Float.TYPE, new int[]{this.numLines, 3});
        this.alphaArray = new float[this.numLines];
        this.widthArray = new float[this.numLines];
        init();
    }

    protected void init() {
        int i;
        this.vertexArray = new float[(this.numLines * this.numOfOneGroup)];
        this.vertexRectArray = new float[(this.numRect * 12)];
        this.colorArray = (float[][]) Array.newInstance(Float.TYPE, new int[]{this.numLines, 3});
        this.alphaArray = new float[this.numLines];
        this.widthArray = new float[this.numLines];
        for (i = 0; i < this.numLines; i++) {
            RandomLineInit(i);
        }
        for (i = 0; i < this.numRect; i++) {
            RandomRect(i);
        }
        initVertex();
    }

    public void setAlpha(float alpha) {
        this.mAlpha = alpha;
    }

    public void setDay(boolean isDay) {
        this.isDay = isDay;
    }

    protected void changePostion(float xoffset, float yoffset) {
        for (int i = 0; i < this.numLines; i++) {
            if (this.vertexArray[(this.numOfOneGroup * i) + 4] < ((float) this.Y_OFFSET_LIMIT)) {
                RandomLine(i);
            } else {
                float f;
                if (this.alphaArray[i] > 0.5f) {
                    f = this.alphaArray[i];
                } else {
                    f = 0.5f;
                }
                float alphaYoffset = (f * yoffset) * 1.1f;
                if (alphaYoffset <= yoffset) {
                    alphaYoffset = yoffset;
                }
                float[] fArr = this.vertexArray;
                int i2 = this.numOfOneGroup * i;
                fArr[i2] = fArr[i2] + xoffset;
                fArr = this.vertexArray;
                i2 = (this.numOfOneGroup * i) + 1;
                fArr[i2] = fArr[i2] + alphaYoffset;
                fArr = this.vertexArray;
                i2 = (this.numOfOneGroup * i) + 3;
                fArr[i2] = fArr[i2] + xoffset;
                fArr = this.vertexArray;
                i2 = (this.numOfOneGroup * i) + 4;
                fArr[i2] = fArr[i2] + alphaYoffset;
                if (i < this.numRect) {
                    if (this.vertexArray[(this.numOfOneGroup * i) + 4] < ((float) this.Y_OFFSET_LIMIT)) {
                        RandomRect(i);
                    }
                    alphaYoffset = (float) (((double) alphaYoffset) * 1.3d);
                    fArr = this.vertexRectArray;
                    i2 = i * 12;
                    fArr[i2] = fArr[i2] + xoffset;
                    fArr = this.vertexRectArray;
                    i2 = (i * 12) + 1;
                    fArr[i2] = fArr[i2] + alphaYoffset;
                    fArr = this.vertexRectArray;
                    i2 = (i * 12) + 3;
                    fArr[i2] = fArr[i2] + xoffset;
                    fArr = this.vertexRectArray;
                    i2 = (i * 12) + 4;
                    fArr[i2] = fArr[i2] + alphaYoffset;
                    fArr = this.vertexRectArray;
                    i2 = (i * 12) + 6;
                    fArr[i2] = fArr[i2] + xoffset;
                    fArr = this.vertexRectArray;
                    i2 = (i * 12) + 7;
                    fArr[i2] = fArr[i2] + alphaYoffset;
                    fArr = this.vertexRectArray;
                    i2 = (i * 12) + 9;
                    fArr[i2] = fArr[i2] + xoffset;
                    fArr = this.vertexRectArray;
                    i2 = (i * 12) + 10;
                    fArr[i2] = fArr[i2] + alphaYoffset;
                }
            }
        }
        initVertex();
    }

    protected void initVertex() {
        ByteBuffer vbb = ByteBuffer.allocateDirect(this.vertexArray.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        this.vertex = vbb.asFloatBuffer();
        this.vertex.put(this.vertexArray);
        this.vertex.position(0);
        ByteBuffer vbb2 = ByteBuffer.allocateDirect(this.vertexRectArray.length * 4);
        vbb2.order(ByteOrder.nativeOrder());
        this.vertexRect = vbb2.asFloatBuffer();
        this.vertexRect.put(this.vertexRectArray);
        this.vertexRect.position(0);
    }

    public void draw(GL10 gl, float xoffset, float yoffset) {
        gl.glFrontFace(2305);
        gl.glEnable(2884);
        gl.glCullFace(WeatherType.ACCU_WEATHER_MOSTLY_CLEAR);
        drawRect(gl, xoffset, yoffset);
        drawLinesRain(gl, xoffset, yoffset);
        gl.glDisable(2884);
    }

    protected void drawRect(GL10 gl, float xoffset, float yoffset) {
        gl.glEnableClientState(32884);
        changePostion(xoffset, yoffset);
        gl.glVertexPointer(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, 5126, 0, this.vertexRect);
        for (int pos = 0; pos < this.numRect; pos++) {
            gl.glColor4f(this.colorArray[pos][0], this.colorArray[pos][1], this.colorArray[pos][2], this.mAlpha);
            gl.glDrawArrays(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, pos * 4, RainSurfaceView.RAIN_LEVEL_RAINSTORM);
        }
        gl.glDisableClientState(32884);
        gl.glFinish();
    }

    protected void drawLinesRain(GL10 gl, float xoffset, float yoffset) {
        gl.glEnableClientState(32884);
        changePostion(xoffset, yoffset);
        gl.glVertexPointer(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, 5126, 0, this.vertex);
        for (int pos = 0; pos < this.numLines; pos++) {
            gl.glLineWidth(this.widthArray[pos]);
            gl.glColor4f(this.colorArray[pos][0], this.colorArray[pos][1], this.colorArray[pos][2], this.alphaArray[pos] * this.mAlpha);
            gl.glDrawArrays(1, pos * 2, RainSurfaceView.RAIN_LEVEL_SHOWER);
        }
        gl.glDisableClientState(32884);
        gl.glFinish();
    }
}
