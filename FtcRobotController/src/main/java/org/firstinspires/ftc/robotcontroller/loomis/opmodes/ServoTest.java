package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by peyto on 2/19/2018.
 */

public class ServoTest extends OpMode {
    Servo Test;

    @Override
    public void init() {
        Test = hardwareMap.get(Servo.class, "Test");
    }

    @Override
    public void loop() {

        Test.setPosition(gamepad1.left_stick_y);
        telemetry.addData("ServoPos", Test.getPosition());
        telemetry.addData("Joystick Pos", gamepad1.left_stick_y);
    }
}
