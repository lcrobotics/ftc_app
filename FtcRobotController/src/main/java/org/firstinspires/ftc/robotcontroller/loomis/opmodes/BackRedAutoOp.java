package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import org.firstinspires.ftc.robotcontroller.internal.ColumnToScore;

/**
 * Created by peyto on 11/28/2017.
 */

public class BackRedAutoOp extends AutoOp {

    @Override
    public void init() {
        super.init();
    }

    public void stop_wheels(){
        drive(0, 0,0);
    }

    public void forwardforcolumn(int columnum) {
        int t = 0;
        if (columnum == 1) {
            t = 700;
        }else if (columnum == 2){
            t = 800;
        }else if (columnum == 3){
            t = 900;
        }
        trapizoidDrive(0, 0.4, 0, t, .5);
    }

    public void rotate90degrees(){
        drive(0, 0, -.2);
        sleep(400);
    }

    public void off_the_balance() {
        trapizoidDrive(0, -.3, 0, 900, .5);
    }

    public void park(ColumnToScore column){
        off_the_balance();
        stop_wheels();
    }




    @Override
    public boolean isBlueTeam() {
        return false;
    }


}
