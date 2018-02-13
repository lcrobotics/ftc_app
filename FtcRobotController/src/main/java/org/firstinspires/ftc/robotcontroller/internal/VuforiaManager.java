package org.firstinspires.ftc.robotcontroller.internal;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.concurrent.BlockingQueue;

/**
 * Created by aozdemir
 */
public class VuforiaManager {

    private VuforiaLocalizer vuforia;
    private VuforiaTrackable relicTemplate;
    private VuforiaTrackables relicTrackables;
    private RelicRecoveryVuMark vuMark;
    private BlockingQueue<VuforiaLocalizer.CloseableFrame> frameQueue;
    private Bitmap bitmap = null;

    /**
     * Create an instance of vuforia tracking which exposes the currently visible image.
     * @param hardwareMap -- the hardwareMap for your opmode
     * @param cameraDirection -- which camera to use
     */
    public VuforiaManager(HardwareMap hardwareMap, VuforiaLocalizer.CameraDirection cameraDirection) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AXkMfE//////AAAAGeoid3R/QkyvsvhE2vtnsyyDQKIMOCtifA0K0u47e5Rq8EJ1bcCpJu7o7gx5ijO9W3Ec9de3oWpPQF9sWWvPLeU22iBDpOSejIMch+IbHWNKRmDto3Q4N+C1f71g05vCgVx6ko432OZVvSO+kQJRC91pWPU2XYHZ4qXE7k6iKbLUwhsRaUYNstEkQQGDxn9FrBqO5RLwvU8ZfvQEIjOFJcwSK4EBe5F9WzLZtK0P5nWnaqtUcmPvnNcaR7ynTl54HxWyXO8a/ePz6fNj2nVEUYXa6u2NDvzbJ4UTPpgmfKtHIrQbnXC+QuZRlCXqqIvfcz/Wei6jyOiWMvXz8mw8Jf6a+9yYjc11z5aLMwG1ppFZ";
        parameters.cameraDirection = cameraDirection;

        // Initialize Vuforia
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        // We ask Vuforia to also provide RGB565 images.
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        // We ask Vuforia to keep the last 1 images.
        vuforia.setFrameQueueCapacity(1);

        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        frameQueue = vuforia.getFrameQueue();
    }

    /**
     * @return The image that was most recently delivered by Vuforia
     *
     * It need not be "closed" or anything like that.
     */
    public Bitmap getLatestColorImage() {
        refreshBitmapFromQueue();
        return bitmap;
    }

    private void refreshBitmapFromQueue() {
        VuforiaLocalizer.CloseableFrame frame = null;
    queueLoop:
        while ((frame = frameQueue.poll()) != null) {
            for (int i = 0; i < frame.getNumImages(); i++) {
                Image img = frame.getImage(i);
                if (img.getFormat() == PIXEL_FORMAT.RGB565) {
                    bitmap = Bitmap.createBitmap(img.getBufferWidth(), img.getBufferHeight(), Bitmap.Config.RGB_565);
                    bitmap.copyPixelsFromBuffer(img.getPixels());
                    break queueLoop;
                }
            }
            frame.close();
        }
        if (frame != null) frame.close();
    }

    /**
     * @return The currently visible viewMark: a representation of which column to deliver blocks to.
     */
    public RelicRecoveryVuMark getVuMark() {
        vuMark = RelicRecoveryVuMark.from(relicTemplate);
        return vuMark;
    }

}
