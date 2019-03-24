package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.MotionProfiling.MotionProfile;

public class Arm {
    public enum DirectionEnum {
        FRONT,
        BACK
    }

    public enum ArmPositionEnum {
        STORAGE, // only for front
        FRONT_LEVEL,
        BACK_LEVEL,
        FRONT_TILT,
        BACK_TILT,
        RESET
    }

    DirectionEnum directionState;
    ArmPositionEnum armPositionState = ArmPositionEnum.STORAGE;

    public Arm() {
        directionState = DirectionEnum.FRONT;
        armPositionState = ArmPositionEnum.STORAGE;
    }

    public void update() {

    }

    // Setters
    
    public void toggleDirection() {
        directionState = directionState == DirectionEnum.FRONT ? DirectionEnum.BACK : DirectionEnum.FRONT;
    }

    public void setDirection(DirectionEnum state) { // may be unneeded
        directionState = state;
    }

    public void setPosition(ArmPositionEnum state) {
        armPositionState = state;
    }

    // Getters

    public DirectionEnum getDirection() {
        return directionState;
    }

    public ArmPositionEnum getPosition() {
        return armPositionState;
    }

    // Testers

    public boolean isClear() { // tests if the arm is forward or backwards enough to clear the top of the elevator.
        return RobotMap.armPivot.getSelectedSensorPosition() > RobotMap.FRONT_ARM_CLEAR_VALUE &&
                RobotMap.armPivot.getSelectedSensorPosition() < RobotMap.BACK_ARM_CLEAR_VALUE; // TODO check pot polarity
    }

    // Utilities

    private void moveArm(double position) {
        RobotMap.armPivot.set(ControlMode.Position, position);
    }

    
}