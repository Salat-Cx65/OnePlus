package net.oneplus.weather.api.nodes;

import java.util.ArrayList;
import java.util.List;
import net.oneplus.weather.api.impl.OppoChinaRequest;
import net.oneplus.weather.api.nodes.LifeIndexWeather.LifeIndex;

public class OppoChinaLifeIndexWeather extends LifeIndexWeather {
    private List<LifeIndex> mIndexList;

    public OppoChinaLifeIndexWeather(String areaCode) {
        super(areaCode, null, OppoChinaRequest.DATA_SOURCE_NAME);
        this.mIndexList = null;
    }

    public List<LifeIndex> getLifeIndexList() {
        return this.mIndexList;
    }

    public void add(LifeIndex item) {
        if (item != null) {
            if (this.mIndexList == null) {
                this.mIndexList = new ArrayList();
            }
            this.mIndexList.add(item);
        }
    }

    public String getBodytempIndexText(String defaultValue) {
        if (this.mIndexList == null) {
            return defaultValue;
        }
        for (LifeIndex item : this.mIndexList) {
            if ("body_temp".equals(item.getShortName())) {
                return item.getLevel();
            }
        }
        return defaultValue;
    }

    public String getPressureIndexText(String defaultValue) {
        if (this.mIndexList == null) {
            return defaultValue;
        }
        for (LifeIndex item : this.mIndexList) {
            if ("pressure".equals(item.getShortName())) {
                return item.getLevel();
            }
        }
        return defaultValue;
    }

    public String getVisibilityIndexText(String defaultValue) {
        if (this.mIndexList == null) {
            return defaultValue;
        }
        for (LifeIndex item : this.mIndexList) {
            if ("visibility".equals(item.getShortName())) {
                return item.getLevel();
            }
        }
        return defaultValue;
    }
}
