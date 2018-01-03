package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by peyto on 10/25/2017.
 */

public class Draft1 extends MechDrive {



    DcMotor rotatingIntake;
    DcMotor stationaryIntake;
    DcMotor conveyor;
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;
    CRServo leftLift;
    CRServo rightLift;
    Servo servo1;
    Servo servo2;


    double a = .83;
    double b = .83;
    double x = 1;
    double y = 0;
    double endx = .5;
    double endy = .13;      

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
        leftLift = hardwareMap.crservo.get("leftLift");
        rightLift = hardwareMap.crservo.get("rightLift");
        servo1 = hardwareMap.servo.get("1");
        servo2 = hardwareMap.servo.get("2");
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        servo1.setPosition(.83);
        servo2.setPosition(.5);



    }


    @Override
    public void loop() {

        if(gamepad1.left_bumper) {
            servo1.setPosition(.5 * gamepad1.left_stick_y + .5);
        }
        if(gamepad1.right_bumper) {
            servo2.setPosition(.5 * gamepad1.right_stick_y + .5);
        }


        telemetry.addData("Servo1", servo1.getPosition());
        telemetry.addData("Servo2", servo2.getPosition());
        telemetry.addData("A", a);
        telemetry.addData("B", b);
        telemetry.addData("X", x);
        telemetry.addData("Y", y);

        if(gamepad1.dpad_up) {
            y =  servo1.getPosition();
        }
        if(gamepad1.dpad_right) {
            b =  servo2.getPosition();
        }
        if(gamepad1.dpad_down) {
            a =  servo2.getPosition();
        }
        if(gamepad1.dpad_left) {
            x =  servo1.getPosition();
        }

        if(gamepad1.a) {
            servo2.setPosition(a);
        }
        if(gamepad1.y) {
            servo1.setPosition(y);
        }
        if(gamepad1.x) {
            servo1.setPosition(x);
        }
        if(gamepad1.b) {
            servo1.setPosition(b);
        }
    }
}
