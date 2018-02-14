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

import java.util.HashMap;
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

        int color2 = bitmap.getPixel(buf_x, buf_y);

        telemetry.addData("Color", "(%3d,%3d,%3d) @ (%4d,%4d)", Color.red(color2), Color.blue(color2), Color.green(color2), buf_x, buf_y);
        telemetry.addData("VuMark", vuforia.getVuMark());

        int r = 0;
        int g = 0;
        int b = 0;


        HashMap<Integer, Double> hashMap = new HashMap<>();
        for(int i = 0; i < bitmap.getWidth(); i = i + 4) {
            double otherBlue = 0;
            double sumOfBlue = 0;
            for(int j = 0; j < bitmap.getHeight(); j++) {
                int color = bitmap.getPixel(i, j);
                r += Color.red(color);
                g += Color.green(color);
                b += Color.blue(color);
                double blueness = Color.blue(color) / Math.sqrt(Color.red(color) * Color.red(color) + Color.green(color) * Color.green(color) + Color.blue(color) * Color.blue(color) + 1);
                blueness = Math.pow(1 / (Math.log(blueness)), 2);
                otherBlue += blueness;
                sumOfBlue += blueness * j;
            }
            hashMap.put(i, sumOfBlue/otherBlue);
        }

        int maxK = 0;
        double maxV = 0;
        for(int i : hashMap.keySet()) {
            double value = hashMap.get(i);
            if(value > maxV) {
                maxV = value;
                maxK = i;
            }
        }
        telemetry.addData("Column", maxK);
        telemetry.addData("Blueness", maxV);

        //sumOfBlue /= otherBlue;

        r /= bitmap.getWidth();
        g /= bitmap.getWidth();
        b /= bitmap.getWidth();

        telemetry.addData("R", r);
        telemetry.addData("G", g);
        telemetry.addData("B", b);
        telemetry.addData("Width", bitmap.getWidth());
        telemetry.addData("Height", bitmap.getHeight());
       // telemetry.addData("Center of blue", sumOfBlue);

        telemetry.update();
    }
}
