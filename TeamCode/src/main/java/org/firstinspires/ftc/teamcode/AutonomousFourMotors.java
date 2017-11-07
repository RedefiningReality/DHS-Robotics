package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="Autonomous Four Motors", group="Autonomous")
public class AutonomousFourMotors extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 0.5;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 1;

    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;

    private DcMotor launcherMotor1;
    private DcMotor launcherMotor2;
    private DcMotor winchMotor;

    private DcMotor zipTieMotor;

    private Servo armishLeverThingy2;
    // private DcMotor armishLeverThingy2;

    private GyroSensor gyro;

    private ColorSensor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {

        // YES THE SERVO IS CALLED ARMISH LEVER THINGY 2.0 (DEAL WITH IT)

        // see method below if servo does not initialize as intended
        initialize();

        // params: drive forward, right wheel 2 inch, left wheel 2 inch, 2 second timeout
        drive(DRIVE_SPEED,  -2,  -2, 2.0);
        // launches 2 balls - perhaps we can launch more than 2...... don't know if that's allowed
        // if launcher is too powerful you can use this method:
        // launch(numBalls, launcherPower, winchPower);
        // defaults:            0.75          0.25
        launch(2);

        drive(TURN_SPEED, -10, 10, 5.0);
        // add code for driving here - drive to edge and turn

        while(gyro.getHeading() > 5 && gyro.getHeading() < 355)
            if(gyro.getHeading() <= 180)
                drive(TURN_SPEED, 0.01, -0.01, 1.0);
            else if (gyro.getHeading() > 180)
                drive(TURN_SPEED, -0.01, 0.01, 1.0);

        // repeat steps twice for each beacon
//        for(int i = 0; i < 2; i++) {
//
//            // keep driving forward until colorSensor.red() > n (figure out n yourself) or vice versa
//            // create a separate autonomous that does the opposite (ex. colorSensor.blue() > n)
//            // make sure it lines up well with button (might have to drive a bit longer after color is sensed)
//            while (colorSensor.red() > 20) {
//                drive(DRIVE_SPEED, -0.1, -0.1, 1.0);
//            }
//            // drive(DRIVE_SPEED, 1, 1, 1);
//
//            // set the position accordingly -> values vary depending upon how servo is placed
//            // if using a motor instead of a servo, uncomment the motor portions of the code
//            armishLeverThingy2.setPosition(1); // 1 is all the way around if using 360 servo
//            armishLeverThingy2.setPosition(0); // rotate back
//            // useMotor(armishLeverThingy2, 0.5, 1500);
//            // useMotor(armishLeveThingy2, -0.5, 1500);
//
//        }

        // possibly drive back and push ball off platform, or at least park on platform
        // drive(TURN_SPEED, 2, -2, 2.0);
        // drive(DRIVE_SPEED, 12, 12, 12.0);

    }

    private void initialize() throws InterruptedException {

        hardwareMap.logDevices();

        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackRight = hardwareMap.dcMotor.get("backRight");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");

        launcherMotor1 = hardwareMap.dcMotor.get("launcherOne");
        launcherMotor2 = hardwareMap.dcMotor.get("launcherTwo");
        winchMotor = hardwareMap.dcMotor.get("winch");

        zipTieMotor = hardwareMap.dcMotor.get("zipTie");

        armishLeverThingy2 = hardwareMap.servo.get("armishLeverThingy2.0");
        // armishLeverThingy2 = hardwareMap.dcMotor.get("armishLeverThingy2.0");
        // armishLeverThingy2.setPower(0); - redundant but I did it with all of the other motors

        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        colorSensor = hardwareMap.colorSensor.get("colorSensor");

        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        launcherMotor2.setDirection(DcMotor.Direction.REVERSE);

        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);

        launcherMotor1.setPower(0);
        launcherMotor2.setPower(0);
        winchMotor.setPower(0);

        zipTieMotor.setPower(0);

        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // initialize servo position - change accordingly
        armishLeverThingy2.setPosition(0);

        waitForStart();

    }

    private void useMotor(DcMotor motor, double speed, int timeMilliseconds)
            throws InterruptedException{
        motor.setPower(speed);
        Thread.sleep(timeMilliseconds);
        motor.setPower(0);
    }

    private void drive(double speed,
                       double leftInches, double rightInches,
                       double timeoutS) throws InterruptedException {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        telemetry.setAutoClear(false);


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = motorFrontLeft.getCurrentPosition() - (int)(leftInches * COUNTS_PER_INCH);
            newFrontRightTarget = motorFrontRight.getCurrentPosition() - (int)(rightInches * COUNTS_PER_INCH);
            newBackLeftTarget = motorBackLeft.getCurrentPosition() - (int)(leftInches * COUNTS_PER_INCH);
            newBackRightTarget = motorBackRight.getCurrentPosition() - (int)(rightInches * COUNTS_PER_INCH);
            motorFrontLeft.setTargetPosition(newFrontLeftTarget);
            motorFrontRight.setTargetPosition(newFrontRightTarget);
            motorBackLeft.setTargetPosition(newBackLeftTarget);
            motorBackRight.setTargetPosition(newBackRightTarget);

            telemetry.addData("frontLeftTarget", newFrontLeftTarget);
            telemetry.addData("frontRightTarget", newFrontRightTarget);
            telemetry.addData("backLeftTarget", newBackLeftTarget);
            telemetry.addData("backRightTarget", newBackRightTarget);

            // Turn On RUN_TO_POSITION
            motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            motorFrontLeft.setPower(Math.abs(speed));
            motorFrontRight.setPower(Math.abs(speed));
            motorBackLeft.setPower(Math.abs(speed));
            motorBackRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motorFrontLeft.isBusy() || motorFrontRight.isBusy()
                        || motorBackLeft.isBusy() || motorBackRight.isBusy())) {
                telemetry.addData("frontLeftPosition", motorFrontLeft.getCurrentPosition());
                telemetry.addData("frontRightPosition", motorFrontRight.getCurrentPosition());
                telemetry.addData("backLeftPosition", motorBackLeft.getCurrentPosition());
                telemetry.addData("backRightPosition", motorBackRight.getCurrentPosition());

                telemetry.addData("frontLeftSpeed", motorFrontLeft.getPower());
                telemetry.addData("frontRightSpeed", motorFrontRight.getPower());
                telemetry.addData("backLeftSpeed", motorBackLeft.getPower());
                telemetry.addData("backRightSpeed", motorBackRight.getPower());

                telemetry.update();
                idle();
            }

            // Stop all motion;
            motorFrontLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackLeft.setPower(0);
            motorBackRight.setPower(0);

            // Turn off RUN_TO_POSITION
            motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    private void launch(int numBalls) throws InterruptedException {
        launch(numBalls, 1, 0.8);
    }

    private void launch(int numBalls, double launcherPower, double winchPower) throws InterruptedException {
        for(int i = 0; i < numBalls; i++) {
            launcherMotor1.setPower(launcherPower);
            launcherMotor2.setPower(launcherPower);
            winchMotor.setPower(winchPower);
            Thread.sleep(2000);
            launcherMotor1.setPower(0);
            launcherMotor2.setPower(0);
            winchMotor.setPower(-winchPower);
            Thread.sleep(2000);
            winchMotor.setPower(0);

            if(i != numBalls - 1)
                rotateZipTie(2000);
        }
    }

    private void rotateZipTie(int numMilliseconds) throws InterruptedException {
        zipTieMotor.setPower(1);
        Thread.sleep(numMilliseconds);
        zipTieMotor.setPower(0);
    }

}