package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;

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
    DcMotor relicSlide;

    CRServo leftLift;
    CRServo rightLift;

    Servo verticalArm;
    Servo horizontalArm;
    Servo grabber;

    ColorSensor colorSensor;
    double fast_speed = .4;
    double strafe_fast_speed = 0.8;
    double slow_speed = .2;
    double strafe_slow_speed = .3;

    boolean isGrabberOpen = false;
    boolean toggledlastloop = false;

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
        relicSlide = hardwareMap.get(DcMotor.class, "rel");
        leftLift = hardwareMap.get(CRServo.class, "leftLift");
        rightLift = hardwareMap.get(CRServo.class, "rightLift");
        grabber = hardwareMap.get(Servo.class, "grabber");
        colorSensor = hardwareMap.get(ColorSensor.class, "cs");
        frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        stationaryIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        verticalArm = hardwareMap.servo.get("1");
        horizontalArm = hardwareMap.servo.get("2");
        stationaryIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        grabberSetPosition(false);
        relicSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        relicSlide.setDirection(DcMotorSimple.Direction.REVERSE);
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
     * Sets postition of relic grabing mechanism
     * @param OpenState Decides whether the grabber is open
     */
    public void grabberSetPosition (boolean OpenState) {
        if (OpenState) {
            grabber.setPosition(0);
        } else {
            grabber.setPosition(.80);
        }
    }

    public void toggle (){
        isGrabberOpen ^= true;
        grabberSetPosition(isGrabberOpen);
    }
    public void processGamepad1A (){
        if (gamepad2.a && !toggledlastloop){
            toggle();
        }
        toggledlastloop = gamepad2.a;
    }

    public void encoderDrive(int desiredPosition) {
        double power = 0;
        double storePower = frontLeftDrive.getPower();
        power = .03*(desiredPosition - frontLeftDrive.getCurrentPosition());
        power = storePower + limitAcceleration(frontLeftDrive.getCurrentPosition(), desiredPosition, storePower, power);
        drive(0, power, 0);
    }

    public double limitAcceleration(int pos, int desPos,double storePower, double power) {
        if(power - storePower > .1 || power - storePower < -.1) {
            return power - storePower < 0 ? -.1 : .1;
        }

        else return power - storePower;

        /*power += 10*(power - storePower)/(pos - desPos + 1);
        return power;*/
    }

    /**
     *  raise lift
     */

    public void lift(){
        leftLift.setPower(.3);
        rightLift.setPower(.79);
    }
    public void sleep (int sleeptime) {
        try {
            Thread.sleep(sleeptime, 0);
        } catch (InterruptedException e) {
            RobotLog.v("Got interupted while sleeping for %d millis. Error %s", sleeptime, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void loop() {

    }
}
