package org.firstinspires.ftc.teamcode.autonomous.dependencies;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by robotics on 1/27/2018.
 */

public abstract class TBAutonomous extends LinearOpMode {

    static final double     DRIVE_SPEED             = 1;
    static final double     TURN_SPEED              = 1;

    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;

    @Override
    public abstract void runOpMode() throws InterruptedException;

    protected void initializeMotors() throws InterruptedException {

        hardwareMap.logDevices();

        motorFrontRight = hardwareMap.dcMotor.get("FR");
        motorFrontLeft = hardwareMap.dcMotor.get("FL");
        motorBackRight = hardwareMap.dcMotor.get("BR");
        motorBackLeft = hardwareMap.dcMotor.get("BL");

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);

        waitForStart();

    }

    protected void drive(double leftPower, double rightPower,
                         long time) throws InterruptedException {
        drive(leftPower, rightPower, leftPower, rightPower, time);
    }

    protected void drive(double frontLeftPower, double frontRightPower,
                         double backLeftPower, double backRightPower,
                         long time) throws InterruptedException {
        motorFrontLeft.setPower(frontLeftPower);
        motorFrontRight.setPower(frontRightPower);
        motorBackLeft.setPower(backLeftPower);
        motorBackRight.setPower(backRightPower);

        Thread.sleep(time);

        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
    }
}
