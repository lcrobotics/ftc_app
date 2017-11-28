package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Students on 10/6/2017.
 */

public class DumbOpMode extends LinearOpMode {

    DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.dcMotor.get("motor");
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.a && motor.getCurrentPosition() < 500) {
                motor.setTargetPosition(500);
            }
            if(gamepad1.b && motor.getCurrentPosition() > 400) {
                motor.setTargetPosition(0);
            }
        }
    }
}
