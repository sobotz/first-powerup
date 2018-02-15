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
	private static double kP = 0.04;
	private static double kI = 0.0;
	private static double kD = 0.01;

	////////////////// ENCODERS PID Coefficients////////////
	private static PIDController EncoderPid;
	private static double ekP = 1;
	private static double ekI = 0.0;
	private static double ekD = 0.0;

	//////// PID output//////////////////
	private static double kPdeviation;//// GyroPID
	private static double kPspeed;/////// EncoderPID

	private Timer timer;
	private static Timer timers = new Timer();
	private int station;
	
	private static int mStation;

	///////////// Scheduler////////////////////////////////////
	///////////// Handle the autonomous Paths//////////////////
	private static boolean goToNextStep;
	private static boolean finalStep;
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
			Driver.setMaxOutput(0.6);
		

	}

	/////////// Timer CODE /////////////
	public void startTimer() {
		timer.start();

		Timer.delay(0.01);
	}

	public void autonomousDrive() {
		if (timer.get() > 0 && timer.get() <= 3.5)// Goes forward for 10 seconds
		{
			Driver.arcadeDrive(-0.8, 0);
		}

		if (timer.get() > 3.5 && timer.get() < 5) {
			// Goes forward for 10 seconds
			Driver.stopMotor();
		}

		if (timer.get() > 5 && timer.get() < 6) {
			Driver.arcadeDrive(0.0, -0.5);
		}

	}

	////////// PIDs Instantiation///////////
	private void GyroPID() {

		GyroPid = new PIDController(kP, kI, kD, Robotmap.ahrs, this);
		GyroPid.setInputRange(-180.0, 180.0);
		GyroPid.setOutputRange(-0.7, 0.7);
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
		EncoderPid.setOutputRange(-0.7, 0.7);
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
			
			return ((6 * 3.14) * ((Robotmap.rEncoder.get()*(-1) + Robotmap.lEncoder.get()) / 2)) / 360;
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
			Driver.arcadeDrive(-kPspeed, -kPdeviation,false);
			goToNextStep = false;
		}
	}

	///////////////////////////////////
	public Boolean liftUp() {

		if(!isRunning) {
			timers.start();
		}

		if (timers.get() < 3) {
			isRunning = true;
			Robotmap.liftMotor.set(-0.5);
			return goToNextStep = false;
		} else {
			timers.reset();
			Robotmap.liftMotor.set(0);
			return goToNextStep = true;
		}

	}

	public Boolean rollOut() {
		if(!isRunning) {
			timers.start();
		}
		
		if (timers.get() < 3) {
			isRunning = true;
			Robotmap.inTakeMotor.set(-0.3);
			return goToNextStep = false;
		} else {
			Robotmap.inTakeMotor.set(0);
			timer.stop();
			return goToNextStep = true;
		}
	}

	////////////////// Handle autonomous Paths ////////////////////////////////////

	public void StepsManager(String switchOrScale, String GD,int S) {
		
		if(switchOrScale == "switch"){
			
		gameData = GD.charAt(0);
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
		
		if(switchOrScale == "scale"){

	}
	}

	public static void StepPositionManager() {
		if (goToNextStep) {
			Steps.put(stepPosition, false);
            
			stabilizer();
			
			stepPosition = stepPosition + 1;
			
			Steps.put(stepPosition, true);
			
			if(Steps.size() == stepPosition-1) {
				finalStep = false;
			}

		}
	}

	public static void Scheduler() {

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
		stepPosition = 1;
		Timer.delay(0.04);
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
		  ((6 * 3.14) * ((Robotmap.rEncoder.get()*(-1) + Robotmap.lEncoder.get()) / 2)) / 360);

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