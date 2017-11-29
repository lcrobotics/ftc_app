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
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        stationaryIntake.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void drive(double x, double y, double w){
        backLeftDrive.setPower(y - x - w);
        frontLeftDrive.setPower(x + y - w);
        backRightDrive.setPower(x + y + w);
        frontRightDrive.setPower(y - x + w);
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
    public void sleep(int sleeptime) {
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
