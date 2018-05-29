package com.facebook.rebound;

public class SpringConfig {
    public static SpringConfig defaultConfig;
    public double friction;
    public double tension;

    static {
        defaultConfig = fromOrigamiTensionAndFriction(40.0d, 7.0d);
    }

    public SpringConfig(double tension, double friction) {
        this.tension = tension;
        this.friction = friction;
    }

    public static SpringConfig fromOrigamiTensionAndFriction(double qcTension, double qcFriction) {
        return new SpringConfig(OrigamiValueConverter.tensionFromOrigamiValue(qcTension), OrigamiValueConverter.frictionFromOrigamiValue(qcFriction));
    }

    public static SpringConfig fromBouncinessAndSpeed(double bounciness, double speed) {
        BouncyConversion bouncyConversion = new BouncyConversion(speed, bounciness);
        return fromOrigamiTensionAndFriction(bouncyConversion.getBouncyTension(), bouncyConversion.getBouncyFriction());
    }
}
