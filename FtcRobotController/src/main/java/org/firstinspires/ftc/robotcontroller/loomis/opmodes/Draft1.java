package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Draft1 extends MechDrive {


    private int maxEncoder = 8500;
    private int minEncoder = 50;

    @Override
    public void init() {
        super.init();
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

    }


    @Override
    public void loop() {

        servo1.setPosition(.5);
        servo2.setPosition(.13);
        relicSlide.setPower(0);

        // What speed to go forward at
        if ((gamepad1.right_trigger  > 0.05 || gamepad2.right_trigger > 0.05) && (!gamepad1.right_bumper)) {
            rotatingIntake.setPower(-1);
            stationaryIntake.setPower(-1);
        }else if (gamepad1.right_bumper && gamepad2.right_trigger > 0.05) {
            rotatingIntake.setPower(1);
            stationaryIntake.setPower(1);
        }else if (gamepad1.right_trigger  > 0.05 && gamepad2.right_bumper) {
            rotatingIntake.setPower(-1);
            stationaryIntake.setPower(-1);
        } else if (gamepad1.right_bumper || gamepad2.right_bumper){
            rotatingIntake.setPower(1);
            stationaryIntake.setPower(1);
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
        double dx = (gamepad1.dpad_left ? -strafe_slow_speed : 0) + (gamepad1.dpad_right ? strafe_slow_speed : 0);


        if(gamepad2.dpad_right && relicSlide.getCurrentPosition() < maxEncoder) {
            relicSlide.setPower(.2);
        }
        if(gamepad2.dpad_left && relicSlide.getCurrentPosition() > minEncoder) {
            relicSlide.setPower(-.2);
        }

        if(gamepad2.dpad_up) {
            minEncoder = relicSlide.getCurrentPosition();
        }
        if(gamepad2.dpad_down) {
            maxEncoder = relicSlide.getCurrentPosition();
        }
        telemetry.addData("Linear Slide", relicSlide.getCurrentPosition());



        backLeftDrive.setPower(y - x - w + dx - dy);
        frontLeftDrive.setPower(x + y - w - dx - dy);
        backRightDrive.setPower(x + y + w - dx - dy);
        frontRightDrive.setPower(y - x + w + dx - dy);

        if (gamepad1.a) lift(1);
        if (gamepad1.b) {
            rotatingIntake.setPower(-1);
        }
        if (gamepad1.x) {
            rotatingIntake.setPower(1);
        }

        telemetry.addData("LeftLiftPos", leftLift.getPower());
        telemetry.addData("RightLiftPos", rightLift.getPower());
    }
}
