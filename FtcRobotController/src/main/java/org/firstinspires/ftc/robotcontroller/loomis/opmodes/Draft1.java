package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Draft1 extends MechDrive {


    private int maxNegEncoder = -4800;
    private int minNegEncoder = -100;

    @Override
    public void init() {
        super.init();
        lift();
        verticalArm.setPosition(.03);
        horizontalArm.setPosition(.09);
        relicSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    @Override
    public void loop() {

        float slidePower = 0f;

        // What speed to go forward at
        if ((gamepad1.right_trigger  > 0.05 || gamepad2.right_trigger > 0.05) && (!gamepad1.right_bumper)) {
            rotatingIntake.setPower(1);
            stationaryIntake.setPower(-1);
        }else if (gamepad1.right_bumper && gamepad2.right_trigger > 0.05) {
            rotatingIntake.setPower(-1);
            stationaryIntake.setPower(1);
        }else if (gamepad1.right_trigger  > 0.05 && gamepad2.right_bumper) {
            rotatingIntake.setPower(1);
            stationaryIntake.setPower(-1);
        } else if (gamepad1.right_bumper || gamepad2.right_bumper){
            rotatingIntake.setPower(-1);
            stationaryIntake.setPower(1);
        } else {
            rotatingIntake.setPower(0);
            stationaryIntake.setPower(0);
        }


        if (gamepad2.dpad_up) conveyor.setPower(.3);
        else if (gamepad2.dpad_down) conveyor.setPower(-.3);
        else conveyor.setPower(gamepad2.left_stick_y);



        //main drive (Strafe) (Right Joystick)
//        double w = -gamepad1.right_stick_x * fast_speed;
//        double y = ((-gamepad1.left_stick_y * fast_speed) + dy);
//        double x = ((gamepad1.left_stick_x * fast_speed) + dx);
        double dy = (gamepad1.dpad_down ? -slow_speed : 0) + (gamepad1.dpad_up ? slow_speed : 0);
        double dx = (gamepad1.dpad_left ? -strafe_slow_speed : 0) + (gamepad1.dpad_right ? strafe_slow_speed : 0);
        double w;
        double y = (-gamepad1.left_stick_y);
        double x = (gamepad1.left_stick_x);


        if (gamepad1.left_trigger > 0.5){
            conveyor.setPower(gamepad1.right_stick_y);
            w = 0;
        }else {
            w = -gamepad1.right_stick_x;
        }


        if (gamepad1.left_bumper){
            w *= slow_speed;
            y *= slow_speed;
            x *= strafe_slow_speed;
        }else {
            w *= fast_speed;
            y *= fast_speed;
            x *= strafe_fast_speed;
        }
            y += dy;
            x += dx;


        telemetry.addData("Y value", gamepad1.left_stick_y);

        if(gamepad2.dpad_right && relicSlide.getCurrentPosition() > maxNegEncoder) {
            slidePower = 1f;
        }
        if(gamepad2.dpad_left && relicSlide.getCurrentPosition() < minNegEncoder) {
            slidePower = -0.5f;
        }

        if(gamepad2.b) {
            blockServo.setPosition(.39);
        }
        else {
            blockServo.setPosition(1);
        }

        telemetry.addData("Block", blockServo.getPosition());
        telemetry.addData("Linear Slide", relicSlide.getCurrentPosition());

        drive(x,y,w);
        telemetry.addData("Back Right" ,backRightDrive.getCurrentPosition());
        telemetry.addData("Back Left", backLeftDrive.getCurrentPosition());
        telemetry.addData("Front right", frontRightDrive.getCurrentPosition());
        telemetry.addData("Front Left", frontLeftDrive.getCurrentPosition());



        telemetry.addData("LeftLiftPos", leftLift.getPower());
        telemetry.addData("RightLiftPos", rightLift.getPower());

        processGamepad2A();
        relicSlide.setPower(slidePower);
    }
}
