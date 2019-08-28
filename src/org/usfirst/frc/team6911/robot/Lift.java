package org.usfirst.frc.team6911.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift {
	private static OI liftController;
	double down_speed = 0.25;
	double up_speed = -0.7;
	double speed = -0.4;
	public Lift() {
	 
	 	driveTrainEncoders();
	 	liftController = new OI(Robotmap.liftController);
	 	
	}
 
 

	public void driveTrainEncoders() {
		Robotmap.liftEncoder.setMaxPeriod(.1);
		Robotmap.liftEncoder.setMinRate(10);
		Robotmap.liftEncoder.setDistancePerPulse(1);
		Robotmap.liftEncoder.setReverseDirection(true);
		Robotmap.liftEncoder.setSamplesToAverage(7);
	}
	
	
	public void liftControl() {
	 
	 Robotmap.inTakeMotor.set(liftController.getRight_Y_AXIS()*-1);
	  
		//SmartDashboard.putNumber("Encoder output", Robotmap.liftEncoder.get());
	SmartDashboard.putBoolean("Direction", 		Robotmap.liftEncoder.getDirection());
	SmartDashboard.putBoolean("DOWN SWITCH", 		Robotmap.bottomPosition.get());
	SmartDashboard.putBoolean("UP SWITCH", 		Robotmap.topPosition.get());
			/*
	
		// Going down
		if(Robotmap.liftEncoder.getDirection()) {
			
			if(!Robotmap.bottomPosition.get()) {
				down_speed = 0.25;
			}
			
		}else {
			if(!Robotmap.bottomPosition.get()) {
				down_speed = 0.6;
			}
		}
			
			/// Going Up
			if(!Robotmap.liftEncoder.getDirection()) {
			
			if(!Robotmap.topPosition.get()) {
				up_speed = -0.25;
			}
			
		}else {
			if(!Robotmap.topPosition.get()) {
				up_speed = -0.6;
			}
		}
			*/
	
			SmartDashboard.putNumber("DOWN SPEED", down_speed);
			SmartDashboard.putNumber("UP SPEED", up_speed);
		
		if(liftController.getLeftTopButton()) {
			if(!Robotmap.topPosition.get()) {
				up_speed = -0.25;
			}
			
			if(!Robotmap.bottomPosition.get()) {
				down_speed = 0.7;
			}
			
			Robotmap.liftMotor.set(up_speed);
			
		}
		else if(liftController.getRightTopButton()){
			if(!Robotmap.topPosition.get()) {
				up_speed = -0.7;
			}
			
			if(!Robotmap.bottomPosition.get()) {
				down_speed = 0.25;
			}
			
				Robotmap.liftMotor.set(down_speed);
		}
		
		if(!liftController.getLeftTopButton() && !liftController.getRightTopButton()) {
			Robotmap.liftMotor.set(-0.15);
		}
		
	}
 
 
}

