package org.usfirst.frc.team6911.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
//import edu.wpi.first.wpilibj.Victor;

public class Robotmap {

	/////// CLASS DESCRIPTION ////////////////

	/*
	 * This class is a kind of map Here we create specific object for each component
	 * of the robot %like SpeedController object=>Motor Controller, Joystick
	 * Object=>Joystick, Ultrasonic Object=> Ultrasonic sensors,
	 * ........................
	 */
	/*
	 * SpeedController Declaration
	 */

    /*
	public static SpeedController frontLeftMotor = new Spark(4);
	public static SpeedController rearLeftMotor = new Spark(3);
	public static SpeedController frontRightMotor = new Spark(1);
	public static SpeedController rearRightMotor = new Spark(2);
	*/
	
	
	public static SpeedController frontLeftMotor = new Spark(4); // SPARK
	public static SpeedController rearLeftMotor = new Spark(3); // SPARK
	public static SpeedController frontRightMotor = new Spark(1); // SPARK
	public static SpeedController rearRightMotor = new Spark(2); // SPARK
	
	
	
	///////////////////////LIFT/////////////////////////////////////
	
	public static SpeedController liftMotor = new Spark(5);
    
	////////////////////// Intake //////////////////////////////////////
	
	public static SpeedController inTakeMotor = new Spark(6);
	
	//////////////////// Winch ///////////////////////////////////////
	
	//public static SpeedController winchMotor = new Spark(7);
	
	//Joystick Declaration
	static Joystick driverJoystick = new Joystick(0);
	static Joystick liftController = new Joystick(1);
	//static Joystick winchController = new Joystick(2);
	
	// declare sensors
	static AHRS ahrs = new AHRS(SPI.Port.kMXP);
	//static Ultrasonic ultraSonic = new Ultrasonic(Navx_extension.getPortextension(0),Navx_extension.getPortextension(9));
	
	public static Encoder lEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X); ///Not sure that works
	public static Encoder rEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X); ///Not sure that works
	public static Encoder liftEncoder = new Encoder(4, 5, false, Encoder.EncodingType.k4X);
	
	public static DigitalInput bottomPosition = new DigitalInput(6);
	public static DigitalInput topPosition = new DigitalInput(7);
}
