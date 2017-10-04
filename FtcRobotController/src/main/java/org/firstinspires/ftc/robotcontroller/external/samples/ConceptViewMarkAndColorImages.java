package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.concurrent.BlockingQueue;

/**
 * Created by aozdemir.
 * Gets color images from Vuphoria
 */
public class ConceptViewMarkAndColorImages extends LinearOpMode {

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
        telemetry.addData("RGB888 images now available: ", Vuforia.setFrameFormat(PIXEL_FORMAT.RGB888, true));
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
            }


            // Pull raw images
            VuforiaLocalizer.CloseableFrame frame;

            boolean clear = false;
            while ((frame = frameQueue.poll()) != null) {

                for (int i = 0; i < frame.getNumImages(); i++) {
                    Image img = frame.getImage(i);
                    clear = true;

                    if (img.getFormat() != PIXEL_FORMAT.GRAYSCALE) {
                        nGrayImages++;
                    } else {
                        nNotGrayImages++;
                    }
                    telemetry.addLine("Format " + img.getFormat() + " height " + img.getHeight() + " width " + img.getWidth());
                }

                telemetry.addData("Grayscale count", nGrayImages);
                telemetry.addData("Non-grayscale count", nNotGrayImages);

                frame.close();
            }

            // Only reset the telemetry if new images arrived in the queue
            if (clear)
                telemetry.update();
        }
    }
}
