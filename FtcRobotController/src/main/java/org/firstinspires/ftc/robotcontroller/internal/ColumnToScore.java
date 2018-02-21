package org.firstinspires.ftc.robotcontroller.internal;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by Students on 2/21/2018.
 */

public enum ColumnToScore {
    UNKNOWN,
    LEFT,
    RIGHT,
    CENTER;

    public static ColumnToScore from(RelicRecoveryVuMark vuMark)
    {
        switch (vuMark) {
            case LEFT:
                return LEFT;
            case RIGHT:
                return RIGHT;
            case CENTER:
                return CENTER;
            default:
                return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case LEFT:
                return "LEFT";
            case RIGHT:
                return "RIGHT";
            case CENTER:
                return "CENTER";
            default:
                return "UNKNOWN";
        }
    }
}
