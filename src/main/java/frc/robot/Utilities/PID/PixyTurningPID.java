package frc.robot.Utilities.PID;

import frc.robot.RobotMap;
import frc.robot.Utilities.Pixy.PixyI2C;

public class PixyTurningPID extends PID {
    PixyI2C pixy;

    public PixyTurningPID() {
        super(RobotMap.PIXY_UPDATE_RATE, RobotMap.PIXY_BREAK_TOLERANCE);
        setPID(RobotMap.PIXY_kP, RobotMap.PIXY_kI, RobotMap.PIXY_kD);
    }

    @Override
    public double getValue() {
        return RobotMap.limelight.getX();
    }
}