package org.usfirst.frc.team6911.robot;

import org.usfirst.frc.team6911.robot.Robotmap.DriveMode;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

public class Driver implements PIDOutput {

	/////// CLASS DESCRIPTION ///////////////
	/*
	 * This class handle the robot driving Specifically the function "Drive"
	 */

	/////////////DRIVETRAIN INSTANCIATION/////////////////
	private static DriveMode driveMode;
	private static OI driverJoystick;
	private DifferentialDrive Driver;
	private SpeedControllerGroup m_left;
	private SpeedControllerGroup m_right;

	///////////////////////////////////////////////////////
	//													//
	//					 	PID						    \\														
	//													//
	////////////////////PID INSTANCIATION /////////////////
    
	//////////////////GYRO PID Coefficients////////////////
	private PIDController GyroPid;
	private double kP = 0.04;
	private double kI = 0.0;
	private double kD = 0.0;
	
	//////////////////ENCODERS PID Coefficients////////////
    private PIDController EncoderPid;
    private double ekP = 0.0;
	private double ekI = 0.0;
	private double ekD = 0.0;
    
	//////// PID output//////////////////
	private double kPdeviation;////GyroPID
	private double kPspeed;///////EncoderPID
	
	//*******************************************************//

	/*
	 * To create an instance of this class, it takes An Enumeration Object in
	 * parameters, Specifically a Drivemode Enumeration Object in @Robotmap class
	 * that allow to specify the way the robot going to be driving
	 * (ARCADEDRIVE,TANKDRIVE,CURVATUREDRIVE. see the online doc for more
	 * explanations about the "drive mode".
	 * https://wpilib.screenstepslive.com/s/currentCS/m/java/l/599700-getting-your-
	 * robot-to-drive-with-the-robotdrive-class
	 */
	public Driver(DriveMode mdrivemode) {

		driveMode = mdrivemode;
		m_left = new SpeedControllerGroup(Robotmap.frontLeftMotor, Robotmap.rearLeftMotor);
		m_right = new SpeedControllerGroup(Robotmap.frontRightMotor, Robotmap.rearRightMotor);

		driverJoystick = new OI(Robotmap.driverJoystick);

		m_left.setInverted(true);
		m_right.setInverted(true);

		Driver = new DifferentialDrive(m_left, m_right);

		GyroPID();

	}
    
	
	////This function allows to drive in teleop MODE//////////////
	public void Drive() {
		if (driverJoystick.getLeft_Y_AXIS() <= 0.1 && driverJoystick.getLeft_Y_AXIS() >= -0.1) {
			//SmartDashboard.putBoolean("Moving", false);
			// Robotmap.ahrs.zeroYaw();
		} else {
			//SmartDashboard.putBoolean("Moving", true);
		}

		if (driveMode == DriveMode.ARCADE) {
			Driver.arcadeDrive(driverJoystick.getLeft_Y_AXIS(), -driverJoystick.getRight_X_AXIS());
			Driver.setMaxOutput(0.8);
		} else if (driveMode == DriveMode.CURVATUREDRIVE) {
			Driver.curvatureDrive(driverJoystick.getLeft_Y_AXIS(), -driverJoystick.getRight_X_AXIS(), false);
			Driver.setMaxOutput(0.7);
			///SImple TEST
			
			GyroPid.enable();

		}

		else if (driveMode == DriveMode.TANKDRIVE) {
			Driver.tankDrive(driverJoystick.getLeft_Y_AXIS(), driverJoystick.getRight_Y_AXIS(), true);
			Driver.setSafetyEnabled(true);
			Driver.setMaxOutput(0.5);
		}

	}
    
	
	///////////Main function for autonomous Mode/////////////
	public void autonomousDrive(double speed, Boolean rangeFinder) {
		if (!GyroPid.isEnabled()) {
			GyroPid.enable();
		}
		
		if(!rangeFinder) {
			Driver.arcadeDrive(speed, -kPdeviation);
		}
		
		////////////////Test function/////////////
		if(rangeFinder) {
			if(Robotmap.ultraSonic.getRangeInches() > 20) {
				Driver.arcadeDrive(speed, -kPdeviation);
			}
			else {
				Driver.stopMotor();
			}
			
		}
		// drive.stopMotor();
	}

	
	public void encoders() {
		
		/////Pulse per Revolution////
		double PPR = 1440;
		/////Cycle per Revolution/////
		double CPR = 360;
		
		//////Distance per Pulse/////
		double DPP = PPR/CPR;

		Robotmap.lEncoder.setMaxPeriod(.1);
		Robotmap.lEncoder.setMinRate(10);
		Robotmap.lEncoder.setDistancePerPulse(DPP);
		Robotmap.lEncoder.setReverseDirection(true);
		Robotmap.lEncoder.setSamplesToAverage(7);
		SmartDashboard.putNumber("Left Encoder", Robotmap.lEncoder.getDistance());

		Robotmap.rEncoder.setMaxPeriod(.1);
		Robotmap.rEncoder.setMinRate(10);
		Robotmap.rEncoder.setDistancePerPulse(DPP);
		Robotmap.rEncoder.setReverseDirection(true);
		Robotmap.rEncoder.setSamplesToAverage(7);
		SmartDashboard.putNumber("Right Encoder", Robotmap.rEncoder.getDistance() * (-1));

	}
	
	////////////This function handle the range finder////////////////////////////
	public void rangeFinder() {
		Robotmap.ultraSonic.setAutomaticMode(true);
		Robotmap.ultraSonic.setEnabled(true);
		SmartDashboard.putNumber("Range", Robotmap.ultraSonic.getRangeInches());
	}
	
    
	
	//////////PIDs Instanciation///////////
	private void GyroPID() {

		GyroPid = new PIDController(kP, kI, kD, Robotmap.ahrs, this);
		GyroPid.setInputRange(-180.0f, 180.0f);
		GyroPid.setOutputRange(-7.0, 7.0);
		GyroPid.setPercentTolerance(98.5);
		GyroPid.setContinuous(true);
	}
	
	private void EncoderPID() {
		///GyroPid = new PIDController(kP, kI, kD, Robotmap.ahrs, this);
	}
	
	//////////////////////////////////////
	public void RotatetoAngle(double angle) {
		if(!GyroPid.onTarget()) {
			stabilizer();
			Driver.arcadeDrive(0, -kPdeviation);
			GyroPid.setSetpoint(angle);
			GyroPid.enable();
		}
	}
	
	public void DriveTo(double distance) {
		Driver.arcadeDrive(-kPspeed, -kPdeviation);
	}
	
	///////// This function act like a stabilizer it reset everything, and allow the
	///////// PID to be balanced properly///////////////
	public void stabilizer() {
		GyroPid.disable();
		GyroPid.reset();
		Robotmap.ahrs.zeroYaw();
		GyroPid.setSetpoint(0);
		Timer.delay(0.3);
	}

	///////// This function is used to reset the Gyro angle/////////////
	public void resetYaw() {
		if (driverJoystick.getA()) {
			Robotmap.ahrs.zeroYaw();
		}
	}

	////// This function is used to reset the encoders///////////
	public void resetencoder() {
		if (driverJoystick.getB()) {
			Robotmap.lEncoder.reset();
			Robotmap.rEncoder.reset();
		}

	}
    
	
	/////////This function is used to put value to the ShuffleBoard/////////////
	public void Dashboard() {
		// SmartDashboard.putNumber("Left Y AXIS", driverJoystick.getLeft_Y_AXIS());
		// SmartDashboard.putNumber("Right X AXIS", driverJoystick.getRight_X_AXIS());
		SmartDashboard.putNumber("YAW angle", Robotmap.ahrs.getYaw());
		SmartDashboard.putBoolean("isConneted   ", Robotmap.ahrs.isConnected());
		SmartDashboard.putBoolean("isMoving   ", Robotmap.ahrs.isMoving());
		SmartDashboard.putBoolean("isRotating   ", Robotmap.ahrs.isRotating());
		
		SmartDashboard.putNumber("PkP", kP);
		SmartDashboard.putNumber("PkI", kI);
		SmartDashboard.putNumber("PkD", kD);
		
		SmartDashboard.putNumber("kP", Table.getNumber("kP", 0));
		SmartDashboard.putNumber("kI", Table.getNumber("kI", 0));
		SmartDashboard.putNumber("kD", Table.getNumber("kD", 0));
	}

	/////////////// This function is used in test mode to Tune the PID
	/////////////// coefficient/////////////////
	public void GyroPIDsetCoefficient() {
		kP = Table.getNumber("kP", 0);
		kI = Table.getNumber("kI", 0);
		kD = Table.getNumber("kD", 0);
		GyroPid.setPID(kP, kI, kD);
	}

	//// Getting the PID output for The GyroPID
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		kPdeviation = output;
		SmartDashboard.putNumber("PID Output", output);

	}

}
