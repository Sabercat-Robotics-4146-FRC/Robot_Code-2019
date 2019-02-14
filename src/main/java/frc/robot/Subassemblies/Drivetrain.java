package frc.robot.Subassemblies;

import frc.robot.RobotMap;
import frc.robot.Utilities.ConsoleLogger;

public class Drivetrain {
    // enum DrivetrainEnum {
    //     CONTROLLER_DRIVE,
    //     VISION_DRIVE,
    //     CUSTOM_DRIVE // Add more later.
    // }

    // private DrivetrainEnum drivetrainState;

    private double speed = 0;
    private double rotation = 0;

    private double speedModifier = 0.8; // -1 for inverted and 1 for not
    private double rotationModifier = 0.8;

    public Drivetrain() {
        // drivetrainState = DrivetrainEnum.CONTROLLER_DRIVE;

        if (Math.abs(speedModifier) != 1 || Math.abs(rotationModifier) != 1) {
            ConsoleLogger.warning("Drivetrain Modifiers are not 1 or -1. Which means its clamped.");
        }
    }

    public void update() {
        RobotMap.drive.arcadeDrive(this.speed * speedModifier, this.rotation * rotationModifier);
    }

    public void setDrivetrainValues(double speed, double rotation) {
        this.speed = speed;
        this.rotation = rotation;
    }

    public void setSpeedModifier(double speedModifier) {
        this.speedModifier = speedModifier;
    }

    public void setRotationModifier(double rotationModifier) {
        this.rotationModifier = rotationModifier;
    }
}