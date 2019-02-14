package frc.robot.Utilities.PID;

import frc.robot.RobotMap;
import frc.robot.Utilities.Pixy.PixyI2C;
import frc.robot.Utilities.ConsoleLogger;


public class VisionPID extends PID{
	PixyI2C pixy;
	public VisionPID(){
		super(RobotMap.LIMELIGHT_UPDATE_RATE, RobotMap.VISION_BREAK_TOLERANCE);
		setPID(RobotMap.VISION_kP, RobotMap.VISION_kI, RobotMap.VISION_kD);
	}
    
    @Override
	public double getValue() {
		return RobotMap.limelight.getX();
	}
}