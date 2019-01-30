package frc.robot.Subassemblies;

import frc.robot.RobotMap;

public class Drivetrain{

    public static void update() {
        RobotMap.drive.arcadeDrive(-RobotMap.driverController.getDeadbandLeftYAxis(), RobotMap.driverController.getDeadbandRightYAxis());
    }
    
}