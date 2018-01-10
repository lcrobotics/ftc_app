package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

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
    DcMotor relicSlide;

    CRServo leftLift;
    CRServo rightLift;

    Servo servo1;
    Servo servo2;
    Servo grabber;

    double fast_speed = .4;
    double slow_speed = .2;
    double strafe_slow_speed = .3;

    @Override
    public void init() {
        rotatingIntake = hardwareMap.get(DcMotor.class, "rotatingIntake");
        stationaryIntake = hardwareMap.get(DcMotor.class, "stationaryIntake");
        //Not working, should be the method below this, dpad controls instead of joystick
        conveyor = hardwareMap.get(DcMotor.class, "conveyor");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftDrive = hardwareMap.get(DcMotor.class, "backLeft");
        backRightDrive = hardwareMap.get(DcMotor.class, "backRight");
        relicSlide = hardwareMap.get(DcMotor.class, "rel");
        leftLift = hardwareMap.get(CRServo.class, "leftLift");
        rightLift = hardwareMap.get(CRServo.class, "rightLift");
        grabber = hardwareMap.get(Servo.class, "grabber");
        frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        stationaryIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        servo1 = hardwareMap.servo.get("1");
        servo2 = hardwareMap.servo.get("2");
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        stationaryIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        relicSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void drive(double x, double y, double w){
        backLeftDrive.setPower(y - x - w);
        frontLeftDrive.setPower(x + y - w);
        backRightDrive.setPower(x + y + w);
        frontRightDrive.setPower(y - x + w);
    }

    public void intake(double v){
        rotatingIntake.setPower(v);
        stationaryIntake.setPower(v);
    }
    public void conveyor (double v) {
        leftLift.setPower(v);
        rightLift.setPower(v);
    }
//    public void grabber (int switchNum) {
//        int openOrClosed = 0;
//        switch (openOrClosed) {
//            case 0:
//                grabber.setPosition();
//                break;
//            case 1:
//                grabber.setPosition();
//                break;
//        }
//    }
    /**
     * sets position of lift
     * @param x should be between 0 and 1
     */

    public void lift(double x){
        leftLift.setPower(1-.730*x);
        rightLift.setPower(.730*x + 0.019);
    }
    public void sleep (int sleeptime) {
        try {
            Thread.sleep(sleeptime, 0);
        } catch (InterruptedException e) {
            RobotLog.v("Got interupted while sleeping for %d millis. Error %s", sleeptime, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void loop() {

    }
}
