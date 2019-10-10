package frc.robot;

import frc.robot.Subassemblies.Intake.CargoRollerEnum;
import frc.robot.Utilities.ConsoleLogger;
import frc.robot.Utilities.Dashboard;
import frc.robot.Utilities.Limelight.LEDEnum;
import frc.robot.Subassemblies.ElevatorAndArm.ScoringPosition;

public class TeleopControls {
    boolean ledFlag = false;
    boolean hatchFlag = true;
    boolean buttonFlag = false;
    boolean armOffsetFlag = false;
    boolean manualModeFlag = false;

    public void update() {
      // controlls for elevator

       //manualMode
      if (!manualModeFlag && RobotMap.copilotController.getButtonA()) {
        RobotMap.elevatorAndArm.toggleManualMode();
        manualModeFlag = true;
      }

      if (!RobotMap.copilotController.getButtonA()) {
        manualModeFlag = false;
      }

        // state machine
        if ((RobotMap.pilotController.getButtonA() || RobotMap.pilotController.getButtonB() ||
                RobotMap.pilotController.getButtonX() || RobotMap.pilotController.getButtonY() ||
                RobotMap.pilotController.getButtonBack()) && !buttonFlag && !RobotMap.elevatorAndArm.getManualMode()) {
            buttonFlag = true;

              // Not Switching Sides
                if (RobotMap.pilotController.getLeftBumper()) { // Port Height
                    if (RobotMap.pilotController.getButtonY()) {
                        if (RobotMap.elevatorAndArm.isArmPhisicalyInFront()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_TOP_ROCKET_PORT);
                        } else if (RobotMap.elevatorAndArm.isArmPhisicalyInBack()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_TOP_ROCKET_PORT);
                        }
                    } else if (RobotMap.pilotController.getButtonX()) {
                        if (RobotMap.elevatorAndArm.isArmPhisicalyInFront()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_MID_ROCKET_PORT);
                        } else if (RobotMap.elevatorAndArm.isArmPhisicalyInBack()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_MID_ROCKET_PORT);
                        }
                    } else if (RobotMap.pilotController.getButtonB()) {
                        if (RobotMap.elevatorAndArm.isArmPhisicalyInFront()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_CARGO_SHIP_PORT);
                        } else if (RobotMap.elevatorAndArm.isArmPhisicalyInBack()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_CARGO_SHIP_PORT);
                        }
                    } else if (RobotMap.pilotController.getButtonA()) {
                        if (RobotMap.elevatorAndArm.isArmPhisicalyInFront()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_BOTTOM_ROCKET_PORT);
                        } else if (RobotMap.elevatorAndArm.isArmPhisicalyInBack()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_BOTTOM_ROCKET_PORT);
                        }
                    } else if (RobotMap.pilotController.getButtonBack()) {
                        if (RobotMap.elevatorAndArm.isArmPhisicalyInFront()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_STORAGE);
                        } else if (RobotMap.elevatorAndArm.isArmPhisicalyInBack()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_STORAGE);
                        }
                    }
                } else { // Not A Port Height. Hatch Height
                    if (RobotMap.pilotController.getButtonY()) {
                        if (RobotMap.elevatorAndArm.isArmPhisicalyInFront()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_TOP_ROCKET_HATCH);
                        } else if (RobotMap.elevatorAndArm.isArmPhisicalyInBack()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_TOP_ROCKET_HATCH);
                        }
                    } else if (RobotMap.pilotController.getButtonX()) {
                        if (RobotMap.elevatorAndArm.isArmPhisicalyInFront()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_MID_ROCKET_HATCH);
                        } else if (RobotMap.elevatorAndArm.isArmPhisicalyInBack()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_MID_ROCKET_HATCH);
                        }
                    } else if (RobotMap.pilotController.getButtonA()) {
                        if (RobotMap.elevatorAndArm.isArmPhisicalyInFront()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_INTAKING_ROCKET_HATCH);
                        } else if (RobotMap.elevatorAndArm.isArmPhisicalyInBack()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_INTAKING_ROCKET_HATCH);
                        }
                    } else if (RobotMap.pilotController.getButtonB()) {
                        if (RobotMap.elevatorAndArm.isArmPhisicalyInFront()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.INTAKING_CARGO);
                        } else if (RobotMap.elevatorAndArm.isArmPhisicalyInBack()) {
                            ConsoleLogger.warning("Tried to intake cargo from back. Not allowed.");
                        }
                    } else if (RobotMap.pilotController.getButtonBack()) {
                        if (RobotMap.elevatorAndArm.isArmPhisicalyInFront()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.FRONT_STORAGE);
                        } else if (RobotMap.elevatorAndArm.isArmPhisicalyInBack()) {
                            RobotMap.elevatorAndArm.setScoringPosition(ScoringPosition.BACK_STORAGE);
                        }
                    
                    }
                }
        }

        if (!RobotMap.pilotController.getButtonA() && !RobotMap.pilotController.getButtonB() &&
                !RobotMap.pilotController.getButtonX() && !RobotMap.pilotController.getButtonY() &&
                !RobotMap.pilotController.getButtonBack()) {
            buttonFlag = false;
        }

        // Arm offsett setting code

        if (!armOffsetFlag && RobotMap.copilotController.getRightBumper()) {
            armOffsetFlag = true;
            RobotMap.armOffset += 5;
        } else if (!armOffsetFlag && RobotMap.copilotController.getLeftBumper()) {
            armOffsetFlag = true;
            RobotMap.armOffset -= 5;
        }

        if (!RobotMap.copilotController.getRightBumper() && !RobotMap.copilotController.getLeftBumper()) {
            armOffsetFlag = false;
        }

        if (RobotMap.copilotController.getButtonB()) {
            RobotMap.armOffset = 0;
        }

        // controlls for drive train
        if (RobotMap.pilotController.getLeftStickPress()) {
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

        // Changed from d-pad to R3 Press
        if (/*RobotMap.pilotController.getDPadBool()*/ RobotMap.pilotController.getRightStickPress() && hatchFlag) {
            RobotMap.intake.toggleClaw();
            hatchFlag = false;

        }
        if (!RobotMap.pilotController.getRightStickPress()/*RobotMap.pilotController.getDPadBool()*/) {
            hatchFlag = true;
        }
        // </editor-fold>

        // controls for EGL
        // <editor-fold>
        // if (RobotMap.copilotController.getRightBumper()) {
        //     RobotMap.egl.raiseRobot();
        // } else if (RobotMap.copilotController.getLeftBumper()) {
        //     RobotMap.egl.lowerRobot();
        // }
        // </editor-fold>
    }
  }
