package frc.robot.Subassemblies;

import edu.wpi.first.wpilibj.Relay.Direction;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.MotionProfiling.GeneratedProfiles;
import frc.robot.MotionProfiling.MotionProfile;
import frc.robot.Utilities.Dashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Elevator {

    MotionProfile testProfile;

    public Elevator() {
        
        testProfile = new MotionProfile(RobotMap.motionProfileTalon, GeneratedProfiles.testProfile, GeneratedProfiles.kNumPointsTestProfile);
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
    
    LevelEnum level = LevelEnum.STORAGE;
    DirectionEnum direction = DirectionEnum.FRONT;
    DirectionEnum lastDirection = DirectionEnum.FRONT;

    String str = "";
    boolean rightBumperFlag = true;
    boolean buttonFlag = true;

    public void update() {

        if (RobotMap.driverController.getRightBumper()) { // switch the direction of the arm
            if ((RobotMap.driverController.getButtonA() || RobotMap.driverController.getButtonB() || 
                 RobotMap.driverController.getButtonX() || RobotMap.driverController.getButtonY()) && buttonFlag) { 
                buttonFlag = false;
                if (RobotMap.driverController.getLeftBumper()) { // going to a port height
                    if (RobotMap.driverController.getButtonA()) { 
                        // switch the direction and go to bottom port height.
                        lastDirection = direction;
                        extracted();
                        level = LevelEnum.BOTTOM_PORT;
                    } else if (RobotMap.driverController.getButtonB()) {
                        // switch direction and going to ship port height
                        lastDirection = direction;
                        extracted();
                        level = LevelEnum.SHIP_PORT;
                    } else if (RobotMap.driverController.getButtonX()) {
                        // switch direction and going to mid port height
                        lastDirection = direction;
                        extracted();
                        level = LevelEnum.MID_PORT;
                    } else if (RobotMap.driverController.getButtonY()) {
                        // switch direction and goin to top port height
                        lastDirection = direction;
                        extracted();
                        level = LevelEnum.TOP_PORT;
                    }
                } else { // going to a hatch height
                    if (RobotMap.driverController.getButtonA()) {
                        // switch direction and go to intaking hatch height
                        lastDirection = direction;
                        extracted();
                        level = LevelEnum.INTAKING_HATCH;
                    } else if (RobotMap.driverController.getButtonB()) {
                        // switch direction and go to intaking cargo height                                         check this so we can't intake cargo on wrong side of drivetrain
                        lastDirection = direction;
                        extracted();
                        level = LevelEnum.INTAKING_CARGO;
                    } else if (RobotMap.driverController.getButtonX()) {
                        // switch direction and go to mid hatch height
                        lastDirection = direction;
                        extracted();
                        level = LevelEnum.MID_HATCH;
                    } else if (RobotMap.driverController.getButtonY()) {
                        // switch direction and go to top hatch height
                        lastDirection = direction;
                        extracted();
                        level = LevelEnum.TOP_HATCH;
                    }
                }
            }
        } else { // staying on the same side of the elevator
            if (RobotMap.driverController.getLeftBumper()) { // going to a port height
                if (RobotMap.driverController.getButtonA()) {
                    // go to bottom port height
                    lastDirection = direction;
                    level = LevelEnum.BOTTOM_PORT;
                } else if (RobotMap.driverController.getButtonB()) {
                    // go to ship port height
                    lastDirection = direction;
                    level = LevelEnum.SHIP_PORT;
                } else if (RobotMap.driverController.getButtonX()) {
                    // go to mid port height
                    lastDirection = direction;
                    level = LevelEnum.MID_PORT;
                } else if (RobotMap.driverController.getButtonY()) {
                    // go to top port height
                    lastDirection = direction;
                    level = LevelEnum.TOP_PORT;
                }
            } else { // go to hatch height
                if (RobotMap.driverController.getButtonA()) {
                    // go to intaking hatch height
                    lastDirection = direction;
                    level = LevelEnum.INTAKING_HATCH;
                } else if (RobotMap.driverController.getButtonB()) {
                    // go to intaking cargo height                                                               check this so we can't intake cargo on wrong side of drivetrain
                    lastDirection = direction;
                    level = LevelEnum.INTAKING_CARGO;
                } else if (RobotMap.driverController.getButtonX()) {
                    // go to mid hatch height
                    lastDirection = direction;
                    level = LevelEnum.MID_HATCH;
                } else if (RobotMap.driverController.getButtonY()) {
                    // go to top hatch height
                    lastDirection = direction;
                    level = LevelEnum.TOP_HATCH;
                }
            }
        }

        // this is just for testing motion profiling
        if (RobotMap.driverController.getButtonStart()) {
            System.out.println("I want to run a profile");
            testProfile.startProfile();
            if (testProfile.isFinished()) {
                System.out.println("MP Finished");
            }
        } else {
            RobotMap.motionProfileTalon.set(ControlMode.PercentOutput, 0.0);
        }

        switch(level) {
            case STORAGE:
                break;

            case INTAKING_CARGO:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Intaking Cargo");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move the arm over and move elevator to the correct value
                        str = ("Switching to Front, Intaking Cargo");
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
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Intaking Hatch");
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Intaking Hatch");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Intaking Hatch");
                    }
                }
                break;

            case BOTTOM_PORT:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Bottom Port");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Bottom Port");
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Bottom Port");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Bottom Port");
                    }
                }
                break;

            case SHIP_PORT:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Ship Port");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Ship Port");
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Ship Port");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Ship Port");
                    }
                }
                break;

            case MID_HATCH:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Mid Hatch");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Mid Hatch");
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Mid Hatch");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Mid Hatch");
                    }
                }
                break;

            case MID_PORT:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Mid Port");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Mid Port");
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Mid Port");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Mid Port");
                    }
                }
                break;

            case TOP_HATCH:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Top Hatch");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Top Hatch");
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Top Hatch");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Top Hatch");
                    }
                }
                break;

            case TOP_PORT:
                if (direction == DirectionEnum.FRONT) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // lift the elevator to the correct value
                        str = ("Front, Top Port");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Front, Top Port");
                    }
                } else if (direction == DirectionEnum.BACK) {
                    if (lastDirection == DirectionEnum.FRONT) {
                        // move arm over and lift elevator to the correct value
                        str = ("Switching to Back, Top Port");
                    } else if (lastDirection == DirectionEnum.BACK) {
                        // lift the elevator to the correct value
                        str = ("Back, Top Port");
                    }
                }
                break;
        }

        // if (!RobotMap.driverController.getRightBumper()) {
        //     rightBumperFlag = true;
        // }

        if (!RobotMap.driverController.getButtonA() && !RobotMap.driverController.getButtonB() && 
            !RobotMap.driverController.getButtonX() && !RobotMap.driverController.getButtonY()) {
            buttonFlag = true;
        }

        Dashboard.send("Elevetor state", str);
    }

    private void extracted() {
        direction = direction == DirectionEnum.FRONT ? DirectionEnum.BACK : DirectionEnum.FRONT;
        // if (rightBumperFlag && RobotMap.driverController.getRightBumper()) {
        //     System.out.println("yo");
        //     direction = direction == DirectionEnum.FRONT ? DirectionEnum.BACK : DirectionEnum.FRONT;
        //     rightBumperFlag = false;
        // }
        
    }
    
}
