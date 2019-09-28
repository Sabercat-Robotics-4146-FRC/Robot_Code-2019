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

        TRANSITION_FRONT(RobotMap.ELEVATOR_TRANSITION_POSITION, RobotMap.ARM_FRONT_TRANSITION_POSITION, Side.FRONT),
        TRANSITION_BACK(RobotMap.ELEVATOR_TRANSITION_POSITION, RobotMap.ARM_BACK_TRANSITION_POSITION, Side.BACK),
        IDLE();
        // Something to note. If IDLE has a get method called from
        // it they will throw a null pointer exception. So don't.

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

        public boolean isFrontSide() {
            return side == Side.FRONT;
        }

        public boolean isBackSide() {
            return side == Side.BACK;
        }

        @Override
        public String toString() {
            return super.toString().replace("_", " ");
        }
    }

    ScoringPosition finalState;
    ScoringPosition currentState;

    boolean limitSwitchPressedFlag;

    public ElevatorAndArm() {
        finalState = ScoringPosition.IDLE;
        currentState = ScoringPosition.IDLE;

        limitSwitchPressedFlag = false;
    }

    public void update() {
        switch(currentState) {
            case IDLE:
                if (finalState == ScoringPosition.IDLE) {
                    holdArmAndElevator();
                    // disableArmAndElevator();
                } else if (isArmPhisicalyInFront()) { // TODO check polarity
                    if (finalState.isFrontSide()) {
                        currentState = finalState;
                    } else { // Arm needs to switch sides
                        currentState = ScoringPosition.TRANSITION_FRONT;
                    }
                } else { // arm is in back
                    if (finalState.isBackSide()) {
                        currentState = finalState;
                    } else { // Arm needs to switch sides
                        currentState = ScoringPosition.TRANSITION_BACK;
                    }
                }
                break;
            case TRANSITION_FRONT:
                // if (!isPhysicallyHere(currentState)) {
                //     move(currentState);
                
                if(!isArmPhysicallyHere(currentState.armSetpoint) 
                    || !isElevatorPhysicallyGreaterThan(RobotMap.ELEVATOR_CLEAR_FOR_ARM_HEIGHT)) {
                    if (finalState.elevatorSetpoint > currentState.elevatorSetpoint) {
                        move(finalState.elevatorSetpoint, currentState.armSetpoint);
                    } else {
                        move(currentState);
                    }
                } else if(finalState.isFrontSide()) {
                    if (isArmClearForElevator() && isArmPhisicalyInFront()) {
                        currentState = finalState;
                    }
                } else {
                    currentState = ScoringPosition.TRANSITION_BACK;
                }
                break;
            case TRANSITION_BACK:
                // if (!isPhysicallyHere(currentState)) {
                //     move(currentState);

                if(!isArmPhysicallyHere(currentState.armSetpoint) 
                    || !isElevatorPhysicallyGreaterThan(RobotMap.ELEVATOR_CLEAR_FOR_ARM_HEIGHT)) {
                    if (finalState.elevatorSetpoint > currentState.elevatorSetpoint) {
                        move(finalState.elevatorSetpoint, currentState.armSetpoint);
                    } else {
                        move(currentState);
                    }
                } else if(finalState.isBackSide()) {
                    if (isArmClearForElevator() && isArmPhisicalyInBack()) {
                        currentState = finalState;
                    }
                } else {
                    currentState = ScoringPosition.TRANSITION_FRONT;
                }
                break;
            default: // State is an actual scoring position
                if(currentState == finalState) {
                    move(currentState);
                } else if(isSameSide(finalState, currentState)) {
                        currentState = finalState;
                        move(currentState);
                } else if(currentState.getSide() == ScoringPosition.Side.FRONT) {
                    currentState = ScoringPosition.TRANSITION_FRONT;
                } else {
                    currentState = ScoringPosition.TRANSITION_BACK;
                }

        }

        if (RobotMap.elevatorLimitSwitch.get() && !limitSwitchPressedFlag) {
            ConsoleLogger.debug("Elevator limit switch pressed, resetting elevator encoder.");
            RobotMap.elevatorFront.setSelectedSensorPosition(0);
            limitSwitchPressedFlag = true;
        }

        if (!RobotMap.elevatorLimitSwitch.get()) {
            limitSwitchPressedFlag = false;
        }

        Dashboard.send("Actual State", currentState.toString());
        Dashboard.send("Final State", finalState.toString());
        Dashboard.send("Arm Is Physically In Front", isArmPhisicalyInFront());

    }

    // Setters

    public void setScoringPosition(ScoringPosition state) {
        finalState = state;
    }

    // Getters
    public Side getDesiredSide() {
        return finalState.getSide();
    }

    public Side getCurrentSide() {
        return currentState.getSide();
    }

    // Utilities

    private void moveElevator(int position) {
        if (position == 0) {
            lowerElevatorSlowly();
        } else {
            RobotMap.elevatorFront.set(ControlMode.Position, position);
        }
    }

    private void lowerElevatorSlowly() {
        if (RobotMap.elevatorLimitSwitch.get()) {
            RobotMap.elevatorFront.set(ControlMode.PercentOutput, 0);
        } else {
            if (RobotMap.elevatorFront.getSelectedSensorPosition() >= RobotMap.LOWER_SLOWER_HEIGHT) {
                RobotMap.elevatorFront.set(ControlMode.PercentOutput, -0.3);
            } else if (RobotMap.elevatorFront.getSelectedSensorPosition() < RobotMap.LOWER_SLOWER_HEIGHT) {
                RobotMap.elevatorFront.set(ControlMode.PercentOutput, -0.2);
            }
        }
    }

    private void moveArm(int position) {
        RobotMap.armPivot.set(ControlMode.Position, position + RobotMap.armOffset);
    }

    private void move(int elevatorPosition, int armPosition){
        moveElevator(elevatorPosition);
        moveArm(armPosition);
    }

    private void move(ScoringPosition position) {
        if ((position != ScoringPosition.TRANSITION_BACK && position != ScoringPosition.TRANSITION_FRONT && position != ScoringPosition.IDLE) &&
                !isSameSide(currentState, position)) {
            currentState = ScoringPosition.IDLE;
            finalState = ScoringPosition.IDLE;
            RobotMap.pilotController.setRumbleBuzz(5, 0.3, 0.3);
        } else {
            moveElevator(position.getElevatorSetpoint());
            moveArm(position.getArmSetpoint());
        }

    }

    private void holdArmAndElevator() {
        moveElevator(RobotMap.elevatorFront.getSelectedSensorPosition());
        moveArm(RobotMap.armPivot.getSelectedSensorPosition());
    }

    private void disableArmAndElevator() {
        RobotMap.elevatorFront.set(ControlMode.PercentOutput, 0);
        RobotMap.armPivot.set(ControlMode.PercentOutput, 0);
    }

    private int getElevatorPosition() {
        return RobotMap.elevatorFront.getSelectedSensorPosition();
    }

    private int getArmPosition() {
        return RobotMap.armPivot.getSelectedSensorPosition();
    }

    public ScoringPosition getCurrentState() {
        return currentState;
    }

    public ScoringPosition getFinalState() {
        return finalState;
    }

    public boolean isSameSide(ScoringPosition stateOne, ScoringPosition stateTwo) {
        return stateOne.getSide() == stateTwo.getSide();
    }

    public boolean isArmPhisicalyInFront() {
        return getArmPosition() > RobotMap.ARM_HALF_WAY_POSITION;
    }

    public boolean isArmPhisicalyInBack() {
        return getArmPosition() <= RobotMap.ARM_HALF_WAY_POSITION;
    }

    public boolean isPhysicallyHere(ScoringPosition state) {
        return inTolerance(RobotMap.elevatorFront.getSelectedSensorPosition(), state.getElevatorSetpoint(), RobotMap.ELEVATOR_TOLERENCE) &&
                inTolerance(RobotMap.armPivot.getSelectedSensorPosition(), state.getArmSetpoint(), RobotMap.ARM_TOLERENCE);
    }

    public boolean isElevatorPhysicallyGreaterThan(int position) {
        return RobotMap.elevatorFront.getSelectedSensorPosition() > position;
    }

    public boolean isArmPhysicallyHere(int position) {
        return inTolerance(RobotMap.armPivot.getSelectedSensorPosition(), position, RobotMap.ARM_TOLERENCE);
    }

    public boolean inTolerance(int physicalValue, int wantedValue, int tolerence) {
        return physicalValue <= wantedValue + tolerence &&
                physicalValue >= wantedValue - tolerence;
    } 

    public boolean isElevatorClearForArm() {
        return RobotMap.elevatorFront.getSelectedSensorPosition() > RobotMap.ELEVATOR_CLEAR_FOR_ARM_HEIGHT;
    }

    public boolean isArmClearForElevator() {
        return RobotMap.armPivot.getSelectedSensorPosition() > RobotMap.FRONT_ARM_DANGER_ZONE_POSITION ||
                RobotMap.armPivot.getSelectedSensorPosition() < RobotMap.BACK_ARM_DANGER_ZONE_POSITION;
    }

    public void setFinalAndCurrentStates(ScoringPosition state) {
        finalState = state;
        currentState = state;
    }
}
