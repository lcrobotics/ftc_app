package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by peyto on 10/18/2017.
 */

public class GyroscopeDemo extends OpMode{
    GyroSensor gyrosensor;
    public void init() {
        gyrosensor  = hardwareMap.get(GyroSensor.class, "gyroscope");
    }

    @Override
    public void loop() {
        telemetry.addData("int getHeading", gyrosensor.getHeading());
        //telemetry.addData("Get rotation fraction ", gyrosensor.getRotationFraction());
        telemetry.addData("Status ", gyrosensor.status());
        telemetry.addData("rawy ", gyrosensor.rawY());
        telemetry.addData("rawx", gyrosensor.rawX());
        telemetry.addData("rawz", gyrosensor.rawZ());

    }
}
