package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by peyto on 11/28/2017.
 */

public class MechDrive extends OpMode {


    DcMotor rotatingIntake;
    DcMotor stationaryIntake;
    DcMotor conveyor;
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;
    CRServo leftLift;
    CRServo rightLift;

    double fast_speed = 4. / 10;
    double slow_speed = 1. / 10;

    @Override
    public void init() {
        rotatingIntake = hardwareMap.get(DcMotor.class, "rotatingIntake");
        stationaryIntake = hardwareMap.get(DcMotor.class, "stationaryIntake");
        conveyor = hardwareMap.get(DcMotor.class, "conveyor");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftDrive = hardwareMap.get(DcMotor.class, "backLeft");
        backRightDrive = hardwareMap.get(DcMotor.class, "backRight");
        leftLift = hardwareMap.get(CRServo.class, "leftLift");
        rightLift = hardwareMap.get(CRServo.class, "rightLift");
        frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        stationaryIntake.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void drive(double x, double y, double w){
        backLeftDrive.setPower(y - x - w);
        frontLeftDrive.setPower(x + y - w);
        backRightDrive.setPower(x + y + w);
        frontRightDrive.setPower(y - x + w);
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
    public void intake(double v){
        rotatingIntake.setPower(v);
        rotatingIntake.setPower(v);
    }
    public void conveyor (double v) {
        leftLift.setPower(v);
        rightLift.setPower(v);
    }
    public void extend (){

    }
    public void sleep (int sleeptime) {
        try {
            Thread.sleep(sleeptime, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loop() {

    }
}
