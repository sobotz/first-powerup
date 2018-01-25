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

	static enum DriveMode {
		ARCADE, TANKDRIVE, CURVATUREDRIVE
	}

    public enum PinType { DigitalIO, PWM, AnalogIn, AnalogOut };

	/*
	 * SpeedController Declaration
	 */

	public static SpeedController frontLeftMotor = new Spark(4);
	public static SpeedController rearLeftMotor = new Spark(3);
	public static SpeedController frontRightMotor = new Spark(1);
	public static SpeedController rearRightMotor = new Spark(2);
	
	
	//public DigitalInput dig_in_6  = new DigitalInput(  getChannelFromPin( PinType.DigitalIO, 6 ));
   // public DigitalOutput dig_out_5 = new DigitalOutput( getChannelFromPin( PinType.DigitalIO, 5 ));
	
	/*public static SpeedController frontLeftMotor = new Victor(4);
	public static SpeedController rearLeftMotor = new Victor(3);
	public static SpeedController frontRightMotor = new Victor(1);
	public static SpeedController rearRightMotor = new Victor(2);
	*/
	public static Encoder lEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X); ///Not sure that works
	public static Encoder rEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X); ///Not sure that works
	

	//Joystick Declaration
	static Joystick driverJoystick = new Joystick(0);
	
	
	// declare sensors
	static AHRS ahrs = new AHRS(SPI.Port.kMXP);
	static Ultrasonic ultraSonic = new Ultrasonic(Navx_extension.getPortextension(0),Navx_extension.getPortextension(9));
	
}
