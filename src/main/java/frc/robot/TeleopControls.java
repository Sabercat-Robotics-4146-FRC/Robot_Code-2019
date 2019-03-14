package frc.robot;

import frc.robot.Subassemblies.Arm.ArmEnum;
import frc.robot.Subassemblies.Intake.CargoRollerEnum;
import frc.robot.Utilities.Dashboard;
import frc.robot.Utilities.Limelight.LEDEnum;
import frc.robot.Subassemblies.Arm.DirectionEnum;

public class TeleopControls {

    public enum TCDirectionEnum {
        FRONT,
        BACK
    }

    TCDirectionEnum direction = TCDirectionEnum.FRONT;
    TCDirectionEnum lastDirection = TCDirectionEnum.FRONT;

    boolean buttonFlag;
    public boolean isUpdating = true;
    boolean ledFlag = false;
    boolean hatchFlag = true;

    public void update() {
        // controlls for elevator
        // <editor-fold>
        if (RobotMap.pilotController.getRightBumper()) { // switch the direction of the arm
            if ((RobotMap.pilotController.getButtonA() || RobotMap.pilotController.getButtonB()
                    || RobotMap.pilotController.getButtonX() || RobotMap.pilotController.getButtonY())
                    && buttonFlag) {
                buttonFlag = false;
                setIsUpdating(true);

                // makes sure that the direction the code thinks the arm is at is the right direction
                if (RobotMap.arm.isArmInFront()) {
                    setDirectionFront();
                } else if (RobotMap.arm.isArmInBack()) {
                    setDirectionBack();
                }

                if (RobotMap.pilotController.getLeftBumper()) { // going to a port height
                    if (RobotMap.pilotController.getButtonA()) {
                        // switch the direction and go to bottom port height.
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateFrontBottomCargo();
                            RobotMap.elevator.setLevelBottomPort();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateBackBottomCargo();
                            RobotMap.elevator.setLevelBottomPort();
                        }
                    } else if (RobotMap.pilotController.getButtonB()) {      // we still need the bumper for the cargo ship heights
                        // switch direction and going to ship port height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateFrontCargoShipPort();
                            RobotMap.elevator.setLevelShipPort();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateBackCargoShipPort();
                            RobotMap.elevator.setLevelShipPort();
                        }
                    } else if (RobotMap.pilotController.getButtonX()) {
                        // switch direction and going to mid port height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateFrontMidCargo();
                            RobotMap.elevator.setLevelMidPort();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateBackMidCargo();
                            RobotMap.elevator.setLevelMidPort();
                        }
                    } else if (RobotMap.pilotController.getButtonY()) {
                        // switch direction and goin to top port height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateFrontTopCargo();
                            RobotMap.elevator.setLevelTopPort();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateBackTopCargo();
                            RobotMap.elevator.setLevelTopPort();
                        }
                    }
                } else { // going to a hatch height
                    if (RobotMap.pilotController.getButtonA()) {
                        // switch direction and go to intaking hatch height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateFrontTilt();
                            RobotMap.elevator.setLevelIntakingHatch();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateBackTilt();
                            RobotMap.elevator.setLevelIntakingHatch();
                        }
                    } else if (RobotMap.pilotController.getButtonB()) {
                        // // tilt arm up to storage 
                        // updateDirection();
                        // RobotMap.arm.setArmStateStorage();
                        if (RobotMap.arm.isArmInFront()) {
                            Dashboard.send("Elevetor state", "Trying to Go to Back Cargo, Not Allowed, Do Nothing");
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateBackTiltForIntakingCargo();
                            RobotMap.elevator.setLevelIntakingCargo();
                        }
                    } else if (RobotMap.pilotController.getButtonX()) {
                        // switch direction and go to mid hatch height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateFrontTilt();
                            RobotMap.elevator.setLevelMidHatch();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateBackTilt();
                            RobotMap.elevator.setLevelMidHatch();
                        }
                    } else if (RobotMap.pilotController.getButtonY()) {
                        // switch direction and go to top hatch height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateFrontTilt();
                            RobotMap.elevator.setLevelTopHatch();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            changeDirection();
                            RobotMap.arm.setArmStateBackTilt();
                            RobotMap.elevator.setLevelTopHatch();
                        }
                    }
                } // port 
            }
        } else { // staying on the same side of the elevator
            if ((RobotMap.pilotController.getButtonA() || RobotMap.pilotController.getButtonB()
                    || RobotMap.pilotController.getButtonX() || RobotMap.pilotController.getButtonY())
                    && buttonFlag) {
                buttonFlag = false;
                setIsUpdating(true);
                
                // makes sure that the direction the code thinks the arm is at is the right direction
                if (RobotMap.arm.isArmInFront()) {
                    setDirectionFront();
                } else if (RobotMap.arm.isArmInBack()) {
                    setDirectionBack();
                }

                if (RobotMap.pilotController.getLeftBumper()) { // going to a port height
                    if (RobotMap.pilotController.getButtonA()) {
                        // go to bottom port height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            RobotMap.arm.setArmStateFrontBottomCargo();
                            RobotMap.elevator.setLevelBottomPort();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            RobotMap.arm.setArmStateBackBottomCargo();
                            RobotMap.elevator.setLevelBottomPort();
                        }
                    } else if (RobotMap.pilotController.getButtonB()) {             // we still need the bumper for the cargo ship heights
                        // go to ship port height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            RobotMap.arm.setArmStateFrontCargoShipPort();
                            RobotMap.elevator.setLevelShipPort();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            RobotMap.arm.setArmStateBackCargoShipPort();
                            RobotMap.elevator.setLevelShipPort();
                        }
                    } else if (RobotMap.pilotController.getButtonX()) {
                        // go to mid port height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            RobotMap.arm.setArmStateFrontMidCargo();
                            RobotMap.elevator.setLevelMidPort();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            RobotMap.arm.setArmStateBackMidCargo();
                            RobotMap.elevator.setLevelMidPort();
                        }
                    } else if (RobotMap.pilotController.getButtonY()) {
                        // go to top port height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            RobotMap.arm.setArmStateFrontTopCargo();
                            RobotMap.elevator.setLevelTopPort();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            RobotMap.arm.setArmStateBackTopCargo();
                            RobotMap.elevator.setLevelTopPort();
                        }
                    }
                } else { // go to hatch height
                    if (RobotMap.pilotController.getButtonA()) {
                        // go to intaking hatch height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            RobotMap.arm.setArmStateFrontLevel();
                            RobotMap.elevator.setLevelIntakingHatch();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            RobotMap.arm.setArmStateBackLevel();
                            RobotMap.elevator.setLevelIntakingHatch();
                        }
                    } else if (RobotMap.pilotController.getButtonB()) {
                        // // tilt arm up to storage 
                        // updateDirection();
                        // RobotMap.arm.setArmStateStorage();
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            RobotMap.arm.setArmStateFrontIntakingCargo();
                            RobotMap.elevator.setLevelIntakingCargo();
                        } else if (RobotMap.arm.isArmInBack()) {
                            Dashboard.send("Elevetor state", "Trying to Go to Back Cargo, Not Allowed, Do Nothing");
                        }
                    } else if (RobotMap.pilotController.getButtonX()) {
                        // go to mid hatch height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            RobotMap.arm.setArmStateFrontLevel();
                            RobotMap.elevator.setLevelMidHatch();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            RobotMap.arm.setArmStateBackLevel();
                            RobotMap.elevator.setLevelMidHatch();
                        }
                    } else if (RobotMap.pilotController.getButtonY()) {
                        // go to top hatch height
                        if (RobotMap.arm.isArmInFront()) {
                            updateDirection();
                            RobotMap.arm.setArmStateFrontLevel();
                            RobotMap.elevator.setLevelTopHatch();
                        } else if (RobotMap.arm.isArmInBack()) {
                            updateDirection();
                            RobotMap.arm.setArmStateBackLevel();
                            RobotMap.elevator.setLevelTopHatch();
                        }
                    }
                } // port 
            }
        }

        if (!RobotMap.pilotController.getButtonA() && !RobotMap.pilotController.getButtonB()
                && !RobotMap.pilotController.getButtonX() && !RobotMap.pilotController.getButtonY()) {
            buttonFlag = true;
        }

        // controlls for arm storage
        if (RobotMap.pilotController.getButtonBack()) {
            RobotMap.arm.setArmStateStorage();
        }

        // </editor-fold>

        // controlls for drive train
        // <editor-fold>
        if (RobotMap.pilotController.getButtonStart()) {
            RobotMap.limelight.setLightMode(LEDEnum.ENABLED);
            ledFlag = false;

            if (RobotMap.limelight.hasValidTarget()) {
                RobotMap.drivetrain.runVisionPID(RobotMap.pilotController.getDeadbandLeftYAxis());
            } else {
                RobotMap.drivetrain.setDrivetrainValues(RobotMap.pilotController.getDeadbandLeftYAxis(),
                        RobotMap.pilotController.getDeadbandRightXAxis());
            }
        } else {
            if (!ledFlag) {
                RobotMap.limelight.setLightMode(LEDEnum.DISABLED);
                ledFlag = true;
            }
            RobotMap.drivetrain.setDrivetrainValues(RobotMap.pilotController.getDeadbandLeftYAxis(),
                    RobotMap.pilotController.getDeadbandRightXAxis());

            RobotMap.drivetrain.restartVisionPID();
        }
        // </editor-fold>

        // controlls for intake/hatch manipulator
        // <editor-fold>
        if (RobotMap.pilotController.getRightTriggerBool()) {
            RobotMap.intake.setCargoRollerState(CargoRollerEnum.INTAKING);
        } else if (RobotMap.pilotController.getLeftTriggerBool()) {
            RobotMap.intake.setCargoRollerState(CargoRollerEnum.OUTPUTTING);
        } else {
            RobotMap.intake.setCargoRollerState(CargoRollerEnum.DISABLED);
        }

        if (RobotMap.pilotController.getDPadBool() && hatchFlag) {
            RobotMap.intake.toggleClaw();
            hatchFlag = false;
            
        }
        if (!RobotMap.pilotController.getDPadBool()) {
            hatchFlag = true;
        }
        // </editor-fold>
    
        // controls for EGL
        // <editor-fold>
        if (RobotMap.copilotController.getRightBumper()) {
            RobotMap.egl.raiseRobot();
        } else if (RobotMap.copilotController.getLeftBumper()) {
            RobotMap.egl.lowerRobot();
        }
        // </editor-fold>
    }

    public boolean getIsUpdating() {
        return isUpdating;
    }

    public void setIsUpdating(boolean isUpdating) {
        this.isUpdating = isUpdating;
    }

    public void updateDirection() {
        RobotMap.arm.updateDirection();
        RobotMap.elevator.updateDirection();
    }

    public void changeDirection() {
        RobotMap.arm.changeDirection();
        RobotMap.elevator.changeDirection();
    }

    public void setDirectionFront() {
        RobotMap.arm.setDirectionFront();
        RobotMap.elevator.setDirectionFront();
    }

    public void setDirectionBack() {
        RobotMap.arm.setDirectionBack();
        RobotMap.elevator.setDirectionBack();
    }

}
