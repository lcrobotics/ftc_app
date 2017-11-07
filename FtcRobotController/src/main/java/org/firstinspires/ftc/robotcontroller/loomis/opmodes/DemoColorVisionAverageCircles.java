package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.Locale;
import java.util.concurrent.BlockingQueue;

/**
 * Created by aozdemir.
 * Gets color images from Vuphoria
 */
public class DemoColorVisionAverageCircles extends LinearOpMode {

    private VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() {

        // Create parameters object to initialize Vuforia with
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AXkMfE//////AAAAGeoid3R/QkyvsvhE2vtnsyyDQKIMOCtifA0K0u47e5Rq8EJ1bcCpJu7o7gx5ijO9W3Ec9de3oWpPQF9sWWvPLeU22iBDpOSejIMch+IbHWNKRmDto3Q4N+C1f71g05vCgVx6ko432OZVvSO+kQJRC91pWPU2XYHZ4qXE7k6iKbLUwhsRaUYNstEkQQGDxn9FrBqO5RLwvU8ZfvQEIjOFJcwSK4EBe5F9WzLZtK0P5nWnaqtUcmPvnNcaR7ynTl54HxWyXO8a/ePz6fNj2nVEUYXa6u2NDvzbJ4UTPpgmfKtHIrQbnXC+QuZRlCXqqIvfcz/Wei6jyOiWMvXz8mw8Jf6a+9yYjc11z5aLMwG1ppFZ";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        // Initialize Vuforia
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        // We ask Vuforia to also provide RGB888 images.
        telemetry.addData("RGB565 images now available: ", Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true));
        // We ask Vuforia to keep the last 10 images.
        vuforia.setFrameQueueCapacity(10);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        waitForStart();

        relicTrackables.activate();

        int nGrayImages = 0;
        int nNotGrayImages = 0;
        BlockingQueue<VuforiaLocalizer.CloseableFrame> frameQueue = vuforia.getFrameQueue();

        while (opModeIsActive()) {

            // Pull raw images
            VuforiaLocalizer.CloseableFrame frame;

            while ((frame = frameQueue.poll()) != null) {

                for (int i = 0; i < frame.getNumImages(); i++) {
                    Image img = frame.getImage(i);

                    if (img.getFormat() == PIXEL_FORMAT.GRAYSCALE) {
                        nGrayImages++;
                    } else {
                        nNotGrayImages++;
                        Bitmap bitmap = Bitmap.createBitmap(img.getBufferHeight(), img.getBufferWidth(), Bitmap.Config.RGB_565);
                        bitmap.copyPixelsFromBuffer(img.getPixels());
                        int x = 100;
                        int y = 200;
                        int color = bitmap.getPixel(x, y);
                        telemetry.addData("Corner Red", Color.red(color));
                        telemetry.addData("Corner Green", Color.green(color));
                        telemetry.addData("Corner Blue", Color.blue(color));
                        telemetry.addData(String.format(Locale.US, "x: %d/%d, y: %d/%d", x, bitmap.getWidth(), y, bitmap.getHeight()), "");

                        int aveColor = averageColorDisk(bitmap, x, y, 10);
                        telemetry.addData("Average Red", Color.red(aveColor));
                        telemetry.addData("Average Green", Color.green(aveColor));
                        telemetry.addData("Average Blue", Color.blue(aveColor));

                        telemetry.addData("Grayscale count", nGrayImages);
                        telemetry.addData("Non-grayscale count", nNotGrayImages);

                        // Only reset the telemetry if new color arrived in the queue
                        telemetry.update();
                    }
                }
                frame.close();
            }

        }
    }

    /**
     * Returns the average color over the *whole* bitmap
     * @param bitmap
     * @return The color in integer form, to be intepreted with Color.red, Color.blue, Color.green, etc.
     */
    private int averageColorWholeImage(Bitmap bitmap) {
        int r = 0;
        int g = 0;
        int b = 0;
        int n = 0;
        for (int x = 0; x < bitmap.getWidth(); x++) {
            for (int y = 0; y < bitmap.getHeight(); y++) {
                r += Color.red(bitmap.getPixel(x, y));
                g += Color.green(bitmap.getPixel(x, y));
                b += Color.blue(bitmap.getPixel(x, y));
                n += 1;
            }
        }
        r /= n;
        g /= n;
        b /= n;
        return Color.rgb(r, g, b);
    }

    /**
     * Returns the average color in a disk.
     * If the disk goes off the edge of the image, then it only includes the pixels that are both
     * in the disk, and in the image.
     *
     * @param bitmap the image
     * @param centerX the x coordinate of the center of the disk
     * @param centerY the y coordinate of the center of the disk
     * @param radius the radius of the disk
     * @return an integer representing the average color. Interpret with `Color.red`, etc.
     */
    private int averageColorDisk(Bitmap bitmap, double centerX, double centerY, double radius) {
        int r = 0;
        int g = 0;
        int b = 0;
        int n = 0;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        double bottomX = centerX - radius;
        double topX = centerX + radius;

        for (int x = (int) bottomX; x < topX; x++) {

            double y_radius = Math.sqrt(radius * radius - (centerX - x) * (centerX - x));

            double bottomY = centerY - y_radius;
            double topY = centerY + y_radius;

            for (int y = (int) bottomY; y < topY; y++) {

                if (x >= 0 && x < width && y >= 0 && y < height) {
                    int color = bitmap.getPixel(x, y);
                    r += Color.red(color);
                    g += Color.green(color);
                    b += Color.blue(color);
                    n += 1;
                }

            }

        }

        r /= n;
        g /= n;
        b /= n;

        return Color.rgb(r, g, b);
    }
}

