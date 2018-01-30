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

        servo1.setPosition(.83);
        servo2.setPosition(.5);
    }

    @Override
    public void loop() {


        if(gamepad1.left_bumper) {
            //servo1.setPosition(.5 * gamepad1.left_stick_y + .5);
            servo1.setPosition(gamepad1.left_stick_y);
        }
        if(gamepad1.right_bumper) {
            //servo2.setPosition(.5 * gamepad1.right_stick_y + .5);
            servo2.setPosition(gamepad1.right_stick_y);
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
            servo2.setPosition(b);
        }
    }
}
