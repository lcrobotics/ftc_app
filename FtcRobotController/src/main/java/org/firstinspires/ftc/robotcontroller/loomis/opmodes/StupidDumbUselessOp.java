package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Students on 1/18/2018.
 */

public class StupidDumbUselessOp extends OpMode {

    DcMotor motor;


    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("m");

        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public void loop() {
        motor.setPower(1.0);
        telemetry.addData("Econdor", motor.getCurrentPosition());
    }
}
