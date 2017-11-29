package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

/**
 * Created by Students on 11/29/2017.
 */

public abstract class AutoOp extends MechDrive {

    public boolean isBlueTeam;

    @Override
    public void init() {
        super.init();
    }

    abstract void leftColumn();

    abstract void midColumn();

    abstract void rightColumn();


}
