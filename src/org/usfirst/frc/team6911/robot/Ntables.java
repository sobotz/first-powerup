package org.usfirst.frc.team6911.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("deprecation")
public class Ntables {

	public static NetworkTableInstance Itable;

	public static NetworkTableEntry table;

	public static NetworkTable tables;

	public Ntables() {

		tables = NetworkTable.getTable("");

	}


	public static double GyroPIDGains(String gain) {
		double mGain = 1.0;
		if (gain == "kP") {

			mGain = NetworkTable.getTable("Root").getNumber(gain, 0.0);
		}

		if (gain == "kI") {

			mGain = NetworkTable.getTable("kP").getNumber(String.valueOf(gain), 1);
			
			SmartDashboard.putNumber("test",  NetworkTable.getTable("/SmartDashboard/kD").getNumber(String.valueOf(gain),0));
		}

		if (gain == "kD") {

			mGain =0;

		}

		if (gain == "kF") {

			mGain = NetworkTable.getTable(" ").getNumber(String.valueOf(gain), 0.0);

		}
		return mGain;
	}
}
