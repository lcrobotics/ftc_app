package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by aozdemir on 9/12/17.
 */

public class Demo extends OpMode {

    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;

    @Override
    public void init() {
        frontLeftDrive  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftDrive  = hardwareMap.get(DcMotor.class, "backLeft");
        backRightDrive = hardwareMap.get(DcMotor.class, "backRight");


    }

    @Override
    public void loop() {
        frontLeftDrive.setPower(-gamepad1.left_stick_y);
        frontRightDrive.setPower(gamepad1.left_stick_y);
        backLeftDrive.setPower(-gamepad1.left_stick_y);
        backRightDrive.setPower(gamepad1.left_stick_y);
    }
}