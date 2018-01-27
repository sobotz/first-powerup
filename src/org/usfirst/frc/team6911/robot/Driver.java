package org.usfirst.frc.team6911.robot;

import org.usfirst.frc.team6911.robot.Robotmap.DriveMode;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;


public class Driver implements PIDOutput {
	
	/////// CLASS DESCRIPTION ///////////////
	/*
	 * This class handle the robot driving
	 * Specifically the function "Drive" 
	 */
    
	
	// Look for differential drive documentation 
	
	private static DriveMode driveMode;
	private static OI driverJoystick;
	private DifferentialDrive Driver;
	private SpeedControllerGroup m_left;
	private SpeedControllerGroup m_right;
	//Spark motorFL = new Spark(0); // front left controller
	//Spark motorFR = new Spark(1); // front right controller
	//Spark motorBL = new Spark(2); // back left controller
	//Spark motorBR = new Spark(3); // back right controller
	
	
	
	
	
	
	
	
	
	/////////////PID///////////////////////////
	PIDController GyroPid;
	private double kP = 0.04;
	private double kI = 0.01;
	private double kD = 0.1;
	private double kF = 0.0;
	
	private double kPdeviation;
	


		
    /*
     * To create an instance of this class, it takes An Enumeration Object in parameters, Specifically a Drivemode Enumeration Object in @Robotmap
     * class
     * that allow to specify the way the robot going to be driving (ARCADEDRIVE,TANKDRIVE,CURVATUREDRIVE.
     * see the online doc for more explanations about the "drive mode".
     * https://wpilib.screenstepslive.com/s/currentCS/m/java/l/599700-getting-your-robot-to-drive-with-the-robotdrive-class 
     */
	public Driver(DriveMode mdrivemode) {
		
		driveMode = mdrivemode;
		m_left = new SpeedControllerGroup(Robotmap.frontLeftMotor, Robotmap.rearLeftMotor);
		m_right = new SpeedControllerGroup(Robotmap.frontRightMotor, Robotmap.rearRightMotor);
		

		driverJoystick = new OI(Robotmap.driverJoystick);
		
		m_left.setInverted(true);
		m_right.setInverted(true);
		

		Driver = new DifferentialDrive(m_left, m_right);
		
		PIDDRIVE();

	}
	public void Drive() {	
		if(driverJoystick.getLeft_Y_AXIS() <= 0.1 && driverJoystick.getLeft_Y_AXIS() >= -0.1) {
			SmartDashboard.putBoolean("Moving",false);
          //  Robotmap.ahrs.zeroYaw();
		}
		else {
			SmartDashboard.putBoolean("Moving",true);
		}
		
		if (driveMode == DriveMode.ARCADE) {
			Driver.arcadeDrive(driverJoystick.getLeft_Y_AXIS(), -driverJoystick.getRight_X_AXIS());
			Driver.setMaxOutput(0.8);
		} else if (driveMode == DriveMode.CURVATUREDRIVE) {
			Driver.curvatureDrive(driverJoystick.getLeft_Y_AXIS(), -driverJoystick.getRight_X_AXIS(), true);
			Driver.setMaxOutput(0.7);

		}
		
		 else if (driveMode == DriveMode.TANKDRIVE) {
				Driver.tankDrive(driverJoystick.getLeft_Y_AXIS(), driverJoystick.getRight_Y_AXIS(), true);
				Driver.setSafetyEnabled(true);
				Driver.setMaxOutput(0.5);
			}
			
	}
	
	
	
	public void autonomousDrive(double speed) {
		GyroPid.enable();
		Driver.arcadeDrive(speed, -kPdeviation);
		//drive.stopMotor();
	}

	
	public void encoders() {
		
		Robotmap.lEncoder.setMaxPeriod(.1);
		Robotmap.lEncoder.setMinRate(10);
		Robotmap.lEncoder.setDistancePerPulse(7);
		Robotmap.lEncoder.setReverseDirection(true);
		Robotmap.lEncoder.setSamplesToAverage(7);
		SmartDashboard.putNumber("Left Encoder", Robotmap.lEncoder.getDistance());
		
		Robotmap.rEncoder.setMaxPeriod(.1);
		Robotmap.rEncoder.setMinRate(10);
		Robotmap.rEncoder.setDistancePerPulse(7);
		Robotmap.rEncoder.setReverseDirection(true);
		Robotmap.rEncoder.setSamplesToAverage(7);
		SmartDashboard.putNumber("Right Encoder", Robotmap.rEncoder.getDistance()*(-1));
		
		
		Robotmap.ultraSonic.setAutomaticMode(true);
		Robotmap.ultraSonic.setEnabled(true);
		SmartDashboard.putNumber("Range", Robotmap.ultraSonic.getRangeInches());

		//Robotmap.lEncoder.reset();

	}
	
	
	private void PIDDRIVE() {
	     GyroPid = new PIDController(kP, kI, kD, kF, Robotmap.ahrs, this);
	     GyroPid.setInputRange(-180.0f,  180.0f);
	     GyroPid.setOutputRange(-7.0, 7.0);
	     GyroPid.setAbsoluteTolerance(5.0);
	     GyroPid.setContinuous(true);
	     GyroPid.setSetpoint(0.0);

	}

	
	
	public void resetYaw() {
		
		if(driverJoystick.getA()) {
		Robotmap.ahrs.zeroYaw();
		//SmartDashboard.putNumber("Trigger", 8886868);

		}
	}
	
	
	public void resetencoder() {
		if(driverJoystick.getB()) {
			
			Robotmap.lEncoder.reset();
			Robotmap.rEncoder.reset();

			// Robotmap.rEncoder.reset(); // right encoder
		}
		
	}
		
	
		public void OIDebugging() {
			//System.out.println("L X AXIS :"+driverJoystick.getLeft_X_AXIS());
			//System.out.println("L Y AXIS :"+driverJoystick.getLeft_Y_AXIS());
			//System.out.println("R Y AXIS :"+driverJoystick.getRight_Y_AXIS());
			//SmartDashboard.putString("DISTANCE FROM THE NEAREST OBSTACLE :",  Robotmap.ultraSonic.getRangeInches() +" Inches");
			
			SmartDashboard.putNumber("Left Y AXIS", driverJoystick.getLeft_Y_AXIS());
			SmartDashboard.putNumber("Right X AXIS", driverJoystick.getRight_X_AXIS());
			SmartDashboard.putNumber("YAW  ", Robotmap.ahrs.getYaw());
			SmartDashboard.putBoolean("isConneted   ", Robotmap.ahrs.isConnected());
	}
		
		
		
		///////////////This function is can only be used in test mode/////////////////
		public void GyroPIDtest()
		
		
		
		{
			  
			Ntables ntable = new Ntables();
			
			  kP = ntable.GyroPIDGains("kP");
			  kI = ntable.GyroPIDGains("kI");
			  kD = ntable.GyroPIDGains("kD");
			  kF = ntable.GyroPIDGains("kF");
			
			SmartDashboard.putNumber("kP", kP);
			SmartDashboard.putNumber("kI", kI);
			SmartDashboard.putNumber("kD", kD);
				
			///this.autonomousDrive(-0.6);

			
		}		
		////Getting the PID output value
		@Override
		public void pidWrite(double output) {
			// TODO Auto-generated method stub
			kPdeviation = output;
			SmartDashboard.putNumber("PID value  ", output);

		}

}
