package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by peyto on 10/25/2017.
 */

public class ArcadeTeleOp extends MechDrive {

/*    DcMotor rotatingIntake;
    DcMotor stationaryIntake;
    DcMotor conveyor;
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;
    CRServo leftLift;
    CRServo rightLift;*/



    @Override
    public void init() {
        super.init();
        frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        stationaryIntake.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    double fast_speed = 4. / 10;
    double slow_speed = 2. / 10;
    double straife_slow_speed = 3. / 10;

    @Override
    public void loop() {

        // What speed to go forward at
        if (gamepad1.right_trigger > 0.05) {
            rotatingIntake.setPower(-1);
            stationaryIntake.setPower(1);
        }else if (gamepad1.right_bumper){
            rotatingIntake.setPower(1);
            stationaryIntake.setPower(-1);
        } else {
            rotatingIntake.setPower(0);
            stationaryIntake.setPower(0);
        }

        conveyor.setPower(gamepad2.left_stick_y);


        //main drive (Strafe) (Right Joystick)
        double w = gamepad1.right_stick_x * fast_speed;
        double y = gamepad1.left_stick_y * fast_speed;
        double x = -gamepad1.left_stick_x * fast_speed;
        double dy = (gamepad1.dpad_down ? -slow_speed : 0) + (gamepad1.dpad_up ? slow_speed : 0);
        double dx = (gamepad1.dpad_left ? -straife_slow_speed : 0) + (gamepad1.dpad_right ? straife_slow_speed : 0);


        backLeftDrive.setPower(y - x - w - dx + dy);
        frontLeftDrive.setPower(x + y - w + dx + dy);
        backRightDrive.setPower(x + y + w + dx + dy);
        frontRightDrive.setPower(y - x + w - dx + dy);

        //if (gamepad1.a) expanding();
        //if (gamepad1.b) shrinking();


    }

    public void expanding() {
        leftLift.setPower(1);
        rightLift.setPower(-1);
        sleep(250);
        leftLift.setPower(0);
        rightLift.setPower(0);
    }
    public void shrinking(){
        leftLift.setPower(-1);
        rightLift.setPower(1);
        sleep(250);
        leftLift.setPower(0);
        rightLift.setPower(0);
    }

    public void sleep(int sleeptime) {
        try {
            Thread.sleep(sleeptime, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
