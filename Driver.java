package org.usfirst.frc.team6911.robot;


import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

public class Driver {
	private String driveMode;
	private RobotDrive Driver;
	public Driver(String mdrivemode) {
		  driveMode = mdrivemode;
		  Driver = new RobotDrive(Robotmap.frontLeftMotor, Robotmap.rearLeftMotor, Robotmap.frontRightMotor, Robotmap.rearRightMotor);
	}
	public void Drive() {
		if(driveMode == "arcade") {
			Driver.arcadeDrive(Robotmap.driverJoystick);
			
			Driver.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
			Driver.setInvertedMotor(RobotDrive.MotorType.kFrontRight , true);
			Driver.setInvertedMotor(RobotDrive.MotorType.kRearLeft , true);
			Driver.setInvertedMotor(RobotDrive.MotorType.kRearRight , true);
			
			Driver.setSafetyEnabled(true);
			Driver.setSensitivity(0.1);
			Driver.setMaxOutput(0.4);
		}
		else {
			//Driver.tankDrive(Robotmap.driverJoystick1,Robotmap.driverJoystick2);
		}
		
	}

}
