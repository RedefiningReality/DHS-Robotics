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

    private double winchSpeed = 0.8d;
    private double extensionSpeed = 0.5d;
    private double angleSpeed = 0.3d;

    private double relicPos1 = 0d;
    private double relicPos2 = 1d;

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    private DcMotor motorAngle;

    private DcMotor motorWinch;
    private DcMotor extensionMotor;

    private Servo servoReallyLongVariableNameBecauseWhyNot;
    private boolean relic;

    private Servo servoLeftClaw;
    private Servo servoRightClaw;
    private boolean claw;

    @Override
    public void init() {

        frontRight = hardwareMap.dcMotor.get("FR");
        frontLeft = hardwareMap.dcMotor.get("FL");
        backRight = hardwareMap.dcMotor.get("BR");
        backLeft = hardwareMap.dcMotor.get("BL");

        motorAngle = hardwareMap.dcMotor.get("angle");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        motorWinch = hardwareMap.dcMotor.get("winch");
        extensionMotor = hardwareMap.dcMotor.get("extension");

        servoLeftClaw = hardwareMap.servo.get("LC");
        servoRightClaw = hardwareMap.servo.get("RC");

        servoReallyLongVariableNameBecauseWhyNot = hardwareMap.servo.get("relic");

        servoLeftClaw.setPosition(1);
        servoRightClaw.setPosition(0);
        claw = false;

        relic = false;
    }

    @Override
    public void loop() {

//--------------------------------------------------------------- Movement
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

//--------------------------------------------------------------- Winch
        if(gamepad1.right_bumper && !gamepad1.left_bumper) motorWinch.setPower(winchSpeed);
        else if(gamepad1.left_bumper) motorWinch.setPower(-winchSpeed);
        else motorWinch.setPower(0);

//--------------------------------------------------------------- Claw
        if (gamepad1.a) claw = true;
        else if(claw) {
            if (servoLeftClaw.getPosition() == 1d) {
                servoLeftClaw.setPosition(0d);
                servoRightClaw.setPosition(1d);
            } else {
                servoLeftClaw.setPosition(1d);
                servoRightClaw.setPosition(0d);
            }

            claw = false;
        }

//--------------------------------------------------------------- Extension
        if(gamepad2.right_bumper && !gamepad1.left_bumper)
            extensionMotor.setPower(extensionSpeed);
        else if(gamepad2.left_bumper)
            extensionMotor.setPower(-extensionSpeed);
        else
            extensionMotor.setPower(0d);

//--------------------------------------------------------------- Relic
        if (gamepad2.a) relic = true;
        else if(relic) {
            if (servoReallyLongVariableNameBecauseWhyNot.getPosition() == relicPos1)
                servoReallyLongVariableNameBecauseWhyNot.setPosition(relicPos2);
            else
                servoReallyLongVariableNameBecauseWhyNot.setPosition(relicPos1);

            relic = false;
        }

//--------------------------------------------------------------- Angle
        if(gamepad2.dpad_up)
            forAft = angleSpeed;
        else if(gamepad2.dpad_down)
            forAft = -angleSpeed;
        else
            forAft = gamepad2.left_stick_y;

        motorAngle.setPower(forAft);

    }

    @Override
    public void stop() {

    }

}
