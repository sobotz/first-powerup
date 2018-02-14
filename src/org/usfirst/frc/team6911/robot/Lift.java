package org.usfirst.frc.team6911.robot;


public class Lift {
	private static OI liftController;
 public Lift() {
	 
	 liftController = new OI(Robotmap.liftController);
 }
 
 
 public void liftControl() {
	 
	 Robotmap.inTakeMotor.set(liftController.getRight_Y_AXIS());

	 if(liftController.getRightTopButton()) {
		 
		 Robotmap.liftMotor.set(-1);

	 }
	 else if(liftController.getLeftTopButton()) {
		 
		 Robotmap.liftMotor.set(1);

	 }
	 else if(!liftController.getLeftTopButton() & !liftController.getRightTopButton()) {
		 Robotmap.liftMotor.set(0.0);

	 }
	 
 }
 
}
