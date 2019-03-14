package frc.robot.Subassemblies;

import frc.robot.RobotMap;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.PID.LimelightTurningPID;

public class Drivetrain {
    private double speed = 0;
    private double rotation = 0;

    private double speedModifier = 1.0; // -1 for inverted and 1 for not
    private double rotationModifier = 0.65;

    private LimelightTurningPID limelightVisionPID;

    public Drivetrain() {
        if (Math.abs(speedModifier) != 1 || Math.abs(rotationModifier) != 1) {
            ConsoleLogger.warning("Drivetrain Modifiers are not 1 or -1. Which means its clamped.");
        }
        

        limelightVisionPID = new LimelightTurningPID();
    }

    public void update() {
        RobotMap.drive.arcadeDrive(this.speed * speedModifier, this.rotation * rotationModifier);
    }

    public void restartVisionPID() {
        this.limelightVisionPID.reset();
        this.limelightVisionPID.flush();
    }

    public void runVisionPID(double speed) {
        this.limelightVisionPID.update(RobotMap.timer.getDT());
        this.speed = speed;
        this.rotation = limelightVisionPID.get();
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