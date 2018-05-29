package net.oneplus.weather.api.helper;

import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class XmlUtils {
    private XmlUtils() {
    }

    public static boolean nextElementWithin(XmlPullParser parser, int outerDepth) throws IOException, XmlPullParserException {
        while (true) {
            int type = parser.next();
            if (type != 1) {
                if (type == 3 && parser.getDepth() == outerDepth) {
                    break;
                } else if (type == 2 && parser.getDepth() == outerDepth + 1) {
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }
}
