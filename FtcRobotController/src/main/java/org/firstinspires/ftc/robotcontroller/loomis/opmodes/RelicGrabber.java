package org.firstinspires.ftc.robotcontroller.loomis.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
/**
 * Created by peyto on 1/11/2018.
 */

public class RelicGrabber extends MechDrive{


    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {
        grabber.setPosition(
                (((gamepad1.left_stick_y)/2)+(1/2))
        );
        telemetry.addData("Servo Position: " ,(((gamepad1.left_stick_y)/2)+(1/2)));

    }
}
