package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by peyto on 11/28/2017.
 */

public class BackRedRobotCode extends AutoOp {

    @Override
    public void init() {
        super.init();
    }

    public void forwardforcolumn(int columnNum) {
        drive(0, -.2, 0);
        sleep(columnNum);
    }

    public void rotate90degrees(){
        drive(0, 0, -.2);
        sleep(400);
    }

    public void leftColumn() {
        forwardforcolumn( 500);
        rotate90degrees();
        drive(0, 0, 0);
    }

    public void midColumn() {
        forwardforcolumn(550);
        rotate90degrees();
        drive(0, 0, 0);
    }

    public void rightColumn() {
        forwardforcolumn(600);
        rotate90degrees();
        drive(0, 0, 0);
    }

    @Override
    public void loop() {
        lift(1);
        leftColumn();
        sleep(100000);
        stop();
    }


}
