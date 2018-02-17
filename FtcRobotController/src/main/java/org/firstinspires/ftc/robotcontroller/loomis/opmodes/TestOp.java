package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

/**
 * Created by Students on 1/29/2018.
 */

public class TestOp extends AutoOp {

     @Override
    public void init() {super.init();}

    public void forwardforcolumn(int columnum) {
        int t = 0;
        if (columnum == 1) {
            t = 700;
        }else if (columnum == 2){
            t = 800;
        }else if (columnum == 3){
            t = 900;
        }
        trapizoidDrive(0, 4, 0, t, .5);
    }

    public void rotate90degrees(){
        trapizoidDrive(0, 0, .2, 800, .5);
    }

    public void positioning(){
        trapizoidDrive(0, .2, 0, 175, .5);
        trapizoidDrive(.3,0,0,850, .5);
    }

    public void off_the_balance(){
        trapizoidDrive(0, .3, 0, 600, .5);
    }
    public void stop_wheels(){
        drive(0, 0,0);
    }

    public void leftColumn() {
        off_the_balance();
        rotate90degrees();
        positioning();
        forwardforcolumn(1);
        stop_wheels();
    }

    public void midColumn() {
        off_the_balance();
        rotate90degrees();
        positioning();
        forwardforcolumn(2);
        stop_wheels();
    }
    public void park() {
       encoderDrive(500);

    }



    public void rightColumn() {
        off_the_balance();
        rotate90degrees();
        positioning();
        forwardforcolumn(3);
        stop_wheels();
    }
    int count;


    @Override
    public boolean isBlueTeam() {
        return true;
    }
}
