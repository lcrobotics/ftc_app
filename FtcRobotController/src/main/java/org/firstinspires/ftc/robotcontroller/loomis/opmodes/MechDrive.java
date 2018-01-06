package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by peyto on 11/28/2017.
 */

public class MechDrive extends OpMode {


    DcMotor rotatingIntake;
    DcMotor stationaryIntake;
    DcMotor conveyor;
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;
    CRServo leftLift;
    CRServo rightLift;
    Servo servo1;
    Servo servo2;

    double fast_speed = 4. / 10;
    double slow_speed = 2. / 10;
    double strafe_slow_speed = 3. / 10;

    @Override
    public void init() {
        rotatingIntake = hardwareMap.get(DcMotor.class, "rotatingIntake");
        stationaryIntake = hardwareMap.get(DcMotor.class, "stationaryIntake");
        //Not working, should be the method below this, dpad controls instead of joystick
        conveyor = hardwareMap.get(DcMotor.class, "conveyor");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftDrive = hardwareMap.get(DcMotor.class, "backLeft");
        backRightDrive = hardwareMap.get(DcMotor.class, "backRight");
        leftLift = hardwareMap.get(CRServo.class, "leftLift");
        rightLift = hardwareMap.get(CRServo.class, "rightLift");
        frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        stationaryIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        servo1 = hardwareMap.servo.get("1");
        servo2 = hardwareMap.servo.get("2");
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        stationaryIntake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void drive(double x, double y, double w){
        backLeftDrive.setPower(y - x - w);
        frontLeftDrive.setPower(x + y - w);
        backRightDrive.setPower(x + y + w);
        frontRightDrive.setPower(y - x + w);
    }

    public void intake(double v){
        rotatingIntake.setPower(v);
        stationaryIntake.setPower(v);
    }
    public void conveyor (double v) {
        leftLift.setPower(v);
        rightLift.setPower(v);
    }
    /**
     * sets position of lift
     * @param x should be between 0 and 1
     */
    public void lift(double x){
        leftLift.setPower(1-.730*x);
        rightLift.setPower(.730*x + 0.019);
    }
    public void sleep (int sleeptime) {
        try {
            Thread.sleep(sleeptime, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loop() {

    }
}
