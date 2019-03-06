package frc.robot.Subassemblies;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.RobotMap;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.MotionProfiling.MotionProfile;

public class Arm {

    MotionProfile armMotionProfile;

    public Arm() {
        armMotionProfile = new MotionProfile(RobotMap.armPivot);
    }
    
    public enum DirectionEnum {
        FRONT,
        BACK
    }

    public enum ArmEnum {
        FRONT_LEVEL,
        BACK_LEVEL,
        FRONT_TILT,
        BACK_TILT,
        RESET
    }

    DirectionEnum direction = DirectionEnum.FRONT;
    DirectionEnum lastDirection = DirectionEnum.FRONT;

    ArmEnum armState = ArmEnum.FRONT_LEVEL;

    boolean changingDirectionFlag = false;

    public void update() {

        switch(armState) {
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

            case RESET:
                // write this once we know pot values
                break;
        }
    
    }

    public void updateDirection() {
        lastDirection = direction;
        changingDirectionFlag = false;
    }
    
    public void changeDirection() {
        direction = direction == DirectionEnum.FRONT ? DirectionEnum.BACK : DirectionEnum.FRONT;
        changingDirectionFlag = true;

        if (lastDirection == DirectionEnum.FRONT && direction == DirectionEnum.BACK) {
            armState = ArmEnum.FRONT_TILT;
        } else if (lastDirection == DirectionEnum.BACK && direction == DirectionEnum.FRONT) {
            armState = ArmEnum.BACK_TILT;
        }
    }

    public boolean isArmClear() {
        if (lastDirection == DirectionEnum.FRONT && direction == DirectionEnum.BACK) {
            if (RobotMap.armPot.get() == RobotMap.BACK_ARM_CLEAR_VALUE) { // fix the == once we know pot values
                return true;
            } else {
                return false;
            }
        }
        if (lastDirection == DirectionEnum.BACK && direction == DirectionEnum.FRONT) {
            if (RobotMap.armPot.get() == RobotMap.FRONT_ARM_CLEAR_VALUE) { // fix the == once we know pot values
                return true;
            } else {
                return false;
            }
        } else {
            ConsoleLogger.error("Checking isArmCLear when not changing direction.");
            return false;
        }
    }

    public void setArmPosition(double position) {
        RobotMap.armPivot.set(ControlMode.Position, position);
    }

    
}