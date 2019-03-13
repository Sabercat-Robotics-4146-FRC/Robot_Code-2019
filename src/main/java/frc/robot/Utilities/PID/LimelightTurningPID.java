package frc.robot.Utilities.PID;

import frc.robot.RobotMap;
import frc.robot.Utilities.Pixy.PixyI2C;

public class LimelightTurningPID extends PID {
    PixyI2C pixy;

    public LimelightTurningPID() {
        super(RobotMap.LIMELIGHT_UPDATE_RATE, RobotMap.LIMELIGHT_BREAK_TOLERANCE);
        setPID(RobotMap.LIMELIGHT_kP, RobotMap.LIMELIGHT_kI, RobotMap.LIMELIGHT_kD);
    }

    @Override
    public double getValue() {
        return RobotMap.limelight.getX();
    }
}