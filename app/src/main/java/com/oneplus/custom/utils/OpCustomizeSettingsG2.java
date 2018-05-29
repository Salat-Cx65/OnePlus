package com.oneplus.custom.utils;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.oneplus.custom.utils.OpCustomizeSettings.CUSTOM_BACK_COVER_TYPE;
import com.oneplus.custom.utils.OpCustomizeSettings.CUSTOM_TYPE;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class OpCustomizeSettingsG2 extends OpCustomizeSettings {
    private static final String custom_back_cover_fn = "/sys/module/param_read_write/parameters/backcover_color";
    private static final String custom_fn = "/sys/module/param_read_write/parameters/cust_flag";

    protected CUSTOM_TYPE getCustomization() {
        Exception e;
        CUSTOM_TYPE result = CUSTOM_TYPE.NONE;
        File themeStateFile = new File(custom_fn);
        if (!themeStateFile.exists()) {
            return result;
        }
        BufferedReader br = null;
        try {
            BufferedReader br2 = new BufferedReader(new FileReader(themeStateFile));
            while (true) {
                try {
                    String line = br2.readLine();
                    if (line != null) {
                        Object obj = -1;
                        switch (line.hashCode()) {
                            case R.styleable.AppCompatTheme_colorControlActivated:
                                if (line.equals("3")) {
                                    obj = null;
                                }
                                break;
                        }
                        switch (obj) {
                            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                                result = CUSTOM_TYPE.AVG;
                                break;
                            default:
                                break;
                        }
                    } else {
                        if (br2 != null) {
                            try {
                                br2.close();
                            } catch (Exception e2) {
                                Log.e("OpCustomizeSettings", e2.getMessage());
                            }
                        }
                        return result;
                    }
                } catch (Exception e3) {
                    e2 = e3;
                    br = br2;
                } catch (Throwable th) {
                    br = br2;
                }
            }
        } catch (Exception e4) {
            e2 = e4;
            try {
                Log.e("OpCustomizeSettings", e2.getMessage());
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e22) {
                        Log.e("OpCustomizeSettings", e22.getMessage());
                    }
                }
                return result;
            } catch (Throwable th2) {
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e222) {
                        Log.e("OpCustomizeSettings", e222.getMessage());
                    }
                }
                return result;
            }
        }
    }

    protected CUSTOM_BACK_COVER_TYPE getCustomBackCoverType() {
        Exception e;
        CUSTOM_BACK_COVER_TYPE result = CUSTOM_BACK_COVER_TYPE.NONE;
        File custFile = new File(custom_back_cover_fn);
        if (!custFile.exists()) {
            return result;
        }
        BufferedReader br = null;
        try {
            BufferedReader br2 = new BufferedReader(new FileReader(custFile));
            while (true) {
                try {
                    String line = br2.readLine();
                    if (line != null) {
                        Object obj = -1;
                        switch (line.hashCode()) {
                            case -766480814:
                                if (line.equals("ff2c2630")) {
                                    obj = 1;
                                }
                                break;
                            case -736897359:
                                if (line.equals("ff3d3740")) {
                                    obj = null;
                                }
                                break;
                            case 682228274:
                                if (line.equals("fff6f7f7")) {
                                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                                }
                                break;
                            case 933263922:
                                if (line.equals("FF2C2630")) {
                                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                                }
                                break;
                            case 962847377:
                                if (line.equals("FF3D3740")) {
                                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                                }
                                break;
                            case 1494438546:
                                if (line.equals("FFF6F7F7")) {
                                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                                }
                                break;
                        }
                        switch (obj) {
                            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                                result = CUSTOM_BACK_COVER_TYPE.MYH;
                                break;
                            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                                result = CUSTOM_BACK_COVER_TYPE.LCH;
                                break;
                            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                                result = CUSTOM_BACK_COVER_TYPE.YYB;
                                break;
                            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                                result = CUSTOM_BACK_COVER_TYPE.MYH;
                                break;
                            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                                result = CUSTOM_BACK_COVER_TYPE.LCH;
                                break;
                            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                                result = CUSTOM_BACK_COVER_TYPE.YYB;
                                break;
                            default:
                                break;
                        }
                    } else {
                        if (br2 != null) {
                            try {
                                br2.close();
                            } catch (Exception e2) {
                                Log.e("OpCustomizeSettings", e2.getMessage());
                            }
                        }
                        return result;
                    }
                } catch (Exception e3) {
                    e2 = e3;
                    br = br2;
                } catch (Throwable th) {
                    br = br2;
                }
            }
        } catch (Exception e4) {
            e2 = e4;
            try {
                Log.e("OpCustomizeSettings", e2.getMessage());
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e22) {
                        Log.e("OpCustomizeSettings", e22.getMessage());
                    }
                }
                return result;
            } catch (Throwable th2) {
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e222) {
                        Log.e("OpCustomizeSettings", e222.getMessage());
                    }
                }
                return result;
            }
        }
    }

    protected long getCustomBackCoverColor() {
        Exception e;
        long result = 0;
        File custFile = new File(custom_back_cover_fn);
        if (!custFile.exists()) {
            return 0;
        }
        BufferedReader br = null;
        try {
            BufferedReader br2 = new BufferedReader(new FileReader(custFile));
            while (true) {
                try {
                    String line = br2.readLine();
                    if (line == null) {
                        break;
                    }
                    result = Long.parseLong(line, ConnectionResult.API_UNAVAILABLE) & -1;
                } catch (Exception e2) {
                    e = e2;
                    br = br2;
                } catch (Throwable th) {
                    br = br2;
                }
            }
            if (br2 != null) {
                try {
                    br2.close();
                } catch (Exception e3) {
                    Log.e("OpCustomizeSettings", e3.getMessage());
                }
            }
            return result;
        } catch (Exception e4) {
            e3 = e4;
            try {
                Log.e("OpCustomizeSettings", e3.getMessage());
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e32) {
                        Log.e("OpCustomizeSettings", e32.getMessage());
                    }
                }
                return result;
            } catch (Throwable th2) {
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e322) {
                        Log.e("OpCustomizeSettings", e322.getMessage());
                    }
                }
                return result;
            }
        }
    }
}
