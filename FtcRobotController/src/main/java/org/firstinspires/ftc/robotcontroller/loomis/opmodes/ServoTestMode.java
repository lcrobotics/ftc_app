package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Students on 10/6/2017.
 */

public class ServoTestMode extends MechDrive {

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
        super.init();

        verticalArm.setPosition(.83);
        horizontalArm.setPosition(.5);
    }

    @Override
    public void loop() {


        if(gamepad1.left_bumper) {
            //verticalArm.setPosition(.5 * gamepad1.left_stick_y + .5);
            verticalArm.setPosition(gamepad1.left_stick_y);
        }
        if(gamepad1.right_bumper) {
            //horizontalArm.setPosition(.5 * gamepad1.right_stick_y + .5);
            horizontalArm.setPosition(gamepad1.right_stick_y);
        }

        telemetry.addData("Servo1", verticalArm.getPosition());
        telemetry.addData("Servo2", horizontalArm.getPosition());
        telemetry.addData("A", a);
        telemetry.addData("B", b);
        telemetry.addData("X", x);
        telemetry.addData("Y", y);


       if(gamepad1.dpad_up) {
            y =  verticalArm.getPosition();
        }
        if(gamepad1.dpad_right) {
            b =  horizontalArm.getPosition();
        }
        if(gamepad1.dpad_down) {
            a =  horizontalArm.getPosition();
        }
        if(gamepad1.dpad_left) {
            x =  verticalArm.getPosition();
        }

         if(gamepad1.a) {
            horizontalArm.setPosition(a);
        }
        if(gamepad1.y) {
            verticalArm.setPosition(y);
        }
        if(gamepad1.x) {
            verticalArm.setPosition(x);
        }
        if(gamepad1.b) {
            horizontalArm.setPosition(b);
        }
    }
}
