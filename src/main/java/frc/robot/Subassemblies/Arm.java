package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.Dashboard;

public class Arm {
    
    public enum DirectionEnum {
        FRONT,
        BACK
    }

    public enum ArmEnum {
        STORAGE,
        INTAKING_CARGO,
        FRONT_LEVEL,
        BACK_LEVEL,
        FRONT_TILT,
        BACK_TILT,
        FRONT_BOTTOM_CARGO,
        FRONT_MID_CARGO,
        FRONT_TOP_CARGO,
        BACK_BOTTOM_CARGO,
        BACK_MID_CARGO,
        BACK_TOP_CARGO,
        FRONT_CARGO_SHIP_PORT,
        BACK_CARGO_SHIP_PORT,
        BACK_TILT_FOR_INTAKING_CARGO, // this is a really stupid state to fix a problem
        RESET
    }

    DirectionEnum direction = DirectionEnum.FRONT;
    DirectionEnum lastDirection = DirectionEnum.FRONT;

    ArmEnum armState = ArmEnum.STORAGE;

    boolean changingDirectionFlag = false;

    public void update() {

        switch(armState) {
            case STORAGE:
                if (isArmInFront()) {
                    setArmPosition(RobotMap.ARM_FRONT_STORAGE_POSITION);
                } else if (isArmInBack()) {
                    setArmPosition(RobotMap.ARM_BACK_STORAGE_POSITION);
                } else {
                    ConsoleLogger.error("The arm is not in the front or back. What the fuck!");
                }
                break;

            case INTAKING_CARGO:
                if (isArmInFront()) {
                    setArmPosition(RobotMap.ARM_FRONT_CARGO_INTAKING_POSITION);
                }
                break;

            case FRONT_LEVEL:
                setArmPosition(RobotMap.ARM_FRONT_LEVEL_POSITION);
                break;

            case BACK_LEVEL:
                setArmPosition(RobotMap.ARM_BACK_LEVEL_POSITION);
                break;

            case FRONT_TILT:
                setArmPosition(RobotMap.ARM_FRONT_TILT_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.BACK_LEVEL;
                }
                break;

            case BACK_TILT:
                setArmPosition(RobotMap.ARM_BACK_TILT_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.FRONT_LEVEL;
                }
                break;

            case BACK_TILT_FOR_INTAKING_CARGO:
                setArmPosition(RobotMap.ARM_BACK_TILT_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.INTAKING_CARGO;
                }
                break; 

            // case FRONT_CARGO:
            //     setArmPosition(RobotMap.ARM_FRONT_CARGO_POSITION);

            //     if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
            //         armState = ArmEnum.BACK_CARGO;
            //     }
            //     break;

            case FRONT_BOTTOM_CARGO:
                setArmPosition(RobotMap.ARM_FRONT_BOTTOM_CARGO_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.BACK_BOTTOM_CARGO;
                }
                break;

            case FRONT_MID_CARGO:
                setArmPosition(RobotMap.ARM_FRONT_MID_CARGO_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.BACK_MID_CARGO;
                }
                break;

            case FRONT_TOP_CARGO:
                setArmPosition(RobotMap.ARM_FRONT_TOP_CARGO_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.BACK_TOP_CARGO;
                }
                break;

            // case BACK_CARGO:
            //     setArmPosition(RobotMap.ARM_BACK_CARGO_POSITION);

            //     if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
            //         armState = ArmEnum.FRONT_CARGO;
            //     }
            //     break;

            case BACK_BOTTOM_CARGO:
                setArmPosition(RobotMap.ARM_BACK_BOTTOM_CARGO_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.FRONT_BOTTOM_CARGO;
                }
                break;

            case BACK_MID_CARGO:
                setArmPosition(RobotMap.ARM_BACK_MID_CARGO_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.FRONT_MID_CARGO;
                }
                break;

            case BACK_TOP_CARGO:
                setArmPosition(RobotMap.ARM_BACK_TOP_CARGO_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.FRONT_TOP_CARGO;
                }
                break;

            case FRONT_CARGO_SHIP_PORT:
                setArmPosition(RobotMap.ARM_FRONT_CARGO_SHIP_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.BACK_CARGO_SHIP_PORT;
                }
                break;

            case BACK_CARGO_SHIP_PORT:
                setArmPosition(RobotMap.ARM_BACK_CARGO_SHIP_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.FRONT_CARGO_SHIP_PORT;
                }
                break;

            case RESET:
                // write this once we know pot values
                break;
        }

        Dashboard.send("isArmCLear", isArmClear());
        Dashboard.send("Arm State", armState.toString());
    
    }

    public void updateDirection() {
        lastDirection = direction;
        changingDirectionFlag = false;
    }
    
    public void changeDirection() {
        direction = direction == DirectionEnum.FRONT ? DirectionEnum.BACK : DirectionEnum.FRONT;
        changingDirectionFlag = true;
    }

    public boolean isArmClear() {
        if (lastDirection == DirectionEnum.FRONT && direction == DirectionEnum.BACK) {
            return RobotMap.armPivot.getSelectedSensorPosition() <= RobotMap.BACK_ARM_CLEAR_VALUE;
        }
        if (lastDirection == DirectionEnum.BACK && direction == DirectionEnum.FRONT) {
            return RobotMap.armPivot.getSelectedSensorPosition() >= RobotMap.FRONT_ARM_CLEAR_VALUE; 
        } 
        if (lastDirection == DirectionEnum.FRONT && direction == DirectionEnum.FRONT) {
            return RobotMap.armPivot.getSelectedSensorPosition() >= RobotMap.FRONT_ARM_CLEAR_VALUE;
        }
        if (lastDirection == DirectionEnum.BACK && direction == DirectionEnum.BACK) {
            return RobotMap.armPivot.getSelectedSensorPosition() <= RobotMap.BACK_ARM_CLEAR_VALUE;
        } else {
            ConsoleLogger.error("Checking isArmCLear when not changing direction.");
            return false;
        }
    }

    public void setArmPosition(double position) {
        RobotMap.armPivot.set(ControlMode.Position, position);
    }

    public boolean isArmInFront() {
        return (RobotMap.armPivot.getSelectedSensorPosition() >= RobotMap.MIDDLE_ARM_VALUE);
    }

    public boolean isArmInBack() {
        return (RobotMap.armPivot.getSelectedSensorPosition() < RobotMap.MIDDLE_ARM_VALUE);
    }

    public void setDirectionFront() {
        direction = DirectionEnum.FRONT;
    }

    public void setDirectionBack() {
        direction = DirectionEnum.BACK;
    }

    public void setArmStateFrontLevel() {
        armState = ArmEnum.FRONT_LEVEL;
    }

    public void setArmStateFrontTilt() {
        armState = ArmEnum.FRONT_TILT;
    }

    public void setArmStateBackLevel() {
        armState = ArmEnum.BACK_LEVEL;
    }

    public void setArmStateBackTilt() {
        armState = ArmEnum.BACK_TILT;
    }

    public void setArmStateFrontBottomCargo() {
        armState = ArmEnum.FRONT_BOTTOM_CARGO;
    }

    public void setArmStateFrontMidCargo() {
        armState = ArmEnum.FRONT_MID_CARGO;
    }

    public void setArmStateFrontTopCargo() {
        armState = ArmEnum.FRONT_TOP_CARGO;
    }

    public void setArmStateFrontCargoShipPort() {
        armState = ArmEnum.FRONT_CARGO_SHIP_PORT;
    }

    public void setArmStateBackBottomCargo() {
        armState = ArmEnum.BACK_BOTTOM_CARGO;
    }

    public void setArmStateBackMidCargo() {
        armState = ArmEnum.BACK_MID_CARGO;
    }

    public void setArmStateBackTopCargo() {
        armState = ArmEnum.BACK_TOP_CARGO;
    }

    public void setArmStateBackCargoShipPort() {
        armState = ArmEnum.BACK_CARGO_SHIP_PORT;
    }

    public void setArmStateStorage() {
        armState = ArmEnum.STORAGE;
    }

    public void setArmStateFrontIntakingCargo() {
        armState = ArmEnum.INTAKING_CARGO;
    }

    public void setArmStateBackTiltForIntakingCargo() {
        armState = ArmEnum.BACK_TILT_FOR_INTAKING_CARGO;
    }

    public DirectionEnum getDirection() {
        return direction;
    }
    
}