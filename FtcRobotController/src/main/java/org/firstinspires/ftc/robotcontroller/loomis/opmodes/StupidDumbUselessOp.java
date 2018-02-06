package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Students on 1/18/2018.
 */

public class StupidDumbUselessOp extends OpMode {

    ColorSensor motor;


    @Override
    public void init() {
        motor = hardwareMap.get(ColorSensor.class, "m");


    }

    @Override
    public void loop() {
        telemetry.addData("Econdor", motor.red());
        telemetry.addData("Econdor", motor.green());
        telemetry.addData("Econdor", motor.blue());
        telemetry.addData("Econdor", "%08x", motor.argb());

    }
}
