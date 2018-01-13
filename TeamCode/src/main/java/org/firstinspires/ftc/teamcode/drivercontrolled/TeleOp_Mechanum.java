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
    private double extensionSpeed = 1d;
    private double angleSpeed = 0.65d;

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

    private Servo servoLeftClaw;
    private Servo servoRightClaw;


    @Override
    public void init() {
        frontRight = hardwareMap.dcMotor.get("FR");
        frontLeft = hardwareMap.dcMotor.get("FL");
        backRight = hardwareMap.dcMotor.get("BR");
        backLeft = hardwareMap.dcMotor.get("BL");

        motorAngle = hardwareMap.dcMotor.get("angle");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
       // frontRight.setDirection(DcMotor.Direction.REVERSE);
        //backRight.setDirection(DcMotor.Direction.REVERSE);

        motorWinch = hardwareMap.dcMotor.get("winch");
        extensionMotor = hardwareMap.dcMotor.get("extension");

        servoLeftClaw = hardwareMap.servo.get("LC");
        servoRightClaw = hardwareMap.servo.get("RC");

        servoReallyLongVariableNameBecauseWhyNot = hardwareMap.servo.get("relic");

        servoLeftClaw.setPosition(.8);
        servoRightClaw.setPosition(.3);
    }

    @Override
    public void loop() {

//--------------------------------------------------------------- Movement
        double r = Math.hypot(gamepad1.right_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.right_stick_x) - Math.PI / 4;
        double rightX = gamepad1.left_stick_x;
        final double v1 = r * Math.sin(robotAngle) - rightX;
        final double v2 = r * Math.cos(robotAngle) + rightX;
        final double v3 = r * Math.cos(robotAngle) - rightX;
        final double v4 = r * Math.sin(robotAngle) + rightX;

        if(gamepad1.right_bumper) {
            frontLeft.setPower(v1*0.25);
            frontRight.setPower(v2*0.25);
            backLeft.setPower(v3*0.25);
            backRight.setPower(v4*0.25);
        }else if(gamepad1.left_bumper){
            frontLeft.setPower(v1*0.5);
            frontRight.setPower(v2*0.5);
            backLeft.setPower(v3*0.5);
            backRight.setPower(v4*0.5);
        } else {
            frontLeft.setPower(v1);
            frontRight.setPower(v2);
            backLeft.setPower(v3);
            backRight.setPower(v4);
        }

//--------------------------------------------------------------- Winch
        if(gamepad2.left_trigger > 0){
            motorWinch.setPower(gamepad2.left_stick_y*0.2);
        motorAngle.setPower(gamepad2.right_stick_y*angleSpeed);}
        else
            motorWinch.setPower(gamepad2.left_stick_y*0.55);
        motorAngle.setPower(gamepad2.right_stick_y*0.45);

//--------------------------------------------------------------- Claw
        if(gamepad2.b) { //Closed
            servoLeftClaw.setPosition(.4);
            servoRightClaw.setPosition(.7);
        }
        if(gamepad2.y) { //Half Open
            servoLeftClaw.setPosition(.5);
            servoRightClaw.setPosition(.6);
        }

        if(gamepad2.a) { //Full Open
            servoLeftClaw.setPosition(0.8);
            servoRightClaw.setPosition(.3);
        }

//--------------------------------------------------------------- Extension
        if(gamepad2.right_bumper && !gamepad2.left_bumper)
            extensionMotor.setPower(extensionSpeed);
        else if(gamepad2.left_bumper)
            extensionMotor.setPower(-extensionSpeed);
        else
            extensionMotor.setPower(0d);

//--------------------------------------------------------------- Relic
        if (gamepad1.a) servoReallyLongVariableNameBecauseWhyNot.setPosition(0);
        if (gamepad1.b) servoReallyLongVariableNameBecauseWhyNot.setPosition(1);


//--------------------------------------------------------------- Angle

    }

    @Override
    public void stop() {

    }

}
