package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.autonomous.dependencies.MechanumAutonomous;

/**
 * Created by robotics on 11/28/2017.
 */
@Autonomous(name="TestMechanumAutonomuous Four Motors", group="TestAutonomous")
public class TestMechanumAutonomous extends MechanumAutonomous {

    private ColorSensor colorSensor;
    private Servo leftClaw;
    private Servo rightClaw;

    private void initializeServosAndColourSensor(){
        colorSensor = hardwareMap.colorSensor.get("colourSensor");

        leftClaw = hardwareMap.servo.get("LC");
        rightClaw = hardwareMap.servo.get("RC");

        leftClaw.setPosition(0.43);
        rightClaw.setPosition(-1);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initializeServosAndColourSensor();
        initializeMotors();

       /* telemetry.setAutoClear(false);
        telemetry.addData("Colour R: ", colorSensor.red());
        telemetry.addData("Colour G: ", colorSensor.green());
        telemetry.addData("Colour B: ", colorSensor.blue());
        telemetry.update();
        */
       closeClaw();
       backwards(24);
    }

    private void openClaw(){
        leftClaw.setPosition(0.43);
        rightClaw.setPosition(-1);
    }

    private void closeClaw(){
        leftClaw.setPosition(0);
        rightClaw.setPosition(1);
    }

}



