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
			
			Driver.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
			Driver.setInvertedMotor(RobotDrive.MotorType.kFrontRight , true);
			Driver.setInvertedMotor(RobotDrive.MotorType.kRearLeft , true);
			Driver.setInvertedMotor(RobotDrive.MotorType.kRearRight , true);
			
			Driver.setSafetyEnabled(true);
			Driver.setSensitivity(0.7);
			Driver.setMaxOutput(0.4);
		}
		else if(driveMode == DriveMode.TANKDRIVE){
		Driver.tankDrive(Robotmap.driverJoystick,Robotmap.driverJoystick);
		}
		
	}

}
