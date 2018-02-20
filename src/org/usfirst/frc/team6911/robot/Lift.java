package org.usfirst.frc.team6911.robot;


public class Lift {
	private static OI liftController;
 public Lift() {
	 
	 liftController = new OI(Robotmap.liftController);
 }
 
 
 public void liftControl() {
	 
	 Robotmap.inTakeMotor.set(liftController.getRight_Y_AXIS());
	Robotmap.liftMotor.set(liftController.getLeft_Y_AXIS());
	 
 }
 
}
