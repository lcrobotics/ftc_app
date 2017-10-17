package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by peyto on 10/17/2017.
 */

public class RosiesGrabber extends OpMode {

        Servo rosiesMechanism;

        @Override
        public void init() {
            rosiesMechanism = hardwareMap.get(Servo.class, "rosiesMechanism");

            rosiesMechanism.setPosition(0.5);


        }


        @Override
        public void loop() {
            if (gamepad1.b) {
                rosiesMechanism.setPosition(1.0);
                telemetry.addData("Trying to ", "open/Close 1.0");


            } else if (gamepad1.a) {
                rosiesMechanism.setPosition(0.5);
                telemetry.addData("trying to open", "/close 0.5");

            }

        }
    }

