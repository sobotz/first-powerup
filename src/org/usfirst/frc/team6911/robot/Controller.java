package org.usfirst.frc.team6911.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {
/////// CLASS DESCRIPTION ////////////////

	/*
	 * MAIN PURPOSE this class acts as interface, it allow to get values from a
	 * Joystick like to determine if a button is pressed or to read an Axis value
	 * from the left or right stick
	 */

	/////////////////////////////////
	Joystick Joystick;
	/////////////////////////////////

	double left_Y_AXIS, left_X_AXIS, right_Y_AXIS, right_X_AXIS, Z_AXIS, SD;
	Boolean A, B, Y, X, leftTopButton, rightTopButton;
	double rightBackButton;
	double leftBackButton;

	/*
	 * TO create a new instance of this class it takes in parameter A Joystick
	 * Object (normally Joystick Object are declared in the Class @RobotMap)
	 */
	public Controller(Joystick joystick) {
		Joystick = joystick;	
	}

	////// Return the value of Y axis on left side of the Joystick
	public double getY_AXIS() {
		left_Y_AXIS = Joystick.getRawAxis(1);
		return left_Y_AXIS;
	}

	public double getX_AXIS() {
		left_X_AXIS = Joystick.getRawAxis(0);
		return left_X_AXIS;
	}

	public double SD() {
		SD = Joystick.getRawAxis(3);
		return SD;
	}

	
	public Boolean get1() {
		A = Joystick.getRawButton(1);
		return A;
	}

	public Boolean get2() {
		B = Joystick.getRawButton(2);
		return B;
	}

}
