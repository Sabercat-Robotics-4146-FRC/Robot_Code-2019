package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;
import frc.robot.Subassemblies.ElevatorAndArm.ScoringPosition.Side;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.Dashboard;

public class ElevatorAndArm {
    public enum ScoringPosition {
        FRONT_TOP_ROCKET_PORT(RobotMap.ELEVATOR_FRONT_TOP_PORT_HEIGHT, RobotMap.ARM_FRONT_TOP_PORT_POSITION, Side.FRONT),
        FRONT_TOP_ROCKET_HATCH(RobotMap.ELEVATOR_FRONT_TOP_HATCH_HEIGHT, RobotMap.ARM_FRONT_TOP_HATCH_POSITION, Side.FRONT),
        FRONT_MID_ROCKET_PORT(RobotMap.ELEVATOR_FRONT_MID_PORT_HEIGHT, RobotMap.ARM_FRONT_MID_PORT_POSITION, Side.FRONT),
        FRONT_MID_ROCKET_HATCH(RobotMap.ELEVATOR_FRONT_MID_HATCH_HEIGHT, RobotMap.ARM_FRONT_MID_HATCH_POSITION, Side.FRONT),
        FRONT_CARGO_SHIP_PORT(RobotMap.ELEVATOR_FRONT_SHIP_PORT_HEIGHT, RobotMap.ARM_FRONT_SHIP_PORT_POSITION, Side.FRONT),
        FRONT_BOTTOM_ROCKET_PORT(RobotMap.ELEVATOR_FRONT_BOTTOM_PORT_HEIGHT, RobotMap.ARM_FRONT_BOTTOM_PORT_POSITION, Side.FRONT),
        FRONT_INTAKING_ROCKET_HATCH(RobotMap.ELEVATOR_FRONT_INTAKING_HATCH_HEIGHT, RobotMap.ARM_FRONT_INTAKING_HATCH_POSITION, Side.FRONT),
        INTAKING_CARGO(RobotMap.ELEVATOR_FRONT_INTAKING_CARGO_HEIGHT, RobotMap.ARM_FRONT_INTAKING_CARGO_POSITION, Side.FRONT), // (Front only)
        FRONT_STORAGE(RobotMap.ELEVATOR_FRONT_STORAGE_HEIGHT, RobotMap.ARM_FRONT_STORAGE_POSITION, Side.FRONT),
        
        BACK_TOP_ROCKET_PORT(RobotMap.ELEVATOR_BACK_TOP_PORT_HEIGHT, RobotMap.ARM_BACK_TOP_PORT_POSITION, Side.BACK),
        BACK_TOP_ROCKET_HATCH(RobotMap.ELEVATOR_BACK_TOP_HATCH_HEIGHT, RobotMap.ARM_BACK_TOP_HATCH_POSITION, Side.BACK),
        BACK_MID_ROCKET_PORT(RobotMap.ELEVATOR_BACK_MID_PORT_HEIGHT, RobotMap.ARM_BACK_MID_PORT_POSITION, Side.BACK),
        BACK_MID_ROCKET_HATCH(RobotMap.ELEVATOR_BACK_MID_HATCH_HEIGHT, RobotMap.ARM_BACK_MID_HATCH_POSITION, Side.BACK),
        BACK_CARGO_SHIP_PORT(RobotMap.ELEVATOR_BACK_SHIP_PORT_HEIGHT, RobotMap.ARM_BACK_SHIP_PORT_POSITION, Side.BACK),
        BACK_BOTTOM_ROCKET_PORT(RobotMap.ELEVATOR_BACK_BOTTOM_PORT_HEIGHT, RobotMap.ARM_BACK_BOTTOM_PORT_POSITION, Side.BACK),
        BACK_INTAKING_ROCKET_HATCH(RobotMap.ELEVATOR_BACK_INTAKING_HATCH_HEIGHT, RobotMap.ARM_BACK_INTAKING_HATCH_POSITION, Side.BACK),
        BACK_STORAGE(RobotMap.ELEVATOR_BACK_STORAGE_HEIGHT, RobotMap.ARM_BACK_STORAGE_POSITION, Side.BACK),

        FRONT_TRANSITION(RobotMap.ELEVATOR_FRONT_TRANSITION_POSITION, RobotMap.ARM_FRONT_TRANSITION_POSITION, Side.FRONT),
        FLIPPING_TO_FRONT_TRANSITION(),
        FLIPPING_TO_BACK_TRANSITION(),
        BACK_TRANSITION(RobotMap.ELEVATOR_BACK_TRANSITION_POSITION, RobotMap.ARM_BACK_TRANSITION_POSITION, Side.BACK),

        IDLE();

        public enum Side {
            FRONT,
            BACK
        }

        int elevatorSetpoint;
        int armSetpoint;
        Side side;

        ScoringPosition(int elevatorSetpoint, int armSetpoint, Side side) {
            this.elevatorSetpoint = elevatorSetpoint;
            this.armSetpoint = armSetpoint;
            this.side = side;
        }

        ScoringPosition() {
        }

        public int getElevatorSetpoint() {
            return elevatorSetpoint;
        }

        public int getArmSetpoint() {
            return armSetpoint;
        }

        public Side getSide() {
            return side;
        }

        @Override
        public String toString() {
            return super.toString().replace("_", " ");
        }
    }

    ScoringPosition desiredState;
    ScoringPosition actualState;

    boolean limitSwitchPressedFlag;

    public ElevatorAndArm() {
        desiredState = ScoringPosition.IDLE;
        actualState = ScoringPosition.IDLE;

        limitSwitchPressedFlag = false;
    }

    public void update() {

    }

    // Setters

    public void setScoringPosition(ScoringPosition state) {
        desiredState = state;
    }

    // Getters
    public Side getDesiredSide() {
        return desiredState.getSide();
    }

    public Side getActualSide() {
        return actualState.getSide();
    }

    // Utilities

    private void moveElevator(int position) {
        RobotMap.elevatorFront.set(ControlMode.Position, position);
    }

    private void moveArm(int position) {
        RobotMap.armPivot.set(ControlMode.Position, position);
    }

    private void move(int elevatorPosition, int armPosition){
        moveElevator(elevatorPosition);
        moveArm(armPosition);
    }

    private int getElevatorPosition() {
        return RobotMap.elevatorFront.getSelectedSensorPosition();
    }

    private int getArmPosition() {
        return RobotMap.armPivot.getSelectedSensorPosition();
    }

    public boolean isSameSide(ScoringPosition stateOne, ScoringPosition stateTwo) {
        return stateOne.getSide() == stateTwo.getSide();
    }

}
