package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcontroller.internal.VuforiaManager;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.concurrent.BlockingQueue;

public class ColorFinder extends OpMode {

    VuforiaManager vuforia;

    private double x = 0;
    private double y = 0;

    @Override
    public void init() {
        vuforia = new VuforiaManager(hardwareMap, VuforiaLocalizer.CameraDirection.FRONT);

        telemetry.addData(">", "Press Play to start. Hold left bumper to enable.");
        telemetry.update();
    }

    @Override
    public void loop() {
        if (gamepad1.left_bumper) {
            x = gamepad1.right_stick_x;
            y = gamepad1.right_stick_y;
        }

        Bitmap bitmap = vuforia.getLatestColorImage();

        int buf_x = (int) (bitmap.getWidth() * ((x + 1.0) / 2.1));
        int buf_y = (int) (bitmap.getHeight() * ((y + 1.0) / 2.1));

        int color = bitmap.getPixel(buf_x, buf_y);

        telemetry.addData("Color", "(%3d,%3d,%3d) @ (%4d,%4d)", Color.red(color), Color.blue(color), Color.green(color), buf_x, buf_y);
        telemetry.addData("VuMark", vuforia.getVuMark());

        telemetry.update();
    }
}
