package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;
import frc.robot.Subassemblies.Arm.DirectionEnum;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.Dashboard;

public class Elevator {
    public enum LevelEnum {
        FRONT_TOP_PORT(RobotMap.FRONT_TOP_PORT_HEIGHT),
        FRONT_TOP_HATCH(RobotMap.FRONT_TOP_HATCH_HEIGHT),
        FRONT_MID_PORT(RobotMap.FRONT_MID_PORT_HEIGHT),
        FRONT_MID_HATCH(RobotMap.FRONT_MID_HATCH_HEIGHT),
        FRONT_SHIP_PORT(RobotMap.FRONT_SHIP_PORT_HEIGHT),
        FRONT_BOTTOM_PORT(RobotMap.FRONT_BOTTOM_PORT_HEIGHT),
        FRONT_INTAKING_HATCH(RobotMap.FRONT_INTAKING_HATCH_HEIGHT),
        INTAKING_CARGO(RobotMap.FRONT_INTAKING_CARGO_HEIGHT), // (Front only)
        FRONT_STORAGE(RobotMap.FRONT_STORAGE_HEIGHT),
        
        BACK_TOP_PORT(RobotMap.BACK_TOP_PORT_HEIGHT),
        BACK_TOP_HATCH(RobotMap.BACK_TOP_HATCH_HEIGHT),// _HEIGHT
        BACK_MID_PORT(RobotMap.BACK_MID_PORT_HEIGHT),
        BACK_MID_HATCH(RobotMap.BACK_MID_HATCH_HEIGHT),
        BACK_SHIP_PORT(RobotMap.BACK_SHIP_PORT_HEIGHT),
        BACK_BOTTOM_PORT(RobotMap.BACK_BOTTOM_PORT_HEIGHT),
        BACK_INTAKING_HATCH(RobotMap.BACK_INTAKING_HATCH_HEIGHT),
        BACK_STORAGE(RobotMap.BACK_STORAGE_HEIGHT);

        int setpoint;

        LevelEnum(int setpoint) {
            this.setpoint = setpoint;
        }

        @Override
        public String toString() {
            return super.toString().replace("_", " ");
        }
    }

    LevelEnum levelState;

    boolean limitSwitchPressedFlag = false;
    String str = "";

    public Elevator() {
        levelState = LevelEnum.FRONT_STORAGE;

        limitSwitchPressedFlag = false;
    }

    public void update() {

    }

    // Setters

    public void setElevatorLevel(LevelEnum state) {
        // if (RobotMap.arm.getD             TAKING_CARGO) { // FIX MEEEEEEEEEEEEEEEEEEEE
        //     levelState = state;
        // }
    }

    // Getters

    public LevelEnum getLevel() {
        return levelState;
    }

    // Testers

    public boolean isClear() { // Tests if the elivator is extended high enough for the arm to pass over.
        return RobotMap.elevatorFront.getSelectedSensorPosition() >= RobotMap.ELEVATOR_CLEAR_HEIGHT;
    }

    public boolean isValueClear(int value) { // Tests if a given value is high enough for the arm to pass over. May be unnessesary/bad.
        return value >= RobotMap.ELEVATOR_CLEAR_HEIGHT;
    }

    // Utilities

    private void moveElevator(double position) {
        RobotMap.elevatorFront.set(ControlMode.Position, position);
    }

}
