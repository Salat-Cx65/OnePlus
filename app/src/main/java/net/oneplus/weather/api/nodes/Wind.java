package net.oneplus.weather.api.nodes;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.Locale;
import net.oneplus.weather.api.R;
import net.oneplus.weather.api.helper.NumberUtils;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import net.oneplus.weather.widget.shap.Stars;

public class Wind extends AbstractWeather {
    private final Direction mDirection;

    public enum Direction {
        NA {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_na);
            }
        },
        RW {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_rw);
            }
        },
        N {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_n);
            }
        },
        NNE {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_nne);
            }
        },
        NE {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_ne);
            }
        },
        ENE {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_ene);
            }
        },
        E {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_e);
            }
        },
        ESE {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_ese);
            }
        },
        SE {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_se);
            }
        },
        SSE {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_sse);
            }
        },
        S {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_s);
            }
        },
        SSW {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_ssw);
            }
        },
        SW {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_sw);
            }
        },
        WSW {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_wsw);
            }
        },
        W {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_w);
            }
        },
        WNW {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_wnw);
            }
        },
        NW {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_nw);
            }
        },
        NWN {
            public String text(Context context) {
                return context.getString(R.string.api_wind_direction_nwn);
            }
        };

        public abstract String text(Context context);
    }

    public static Direction getDirectionFromAccu(String text) {
        String toLowerCase = text.toLowerCase(Locale.ENGLISH);
        Object obj = -1;
        switch (toLowerCase.hashCode()) {
            case net.oneplus.weather.R.styleable.AppCompatTheme_textAppearanceSearchResultTitle:
                if (toLowerCase.equals("e")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_windowActionBarOverlay:
                if (toLowerCase.equals("n")) {
                    obj = null;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_windowFixedWidthMinor:
                if (toLowerCase.equals("s")) {
                    obj = DetectedActivity.RUNNING;
                }
                break;
            case 119:
                if (toLowerCase.equals("w")) {
                    obj = WeatherCircleView.ARC_DIN;
                }
                break;
            case 3511:
                if (toLowerCase.equals("ne")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 3529:
                if (toLowerCase.equals("nw")) {
                    obj = ConnectionResult.TIMEOUT;
                }
                break;
            case 3666:
                if (toLowerCase.equals("se")) {
                    obj = ConnectionResult.RESOLUTION_REQUIRED;
                }
                break;
            case 3684:
                if (toLowerCase.equals("sw")) {
                    obj = ConnectionResult.DEVELOPER_ERROR;
                }
                break;
            case 100572:
                if (toLowerCase.equals("ene")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case 100727:
                if (toLowerCase.equals("ese")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case 109221:
                if (toLowerCase.equals("nne")) {
                    obj = 1;
                }
                break;
            case 109509:
                if (toLowerCase.equals("nwn")) {
                    obj = ConnectionResult.INTERRUPTED;
                }
                break;
            case 114181:
                if (toLowerCase.equals("sse")) {
                    obj = DetectedActivity.WALKING;
                }
                break;
            case 114199:
                if (toLowerCase.equals("ssw")) {
                    obj = ConnectionResult.SERVICE_INVALID;
                }
                break;
            case 117888:
                if (toLowerCase.equals("wnw")) {
                    obj = ConnectionResult.CANCELED;
                }
                break;
            case 118043:
                if (toLowerCase.equals("wsw")) {
                    obj = ConnectionResult.LICENSE_CHECK_FAILED;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return Direction.N;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return Direction.NNE;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return Direction.NE;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return Direction.ENE;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return Direction.E;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return Direction.ESE;
            case ConnectionResult.RESOLUTION_REQUIRED:
                return Direction.SE;
            case DetectedActivity.WALKING:
                return Direction.SSE;
            case DetectedActivity.RUNNING:
                return Direction.S;
            case ConnectionResult.SERVICE_INVALID:
                return Direction.SSW;
            case ConnectionResult.DEVELOPER_ERROR:
                return Direction.SW;
            case ConnectionResult.LICENSE_CHECK_FAILED:
                return Direction.WSW;
            case WeatherCircleView.ARC_DIN:
                return Direction.W;
            case ConnectionResult.CANCELED:
                return Direction.WNW;
            case ConnectionResult.TIMEOUT:
                return Direction.NW;
            case ConnectionResult.INTERRUPTED:
                return Direction.NWN;
            default:
                return Direction.NA;
        }
    }

    public static Direction getDirectionFromOppo(String text) {
        String toLowerCase = text.toLowerCase(Locale.ENGLISH);
        Object obj = -1;
        switch (toLowerCase.hashCode()) {
            case -589995872:
                if (toLowerCase.equals("\u65cb\u8f6c\u4e0d\u5b9a\u98ce")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case 658994:
                if (toLowerCase.equals("\u4e1c\u98ce")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case 698519:
                if (toLowerCase.equals("\u5317\u98ce")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case 700503:
                if (toLowerCase.equals("\u5357\u98ce")) {
                    obj = DetectedActivity.WALKING;
                }
                break;
            case 1130287:
                if (toLowerCase.equals("\u897f\u98ce")) {
                    obj = ConnectionResult.SERVICE_INVALID;
                }
                break;
            case 19914675:
                if (toLowerCase.equals("\u4e1c\u5317\u98ce")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
            case 19916659:
                if (toLowerCase.equals("\u4e1c\u5357\u98ce")) {
                    obj = ConnectionResult.RESOLUTION_REQUIRED;
                }
                break;
            case 26220013:
                if (toLowerCase.equals("\u65cb\u8f6c\u98ce")) {
                    obj = null;
                }
                break;
            case 34524758:
                if (toLowerCase.equals("\u897f\u5317\u98ce")) {
                    obj = ConnectionResult.DEVELOPER_ERROR;
                }
                break;
            case 34526742:
                if (toLowerCase.equals("\u897f\u5357\u98ce")) {
                    obj = DetectedActivity.RUNNING;
                }
                break;
            case 812250606:
                if (toLowerCase.equals("\u65cb\u8f6c\u4e0d\u5b9a")) {
                    obj = 1;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return Direction.RW;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return Direction.N;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return Direction.NE;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return Direction.E;
            case ConnectionResult.RESOLUTION_REQUIRED:
                return Direction.SE;
            case DetectedActivity.WALKING:
                return Direction.S;
            case DetectedActivity.RUNNING:
                return Direction.SW;
            case ConnectionResult.SERVICE_INVALID:
                return Direction.W;
            case ConnectionResult.DEVELOPER_ERROR:
                return Direction.NW;
            default:
                return Direction.NA;
        }
    }

    public static Direction getDirectionFromSwa(String text) {
        Object obj = -1;
        switch (text.hashCode()) {
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorAccent:
                if (text.equals("0")) {
                    obj = null;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorBackgroundFloating:
                if (text.equals("1")) {
                    obj = 1;
                }
                break;
            case Stars.CIRCLE_COUNT:
                if (text.equals("2")) {
                    obj = RainSurfaceView.RAIN_LEVEL_SHOWER;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorControlActivated:
                if (text.equals("3")) {
                    obj = RainSurfaceView.RAIN_LEVEL_DOWNPOUR;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorControlHighlight:
                if (text.equals("4")) {
                    obj = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorControlNormal:
                if (text.equals("5")) {
                    obj = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorError:
                if (text.equals("6")) {
                    obj = ConnectionResult.RESOLUTION_REQUIRED;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorPrimary:
                if (text.equals("7")) {
                    obj = DetectedActivity.WALKING;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorPrimaryDark:
                if (text.equals("8")) {
                    obj = DetectedActivity.RUNNING;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorSwitchThumbNormal:
                if (text.equals("9")) {
                    obj = ConnectionResult.SERVICE_INVALID;
                }
                break;
        }
        switch (obj) {
            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                return Direction.NA;
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                return Direction.NE;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                return Direction.E;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                return Direction.SE;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                return Direction.S;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                return Direction.SW;
            case ConnectionResult.RESOLUTION_REQUIRED:
                return Direction.W;
            case DetectedActivity.WALKING:
                return Direction.NW;
            case DetectedActivity.RUNNING:
                return Direction.N;
            case ConnectionResult.SERVICE_INVALID:
                return Direction.RW;
            default:
                return Direction.NA;
        }
    }

    public Wind(String areaCode, String dataSource, Direction direction) {
        this(areaCode, null, dataSource, direction);
    }

    public Wind(String areaCode, String areaName, String dataSource, Direction direction) {
        super(areaCode, areaName, dataSource);
        this.mDirection = direction;
    }

    public String getWeatherName() {
        return "Wind";
    }

    public Direction getDirection() {
        return this.mDirection;
    }

    public double getSpeed() {
        return NumberUtils.NAN_DOUBLE;
    }

    public String getWindPower() {
        return StringUtils.EMPTY_STRING;
    }

    public String getWindPower(Context context) {
        return StringUtils.EMPTY_STRING;
    }
}
