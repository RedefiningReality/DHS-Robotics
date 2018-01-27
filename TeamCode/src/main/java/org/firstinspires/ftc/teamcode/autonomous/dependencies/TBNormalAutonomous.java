package org.firstinspires.ftc.teamcode.autonomous.dependencies;

/**
 * Created by robotics on 1/27/2018.
 */

public abstract class TBNormalAutonomous extends TBAutonomous {
    protected void forwards(double power, long time) throws InterruptedException {
        drive(power, power, time);
    }

    protected void forwards(long time) throws InterruptedException {
        drive(DRIVE_SPEED, DRIVE_SPEED, time);
    }

    protected void backwards(double power, long time) throws InterruptedException {
        drive(-power, -power, time);
    }

    protected void backwards(long time) throws InterruptedException {
        drive(DRIVE_SPEED, DRIVE_SPEED, time);
    }

    protected void left(double power, long time) throws InterruptedException {
        drive(-power, power, time);
    }

    protected void right(double power, long time) throws InterruptedException {
        drive(power, -power, time);
    }

    protected void right(long time) throws InterruptedException {
        drive(TURN_SPEED, -TURN_SPEED, time);
    }
}
