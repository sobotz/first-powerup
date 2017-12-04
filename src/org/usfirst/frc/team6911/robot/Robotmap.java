package org.usfirst.frc.team6911.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class Robotmap {
	
	
	static enum DriveMode{
		
		ARCADE,
		TANKDRIVE
	}
	
	

	//SpeedController Declaration
	
	static SpeedController frontLeftMotor = new Victor(0);
	static SpeedController rearLeftMotor = new Victor(0);
	static SpeedController frontRightMotor = new Victor(0);
	static SpeedController rearRightMotor = new Victor(0);
	
	//Joy stick Declaration
	
	
	static Joystick driverJoystick = new Joystick(0);

}
