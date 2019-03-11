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
        FRONT_LEVEL,
        BACK_LEVEL,
        FRONT_TILT,
        BACK_TILT,
        FRONT_CARGO,
        BACK_CARGO,
        RESET
    }

    DirectionEnum direction = DirectionEnum.FRONT;
    DirectionEnum lastDirection = DirectionEnum.FRONT;

    ArmEnum armState = ArmEnum.FRONT_LEVEL;

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

            case FRONT_CARGO:
                setArmPosition(RobotMap.ARM_FRONT_CARGO_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.BACK_CARGO;
                }
                break;

            case BACK_CARGO:
                setArmPosition(RobotMap.ARM_BACK_CARGO_POSITION);

                if (RobotMap.elevator.isElevatorClear() && changingDirectionFlag) {
                    armState = ArmEnum.FRONT_CARGO;
                }
                break;

            case RESET:
                // write this once we know pot values
                break;
        }

        Dashboard.send("isArmCLear", isArmClear());
    
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

    public void setArmStateFrontCargo() {
        armState = ArmEnum.FRONT_CARGO;
    }

    public void setArmStateBackCargo() {
        armState = ArmEnum.BACK_CARGO;
    }

    public void setArmStateStorage() {
        armState = ArmEnum.STORAGE;
    }

    public DirectionEnum getDirection() {
        return direction;
    }
    
}