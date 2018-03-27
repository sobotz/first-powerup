package org.usfirst.frc.team6911.robot;
public class Winch {
	private OI liftController;
	public Winch() {
		 liftController = new OI(Robotmap.liftController);
	}
	public void WinchControl() {
		if (liftController.getLeftTopButton() && liftController.getRightTopButton()) {
			Robotmap.winchMotor.set(0.5);
		} else if (!liftController.getLeftTopButton() || !liftController.getRightTopButton()) {
			Robotmap.winchMotor.set(0.0);
		} 
	}
}