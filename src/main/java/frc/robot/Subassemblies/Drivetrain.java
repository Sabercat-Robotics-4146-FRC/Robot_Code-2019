package frc.robot.Subassemblies;

import frc.robot.RobotMap;
import frc.robot.Utilities.Logger;

public class Drivetrain {
    enum DrivetrainEnum {
        CONTROLLER_DRIVE,
        MANUAL_VALUES // Add more later.
    }

    DrivetrainEnum drivetrainState;
    private double speedInverter = 1; // -1 for inverted and 1 for not
    private double rotationInverter = 1;

    public Drivetrain() {
        this.drivetrainState = DrivetrainEnum.CONTROLLER_DRIVE;

        if (Math.abs(speedInverter) != 1 || Math.abs(rotationInverter) != 1) {
            Logger.error("Drivetrain Inversions are not 1 or -1.");
        }
    }

    public void update() {
        if(drivetrainState == DrivetrainEnum.CONTROLLER_DRIVE) {
            RobotMap.drive.arcadeDrive(speedInverter * RobotMap.driverController.getDeadbandLeftYAxis(),
                rotationInverter * RobotMap.driverController.getDeadbandRightYAxis());
        } else if(drivetrainState == DrivetrainEnum.MANUAL_VALUES) {
            // Basically do nothing. Just opens up the option to use the setDrivetrainValues method.
        }
    }

    public void changeDrivetrainState(DrivetrainEnum state) {
        this.drivetrainState = state;
    }
    
    public void setDrivetrainValues(double speed, double rotation) {
        if (drivetrainState == DrivetrainEnum.MANUAL_VALUES) {
            RobotMap.drive.arcadeDrive(speedInverter * speed, rotationInverter * rotation);
        } else {
            Logger.error("Cannot use setDrivetrainValues when not in MANUAL_VALUES state.");
        }
    }
}