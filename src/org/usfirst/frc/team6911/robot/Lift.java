package org.usfirst.frc.team6911.robot;


public class Lift {
	private static OI liftController;
 public Lift() {
	 
	 liftController = new OI(Robotmap.liftController);
 }
 
 
 public void liftControl() {
	 
	 // Robotmap.inTakeMotor.set(liftController.getRight_Y_AXIS());
	 
	 // set limit for intake speed
	 if(liftController.getRight_Y_AXIS() <= 0.1) {
		 Robotmap.inTakeMotor.set(Math.max(-0.6,liftController.getRight_Y_AXIS()));
	 } else if(liftController.getRight_Y_AXIS() >= 0.1) {
		 Robotmap.inTakeMotor.set(Math.min(1.0,liftController.getRight_Y_AXIS()));
	 }
	 
	Robotmap.liftMotor.set(liftController.getLeft_Y_AXIS());
	 
 }
 
}
