package frc.robot.Utilities;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {
	public static void send(String fieldName, double value) {
		SmartDashboard.putNumber(fieldName, value);
	}
	
	public static void send(String fieldName, int value) {
		SmartDashboard.putNumber(fieldName, value);
	}
	
	public static void send(String fieldName, boolean value) {
		SmartDashboard.putBoolean(fieldName, value);
	}

	public static void send(String fieldName, String string) {
		SmartDashboard.putString(fieldName, string);
	}
}