package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.concurrent.BlockingQueue;

public class ColorFinder extends OpMode {

    private VuforiaLocalizer vuforia;
    private VuforiaTrackables relicTrackables;
    private VuforiaTrackable relicTemplate;

    private double x = 0;
    private double y = 0;

    @Override
    public void init() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AXkMfE//////AAAAGeoid3R/QkyvsvhE2vtnsyyDQKIMOCtifA0K0u47e5Rq8EJ1bcCpJu7o7gx5ijO9W3Ec9de3oWpPQF9sWWvPLeU22iBDpOSejIMch+IbHWNKRmDto3Q4N+C1f71g05vCgVx6ko432OZVvSO+kQJRC91pWPU2XYHZ4qXE7k6iKbLUwhsRaUYNstEkQQGDxn9FrBqO5RLwvU8ZfvQEIjOFJcwSK4EBe5F9WzLZtK0P5nWnaqtUcmPvnNcaR7ynTl54HxWyXO8a/ePz6fNj2nVEUYXa6u2NDvzbJ4UTPpgmfKtHIrQbnXC+QuZRlCXqqIvfcz/Wei6jyOiWMvXz8mw8Jf6a+9yYjc11z5aLMwG1ppFZ";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        // Initialize Vuforia
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        // We ask Vuforia to also provide RGB888 images.
        telemetry.addData("RGB565 images now available: ", Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true));
        // We ask Vuforia to keep the last 10 images.
        vuforia.setFrameQueueCapacity(10);

        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        telemetry.addData(">", "Press Play to start. Hold left bumper to enable.");
        telemetry.update();
    }

    @Override
    public void loop() {
        BlockingQueue<VuforiaLocalizer.CloseableFrame> frameQueue = vuforia.getFrameQueue();
        VuforiaLocalizer.CloseableFrame frame;
        while ((frame = frameQueue.poll()) != null) {
            for (int i = 0; i < frame.getNumImages(); i++) {
                Image img = frame.getImage(i);
                if (img.getFormat() == PIXEL_FORMAT.RGB565) {
                    Bitmap bitmap = Bitmap.createBitmap(img.getBufferWidth(), img.getBufferHeight(), Bitmap.Config.RGB_565);
                    bitmap.copyPixelsFromBuffer(img.getPixels());

                    int buf_x = (int) (bitmap.getWidth() * ((x + 1.0) / 2.1));
                    int buf_y = (int) (bitmap.getHeight() * ((y + 1.0) / 2.1));

                    int color = bitmap.getPixel(buf_x, buf_y);

                    telemetry.addData("Color", "(%3d,%3d,%3d) @ (%4d,%4d)", Color.red(color), Color.blue(color), Color.green(color), buf_x, buf_y);
                }
            }
            frame.close();
        }

        if (gamepad1.left_bumper) {
            x = gamepad1.right_stick_x;
            y = gamepad1.right_stick_y;
        }
        telemetry.update();
    }
}
