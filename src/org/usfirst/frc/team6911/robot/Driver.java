package org.usfirst.frc.team6911.robot;

import org.usfirst.frc.team6911.robot.Robotmap.DriveMode;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Driver {

	private DriveMode driveMode;
	private DifferentialDrive Driver;
	private OI driverJoystick;

	public Driver(DriveMode mdrivemode) {

		driveMode = mdrivemode;
		SpeedControllerGroup m_left = new SpeedControllerGroup(Robotmap.frontLeftMotor, Robotmap.rearLeftMotor);
		SpeedControllerGroup m_right = new SpeedControllerGroup(Robotmap.frontRightMotor, Robotmap.frontRightMotor);
		
		m_left.setInverted(true);
		m_right.setInverted(true);
		driverJoystick = new OI(Robotmap.driverJoystick);

		Driver = new DifferentialDrive(m_left, m_right);

		driverJoystick = new OI(Robotmap.driverJoystick);

	}

	public void Drive() {
		if (driveMode == DriveMode.ARCADE) {
			Driver.arcadeDrive(driverJoystick.getLeft_Y_AXIS(), driverJoystick.getRight_X_AXIS());
			Driver.setSafetyEnabled(true);
			Driver.setMaxOutput(0.5);
		} else if (driveMode == DriveMode.CURVATUREDRIVE) {
			Driver.curvatureDrive(driverJoystick.getLeft_Y_AXIS(), driverJoystick.getRight_X_AXIS(), true);
			Driver.setSafetyEnabled(true);
			Driver.setMaxOutput(0.5);
		}
	}
		
		public void OIDebugging() {
			driverJoystick = new OI(Robotmap.driverJoystick);
			
			
			driverJoystick.getLeft_X_AXIS();
			

			

	}

}
