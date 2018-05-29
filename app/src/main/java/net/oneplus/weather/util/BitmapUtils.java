package net.oneplus.weather.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import com.google.android.gms.location.LocationRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import net.oneplus.weather.app.BaseApplication;
import net.oneplus.weather.widget.WeatherCircleView;

public class BitmapUtils {
    private static final int MEMORY_SIZE_LIMIT = 20971520;
    public static final String SHARE_IMAGE_PATH;
    private static final long SHARE_IMAGE_SIZE_LIMIT = 4194304;
    private static final String TAG = "BitmapUtils";

    static {
        SHARE_IMAGE_PATH = Environment.getExternalStorageDirectory() + "/OPWeather/";
    }

    public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        System.out.println("h:" + h);
        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Config.RGB_565);
        scrollView.draw(new Canvas(bitmap));
        return bitmap;
    }

    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, LocationRequest.PRIORITY_HIGH_ACCURACY, baos);
        int options = LocationRequest.PRIORITY_HIGH_ACCURACY;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            image.compress(CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        return BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()), null, null);
    }

    private static File compressBitmap(Bitmap bitmap, File file, CompressFormat format, int quality) {
        FileOutputStream fos = null;
        try {
            FileOutputStream fos2 = new FileOutputStream(file);
            try {
                bitmap.compress(format, quality, fos2);
                if (fos2 != null) {
                    try {
                        fos2.flush();
                        fos2.close();
                        fos = fos2;
                    } catch (IOException e) {
                        fos = fos2;
                    }
                }
            } catch (Exception e2) {
                e = e2;
                fos = fos2;
                Log.e(TAG, "compressBitmap photo fail. " + e.toString());
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
                return file;
            } catch (Throwable th) {
                th = th;
                fos = fos2;
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            try {
                Exception e4;
                Log.e(TAG, "compressBitmap photo fail. " + e4.toString());
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e5) {
                    }
                }
            } catch (Throwable th2) {
                Throwable th3;
                th3 = th2;
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e6) {
                    }
                }
                throw th3;
            }
            return file;
        }
        return file;
    }

    public static void savePic(Bitmap b, String fname) {
        FileNotFoundException e;
        FileOutputStream fileOutputStream;
        IOException e2;
        if (!TextUtils.isEmpty(fname)) {
            new File(fname).deleteOnExit();
            try {
                FileOutputStream fos = new FileOutputStream(fname);
                if (fos != null) {
                    try {
                        b.compress(CompressFormat.PNG, WeatherCircleView.START_ANGEL_90, fos);
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e3) {
                        e = e3;
                        fileOutputStream = fos;
                        e.printStackTrace();
                    } catch (IOException e4) {
                        e2 = e4;
                        fileOutputStream = fos;
                        e2.printStackTrace();
                    }
                }
                fileOutputStream = fos;
            } catch (FileNotFoundException e5) {
                e = e5;
                e.printStackTrace();
            } catch (IOException e6) {
                e2 = e6;
                e2.printStackTrace();
            }
        }
    }

    public static void savePicByLimit(Bitmap bitmap, String fname) {
        Exception e;
        Throwable th;
        if (!TextUtils.isEmpty(fname)) {
            File pngFile = new File(fname);
            pngFile.deleteOnExit();
            FileOutputStream fos = null;
            try {
                FileOutputStream fos2 = new FileOutputStream(pngFile);
                try {
                    bitmap.compress(CompressFormat.PNG, LocationRequest.PRIORITY_HIGH_ACCURACY, fos2);
                    if (pngFile.length() > 4194304) {
                        pngFile.delete();
                        File pngFile2 = new File(fname);
                        int i = LocationRequest.PRIORITY_HIGH_ACCURACY;
                        while (compressBitmap(bitmap, pngFile2, CompressFormat.JPEG, i).length() >= 4194304) {
                            try {
                                i -= 2;
                            } catch (Exception e2) {
                                e = e2;
                                fos = fos2;
                                pngFile = pngFile2;
                            } catch (Throwable th2) {
                                th = th2;
                                fos = fos2;
                                pngFile = pngFile2;
                            }
                        }
                        pngFile = pngFile2;
                    }
                    MediaUtil.getInstace().scanFile(BaseApplication.getContext(), pngFile.toString(), "image/png");
                    if (fos2 != null) {
                        try {
                            fos2.flush();
                            fos2.close();
                        } catch (IOException e3) {
                            fos = fos2;
                        }
                    }
                    fos = fos2;
                } catch (Exception e4) {
                    e = e4;
                    fos = fos2;
                    Log.e(TAG, "savePicByLimit fail. " + e.toString());
                    if (fos != null) {
                        fos.flush();
                        fos.close();
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fos = fos2;
                    if (fos != null) {
                        fos.flush();
                        fos.close();
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                try {
                    Log.e(TAG, "savePicByLimit fail. " + e.toString());
                    if (fos != null) {
                        try {
                            fos.flush();
                            fos.close();
                        } catch (IOException e6) {
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    if (fos != null) {
                        try {
                            fos.flush();
                            fos.close();
                        } catch (IOException e7) {
                        }
                    }
                    throw th;
                }
            }
        }
    }

    public static String getPicFileName(String location, Context context) {
        File outfile = new File(SHARE_IMAGE_PATH);
        if (outfile.isDirectory()) {
            File[] childFile = outfile.listFiles();
            if (!(childFile == null || childFile.length == 0)) {
                for (File f : childFile) {
                    if (f.delete() && MediaUtil.getInstace().getImageContentUri(context, f) != null) {
                        context.getContentResolver().delete(MediaUtil.getInstace().getImageContentUri(context, f), null, null);
                    }
                }
            }
        } else {
            try {
                outfile.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return SHARE_IMAGE_PATH + location.hashCode() + System.currentTimeMillis() + ".png";
    }

    public static boolean isEnoughMemoryForBitmap(View view) {
        Runtime runtime = Runtime.getRuntime();
        return ((long) (20971520 + ((view.getWidth() * view.getHeight()) * 4))) < runtime.maxMemory() - runtime.totalMemory();
    }

    public static int getMaxCanvasHeight() {
        return new Canvas().getMaximumBitmapHeight();
    }
}
