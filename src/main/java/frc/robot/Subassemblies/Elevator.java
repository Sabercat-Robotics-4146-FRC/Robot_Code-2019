package frc.robot.Subassemblies;

import frc.robot.RobotMap;

public class Elevator {

    public enum LevelEnum {
        STORAGE,
        INTAKING_CARGO,
        INTAKING_HATCH,     // change this to a better name 
        BOTTOM_PORT,
        SHIP_PORT,
        MID_HATCH,
        MID_PORT,
        TOP_HATCH,
        TOP_PORT
    }

    public enum DirectionEnum {
        FRONT,
        BACK
    }
    
    LevelEnum level = LevelEnum.STORAGE;
    DirectionEnum direction = DirectionEnum.FRONT;
    DirectionEnum lastDirection = DirectionEnum.FRONT;

    public void update() {

        if (RobotMap.driverController.getRightBumper()) { // switch the direction of the arm
            if (RobotMap.driverController.getLeftBumper()) { // going to a port hight
                if (RobotMap.driverController.getButtonA()) { 
                    // switch the direction and go to bottom port hight.
                    lastDirection = direction;
                    direction = direction ==  DirectionEnum.FRONT ? DirectionEnum.BACK : DirectionEnum.FRONT;
                    level = LevelEnum.BOTTOM_PORT;
                }
            }
        }

        switch(level) {
            case STORAGE:
                break;

            case INTAKING_CARGO:
                break;

            case INTAKING_HATCH:
                break;

            case BOTTOM_PORT:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                    }
                }
                break;

            case SHIP_PORT:
                break;

            case MID_HATCH:
                break;

            case MID_PORT:
                break;

            case TOP_HATCH:
                break;

            case TOP_PORT:
                break;
        }
    }
    
}
