package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp Mechanum", group="Driver Controlled")
public class TeleOp_Mechanum extends OpMode {
    private float translationInputScale = 1f;
    private float rotationInputScale = 1f;

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    private DcMotor motorWinch;

    private Servo servoLeftClaw;
    private Servo servoRightClaw;
    private boolean claw;

    @Override
    public void init() {

        frontRight = hardwareMap.dcMotor.get("front right");
        frontLeft = hardwareMap.dcMotor.get("front left");
        backRight = hardwareMap.dcMotor.get("back right");
        backLeft = hardwareMap.dcMotor.get("back left");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        motorWinch = hardwareMap.dcMotor.get("winch");

        servoLeftClaw = hardwareMap.servo.get("left claw");
        servoRightClaw = hardwareMap.servo.get("right claw");

        servoLeftClaw.setPosition(0);
        servoRightClaw.setPosition(0);
        claw = false;

    }

    @Override
    public void loop() {
        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y) * translationInputScale;

        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x * rotationInputScale;

        double frontLeftSpeed = r * Math.cos(robotAngle) + rightX;
        double frontRightSpeed = r * Math.sin(robotAngle) - rightX;
        double backLeftSpeed = r * Math.sin(robotAngle) + rightX;
        double backRightSpeed = r * Math.cos(robotAngle) - rightX;

        frontLeft.setPower(frontLeftSpeed);
        frontRight.setPower(frontRightSpeed);
        backLeft.setPower(backLeftSpeed);
        backRight.setPower(backRightSpeed);

//---------------------------------------------------------------
        if(gamepad1.right_bumper) motorWinch.setPower(0.6);
        else if(gamepad1.left_bumper) motorWinch.setPower(-0.6);
        else if(gamepad1.left_bumper && gamepad1.right_bumper) motorWinch.setPower(0);
        else motorWinch.setPower(0);

//---------------------------------------------------------------
        if (gamepad1.a) claw = true;
        else if(claw) {
            if (servoLeftClaw.getPosition() == 1d) {
                servoLeftClaw.setPosition(0d);
                servoRightClaw.setPosition(0d);
            } else {
                servoLeftClaw.setPosition(1d);
                servoRightClaw.setPosition(1d);
            }

            claw = false;
        }

    }

    @Override
    public void stop() {

    }

}
