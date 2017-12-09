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
    private double angleSpeed = 0.5d;

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

        //frontLeft.setDirection(DcMotor.Direction.REVERSE);
        //backLeft.setDirection(DcMotor.Direction.REVERSE);
       // frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        motorWinch = hardwareMap.dcMotor.get("winch");
        extensionMotor = hardwareMap.dcMotor.get("extension");

        servoLeftClaw = hardwareMap.servo.get("LC");
        servoRightClaw = hardwareMap.servo.get("RC");

        servoReallyLongVariableNameBecauseWhyNot = hardwareMap.servo.get("relic");

        servoLeftClaw.setPosition(0.43);
        servoRightClaw.setPosition(-1);





    }

    @Override
    public void loop() {

//--------------------------------------------------------------- Movement
        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;
        final double v1 = r * Math.sin(robotAngle) - rightX;
        final double v2 = r * Math.cos(robotAngle) + rightX;
        final double v3 = r * Math.cos(robotAngle) - rightX;
        final double v4 = r * Math.sin(robotAngle) + rightX;

        frontLeft.setPower(v1);
        frontRight.setPower(v2);
        backLeft.setPower(v3);
        backRight.setPower(v4);

//--------------------------------------------------------------- Winch
        if(gamepad1.right_bumper && !gamepad1.left_bumper) motorWinch.setPower(winchSpeed);
        else if(gamepad1.left_bumper) motorWinch.setPower(-winchSpeed);
        else motorWinch.setPower(0);

//--------------------------------------------------------------- Claw
        if(gamepad1.a){
            servoLeftClaw.setPosition(0);
            servoRightClaw.setPosition(1);
        }

        if(gamepad1.b){
            servoLeftClaw.setPosition(0.43);
            servoRightClaw.setPosition(-1);
        }

//--------------------------------------------------------------- Extension
        if(gamepad2.right_bumper && !gamepad2.left_bumper)
            extensionMotor.setPower(extensionSpeed);
        else if(gamepad2.left_bumper)
            extensionMotor.setPower(-extensionSpeed);
        else
            extensionMotor.setPower(0d);

//--------------------------------------------------------------- Relic
        if (gamepad2.a) servoReallyLongVariableNameBecauseWhyNot.setPosition(1);
        if (gamepad2.b) servoReallyLongVariableNameBecauseWhyNot.setPosition(0);


//--------------------------------------------------------------- Angle

        motorAngle.setPower(gamepad2.left_stick_y*angleSpeed);

    }

    @Override
    public void stop() {

    }

}
