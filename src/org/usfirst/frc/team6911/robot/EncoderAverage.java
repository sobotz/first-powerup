package org.usfirst.frc.team6911.robot;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EncoderAverage implements PIDSource {
	PIDSourceType p_source;

	public EncoderAverage() {

		p_source = PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		// SmartDashboard.putNumber("Encoder Average",
		// Math.abs((6*3.14)*(Robotmap.rEncoder.get() +
		// Robotmap.lEncoder.get()/2)/360));
		return Math.abs((6 * 3.14) * (Robotmap.rEncoder.get() + Robotmap.lEncoder.get() / 2) / 360);
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