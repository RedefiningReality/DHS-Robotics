package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.dependencies.MechanumAutonomous;

/**
 * Created by robotics on 11/28/2017.*/
@Autonomous(name="TestMechanumAutonomuous Four Motors", group="TestAutonomous")

public class TestMechanumAutonomous extends LinearOpMode {

    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;

    private ColorSensor colorSensor;
    private Servo leftClaw;
    private Servo rightClaw;

    private DcMotor winch;

    private void initializeServos() throws InterruptedException {
        leftClaw = hardwareMap.servo.get("LC");
        rightClaw = hardwareMap.servo.get("RC");

        openClaw();
    }

    protected void initializeMotors() {
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

    private void initializeWinch() {
        winch = hardwareMap.dcMotor.get("winch");
    }

    public void runOpMode() throws InterruptedException {
        initializeWinch();
        initializeServos();
        initializeMotors();

        closeClaw();
        raiseWinch(500);

        forwards(1000);
    }

    protected void forwards(double time) throws InterruptedException {
        forwards(0.2, time);
    }

    protected void forwards(double speed, double time) throws InterruptedException {
        motorBackLeft.setPower(speed);
        motorBackRight.setPower(speed);
        motorFrontLeft.setPower(speed);
        motorFrontRight.setPower(speed);

        Thread.sleep((long)time);

        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
    }

    private void openClaw() throws InterruptedException {
        leftClaw.setPosition(.8);
        rightClaw.setPosition(.3);

        Thread.sleep(1000);
    }

    private void closeClaw() throws InterruptedException {
        leftClaw.setPosition(.4);
        rightClaw.setPosition(.7);

        Thread.sleep(1000);
    }

    private void raiseWinch(int time) throws InterruptedException {
        moveWinch(time);
    }

    private void raiseWinch(double speed, int time) throws InterruptedException {
        moveWinch(speed, time);
    }

    private void lowerWinch(int time) throws InterruptedException {
        moveWinch(-2.0, time);
    }

    private void lowerWinch(double speed, int time) throws InterruptedException {
        moveWinch(-speed, time);
    }

    private void moveWinch(int time) throws InterruptedException {
        winch.setPower(0.2d);
        Thread.sleep(time);
        winch.setPower(0d);
    }

    private void moveWinch(double speed, int time) throws InterruptedException {
        winch.setPower(speed);
        Thread.sleep(time);
        winch.setPower(0d);
    }

}



