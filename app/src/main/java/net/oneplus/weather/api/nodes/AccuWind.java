package net.oneplus.weather.api.nodes;

import net.oneplus.weather.api.nodes.Wind.Direction;

public class AccuWind extends Wind {
    private final double mSpeed;

    public AccuWind(String areaCode, String dataSource, Direction direction, double speed) {
        super(areaCode, dataSource, direction);
        this.mSpeed = speed;
    }

    public AccuWind(String areaCode, String areaName, String dataSource, Direction direction, double speed) {
        super(areaCode, areaName, dataSource, direction);
        this.mSpeed = speed;
    }

    public double getSpeed() {
        return this.mSpeed;
    }
}
