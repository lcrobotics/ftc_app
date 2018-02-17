package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by peyto on 11/28/2017.
 */

public class BackBlueAutoOp extends AutoOp {

    @Override
    public void init() {super.init();}

    public void forwardforcolumn(int columnum) {
        int t = 0;
        if (columnum == 1) {
            t = 600;
        }else if (columnum == 2){
            t = 800;
        }else if (columnum == 3){
            t = 900;
        }
        trapizoidDrive(0, 4, 0, t, .5);
    }

    public void rotate90degrees(){
        trapizoidDrive(0, 0, .35, 800, .5);
    }


    public void off_the_balance(){
        trapizoidDrive(0, .3, 0, 900, .5);
    }

    public void strafe(){
        trapizoidDrive(-1, 0 ,0, 200, .5);
    }
    public void stop_wheels(){
        drive(0, 0,0);
    }

    public void leftColumn() {
        off_the_balance();
        stop_wheels();
        forwardforcolumn(1);
        stop_wheels();
        rotate90degrees();
        stop_wheels();
    }

    public void midColumn() {
        off_the_balance();
        rotate90degrees();
        forwardforcolumn(2);
        stop_wheels();
    }

    public void rightColumn() {
        off_the_balance();
        forwardforcolumn(3);
        stop_wheels();
    }

    public void park() {

        stationaryIntake.setPower(-1);
        rotatingIntake.setPower(1);
        trapizoidDrive(0, .3, 0, 1420, 0.3);
        stationaryIntake.setPower(0);
        rotatingIntake.setPower(0);

        rotate90degrees();
        trapizoidDrive(0,0.4,0,250,0.3);
        trapizoidDrive(0,-.3, 0, 125, 0.2);

        stationaryIntake.setPower(1);
        rotatingIntake.setPower(-1);
        sleep(700);
        stationaryIntake.setPower(0);
        rotatingIntake.setPower(0);

        trapizoidDrive(0,.3,0,125, 0.2);
        trapizoidDrive(0,-.3,0,125, 0.2);

        stop_wheels();
    }
     public void blockPlacing() {

     }

    int count;


    @Override
    public boolean isBlueTeam() {
        return true;
    }

}
