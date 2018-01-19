package org.usfirst.frc.team6911.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;

public class Robotmap {

	/////// CLASS DESCRIPTION ////////////////

	/*
	 * This class is a kind of map Here we create specific object for each component
	 * of the robot %like SpeedController object=>Motor Controller, Joystick
	 * Object=>Joystick, Ultrasonic Object=> Ultrasonic sensors,
	 * ........................
	 */

	static enum DriveMode {

		ARCADE, TANKDRIVE, CURVATUREDRIVE
	}

	/*
	 * SpeedController Declaration
	 */

	static SpeedController frontLeftMotor = new Victor(4);
	static SpeedController rearLeftMotor = new Victor(3);
	static SpeedController frontRightMotor = new Victor(1);
	static SpeedController rearRightMotor = new Victor(2);
	

	//Joystick Declaration
	static Joystick driverJoystick = new Joystick(0);
	
	
	
	/////SENSORS DECLARATION ///////
	static AHRS ahrs = new AHRS(SerialPort.Port.kMXP);
	////////////static Ultrasonic ultraSonic = new Ultrasonic(1,1);
	
}
