package org.usfirst.frc.team6911.robot;

public class Lift {
	private static OI liftController;
 public Lift() {
	 
	 liftController = new OI(Robotmap.liftController);
 }
 
 
 public void liftControl() {
	 
	 if(liftController.getRightTopButton() & !liftController.getLeftTopButton()) {
		 
		 Robotmap.liftMotor.set(0.5);
	 }
	 
	 else if(!liftController.getRightTopButton() & liftController.getLeftTopButton()) {
		 
		 Robotmap.liftMotor.set(0.5);

	 }
	 else {
		 
		 Robotmap.liftMotor.set(0.0);
	 }
	 
	 Robotmap.inTakeMotor.set(liftController.getRight_Y_AXIS());
     
 }
 
}
