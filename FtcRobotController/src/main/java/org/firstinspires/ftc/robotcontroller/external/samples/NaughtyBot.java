
package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Hardware;

/**
 * Created by JH on 9/12/17.
 */

public class NaughtyBot extends OpMode {

    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;

    @Override
    public void init() {
        frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftDrive = hardwareMap.get(DcMotor.class, "backLeft");
        backRightDrive = hardwareMap.get(DcMotor.class, "backRight");

    }

    @Override
    public void loop() {
        float LX = gamepad1.left_stick_x;
        float LY = gamepad1.left_stick_y;
        float RX = gamepad1.right_stick_x;
        float RY = gamepad1.right_stick_y;

        backLeftDrive.setPower(((LX+RX)/2)+LY);
        frontLeftDrive.setPower(LY-((LX+RX)/2));
        frontRightDrive.setPower(((LX+RX)/2)+RY);
        backRightDrive.setPower(RY-((LX+RX)/2));


    }
}
