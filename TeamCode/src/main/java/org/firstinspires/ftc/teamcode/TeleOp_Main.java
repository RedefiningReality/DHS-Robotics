package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp Main", group="Driver Controlled")
public class TeleOp_Main extends OpMode {
    private float inputScale = 1f;

    private DcMotor motorRight;
    private DcMotor motorLeft;
    private DcMotor motorWinch;
    private Servo servoLeftClaw;
    private Servo servoRightClaw;
    private boolean claw; //true = on false = off

    @Override
    public void init() {

        motorRight = hardwareMap.dcMotor.get("right");
        motorLeft = hardwareMap.dcMotor.get("left");
        motorWinch = hardwareMap.dcMotor.get("winch");
        servoLeftClaw = hardwareMap.servo.get("leftClaw");
        servoRightClaw = hardwareMap.servo.get("rightClaw");


        motorRight.setDirection(DcMotor.Direction.REVERSE);

        //The values for the servos are not CURRENTLY acurate and may be subject to change when testing the robot
        servoLeftClaw.setPosition(0);
        servoRightClaw.setPosition(0);
        claw = false;

    }

    @Override
    public void loop() {
        float right = gamepad1.right_stick_y;
        float left = gamepad1.left_stick_y;

        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        right *= inputScale;
        left *= inputScale;

        motorRight.setPower(right);
        motorLeft.setPower(left);

//---------------------------------------------------------------
        if(gamepad1.right_bumper) motorWinch.setPower(0.6);
        else if(gamepad1.left_bumper) motorWinch.setPower(-0.6);
        else if(gamepad1.left_bumper && gamepad1.right_bumper) motorWinch.setPower(0);
        else motorWinch.setPower(0);

//---------------------------------------------------------------
        //The values for the servos are not CURRENTLY accurate and may be subject to change when testing the robot
        if (gamepad1.a && !claw){
            servoRightClaw.setPosition(90);
            servoLeftClaw.setPosition(90);
            claw = !claw;
        } else if (gamepad1.a && claw){
            servoLeftClaw.setPosition(0);
            servoRightClaw.setPosition(0);
            claw = !claw;
        }
//---------------------------------------------------------------

    }

    @Override
    public void stop() {

    }

}
