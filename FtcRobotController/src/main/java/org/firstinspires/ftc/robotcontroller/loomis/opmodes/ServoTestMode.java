package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Students on 10/6/2017.
 */

public class ServoTestMode extends LinearOpMode {

    Servo servo;

    @Override
    public void runOpMode() {
        servo = hardwareMap.get(Servo.class, "servo");
        waitForStart();
        while(opModeIsActive()) {
            if(gamepad1.a) {
                servo.setPosition(0.3);
                telemetry.addLine("A");
            }
            if(gamepad1.b) {
                telemetry.addLine("B");
                servo.setPosition(0.0);
            }
            if(gamepad1.x) {
                telemetry.addLine("X");
                servo.setPosition(0.6);
            }
            if(gamepad1.y) {
                telemetry.addLine("Y");
                servo.setPosition(1);
            }
        }
    }
}
