package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.autonomous.dependencies.NormalAutonomous;

/**
 * Created by robotics on 11/7/2017.
 */
@Autonomous(name="TestAutonomous Four Motors", group="TestAutonomous")
public class TestAutonomous extends NormalAutonomous {

    ColorSensor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {

        colorSensor = hardwareMap.colorSensor.get("colorSensor");
        initializeMotors();

        forwards(2);
        backwards(2);
        left(2);
        right(2);
    }

}
