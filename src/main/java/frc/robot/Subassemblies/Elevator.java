package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;
import frc.robot.Subassemblies.Arm.DirectionEnum;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.Dashboard;

public class Elevator {
    public enum LevelEnum {
        STORAGE,
        INTAKING_CARGO,
        INTAKING_HATCH,
        BOTTOM_PORT,
        SHIP_PORT,
        MID_HATCH,
        MID_PORT,
        TOP_HATCH,
        TOP_PORT
    }

    LevelEnum levelState;

    boolean limitSwitchPressedFlag = false;
    String str = "";

    public Elevator() {
        levelState = LevelEnum.STORAGE;

        limitSwitchPressedFlag = false;
    }

    public void update() {

    }

    // Setters

    public void setElevatorLevel(LevelEnum state) {
        if (RobotMap.arm.getD             TAKING_CARGO) { // FIX MEEEEEEEEEEEEEEEEEEEE
            levelState = state;
        }
    }

    // Getters

    public LevelEnum getLevel() {
        return levelState;
    }

    // Testers

    public boolean isElevatorClear() { // Tests if the elivator is extended high enough for the arm to pass over.
        return RobotMap.elevatorFront.getSelectedSensorPosition() >= RobotMap.ARM_OVER_HEIGHT;
    }

    public boolean isValueClear(int value) { // Tests if a given value is high enough for the arm to pass over. May be unnessesary/bad.
        return value >= RobotMap.ARM_OVER_HEIGHT;
    }

    // Utilities

    private void moveElevator(double position) {
        RobotMap.elevatorFront.set(ControlMode.Position, position);
    }

}
