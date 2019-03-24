package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.Dashboard;

public class ElevatorAndArm {
    public enum ScoringPositionEnum {
        FRONT_TOP_ROCKET_PORT(RobotMap.ELEVATOR_FRONT_TOP_PORT_HEIGHT, RobotMap.ARM_FRONT_TOP_PORT_POSITION, DirectionEnum.FRONT),
        FRONT_TOP_ROCKET_HATCH(RobotMap.ELEVATOR_FRONT_TOP_HATCH_HEIGHT, RobotMap.ARM_FRONT_TOP_HATCH_POSITION, DirectionEnum.FRONT),
        FRONT_MID_ROCKET_PORT(RobotMap.ELEVATOR_FRONT_MID_PORT_HEIGHT, RobotMap.ARM_FRONT_MID_PORT_POSITION, DirectionEnum.FRONT),
        FRONT_MID_ROCKET_HATCH(RobotMap.ELEVATOR_FRONT_MID_HATCH_HEIGHT, RobotMap.ARM_FRONT_MID_HATCH_POSITION, DirectionEnum.FRONT),
        FRONT_CARGO_SHIP_PORT(RobotMap.ELEVATOR_FRONT_SHIP_PORT_HEIGHT, RobotMap.ARM_FRONT_SHIP_PORT_POSITION, DirectionEnum.FRONT),
        FRONT_BOTTOM_ROCKET_PORT(RobotMap.ELEVATOR_FRONT_BOTTOM_PORT_HEIGHT, RobotMap.ARM_FRONT_BOTTOM_PORT_POSITION, DirectionEnum.FRONT),
        FRONT_INTAKING_HATCH(RobotMap.ELEVATOR_FRONT_INTAKING_HATCH_HEIGHT, RobotMap.ARM_FRONT_INTAKING_HATCH_POSITION, DirectionEnum.FRONT),
        INTAKING_CARGO(RobotMap.ELEVATOR_FRONT_INTAKING_CARGO_HEIGHT, RobotMap.ARM_FRONT_INTAKING_CARGO_POSITION, DirectionEnum.FRONT), // (Frofnt only)
        FRONT_STORAGE(RobotMap.ELEVATOR_FRONT_STORAGE_HEIGHT, RobotMap.ARM_FRONT_STORAGE_POSITION, DirectionEnum.FRONT),
        
        BACK_TOP_ROCKET_PORT(RobotMap.ELEVATOR_BACK_TOP_PORT_HEIGHT, RobotMap.ARM_BACK_TOP_PORT_POSITION, DirectionEnum.BACK),
        BACK_TOP_ROCKET_HATCH(RobotMap.ELEVATOR_BACK_TOP_HATCH_HEIGHT, RobotMap.ARM_BACK_TOP_HATCH_POSITION, DirectionEnum.BACK),
        BACK_MID_ROCKET_PORT(RobotMap.ELEVATOR_BACK_MID_PORT_HEIGHT, RobotMap.ARM_BACK_MID_PORT_POSITION, DirectionEnum.BACK),
        BACK_MID_ROCKET_HATCH(RobotMap.ELEVATOR_BACK_MID_HATCH_HEIGHT, RobotMap.ARM_BACK_MID_HATCH_POSITION, DirectionEnum.BACK),
        BACK_CARGO_SHIP_PORT(RobotMap.ELEVATOR_BACK_SHIP_PORT_HEIGHT, RobotMap.ARM_BACK_SHIP_PORT_POSITION, DirectionEnum.BACK),
        BACK_BOTTOM_ROCKET_PORT(RobotMap.ELEVATOR_BACK_BOTTOM_PORT_HEIGHT, RobotMap.ARM_BACK_BOTTOM_PORT_POSITION, DirectionEnum.BACK),
        BACK_INTAKING_ROCKET_HATCH(RobotMap.ELEVATOR_BACK_INTAKING_HATCH_HEIGHT, RobotMap.ARM_BACK_INTAKING_HATCH_POSITION, DirectionEnum.BACK),
        BACK_STORAGE(RobotMap.ELEVATOR_BACK_STORAGE_HEIGHT, RobotMap.ARM_BACK_STORAGE_POSITION, DirectionEnum.BACK),

        FRONT_TRANSITION(RobotMap.ELEVATOR_FRONT_TRANSITION_POSITION, RobotMap.ARM_FRONT_TRANSITION_POSITION, DirectionEnum.FRONT),
        FLIPPING_TO_FRONT_TRANSITION(),
        FLIPPING_TO_BACK_TRANSITION(),
        BACK_TRANSITION(RobotMap.ELEVATOR_BACK_TRANSITION_POSITION, RobotMap.ARM_BACK_TRANSITION_POSITION, DirectionEnum.BACK);

        enum DirectionEnum {
            FRONT,
            BACK
        }

        int elevatorSetpoint;
        int armSetpoint;
        DirectionEnum direction;

        ScoringPositionEnum(int elevatorSetpoint, int armSetpoint, DirectionEnum direction) {
            this.elevatorSetpoint = elevatorSetpoint;
            this.armSetpoint = armSetpoint;
            this.direction = direction;
        }

        ScoringPositionEnum() {
        }

        public int getElevatorSetpoint() {
            return elevatorSetpoint;
        }

        public int getArmSetpoint() {
            return armSetpoint;
        }

        public DirectionEnum getDirection() {
            return direction;
        }

        @Override
        public String toString() {
            return super.toString().replace("_", " ");
        }
    }

    ScoringPositionEnum scoringStates;

    boolean limitSwitchPressedFlag;

    public ElevatorAndArm() {
        scoringStates = ScoringPositionEnum.FRONT_STORAGE;

        limitSwitchPressedFlag = false;
    }

    public void update() {

    }

    // Setters

    public void setScoringPosition(ScoringPositionEnum state) {
        
    }

    // Utilities

    private void moveElevator(double position) {
        RobotMap.elevatorFront.set(ControlMode.Position, position);
    }

}
