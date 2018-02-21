package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import org.firstinspires.ftc.robotcontroller.internal.ColumnToScore;

/**
 * Created by Students on 1/30/2018.
 */

public class FrontRedRobotOp extends AutoOp {

    @Override
    public void init() {
        super.init();
    }

    public void forwardforcolumn(int columnum) {
        int t = 0;
        if (columnum == 1) {
            t = 700;
        } else if (columnum == 2) {
            t = 800;
        } else if (columnum == 3) {
            t = 900;
        }
        trapizoidDrive(0, 4, 0, t, .5);
    }

    public void rotate90degrees() {
        trapizoidDrive(0, 0, .2, 800, .5);
    }

    public void positioning() {
        trapizoidDrive(0, .2, 0, 175, .5);
        trapizoidDrive(.3, 0, 0, 850, .5);
    }

    public void off_the_balance() {
        trapizoidDrive(0, .3, 0, 600, .5);
    }

    public void stop_wheels() {
        drive(0, 0, 0);
    }


    public void park(ColumnToScore column) {
        trapizoidDrive(0, -.3, 0, 1475, .5);
        conveyor.setPower(-1);
        trapizoidDrive(0, .3, 0, 100, .3);
        sleep(1000);
        trapizoidDrive(0, -.3, 0, 175, .5);
        trapizoidDrive(0, .3, 0, 60, .3);
        conveyor.setPower(0);
    }

        @Override
        public boolean isBlueTeam () {
            return false;
        }

}

