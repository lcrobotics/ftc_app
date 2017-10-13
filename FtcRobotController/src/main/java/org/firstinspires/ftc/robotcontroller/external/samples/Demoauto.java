
package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
@Disabled
public class Demoauto extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftDrive = hardwareMap.get(DcMotor.class, "backLeft");
        backRightDrive = hardwareMap.get(DcMotor.class, "backRight");
        frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        boolean gamerunning = true;

        waitForStart();
        runtime.reset();

        while (gamerunning) {
            if (gamepad1.x) knockLeftBall();
            else if (gamepad1.b) knockRightBall();
            else if (gamepad1.start) gamerunning = false;
        }
    }

    void setAllMotors(double motorSpeeds) {
        setDriveMotors(motorSpeeds, motorSpeeds);
    }

    void setDriveMotors(double leftSpeed, double rightSpeed) {
        frontLeftDrive.setPower(leftSpeed);
        backLeftDrive.setPower(leftSpeed);
        frontRightDrive.setPower(rightSpeed);
        backRightDrive.setPower(rightSpeed);
    }
    void knockRightBall() {
        setDriveMotors(.5, -.5);
        sleep(300);
        setAllMotors(1);
        sleep(250);
        setDriveMotors(.5, -.5);
        sleep(300);
        setDriveMotors(0,0);
    }
    void knockLeftBall() {
        setDriveMotors(-.5, .5);
        sleep(300);
        setAllMotors(1);
        sleep(250);
        setDriveMotors(-.5, .5);
        sleep(300);
        setDriveMotors(0,0);
    }
    void sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}