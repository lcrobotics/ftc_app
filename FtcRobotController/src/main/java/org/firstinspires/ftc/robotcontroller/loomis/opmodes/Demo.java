package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * This OpMode can only drive forward and backwards.
 *
 * It is an example OpMode for the programming tutorial.
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

        // What speed to go forward at
        double forward_speed = gamepad1.left_stick_y;

        // Left motors run in opposite directions
        frontLeftDrive.setPower(-forward_speed);
        backLeftDrive.setPower(-forward_speed);

        frontRightDrive.setPower(forward_speed);
        backRightDrive.setPower(forward_speed);

    }
}