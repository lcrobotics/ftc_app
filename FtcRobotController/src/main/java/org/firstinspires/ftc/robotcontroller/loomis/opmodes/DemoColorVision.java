package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.Matrix;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robot.Robot;
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

import java.util.ArrayList;
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
            int ct = 0;
            while ((frame = frameQueue.poll()) != null) {
                ct++;

                for (int i = 0; i < frame.getNumImages(); i++) {
                    Image img = frame.getImage(i);
                    RobotLog.v("Format %d, height %d, width %d", img.getFormat(), img.getHeight(), img.getWidth());
                    if (img.getFormat() == PIXEL_FORMAT.GRAYSCALE) {
                        nGrayImages++;
                        continue;
                    } else {
                        nNotGrayImages++;
                        //telemetry.addData("iteration", ct);

                        Mat image = new Mat(img.getBufferHeight(), img.getBufferWidth(), CV_8UC3);
                        Mat copy = new Mat(img.getBufferHeight(), img.getBufferWidth(), CV_8UC1);
                        MatOfPoint3f points = new MatOfPoint3f();
                        Bitmap bitmap = Bitmap.createBitmap(img.getBufferHeight(), img.getBufferWidth(), Bitmap.Config.RGB_565);
                        bitmap.copyPixelsFromBuffer(img.getPixels());
                       // Utils.bitmapToMat(bitmap, image);
                       // Imgproc.cvtColor(image, copy, Imgproc.COLOR_BGR2GRAY);
                       // Imgproc.HoughCircles(copy, points, Imgproc.CV_HOUGH_GRADIENT,3.0,20.0,100,100,100,150);
                       // RobotLog.v("HOUGHCIRCLES PROBABLY RAN!!!");
                       // Point3[] circles = points.toArray();
                       // RobotLog.v("CIRCLE LENGTH IS %d", circles.length);
                       // ArrayList<Point3> uniqueCircles = new ArrayList<>();
                        float leftX = 100;
                        float leftY = img.getBufferWidth()/2;
                        float rightX = img.getBufferHeight()/4*3;
                        float radius = img.getBufferWidth()/10;
                        /*for(int j = 0; j < circles.length && j < 10; j++) {
                            RobotLog.v("CIRCLE FOR LOOP INDEX : %s!!!", j);
                            telemetry.addLine("Center: (" + circles[j].x + ", " + circles[j].y + ") Radius: " + circles[j].z);
                            RobotLog.v("X = %f, Y = %f, RADIUS = %f",circles[j].x,circles[j].y, circles[j].z);
                            //Point3 point3 = new Point3();
                            //point3.x = circles[j].x;
                            //point3.y = circles[j].y;
                            //point3.z = circles[j].z;
                            //avgX += (float)circles[j].x/circles.length;
                            //avgY += (float)circles[j].y/circles.length;
                        }*/
                        RobotLog.v("LeftX: %s, LeftY: %s, RightX: %s, Radius: %s", leftX,leftY,rightX,radius);
                        int colorA = averageColorDisk(bitmap, leftX ,leftY, radius);
                        int colorB = averageColorDisk(bitmap, rightX ,leftY, radius);
                        RobotLog.v("Colors for left ballA are Red: %s, Blue: %s, Green: %s", Color.red(colorA), Color.blue(colorA), Color.green(colorA));
                        RobotLog.v("Colors for right ballB are Red: %s, Blue: %s, Green: %s", Color.red(colorB), Color.blue(colorB), Color.green(colorB));

                        if(Color.red(colorA) > 120) {
                            telemetry.addLine("Red Ball");
                            RobotLog.v("The ball is RED");
                        }

                        if(Color.blue(colorA) > 120) {
                            telemetry.addLine("Blue Ball");
                            RobotLog.v("The ball is BLUE");
                        }


                        telemetry.update();
                        RobotLog.v("LOG MESSAGE : END OF CIRCLE LOOP!!!");
                      //  telemetry.addData("Length of points is ", points.length);
                        //telemetry.update();
                    }
                }
                frame.close();
            }

            // Only reset the telemetry if new images arrived in the queue
        }
    }

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

