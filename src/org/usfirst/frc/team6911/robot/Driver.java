package org.usfirst.frc.team6911.robot;

import java.util.HashMap;

import org.usfirst.frc.team6911.robot.Robotmap.DriveMode;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public final class Driver implements PIDOutput {

	///////////// DRIVETRAIN INSTANCIATION/////////////////
	private static OI driverJoystick;
	private static DifferentialDrive Driver;
	private static SpeedControllerGroup m_left;
	private static SpeedControllerGroup m_right;

	////////////////////////////////////////////////////////

	//////////////////// PID INSTANCIATION /////////////////

	////////////////// GYRO PID Coefficients////////////////
	private static PIDController GyroPid;
	private static double kP = 0.07;
	private static double kI = 0.0;
	private static double kD = 0.02;
	private static boolean teleopStraight = false;
	private static boolean stabilizerinit = false;

	////////////////// ENCODERS PID Coefficients////////////
	private static PIDController EncoderPid;
	private static double ekP = 0.06;
	private static double ekI = 0.0;
	private static double ekD = 0.03;

	//////// PID output//////////////////
	private static double kPdeviation;//// GyroPID
	private static double kPspeed;/////// EncoderPID

	private Timer timer;
	private static Timer timers = new Timer();
	private static Timer timerss = new Timer();
	private int station;

	private static int mStation;
	private static String switchOrScale;

	///////////// Scheduler////////////////////////////////////
	///////////// Handle the autonomous Paths//////////////////
	private static boolean goToNextStep;
	private static boolean finalStep = false;
	private static int stepPosition;
	private static boolean isRunning = false;
	private static char gameData;

	private static HashMap<Integer, Boolean> Steps = new HashMap<Integer, Boolean>();

	// *******************************************************//

	/*
	 * To create an instance of this class, it takes An Enumeration Object in
	 * parameter, Specifically a Drivemode Enumeration Object in @Robotmap class
	 * that allow to specify the way the robot going to be driving
	 * (ARCADEDRIVE,TANKDRIVE,CURVATUREDRIVE. see the online doc for more
	 * explanations about the "drive mode".
	 * https://wpilib.screenstepslive.com/s/currentCS/m/java/l/599700-getting-your-
	 * robot-to-drive-with-the-robotdrive-class
	 */
	public Driver() {

		m_left = new SpeedControllerGroup(Robotmap.frontLeftMotor, Robotmap.rearLeftMotor);
		m_right = new SpeedControllerGroup(Robotmap.frontRightMotor, Robotmap.rearRightMotor);

		driverJoystick = new OI(Robotmap.driverJoystick);

		m_left.setInverted(true);
		m_right.setInverted(true);

		Driver = new DifferentialDrive(m_left, m_right);

		///////// Initialization/////////////////
		driveTrainEncoders();

		GyroPID();

		EncoderPID();

		stepPosition = 1;

		timer = new Timer();

	}

	//// This function allows to drive in teleop MODE//////////////
	public void Drive() {
        
		
		Driver.arcadeDrive(driverJoystick.getLeft_Y_AXIS(), -driverJoystick.getRight_X_AXIS(), false);
		Driver.setMaxOutput(0.6); // set to 0.6
		
		if(driverJoystick.getRightTopButton()) {
			 if(!teleopStraight) {
				 GyroPid.setSetpoint(0.0f);
				 GyroPid.enable();
			 }
			 teleopStraight = true;
			 stabilizerinit = true;
			Driver.arcadeDrive(driverJoystick.getLeft_Y_AXIS(), -kPdeviation, false);

		}
		
		if(!driverJoystick.getRightTopButton()) {
			if(stabilizerinit) {
			 if(teleopStraight) {
				 stabilizer();
				 teleopStraight = false;
				 stabilizerinit = false;
			 }
			}
		}

	}

	public void startTimer() {
		timer.start();
		Timer.delay(0.01); 
		}
	
	 public void autonomousDrive() { 
		 if(timer.get() > 0 & timer.get() < .6){		    //turns counterclockwise at 60% speed for .6 seconds(45 degree turn)
				Driver.tankDrive(-0.6,0.6);
			}
			if(timer.get()>.6 && timer.get()< 4)
				Driver.stopMotor();
	 }
		  /*
		 if(timer.get() > 0 & timer.get() < 1){		    //turns counterclockwise at 60% speed for 1 seconds(90 degree turn)
				Driver.tankDrive(-0.6,0.6);
			}
			if(timer.get()>1 && timer.get()< 4)
				Driver.stopMotor();
	 }
		 /*
		 	///////////Test 1///////////
		 if(timer.get()>0 && timer.get() < 5){	     		//goes at 60% speed for 5 second(13ft)(2.6ftpersec)(31.2"persec  )
				Driver.arcadeDrive(-0.6, 0);
			}
			if(timer.get()>5 && timer.get()< 7)
				Driver.stopMotor();
	 }
	 /* ///////////Simple Straight and Lift(15
	 * seconds)/////////// if(timer.get()>0 && timer.get()<=7){ //Goes forward for
	 * 10 seconds Driver.arcadeDrive(0.3,0); }
	 * 
	 * if(timer.get()>7 && timer.get<=10){ Driver.stopMotor(); //Stops driving }
	 * 
	 * if(timer.get()>10 && timer.get()<=12){ Robotmap.liftMotor.set(0.5); //lift
	 * goes up }
	 * 
	 * if(timer.get()>12 && timer.get()<=13){ Robotmap.liftMotor.set(0); //Stop the
	 * lift motor }
	 * 
	 * if(timer.get()>13 && timer.get()<=14){ Robotmap.inTakeA.set(-1); //Starts
	 * intake motor-launches power cube }
	 * 
	 * if(timer.get()>14 && timer.get()<=15){ Robotmap.inTakeA.set(0); //Stop the
	 * intake motor }
	 * 
	 * ///////////DRIVERSTATION 1/////////// if(timer.get()>0 && timer.get() <= 1){
	 * //.428575(s)- .5(s) interval Driver.arcadeDrive(0.3, 0); } if(timer.get()>1
	 * && timer.get()<=1.75) Driver.stopMotor(); if(timer.get() > 1.75 &&
	 * timer.get() <= 2.75){ //.5(s)- .5(s) interval Driver.tankDrive(-0.3,0.3); }
	 * if(timer.get()>2.75 && timer.get()<=3.5) Driver.stopMotor(); if(timer.get() >
	 * 3.5 && timer.get() <= 6.5){ //.85714(s)- .5(s) interval
	 * Driver.arcadeDrive(0.3, 0); } if(timer.get()>6.5 && timer.get() <= 7.25)
	 * Driver.stopMotor(); if(timer.get() > 7.25 && timer.get() <= 8.25){ //.5(s)-
	 * .5(s) interval Driver.tankDrive(0.3,-0.3); } if(timer.get()>8.25 &&
	 * timer.get()<=9) Driver.stopMotor(); if(timer.get() > 9 && timer.get() <= 11){
	 * //5.0(s)- .5(s) interval Driver.arcadeDrive(0.3, 0); } if(timer.get()>11 &&
	 * timer.get() <= 11.75) Driver.stopMotor(); if(timer.get() > 11.75 &&
	 * timer.get() <= 12.5){ //.5(s)- no interval Driver.tankDrive(-0.3,0.3); }
	 * if(timer.get()>12.5 && timer.get() <= 13) Driver.stopMotor();
	 * if(timer.get()>13 && timer.get() <= 13.5) Robotmap.liftMotor.set(0.5); //lift
	 * goes up if(timer.get()>13.5 && timer.get() <= 14.5) Robotmap.inTakeA.set(-1);
	 * //Starts intake motor-launches power cube if(timer.get()>14.5 && timer.get()
	 * <= 15) Robotmap.inTakeA.set(0);
	 * 
	 * ///////////DRIVERSTATION 2/////////// //All it does is cross the line
	 * if(timer.get()>0 && timer.get()<=7){ //Goes forward for 10 seconds
	 * Driver.arcadeDrive(0.3,0); }
	 * 
	 * if(timer.get()>7 && timer.get<=10){ Driver.stopMotor(); //Stops driving }
	 * 
	 * ///////////DRIVERSTATION 3/////////// if(timer.get()>0 && timer.get() <= 1)
	 * //.428575(s)- .5(s) interval Driver.arcadeDrive(0.3, 0);
	 * 
	 * if(timer.get()>1 && timer.get()<=1.75) Driver.stopMotor();
	 * 
	 * if(timer.get() > 1.75 && timer.get() <= 2.75) //.5(s)- .5(s) interval
	 * Driver.tankDrive(0.3,-0.3);
	 * 
	 * if(timer.get()>2.75 && timer.get()<=3.5) Driver.stopMotor();
	 * 
	 * if(timer.get() > 3.5 && timer.get() <= 6.5) //.85714(s)- .5(s) interval
	 * Driver.arcadeDrive(0.3, 0);
	 * 
	 * if(timer.get()>6.5 && timer.get() <= 7.25) Driver.stopMotor();
	 * 
	 * if(timer.get() > 7.25 && timer.get() <= 8.25) //.5(s)- .5(s) interval
	 * Driver.tankDrive(-0.3,0.3);
	 * 
	 * if(timer.get()>8.25 && timer.get()<=9) Driver.stopMotor();
	 * 
	 * if(timer.get() > 9 && timer.get() <= 11) //5.0(s)- .5(s) interval
	 * Driver.arcadeDrive(0.3, 0);
	 * 
	 * if(timer.get()>11 && timer.get() <= 11.75) Driver.stopMotor();
	 * 
	 * if(timer.get() > 11.75 && timer.get() <= 12.5) //.5(s)- no interval
	 * Driver.tankDrive(0.3,-0.3);
	 * 
	 * if(timer.get()>12.5 && timer.get() <= 13) Driver.stopMotor();
	 * 
	 * if(timer.get()>13 && timer.get() <= 13.5) Robotmap.liftMotor.set(0.5); //lift
	 * goes up
	 * 
	 * if(timer.get()>13.5 && timer.get() <= 14.5) Robotmap.inTakeA.set(-1);
	 * //Starts intake motor-launches power cube
	 * 
	 * if(timer.get()>14.5 && timer.get() <= 15) Robotmap.inTakeA.set(0); //Stops
	 *intake motor 
	 */ 
	////////// PIDs Instantiation///////////
	private void GyroPID() {

		GyroPid = new PIDController(kP, kI, kD, Robotmap.ahrs, this);
		GyroPid.setInputRange(-180.0, 180.0);
		GyroPid.setOutputRange(-0.9, 0.9);
		GyroPid.setAbsoluteTolerance(3.0f);
		GyroPid.setContinuous(true);
	}

	//// Getting the PID output for The GyroPID
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		kPdeviation = output;
		SmartDashboard.putNumber("Gyro PID Output", output);

	}
	//////////////////////////////////////////////////////////////////////////////

	////////////////////// ENCODER PID STUFF//////////////////////////////////////
	///////////////////////////////////////////////////////// ////////////////////
	private void EncoderPID() {
		EncoderPid = new PIDController(ekP, ekI, ekD, new EncodersAverage(), new EncoderPIDOutput());
		EncoderPid.setInputRange(-5000, 5000);
		EncoderPid.setOutputRange(-0.9, 0.9);
		EncoderPid.setAbsoluteTolerance(6.0f);
		EncoderPid.setContinuous(true);
	}

	/////////// Inside Class ///////////////////////////////////////////////////
	public class EncoderPIDOutput implements PIDOutput {

		@Override
		public void pidWrite(double output) {
			// TODO Auto-generated method stub

			kPspeed = output;

			SmartDashboard.putNumber("Encoder PID output", output);
		}

		// return (Robotmap.rEncoder.getDistance() + Robotmap.lEncoder.getDistance())/2;

	}

	public class EncodersAverage implements PIDSource {
		PIDSourceType p_source;

		public EncodersAverage() {
			p_source = PIDSourceType.kDisplacement;

		}

		@Override
		public double pidGet() {
			// TODO Auto-generated method stub

			return ((6 * 3.14) * ((Robotmap.rEncoder.get() * (-1) + Robotmap.lEncoder.get()) / 2)) / 360;
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			// TODO Auto-generated method stub
			p_source = pidSource;
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			// TODO Auto-generated method stub
			return p_source;
		}

	}

	/////////////////////////////////////////////////

	//////////// This function handle the range finder///////////////////////////
	/*
	 * public void rangeFinder() { Robotmap.ultraSonic.setAutomaticMode(true);
	 * Robotmap.ultraSonic.setEnabled(true); SmartDashboard.putNumber("RangeFinder",
	 * Robotmap.ultraSonic.getRangeInches()); }
	 */

	/////////// Set up the encoders ////////////////////////////////////////////
	public void driveTrainEncoders() {

		//////////////// Encoders///////////////////////////////////////////////////
		///// Pulse per Revolution////
		double PPR = 1440;
		///// Cycle per Revolution/////
		double CPR = 360;
		////// Distance per Pulse/////
		double DPP = PPR / CPR;

		Robotmap.lEncoder.setMaxPeriod(.1);
		Robotmap.lEncoder.setMinRate(10);
		Robotmap.lEncoder.setDistancePerPulse(DPP);
		Robotmap.lEncoder.setReverseDirection(true);
		Robotmap.lEncoder.setSamplesToAverage(7);

		Robotmap.rEncoder.setMaxPeriod(.1);
		Robotmap.rEncoder.setMinRate(10);
		Robotmap.rEncoder.setDistancePerPulse(DPP);
		Robotmap.rEncoder.setReverseDirection(true);
		Robotmap.rEncoder.setSamplesToAverage(7);

	}

	/////////////// Autonomous Command////////////////////////////////////////////

	/*
	 * During autonomous for any paths we choose we will have to repeat some typical
	 * action like Rotate To a certain angle drive to a certain distance .......
	 * That the purpose of the following method (RotateTo();, DriveTo();)
	 */

	// Calling this method will make the robot to turn the angle specified in
	// parameter
	// In order to turn the we use a PID loop, we don't apply a constant speed to
	// turn the robot, the PID controller
	// would apply a constant speed to turn the robot until the gyro states that the
	// angle has be changed to the angle wanted

	public static Boolean RotateTo(double angle) {
		GyroPid.setSetpoint(angle);
		GyroPid.enable();
		if (GyroPid.onTarget()) {
			return goToNextStep = true;
		} else {
			Driver.arcadeDrive(0.0, -kPdeviation, false);
			return goToNextStep = false;

		}

	}

	// Calling this function will make the robot to drive a certain distance
	// we don't use a constant speed, cause if we did we would overshoot (we could
	// miss the set point) the target.
	// then we use A PID loop that will apply a proportional speed until the set
	// point is reached in this case
	// until the robot travels the distance wanted
	public static void DriveTo(double distance) {
		EncoderPid.setSetpoint(distance);
		GyroPid.setSetpoint(0.0f);

		EncoderPid.enable();
		GyroPid.enable();

		if (EncoderPid.onTarget()) {
			goToNextStep = true;
		} else {
			Driver.arcadeDrive(-kPspeed, -kPdeviation, false);
			goToNextStep = false;
		}
	}

	///////////////////////////////////
	public static Boolean liftUp() {

		if (!isRunning) {
			timers.start();
		}

		if (timers.get() < 7) {
			isRunning = true;
			Robotmap.liftMotor.set(-0.6);
			return goToNextStep = false;
		} else {
			timers.reset();
			Robotmap.liftMotor.set(0);
			return goToNextStep = true;
		}

	}

	public static Boolean rollOut() {
		if (!isRunning) {
			timerss.start();
		}

		if (timerss.get() < 3) {
			isRunning = true;
			Robotmap.inTakeMotor.set(-1);
			return goToNextStep = false;
		} else {
			Robotmap.inTakeMotor.set(0);
			timerss.reset();
			return goToNextStep = true;
		}
	}

	////////////////// Handle autonomous Paths ////////////////////////////////////

	public void StepsManager(String switchorscale, String GD, int S) {

		switchOrScale = switchorscale;
		if (switchOrScale == "switch") {

			gameData = GD.charAt(0);
			mStation = S;
			if (mStation == 1) {

				if (gameData == 'L') {
					Steps.put(1, true);
					Steps.put(2, false);
					Steps.put(3, false);
					Steps.put(4, false);
					Steps.put(5, false);
					Steps.put(6, false);
					Steps.put(7, false);
					Steps.put(8, false);
					Steps.put(9, false);
					Steps.put(10 , false);


				} else if (gameData == 'R') {
					Steps.put(1, true);
					Steps.put(2, false);
					Steps.put(3, false);
					Steps.put(4, false);
				}
			}

			if (mStation == 2) {
				if (gameData == 'L') {
					Steps.put(1, true);
					Steps.put(2, false);
					Steps.put(3, false);
				} else if (gameData == 'R') {

					Steps.put(1, true);
					Steps.put(2, false);
					Steps.put(3, false);
					Steps.put(4, false);
				}

			}

			if (mStation == 3) {
				if (gameData == 'L') {
					Steps.put(1, true);
					Steps.put(2, false);
					Steps.put(3, false);
				} else if (gameData == 'R') {

					Steps.put(1, true);
					Steps.put(2, false);
					Steps.put(3, false);
					Steps.put(4, false);
				}
			}
		}

		if (switchOrScale == "scale") {

			gameData = GD.charAt(1);
			mStation = S;
			if (mStation == 1) {

				if (gameData == 'L') {
					Steps.put(1, true);
					Steps.put(2, false);
				} else if (gameData == 'R') {
					Steps.put(1, true);
					Steps.put(2, false);
					Steps.put(3, false);
					Steps.put(4, false);
				}
			}

			if (mStation == 2) {
				if (gameData == 'L') {
					Steps.put(1, true);
					Steps.put(2, false);
					Steps.put(3, false);
				} else if (gameData == 'R') {

					Steps.put(1, true);
					Steps.put(2, false);
					Steps.put(3, false);
					Steps.put(4, false);
				}

			}

			if (mStation == 3) {
				if (gameData == 'L') {
					Steps.put(1, true);
					Steps.put(2, false);
					Steps.put(3, false);
				} else if (gameData == 'R') {

					Steps.put(1, true);
					Steps.put(2, false);
					Steps.put(3, false);
					Steps.put(4, false);
				}
			}
		}
	}

	public static void StepPositionManager() {
		if (goToNextStep) {
			Steps.put(stepPosition, false);

			stabilizer();

			stepPosition = stepPosition + 1;

			Steps.put(stepPosition, true);

			if (Steps.size() == stepPosition - 1) {
				finalStep = true;
			}

		}
	}

	public static void Scheduler() {
 if(switchOrScale == "switch") {
	 
		if (mStation == 1) {

			if (gameData == 'L') {

				if (Steps.get(1) && !finalStep) {
					DriveTo(30);
				}

				if (Steps.get(2) && !finalStep) {
					RotateTo(-45.0f);
				}
				
				if (Steps.get(3) && !finalStep) {
					DriveTo(48);
				}
				
				if (Steps.get(4) && !finalStep) {
					RotateTo(45.0f);
				}
				
				if (Steps.get(5) && !finalStep) {
					DriveTo(112);
				}
				
				if (Steps.get(6) && !finalStep) {
					RotateTo(90.0f);
				}
				
				if (Steps.get(7) && !finalStep) {
					DriveTo(25.0f);
				}
				
				if (Steps.get(8) && !finalStep) {
					liftUp();
				}
				
				if (Steps.get(9) && !finalStep) {
					DriveTo(16.0f);
				}
				
				if (Steps.get(10) && !finalStep) {
					rollOut();
				}
				
				

			}

			else if (gameData == 'R') {

			}
		}

		if (mStation == 2) {

		}

		if (mStation == 3) {

		}
 }
 
 if(switchOrScale == "scale") {
	 
		if (mStation == 1) {

			if (gameData == 'L') {

				if (Steps.get(1) && !finalStep) {
					DriveTo(120);
				}

				if (Steps.get(2) && !finalStep) {
					RotateTo(45.0f);
				}

			}

			else if (gameData == 'R') {

			}
		}

		if (mStation == 2) {

		}

		if (mStation == 3) {

		}
}

		StepPositionManager();
	}

	///////// This function act like a stabilizer it reset everything, and allow the
	///////// PID to be balanced properly///////////////
	public static void stabilizer() {

		GyroPid.disable();
		GyroPid.reset();
		Robotmap.ahrs.zeroYaw();
		GyroPid.setSetpoint(0.0f);

		Robotmap.lEncoder.reset();
		Robotmap.rEncoder.reset();
		EncoderPid.disable();
		EncoderPid.reset();
		EncoderPid.setSetpoint(0.0f);

		isRunning = false;
		Timer.delay(0.03);
	}
	
	public static void stabilizer2() {
		
		stepPosition = 1;
		finalStep = false;
		
	}

	///////// This function is used to reset the Gyro angle/////////////
	public void resetYaw() {
		if (driverJoystick.getA()) {
			GyroPid.disable();
			GyroPid.reset();
			Robotmap.ahrs.zeroYaw();
			Timer.delay(0.01);
		}
	}

	////// This function is used to reset the encoders///////////
	public void resetencoder() {
		if (driverJoystick.getB()) {
			Robotmap.lEncoder.reset();
			Robotmap.rEncoder.reset();
		}

	}

	///////// This function is used to put value to the ShuffleBoard/////////////
	public void Dashboard() {
		// SmartDashboard.putNumber("Left Y AXIS", driverJoystick.getLeft_Y_AXIS());
		// SmartDashboard.putNumber("Right X AXIS", driverJoystick.getRight_X_AXIS());
		SmartDashboard.putNumber("YAW angle", Robotmap.ahrs.getYaw());
		SmartDashboard.putBoolean("isConneted   ", Robotmap.ahrs.isConnected());
		SmartDashboard.putBoolean("isMoving   ", Robotmap.ahrs.isMoving());
		SmartDashboard.putBoolean("isRotating   ", Robotmap.ahrs.isRotating());
		SmartDashboard.putBoolean("onTarget   ", goToNextStep);

		SmartDashboard.putNumber("Left encoder", Robotmap.lEncoder.get());
		SmartDashboard.putNumber("Right encoder", Robotmap.rEncoder.get() * (-1));

		SmartDashboard.putData("GYRO PID", GyroPid);
		SmartDashboard.putData("Encoder PID", EncoderPid);

		SmartDashboard.putNumber("Encoders Average",
				((6 * 3.14) * ((Robotmap.rEncoder.get() * (-1) + Robotmap.lEncoder.get()) / 2)) / 360);

	}

	/////////////// Those functions are used in test mode to Tune the PID
	/////////////// coefficient/////////////////
	public static void PIDsetCoefficient() {
		kP = Table.getNumber("kP", 0);
		kI = Table.getNumber("kI", 0);
		kD = Table.getNumber("kD", 0);
		GyroPid.setPID(kP, kI, kD);

		ekP = Table.getNumber("ekP", 0);
		ekI = Table.getNumber("ekI", 0);
		ekD = Table.getNumber("ekD", 0);
		EncoderPid.setPID(ekP, ekI, ekD);
	}

	///////// Disable the motors ////////////////////////////////////////
	public void disablemotor() {

		Driver.stopMotor();

		Robotmap.liftMotor.set(0);
	}
}