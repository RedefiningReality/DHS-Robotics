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
    private boolean claw;

    @Override
    public void init() {

        motorRight = hardwareMap.dcMotor.get("right");
        motorLeft = hardwareMap.dcMotor.get("left");

        motorWinch = hardwareMap.dcMotor.get("winch");

        servoLeftClaw = hardwareMap.servo.get("leftClaw");
        servoRightClaw = hardwareMap.servo.get("rightClaw");

        motorRight.setDirection(DcMotor.Direction.REVERSE);

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
