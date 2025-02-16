package me.pm7.session6.Utils;

import org.bukkit.util.Vector;

public enum Direction {
    POSITIVE_X,
    NEGATIVE_X,
    POSITIVE_Z,
    NEGATIVE_Z;

    public static Direction fromYaw(double yaw) {
        yaw+=360; // it took so stupidly long for me to add this
        yaw%=360;
        if (yaw >= 225 && yaw < 315) return POSITIVE_X;
        else if (yaw >= 45 && yaw < 135) return NEGATIVE_X;
        else if (yaw >= 315 || yaw < 45) return POSITIVE_Z;
        else return NEGATIVE_Z;
    }

    public Vector toVector() {
        return switch (this) {
            case POSITIVE_X -> new Vector(1, 0, 0);
            case NEGATIVE_X -> new Vector(-1, 0, 0);
            case POSITIVE_Z -> new Vector(0, 0, 1);
            case NEGATIVE_Z -> new Vector(0, 0, -1);
        };
    }

    public Vector toVector(double p) {
        return switch (this) {
            case POSITIVE_X -> new Vector(p, 0, 0);
            case NEGATIVE_X -> new Vector(-p, 0, 0);
            case POSITIVE_Z -> new Vector(0, 0, p);
            case NEGATIVE_Z -> new Vector(0, 0, -p);
        };
    }

    public int getCardinal() {
        return switch (this) {
            case POSITIVE_X -> -90;
            case NEGATIVE_X -> 90;
            case POSITIVE_Z -> 0;
            case NEGATIVE_Z -> 180;
        };
    }
}
