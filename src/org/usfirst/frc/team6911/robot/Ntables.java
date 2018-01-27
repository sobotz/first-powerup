package org.usfirst.frc.team6911.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

@SuppressWarnings("deprecation")
public class Ntables {

	public static NetworkTableInstance Itable;

	public static NetworkTableEntry table;

	public static NetworkTable tables;

	public Ntables() {

		tables = NetworkTable.getTable("SmartDashboard");

	}


	public static double GyroPIDGains(String gain) {
		double mGain = 0.0;
		if (gain == "kP") {

			tables.getNumber(String.valueOf(gain), 0.0);
		}

		if (gain == "kI") {

			mGain = tables.getNumber(String.valueOf(gain), 1.0);
		}

		if (gain == "kD") {

			mGain = tables.getNumber(String.valueOf(gain), 0.0);

		}

		if (gain == "kF") {

			mGain = tables.getNumber(String.valueOf(gain), 0.0);

		}
		return mGain;
	}
}
