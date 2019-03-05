package frc.robot.Subassemblies;

import frc.robot.RobotMap;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.PID.VisionTurningPID;

public class Drivetrain {
    private double speed = 0;
    private double rotation = 0;

    private double speedModifier = 1.0; // -1 for inverted and 1 for not
    private double rotationModifier = 0.83;

    private VisionTurningPID visionPID;

    public Drivetrain() {
        if (Math.abs(speedModifier) != 1 || Math.abs(rotationModifier) != 1) {
            ConsoleLogger.warning("Drivetrain Modifiers are not 1 or -1. Which means its clamped.");
        }

        visionPID = new VisionTurningPID();
    }

    public void update() {
        RobotMap.drive.arcadeDrive(this.speed * speedModifier, this.rotation * rotationModifier);
    }

    public void restartVisionPID() {
        this.visionPID.reset();
        this.visionPID.flush();
    }

    public void runVisionPID(double speed) {
        this.visionPID.update(RobotMap.timer.getDT());
        this.speed = speed;
        this.rotation = visionPID.get();
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