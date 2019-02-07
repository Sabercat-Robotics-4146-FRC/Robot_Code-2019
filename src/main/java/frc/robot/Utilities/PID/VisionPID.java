package frc.robot.Utilities.PID;

import frc.robot.RobotMap;
import frc.robot.Utilities.Pixy.PixyI2C;
import frc.robot.Utilities.Logger;


public class VisionPID extends PID{
	PixyI2C pixy;
	public VisionPID(){
		super(RobotMap.PIXY_UPDATE_RATE, RobotMap.VISION_BREAK_TOLERANCE);
		setPID(RobotMap.VISION_kP, RobotMap.VISION_kI, RobotMap.VISION_kD);

		try {
			pixy = RobotMap.pixy;
		} catch (NullPointerException e) {
			Logger.error("VisionPID unable to make reference to pixy in RobotMap due to Null Pointer Exception.");
		}
	}
	
	public double getPixyMiddle(){
		
		return 0.0;
	}
    
    @Override
	public double getValue() {
		return 0.0;//TODO Dashboard.getDouble("gear_x") != 0 ? Dashboard.getDouble("gear_x") -160 : 0.0; // ROBOT MAP THIS PLEASE. ADD UNWRAP CODE
	}
}