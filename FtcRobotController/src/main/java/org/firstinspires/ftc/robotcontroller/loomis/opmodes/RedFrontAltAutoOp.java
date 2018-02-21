package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import org.firstinspires.ftc.robotcontroller.internal.ColumnToScore;

/**
 * Created by peyto on 11/28/2017.
 */

public class RedFrontAltAutoOp extends AutoOp {

    @Override
    public void init() {super.init();}

    public void forwardforcolumn(int columnum) {
        int t = 0;
        if (columnum == 1) {
            t = 600;
        }else if (columnum == 2){
            t = 800;
        }else if (columnum == 3){
            t = 900;
        }
        trapizoidDrive(0, 4, 0, t, .5);
    }

    public void rotate90degrees(){
        trapizoidDrive(0, 0, .35, 800, .5);
    }


    public void off_the_balance(){
        trapizoidDrive(0, .3, 0, 900, .5);
    }

    public void strafe(){
        trapizoidDrive(-1, 0 ,0, 200, .5);
    }
    public void stop_wheels(){
        drive(0, 0,0);
    }


    public void park(ColumnToScore column) {

        stationaryIntake.setPower(-1);
        rotatingIntake.setPower(1);
        trapizoidDrive(0, .3, 0, 1420, 0.3);
        stationaryIntake.setPower(0);
        rotatingIntake.setPower(0);

        rotate90degrees();
        trapizoidDrive(0,0.4,0,250,0.3);
        trapizoidDrive(0,-.3, 0, 125, 0.2);

        stationaryIntake.setPower(1);
        rotatingIntake.setPower(-1);
        sleep(700);
        stationaryIntake.setPower(0);
        rotatingIntake.setPower(0);

        trapizoidDrive(0,.3,0,135, 0.2);
        trapizoidDrive(0,-.3,0,67, 0.2);

        stop_wheels();
    }
    public void blockPlacing() {

    }

    int count;

    @Override
    public boolean isBlueTeam() {
        return false;
    }

}

