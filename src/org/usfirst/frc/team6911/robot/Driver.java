package org.usfirst.frc.team6911.robot;

import org.usfirst.frc.team6911.robot.Robotmap.DriveMode;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Driver {
	
	/////// CLASS DESCRIPTION ///////////////
	/*
	 * This class handle the robot driving
	 * Specifically the function "Drive" 
	 */
    
	
	/*FUll explanatios @ http://first.wpi.edu/FRC/roborio/release/docs/java/ Look for the 
	 *Look for @DifferentialDrive class documentation
	 */
	private static DriveMode driveMode;
	private static OI driverJoystick;
	private DifferentialDrive Driver;

    /*
     * To create an instance of this class, it takes An Enumeration Object in parameters, Specifically a Drivemode Enumeration Object in @Robotmap
     * class
     * that allow to specify the way the robot going to be driving (ARCADEDRIVE,TANKDRIVE,CURVATUREDRIVE.
     * see the online doc for more explanations about the "drive mode".
     * https://wpilib.screenstepslive.com/s/currentCS/m/java/l/599700-getting-your-robot-to-drive-with-the-robotdrive-class 
     */
	public Driver(DriveMode mdrivemode) {
		
		driveMode = mdrivemode;
		SpeedControllerGroup m_left = new SpeedControllerGroup(Robotmap.frontLeftMotor, Robotmap.rearLeftMotor);
		SpeedControllerGroup m_right = new SpeedControllerGroup(Robotmap.frontRightMotor, Robotmap.frontRightMotor);
		

		driverJoystick = new OI(Robotmap.driverJoystick);
		
		m_left.setInverted(true);
		m_right.setInverted(true);
		

		Driver = new DifferentialDrive(m_left, m_right);
		

	}
	public void Drive() {
		if (driveMode == DriveMode.ARCADE) {
			Driver.arcadeDrive(driverJoystick.getLeft_Y_AXIS(), driverJoystick.getRight_X_AXIS());
			Driver.setMaxOutput(0.8);
		} else if (driveMode == DriveMode.CURVATUREDRIVE) {
			Driver.curvatureDrive(driverJoystick.getLeft_Y_AXIS(), driverJoystick.getRight_X_AXIS(), true);
			Driver.setMaxOutput(0.5);

		}
		
		 else if (driveMode == DriveMode.TANKDRIVE) {
				Driver.tankDrive(driverJoystick.getLeft_Y_AXIS(), driverJoystick.getRight_Y_AXIS(), true);
				Driver.setSafetyEnabled(true);
				Driver.setMaxOutput(0.5);
			}
			
	}
	
	
	
/*
	public void automousDrive() {
		
		double distance = Robotmap.ultraSonic.getRangeInches(); 
		
			Driver.arcadeDrive(-1, 0.0);
			Driver.setSafetyEnabled(true);			
			Driver.setMaxOutput(0.5);
		
	}
	*/
	
	
		
	
		public void OIDebugging() {
			//System.out.println("L X AXIS :"+driverJoystick.getLeft_X_AXIS());
			//System.out.println("L Y AXIS :"+driverJoystick.getLeft_Y_AXIS());
			//System.out.println("R X AXIS :"+driverJoystick.getRight_X_AXIS());
			//System.out.println("R Y AXIS :"+driverJoystick.getRight_Y_AXIS());
			//System.out.println("Z AXIS :"+driverJoystick.getZ_AXIS());
			
			
			 //SmartDashboard.putString("DISTANCE FROM THE NEAREST OBSTACLE :",  Robotmap.ultraSonic.getRangeInches() +" Inches");
			
			 //SmartDashboard.putNumber("Left X AXIS", driverJoystick.getLeft_X_AXIS());
			// SmartDashboard.putNumber("Left Y AXIS", driverJoystick.getLeft_Y_AXIS());
			 //SmartDashboard.putNumber("Right X AXIS", driverJoystick.getRight_X_AXIS());
			 //SmartDashboard.putNumber("Right Y AXIS", driverJoystick.getRight_Y_AXIS());
			//SmartDashboard.putNumber("ANGLE ", Robotmap.ahrs.getAngle());
			


			

	}

}
