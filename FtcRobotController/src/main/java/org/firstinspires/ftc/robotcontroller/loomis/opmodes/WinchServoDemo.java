package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by peyto on 10/31/2017.
 */

public class WinchServoDemo extends OpMode{
    CRServo Winch;
    public void init(){
        Winch = hardwareMap.get (CRServo.class, "WinchDemo");

    }
    public void loop(){
        Winch.setPower(gamepad1.a ? (1):0);

    }

}
