package org.firstinspires.ftc.teamcode.drivercontrolled;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp Mechanum", group="Driver Controlled")
public class TeleOp_Mechanum extends OpMode {
    private double translationInputScale = 1d;
    private double rotationInputScale = 1d;

    private double extensionSpeed = 0.5d;

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    private DcMotor motorWinch;
    private DcMotor extensionMotor;

    private Servo servoLeftClaw;
    private Servo servoRightClaw;
    private boolean claw;

    @Override
    public void init() {

        frontRight = hardwareMap.dcMotor.get("FR");
        frontLeft = hardwareMap.dcMotor.get("FL");
        backRight = hardwareMap.dcMotor.get("BR");
        backLeft = hardwareMap.dcMotor.get("BL");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        motorWinch = hardwareMap.dcMotor.get("winch");
        extensionMotor = hardwareMap.dcMotor.get("extension");

        servoLeftClaw = hardwareMap.servo.get("LC");
        servoRightClaw = hardwareMap.servo.get("RC");

        servoLeftClaw.setPosition(1);
        servoRightClaw.setPosition(0);
        claw = false;

    }

    @Override
    public void loop() {
        double forAft;
        if(gamepad1.dpad_up)
            forAft = 1d;
        else if(gamepad1.dpad_down)
            forAft = -1d;
        else
            forAft = gamepad1.left_stick_y;

        double leftRight;
        if(gamepad1.dpad_right)
            leftRight = 1d;
        else if(gamepad1.dpad_left)
            leftRight = -1d;
        else
            leftRight = gamepad1.left_stick_x;


        double r = Math.hypot(leftRight, forAft) * translationInputScale;
        double robotAngle = Math.atan2(forAft, leftRight) - Math.PI / 4;

        double rightX = gamepad1.right_stick_x * rotationInputScale;

        double frontRightSpeed = r * Math.cos(robotAngle) + rightX;
        double frontLeftSpeed = r * Math.sin(robotAngle) - rightX;
        double backRightSpeed = r * Math.sin(robotAngle) + rightX;
        double backLeftSpeed = r * Math.cos(robotAngle) - rightX;

        frontLeft.setPower(frontLeftSpeed);
        frontRight.setPower(frontRightSpeed);
        backLeft.setPower(backLeftSpeed);
        backRight.setPower(backRightSpeed);

//---------------------------------------------------------------
        if(gamepad1.right_bumper) motorWinch.setPower(0.8);
        else if(gamepad1.left_bumper) motorWinch.setPower(-0.8);
        else if(gamepad1.left_bumper && gamepad1.right_bumper) motorWinch.setPower(0);
        else motorWinch.setPower(0);

//---------------------------------------------------------------
        if (gamepad1.a) claw = true;
        else if(claw) {
            if (servoLeftClaw.getPosition() == 1d) {
                servoLeftClaw.setPosition(1d);
                servoRightClaw.setPosition(0d);
            } else {
                servoLeftClaw.setPosition(0d);
                servoRightClaw.setPosition(1d);
            }

            claw = false;
        }

//---------------------------------------------------------------
        if(gamepad1.left_bumper)
            extensionMotor.setPower(-extensionSpeed);
        else if(gamepad1.right_bumper)
            extensionMotor.setPower(extensionSpeed);
        else
            extensionMotor.setPower(0d);

    }

    @Override
    public void stop() {

    }

}
