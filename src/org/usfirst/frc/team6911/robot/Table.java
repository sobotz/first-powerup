package org.usfirst.frc.team6911.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Table {

	public static double getNumber(String key, double defaultValue) {

		return NetworkTableInstance.getDefault().getEntry(key).getDouble(defaultValue);
	}
}
