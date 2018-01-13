package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.autonomous.dependencies.MechanumAutonomous;

/**
 * Created by robotics on 11/28/2017.
 */
@Autonomous(name="TestMechanumAutonomuous Four Motors", group="TestAutonomous")
public class TestMechanumAutonomous extends MechanumAutonomous {

    private ColorSensor colorSensor;
    private Servo leftClaw;
    private Servo rightClaw;

    private DcMotor winch;

    private void initializeServos() throws InterruptedException {
        leftClaw = hardwareMap.servo.get("LC");
        rightClaw = hardwareMap.servo.get("RC");

        openClaw();
    }

    private void initializeWinch() {
        winch = hardwareMap.dcMotor.get("winch");
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initializeWinch();
        initializeServos();
        initializeMotors();

        closeClaw();
        raiseWinch(500);

   //     backwards(24);
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



