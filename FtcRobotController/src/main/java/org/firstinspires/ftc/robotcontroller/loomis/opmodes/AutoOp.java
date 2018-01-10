package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint3f;

import java.util.concurrent.BlockingQueue;

import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.CvType.CV_8UC3;

/**
 * Created by Students on 11/29/2017.
 */

public abstract class AutoOp extends MechDrive {


    public int state = 0;

   // private enum State {
        public final int START = 0;
        public final int INITCAMERA = 1;
        public final int CHECKJEWELS = 2;
        public final int KNOCKJEWELLEFT = 3;
        public final int KNOCKJEWELRIGHT = 4;
        public final int LEFTCOLUMN = 5;
        public final int MIDCOLUMN = 6;
        public final int RIGHTCOLUMN = 7;
        public final int END = 8;

    //}

    public boolean rightRed;

    private VuforiaLocalizer vuforia;
    VuforiaTrackable relicTemplate;
    public VuforiaTrackables relicTrackables;
    public RelicRecoveryVuMark vuMark;


    @Override
    public void init() {
        super.init();

        // Create parameters object to initialize Vuforia with
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

        telemetry.addData(">", "Press Play to start");
        telemetry.update();




    }

    abstract void leftColumn();

    abstract void midColumn();

    abstract void rightColumn();

    // NB: unfinished
    public void EncoderForward (int v){
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        frontRightDrive.setPower(.3);
        frontLeftDrive.setPower(.3);
        backRightDrive.setPower(.3);
        backLeftDrive.setPower(.3);

        int vf = frontLeftDrive.getCurrentPosition() + v;

        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() + v);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + v);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() + v);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + v);

        while (Math.abs(frontRightDrive.getCurrentPosition() - vf) > (10)) {
            sleep(100);
        }

        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }


    public void EncoderRotation (int v){
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRightDrive.setPower(.3);
        frontLeftDrive.setPower(.3);
        backRightDrive.setPower(.3);
        backLeftDrive.setPower(.3);

        int vf = frontLeftDrive.getCurrentPosition() + v;

        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() + v);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() - v);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() - v);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + v);

        while (Math.abs(frontRightDrive.getCurrentPosition() - vf) > (10)) {
            sleep(100);
        }

        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void EncoderStraife (int v){
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRightDrive.setPower(.3);
        frontLeftDrive.setPower(.3);
        backRightDrive.setPower(.3);
        backLeftDrive.setPower(.3);

        int vf = frontLeftDrive.getCurrentPosition() + v;


        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() - v);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + v);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() - v);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + v);

        while (Math.abs(frontRightDrive.getCurrentPosition() - vf) > (10)) {
            sleep(100);
        }

        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }



    /**
     * TrapizoidDrive is a drive for the autonomous that is more accurate than the regular drive.
     * It takes longer for the robot to speed up and slow down
     * @param x the straife speed of the robot
     * @param y the forward and backward to the robot
     * @param w the rotation of the robot
     * @param t The total time spent in run (Miliseconds)
     * @param sustain The fraction of time spent at max speed
     */
    public void trapizoidDrive(double x, double y, double w, int t, double sustain){
        double x1 = 0;
        double y1 = 0;
        double w1 = 0;
        double st = t*sustain;
        for (int iterations = 0; iterations < 10; iterations++) {
            x1 = x1 + (x/10);
            y1 = y1 + (y/10);
            w1 = w1 + (w/10);
            drive(x1,y1,w1);
            sleep((int) (.5 * (t - st)) / 10);
        }
        drive(x,y,w);
        sleep((int) (st));
        for (int iterations = 0; iterations < 10; iterations++){
            x1 = x1 - (x/10);
            y1 = y1 - (y/10);
            w1 = w1 - (w/10);
            drive(x1, y1, w1);
            sleep((int) (.5 * (t - st)) / 10);
            telemetry.addData("x = ", x1);
            telemetry.addData("y = ", y1);
            telemetry.addData("w = ", w1);
            drive(0,0,0);
        }
    }


    @Override
    public void loop() {
        telemetry.addData("State", state);
        switch(state) {
            case START:
                relicTrackables.activate();
                state = CHECKJEWELS;
                servo2.setPosition(.83);
                servo1.setPosition(.5);
                sleep(500);
                servo1.setPosition(.8);
                break;

            case INITCAMERA:
                vuMark = RelicRecoveryVuMark.from(relicTemplate);
                if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                    RobotLog.v("COLUMN IS: %s", vuMark);
                    relicTrackables.deactivate();
                    state = CHECKJEWELS;
                }
                break;
            case CHECKJEWELS:
                BlockingQueue<VuforiaLocalizer.CloseableFrame> frameQueue = vuforia.getFrameQueue();
                VuforiaLocalizer.CloseableFrame frame;
                int ct = 0;
                whileLoop:
                while ((frame = frameQueue.poll()) != null) {
                    ct++;

                    for (int i = 0; i < frame.getNumImages(); i++) {
                        Image img = frame.getImage(i);
                        RobotLog.v("Format %d, height %d, width %d", img.getFormat(), img.getHeight(), img.getWidth());
                        if (img.getFormat() == PIXEL_FORMAT.GRAYSCALE) {
                            continue;
                        } else {


                            // TODO: THIS IS BROKEN!
                            Bitmap bitmap = Bitmap.createBitmap(img.getBufferWidth(), img.getBufferHeight(), Bitmap.Config.RGB_565);
                            bitmap.copyPixelsFromBuffer(img.getPixels());

                            float leftX = img.getWidth() * 0.72916f;
                            float leftY = img.getHeight() * 0.416f;
                            float radius = img.getWidth() / 6;

                            int colorA = averageColorDisk(bitmap, leftX, leftY, radius);
                            RobotLog.v("Colors for left ball are Red: %s, Blue: %s, Green: %s", Color.red(colorA), Color.blue(colorA), Color.green(colorA));
                            String colormsg = String.format("Red: %s, Blue: %s, Green: %s", Color.red(colorA), Color.blue(colorA), Color.green(colorA));
                            telemetry.addLine(colormsg);

                            rightRed = Color.red(colorA) > Color.blue(colorA);
                            if (rightRed) {
                                telemetry.addLine("Red Ball");
                                RobotLog.v("The ball is RED");
                                state = isBlueTeam() ? KNOCKJEWELRIGHT : KNOCKJEWELLEFT;
                            }
                            else {
                                telemetry.addLine("Blue Ball");
                                RobotLog.v("The ball is BLUE");
                                state = isBlueTeam() ? KNOCKJEWELLEFT : KNOCKJEWELRIGHT;
                            }
                            frame.close();
                            break whileLoop;
                            /*if (Color.blue(colorA) > 120) {
                                telemetry.addLine("Blue Ball");
                                RobotLog.v("The ball is BLUE");
                                leftIsBlue = true;
                                if(isBlueTeam()) {
                                    state = KNOCKJEWELLEFT;
                                }
                                else state = KNOCKJEWELRIGHT;
                            }*/


                            //  telemetry.addData("Length of points is ", points.length);
                            //telemetry.update();
                        }
                    }
                    frame.close();

                }
                break;
            case KNOCKJEWELLEFT:
                servo2.setPosition(.0);
                sleep(1000);
                servo1.setPosition(0);
                servo2.setPosition(.5);
                state = END;
                break;
             /*   switch (vuMark) {
                    case LEFT: state = LEFTCOLUMN; break;
                    case RIGHT: state = RIGHTCOLUMN; break;
                    case CENTER: state = MIDCOLUMN; break;
                }*/
            case KNOCKJEWELRIGHT:
                servo2.setPosition(1);
                sleep(1000);
                servo2.setPosition(.5);
                servo1.setPosition(0);
                state = END;
                break;
            case LEFTCOLUMN:
                leftColumn();
                state = END;
                break;
            case MIDCOLUMN:
                midColumn();
                state = END;
                break;
            case RIGHTCOLUMN:
                rightColumn();
                state = END;
                break;
            case END:
                lift(1.0);
                break;
            default: break;
        }
        telemetry.update();
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



    public abstract boolean isBlueTeam();

}
