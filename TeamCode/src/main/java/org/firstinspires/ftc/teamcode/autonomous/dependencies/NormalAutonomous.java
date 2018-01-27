package org.firstinspires.ftc.teamcode.autonomous.dependencies;

public abstract class NormalAutonomous extends Autonomous {

    protected void forwards(double speed, double inches, double timeout) throws InterruptedException {
        drive(speed, inches, inches, timeout);
    }

    protected void forwards(double speed, double inches) throws InterruptedException {
        drive(speed, inches, inches, Double.MAX_VALUE);
    }

    protected void forwards(double inches) throws InterruptedException {
        drive(DRIVE_SPEED, inches, inches, Double.MAX_VALUE);
    }

    protected void backwards(double speed, double inches, double timeout) throws InterruptedException {
        drive(speed, -inches, -inches, timeout);
    }

    protected void backwards(double speed, double inches) throws InterruptedException {
        drive(speed, -inches, -inches, Double.MAX_VALUE);
    }

    protected void backwards(double inches) throws InterruptedException {
        drive(DRIVE_SPEED, -inches, -inches, Double.MAX_VALUE);
    }

    protected void left(double speed, double inches, double timeout) throws InterruptedException {
        drive(speed, -inches, inches, timeout);
    }

    protected void left(double speed, double inches) throws InterruptedException {
        drive(speed, -inches, inches, Double.MAX_VALUE);
    }

    protected void left(double inches) throws InterruptedException {
        drive(TURN_SPEED, -inches, inches, Double.MAX_VALUE);
    }

    protected void right(double speed, double inches, double timeout) throws InterruptedException {
        drive(speed, inches, -inches, timeout);
    }

    protected void right(double speed, double inches) throws InterruptedException {
        drive(speed, inches, -inches, Double.MAX_VALUE);
    }

    protected void right(double inches) throws InterruptedException {
        drive(TURN_SPEED, inches, -inches, Double.MAX_VALUE);
    }

}