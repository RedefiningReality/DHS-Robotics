package org.firstinspires.ftc.teamcode;

/* Imports are handled for you - that's the beauty of IDEs */
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Name of TeleOp (shows up on phone)", group="Group Name (sort of pointless)")
public class BasicFourMotors extends OpMode {

	/* The various motors */
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;

	/* A basic servo (so that you can see how to use it) */
    private Servo basicServo;
	
	/* Utilised for the toggle switch (see below) */
	private boolean toggleSpeed = false;
	
	/*
		I like storing speeds as global variables as it makes them easier to edit later on
		We may start using an interface in the future as Josh suggested
		I made up random values for the sake of this example, but coming up with actual values is esentially trial-and-error
	*/
	private double inputScaleFast = 0.72d;
	private double inputScaleSlow = 0.51d;
	
	/* Actual speed of robot at any given time */
	private double inputScale = inputScaleFast;

    @Override
    public void init() {
		
		// Maps motors to the hardware -> names are defined in the infamous config file
        motorFrontRight = hardwareMap.dcMotor.get("front-right motor name");
        motorFrontLeft = hardwareMap.dcMotor.get("front-left motor name");
        motorBackRight = hardwareMap.dcMotor.get("back-right motor name");
        motorBackLeft = hardwareMap.dcMotor.get("back-left motor name");

		// Maps servo to the hardware
        basicServo = hardwareMap.servo.get("servo name");
		
		// Initialises servo position to double 0
		// Position values range from 0 to 1 inclusive
		// 		180 servo: 0 => 0°, 1 => 180°
		// 		360 servo: 0 => 0°, 1 => 360°
        basicServo.setPosition(0d);

		// Sets all of the other motors on the left side to rotate backward
		// Note: Motors accross from each other are facing in opposite directions
		// 	so they must also rotate in opposite directions
		// It's a confusing thought at first - visualise it
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop() {
		
		/* Motors */
		
		// The 'right' float stores the speed of the right motor (ranging from 0 to 1)
		// The 'left' float stores the speed of the left motor (also ranging from 0 to 1)
		
		// I explained this assignment (gamepads and sticks) in more detail via the email I sent you
		// See point number 2 under the 'Overridable methods' section
        float right = gamepad1.right_stick_y;
        float left = gamepad1.left_stick_y;

		// Sets both the right and left values to be between -1 and 1
		// I have no idea why this is here -> as far as I know, the values are already between -1 and 1 (see documentation)
		// The example code I got 2ish years ago had these lines in it, so I left them just in case
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);
		
		// Sets the speed
		// Now, right and left values will range from -inputScale to inputScale rather than -1 to 1
		// If inputScale is equivalent to inputScaleFast, we have a fast speed (ranging from -0.72 to 0.72)
		// If inputScale is equivalent to inputScaleSlow, we have a slow speed (ranging from -0.51 to 0.51)
		right *= inputScale;
        left *= inputScale;
		
		// Sets the actual power of the motors to the values dictated by floats 'right' and 'left'
		motorFrontRight.setPower(right);
		motorBackRight.setPower(right);
		
        motorFrontLeft.setPower(left);
        motorBackLeft.setPower(left);
		
		/* Clickable buttons */

		// As the loop() method is continously executed, all buttons which must be PUSHED AND RELEASED in order for the action to take effect
		// (such as toggle buttons), are created using the following implementation:
		// 	if a button is pressed
		// 		set a global boolean to true
		// 	else if that boolean was previously set to true
		// 		perform the action
		// 		set the global boolean to false so that the action is not performed on next iteration of the method
		
		// If you can think of a more efficient implementation than the one above, then please do let me know
		
		// The following example makes use of the idea above to toggle between fast and slow speeds when the 'a' button is pressed
        if(gamepad1.a) {
            toggleSpeed = true;
        } else if(toggleSpeed) {
            if(inputScale == inputScaleFast)
                inputScale = inputScaleSlow;
            else
                inputScale = inputScaleFast;

			toggleSpeed = false;
        }
		
		// However, sometimes we want the rest of the loop() method to be executed and the toggle action to be performed AT THE SAME TIME
		// For this, we have to use multithreading (if you ever decide take Data Structures, you will learn about this concept if you don't already know it)
		// Do not worry at all if you do not understand how the following code works - it is just here for your reference
		// 	if (gamepad1.a) {
        // 		new Thread(new Runnable() {
        // 			@Override
        // 			public void run() {
        // 				try {
        //            		<perform action>
        // 				} catch (InterruptedException e) {
        // 					e.printStackTrace();
        // 				}
        // 			}
        // 		}).start();
		// 	}

		/* Servo */
		
		// Servo Pseudocode:
		// 	if (the up button on gamepad1 is pressed) AND (incrementing the servo position would not cause the position to be > 1)
		// 		increment the servo position
		//	else if (the down button on gamepad1 is pressed) AND (decrementing the servo position would not cause the position to be < 0)
		//		decrement the servo position
	
		// Note: Here, the increment and decrement value is 0.05 - I just thought of a random value
		//	Normally I would save that as a global variable for making easy changes to and use trial-and-error to find an adequate value
	
		// If the pseudocode makes sense to you, then cool beans
		// Else do not fret, I will explain this in more detail during robotics
        if(gamepad1.dpad_up && basicServo.getPosition() - 0.05 >= 0d)
            basicServo.setPosition(basicServo.getPosition() - 0.05);
        else if(gamepad1.dpad_down && basicServo.getPosition() + 0.05 <= 1d)
            basicServo.setPosition(basicServo.getPosition() + 0.05);
    }

}