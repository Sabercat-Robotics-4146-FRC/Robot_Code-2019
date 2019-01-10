package frc.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Dashboard {
	
	private static NetworkTable networktable;
	
	public static void setNetworkTable(NetworkTable n) {
		networktable = n;
	}
	public static double getDouble(String name) {
		return networktable.getDouble(name, 0);
	}
	public static void send(String fieldName, double value) {
		networktable.putNumber(fieldName, value);
	}
	
	public static void send(String fieldName, int value) {
		networktable.putNumber(fieldName, value);
	}
	
	public static void send(String fieldName, boolean value) {
		networktable.putBoolean(fieldName, value);
	}
	public static void publishImage() {
		
	}

	public static void send(String fieldName, String string) {
		networktable.putString(fieldName, string);
		
	}
}