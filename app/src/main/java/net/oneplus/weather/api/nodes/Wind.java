package net.oneplus.weather.api.nodes;

import android.content.Context;

import net.oneplus.weather.R;
import net.oneplus.weather.api.helper.NumberUtils;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.shap.Stars;

import java.util.Locale;

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
        int obj = -1;
        switch (toLowerCase.hashCode()) {
            case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle:
                if (toLowerCase.equals("e")) {
                    obj = 4;
                }
                break;
            case R.styleable.AppCompatTheme_windowActionBarOverlay:
                if (toLowerCase.equals("n")) {
                    obj = -1;
                }
                break;
            case R.styleable.AppCompatTheme_windowFixedWidthMinor:
                if (toLowerCase.equals("s")) {
                    obj = 8;
                }
                break;
            case 119:
                if (toLowerCase.equals("w")) {
                    obj = WeatherCircleView.ARC_DIN;
                }
                break;
            case 3511:
                if (toLowerCase.equals("ne")) {
                    obj = 2;
                }
                break;
            case 3529:
                if (toLowerCase.equals("nw")) {
                    obj = 14;
                }
                break;
            case 3666:
                if (toLowerCase.equals("se")) {
                    obj = 6;
                }
                break;
            case 3684:
                if (toLowerCase.equals("sw")) {
                    obj = 10;
                }
                break;
            case 100572:
                if (toLowerCase.equals("ene")) {
                    obj = 3;
                }
                break;
            case 100727:
                if (toLowerCase.equals("ese")) {
                    obj = 5;
                }
                break;
            case 109221:
                if (toLowerCase.equals("nne")) {
                    obj = 1;
                }
                break;
            case 109509:
                if (toLowerCase.equals("nwn")) {
                    obj = 15;
                }
                break;
            case 114181:
                if (toLowerCase.equals("sse")) {
                    obj = 7;
                }
                break;
            case 114199:
                if (toLowerCase.equals("ssw")) {
                    obj = 9;
                }
                break;
            case 117888:
                if (toLowerCase.equals("wnw")) {
                    obj = 13;
                }
                break;
            case 118043:
                if (toLowerCase.equals("wsw")) {
                    obj = 11;
                }
                break;
        }
        switch (obj) {
            case 0:
                return Direction.N;
            case 1:
                return Direction.NNE;
            case 2:
                return Direction.NE;
            case 3:
                return Direction.ENE;
            case 4:
                return Direction.E;
            case 5:
                return Direction.ESE;
            case 6:
                return Direction.SE;
            case 7:
                return Direction.SSE;
            case 8:
                return Direction.S;
            case 9:
                return Direction.SSW;
            case 10:
                return Direction.SW;
            case 11:
                return Direction.WSW;
            case WeatherCircleView.ARC_DIN:
                return Direction.W;
            case 13:
                return Direction.WNW;
            case 14:
                return Direction.NW;
            case 15:
                return Direction.NWN;
            default:
                return Direction.NA;
        }
    }

    public static Direction getDirectionFromOppo(String text) {
        String toLowerCase = text.toLowerCase(Locale.ENGLISH);
        int obj = -1;
        switch (toLowerCase.hashCode()) {
            case -589995872:
                if (toLowerCase.equals("旋转不定风")) {
                    obj = 2;
                }
                break;
            case 658994:
                if (toLowerCase.equals("东风")) {
                    obj = 5;
                }
                break;
            case 698519:
                if (toLowerCase.equals("北风")) {
                    obj = 3;
                }
                break;
            case 700503:
                if (toLowerCase.equals("南风")) {
                    obj = 7;
                }
                break;
            case 1130287:
                if (toLowerCase.equals("西风")) {
                    obj = 9;
                }
                break;
            case 19914675:
                if (toLowerCase.equals("东北风")) {
                    obj = 4;
                }
                break;
            case 19916659:
                if (toLowerCase.equals("东南风")) {
                    obj = 6;
                }
                break;
            case 26220013:
                if (toLowerCase.equals("旋转风")) {
                    obj = -1;
                }
                break;
            case 34524758:
                if (toLowerCase.equals("西北风")) {
                    obj = 10;
                }
                break;
            case 34526742:
                if (toLowerCase.equals("西南风")) {
                    obj = 8;
                }
                break;
            case 812250606:
                if (toLowerCase.equals("旋转不定")) {
                    obj = 1;
                }
                break;
        }
        switch (obj) {
            case 0:
            case 1:
            case 2:
                return Direction.RW;
            case 3:
                return Direction.N;
            case 4:
                return Direction.NE;
            case 5:
                return Direction.E;
            case 6:
                return Direction.SE;
            case 7:
                return Direction.S;
            case 8:
                return Direction.SW;
            case 9:
                return Direction.W;
            case 10:
                return Direction.NW;
            default:
                return Direction.NA;
        }
    }

    public static Direction getDirectionFromSwa(String text) {
        int obj = -1;
        switch (text.hashCode()) {
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorAccent:
                if (text.equals("0")) {
                    obj = -1;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorBackgroundFloating:
                if (text.equals("1")) {
                    obj = 1;
                }
                break;
            case Stars.CIRCLE_COUNT:
                if (text.equals("2")) {
                    obj = 2;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorControlActivated:
                if (text.equals("3")) {
                    obj = 3;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorControlHighlight:
                if (text.equals("4")) {
                    obj = 4;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorControlNormal:
                if (text.equals("5")) {
                    obj = 5;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorError:
                if (text.equals("6")) {
                    obj = 6;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorPrimary:
                if (text.equals("7")) {
                    obj = 7;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorPrimaryDark:
                if (text.equals("8")) {
                    obj = 8;
                }
                break;
            case net.oneplus.weather.R.styleable.AppCompatTheme_colorSwitchThumbNormal:
                if (text.equals("9")) {
                    obj = 9;
                }
                break;
        }
        switch (obj) {
            case 0:
                return Direction.NA;
            case 1:
                return Direction.NE;
            case 2:
                return Direction.E;
            case 3:
                return Direction.SE;
            case 4:
                return Direction.S;
            case 5:
                return Direction.SW;
            case 6:
                return Direction.W;
            case 7:
                return Direction.NW;
            case 8:
                return Direction.N;
            case 9:
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
