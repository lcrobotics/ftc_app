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
public class MechanumTank extends OpMode {

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
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);




    }

    @Override
    public void loop() {

        // What speed to go forward at
        double lystick = gamepad1.left_stick_y;
        double lxstick = gamepad1.left_stick_x;
        double rystick = gamepad1.right_stick_y;
        double rxstick = gamepad1.right_stick_x;


        frontLeftDrive.setPower((lxstick + lystick)/2);
        backLeftDrive.setPower((lystick - lxstick)/2);

        frontRightDrive.setPower((rystick - rxstick)/2);
        backRightDrive.setPower((rxstick + rystick)/2);

    }
}