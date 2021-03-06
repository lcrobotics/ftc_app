package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcontroller.internal.ColumnToScore;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.concurrent.BlockingQueue;

public abstract class AutoOp extends MechDrive {


    public int state = 0;

    private final int START = 0;
    private final int INITCAMERA = 1;
    private final int CHECKJEWELS = 2;
    private final int KNOCKJEWELLEFT = 3;
    private final int KNOCKJEWELRIGHT = 4;
    private final int DRIVETOFIRSTCOL = 12;
    private final int PARKING = 9;
    private final int END = 10;
    private final int JEWELDONE = 11;

    private boolean leftRed;

    private VuforiaLocalizer vuforia;
    VuforiaTrackable relicTemplate;
    private VuforiaTrackables relicTrackables;
    private ColumnToScore column = ColumnToScore.UNKNOWN;


    @Override
    public void init() {
        super.init();
        this.msStuckDetectLoop = 30000;

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

        verticalArm.setPosition(.03);
        horizontalArm.setPosition(.09);

    }


    abstract void park(ColumnToScore column);

    public void EncoderAveragingForward (int v){
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRightDrive.setPower(.3);
        frontLeftDrive.setPower(.3);
        backRightDrive.setPower(.3);
        backLeftDrive.setPower(.3);

        int targetFrontLeft = frontLeftDrive.getCurrentPosition() + v;
        int targetBackRight = backRightDrive.getCurrentPosition() + v;

        double averageTargetPos = (targetBackRight + targetFrontLeft) / 2;

        double average = (frontLeftDrive.getCurrentPosition() + backRightDrive.getCurrentPosition()) / 2;

        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + v);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + v);

        while (Math.abs(average - averageTargetPos) > (10)) {
            sleep(100);
            telemetry.addData("","Back Right %d/%d", backRightDrive.getCurrentPosition(), targetBackRight);
            telemetry.addData("","Front Left %d/%d", frontLeftDrive.getCurrentPosition(), targetFrontLeft);
            telemetry.update();
        }

        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
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
        int vf1 = frontRightDrive.getCurrentPosition() + v;
        int vf2 = backLeftDrive.getCurrentPosition() + v;
        int vf3 = backRightDrive.getCurrentPosition() + v;

        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() + v);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + v);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() + v);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + v);

        while (Math.abs(frontRightDrive.getCurrentPosition() - vf) > (10)) {
            sleep(100);
            telemetry.addData("","Left Back %d/%d", backLeftDrive.getCurrentPosition(), vf2);
            telemetry.addData("","Back Right %d/%d", backRightDrive.getCurrentPosition(), vf3);
            telemetry.addData("","Front Left %d/%d", frontLeftDrive.getCurrentPosition(), vf);
            telemetry.addData("", "Front Right Drive %d/%d",frontRightDrive.getCurrentPosition(), vf1);
            telemetry.update();
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

    public void EncoderStrafe (int v){
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


    public void deployArm(){
        verticalArm.setPosition(.5);
        sleep(250);
        horizontalArm.setPosition(.5);
        sleep(250);
        verticalArm.setPosition(.769);
    }
    @Override
    public void loop() {
        telemetry.addData("State", state);
        switch(state) {
            case START:
                relicTrackables.activate();
                deployArm();
                state = CHECKJEWELS;

                break;

            case INITCAMERA:
                column = ColumnToScore.from(RelicRecoveryVuMark.from(relicTemplate));
                if (column != ColumnToScore.UNKNOWN) {
                    RobotLog.v("COLUMN IS: %s", column);
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


                            Bitmap bitmap = Bitmap.createBitmap(img.getBufferWidth(), img.getBufferHeight(), Bitmap.Config.RGB_565);
                            bitmap.copyPixelsFromBuffer(img.getPixels());

                            float leftX = img.getWidth() * 0.76f;
                            float leftY = img.getHeight() * 0.17f;
                            float radius = img.getWidth() / 6;

                            int colorA = averageColorDisk(bitmap, leftX, leftY, radius);
                            RobotLog.v("Colors for left ball are Red: %s, Blue: %s, Green: %s", Color.red(colorA), Color.blue(colorA), Color.green(colorA));
                            String colormsg = String.format("Red: %s, Blue: %s, Green: %s", Color.red(colorA), Color.blue(colorA), Color.green(colorA));
                            telemetry.addLine(colormsg);

                            leftRed = Color.red(colorA) > Color.blue(colorA);
                            if (leftRed) {
                                telemetry.addLine("Red Ball");
                                RobotLog.v("The ball is RED");
                                state = isBlueTeam() ? KNOCKJEWELLEFT : KNOCKJEWELRIGHT ;
                            }
                            else {
                                telemetry.addLine("Blue Ball");
                                RobotLog.v("The ball is BLUE");
                                state = isBlueTeam() ? KNOCKJEWELRIGHT : KNOCKJEWELLEFT;
                            }
                            frame.close();
                            break whileLoop;
                        }
                    }
                    frame.close();

                }
                break;
            case KNOCKJEWELLEFT:
                horizontalArm.setPosition(0);
                sleep(250);
                state = JEWELDONE;
                break;

            case KNOCKJEWELRIGHT:
                horizontalArm.setPosition(1);
                sleep(250);
                state = JEWELDONE;
                break;
            case JEWELDONE:
                verticalArm.setPosition(0.13);
                horizontalArm.setPosition(.09);
                sleep(300);
                lift();
                sleep(6000);
                verticalArm.setPosition(0.03);
                state = PARKING;
                break;

            case DRIVETOFIRSTCOL:
                drive(0, 0, 0);
                BlockingQueue<VuforiaLocalizer.CloseableFrame> frameQueue2 = vuforia.getFrameQueue();
                VuforiaLocalizer.CloseableFrame frame2;
                int ct2 = 0;
                whileLoop2:
                while ((frame2 = frameQueue2.poll()) != null) {
                    drive(0, 0, 0);
                    ct2++;

                    for (int i = 0; i < frame2.getNumImages(); i++) {
                        Image img = frame2.getImage(i);
                        RobotLog.v("Format %d, height %d, width %d", img.getFormat(), img.getHeight(), img.getWidth());
                        if (img.getFormat() == PIXEL_FORMAT.GRAYSCALE) {
                            continue;
                        } else {
                            Bitmap bitmap = Bitmap.createBitmap(img.getBufferWidth(), img.getBufferHeight(), Bitmap.Config.RGB_565);
                            bitmap.copyPixelsFromBuffer(img.getPixels());

                            int r = 0;
                            int g = 0;
                            int b = 0;
                            double otherBlue = 0;
                            double sumOfBlue = 0;
                            for(int j = 0; j < bitmap.getHeight(); j++) {
                                int color = bitmap.getPixel(j, bitmap.getWidth()/2);
                                r += Color.red(color);
                                g += Color.green(color);
                                b += Color.blue(color);
                                otherBlue += Color.blue(color)/Math.sqrt(Color.red(color) * Color.red(color) + Color.blue(color) * Color.blue(color) + 1);
                                sumOfBlue += Color.blue(color)/Math.sqrt(Color.red(color) * Color.red(color) + Color.blue(color) * Color.blue(color) + 1) * j;

                            }

                            sumOfBlue /= otherBlue;

                            r /= bitmap.getWidth();
                            g /= bitmap.getWidth();
                            b /= bitmap.getWidth();

                            telemetry.addData("R", r);
                            telemetry.addData("G", g);
                            telemetry.addData("B", b);
                            telemetry.addData("Width", bitmap.getWidth());
                            telemetry.addData("Height", bitmap.getHeight());
                            telemetry.addData("Center of blue", sumOfBlue);

                            int centerH = bitmap.getHeight()/2;
                            double power = 0;
                            if(sumOfBlue - centerH > 10) {
                                power = .3 * (sumOfBlue - centerH) / centerH + .1;
                            } else if(sumOfBlue - centerH < -10) {
                                power = .3 * (sumOfBlue - centerH) / centerH - .1;
                            }
                            telemetry.addData("Power", power);
                            telemetry.update();
                            drive(0, power, 0);

                        }
                    }
                    frame2.close();
                }
                break;

            case PARKING:
                park(column);
                state = END;
                break;
            case END:
                verticalArm.setPosition(0.03);
                break;
            default: break;
        }
        if (column ==  ColumnToScore.UNKNOWN) {
            column = ColumnToScore.from(RelicRecoveryVuMark.from(relicTemplate));
        }
        if (column != ColumnToScore.UNKNOWN) {
            RobotLog.v("COLUMN IS: %s", column);
            relicTrackables.deactivate();
        }
        telemetry.addData("column", column.toString());
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

