package org.usfirst.frc.team6911.robot;

import org.usfirst.frc.team6911.robot.Robotmap.DriveMode;

import edu.wpi.first.wpilibj.RobotDrive;

public class Driver {
	
	
	private DriveMode driveMode;
	private RobotDrive Driver;
	
	
	public Driver(DriveMode mdrivemode) {
		  driveMode = mdrivemode;
		    
		  
		  
		  Driver = new RobotDrive (Robotmap.frontLeftMotor, Robotmap.rearLeftMotor, Robotmap.frontRightMotor, Robotmap.rearRightMotor);
	    
	}
	
	
	
	public void Drive() {
		
		if(driveMode == DriveMode.ARCADE) {
			
			Driver.arcadeDrive(Robotmap.driverJoystick);
		}
		else {
			
			//Driver.tankDrive(Robotmap.driverJoystick1,Robotmap.driverJoystick2);

		}
		
	}

}
