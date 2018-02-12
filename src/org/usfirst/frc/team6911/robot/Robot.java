/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6911.robot;

import org.usfirst.frc.team6911.robot.Robotmap.DriveMode;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {

	  private Driver driver;
	  private Lift lift;
	private SendableChooser<Integer> stationChooser = new SendableChooser<>();
	private SendableChooser<String> allianceChooser = new SendableChooser<>();
	private int selectedStation;
	private String selectedAlliance;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		driver = new Driver(DriveMode.ARCADE);
		lift = new Lift();
		
		allianceChooser.addObject("RED Alliance", "Red");
		allianceChooser.addObject("BLUE Alliance", "Blue");
		SmartDashboard.putData("Set alliance", stationChooser);

		

		stationChooser.addDefault("Station 1", 1);
		stationChooser.addObject("Station 2", 2);
		stationChooser.addObject("Station 3", 3);
		SmartDashboard.putData("Select Station", stationChooser);

		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(640, 480);


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
		selectedAlliance = allianceChooser.getSelected();
		driver.StepsManager(selectedAlliance,selectedStation);
		driver.stabilizer();
        
		//driver.startTimer();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@SuppressWarnings("static-access")
	@Override
	public void autonomousPeriodic() {
	driver.DriveTo(120);
		
		//driver.autonomousDrive();
		//driver.Scheduler(selectedAlliance,selectedStation);


		driver.Dashboard();
	}

	@SuppressWarnings("static-access")
	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub
		driver.stabilizer();

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		driver.Drive();
		lift.liftControl();
		driver.Dashboard();
		driver.resetYaw();
		driver.resetencoder();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {



	}

	@Override
	public void disabledInit() {
		// TODO Auto-generated method stub
driver.disablemotor();
	}








}
