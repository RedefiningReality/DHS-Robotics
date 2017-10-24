package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp Team 2", group="Driver Controlled")
public class TeleOp_Team2 extends OpMode {
    private float inputScale = 1f;

    private DcMotor motorRight;
    private DcMotor motorLeft;
    private DcMotor motorWinch;

    @Override
    public void init() {

        motorRight = hardwareMap.dcMotor.get("right");
        motorLeft = hardwareMap.dcMotor.get("left");
        motorWinch = hardwareMap.dcMotor.get("winch");

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
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
    }

    @Override
    public void stop() {

    }

}
