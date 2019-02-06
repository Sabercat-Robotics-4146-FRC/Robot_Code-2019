package frc.robot.PID;

import frc.robot.RobotMap;


public class VisionPID extends PID{
	public VisionPID(){
		super(RobotMap.PIXY_UPDATE_RATE, RobotMap.VISION_BREAK_TOLERANCE);
		setPID(RobotMap.VISION_kP, RobotMap.VISION_kI, RobotMap.VISION_kD);
	}
    
    @Override
	public double getValue() {
		return RobotMap.tx.getDouble(0.0);
	}
}
