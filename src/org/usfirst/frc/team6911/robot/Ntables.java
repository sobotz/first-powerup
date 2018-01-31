package org.usfirst.frc.team6911.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

@SuppressWarnings("deprecation")
public class Ntables {



	public static NetworkTable tables;

	public Ntables() {

		NetworkTable.getTable("SmartDashboard");

	}


	public static double GyroPIDGains(String gain) {
		double mGain = 0.0;
		if (gain == "kP") {

			tables.getDouble(String.valueOf(gain), 0.0);
		}

		if (gain == "kI") {

			tables.getDouble(String.valueOf(gain), 0.0);
		}

		if (gain == "kD") {

			tables.getDouble(String.valueOf(gain), 0.0);

		}

		if (gain == "kF") {

			tables.getDouble(String.valueOf(gain), 0.0);

		}
		return mGain;
	}
}
