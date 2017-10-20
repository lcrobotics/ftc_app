package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import android.graphics.Bitmap;
import android.opengl.Matrix;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.Point3;
import org.opencv.imgproc.Imgproc;

import java.util.concurrent.BlockingQueue;

import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.CvType.CV_8UC3;

/**
 * Created by aozdemir.
 * Gets color images from Vuphoria
 */
public class DemoColorVision extends LinearOpMode {

    private VuforiaLocalizer vuforia;

    @Override public void runOpMode() {

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

            // Look for ViewMark
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
            } else {
                telemetry.addData("VuMark", "not visible");
            }


            // Pull raw images
            VuforiaLocalizer.CloseableFrame frame;

            while ((frame = frameQueue.poll()) != null) {

                for (int i = 0; i < frame.getNumImages(); i++) {
                    Image img = frame.getImage(i);

                    if (img.getFormat() == PIXEL_FORMAT.GRAYSCALE) {
                        telemetry.addLine("Continued");
                        telemetry.update();
                        nGrayImages++;
                        continue;
                    } else {
                        nNotGrayImages++;
                        Mat image = new Mat(img.getBufferHeight(), img.getBufferWidth(), CV_8UC3);
                        Mat copy = new Mat(img.getBufferHeight(), img.getBufferWidth(), CV_8UC1);
                        MatOfPoint3f points = new MatOfPoint3f();
                        Bitmap bitmap = Bitmap.createBitmap(img.getBufferHeight(), img.getBufferWidth(), Bitmap.Config.RGB_565);
                        bitmap.copyPixelsFromBuffer(img.getPixels());
                        Utils.bitmapToMat(bitmap, image);
                        Imgproc.cvtColor(image, copy, Imgproc.COLOR_BGR2GRAY);
                        Imgproc.HoughCircles(copy, points, Imgproc.CV_HOUGH_GRADIENT,1.0,10.0,100,100,40,100);
                        Point3[] circles = points.toArray();
                        byte color1 = 0;
                        byte color2 = 0;
                        byte color3 = 0;
                        for(int j = 0; j < circles.length; j++) {
                            telemetry.addLine("Center: (" + circles[i].x + ", " + circles[i].y + ") Radius: " + circles[i].z);
                            byte[] bytes = new byte[3];
                            image.get((int)circles[i].x,(int)circles[i].y, bytes);
                             color1 += bytes[0];
                             color2 += bytes[1];
                             color3 += bytes[2];
                        }
                      //  telemetry.addData("Length of points is ", points.length);
                        //telemetry.update();
                    }
                    RobotLog.v("Format %d, height %d, width %d", img.getFormat(), img.getHeight(), img.getWidth());
                }
                frame.close();
            }

            telemetry.addData("Grayscale count", nGrayImages);
            telemetry.addData("Non-grayscale count", nNotGrayImages);

            // Only reset the telemetry if new images arrived in the queue
            telemetry.update();
        }
    }
}

