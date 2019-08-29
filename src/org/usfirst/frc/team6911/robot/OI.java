package org.usfirst.frc.team6911.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;

public class OI {

	/////// CLASS DESCRIPTION ////////////////

	/*
	 * MAIN PURPOSE this class acts as interface, it allow to get values from a
	 * Joystick like to determine if a button is pressed or to read an Axis value
	 * from the left or right stick
	 */

	/////////////////////////////////
	Joystick Joystick;
	/////////////////////////////////

	double left_Y_AXIS, left_X_AXIS, right_Y_AXIS, right_X_AXIS, Z_AXIS;
	Boolean A, B, Y, X, leftTopButton, rightTopButton;
	double rightBackButton;
	double leftBackButton;

	/*
	 * TO create a new instance of this class it takes in parameter A Joystick
	 * Object (normally Joystick Object are declared in the Class @RobotMap)
	 */
	public OI(Joystick joystick) {
		Joystick = joystick;
	}

	////// Return the value of Y axis on left side of the Joystick
	public double getLeft_Y_AXIS() {
		left_Y_AXIS = Joystick.getRawAxis(1);
		return left_Y_AXIS;
	}

	public double getLeft_X_AXIS() {
		left_X_AXIS = Joystick.getRawAxis(0);
		return left_X_AXIS;
	}

	public double getRight_Y_AXIS() {
		right_Y_AXIS = Joystick.getRawAxis(5);
		return right_Y_AXIS;
	}

	public double getRight_X_AXIS() {
		right_X_AXIS = Joystick.getRawAxis(4);
		return right_X_AXIS;
	}

	public double getZ_AXIS() {
		Z_AXIS = Joystick.getDirectionDegrees();
		return Z_AXIS;
	}

	public Boolean getA() {
		A = Joystick.getRawButton(1);
		return A;
	}

	public Boolean getB() {
		B = Joystick.getRawButton(2);
		return B;
	}

	public Boolean getY() {
		Y = Joystick.getRawButton(4);
		return Y;
	}

	public Boolean getX() {
		X = Joystick.getRawButton(3);
		return X;
	}

	public Boolean getLeftTopButton() {
		leftTopButton = Joystick.getRawButton(5);
		return leftTopButton;
	}

	public Boolean getRightTopButton() {
		rightTopButton = Joystick.getRawButton(6);
		return rightTopButton;
	}

	public double getLtrigger() {
		leftBackButton = Joystick.getRawAxis(2);
		return leftBackButton;
	}

	public double getRtrigger() {
		rightBackButton = Joystick.getRawAxis(3);
		return rightBackButton;
	}

	public boolean getPOV(int pov) {
		boolean b = false;
		if (Joystick.getPOV() == pov) {
			b = true;
		}
		return b;
	}

}
