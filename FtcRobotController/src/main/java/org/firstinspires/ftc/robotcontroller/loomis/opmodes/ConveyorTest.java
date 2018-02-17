package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by peyto on 1/30/2018.
 */

public class ConveyorTest extends MechDrive {
    @Override
    public void init() {
        super.init();

    }

    double leftY = .5;
    double rightY = 0.5;
    @Override
    public void loop() {
        if (gamepad1.dpad_down){
            leftY -=.01;
        }else if (gamepad1.dpad_up){
            leftY += 0.01;
        }else if (gamepad1.dpad_left){
            rightY -= 0.01;
        }else if (gamepad1.dpad_right){
            rightY += 0.01;
        }
        telemetry.addData("Left value", leftY);
        telemetry.addData("Right value", rightY);

        leftLift.setPower(leftY);
        rightLift.setPower(rightY);

    }
}
