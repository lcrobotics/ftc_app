package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Created by Students on 11/29/2017.
 */

public abstract class AutoOp extends MechDrive {

    public boolean isBlueTeam;


    @Override
    public void init() {
        super.init();
    }

    abstract void leftColumn();

    abstract void midColumn();

    abstract void rightColumn();

    // NB: unfinished
    public void forward (int v){
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setPower(.3);
        frontLeftDrive.setPower(3);
        backRightDrive.setPower(3);
        backLeftDrive.setPower(3);
        frontRightDrive.setTargetPosition(v);
    }

    /**
     * TrapizoidDrive is a drive for the autonomous that is more accurate than the regular drive.
     * It takes longer for the robot to speed up and slow down
     * @param x the straife speed of the robot
     * @param y the forward and backward to the robot
     * @param w the rotation of the robot
     * @param t The total time spent in run (Miliseconds)
     * @param sustain The fraction of time spent at max speed
     */
    public void trapizoidDrive(double x, double y, double w, int t, double sustain){
        double x1 = 0;
        double y1 = 0;
        double w1 = 0;
        double st = t*sustain;
        for (int iterations = 0; iterations < 10; iterations++) {
            x1 = x1 + (x/10);
            y1 = y1 + (y/10);
            w1 = w1 + (w/10);
            drive(x1,y1,w1);
            sleep((int) (.5 * (t - st)) / 10);
        }
        drive(x,y,w);
        sleep((int) (st));
        for (int iterations = 0; iterations < 10; iterations++){
            x1 = x1 - (x/10);
            y1 = y1 - (y/10);
            w1 = w1 - (w/10);
            drive(x1, y1, w1);
            sleep((int) (.5 * (t - st)) / 10);
            telemetry.addData("x = ", x1);
            telemetry.addData("y = ", y1);
            telemetry.addData("w = ", w1);
            drive(0,0,0);
        }
    }


}
