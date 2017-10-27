package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by peyto on 10/25/2017.
 */

public class Draft1 extends OpMode{

    DcMotor rotatingIntake;
    DcMotor stationaryIntake;
    //DcMotor conveyor;
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;

    double fast_speed = 1. / 2;
    double slow_speed = 1. / 10;

    @Override
    public void init() {
        rotatingIntake = hardwareMap.get(DcMotor.class, "rotatingIntake");
        stationaryIntake = hardwareMap.get(DcMotor.class, "stationaryIntake");
        //conveyor = hardwareMap.get(DcMotor.class, "conveyor");
        frontLeftDrive  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftDrive  = hardwareMap.get(DcMotor.class, "backLeft");
        backRightDrive = hardwareMap.get(DcMotor.class, "backRight");
        //backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);



    }

    @Override
    public void loop() {

        // What speed to go forward at
        if (gamepad1.a == true) {
            rotatingIntake.setPower(1);
            stationaryIntake.setPower(-1);
        }else if (gamepad1.y == true) {
            rotatingIntake.setPower(-1);
            stationaryIntake.setPower(1);
        }else{
            rotatingIntake.setPower(0);
            stationaryIntake.setPower(0);
        }
        /*while (gamepad1.x == true) {
            conveyor.setPower(1);
        }
        while (gamepad1.b == true) {
            conveyor.setPower(-1);
        }
*/
        //main drive (Strafe) (Right Joystick)
        double w = -gamepad1.right_stick_x*fast_speed;
        double y = gamepad1.left_stick_y*fast_speed;
        double x = gamepad1.left_stick_x*fast_speed;
        double dy = (gamepad1.dpad_down ? -slow_speed : 0 ) + (gamepad1.dpad_up ? slow_speed : 0);
        double dx = (gamepad1.dpad_left ? -slow_speed : 0) + (gamepad1.dpad_right ? slow_speed : 0);


        backLeftDrive.setPower(y-x-w-dx+dy);
        frontLeftDrive.setPower(x+y-w+dx+dy);
        backRightDrive.setPower(x+y+w+dx+dy);
        frontRightDrive.setPower(y-x+w-dx+dy);

//        backLeftDrive.setPower(gamepad1.left_stick_y-gamepad1.left_stick_x-w);
//        frontLeftDrive.setPower(gamepad1.left_stick_x+gamepad1.left_stick_y-w);
//        backRightDrive.setPower(gamepad1.left_stick_x+gamepad1.left_stick_y+w);
//        frontRightDrive.setPower(gamepad1.left_stick_y-gamepad1.left_stick_x+w);

    }
}