package frc.robot.Subassemblies;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Utilities.Dashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Elevator {

    public Elevator() {
    
    }

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
    
    public LevelEnum level = LevelEnum.STORAGE;
    DirectionEnum direction = DirectionEnum.FRONT;
    DirectionEnum lastDirection = DirectionEnum.FRONT;

    String str = "";

    public void update() {

        // check the Limelight to see if the elevator is going to a port or hatch height
        if (level == LevelEnum.INTAKING_HATCH) {
            if (RobotMap.limelight.getY() >= RobotMap.LIMELIGHT_PORT_TAPE_HEIGHT) {
                level = LevelEnum.BOTTOM_PORT;
            }
        }
        if (level == LevelEnum.MID_HATCH) {
            if (RobotMap.limelight.getY() >= RobotMap.LIMELIGHT_PORT_TAPE_HEIGHT) {
                level = LevelEnum.MID_PORT;
            }
        }
        if (level == LevelEnum.TOP_HATCH) {
            if (RobotMap.limelight.getY() >= RobotMap.LIMELIGHT_PORT_TAPE_HEIGHT) {
                level = LevelEnum.TOP_PORT;
            }
        }

        switch(level) {
            case STORAGE:
                break;

            case INTAKING_CARGO:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Intaking Cargo");
                        moveElevator(RobotMap.FRONT_INTAKING_CARGO_HEIGHT);
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move the arm over and move elevator to the correct value
                        str = ("Switching to Front, Intaking Cargo");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.FRONT_INTAKING_CARGO_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // dont do anything, you cant intake from the back
                        str = ("Trying to Go to Back Cargo, Not Allowed, Do Nothing");
                    } else if (lastDirection == DirectionEnum.BACK) {
                       // dont do anything, you cant intake from the back
                       str = ("Trying to Go to Back Cargo, Not Allowed, Do Nothing");
                    }
                }
                break;

            case INTAKING_HATCH:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Intaking Hatch");
                        moveElevator(RobotMap.FRONT_INTAKING_HATCH_HEIGHT);
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Intaking Hatch");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.FRONT_INTAKING_HATCH_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Intaking Hatch");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.BACK_INTAKING_HATCH_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Intaking Hatch");
                        moveElevator(RobotMap.BACK_INTAKING_HATCH_HEIGHT);
                    }
                }
                break;

            case BOTTOM_PORT:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Bottom Port");
                        moveElevator(RobotMap.FRONT_BOTTOM_PORT_HEIGHT);
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Bottom Port");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.FRONT_BOTTOM_PORT_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Bottom Port");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.BACK_BOTTOM_PORT_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Bottom Port");
                        moveElevator(RobotMap.BACK_BOTTOM_PORT_HEIGHT);
                    }
                }
                break;

            case SHIP_PORT:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Ship Port");
                        moveElevator(RobotMap.FRONT_SHIP_PORT_HEIGHT);
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Ship Port");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.FRONT_SHIP_PORT_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Ship Port");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.BACK_SHIP_PORT_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Ship Port");
                        moveElevator(RobotMap.BACK_SHIP_PORT_HEIGHT);
                    }
                }
                break;

            case MID_HATCH:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Mid Hatch");
                        moveElevator(RobotMap.FRONT_MID_HATCH_HEIGHT);
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Mid Hatch");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.FRONT_MID_HATCH_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Mid Hatch");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.BACK_MID_HATCH_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Mid Hatch");
                        moveElevator(RobotMap.BACK_MID_HATCH_HEIGHT);
                    }
                }
                break;

            case MID_PORT:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Mid Port");
                        moveElevator(RobotMap.FRONT_MID_PORT_HEIGHT);
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Mid Port");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.FRONT_MID_PORT_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Mid Port");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.BACK_MID_PORT_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Mid Port");
                        moveElevator(RobotMap.BACK_MID_PORT_HEIGHT);
                    }
                }
                break;

            case TOP_HATCH:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Top Hatch");
                        moveElevator(RobotMap.FRONT_TOP_HATCH_HEIGHT);
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Top Hatch");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.FRONT_TOP_HATCH_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Top Hatch");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.BACK_TOP_HATCH_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Top Hatch");
                        moveElevator(RobotMap.BACK_TOP_HATCH_HEIGHT);
                    }
                }
                break;

            case TOP_PORT:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Top Port");
                        moveElevator(RobotMap.FRONT_TOP_PORT_HEIGHT);
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Top Port");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.FRONT_TOP_PORT_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Top Port");
                        if (RobotMap.arm.isArmClear()) {
                            moveElevator(RobotMap.BACK_TOP_PORT_HEIGHT);
                        } else {
                            moveElevator(RobotMap.ARM_OVER_HEIGHT);
                        }
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Top Port");
                        moveElevator(RobotMap.BACK_TOP_PORT_HEIGHT);
                    }
                }
                break;
        }

        Dashboard.send("Elevetor state", str);
    }

    public void upadteDirection() {
        lastDirection = direction;
    }
    
    public void changeDirection() {
        direction = direction == DirectionEnum.FRONT ? DirectionEnum.BACK : DirectionEnum.FRONT;
    }

    private void moveElevator(double setPoint) {
        RobotMap.elevatorFront.set(ControlMode.Position, setPoint);
    }

    public void setLevelIntakingHatch() {
        level = LevelEnum.INTAKING_HATCH;
    }
    
    public void setLevelIntakingCargo() {
        level = LevelEnum.INTAKING_CARGO;
    }

    public void setLevelMidHatch() {
        level = LevelEnum.MID_HATCH;
    }

    public void setLevelTopHatch() {
        level = LevelEnum.TOP_HATCH;
    }

    public void setLevelBottomPort() {
        level = LevelEnum.BOTTOM_PORT;
    }

    public void setLevelShipPort() {
        level = LevelEnum.SHIP_PORT;
    }
    
    public void setLevelMidPort() {
        level = LevelEnum.MID_PORT;
    }

    public void setLevelTopPort() {
        level = LevelEnum.TOP_PORT;
    }
    
}
