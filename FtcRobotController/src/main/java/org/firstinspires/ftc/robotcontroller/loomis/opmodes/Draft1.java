package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by peyto on 10/25/2017.
 */

public class Draft1 extends OpMode {

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


    }


    @Override
    public void loop() {

        // What speed to go forward at
        rotatingIntake.setPower(gamepad2.right_stick_y);
        stationaryIntake.setPower(-gamepad2.right_stick_y);

        conveyor.setPower(gamepad2.left_stick_y);


        //main drive (Strafe) (Right Joystick)
        double w = -gamepad1.right_stick_x * fast_speed;
        double y = gamepad1.left_stick_y * fast_speed;
        double x = gamepad1.left_stick_x * fast_speed;
        double dy = (gamepad1.dpad_down ? -slow_speed : 0) + (gamepad1.dpad_up ? slow_speed : 0);
        double dx = (gamepad1.dpad_left ? -slow_speed : 0) + (gamepad1.dpad_right ? slow_speed : 0);


        backLeftDrive.setPower(y - x - w - dx + dy);
        frontLeftDrive.setPower(x + y - w + dx + dy);
        backRightDrive.setPower(x + y + w + dx + dy);
        frontRightDrive.setPower(y - x + w - dx + dy);

        if (gamepad1.a) expanding();
        if (gamepad1.b) shrinking();


    }

    public void expanding() {
        leftLift.setPower(1);
        rightLift.setPower(-1);
        sleep(250);
        leftLift.setPower(0);
        rightLift.setPower(0);
    }
    public void shrinking(){
        leftLift.setPower(-1);
        rightLift.setPower(1);
        sleep(250);
        leftLift.setPower(0);
        rightLift.setPower(0);
    }

    public void sleep(int sleeptime) {
        try {
            Thread.sleep(sleeptime, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
