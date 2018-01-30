package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Draft1 extends MechDrive {


    private int maxEncoder = 14000;
    private int minEncoder = 100;

    @Override
    public void init() {
        super.init();
//        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
//        frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
//        backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
//        backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        lift(1);

        servo2.setPosition(.5);
        servo1.setPosition(0);
        relicSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    @Override
    public void loop() {

        servo1.setPosition(0);
        servo2.setPosition(.5);
        float slidePower = 0f;

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


        if (gamepad2.dpad_up) conveyor.setPower(.3);
        else if (gamepad2.dpad_down) conveyor.setPower(-.3);
        else conveyor.setPower(gamepad2.left_stick_y);


        //main drive (Strafe) (Right Joystick)
        double dy = (gamepad1.dpad_down ? -slow_speed : 0) + (gamepad1.dpad_up ? slow_speed : 0);
        double dx = (gamepad1.dpad_left ? -strafe_slow_speed : 0) + (gamepad1.dpad_right ? strafe_slow_speed : 0);
        double w = -gamepad1.right_stick_x * fast_speed;
        double y = ((-gamepad1.left_stick_y * fast_speed) + dy);
        double x = ((gamepad1.left_stick_x * fast_speed) + dx);


        telemetry.addData("Y value", gamepad1.left_stick_y);

        if(gamepad2.dpad_right && relicSlide.getCurrentPosition() < maxEncoder) {
            slidePower = 1f;
        }
        if(gamepad2.dpad_left && relicSlide.getCurrentPosition() > minEncoder) {
            slidePower = -.5f;
        }

        telemetry.addData("Linear Slide", relicSlide.getCurrentPosition());

        drive(x,y,w);
        telemetry.addData("Back Right" ,backRightDrive.getCurrentPosition());
        telemetry.addData("Back Left", backLeftDrive.getCurrentPosition());
        telemetry.addData("Front right", frontRightDrive.getCurrentPosition());
        telemetry.addData("Front Left", frontLeftDrive.getCurrentPosition());
//        backLeftDrive.setPower(y - x - w + dx - dy);
//        frontLeftDrive.setPower(x + y - w - dx - dy);
//        backRightDrive.setPower(x + y + w - dx - dy);
//        frontRightDrive.setPower(y - x + w + dx - dy);

        if (gamepad1.b) {
            rotatingIntake.setPower(-1);
        }
        if (gamepad1.x) {
            rotatingIntake.setPower(1);
        }

        telemetry.addData("LeftLiftPos", leftLift.getPower());
        telemetry.addData("RightLiftPos", rightLift.getPower());

        processGamepad1A();
        relicSlide.setPower(slidePower);
    }
}
