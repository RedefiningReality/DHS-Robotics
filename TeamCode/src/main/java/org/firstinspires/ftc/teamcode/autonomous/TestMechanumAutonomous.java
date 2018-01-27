package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ThreadPool;

import org.firstinspires.ftc.robotcore.internal.android.dx.rop.cst.CstAnnotation;
import org.firstinspires.ftc.robotcore.internal.system.SystemProperties;
import org.firstinspires.ftc.teamcode.autonomous.dependencies.MechanumAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.dependencies.TBAutonomous;
import org.firstinspires.ftc.teamcode.autonomous.dependencies.TBMechanumAutonomous;

import static android.R.string.yes;

/**
 * Created by robotics on 11/28/2017.*/
@Autonomous(name="TestMechanumAutonomuous Four Motors", group="TestAutonomous")
public class TestMechanumAutonomous extends TBMechanumAutonomous {

    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;

    private DcMotor winch;

    private ColorSensor colorSensor;

    private Servo clawServo;
    private Servo leftClaw;
    private Servo rightClaw;

    private boolean swap = true;

    private void initializeServos() throws InterruptedException {
        leftClaw = hardwareMap.servo.get("LC");
        rightClaw = hardwareMap.servo.get("RC");
        clawServo = hardwareMap.servo.get("CS");

        openClaw();
    }

    private void initializeColorSensor() { colorSensor = hardwareMap.colorSensor.get("ColorSensor"); }

    private void initializeWinch() {
        winch = hardwareMap.dcMotor.get("winch");
    }

    public void runOpMode() throws InterruptedException {
        initializeWinch();
        initializeColorSensor();
        initializeServos();
        initializeMotors();

        closeClaw();
        raiseWinch(500);

        clawServo.setPosition(1);

        while (swap){
            colorSensor.enableLed(false);

            if(colorSensor.red() > 30 && colorSensor.blue() < 25){
                rotateLeft(700);
                clawServo.setPosition(0);
                rotateLeft(1000);
                backwards(1000);
                rotateLeft(4);
                forwards(500);

                swap = false;
            }
            else if(colorSensor.blue() > 30 && colorSensor.red() < 25){
                rotateRight(500);
                clawServo.setPosition(0);

                swap = false;
            }
         }

        Thread.sleep(2000);
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



