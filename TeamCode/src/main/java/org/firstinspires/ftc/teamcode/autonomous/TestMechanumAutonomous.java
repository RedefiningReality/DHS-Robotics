package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.autonomous.dependencies.MechanumAutonomous;

/**
 * Created by robotics on 11/28/2017.
 */
@Autonomous(name="TestMechanumAutonomuous Four Motors", group="TestAutonomous")
public class TestMechanumAutonomous extends MechanumAutonomous {

    ColorSensor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        colorSensor = hardwareMap.colorSensor.get("colourSensor");
        initializeMotors();

        telemetry.setAutoClear(false);
        telemetry.addData("Colour R: ", colorSensor.red());
        telemetry.addData("Colour G: ", colorSensor.green());
        telemetry.addData("Colour B: ", colorSensor.blue());
        telemetry.update();

        forwards(2);
        backwards(2);
        left(2);
        right(2);
        rotateRight(2);
        rotateLeft(2);
    }

}
