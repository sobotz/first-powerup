package org.usfirst.frc.team6911.robot;
public class Winch {
	public Winch() {
		Robotmap.winchMotor.set(0.0);
	}
	public void WinchControl() {
		if (liftController.getLeftTopButton() & liftController.getRightTopButton()) {
			Robotmap.winchMotor.set(0.5);
		} else if (liftController.getLeftTopButton() & !liftController.getRightTopButton()) {
			Robotmap.winchMotor.set(0.0);
		} else if (!liftController.getLeftTopButton() & liftController.getRightTopButton()) {
			Robotmap.winchMotor.set(0.0);
		} else {
			Robotmap.winchMotor.set(0.0);
		}
	}
}
