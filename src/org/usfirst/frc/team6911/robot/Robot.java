package org.usfirst.frc.team6911.robot;

import org.usfirst.frc.team6911.robot.Driver.EncodersAverage;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private Driver driver;
	private Lift lift;
	private Winch winch;
	private SendableChooser<Integer> stationChooser = new SendableChooser<>();
	private SendableChooser<String> switchORScaleChooser = new SendableChooser<>();
	private SendableChooser<Boolean> openLooporClosedloop = new SendableChooser<>();
	private SendableChooser<Double> maxSpeedChooser = new SendableChooser<>();
	private SendableChooser<Boolean> GamepadOrJoystickChooser = new SendableChooser<>();
	private int selectedStation;
	private String switchORScale;
	private Boolean  autoMethod;
	private Boolean GamepadOrJoystick;
	private Double maxSpeed;



	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		openLooporClosedloop.addDefault("Closed loop", true);
		openLooporClosedloop.addObject("Open loop", false);
		SmartDashboard.putData("Open or Closed loop", openLooporClosedloop);
		
		switchORScaleChooser.addDefault("Scale", "scale");
		switchORScaleChooser.addObject("Switch", "switch");
		SmartDashboard.putData("Switch or Scale", switchORScaleChooser);

		stationChooser.addDefault("Station 1", 1);
		stationChooser.addObject("Station 2", 2);
		stationChooser.addObject("Station 3", 3);
		SmartDashboard.putData("Select Station", stationChooser);

		GamepadOrJoystickChooser.addObject("GamePad", true);
		GamepadOrJoystickChooser.addDefault("Joystick", false);
		SmartDashboard.putData("Choose controller", GamepadOrJoystickChooser);

		
		maxSpeedChooser.addDefault("0.6", 0.8);
		maxSpeedChooser.addObject("0.8", 0.8);
		maxSpeedChooser.addObject("1", 1.0);
		SmartDashboard.putData("Set MaxSpeed", maxSpeedChooser);		
		driver = new Driver();
	lift = new Lift();
	winch = new Winch();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@SuppressWarnings("static-access")
	@Override
	public void autonomousInit() {
		selectedStation = stationChooser.getSelected();
		switchORScale = switchORScaleChooser.getSelected();
		autoMethod = openLooporClosedloop.getSelected();
		if(autoMethod) {
			driver.stabilizer();
			driver.stabilizer2();
			driver.StepsManager(switchORScale, DriverStation.getInstance().getGameSpecificMessage(),selectedStation,maxSpeedChooser.getSelected());

		}else {
			driver.startTimer();
		}
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@SuppressWarnings("static-access")
	@Override
	
	
	public void autonomousPeriodic() {

		
		if(autoMethod) {
			driver.Scheduler();
		}else {
			driver.autonomousDrive();
		}

		driver.Dashboard();
	}

	@SuppressWarnings("static-access")
	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub
		driver.stabilizer();
		GamepadOrJoystick = GamepadOrJoystickChooser.getSelected();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		maxSpeed = maxSpeedChooser.getSelected();
		driver.Drive(GamepadOrJoystick,maxSpeed);
		lift.liftControl(); // change to liftControl for lift class #1
		//winch.WinchControl();
		driver.Dashboard();
		//driver.resetYaw();
		///driver.resetencoder();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {



	}








}
