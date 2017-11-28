package org.firstinspires.ftc.robotcontroller.loomis.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.sun.tools.javac.util.ByteBuffer;
import com.vuforia.Image;

import android.graphics.Bitmap;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Consumer;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import java.util.*;
import java.util.HashSet;

/**
 * This OpMode illustrates the basics of using the Vuforia engine to determine
 * the identity of Vuforia VuMarks encountered on the field. The code is structured as
 * duplicate the core Vuforia documentation found there, but rather instead focus on the
 * differences between the use of Vuforia for navigation vs VuMark identification.
 *
 * @see VuforiaLocalizer
 * @see VuforiaTrackableDefaultListener
 * see  ftc_app/doc/tutorial/FTC_FieldCoordinateSystemDefinition.pdf
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained in {@link }.
 */

@Autonomous(name="Concept: VuMark Id", group ="Concept")
@Disabled
public class GrayscaleOpMode extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    VuforiaLocalizer vuforia;


    public class MyPair {
        private final Double key;
        private final Double value;

        public MyPair(Double aKey, Double aValue) {
            key = aKey;
            value = aValue;
        }

        public Double key() {
            return key;
        }

        public Double value() {
            return value;
        }
}



@Override public void runOpMode() {

    //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    /*
     * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
     * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
     */
    int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
    VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

    // OR...  Do Not Activate the Camera Monitor View, to save power
    // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code onthe next line, between the double quotes.
     */
    parameters.vuforiaLicenseKey = "AYz00q7/////AAAAGa7gZ11UPU1+mLCbfwcnujSJMV8UDdXssSUo+HIcwgEpBBQHxTlhQ+EYnzrK+v/gGXF3Gw2/tDmTnLB/ZOZMz8/UgVY8IfFmQysbn65Uw8Tb52soPSX0/1m9RQ9e7v2R0rfSZ5IwycbHm3FzlfjJMmxLybHyF/jYoj86tMd+ZtBHiqGt3v/vAthOCZhucS0PwhHFMtAJJy+iZG3TZZ1MmAMRX2M/w7puCvgE3H1fDRYEqCpG/KmEMVn21KuFRjaRkHbP4aMoObjAbqzctk0rLBUxbqNPkDTUkUcMCm98v44xYd/qxEl8F6tInoFqa8mHGFOrJBzTOHpKGr+Q+V7cFINyOsz2ZuUStqTtaXM1bI/3    ";

    /*
     * We also indicate which camera on the RC that we wish to use.
     * Here we chose the back (HiRes) camera (for greater range), but
     * for a competition robot, the front camera might be more convenient.
     */
    parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
    this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

    /**
     * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
     * in this data set: all three of the VuMarks in the game were created from this one template,
     * but differ in their instance id information.
     * @see VuMarkInstanceId
     */
    VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
    VuforiaTrackable relicTemplate = relicTrackables.get(0);
    relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

    telemetry.addData(">", "Press Play to start");
    telemetry.update();

    vuforia.setFrameQueueCapacity(100);

    waitForStart();

    relicTrackables.activate();



    while (opModeIsActive()) {


            VuforiaLocalizer.CloseableFrame tempFrame = vuforia.getFrameQueue().poll();

            if(tempFrame != null) {

                Image image = tempFrame.getImage(0);
                Bitmap bitmap = Bitmap.createBitmap(image.getBufferWidth(), image.getBufferHeight(), Bitmap.Config.ALPHA_8);
                bitmap.copyPixelsFromBuffer(image.getPixels());
                Bitmap gradient = Bitmap.createBitmap(image.getBufferWidth()-1, image.getBufferHeight()-1, Bitmap.Config.ALPHA_8);
                HashSet blueCoord = new HashSet();
                Bitmap circles = Bitmap.createBitmap(image.getBufferWidth()-1, image.getBufferHeight()-1, Bitmap.Config.ALPHA_8);


                for(int i = 1; i < bitmap.getHeight()-2; i++) {
                    for(int j = 1; j < bitmap.getWidth() - 2; j++) {
                        int color = bitmap.getPixel(j,i) >> 24 & 0xff;
                        int colorx = bitmap.getPixel(j+1,i) >> 24 & 0xff;
                        int colory = bitmap.getPixel(j,i+1) >> 24 & 0xff;
                        gradient.setPixel(j,i,(int)(Math.sqrt(((color-colorx)*(color-colorx))+(color-colory)*(color-colory))));
                        telemetry.addData("I: ", i);
                        telemetry.addData("J: ", j);
                        telemetry.update();
                    }
                }
                for(int i = 41; i < gradient.getHeight()-41; i++) {
                    for(int j = 41; j < gradient.getWidth()-41; j++) {
                        int color = gradient.getPixel(j,i);
                        if(color > 3) {


                            for (double t = 0; t < 2 * Math.PI; t = t + .2) {

                                telemetry.addData("I-circle: ", i);
                                telemetry.addData("J-circle: ", j);
                                telemetry.update();

                                circles.setPixel(
                                        (int) (j + 40 * Math.cos(t)),
                                        (int) (i + 40 * Math.sin(t)),
                                        (circles.getPixel((int) (j + 40 * Math.cos(t)), (int) (i + 40 * Math.sin(t))) + color));
                            }
                        }
                    }
                }
                int max = -1;
                int maxX = 0;
                int maxY = 0;

                for(int i = 0; i < circles.getHeight()-1; i++) {
                    for(int j = 0; j < circles.getWidth()-1; j++) {
                        int color = circles.getPixel(j,i);
                        if(color > max) {
                            maxX = j;
                            maxY = i;
                            max = color;
                        }
                    }
                }
                telemetry.addData("MaxX: ", maxX);
                telemetry.addData("MaxY: ", maxY);
                telemetry.update();
                Telemetry.Log.DisplayOrder.valueOf("Hi");
            }
            else {
                telemetry.addLine("Poll returned null");
            }

            /**
             * See if any of the instances of {@link relicTemplate} are currently visible.
             * {@link RelicRecoveryVuMark} is an enum which can have the following values:
             * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
             * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
             */
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                /* Found an instance of the template. In the actual game, you will probably
                 * loop until this condition occurs, then move on to act accordingly depending
                 * on which VuMark was visible. */
                telemetry.addData("VuMark", "%s visible", vuMark);

                /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
                 * it is perhaps unlikely that you will actually need to act on this pose information, but
                 * we illustrate it nevertheless, for completeness. */
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
                telemetry.addData("Pose", format(pose));

                /* We further illustrate how to decompose the pose into useful rotational and
                 * translational components */
                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    // Extract the X, Y, and Z components of the offset of the target relative to the robot
                    double tX = trans.get(0);
                    double tY = trans.get(1);
                    double tZ = trans.get(2);

                    // Extract the rotational components of the target relative to the robot
                    double rX = rot.firstAngle;
                    double rY = rot.secondAngle;
                    double rZ = rot.thirdAngle;
                }
            }
            else {
                telemetry.addData("VuMark", "not visible");
            }

            telemetry.update();
        }


    }

    public static int[] convertToIntArray(byte[] input)
    {
        int[] ret = new int[input.length];
        for (int i = 0; i < input.length; i++)
        {
            ret[i] = input[i] & 0xff; // Range 0 to 255, not -128 to 127
        }
        return ret;
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}