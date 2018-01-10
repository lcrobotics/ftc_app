package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

/**
 * Created by peyto on 12/12/2017.
 */

public class ServoDemo extends OpMode {
    CRServo servo1;

    @Override
    public void init() {
        servo1 = hardwareMap.crservo.get("servo1");
        telemetry.addData("servo1 power is ", servo1.getPower());
        telemetry.update();
    }
//    public void shrinking() {
//        servo1.setPower(1);
//        telemetry.addData("Shrinking", servo1.getPower());
//        sleep(600);
//        servo1.setPower(0);
//        telemetry.addData("setting power to zero", servo1.getPower());
//    }
    public void expanding() {
        if (gamepad1.a) {
            servo1.setPower(1);
            telemetry.addData("Power is ", servo1.getPower());
        }else if (gamepad1.b){
            servo1.setPower(.5);
            telemetry.addData("Power is", servo1.getPower());
        }else if (gamepad1.y){
            servo1.setPower(0);
            telemetry.addData("Power is", servo1.getPower());
        }

    }



//        telemetry.addData("Expanding", servo1.getPower());
//        sleep(600);
//        servo1.setPower(1);
//        telemetry.addData("Setting power to zero", servo1.getPower());

//    }
//    public void sleep(int sleeptime) {
//        try {
//            Thread.sleep(sleeptime, 0);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public void loop() {
        double x = (gamepad1.right_stick_x*(.5)) + (.5);
        servo1.setPower(x);
        telemetry.addData("X is ", x);
        telemetry.addData("The right stick power is", servo1.getPower());
        //expanding();
        //servo1.setPower(1);
    }
}
